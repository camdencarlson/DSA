import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.NoSuchElementException;

/**
 * Your implementation of a BST.
 *
 * @author Camden Carlson
 * @version 1.0
 * @userid ccarlson43
 * @GTID 903550413
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class BST<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private BSTNode<T> root;
    private int size;

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize an empty BST.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public BST() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new BST.
     *
     * This constructor should initialize the BST with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * Hint: Not all Collections are indexable like Lists, so a regular for loop
     * will not work here. However, all Collections are Iterable, so what type
     * of loop would work?
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public BST(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you provided is null.");
        }
        for (T item : data) {
            add(item);
        }
    }

    /**
     * Adds the data to the tree.
     *
     * This must be done recursively.
     *
     * The data becomes a leaf in the tree.
     *
     * Traverse the tree to find the appropriate location. If the data is
     * already in the tree, then nothing should be done (the duplicate
     * shouldn't get added, and size should not be incremented).
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you tried to add was null.");
        }
        root = rAdd(root, data);


    }

    /**
     * Private helper method for adding to the BST
     * @param curr Current node
     * @param data Data to add with Comparable interface
     * @return BSTNode pointer reinforcement.
     */
    private BSTNode<T> rAdd(BSTNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new BSTNode<>(data);
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(rAdd(curr.getRight(), data));
        }
        return curr;
    }

    /**
     * Removes and returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     * simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     * replace it with its child.
     * 3: The node containing the data has 2 children. Use the successor to
     * replace the data. You MUST use recursion to find and remove the
     * successor (you will likely need an additional helper method to
     * handle this case efficiently).
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you provided is null.");
        }
        BSTNode<T> dummy = new BSTNode<>(null);
        root = rRemove(data, root, dummy);
        return dummy.getData();
    }

    /**
     * Private helper method to recursively remove
     * @param data data to remove
     * @param curr current node
     * @param dummy dummy node
     * @return BSTNode
     */
    private BSTNode<T> rRemove(T data, BSTNode<T> curr, BSTNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("The data you tried to remove does not exist in the BST.");
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rRemove(data, curr.getLeft(), dummy));
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rRemove(data, curr.getRight(), dummy));
        } else {
            dummy.setData(curr.getData());
            size--;
            if (curr.getRight() == null && curr.getLeft() == null) {
                return null;
            } else if (curr.getRight() == null) {
                return curr.getLeft();
            } else if (curr.getLeft() == null) {
                return curr.getRight();
            } else {
                BSTNode<T> dummy2 = new BSTNode<>(null);
                curr.setRight(removeSuccessor(curr.getRight(), dummy2));
                curr.setData(dummy2.getData());
            }

        }
        return curr;
    }

    /**
     * Removes successor from the BST.
     * @param currRight current node to the right of the node that was just removed
     * @param dummy2 A dummy node to hold the successors data
     * @return BSTNode
     */
    private BSTNode<T> removeSuccessor(BSTNode<T> currRight, BSTNode<T> dummy2) {
        if (currRight.getLeft() == null) {
            dummy2.setData(currRight.getData());
            return currRight.getRight();
        } else {
            currRight.setLeft(removeSuccessor(currRight.getLeft(), dummy2));
        }
        return currRight;
    }

    /**
     * Returns the data from the tree matching the given parameter.
     *
     * This must be done recursively.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you provided is null.");
        }
        BSTNode<T> curr = root;
        curr = rGet(data, curr);
        return curr.getData();
    }

    /**
     * recursive get private helper method
     * @param data data to find
     * @param curr current node
     * @return BSTNode
     */
    private BSTNode<T> rGet(T data, BSTNode<T> curr) {
        if (curr == null) {
            throw new NoSuchElementException("The data does not exist in the BST.");
        } else if (curr.getData().compareTo(data) > 0) {
            return rGet(data, curr.getLeft());
        } else if (curr.getData().compareTo(data) < 0) {
            return rGet(data, curr.getRight());
        } else {
            return curr;
        }
    }

    /**
     * Returns whether or not data matching the given parameter is contained
     * within the tree.
     *
     * This must be done recursively.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Must be O(log n) for best and average cases and O(n) for worst case.
     *
     * @param data the data to search for
     * @return true if the parameter is contained within the tree, false
     * otherwise
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public boolean contains(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you provided is null.");
        }
        return rContains(data, root);
    }

    /**
     * recursive contains private helper method
     * @param data data to find
     * @param curr current node
     * @return boolean representing whether or not the method contains the data
     */
    private boolean rContains(T data, BSTNode<T> curr) {
        if (curr == null) {
            return false;
        } else if (curr.getData().compareTo(data) > 0) {
            return rContains(data, curr.getLeft());
        } else if (curr.getData().compareTo(data) < 0) {
            return rContains(data, curr.getRight());
        } else {
            return true;
        }
    }

    /**
     * Generate a pre-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the preorder traversal of the tree
     */
    public List<T> preorder() {
        List<T> list = new LinkedList<>();
        rPreorder(root, list);
        return list;
    }

    /**
     * Preorder private helper method
     * @param curr current node
     * @param list list containing the preordered data
     */
    private void rPreorder(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            list.add(curr.getData());
            rPreorder(curr.getLeft(), list);
            rPreorder(curr.getRight(), list);
        }
    }
    /**
     * Generate an in-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the inorder traversal of the tree
     */
    public List<T> inorder() {
        List<T> list = new LinkedList<>();
        rInorder(root, list);
        return list;
    }

    /**
     * In order private helper method
     * @param curr current node
     * @param list list containing the in-ordered data
     */
    private void rInorder(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            rInorder(curr.getLeft(), list);
            list.add(curr.getData());
            rInorder(curr.getRight(), list);
        }
    }

    /**
     * Generate a post-order traversal of the tree.
     *
     * This must be done recursively.
     *
     * Must be O(n).
     *
     * @return the postorder traversal of the tree
     */
    public List<T> postorder() {
        List<T> list = new LinkedList<>();
        rPostOrder(root, list);
        return list;
    }

    /**
     * Post order private helper method
     * @param curr current node
     * @param list list containing the post-ordered data
     */
    private void rPostOrder(BSTNode<T> curr, List<T> list) {
        if (curr != null) {
            rPostOrder(curr.getLeft(), list);
            rPostOrder(curr.getRight(), list);
            list.add(curr.getData());
        }
    }

    /**
     * Generate a level-order traversal of the tree.
     *
     * This does not need to be done recursively.
     *
     * Hint: You will need to use a queue of nodes. Think about what initial
     * node you should add to the queue and what loop / loop conditions you
     * should use.
     *
     * Must be O(n).
     *
     * @return the level order traversal of the tree
     */
    public List<T> levelorder() {
        Queue<BSTNode<T>> q = new LinkedList<>();
        List<T> list = new LinkedList<>();
        q.add(root);
        while (!q.isEmpty()) {
            BSTNode<T> curr = q.remove();
            if (curr != null) {
                list.add(curr.getData());
                q.add(curr.getLeft());
                q.add(curr.getRight());
            }
        }
        return list;
    }

    /**
     * Returns the height of the root of the tree.
     *
     * This must be done recursively.
     *
     * A node's height is defined as max(left.height, right.height) + 1. A
     * leaf node has a height of 0 and a null child has a height of -1.
     *
     * Must be O(n).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        return rHeight(root);
    }

    /**
     * Private helper method for height
     * @param curr current node
     * @return int representing the height of the root
     */
    private int rHeight(BSTNode<T> curr) {
        if (curr == null) {
            return -1;
        } else {
            int left = rHeight(curr.getLeft());
            int right = rHeight(curr.getRight());

            if (left > right) {
                return (left + 1);
            } else {
                return (right + 1);
            }
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Finds and retrieves the k-largest elements from the BST in sorted order,
     * least to greatest.
     *
     * This must be done recursively.
     *
     * In most cases, this method will not need to traverse the entire tree to
     * function properly, so you should only traverse the branches of the tree
     * necessary to get the data and only do so once. Failure to do so will
     * result in an efficiency penalty.
     *
     * EXAMPLE: Given the BST below composed of Integers:
     *
     *                50
     *              /    \
     *            25      75
     *           /  \
     *          12   37
     *         /  \    \
     *        10  15    40
     *           /
     *          13
     *
     * kLargest(5) should return the list [25, 37, 40, 50, 75].
     * kLargest(3) should return the list [40, 50, 75].
     *
     * Should have a running time of O(log(n) + k) for a balanced tree and a
     * worst case of O(n + k).
     *
     * @param k the number of largest elements to return
     * @return sorted list consisting of the k largest elements
     * @throws java.lang.IllegalArgumentException if k > n, the number of data
     *                                            in the BST
     */
    public List<T> kLargest(int k) {
        if (k > size || k < 0) {
            throw new IllegalArgumentException("There is not enough data in the BST for the provided int k");
        }
        LinkedList<T> list = new LinkedList<>();
        rKlargest(root, list, k);
        return list;
    }

    /**
     * Private helper method for klargest
     * @param curr current node
     * @param list list containing the k largest data
     * @param k amount of largest data to find
     */
    private void rKlargest(BSTNode<T> curr, LinkedList<T> list, int k) {
        if (curr != null) {
            rKlargest(curr.getRight(), list, k);
            if (k > list.size()) {
                list.addFirst(curr.getData());
            }
            rKlargest(curr.getLeft(), list, k);
        }
    }


    /**
     * Returns the root of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the root of the tree
     */
    public BSTNode<T> getRoot() {
        // DO NOT MODIFY THIS METHOD!
        return root;
    }

    /**
     * Returns the size of the tree.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the tree
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
