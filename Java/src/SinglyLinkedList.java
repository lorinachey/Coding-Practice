import java.util.NoSuchElementException;

/**
 * Your implementation of a SinglyLinkedList
 *
 * @author Lorin Achey
 * @version 1.0
 */
public class SinglyLinkedList<T extends Comparable<? super T>> implements
        LinkedListInterface<T> {
    // Do not add new instance variables.
    private SLLNode<T> head;
    private SLLNode<T> tail;
    private int size;

    @Override
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        } else if (size == 0) {
            SLLNode<T> newNode = new SLLNode<T>(data);
            head = newNode;
            tail = newNode;
            size++;
        } else {
            SLLNode<T> newNode = new SLLNode<T>(data);
            newNode.setNext(head);
            head = newNode;
            size++;
        }
    }

    @Override
    public void addAtIndex(T data, int index) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null");
        }
        if (index > size || index < 0) {
            throw new IndexOutOfBoundsException("Index is"
                    + index + " but bounds are 0 to "
                    + size + ".");
        }
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            SLLNode<T> newNode = new SLLNode<T>(data);
            SLLNode<T> current = head;

            for (int i = 1; i < index; i++) {
                current = current.getNext();
            }
            SLLNode<T> temp = current.getNext();
            current.setNext(newNode);
            newNode.setNext(temp);
            size++;
        }
    }

    @Override
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data is null.");
        }
        if (size == 0) {
            SLLNode<T> newNode = new SLLNode<T>(data);
            head = newNode;
            tail = newNode;
            size++;
        } else {
            SLLNode<T> newNode = new SLLNode(data);
            tail.setNext(newNode);
            tail = newNode;
            size++;
        }
    }

    @Override
    public T removeFromFront() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            SLLNode<T> temp = head;
            clear();
            return temp.getData();
        } else {
            SLLNode<T> temp = head;
            head = head.getNext();
            size--;
            return temp.getData();
        }
    }

    @Override
    public T removeAtIndex(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is"
                    + index + " but bounds are 0 to "
                    + size + ".");
        } else if (index == 0) {
            return (removeFromFront());
        } else if (index == size - 1) {
            return (removeFromBack());
        } else {
            SLLNode<T> temp = head;
            for (int i = 1; i < index; i++) {
                temp = temp.getNext();
            }
            SLLNode<T> removed = temp.getNext();
            temp.setNext(temp.getNext().getNext());
            size--;
            return removed.getData();
        }
    }

    @Override
    public T removeFromBack() {
        if (size == 0) {
            return null;
        } else {
            SLLNode<T> previous = head;
            for (int i = 1; i < size - 1; i++) {
                previous = previous.getNext();
            }
            if (size == 1) {
                SLLNode<T> temp = head;
                clear(); //clear() will update size variable
                return temp.getData();
            } else {
                SLLNode<T> temp = previous.getNext();
                previous.setNext(null);
                tail = previous;
                size--;
                return temp.getData();
            }
        }
    }

    @Override
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index is"
                    + index + " but bounds are 0 to "
                    + size + ".");
        } else if (index == 0) {
            return head.getData();
        } else if (index == size - 1) {
            return tail.getData();
        } else {
            SLLNode<T> current = head;
            for (int i = 0; i < index; i++) {
                current = current.getNext();
            }
            return current.getData();
        }
    }

    @Override
    public T findLargestElement() {
        if (size == 0) {
            return null;
        } else if (size == 1) {
            return head.getData();
        } else {
            T largest = head.getData();
            SLLNode<T> temp = head.getNext();
            while (temp != null) {
                if (largest.compareTo(temp.getData()) < 0) {
                    largest = temp.getData();
                }
                temp = temp.getNext();
            }
            return largest;
        }
    }

    @Override
    public Object[] toArray() {
        Object[] arr = new Object[size];
        SLLNode<T> temp = head;
        for (int i = 0; i < size; i++) {
            arr[i] = temp.getData();
            temp = temp.getNext();
        }
        return arr;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return (size == 0);
    }

    @Override
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public SLLNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    @Override
    public SLLNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    //the following method is a test method for exam review

    public boolean removeEvenElements() {
        boolean isModified = false;
        if (size == 0) {
            throw new NoSuchElementException("This list has no elements in it.");
        } else {
            if (size == 1) {
                if ((Integer) head.getData() % 2 == 0) {
                    head = null;
                    size--;
                    isModified = true;
                }
                return isModified;
            } else if (size == 2) {
                if ( (Integer) head.getData() % 2 == 0) {
                    size--;
                    head = head.getNext();
                    if ((Integer) head.getData() % 2 == 0) {
                        head = null;
                        size--;
                    }
                    isModified = true;
                }
                return isModified;
            } else {
                SLLNode<T> current = head;
                while (current != null) {
                    if (current.getNext() != null
                            && current.getNext().getNext() != null) {
                                if ((Integer)  current.getNext().getData() % 2 == 0) {
                                    current.setNext(current.getNext().getNext());
                                    size--;
                                    isModified = true;
                                }
                    } else if (current.getNext() != null
                            && current.getNext().getNext() == null) {
                                if ((Integer) current.getNext().getData() % 2 == 0) {
                                    current.setNext(null);
                                    size--;
                                    isModified = true;
                                }
                    }
                    current = current.getNext();
                }
                SLLNode<T> temp = head;
                for (int i = 0; i < size; i++) {
                    System.out.println(temp);
                    temp = temp.getNext();
                }
                return isModified;
            }
        }
    }
}
