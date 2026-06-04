package io.flatf.common.graph.dag;

import org.eclipse.collections.api.list.ImmutableList;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * DAG : Directed Acyclic Graph
 *
 * @author <a href="michal.maczka@dimatics.com">Michal Maczka</a>
 */
public class DAG implements Cloneable, Serializable {

    @Serial
    private static final long serialVersionUID = -8639632777005175375L;

    // ------------------------------------------------------------
    // Fields
    // ------------------------------------------------------------
    /**
     * Nodes will be kept in two data structures at the same time for faster
     * processing
     * Maps vertex's label to vertex
     */
    private final Map<String, Vertex> vertexMap = new HashMap<>();

    /**
     * Contain list of all vertices
     */
    private final List<Vertex> vertexList = new ArrayList<>();

    // ------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------

    /**
     *
     */
    public DAG() {
    }

    // ------------------------------------------------------------
    // Accessors
    // ------------------------------------------------------------

    /**
     * @return List<Vertex>
     */
    public List<Vertex> getVertices() {
        return vertexList;
    }

    public Set<String> getLabels() {
        return vertexMap.keySet();
    }

    // ------------------------------------------------------------
    // Implementation
    // ------------------------------------------------------------

    /**
     * Adds vertex to DAG. If vertex of given label already exist in DAG no vertex
     * is added
     *
     * @param label The label of the Vertex
     * @return New vertex if vertex of given label was not present in the DAG or
     * existing vertex if vertex of given label was already added to DAG
     */
    public Vertex addVertex(final String label) {
        Vertex retValue;
        // check if vertex is already in DAG
        if (vertexMap.containsKey(label)) {
            retValue = vertexMap.get(label);
        } else {
            retValue = new Vertex(label);
            vertexMap.put(label, retValue);
            vertexList.add(retValue);
        }
        return retValue;
    }

    public void addEdge(String from, String to) throws CycleDetectedException {
        addEdge(addVertex(from), addVertex(to));
    }

    public void addEdge(final Vertex from, final Vertex to) throws CycleDetectedException {

        from.addEdgeTo(to);
        to.addEdgeFrom(from);

        List<String> cycle = CycleDetector.introducesCycle(to);

        if (cycle != null) {
            // remove edge which introduced cycle
            removeEdge(from, to);
            throw new CycleDetectedException(
                    "Edge between " + from + " and " + to + " introduces to cycle in the graph", cycle);
        }
    }

    public void removeEdge(final String from, final String to) {
        removeEdge(addVertex(from), addVertex(to));
    }

    public void removeEdge(Vertex from, Vertex to) {
        from.removeEdgeTo(to);
        to.removeEdgeFrom(from);
    }

    public Vertex getVertex(String label) {
        return vertexMap.get(label);
    }

    public boolean hasEdge(String label1, String label2) {
        return getVertex(label1).getChildren().contains(getVertex(label2));
    }

    /**
     * @param label String
     * @return ImmutableList<String>
     */
    public ImmutableList<String> getChildLabels(String label) {
        return getVertex(label).getChildLabels();
    }

    /**
     * @param label String
     * @return ImmutableList<String>
     */
    public ImmutableList<String> getParentLabels(String label) {
        return getVertex(label).getParentLabels();
    }

    /**
     * @see java.lang.Object#clone()
     */
    public Object clone() throws CloneNotSupportedException {
        // this is what's failing.
        return super.clone();
    }

    /**
     * Indicates if there is at least one edge leading to or from vertex of given
     * label
     *
     * @return <code>true</code> if this vertex is connected with other
     * vertex,<code>false</code> otherwise
     */
    public boolean isConnected(final String label) {
        return getVertex(label).isConnected();
    }

    /**
     * Return the list of labels of successor in order decided by topological sort
     *
     * @param label The label of the vertex whose predecessors are searched
     * @return The list of labels. Returned list contains also the label passed as
     * parameter to this method. This label should always be the last item
     * in the list.
     */
    public List<String> getSuccessorLabels(final String label) {
        Vertex vertex = getVertex(label);
        List<String> retValue;
        // optimization.
        if (vertex.isLeaf()) {
            retValue = new ArrayList<>(1);
            retValue.add(label);
        } else {
            retValue = TopologicalSorter.sort(vertex);
        }
        return retValue;
    }

}
