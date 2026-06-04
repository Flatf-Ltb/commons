package io.flatf.common.graph.dag;

import io.flatf.common.collections.MutableLists;
import org.eclipse.collections.api.list.ImmutableList;
import org.eclipse.collections.api.list.MutableList;

import java.io.Serial;
import java.io.Serializable;

import static org.eclipse.collections.impl.collector.Collectors2.toImmutableList;

/**
 * @author <a href="michal.maczka@dimatics.com">Michal Maczka</a>
 */
public class Vertex implements Cloneable, Serializable {

    @Serial
    private static final long serialVersionUID = 6226650981773662643L;

    // ------------------------------------------------------------
    // Fields
    // ------------------------------------------------------------

    private final String label;

    private final MutableList<Vertex> children = MutableLists.newFastList();

    private final MutableList<Vertex> parents = MutableLists.newFastList();

    public String getLabel() {
        return label;
    }

    /**
     * @return ImmutableList<Vertex>
     */
    public ImmutableList<Vertex> getChildren() {
        return children.toImmutable();
    }

    /**
     * @return ImmutableList<Vertex>
     */
    public ImmutableList<Vertex> getParents() {
        return parents.toImmutable();
    }

    // ------------------------------------------------------------
    // Constructors
    // ------------------------------------------------------------

    /**
     *
     */
    public Vertex(String label) {
        this.label = label;
    }

    // ------------------------------------------------------------
    // Accessors
    // ------------------------------------------------------------

    /**
     * @param vertex Vertex
     */
    public void addEdgeTo(Vertex vertex) {
        children.add(vertex);
    }

    /**
     * @param vertex Vertex
     */
    public void removeEdgeTo(Vertex vertex) {
        children.remove(vertex);
    }

    /**
     * @param vertex Vertex
     */
    public void addEdgeFrom(Vertex vertex) {
        parents.add(vertex);
    }

    /**
     * @param vertex Vertex
     */
    public void removeEdgeFrom(Vertex vertex) {
        parents.remove(vertex);
    }

    /**
     * Get the labels used by the most direct children.
     *
     * @return the labels used by the most direct children.
     */
    public ImmutableList<String> getChildLabels() {
        return children.stream().map(Vertex::getLabel).collect(toImmutableList());
    }

    /**
     * Get the labels used by the most direct ancestors (parents).
     *
     * @return the labels used parents
     */
    public ImmutableList<String> getParentLabels() {
        return parents.stream().map(Vertex::getLabel).collect(toImmutableList());
    }

    /**
     * Indicates if given vertex has no child
     *
     * @return <code>true</code> if this vertex has no child, <code>false</code>
     * otherwise
     */
    public boolean isLeaf() {
        return children.isEmpty();
    }

    /**
     * Indicates if given vertex has no parent
     *
     * @return <code>true</code> if this vertex has no parent, <code>false</code>
     * otherwise
     */
    public boolean isRoot() {
        return parents.isEmpty();
    }

    /**
     * Indicates if there is at least one edee leading to or from given vertex
     *
     * @return <code>true</code> if this vertex is connected with other
     * vertex,<code>false</code> otherwise
     */
    public boolean isConnected() {
        return isRoot() || isLeaf();
    }

    public Object clone() throws CloneNotSupportedException {
        // this is what's failing.
        return super.clone();
    }

    public String toString() {
        return "Vertex{label=" + label + "}";
    }

}
