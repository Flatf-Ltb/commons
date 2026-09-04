package io.flatf.common.concurrent.disruptor;

import com.lmax.disruptor.EventHandler;
import io.flatf.common.thread.Sleep;
import org.junit.Test;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicIntegerArray;

import static io.flatf.common.concurrent.disruptor.SimpleWaitStrategy.YIELDING;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * 阶段串接守卫：管线中第 N 阶段必须在第 N-1 阶段处理完<b>同一事件</b>之后才能处理它。
 *
 * <p>已有的 {@code RingProcessChainTest} 也构建三阶段管线，但只断言每个处理器收到的事件总数，
 * 不校验阶段之间的先后关系——所以它在阶段串接失效时同样通过。本测试补的正是这个缺口。
 *
 * <p>失效形态是安静的：{@code EventHandlerGroup.then(...)} 返回新分组，若不回写，后续阶段会全部挂在
 * stage 0 的屏障上，与前一阶段<b>并发</b>执行。不抛异常，只是让下游读到写了一半的上游状态。
 * 两阶段时结果恰好正确，因此该缺陷只在三阶段起才显形。
 */
public class EventHandlerGraphStageOrderingTest {

    private static final int EVENT_COUNT = 4;

    /**
     * 每个非末阶段的驻留时间。用于放大窗口：若下游阶段没有被上游 gate 住，它必然抢跑。
     */
    private static final long STAGE_DWELL_MILLIS = 20L;

    private static final long AWAIT_SECONDS = 10L;

    /**
     * 两阶段：串接本来就正确，作为回归基线固定住——{@code PipelineAssembler} 目前正是这个形状。
     */
    @Test
    public void twoStagePipelineRunsStagesInOrder() {
        assertStagesRunInOrder(2);
    }

    /**
     * 三阶段：菱形工作流（状态处理 -> 策略并行 -> 信号汇聚）的形状，缺陷在此显形。
     */
    @Test
    public void threeStagePipelineRunsStagesInOrder() {
        assertStagesRunInOrder(3);
    }

    @Test
    public void fourStagePipelineRunsStagesInOrder() {
        assertStagesRunInOrder(4);
    }

    /**
     * 构建 {@code stageCount} 个单处理器阶段的管线，逐事件校验相邻阶段的先后关系。
     *
     * <p>违例记录进队列而不是就地抛出：处理器内抛出的异常会被 Disruptor 的异常处理器吞掉，
     * 断言必须回到主线程做。
     */
    private static void assertStagesRunInOrder(int stageCount) {
        var completed = new AtomicIntegerArray[stageCount];
        for (int stage = 0; stage < stageCount; stage++)
            completed[stage] = new AtomicIntegerArray(EVENT_COUNT);

        Queue<String> violations = new ConcurrentLinkedQueue<>();
        var finished = new CountDownLatch(EVENT_COUNT);

        @SuppressWarnings("unchecked")
        EventHandler<ReusableLongEvent>[] handlers = new EventHandler[stageCount];
        for (int stage = 0; stage < stageCount; stage++) {
            final int current = stage;
            final boolean last = stage == stageCount - 1;
            handlers[stage] = (event, sequence, endOfBatch) -> {
                int index = (int) event.get();
                if (current > 0 && completed[current - 1].get(index) != 1)
                    violations.add("stage" + current + " processed event " + index
                        + " before stage" + (current - 1) + " completed it");
                if (!last)
                    Sleep.millis(STAGE_DWELL_MILLIS);
                completed[current].set(index, 1);
                if (last)
                    finished.countDown();
            };
        }

        RingEventbus<ReusableLongEvent> pipeline = RingEventbus
            .singleProducer(ReusableLongEvent.FACTORY)
            .name("Test-StageOrdering-" + stageCount)
            .size(32)
            .waitStrategy(YIELDING.getInstance())
            .buildPipelineWith(handlers);
        try {
            var publisher = pipeline.newPublisher(
                (ReusableLongEvent event, long sequence, Long value) -> event.set(value));
            for (long value = 0L; value < EVENT_COUNT; value++)
                publisher.publish(value);

            assertTrue(stageCount + " 阶段管线未在 " + AWAIT_SECONDS + "s 内处理完全部事件",
                finished.await(AWAIT_SECONDS, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new AssertionError("等待管线完成时被中断", e);
        } finally {
            pipeline.stop();
        }

        assertEquals("阶段串接失效: " + violations, 0, violations.size());
        for (int stage = 0; stage < stageCount; stage++)
            for (int index = 0; index < EVENT_COUNT; index++)
                assertEquals("stage" + stage + " 未处理事件 " + index,
                    1, completed[stage].get(index));
    }

}
