import java.util.Set;
import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
/**
 * A class representing a graph, with an edge list and adjacency list.
 *
 * DO NOT EDIT THIS CLASS!!!
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class GraphAdjList<T> {

    private Set<Vertex<T>> vertices;
    private Set<Edge<T>> edges;
    private Map<Vertex<T>, List<Edge<T>>> adjList;

    /**
     * Builds the graph from a set of vertices and an edge list.
     *
     * @param vertices the vertex set
     * @param edges the edge set
     * @throws IllegalArgumentException if any of the arguments are null or if
     * the vertex set doesn't contain all of the vertices.
     */
    public GraphAdjList(Set<Vertex<T>> vertices, Set<Edge<T>> edges) {
        if (vertices == null || edges == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

        this.vertices = vertices;
        this.edges = edges;
        adjList = new HashMap<>();
        for (Vertex<T> v : vertices) {
            adjList.put(v, new ArrayList<>());
        }

        for (Edge<T> e : edges) {
            if (adjList.containsKey(e.getU())) {
                adjList.get(e.getU()).add(e);
            } else {
                throw new IllegalArgumentException("Vertex set must contain all"
                    + "vertices of the graph.");
            }
        }
    }

    /**
     * Gets the edge set of this graph.
     *
     * @return the edge set of this graph
     */
    public Set<Edge<T>> getEdges() {
        return edges;
    }

    /**
     * Gets the vertex set of this graph.
     *
     * @return the vertex set of this graph
     */
    public Set<Vertex<T>> getVertices() {
        return vertices;
    }

    /**
     * Gets the adjacency list of this graph.
     *
     * @return the adjacency list of this graph
     */
    public Map<Vertex<T>, List<Edge<T>>> getAdjList() {
        return adjList;
    }

}