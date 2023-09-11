#include "MinHeap.hpp"
#include "Node.hpp"

#include <cmath>
#include <algorithm>
#include <limits>
#include <string>
#include <vector>
#include <unordered_map>

MinHeap::MinHeap(vector<Node*> array) {
    for (int i = 0; i < array.size(); i++) {
        auto node = array[i];
        nodePositionsInHeap[node->id] = i;
    }
    heap = buildHeap(array);
}

vector<Node*> MinHeap::buildHeap(vector<Node*>& array) {
    int firstParentIndex = (array.size() - 1) / 2;
    for (int currentIndex = firstParentIndex; currentIndex >= 0; currentIndex--) {
        siftDown(currentIndex, array.size() - 1, array);
    }
    return array;
}

bool MinHeap::isEmpty() { return heap.size() == 0; }

// O(log(n)) time | O(1) space
void MinHeap::siftDown(int currentIndex, int endIndex, vector<Node*>& array) {
    int childOneIndex = currentIndex * 2 + 1;
    while (childOneIndex <= endIndex) {
        int childTwoIndex = (currentIndex * 2 + 2 <= endIndex) ? (currentIndex * 2 + 2) : -1;
        int indexToSwap;

        if (childTwoIndex != -1
            && array[childTwoIndex]->estimatedDistanceToEnd < heap[childOneIndex]->estimatedDistanceToEnd) {
                indexToSwap = childTwoIndex;
        } else {
            indexToSwap = childOneIndex;
        }

        if (array[indexToSwap]->estimatedDistanceToEnd < array[currentIndex]->estimatedDistanceToEnd) {
            swap(currentIndex, indexToSwap);
            currentIndex = indexToSwap;
            childOneIndex = currentIndex * 2 + 1;
        } else {
            return;
        }
    }
}

// O(log(n)) time | O(1) space
void MinHeap::siftUp(int currentIndex) {
    int parentIndex = (currentIndex - 1) / 2;
    while (currentIndex > 0
            && heap[currentIndex]->estimatedDistanceToEnd < heap[parentIndex]->estimatedDistanceToEnd) {
        swap(currentIndex, parentIndex);
        currentIndex = parentIndex;
        parentIndex = (currentIndex - 1) / 2;
    }
}

Node* MinHeap::remove() {
    if (isEmpty()) {
        return nullptr;
    }

    swap(0, heap.size() - 1);
    auto node = heap.back();
    heap.pop_back();
    nodePositionsInHeap.erase(node->id);
    siftDown(0, heap.size() - 1, heap);
    return node;
}

void MinHeap::insert(Node* node) {
    heap.push_back(node);
    nodePositionsInHeap[node->id] = heap.size() - 1;
    siftUp(heap.size() - 1);
}

void MinHeap::swap(int i, int j) {
    nodePositionsInHeap[heap[i]->id] = j;
    nodePositionsInHeap[heap[j]->id] = i;
    auto temp = heap[i];
    heap[i] = heap[j];
    heap[j] = temp;
}

bool MinHeap::containsNode(Node* node) {
    return nodePositionsInHeap.find(node->id) != nodePositionsInHeap.end();
}

void MinHeap::update(Node* node) { siftUp(nodePositionsInHeap[node->id]); }
