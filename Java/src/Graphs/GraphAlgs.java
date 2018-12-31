import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Set;

/**
 * Your implementation of 4 different graph algorithms.
 *
 * @author Lorin Achey
 * @version 1.0
 */
public class GraphAlgs {

    /**
     * Performs a depth first search (dfs) on the input graph, starting at
     * {@code start} which represents the starting vertex. You will be
     * modifying the empty list passed in to contain the vertices in
     * visited order. The start vertex should be at the beginning of the list
     * and the last vertex visited should be at the end.  (You may assume the
     * list is empty in the beginning).
     *
     * This method should utilize the adjacency matrix represented graph.
     *
     * When deciding which neighbors to visit next from a vertex, visit starting
     * with the vertex at index 0 to the vertex at index |V| - 1. Failure to do
     * so may cause you to lose points.
     *
     * *NOTE* You MUST implement this method recursively, or else you will lose
     * most if not all points for this method.
     *
     * You may import/use {@code java.util.Set}, {@code java.util.Map},
     * {@code java.util.List}, and any classes that implement the
     * aforementioned interfaces, as long as it is efficient.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if {@code start} doesn't exist in the graph
     * @param <T> the generic typing of the data
     * @param start the vertex to begin the dfs on
     * @param graph the graph in an adjacency matrix format to search
     * @param dfsList the list of visited vertices in order. This list will be
     * empty initially. You will be adding to this list as you perform dfs.
     * @return true if the graph is connected (you were able to reach every
     * vertex and edge from {@code start}), false otherwise
     */
    public static <T> boolean depthFirstSearch(Vertex<T> start,
                                               GraphAdjMatrix<T> graph,
                                               List<Vertex<T>> dfsList) {

        if (start == null || graph == null || dfsList == null) {
            throw new IllegalArgumentException("Cannot have null inputs.");
        }
        if (start.getId() < 0 || start.getId()
                > graph.getVertices().size() - 1) {
            throw new IllegalArgumentException("Starting "
                    + "vertex is not in the graph.");
        }

        Integer[][] adjMatrix = graph.getAdjMatrix();
        Set<Vertex<T>> visitedSet = new HashSet<>();

        depthFirstSearch(start, graph, dfsList, adjMatrix, visitedSet);
        if (visitedSet.size() != graph.getVertices().size()) {
            return false;
        }
        return true;
    }

    /**
     * Private helper method for depthFirstSearch.
     * @param current the starting or current node
     * @param graph the graph in the adjacency matrix format to search
     * @param dfsList the list of vertices to add to
     * @param adjMatrix the adjacency matrix
     * @param visitedSet the set of visited vertices
     * @param <T> the generic typing of the data
     */
    private static <T> void depthFirstSearch(Vertex<T> current,
                                                GraphAdjMatrix<T> graph,
                                                List<Vertex<T>> dfsList,
                                                Integer[][] adjMatrix,
                                                Set<Vertex<T>> visitedSet) {
        if (!visitedSet.contains(current)) {
            visitedSet.add(current);
            dfsList.add(current);
            Integer[] arrayAtCurrentID = adjMatrix[current.getId()];
            for (int j = 0; j < arrayAtCurrentID.length; j++) {
                if (arrayAtCurrentID[j] != null) {
                    List<Vertex<T>> vertices = graph.getVertices();
                    depthFirstSearch(vertices.get(j),
                            graph, dfsList, adjMatrix, visitedSet);
                }
            }
        }
    }

    /**
     * Find the single source shortest distance between the start vertex and
     * all vertices given a weighted graph using Dijkstra's shortest path
     * algorithm.
     *
     * Return a map of the shortest distances such that the key of each entry is
     * a node in the graph and the value for the key is the shortest distance
     * to that node from start, or Integer.MAX_VALUE (representing infinity)
     * if no path exists. You may assume that going from a vertex to itself
     * has a shortest distance of 0.
     *
     * This method should utilize the adjacency list represented graph.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Map}, and {@code java.util.Set} and any class that
     * implements the aforementioned interfaces.
     *
     * You should implement CLASSIC Dijkstra's, which is the version of the
     * algorithm that terminates once you've "visited" all of the nodes.
     *
     * @throws IllegalArgumentException if any input
     *  is null, or if start doesn't exist in the graph.
     * @throws IllegalStateException if any of the edges are negative
     * @param <T> the generic typing of the data
     * @param start index representing which vertex to start at (source)
     * @param graph the Graph we are searching using an adjacency List
     * @return a map of the shortest distances from start to every other node
     *         in the graph
     */
    public static <T> Map<Vertex<T>, Integer> shortPathDijk(Vertex<T> start,
                                                      GraphAdjList<T> graph) {
        if (start == null || graph == null) {
            throw new IllegalArgumentException("Inputs cannot be null.");
        }
        Set<Vertex<T>> vertices = graph.getVertices();
        if (!vertices.contains(start)) {
            throw new IllegalArgumentException("Starting "
                     + "vertex is not in the graph.");
        }
        Set<Edge<T>> edges = graph.getEdges();
        for (Edge<T> edge : edges) {
            if (edge.getWeight() < 0) {
                throw new IllegalStateException("Edge weights cannot be zero.");
            }
        }

        Queue<Edge<T>> queue = new PriorityQueue<>();
        Map<Vertex<T>, Integer> shortestDistanceMap = new HashMap<>();
        Map<Vertex<T>, List<Edge<T>>> adjList = graph.getAdjList();

        for (Vertex<T> vertex : vertices) {
            shortestDistanceMap.put(vertex, Integer.MAX_VALUE);
        }
        Edge<T> startingEdge = new Edge<>(start, start, 0);
        queue.add(startingEdge);
        while (!queue.isEmpty()) {
            Edge<T> currentEdge = queue.poll();
            if (shortestDistanceMap.get(currentEdge.getV())
                    == Integer.MAX_VALUE) {
                shortestDistanceMap.put(currentEdge.getV(),
                        currentEdge.getWeight());
                List<Edge<T>> listOfNeighboringEdges
                        = adjList.get(currentEdge.getV());
                for (Edge<T> edge : listOfNeighboringEdges) {
                    Edge<T> newEdgeToEnqueue = new Edge<>(currentEdge.getU(),
                            edge.getV(), currentEdge.getWeight() + edge.getWeight());
                    queue.add(newEdgeToEnqueue);
                }
            }
        }
        return shortestDistanceMap;
    }

    /**
     * Run Prim's algorithm on the given graph and return the MST/MSF
     * in the form of a set of Edges.  If the graph is disconnected, and
     * therefore there is no valid MST, return a minimal spanning forest (MSF).
     *
     * This method should utilize the adjacency list represented graph.
     *
     * A minimal spanning forest (MSF) is just a generalized version of the MST
     * for disconnected graphs. After the MST algorithm finishes, just check to
     * see if there are still some vertices that are not connected to the
     * MST/MSF. If all vertices have been visited, you are done. If not, run
     * the algorithm again on an unvisited vertex.
     *
     * You may assume that all of the edge weights are unique (THIS MEANS THAT
     * THE MST/MSF IS UNIQUE FOR THE GRAPH, REGARDLESS OF STARTING VERTEX!!)
     * Although, if your algorithm works correctly, it should work even if the
     * MST/MSF is not unique, this is just for testing purposes.
     *
     * You should not allow for any self-loops in the MST/MSF. Additionally,
     * you may assume that the graph is undirected.
     *
     * You may import/use {@code java.util.PriorityQueue} and
     * {@code java.util.Set} and any class that
     * implements the aforementioned interfaces.
     *
     * @throws IllegalArgumentException if any input is null
     * @param <T> the generic typing of the data
     * @param graph the Graph we are searching using an adjacency list
     * @return the MST/MSF of the graph
     */
    public static <T> Set<Edge<T>> mstPrim(GraphAdjList<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("The graph cannot be null.");
        }
        Set<Edge<T>> mst = new HashSet<>();
        //Set<Vertex<T>> unVisitedSet = graph.getVertices();

        // ^ changed to the following code:
        Set<Vertex<T>> unVisitedSet = new HashSet<>();
        for (Vertex<T> vertex : graph.getVertices()) {
            unVisitedSet.add(vertex);
        }

        Vertex<T> currentVertex;

        Iterator<Vertex<T>> iteratoreForStartingVertex
                = unVisitedSet.iterator();
        if (iteratoreForStartingVertex.hasNext()) {
            Vertex<T> start = iteratoreForStartingVertex.next();
            currentVertex = start;
        } else {
            return mst;
        }

        Queue<Edge<T>> queue = new PriorityQueue<>();
        Map<Vertex<T>, List<Edge<T>>> adjList = graph.getAdjList();
        unVisitedSet.remove(currentVertex);
        List<Edge<T>> listOfEdges = adjList.get(currentVertex);
        for (Edge<T> edge : listOfEdges) {
            Edge<T> newEdge = new Edge<>(edge.getU(),
                    edge.getV(), edge.getWeight());
            queue.add(newEdge);
        }

        while (!queue.isEmpty()
                || mst.size() < graph.getVertices().size() - 1) {
            Edge<T> minimalPath = queue.poll();
            if (minimalPath != null) {
                currentVertex = minimalPath.getV();
                if (unVisitedSet.contains(currentVertex)) {
                    Edge<T> minimalPathReversed = new Edge<>(minimalPath.getV(),
                            minimalPath.getU(), minimalPath.getWeight());
                    mst.add(minimalPath);
                    mst.add(minimalPathReversed);

                    unVisitedSet.remove(currentVertex);
                    List<Edge<T>> list = adjList.get(currentVertex);
                    for (Edge<T> edge : list) {
                        Edge<T> newEdge = new Edge<>(edge.getU(),
                                edge.getV(), edge.getWeight());
                        queue.add(newEdge);
                    }
                }
            }
            if (!unVisitedSet.isEmpty() && queue.isEmpty()) {
                Iterator<Vertex<T>> iterator = unVisitedSet.iterator();
                currentVertex = iterator.next();
                unVisitedSet.remove(currentVertex);
                unVisitedSet.remove(currentVertex);
                List<Edge<T>> listEdges = adjList.get(currentVertex);
                for (Edge<T> edge : listEdges) {
                    Edge<T> newEdge = new Edge<>(edge.getU(),
                            edge.getV(), edge.getWeight());
                    queue.add(newEdge);
                }
            }
        }
        return mst;
    }

    /**
     * Run Kruskal's algorithm on the given graph and return the MST/MSF
     * in the form of a set of Edges.  If the graph is disconnected, and
     * therefore there is no valid MST, return a minimal spanning forest (MSF).
     *
     * This method should utilize the adjacency list represented graph.
     *
     * A minimal spanning forest (MSF) is just a generalized version of the MST
     * for disconnected graphs. Unlike Prim's algorithm, Kruskal's algorithm
     * will naturally return a MSF if the graph is disconnected.
     *
     * You may assume that all of the edge weights are unique (THIS MEANS THAT
     * THE MST/MSF IS UNIQUE FOR THE GRAPH.) Although, if your algorithm works
     * correctly, it should work even if the MST/MSF is not unique, this is
     * just for testing purposes.
     *
     * You should not allow for any self-loops in the MST/MSF. Additionally,
     * you may assume that the graph is undirected.
     *
     * Kruskal's will also require you to use a Disjoint Set which has been
     * provided for you.  A Disjoint Set will keep track of which vertices are
     * connected to each other by the edges you've chosen for your MST/MSF.
     * Without a Disjoint Set, it is possible for Kruskal's to omit edges that
     * should be in the final MST/MSF.
     *
     * You may import/use {@code java.util.PriorityQueue},
     * {@code java.util.Set}, and any class that implements the aforementioned
     * interface.
     *
     * @throws IllegalArgumentException if any input is null
     * @param <T> the generic typing of the data
     * @param graph the Graph we are searching using an adjacency list
     * @return the MST/MSF of the graph
     */
    public static <T> Set<Edge<T>> mstKruskal(GraphAdjList<T> graph) {
        if (graph == null) {
            throw new IllegalArgumentException("The graph cannot be null.");
        }
        Set<Edge<T>> mst = new HashSet<>();

        Set<Vertex<T>> vertices = graph.getVertices();
        Collection<T> collectionOfDataForDisjointSet = new HashSet<>();
        for (Vertex<T> vertex : vertices) {
            collectionOfDataForDisjointSet.add(vertex.getData());
        }
        DisjointSet<T> disjointSet
                = new DisjointSet<>(collectionOfDataForDisjointSet);

        Queue<Edge<T>> queue = new PriorityQueue<>();
        for (Edge<T> edge : graph.getEdges()) {
            queue.add(edge);
        }
        if (queue.isEmpty() && vertices.size() > 0) {
            return mst;
        }
        while (!queue.isEmpty()
                || mst.size() < graph.getVertices().size() - 1) {
            Edge<T> currentEdge = queue.poll();
            if (disjointSet.find(currentEdge.getU().getData())
                    != disjointSet.find(currentEdge.getV().getData())) {
                disjointSet.union(currentEdge.getV().getData(),
                        currentEdge.getU().getData());
                mst.add(currentEdge);
                Edge<T> reversedCurrentEdge = new Edge<>(currentEdge.getV(),
                        currentEdge.getU(), currentEdge.getWeight());
                mst.add(reversedCurrentEdge);
            }
        }
        return mst;
    }
}