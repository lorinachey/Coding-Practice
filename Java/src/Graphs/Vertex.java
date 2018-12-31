/**
 * Class representing a vertex.
 *
 * DO NOT EDIT THIS CLASS!!!
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class Vertex<T> {

    private T data;
    private int id;

    /**
     * Creates a Vertex object holding the given data.
     *
     * @param data the object that is stored in this Vertex
     * @param id represents the "number" vertex in the graph. Mainly used with
     * the adjacency matrix.
     * @throws IllegalArgumentException if data is null
     */
    public Vertex(T data, int id) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        this.data = data;
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (o != null && o instanceof Vertex) {
            return data.equals(((Vertex<?>) o).data)
                && id == ((Vertex<?>) o).id;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return data.hashCode() * id;
    }

    /**
     * Gets the data in this vertex.
     *
     * @return the data in this vertex
     */
    public T getData() {
        return data;
    }

    /**
     * Gets the id of this vertex.
     *
     * @return the id of this vertex
     */
    public int getId() {
        return id;
    }

    @Override
    public String toString() {
        return data.toString();
    }

}
