package io.flatf.common.collections.counter;

/**
 * 累加计数器接口
 *
 * @param <T>
 * @author yellow013
 */
public interface Counter<T extends Counter<T>> {

    /**
     * 设置一个值, tag应使用唯一值, 重复的tag将覆盖已有tag
     *
     * @param tag   long
     * @param delta long
     * @return T
     */
    T add(long tag, long delta);

    /**
     * @param tag   long
     * @param delta long
     * @return T
     */
    default T subtract(long tag, long delta) {
        return add(tag, -delta);
    }

    /**
     * 移除一个tag和tag指向的值
     *
     * @param tag long
     * @return T
     */
    T removeDelta(long tag);

    /**
     * 在历史delta上添加新的delta
     *
     * @param tag   long
     * @param delta long
     * @return T
     */
    T addDelta(long tag, long delta);

    /**
     * @param tag   long
     * @param delta long
     * @return T
     */
    default T subtractDelta(long tag, long delta) {
        return addDelta(tag, -delta);
    }

    /**
     * @param tag long
     * @return T
     */
    default T increment(long tag) {
        return add(tag, 1);
    }

    /**
     * @param tag long
     * @return T
     */
    default T decrement(long tag) {
        return subtract(tag, 1);
    }

    /**
     * @return long
     */
    long getValue();

}
