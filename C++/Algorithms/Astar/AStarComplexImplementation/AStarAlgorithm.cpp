#include "MinHeap.hpp"
#include "Node.hpp"

#include <vector>

int calculateManhattanDistance(Node* currentNode, Node* endNode);
vector<vector<Node*>> initializeNodes(vector<vector<int>> graph);
vector<Node*> getNeighborNodes(Node* node, vector<vector<Node*>>& nodes);
vector<vector<int>> reconstructPath(Node* endNode);

vector<vector<int>> aStarAlgorithm(int startRow, int startCol, int endRow, int endCol, vector<vector<int>> graph) {
    auto nodes = initializeNodes(graph);
    auto startNode = nodes[startRow][startCol];
    auto endNode = nodes[endRow][endCol];

    startNode->distanceFromStart = 0;
    startNode->estimatedDistanceToEnd = calculateManhattanDistance(startNode, endNode);

    MinHeap nodesToVisit(vector<Node*>{startNode});

    while (!nodesToVisit.isEmpty()) {
        auto currentMinDistanceNode = nodesToVisit.remove();

        if (currentMinDistanceNode == endNode) {
            break;
        }

        auto neighbors = getNeighborNodes(currentMinDistanceNode, nodes);
        for (auto neighbor : neighbors) {
            if (neighbor->value == 1) {
                // 1 represents an obstacle in our provided grid world so we continue on
                continue;
            }

            int tentativeDistanceToNeighbor = currentMinDistanceNode->distanceFromStart + 1;
            if (tentativeDistanceToNeighbor >= neighbor->distanceFromStart) {
                // We only want to continue if the new distance is less than we currently have
                continue;
            }

            // We found a shorter path so we need to update the distances, cameFrom node, and the minheap
            neighbor->cameFrom = currentMinDistanceNode;
            neighbor->distanceFromStart = tentativeDistanceToNeighbor;
            neighbor->estimatedDistanceToEnd =
                    tentativeDistanceToNeighbor + calculateManhattanDistance(neighbor, endNode);

            if (!nodesToVisit.containsNode(neighbor)) {
                nodesToVisit.insert(neighbor);
            } else {
                nodesToVisit.update(neighbor);
            }
        }
    }
    return reconstructPath(endNode);
}

int calculateManhattanDistance(Node* currentNode, Node* endNode) {
    int currentRow = currentNode->row;
    int currentCol = currentNode->col;
    int endRow = endNode->row;
    int endCol = endNode->col;
    return abs(currentRow - endRow) + abs(currentCol - endCol);
}

vector<vector<Node*>> initializeNodes(vector<vector<int>> graph) {
    vector<vector<Node*>> nodes;
    for (int i = 0; i < graph.size(); i++) {
        nodes.push_back(vector<Node*>{});
        for (int j = 0; j < graph[i].size(); j++) {
            nodes[i].push_back(new Node(i, j, graph[i][j]));
        }
    }
    return nodes;
}

vector<Node*> getNeighborNodes(Node* node, vector<vector<Node*>>& nodes) {
    vector<Node*> neighbors;

    int numRows = nodes.size();
    int numCols = nodes[0].size();

    int row = node->row;
    int col = node->col;

    // Get the neighbors left, right, up, and down. We don't count the diagonal nodes as neighbors.
    if (col > 0) {
        neighbors.push_back(nodes[row][col - 1]);
    }
    if (col < numCols - 1) {
        neighbors.push_back(nodes[row][col + 1]);
    }
    if (row > 0) {
        neighbors.push_back(nodes[row - 1][col]);
    }
    if (row < numRows - 1) {
        neighbors.push_back(nodes[row + 1][col]);
    }

    return neighbors;
}

vector<vector<int>> reconstructPath(Node* endNode) {
    vector<vector<int>> path {};
    if (endNode->cameFrom == nullptr) {
        return path;
    }

    Node* currentNode = endNode;
    while (currentNode != nullptr) {
        path.push_back(vector<int>{currentNode->row, currentNode->col});
        currentNode = currentNode->cameFrom;
    }
    reverse(path.begin(), path.end());
    return path;
}

int main() {
    printf("TODO - add test cases here.");
}
