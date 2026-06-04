package io.flatf.common.collections.queue;

/**
 * @param <E>
 * @author yellow013
 */
public class EventContainer<E> {

    private int type;

    private E event;

    /**
     * @param event E
     */
    public void loading(E event) {
        this.type = 0;
        this.event = event;
    }

    /**
     * @param type  int
     * @param event E
     */
    public void loading(int type, E event) {
        this.type = type;
        this.event = event;
    }

    public int type() {
        return type;
    }

    public E unloading() {
        return event;
    }

}