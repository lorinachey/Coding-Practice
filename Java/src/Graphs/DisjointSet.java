import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class to store a DisjointSet data structure. This data structure has two
 * main functions: find and union. find will look for the root (parent) of a
 * DisjointSet. Calling find on two different T data will check if those two are
 * part of the same set. union will join two sets together if not already.
 *
 * Use this for Kruskal's Algorithm.
 *
 * DO NOT EDIT THIS CLASS!!!
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class DisjointSet<T> {
    private Map<T, DisjointSetNode<T>> disjointSet;

    /**
     * Initializes the disjoint sets.  Puts each T data from the collection into
     * it's own disjoint set.
     *
     * @param collection The collection of data to convert
     */
    public DisjointSet(Collection<T> collection) {
        disjointSet = new HashMap<>();
        for (T t : collection) {
            disjointSet.put(t, new DisjointSetNode<>(t));
        }
    }

    /**
     * Finds the parent/root data of the data passed in.
     *
     * @param data the data to find the parent of.
     * @return parent/root of the data.
     */
    public T find(T data) {
        return find(disjointSet.get(data)).getData();
    }

    /**
     * Recursively finds the parent of the DisjointSetNode.  Performs path
     * compression such that all DisjointSetNodes along the path to the parent
     * will all directly point to the parent.
     *
     * @param curr The current DisjointSetNode to find the parent of.
     * @return The parent/root of the current node.
     */
    private DisjointSetNode<T> find(DisjointSetNode<T> curr) {
        DisjointSetNode<T> parent = curr.getParent();
        if (parent == curr) {
            return curr;
        } else {
            parent = find(curr.getParent());
            curr.setParent(parent);
            return parent;
        }
    }

    /**
     * Attempts to join the two data into the same set by pointing the parent
     * of one set to the parent of another set.
     *
     * @param first The first data to find the parent of.
     * @param second The second data to find the parent of.
     */
    public void union(T first, T second) {
        union(disjointSet.get(first), disjointSet.get(second));
    }

    /**
     * This is where the work is done for union().  This method finds the
     * parents of both passed in nodes and checks if they have the same parent.
     * If not the same parent, the the parent with the least rank will point
     * the the node with higher rank using merge by rank.
     *
     * @param first The first DisjointSetNode to find the parent of.
     * @param second The second DisjointSetNode to find the parent of.
     */
    private void union(DisjointSetNode<T> first, DisjointSetNode<T> second) {
        // Finds parents
        DisjointSetNode<T> firstParent = find(first);
        DisjointSetNode<T> secondParent = find(second);

        // If parents are different (different sets)
        if (firstParent != secondParent) {
            if (firstParent.getRank() < secondParent.getRank()) {
                firstParent.setParent(secondParent);
            } else {
                secondParent.setParent(firstParent);
                if (firstParent.getRank() == secondParent.getRank()) {
                    firstParent.setRank(firstParent.getRank() + 1);
                }
            }
        }
    }
}