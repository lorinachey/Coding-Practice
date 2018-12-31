import org.junit.Before;
import org.junit.Test;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Basic student tests to check GraphAlgs. These tests are in
 * no way comprehensive nor do they guarantee any kind of grade.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class GraphAlgsStudentTests {

    private GraphAdjMatrix<Integer> directedMatrix;
    private GraphAdjList<Character> undirectedList;

    @Before
    public void init() {
        directedMatrix = createDirectedMatrix();
        undirectedList = createUndirectedList();
    }

    /**
     * Creates a directed graph using the adjacency matrix format.
     * The graph is depicted in the pdf.
     *
     * @return the completed graph
     */
    private GraphAdjMatrix<Integer> createDirectedMatrix() {
        Set<Vertex<Integer>> vertices = new HashSet<Vertex<Integer>>();
        for (int i = 1; i <= 7; i++) {
            vertices.add(new Vertex<Integer>(i, i - 1));
        }

        Integer[][] adjMatrix = new Integer[7][7];
        adjMatrix[0][1] = 1;
        adjMatrix[0][2] = 1;
        adjMatrix[0][3] = 1;
        adjMatrix[2][4] = 1;
        adjMatrix[3][5] = 1;
        adjMatrix[4][3] = 1;
        adjMatrix[4][6] = 1;
        adjMatrix[6][5] = 1;

        return new GraphAdjMatrix<Integer>(vertices, adjMatrix);
    }

    /**
     * Creates an undirected graph using the adjacency list format.
     * The graph is depicted in the pdf.
     *
     * @return the completed graph
     */
    private GraphAdjList<Character> createUndirectedList() {
        Set<Vertex<Character>> vertices = new HashSet<>();
        for (int i = 65; i <= 70; i++) {
            vertices.add(new Vertex<Character>((char) i, i - 65));
        }

        Set<Edge<Character>> edges = new HashSet<>();
        edges.add(new Edge<>(new Vertex<>('A', 0), new Vertex<>('B', 1), 7));
        edges.add(new Edge<>(new Vertex<>('B', 1), new Vertex<>('A', 0), 7));
        edges.add(new Edge<>(new Vertex<>('A', 0), new Vertex<>('C', 2), 5));
        edges.add(new Edge<>(new Vertex<>('C', 2), new Vertex<>('A', 0), 5));
        edges.add(new Edge<>(new Vertex<>('C', 2), new Vertex<>('D', 3), 2));
        edges.add(new Edge<>(new Vertex<>('D', 3), new Vertex<>('C', 2), 2));
        edges.add(new Edge<>(new Vertex<>('A', 0), new Vertex<>('D', 3), 4));
        edges.add(new Edge<>(new Vertex<>('D', 3), new Vertex<>('A', 0), 4));
        edges.add(new Edge<>(new Vertex<>('D', 3), new Vertex<>('E', 4), 1));
        edges.add(new Edge<>(new Vertex<>('E', 4), new Vertex<>('D', 3), 1));
        edges.add(new Edge<>(new Vertex<>('B', 1), new Vertex<>('E', 4), 3));
        edges.add(new Edge<>(new Vertex<>('E', 4), new Vertex<>('B', 1), 3));
        edges.add(new Edge<>(new Vertex<>('B', 1), new Vertex<>('F', 5), 8));
        edges.add(new Edge<>(new Vertex<>('F', 5), new Vertex<>('B', 1), 8));
        edges.add(new Edge<>(new Vertex<>('E', 4), new Vertex<>('F', 5), 6));
        edges.add(new Edge<>(new Vertex<>('F', 5), new Vertex<>('E', 4), 6));

        return new GraphAdjList<Character>(vertices, edges);
    }

    @Test
    public void testDepthFirstSearch() {
        List<Vertex<Integer>> dfsActual = new LinkedList<>();
        GraphAlgs.depthFirstSearch(new Vertex<Integer>(5, 4), directedMatrix,
            dfsActual);

        List<Vertex<Integer>> dfsExpected = new LinkedList<>();
        dfsExpected.add(new Vertex<Integer>(5, 4));
        dfsExpected.add(new Vertex<Integer>(4, 3));
        dfsExpected.add(new Vertex<Integer>(6, 5));
        dfsExpected.add(new Vertex<Integer>(7, 6));

        assertEquals(dfsExpected, dfsActual);
    }

    @Test
    public void testShortPathDijk() {
        Map<Vertex<Character>, Integer> dijkActual = GraphAlgs.shortPathDijk(
            new Vertex<Character>('D', 3), undirectedList);
        Map<Vertex<Character>, Integer> dijkExpected = new HashMap<>();
        dijkExpected.put(new Vertex<>('A', 0), 4);
        dijkExpected.put(new Vertex<>('B', 1), 4);
        dijkExpected.put(new Vertex<>('C', 2), 2);
        dijkExpected.put(new Vertex<>('D', 3), 0);
        dijkExpected.put(new Vertex<>('E', 4), 1);
        dijkExpected.put(new Vertex<>('F', 5), 7);

        assertEquals(dijkExpected, dijkActual);
    }

    @Test
    public void testMSTPrims() {
        Set<Edge<Character>> mstActual = GraphAlgs.mstPrim(undirectedList);
        Set<Edge<Character>> edges = new HashSet<>();
        edges.add(new Edge<>(new Vertex<>('C', 2), new Vertex<>('D', 3), 2));
        edges.add(new Edge<>(new Vertex<>('D', 3), new Vertex<>('C', 2), 2));
        edges.add(new Edge<>(new Vertex<>('A', 0), new Vertex<>('D', 3), 4));
        edges.add(new Edge<>(new Vertex<>('D', 3), new Vertex<>('A', 0), 4));
        edges.add(new Edge<>(new Vertex<>('D', 3), new Vertex<>('E', 4), 1));
        edges.add(new Edge<>(new Vertex<>('E', 4), new Vertex<>('D', 3), 1));
        edges.add(new Edge<>(new Vertex<>('B', 1), new Vertex<>('E', 4), 3));
        edges.add(new Edge<>(new Vertex<>('E', 4), new Vertex<>('B', 1), 3));
        edges.add(new Edge<>(new Vertex<>('E', 4), new Vertex<>('F', 5), 6));
        edges.add(new Edge<>(new Vertex<>('F', 5), new Vertex<>('E', 4), 6));

        assertEqualsSet(edges, mstActual);
    }

    @Test
    public void testMSTKruskals() {
        Set<Edge<Character>> mstActual = GraphAlgs.mstKruskal(undirectedList);
        Set<Edge<Character>> edges = new HashSet<>();
        edges.add(new Edge<>(new Vertex<>('C', 2), new Vertex<>('D', 3), 2));
        edges.add(new Edge<>(new Vertex<>('D', 3), new Vertex<>('C', 2), 2));
        edges.add(new Edge<>(new Vertex<>('A', 0), new Vertex<>('D', 3), 4));
        edges.add(new Edge<>(new Vertex<>('D', 3), new Vertex<>('A', 0), 4));
        edges.add(new Edge<>(new Vertex<>('D', 3), new Vertex<>('E', 4), 1));
        edges.add(new Edge<>(new Vertex<>('E', 4), new Vertex<>('D', 3), 1));
        edges.add(new Edge<>(new Vertex<>('B', 1), new Vertex<>('E', 4), 3));
        edges.add(new Edge<>(new Vertex<>('E', 4), new Vertex<>('B', 1), 3));
        edges.add(new Edge<>(new Vertex<>('E', 4), new Vertex<>('F', 5), 6));
        edges.add(new Edge<>(new Vertex<>('F', 5), new Vertex<>('E', 4), 6));

        assertEqualsSet(edges, mstActual);
    }

    /**
     * Checks to see if two sets are the same.
     *
     * @param <T> the generic typing
     * @param expected Expected set result.
     * @param actual Actual set result.
     */
    public static <T> void assertEqualsSet(Set<Edge<T>> expected,
        Set<Edge<T>> actual) {
        assertEquals(expected.size(), actual.size());
        for (Edge<T> e : expected) {
            assertTrue(actual.contains(e));
        }
    }
}
