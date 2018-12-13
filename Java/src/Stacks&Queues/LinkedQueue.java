import java.util.NoSuchElementException;

/**
 * Your implementation of a linked queue.
 *
 * @author Lorin Achey
 * @version 1.0
 */
public class LinkedQueue<T> implements QueueInterface<T> {

    // Do not add new instance variables.
    private LinkedNode<T> head;
    private LinkedNode<T> tail;
    private int size;

    @Override
    public T dequeue() {
        if (size == 0) {
            throw new NoSuchElementException("The queue is empty");
        } else if (size == 1) {
            LinkedNode<T> answer = head;
            head = null;
            tail = null;
            size--;
            return answer.getData();
        } else {
            LinkedNode<T> answer = head;
            head = head.getNext();
            size--;
            return answer.getData();
        }
    }

    @Override
    public void enqueue(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data.");
        }
        if (size == 0) {
            LinkedNode<T> newNode = new LinkedNode<T>(data);
            head = newNode;
            tail = newNode;
            size++;
        } else {
            LinkedNode<T> newNode = new LinkedNode<T>(data);
            tail.setNext(newNode);
            tail = newNode;
            size++;
        }
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
     * Returns the head of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the head node
     */
    public LinkedNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail of this queue.
     * Normally, you would not do this, but we need it for grading your work.
     *
     * DO NOT USE THIS METHOD IN YOUR CODE.
     *
     * @return the tail node
     */
    public LinkedNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }
}