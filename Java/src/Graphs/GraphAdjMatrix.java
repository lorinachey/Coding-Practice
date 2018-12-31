import java.util.Set;
import java.util.HashSet;
import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
/**
 * Class that represents a graph using an adjacency matrix.
 *
 * DO NOT EDIT THIS CLASS!!
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class GraphAdjMatrix<T> {

    private List<Vertex<T>> vertices;
    private Integer[][] adjMatrix;

    /**
     * Builds a graph with the passed in information. The rows of the matrix
     * correspond to the starting vertex, and the columns correspond to the
     * ending vertex.
     *
     * If there is no edge from vertex u to v, then that spot should contain
     * null. Otherwise, it'll contain the edge's weight. This is possible since
     * the matrix is of type Integer, not int.
     *
     * To access the row/col of a certain vertex, use the vertex's id field.
     *
     * Example: If we want to check if there is an edge from vertex u to vertex
     * v, then we'll check adjMatrix[u.getId()][v.getId()].
     *
     * The vertex list is also sorted in order of ids, so if you want the vertex
     * with id 0, you just access index 0 of the vertex list.
     *
     * NOTE: There is no functionality for this adjacency matrix to have
     * parallel edges. As such, we will not test parallel edges for the matrix.
     *
     * @param vertices the set of vertices in the graph
     * @param adjMatrix the adjacency matrix of the graph
     * @throws IllegalArgumentException if any of the parameters are nullor if
     * the id fields of the vertices are not within the specified range
     */
    public GraphAdjMatrix(Set<Vertex<T>> vertices, Integer[][] adjMatrix) {
        if (vertices == null || adjMatrix == null) {
            throw new IllegalArgumentException("Arguments cannot be null.");
        }

        Set<Integer> seen = new HashSet<>();
        for (Vertex<T> v : vertices) {
            if (v.getId() >= vertices.size() || v.getId() < 0) {
                throw new IllegalArgumentException("Vertex ids must be within"
                    + " the range [0, |V|)");
            } else if (!seen.add(v.getId())) {
                throw new IllegalArgumentException("Cannot have duplicate id.");
            }
        }


        this.vertices = new ArrayList<>(vertices);
        Collections.sort(this.vertices, (a, b) -> a.getId() - b.getId());
        this.adjMatrix = adjMatrix;
    }

    /**
     * Gets the vertex set of the graph.
     *
     * @return the vertex set of the graph
     */
    public List<Vertex<T>> getVertices() {
        return vertices;
    }

    /**
     * Gets the adjacency matrix of the graph.
     *
     * @return the adjacency matrix of the graph
     */
    public Integer[][] getAdjMatrix() {
        return adjMatrix;
    }
}