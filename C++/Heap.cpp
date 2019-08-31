<<<<<<< Updated upstream
// Heap.cpp : This file contains the 'main' function. Program execution begins and ends there.
//
=======
/* Heap.cpp : This file contains the 'main' function. Program execution begins and ends there.

This file is about practicing setting up classes and inheritance structures in cpp. Implementing
a min and max heap that inherit from a base heap class helps with practicing object oriented
principles in C++ and serves as good practice for implementing data structures like trees.

Author: Lorin Achey
*/
>>>>>>> Stashed changes

#include <iostream>
#include <vector>

class Heap
{
public:
	int size = 0;
	std::vector<int> heapVector;

<<<<<<< Updated upstream
=======
	//TODO: Understand virtual keyword as it pertains to inheritance in MinHeap and MaxHeap classes
>>>>>>> Stashed changes
	//virtual void heapify();
	void printHeap(std::vector<int> heapVector);
};

class MaxHeap : public Heap
{
public:
	int size;
	std::vector<int> heapVector;

	MaxHeap(std::vector<int> heapVector, int size) : heapVector(heapVector), size(size) {}

	void heapify(std::vector<int> heapVector, int size, int p);

	int removeMax(std::vector<int> heapVector);
};

class MinHeap : public Heap
{
public:
	int size;
	std::vector<int> heapVector;

	MinHeap(std::vector<int> heapVector, int size) : heapVector(heapVector), size(size) {}

	void heapify(std::vector<int> heapVector, int size, int p);

	int removeMin(std::vector<int> heapVector);
};

void MinHeap::heapify(std::vector<int> heapVector, int size, int p)
{

}

void MaxHeap::heapify(std::vector<int> heapVector, int size, int p)
{

}

void Heap::printHeap(std::vector<int> heapVector)
{
	std::cout << "Heap in Array Representation: ";

	for (auto i : heapVector)
	{
		std::cout << i << " ";
	}
}

int main()
{
	std::vector<int> heapVector = {1, 3, 0, 5, 7, 8};
	int size = heapVector.size();

	MinHeap* minHeap = new MinHeap(heapVector, size);
	minHeap->heapify(minHeap->heapVector, minHeap->size, minHeap->heapVector[0]);
	minHeap->printHeap(heapVector);
<<<<<<< Updated upstream

    std::cout << "Hello World!\n";
=======
	
>>>>>>> Stashed changes
	getchar();
}