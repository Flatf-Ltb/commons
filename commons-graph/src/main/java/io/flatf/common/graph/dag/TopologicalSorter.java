package io.flatf.common.graph.dag;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="michal.maczka@dimatics.com">Michal Maczka</a>
 */
public class TopologicalSorter {

    private final static Integer NOT_VISITED = 0;

    private final static Integer VISITING = 1;

    private final static Integer VISITED = 2;

    /**
     * @param graph DAG
     * @return List of String (vertex labels)
     */

    public static List<String> sort(final DAG graph) {
        return dfs(graph);
    }

    public static List<String> sort(final Vertex vertex) {
        // we need to use addFirst method, so we will use LinkedList explicitly
        final List<String> retValue = new LinkedList<>();
        dfsVisit(vertex, new HashMap<>(), retValue);
        return retValue;
    }

    private static List<String> dfs(final DAG graph) {
        // we need to use addFirst method, so we will use LinkedList explicitly
        List<String> retValue = new LinkedList<>();
        Map<Vertex, Integer> vertexStateMap = new HashMap<>();
        for (Vertex vertex : graph.getVertices()) {
            if (isNotVisited(vertex, vertexStateMap)) {
                dfsVisit(vertex, vertexStateMap, retValue);
            }
        }
        return retValue;
    }

    /**
     * @param vertex         Vertex
     * @param vertexStateMap Map<Vertex, Integer>
     * @return boolean
     */
    private static boolean isNotVisited(Vertex vertex, Map<Vertex, Integer> vertexStateMap) {
        Integer state = vertexStateMap.get(vertex);
        return (state == null) || NOT_VISITED.equals(state);
    }

    private static void dfsVisit(Vertex vertex, Map<Vertex, Integer> vertexStateMap, final List<String> list) {
        vertexStateMap.put(vertex, VISITING);
        for (Vertex v : vertex.getChildren()) {
            if (isNotVisited(v, vertexStateMap)) {
                dfsVisit(v, vertexStateMap, list);
            }
        }
        vertexStateMap.put(vertex, VISITED);
        list.add(vertex.getLabel());
    }

}
