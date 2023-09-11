#ifndef NODE_HPP
#define NODE_HPP

#include <algorithm>
#include <cmath>
#include <limits>
#include <string>
#include <vector>

using namespace std;

class Node {
 public:
    string id;
    int row;
    int col;
    int value;
    int distanceFromStart;
    int estimatedDistanceToEnd;
    Node* cameFrom;

    Node(int row, int col, int value) {
        this->id = to_string(row) + '-' + to_string(col);
        this->row = row;
        this->col = col;
        this->value = value;
        this->distanceFromStart = numeric_limits<int>::max();
        this->estimatedDistanceToEnd = numeric_limits<int>::max();
        this->cameFrom = nullptr;
    }
};

#endif // NODE_HPP
