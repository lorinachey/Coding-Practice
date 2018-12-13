package StacksAndQueues;

import SinglyLinkedList.SinglyLinkedList;

import java.util.NoSuchElementException;

/**
 * Your implementation of an array-backed stack.
 *
 * @author Lorin Achey
 * @version 1.0
 */
public class ArrayStack<T> implements StackInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new StacksAndQueues.ArrayStack.
     */
    public ArrayStack() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    /**
     * Pop from the stack.
     *
     * Do not shrink the backing array.
     *
     * @see SinglyLinkedList.StackInterface#pop()
     */
    @Override
    public T pop() {
        if (size == 0) {
            throw new NoSuchElementException("The stack is empty.");
        } else {
            T answer = backingArray[size - 1];
            backingArray[size - 1] = null;
            size--;
            return answer;
        }
    }

    /**
     * Push the given data onto the stack.
     *
     * If sufficient space is not available in the backing array, you should
     * regrow it to (double the current length) + 1; in essence, 2n + 1, where n
     * is the current capacity.
     *
     * @see SinglyLinkedList.StackInterface#push(T)
     */
    @Override
    public void push(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null"
                    + "data to the stack.");
        }
        if (size == backingArray.length) {
            backingArray = regrowArray(backingArray);
            backingArray[size] = data;
            size++;
        } else {
            backingArray[size] = data;
            size++;
        }
    }

    /**
     * This method regrows an array to 2*length+1.
     *@param arr Array that needs to be regrown.
     *@return the new array, regrown
     */
    private T[] regrowArray(T[] arr) {
        T[] regrownArray = (T[]) new Object[2 * backingArray.length + 1];
        for (int i = 0; i < arr.length; i++) {
            regrownArray[i] = arr[i];
        }
        return regrownArray;
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the backing array of this stack.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the backing array
     */
    public Object[] getBackingArray() {
        // DO NOT MODIFY!
        return backingArray;
    }
}

