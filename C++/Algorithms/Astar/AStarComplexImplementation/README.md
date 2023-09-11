### AStar Algorithm Implementation

This directory contains a very specific implementation of the A* algorithm using a 2D grid world of 1's and 0's. This problem was originally pulled from [AlgoExpert's Question Bank](https://www.algoexpert.io/questions/a*-algorithm).

There are much simpler ways to pass the test on AlgoExpert. This implementation is overkill for that context, but I wanted to write up header files and implement classes for the practice.

**Implementation Details:**
 * Uses a Node class to represent grid cells
 * Uses a MinHeap implementation specific to this problem for a priority queue
 * Uses the manhattan distance as the heuristic

**What This Code is Missing:**
 * Thorough unit tests
