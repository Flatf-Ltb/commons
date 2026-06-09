package io.flatf.common.concurrent.disruptor;

import com.lmax.disruptor.EventHandler;
import org.junit.Test;

public class EventHandlerGraphValidationTest {

    @Test(expected = IllegalArgumentException.class)
    public void rejectsEmptyInitialHandlerStage() {
        EventHandlerGraph.<ReusableLongEvent>with();
    }

    @Test(expected = NullPointerException.class)
    public void rejectsNullInitialHandler() {
        EventHandlerGraph.with((EventHandler<ReusableLongEvent>) null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void rejectsEmptyPipelineStage() {
        EventHandlerGraph
                .with(noopHandler())
                .then();
    }

    @Test(expected = NullPointerException.class)
    public void rejectsNullPipelineHandler() {
        EventHandlerGraph
                .with(noopHandler())
                .then((EventHandler<ReusableLongEvent>) null);
    }

    private static EventHandler<ReusableLongEvent> noopHandler() {
        return (event, sequence, endOfBatch) -> {
        };
    }
}
