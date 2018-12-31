import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;
import java.util.Set;

import static java.lang.Math.max;

/**
 * Your implementation of an AVL Tree.
 *
 * @author Lorin Achey
 * @version 1.0
 */
public class AVL<T extends Comparable<? super T>> implements AVLInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private AVLNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty AVL tree.
     * DO NOT IMPLEMENT THIS CONSTRUCTOR!
     */
    public AVL() {
    }

    /**
     * Initializes the AVL tree with the data in the Collection. The data
     * should be added in the same order it is in the Collection.
     *
     * @param data the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data in the Collection"
                    + " cannot be null.");
        }
        for (T element : data) {
            add(element);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data.");
        }
        root = add(data, root);
    }

    /**
     * Adds the data to the tree recursively.
     * Returns the node
     * @param data the data to add to the tree
     * @param current the current node for that iteration
     * @return the root node of the tree
     */
    private AVLNode<T> add(T data, AVLNode<T> current) {

        if (current == null) {
            AVLNode<T> newNode = new AVLNode<T>(data);
            updateHeightAndBalanceFactor(newNode);
            size++;
            return newNode;
        } else if (current.getData().compareTo(data) < 0) {
            current.setRight(add(data, current.getRight()));
        } else if (current.getData().compareTo(data) > 0) {
            current.setLeft(add(data, current.getLeft()));
        }

        updateHeightAndBalanceFactor(current);
        int rotationType = checkForNecessaryRotation(current);
        if (rotationType > 0 && rotationType < 2) {
            current = leftRotation(current);
        } else if (rotationType > 1 && rotationType < 3) {
            current = rightRotation(current);
        } else if (rotationType > 2 && rotationType < 4) {
            current.setRight(rightRotation(current.getRight()));
            current = leftRotation(current);
        } else if (rotationType > 3 && rotationType < 5) {
            current.setLeft(leftRotation(current.getLeft()));
            current = rightRotation(current);
        }
        return current;
    }

    /**
     * Updates the height and balance factor for the parameter node
     * @param current the current node for that iteration
     */
    private void updateHeightAndBalanceFactor(AVLNode<T> current) {
        if (current != null) {
            if (current.getLeft() == null && current.getRight() == null) {
                current.setHeight(0);
                current.setBalanceFactor(0);
            } else if (current.getLeft() != null && current.getRight() != null) {
                current.setHeight(max(current.getLeft().getHeight(),
                        current.getRight().getHeight()) + 1);
                current.setBalanceFactor(current.getLeft().getHeight()
                        - current.getRight().getHeight());
            } else if (current.getLeft() == null && current.getRight() != null) {
                current.setHeight(current.getRight().getHeight() + 1);
                current.setBalanceFactor((-1) - current.getRight().getHeight());
            } else if (current.getRight() == null && current.getLeft() != null) {
                current.setHeight(current.getLeft().getHeight() + 1);
                current.setBalanceFactor(current.getLeft().getHeight() - (-1));
            }
        }
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        AVLNode<T> dummyNode = new AVLNode<T>(null);
        root = remove(root, data, dummyNode);
        return dummyNode.getData();
    }

    /**
     * Remove helper method that handles the 3 case: No child, 1 child, 2 child
     * @param current the node currently being evaluated
     * @param data the data to be removed
     * @param dummyNode the node that will store the data
     *                  to be removed and returned
     * @return the current node each iteration
     */
    private AVLNode<T> remove(AVLNode<T> current, T data,
                              AVLNode<T> dummyNode) {
        if (current == null) {
            throw new NoSuchElementException("Data is not in the "
                    + "tree and therefore can't be removed.");
        }

        if (current.getData().compareTo(data) == 0) {
            if (current.getLeft() == null
                    && current.getRight() == null) {

                dummyNode.setData(current.getData());
                current = null;
                size--;

            } else if (current.getLeft() != null
                    && current.getRight() == null) {

                dummyNode.setData(current.getData());
                current = current.getLeft();
                size--;
                updateHeightAndBalanceFactor(current);

            } else if (current.getRight() != null
                    && current.getLeft() == null) {

                dummyNode.setData(current.getData());
                current = current.getRight();
                updateHeightAndBalanceFactor(current);

            } else if (current.getLeft() != null
                    && current.getRight() != null) {

                dummyNode.setData(current.getData());
                AVLNode<T> dummyForSuccessor = new AVLNode<T>(null);
                current.setRight(findSuccessor(
                        current.getRight(), dummyForSuccessor));
                current.setData(dummyForSuccessor.getData());
                size--;
                updateHeightAndBalanceFactor(current.getRight());
                updateHeightAndBalanceFactor(current);

            }
        } else if (current.getData().compareTo(data) < 0) {
            current.setRight(remove(current.getRight(), data, dummyNode));
        } else if (current.getData().compareTo(data) > 0) {
            current.setLeft(remove(current.getLeft(), data, dummyNode));
        }

        updateHeightAndBalanceFactor(current);
        int rotationType = checkForNecessaryRotation(current);
        if (rotationType > 0 && rotationType < 2) {
            current = leftRotation(current);
        } else if (rotationType > 1 && rotationType < 3) {
            current = rightRotation(current);
        } else if (rotationType > 2 && rotationType < 4) {
            current.setRight(rightRotation(current.getRight()));
            current = leftRotation(current);
        } else if (rotationType > 3 && rotationType < 5) {
            current.setLeft(leftRotation(current.getLeft()));
            current = rightRotation(current);
        }
        return current;
    }

    /**
     * Adds the data to the tree recursively.
     * Returns the node
     * @param current the current node for that iteration
     * @param successor the successor to the node data being removed
     * @return the root node of the tree
     */
    private AVLNode<T> findSuccessor(AVLNode<T> current, AVLNode<T> successor) {
        if (current.getLeft() == null) {
            successor.setData(current.getData());
            current = current.getRight();
        } else {
            current.setLeft(findSuccessor(current.getLeft(), successor));
        }
        return current;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        T dataReturnedFromGet = get(root, data);
        if (dataReturnedFromGet == null) {
            throw new NoSuchElementException("Data is not in the tree.");
        }
        return dataReturnedFromGet;
    }

    /**
     * Retrieves the data from the BST that matches the input data
     * @param current the node currently being evaluated each iteration
     * @param data the data your looking for in the BST
     * @return the actual data from the BST that matches input data
     */
    private T get(AVLNode<T> current, T data) {
        if (current == null) {
            return null;
        } else if (data.compareTo(current.getData()) == 0) {
            return current.getData();
        } else if (data.compareTo(current.getData()) > 0) {
            return (get(current.getRight(), data));
        } else {
            return (get(current.getLeft(), data));
        }
    }

    @Override
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        T dataContained = get(root, data);
        return (dataContained != null);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public List<T> preorder() {
        List<T> list = new ArrayList<T>();
        list = preorder(root, list);
        return list;
    }

    /**
     * The preorder list structure.
     * @param current the node currently being evaluated each iteration
     * @param list the list of data from the BST
     * @return the list in preorder
     */
    private List<T> preorder(AVLNode<T> current, List<T> list) {
        if (current != null) {
            list.add(current.getData());
            preorder(current.getLeft(), list);
            preorder(current.getRight(), list);
            return list;
        } else {
            return list;
        }
    }

    @Override
    public List<T> postorder() {
        List<T> list = new ArrayList<T>();
        list = postorder(root, list);
        return list;
    }

    /**
     * The postorder list structure.
     * @param current the node currently being evaluated each iteration
     * @param list the list that the data will be added to
     * @return the list in postorder order
     */
    private List<T> postorder(AVLNode<T> current, List<T> list) {
        if (current != null) {
            postorder(current.getLeft(), list);
            postorder(current.getRight(), list);
            list.add(current.getData());
            return list;
        } else {
            return list;
        }
    }
    @Override
    public Set<T> threshold(T lower, T upper) {
        if (lower == null || upper == null) {
            throw new IllegalArgumentException("Upper and"
                    + " lower cannot be null.");
        }
        Set<T> set = new HashSet<T>();
        set = threshold(root, lower, upper, set);
        return set;
    }

    /**
     * Finds all the data between the lower and upper parameters
     * and adds it to a set
     * @param current the current node for that iteration
     * @param lower the lower data for the threshold
     * @param upper the upper data for the threshold
     * @param set the set the data within the threshold is added to
     * @return the set of data within the threshold
     */
    private Set<T> threshold(AVLNode<T> current, T lower, T upper, Set<T> set) {
        if (current == null) {
            return set;
        } else {
            if (current.getData().compareTo(lower) > 0
                    && current.getData().compareTo(upper) < 0) {
                set.add(current.getData());
            }
            threshold(current.getLeft(), lower, upper, set);
            threshold(current.getRight(), lower, upper, set);
        }
        return set;
    }

    @Override
    public List<T> levelorder() {
        Queue<AVLNode<T>> queue = new LinkedList<AVLNode<T>>();
        List<T> list = new ArrayList<T>();
        queue.add(root);
        while (!queue.isEmpty()) {
            AVLNode<T> current = queue.poll();
            if (current != null) {
                list.add(current.getData());
                queue.add(current.getLeft());
                queue.add(current.getRight());
            }
        }
        return list;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        if (size == 0) {
            return -1;
        } else {
            return (root.getHeight());
        }
    }

    /**
     * Check if the node needs a rotation
     * @param current the current node for that iteration
     * @return the integer value for that type of rotation
     */
    private int checkForNecessaryRotation(AVLNode<T> current) {
        if (current == null) {
            return -1;
        }
        //return 1 for a single left rotation, 2 for a single right
        //return 3 for right-left, return 4 for left-right
        if (current.getBalanceFactor() <= -2) {
            if (current.getRight() != null
                    && current.getRight().getBalanceFactor() <= 0) {
                return 1;
            } else {
                return 3;
            }
        } else if (current.getBalanceFactor() >= 2) {
            if (current.getLeft() != null
                    && current.getLeft().getBalanceFactor() >= 0) {
                return 2;
            } else {
                return 4;
            }
        }
        return -1;
    }

    /**
     * Performs a right rotation
     * @param current the current node for that iteration
     * @return the root node of the subtree
     */
    private AVLNode<T> rightRotation(AVLNode<T> current) {
        AVLNode<T> reassignedNode = current.getLeft();
        current.setLeft(reassignedNode.getRight());
        reassignedNode.setRight(current);

        updateHeightAndBalanceFactor(reassignedNode.getLeft());
        updateHeightAndBalanceFactor(reassignedNode.getRight());
        updateHeightAndBalanceFactor(reassignedNode);

        return reassignedNode;
    }

    /**
     * Performs a left rotation
     * @param current the current node for that iteration
     * @return the root node of the subtree
     */
    private AVLNode<T> leftRotation(AVLNode<T> current) {
        AVLNode<T> reassignedNode = current.getRight();
        current.setRight(reassignedNode.getLeft());
        reassignedNode.setLeft(current);

        updateHeightAndBalanceFactor(reassignedNode.getRight());
        updateHeightAndBalanceFactor(reassignedNode.getLeft());
        updateHeightAndBalanceFactor(reassignedNode);

        return reassignedNode;
    }

    @Override
    public AVLNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
