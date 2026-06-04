package io.flatf.common.graph;

import org.eclipse.collections.api.set.ImmutableSet;
import org.jgrapht.Graph;
import org.jgrapht.traverse.BreadthFirstIterator;
import org.jgrapht.traverse.DepthFirstIterator;

import static io.flatf.common.collections.MutableSets.newUnifiedSet;

public final class VertexScanner {

    private VertexScanner() {
    }

    /**
     * @param <V>   V
     * @param <E>   E extends Edge
     * @param graph Graph<V, E>
     * @param start V type
     * @return ImmutableSet<V>
     */
    public <V, E extends Edge> ImmutableSet<V> breadthFirst(Graph<V, E> graph, V start) {
        return newUnifiedSet(new BreadthFirstIterator<>(graph, start)).toImmutable();
    }

    /**
     * @param <V>   start
     * @param <E>   E extends Edge
     * @param graph Graph<V, E>
     * @param start V
     * @return ImmutableSet<V>
     */
    public <V, E extends Edge> ImmutableSet<V> depthFirst(Graph<V, E> graph, V start) {
        return newUnifiedSet(new DepthFirstIterator<>(graph, start)).toImmutable();
    }

}
