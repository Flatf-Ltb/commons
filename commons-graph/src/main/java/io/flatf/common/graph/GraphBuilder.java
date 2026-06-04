package io.flatf.common.graph;

import io.flatf.common.log4j2.Log4j2LoggerFactory;
import org.jgrapht.Graph;
import org.jgrapht.graph.builder.GraphTypeBuilder;
import org.slf4j.Logger;

import java.util.function.Supplier;

public final class GraphBuilder {

    private static final Logger log = Log4j2LoggerFactory.getLogger(GraphBuilder.class);

    /**
     * @param <V>         V
     * @param <E>         E
     * @param vertexClass Class<V>
     * @param edgeClass   Class<E>
     * @return Graph<V, E>
     */
    public static <V, E extends Edge> Graph<V, E> directed(Class<V> vertexClass, Class<E> edgeClass) {
        return directed(vertexClass, edgeClass, GraphOptions.DEFAULT);
    }

    /**
     * @param <V>         V
     * @param <E>         E
     * @param vertexClass Class<V>
     * @param edgeClass   Class<E>
     * @param options     GraphOptions
     * @return Graph<V, E>
     */
    public static <V, E extends Edge> Graph<V, E> directed(Class<V> vertexClass, Class<E> edgeClass,
                                                           GraphOptions options) {
        GraphTypeBuilder<V, E> builder = GraphTypeBuilder.directed()
                .vertexClass(vertexClass)
                .edgeClass(edgeClass)
                .allowingMultipleEdges(options.isAllowingMultipleEdges())
                .allowingSelfLoops(options.isAllowingSelfLoops())
                .weighted(options.isWeighted());
        log.info("GraphBuilder -> {}, VertexClass -> {}, EdgeClass -> {}", builder.buildType(), vertexClass, edgeClass);
        return builder.buildGraph();
    }

    /**
     * @param <V>         V
     * @param vertexClass Class<V>
     * @return Graph<V, Edge>
     */
    public static <V> Graph<V, Edge> directed(Class<V> vertexClass) {
        return directed(vertexClass, GraphOptions.DEFAULT);
    }

    /**
     * @param <V>         V
     * @param vertexClass Class<V>
     * @param options     GraphOptions
     * @return Graph<V, Edge>
     */
    public static <V> Graph<V, Edge> directed(Class<V> vertexClass, GraphOptions options) {
        return directed(vertexClass, Edge::new, options);
    }

    /**
     * @param <V>          V
     * @param <E>          E
     * @param vertexClass  Class<V>
     * @param edgeSupplier Supplier<E>
     * @return Graph<V, E>
     */
    public static <V, E extends Edge> Graph<V, E> directed(Class<V> vertexClass, Supplier<E> edgeSupplier) {
        return directed(vertexClass, edgeSupplier, GraphOptions.DEFAULT);
    }

    /**
     * @param <V>          V
     * @param <E>          E
     * @param vertexClass  Class<V>
     * @param edgeSupplier Supplier<E>
     * @param options      GraphOptions
     * @return Graph<V, E>
     */
    public static <V, E extends Edge> Graph<V, E> directed(Class<V> vertexClass, Supplier<E> edgeSupplier,
                                                           GraphOptions options) {
        GraphTypeBuilder<V, E> builder = GraphTypeBuilder.directed()
                .vertexClass(vertexClass)
                .edgeSupplier(edgeSupplier)
                .allowingMultipleEdges(options.isAllowingMultipleEdges())
                .allowingSelfLoops(options.isAllowingSelfLoops())
                .weighted(options.isWeighted());
        log.info("GraphBuilder -> {}, VertexClass -> {}", builder.buildType(), vertexClass);
        return builder.buildGraph();
    }

    /**
     * @author yellow013
     */
    public static class GraphOptions {

        private boolean allowingMultipleEdges = false;
        private boolean allowingSelfLoops = false;
        private boolean weighted = false;

        public static final GraphOptions DEFAULT = new GraphOptions();

        private GraphOptions() {
        }

        public static GraphOptions newInstance() {
            return new GraphOptions();
        }


        public boolean isAllowingMultipleEdges() {
            return allowingMultipleEdges;
        }

        public boolean isAllowingSelfLoops() {
            return allowingSelfLoops;
        }

        public boolean isWeighted() {
            return weighted;
        }

        public GraphOptions allowingMultipleEdges() {
            this.allowingMultipleEdges = true;
            return this;
        }

        public GraphOptions allowingSelfLoops() {
            this.allowingSelfLoops = true;
            return this;
        }

        public GraphOptions enableWeighted() {
            this.weighted = true;
            return this;
        }

    }

}
