package io.flatf.common.collections.queue;

import io.flatf.common.collections.MutableSets;
import org.eclipse.collections.api.set.sorted.MutableSortedSet;

import javax.annotation.concurrent.NotThreadSafe;
import java.util.Collection;
import java.util.Optional;

@NotThreadSafe
public class PriorityQueue<E> {

    // 优先集合
    private final MutableSortedSet<E> prioritySet = MutableSets.newTreeSortedSet();

    // 次级集合
    private final MutableSortedSet<E> secondarySet = MutableSets.newTreeSortedSet();

    /**
     * @param collection Collection<E>
     * @return boolean
     */
    public boolean addPriority(Collection<E> collection) {
        return prioritySet.addAll(collection);
    }

    /**
     * @param e E
     * @return boolean
     */
    public boolean addPriority(E e) {
        return prioritySet.add(e);
    }

    /**
     * @param collection Collection<E>
     * @return boolean
     */
    public boolean addSecondary(Collection<E> collection) {
        return secondarySet.addAll(collection);
    }

    /**
     * @param e E
     * @return boolean
     */
    public boolean addSecondary(E e) {
        return secondarySet.add(e);
    }

    /**
     * @return boolean
     */
    public boolean isEmpty() {
        return prioritySet.isEmpty() && secondarySet.isEmpty();
    }

    /**
     * @return Optional<E>
     */
    public Optional<E> next() {
        return prioritySet.notEmpty() ? Optional.of(prioritySet.first())
                : secondarySet.notEmpty() ? Optional.of(secondarySet.first())
                : Optional.empty();
    }

}
