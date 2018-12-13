import java.util.NoSuchElementException;

/**
 * Your implementation of an array-backed queue.
 *
 * @author Lorin Achey
 * @version 1.0
 */
public class ArrayQueue<T> implements QueueInterface<T> {

    // Do not add new instance variables.
    private T[] backingArray;

    // {@code front} is the index you will dequeue from
    private int front;
    // {@code back} is the index you will enqueue into
    private int back;
    private int size;


    /**
     * Constructs a new ArrayQueue.
     */
    public ArrayQueue() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
    }

    /**
     * Dequeue from the front of the queue.
     *
     * Do not shrink the backing array.
     * If the queue becomes empty as a result of this call, you must not
     * explicitly reset front or back to 0.
     *
     * @see QueueInterface#dequeue()
     */
    @Override
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("The Queue is empty.");
        } else {
            T answer = backingArray[front];
            backingArray[front] = null;
            front = (front + 1) % backingArray.length;
            size--;
            return answer;
        }
    }

    /**
     * Add the given data to the back of the queue.
     *
     * If sufficient space is not available in the backing array, you should
     * regrow it to (double the current length) + 1; in essence, 2n + 1, where n
     * is the current capacity. If a regrow is necessary, you should copy
     * elements to the beginning of the new array and reset back to 0.
     *
     * @see QueueInterface#enqueue(T)
     */
    @Override
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data.");
        }
        if (size == backingArray.length) {
            backingArray = regrowArray(backingArray);
            backingArray[size] = data;
            back = size + 1;
            size++;
        } else {
            backingArray[back] = data;
            back = (back + 1) % backingArray.length;
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
        front = 0;
        return regrownArray;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public int size() {
        return size;
    }

    /**
     * Returns the backing array of this queue.
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
