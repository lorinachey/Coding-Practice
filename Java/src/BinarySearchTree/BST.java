import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

import static java.lang.Integer.max;

/**
 * Your implementation of a binary search tree.
 *
 * @author Lorin Achey
 * @version 1.0
 */
public class BST<T extends Comparable<? super T>> implements BSTInterface<T> {
    // DO NOT ADD OR MODIFY INSTANCE VARIABLES.
    private BSTNode<T> root;
    private int size;

    /**
     * A no argument constructor that should initialize an empty BST.
     * YOU DO NOT NEED TO IMPLEMENT THIS CONSTRUCTOR!
     */
    public BST() {
    }

    /**
     * Initializes the BST with the data in the Collection. The data in the BST
     * should be added in the same order it is in the Collection.
     *
     * @param collection the data to add to the tree
     * @throws IllegalArgumentException if data or any element in data is null
     */
    public BST(Collection<T> collection) {
        for (T element : collection) {
            add(element);
        }
    }

    @Override
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot add null data.");
        } else {
            root = add(data, root);
        }
    }

    /**
     * The private helper method that involves the recursion for add.
     * @param data the data that is to be added
     * @param current the current node for that iteration
     * @return the root of the tree
     */
    private BSTNode<T> add(T data, BSTNode<T> current) {
        if (current == null) {
            BSTNode<T> newNode = new BSTNode<T>(data);
            size++;
            return newNode;
        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(add(data, current.getLeft()));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(add(data, current.getRight()));
        }
        return current;
    }

    @Override
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot search for null data.");
        }
        BSTNode<T> dummyForRemovedData = new BSTNode<T>(null);
        root = remove(root, data, dummyForRemovedData);
        return dummyForRemovedData.getData();
    }

    /**
     * Remove helper method that handles the 3 case: No child, 1 child, 2 child
     * @param current the node currently being evaluated
     * @param data the data to be removed
     * @param dummyNode the node that will store the data
     *                  to be removed and returned
     * @return the current node each iteration
     */
    private BSTNode<T> remove(BSTNode<T> current, T data,
                              BSTNode<T> dummyNode) {
        if (current == null) {
            throw new NoSuchElementException("Data is not in the "
                    + "tree and therefore can't be removed.");
        }
        if (current.getData().compareTo(data) == 0) {
            if (current.getLeft() == null && current.getRight() == null) {
                dummyNode.setData(current.getData());
                current = null;
                size--;
            } else if (current.getLeft() == null
                    && current.getRight() != null) {
                dummyNode.setData(current.getData());
                current = current.getRight();
                size--;
                return current;
            } else if (current.getRight() == null
                    && current.getLeft() != null) {
                dummyNode.setData(current.getData());
                current = current.getLeft();
                size--;
                return current;
            } else if (current.getLeft() != null
                    && current.getRight() != null) {

                dummyNode.setData(current.getData());
                BSTNode<T> secondDummyNode = new BSTNode<T>(null);
                current.setLeft(removePred(current.getLeft(), secondDummyNode));
                current.setData(secondDummyNode.getData());
                size--;
                return current;
            }
        } else if (data.compareTo(current.getData()) < 0) {
            current.setLeft(remove(current.getLeft(), data, dummyNode));
        } else if (data.compareTo(current.getData()) > 0) {
            current.setRight(remove(current.getRight(), data, dummyNode));
        }
        return current;
    }

    /**
     * The method that removes the predecessor to the removed node
     * @param current the current node being evaluated that iteration
     * @param localDummy the dummy node that stores the predecessor informaiton
     * @return the node where the predecessor is found
     */
    private BSTNode<T> removePred(BSTNode<T> current, BSTNode<T> localDummy) {
        if (current.getRight() == null) {
            localDummy.setData(current.getData());
            current = current.getLeft();
        } else {
            current.setRight(removePred(current.getRight(), localDummy));
        }
        return current;
    }

    @Override
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Cannot get null data.");
        }
        T returnedFromSearch = get(root, data);
        if (returnedFromSearch == null) {
            throw new NoSuchElementException("Data is not in the tree.");
        }
        return returnedFromSearch;
    }

    /**
     * Retrieves the data from the BST that matches the input data
     * @param current the node currently being evaluated each iteration
     * @param data the data your looking for in the BST
     * @return the actual data from the BST that matches input data
     */
    private T get(BSTNode<T> current, T data) {
        if (current == null) {
            return null;
        } else if (data.equals(current.getData())) {
            return current.getData();
        } else if (data.compareTo(current.getData()) > 0) {
            return (get(current.getRight(), data));
        } else {
            return (get(current.getLeft(), data));
        }
    }

    @Override
    public boolean contains(T data) {
        T dataFound = get(data);
        return (dataFound != null);
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
    private List<T> preorder(BSTNode<T> current, List<T> list) {
        if (current != null) {
            list.add(current.getData());
            preorder(current.getLeft(), list);
            preorder(current.getRight(), list);
            return list;
        } else {
            return null;
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
    private List<T> postorder(BSTNode<T> current, List<T> list) {
        if (current != null) {
            postorder(current.getLeft(), list);
            postorder(current.getRight(), list);
            list.add(current.getData());
            return list;
        } else {
            return null;
        }
    }

    @Override
    public List<T> inorder() {
        List<T> list = new ArrayList<T>();
        list = inorder(root, list);
        return list;
    }

    /**
     * The inorder list structure.
     * @param current the node currently being evaluated each iteration
     * @param list the list that the data will be added to
     * @return the list in inorder order
     */
    private List<T> inorder(BSTNode<T> current, List<T> list) {
        if (current != null) {
            inorder(current.getLeft(), list);
            list.add(current.getData());
            inorder(current.getRight(), list);
            return list;
        } else {
            return null;
        }
    }

    @Override
    public List<T> findPathBetween(T data1, T data2) {
        if (data1 == null || data2 == null) {
            throw new IllegalArgumentException("Data cannot be null.");
        }
        List<T> list = new LinkedList<>();
        if (data1.compareTo(data2) == 0) {
            list.add(data1);
            return list;
        }
        BSTNode<T> deepestCommonAncestor =
                deepestCommonAncestor(data1, data2, root);
        list = traverseToData1(data1, deepestCommonAncestor, list);
        list = traverseToData2(data2, deepestCommonAncestor, list);
        return list;
    }

    /**
     * Takes the common ancestor and starts traversing to data1,
     * adding to the list from the front
     * @param data1 the first parameter in the findPathBetween method
     * @param current the node currently being evaluated each iteration
     * @param list the list that represents the path
     * @return the list complete with the path to data1
     */
    private List<T> traverseToData1(T data1, BSTNode<T> current, List<T> list) {
        if (current != null) {
            if (data1.compareTo(current.getData()) == 0) {
                ((LinkedList<T>) list).addFirst(current.getData());
                ((LinkedList<T>) list).removeLast();
                //Keeps common ancestor data
                //from being added twice: once in the
                //traverseToData1 and again in
                //in the traverseToData2 method
                return list;
            } else if (data1.compareTo(current.getData()) > 0) {
                ((LinkedList<T>) list).addFirst(current.getData());
                return traverseToData1(data1, current.getRight(), list);
            } else {
                ((LinkedList<T>) list).addFirst(current.getData());
                return traverseToData1(data1, current.getLeft(), list);
            }
        }
        throw new NoSuchElementException("Data1 is not in the tree.");
    }

    /**
     * Takes the common ancestor and starts traversing to data2
     * adding to the list from the back
     * @param data2 the second parameter in the findpathbetween method
     * @param current the node currently being evaluated each iteration
     * @param list the partially completed list
     * @return the fully completed list representing the path
     * between the two elements
     */
    private List<T> traverseToData2(T data2, BSTNode<T> current, List<T> list) {
        if (current != null) {
            if (data2.compareTo(current.getData()) == 0) {
                ((LinkedList<T>) list).addLast(current.getData());
                return list;
            } else if (data2.compareTo(current.getData()) > 0) {
                ((LinkedList<T>) list).addLast(current.getData());
                return traverseToData2(data2, current.getRight(), list);
            } else {
                ((LinkedList<T>) list).addLast(current.getData());
                return traverseToData2(data2, current.getLeft(), list);
            }
        }
        throw new NoSuchElementException("Data2 is not in the tree.");
    }

    /**
     * The method that finds the deepest common ancestor.
     * @param data1 the first data element
     * @param data2 the second data element
     * @param current the node currently being evaluated each iteration
     * @return the node that holds the data for the deepest common ancestor
     */
    private BSTNode<T> deepestCommonAncestor(T data1, T data2,
                                             BSTNode<T> current) {
        if (current == null) {
            throw new NoSuchElementException("Data is not in the tree.");
        }
        if (data1.compareTo(data2) < 0) {
            if (current.getData().compareTo(data1) >= 0
                    && current.getData().compareTo(data2) <= 0) {
                return current;
            } else if (current.getData().compareTo(data1) < 0) {
                return (deepestCommonAncestor(data1, data2,
                        current.getRight()));
            } else {
                return (deepestCommonAncestor(data1, data2, current.getLeft()));
            }
        } else {
            if (current.getData().compareTo(data2) >= 0
                    && current.getData().compareTo(data1) <= 0) {
                return current;
            } else if (current.getData().compareTo(data2) < 0) {
                return (deepestCommonAncestor(data1, data2,
                        current.getRight()));
            } else {
                return (deepestCommonAncestor(data1, data2, current.getLeft()));
            }
        }
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    @Override
    public int height() {
        if (size == 1) {
            return 0;
        }
        int height = height(root);
        return  height;
    }

    /**
     * The height helper function where recursion takes place
     * @param current the node currently being evaluated each iteration
     * @return the integer representing the height
     */
    private int height(BSTNode<T> current) {
        if (current == null) {
            return -1;
        } else {
            int left = height(current.getLeft());
            int right = height(current.getRight());
            return (max(left, right) + 1);
        }
    }

    @Override
    public BSTNode<T> getRoot() {
        // DO NOT EDIT THIS METHOD!
        return root;
    }
}
