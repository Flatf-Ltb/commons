package io.flatf.common.collections.list;

public abstract class LimitedContainer<E> {

    private int head = 0;

    private int tail = 0;

    private int count = 0;

    private final int capacity;

    protected LimitedContainer(int capacity) {
        this.capacity = capacity;
    }

    protected int headIndex() {
        return head;
    }

    protected int tailIndex() {
        return tail;
    }

    public int count() {
        return count;
    }

    public void add(E e) {
        updateTail();
        setTail(tail, e);
        updateHead();
        updateCount();
    }

    private void updateTail() {
        if (++tail == capacity)
            tail = 0;
    }

    private void updateHead() {
        if (count == capacity) {
            if (++head == capacity)
                head = 0;
        }
    }

    private void updateCount() {
        if (count < capacity)
            count++;
    }

    protected abstract void setTail(int tail, E e);

    public abstract E getTail();

    public abstract E getHead();

}
