#ifndef MINHEAP_HPP
#define MINHEAP_HPP

#include "Node.hpp"

#include <cmath>
#include <algorithm>
#include <limits>
#include <string>
#include <vector>
#include <unordered_map>

class MinHeap {
 public:
  vector<Node*> heap;
  std::unordered_map<std::string, int> nodePositionsInHeap;

  MinHeap(vector<Node*> array);

  vector<Node*> buildHeap(vector<Node*>& array);
  bool isEmpty();
  void siftDown(int currentIndex, int endIndex, vector<Node*>& array);
  void siftUp(int currentIndex);
  Node* remove();
  void insert(Node* node);
  void swap(int i, int j);
  bool containsNode(Node* node);
  void update(Node* node);
};

#endif  // MINHEAP_HPP