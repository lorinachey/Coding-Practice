/* Heap.cpp : This file contains the 'main' function. Program execution begins and ends there.

This file is about practicing setting up classes and inheritance structures in cpp. Implementing
a min and max heap that inherit from a base heap class helps with practicing object oriented
principles in C++ and serves as good practice for implementing data structures like trees.

Author: Lorin Achey
*/

#include <iostream>
#include <vector>

class Heap
{
public:
    //TODO: what's the OOP best practice on variable declarations for parent and child classes?
    int size = 0;
    std::vector<int> heapVector;
    
    // Defined as pure virtual because MinHeap will need a different heapify
    // than MaxHeap and Min or Max must be specified to build the heap.
    virtual void heapify(std::vector<int> &heapVector, int size, int p) = 0;

    void buildHeap(std::vector<int> &heapVector);
    
    void printHeap(std::vector<int> heapVector);

    virtual void overridePractice();

    virtual ~Heap() {}
};

class MaxHeap : public Heap
{
public:
    int size;
    std::vector<int> heapVector;

    MaxHeap(std::vector<int> heapVector, int size) : heapVector(heapVector), size(size) {}

    void heapify(std::vector<int> &heapVector, int size, int p);

    //TODO: implement remove Max and add bounds checks to it
    int removeMax(std::vector<int> heapVector);
};

class MinHeap : public Heap
{
public:
    int size;
    std::vector<int> heapVector;

    MinHeap(std::vector<int> heapVector, int size) : heapVector(heapVector), size(size) {}

    void heapify(std::vector<int> &heapVector, int size, int p);

    int removeMin(std::vector<int> &heapVector);

    void overridePractice();
};

void MinHeap::heapify(std::vector<int> &heapVector, int size, int p)
{
    int smallest = p;
    int left = 2 * p + 1;
    int right = left + 1;

    if (left < size && heapVector[left] < heapVector[smallest])
    {
        smallest = left;
    }

    if (right < size && heapVector[right] < heapVector[smallest])
    {
        smallest = right;
    }

    if (smallest != p)
    {
        std::swap(heapVector[p], heapVector[smallest]);
        heapify(heapVector, heapVector.size(), smallest);
    }
}

int MinHeap::removeMin(std::vector<int> &heapVector)
{
    //TODO: Add bounds checks and error checking!

    int returnVal = heapVector[0];

    heapVector[0] = heapVector.back();
    heapVector.pop_back();

    heapify(heapVector, heapVector.size(), 0);

    return returnVal;
}

void MinHeap::overridePractice()
{
    std::cout << "Override from MinHeap Class";
}

void MaxHeap::heapify(std::vector<int> &heapVector, int size, int p)
{
    int largest = p;
    int left = 2 * p + 1;
    int right = left + 1;

    if (left < size && heapVector[left] > heapVector[largest])
    {
        largest = left;
    }

    if (right < size && heapVector[right] > heapVector[largest])
    {
        largest = right;
    }

    if (largest != p)
    {
        std::swap(heapVector[p], heapVector[largest]);
        heapify(heapVector, heapVector.size(), largest);
    }

}

void Heap::buildHeap(std::vector<int> &heapVector)
{
    int startNode = (heapVector.size() / 2) - 1;

    for (int i = startNode; i >= 0; i--)
    {
        heapify(heapVector, heapVector.size(), i);
    }
}

void Heap::printHeap(std::vector<int> heapVector)
{
    for (auto i : heapVector)
    {
        std::cout << i << " ";
    }
    std::cout << "\n";
}

void Heap::overridePractice()
{
    std::cout << "Override in Heap Class";
}

int main()
{
    std::vector<int> heapVector = {100, 30, 20, 10};
    int size = heapVector.size();

    MaxHeap* maxHeap = new MaxHeap(heapVector, size);
    maxHeap->buildHeap(maxHeap->heapVector);

    std::cout << "\nBuilding Max Heap: ";
    maxHeap->printHeap(maxHeap->heapVector);

    std::cout << "\nBuilding Min Heap: ";
    MinHeap* minHeap = new MinHeap(heapVector, size);
    minHeap->buildHeap(minHeap->heapVector);
    minHeap->printHeap(minHeap->heapVector);

    minHeap->removeMin(minHeap->heapVector);
    minHeap->printHeap(minHeap->heapVector);
    
    delete maxHeap;
    delete minHeap;
    getchar();
}