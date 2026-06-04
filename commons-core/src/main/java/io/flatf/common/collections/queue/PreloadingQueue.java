package io.flatf.common.collections.queue;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.LinkedList;

@NotThreadSafe
public class PreloadingQueue<T> {

    private final LinkedList<T> list = new LinkedList<>();

    public T next() {
        return list.removeFirst();
    }

    public void add(T content) {
        list.addLast(content);
    }

    public boolean isEmpty() {
        return list.isEmpty();
    }

    public boolean notEmpty() {
        return !isEmpty();
    }

    public LinkedList<T> getLinkedList() {
        return list;
    }

}