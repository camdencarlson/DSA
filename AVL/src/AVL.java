import java.util.*;

/**
 * Your implementation of an AVL.
 *
 * @author Camden Carlson
 * @version 1.0
 * @userid XXXXXXXX
 * @GTID XXXXXXXX
 *
 * Collaborators:
 *
 * Resources:
 */
public class AVL<T extends Comparable<? super T>> {

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private AVLNode<T> root;
    private int size;

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize an empty AVL.
     *
     * Since instance variables are initialized to their default values, there
     * is no need to do anything for this constructor.
     */
    public AVL() {
        // DO NOT IMPLEMENT THIS CONSTRUCTOR!
    }

    /**
     * Constructs a new AVL.
     *
     * This constructor should initialize the AVL with the data in the
     * Collection. The data should be added in the same order it is in the
     * Collection.
     *
     * @param data the data to add to the tree
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public AVL(Collection<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you provided is null.");
        }
        for (T datum : data) {
            add(datum);
        }
    }

    /**
     * Adds the element to the tree.
     *
     * Start by adding it as a leaf like in a regular BST and then rotate the
     * tree as necessary.
     *
     * If the data is already in the tree, then nothing should be done (the
     * duplicate shouldn't get added, and size should not be incremented).
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after adding the element, making sure to rebalance if
     * necessary.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you provided is null.");
        }
        root = rAdd(root, data);
    }

    /**
     * Private recursive helper method for adding to an AVL
     * @param curr current node
     * @param data data to add
     * @return node with data added
     */
    private AVLNode<T> rAdd(AVLNode<T> curr, T data) {
        if (curr == null) {
            size++;
            return new AVLNode<>(data);
        } else if (curr.getData().compareTo(data) > 0) {
            curr.setLeft(rAdd(curr.getLeft(), data));
            curr.setHeight((curr.getLeft().getHeight() + 1));
            if (curr.getLeft().getHeight() - (curr.getRight() == null ? -1:curr.getRight().getHeight()) > 1) {
                if (curr.getLeft().getBalanceFactor() < 0) {
                    curr = LeftRight(curr);
                } else {
                    curr = rightRotation(curr);
                }
            } else if (curr.getLeft().getHeight() - (curr.getRight() == null ? -1:curr.getRight().getHeight()) < -1) {
                if (curr.getRight().getBalanceFactor() > 0) {
                    curr = RightLeft(curr);
                } else {
                    curr = leftRotation(curr);
                }
            } else {
                curr.setBalanceFactor(curr.getLeft().getHeight()
                        - (curr.getRight() == null ? -1:curr.getRight().getHeight()));
            }
        } else if (curr.getData().compareTo(data) < 0) {
            curr.setRight(rAdd(curr.getRight(), data));
            curr.setHeight(curr.getRight().getHeight() + 1);
            if ((curr.getLeft() == null ? -1:curr.getLeft().getHeight()) - curr.getRight().getHeight() > 1) {
                if (curr.getRight().getBalanceFactor() < 0) {
                    curr = LeftRight(curr);
                } else {
                    curr = rightRotation(curr);
                }
            } else if ((curr.getLeft() == null ? -1:curr.getLeft().getHeight()) - curr.getRight().getHeight() < -1) {
                if (curr.getRight().getBalanceFactor() > 0) {
                    curr = RightLeft(curr);
                } else {
                    curr = leftRotation(curr);
                }
            } else {
                curr.setBalanceFactor((curr.getLeft() == null ? -1:curr.getLeft().getHeight())
                        - curr.getRight().getHeight());
            }
        }
        return curr;
    }

    /**
     * Private helper for left rotations in an AVL
     * @param node node to start at
     * @return node to the right of the starting node
     */
    private AVLNode<T> leftRotation(AVLNode<T> node) {
        node.setHeight((node.getHeight() > 1 ? node.getHeight() - 2: 0));
        AVLNode<T> dummy = node.getRight();
        node.setRight(dummy.getLeft());
        dummy.setLeft(node);
        if (node.getHeight() == 0) {
            node.setBalanceFactor(0);
        } else if (node.getLeft() == null) {
            node.setBalanceFactor(-1 - node.getRight().getHeight());
        } else if (node.getRight() == null) {
            node.setBalanceFactor(node.getLeft().getHeight() + 1);
        } else {
            node.setBalanceFactor(node.getLeft().getHeight() - node.getRight().getHeight());
        }
        dummy.setHeight(dummy.getRight() == null ? dummy.getLeft().getHeight() + 1:dummy.getHeight());
        dummy.setBalanceFactor(dummy.getLeft().getHeight()
                - (dummy.getRight() == null ? -1:dummy.getRight().getHeight()));
        return dummy;
    }

    /**
     * Private helper for right rotations in an AVL
     * @param node node to start at
     * @return node to the left of the starting node
     */
    private AVLNode<T> rightRotation(AVLNode<T> node) {
        node.setHeight((node.getHeight() > 1 ? node.getHeight() - 2: 0));
        AVLNode<T> dummy = node.getLeft();
        node.setLeft(dummy.getRight());
        dummy.setRight(node);

        if (node.getHeight() == 0) {
            node.setBalanceFactor(0);
        } else if (node.getRight() == null) {
            node.setBalanceFactor(node.getLeft().getHeight() + 1);
        } else if (node.getLeft() == null) {
            node.setBalanceFactor(-1 - node.getRight().getHeight());
        } else {
            node.setBalanceFactor(node.getRight().getHeight() - node.getLeft().getHeight());
        }
        if (dummy.getLeft() == null) {
            dummy.setBalanceFactor(-1 -dummy.getRight().getHeight());
        } else {
            dummy.setBalanceFactor(dummy.getLeft().getHeight() - dummy.getRight().getHeight());
        }
        dummy.setHeight(dummy.getLeft() == null ? dummy.getRight().getHeight() + 1:dummy.getHeight());
        return dummy;
    }

    /**
     * Private helper for RightLeft double rotation
     * @param node node to start at
     * @return highest node after it has been rotated
     */
    private AVLNode<T> RightLeft(AVLNode<T> node) {
        node.setRight(rightRotation(node.getRight()));
        AVLNode<T> dummy = leftRotation(node);
        return dummy;
    }

    /**
     * Private helper for LeftRight double rotation
     * @param node node to start at
     * @return highest node after it has been rotated
     */
    private AVLNode<T> LeftRight(AVLNode<T> node) {
        node.setLeft(leftRotation(node.getLeft()));
        AVLNode<T> dummy = rightRotation(node);
        return dummy;
    }

    /**
     * Removes and returns the element from the tree matching the given
     * parameter.
     *
     * There are 3 cases to consider:
     * 1: The node containing the data is a leaf (no children). In this case,
     *    simply remove it.
     * 2: The node containing the data has one child. In this case, simply
     *    replace it with its child.
     * 3: The node containing the data has 2 children. Use the predecessor to
     *    replace the data, NOT successor. As a reminder, rotations can occur
     *    after removing the predecessor node.
     *
     * Remember to recalculate heights and balance factors while going back
     * up the tree after removing the element, making sure to rebalance if
     * necessary.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to remove
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if the data is null
     * @throws java.util.NoSuchElementException   if the data is not found
     */
    public T remove(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you provided is null.");
        }
        AVLNode<T> dummy = new AVLNode<>(null);
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
    private AVLNode<T> rRemove(T data, AVLNode<T> curr, AVLNode<T> dummy) {
        if (curr == null) {
            throw new NoSuchElementException("The data you tried to remove does not exist in the BST.");
        } else if (data.compareTo(curr.getData()) < 0) {
            curr.setLeft(rRemove(data, curr.getLeft(), dummy));
            if (curr.getRight() == null && curr.getLeft() == null) {
                curr.setBalanceFactor(0);
                curr.setHeight(0);
            } else if (curr.getLeft() == null) {
                curr.setHeight(curr.getRight().getHeight() + 1);
                if (-1 - curr.getRight().getHeight() < -1) {
                    if (curr.getRight().getBalanceFactor() > 0) {
                        RightLeft(curr);
                    } else {
                        leftRotation(curr);
                    }
                } else {
                    curr.setBalanceFactor(-1 - curr.getRight().getHeight());
                }
            } else if (curr.getRight() == null) {
                curr.setHeight(curr.getLeft().getHeight() + 1);
                if (curr.getLeft().getHeight() + 1 > 1) {
                    if (curr.getLeft().getBalanceFactor() < 0) {
                        LeftRight(curr);
                    } else {
                        rightRotation(curr);
                    }
                } else {
                    curr.setBalanceFactor(curr.getLeft().getHeight() + 1);
                }
            } else {
                curr.setHeight((curr.getRight().getHeight() > curr.getLeft().getHeight() ?
                        curr.getRight().getHeight() + 1:curr.getLeft().getHeight() + 1));
                if (curr.getLeft().getHeight() - curr.getRight().getHeight() > 1) {
                    if (curr.getLeft().getBalanceFactor() < 0) {
                        LeftRight(curr);
                    } else {
                        rightRotation(curr);
                    }
                } else if (curr.getLeft().getBalanceFactor() - curr.getRight().getBalanceFactor() < -1) {
                    if (curr.getRight().getBalanceFactor() > 0) {
                        RightLeft(curr);
                    } else {
                        leftRotation(curr);
                    }
                } else {
                    curr.setBalanceFactor(curr.getLeft().getHeight() - curr.getRight().getHeight());
                }
            }
        } else if (data.compareTo(curr.getData()) > 0) {
            curr.setRight(rRemove(data, curr.getRight(), dummy));
            if (curr.getRight() == null && curr.getLeft() == null) {
                curr.setBalanceFactor(0);
                curr.setHeight(0);
            } else if (curr.getLeft() == null) {
                curr.setHeight(curr.getRight().getHeight() + 1);
                if (-1 - curr.getRight().getHeight() < -1) {
                    if (curr.getRight().getBalanceFactor() > 0) {
                        RightLeft(curr);
                    } else {
                        leftRotation(curr);
                    }
                } else {
                    curr.setBalanceFactor(-1 - curr.getRight().getHeight());
                }
            } else if (curr.getRight() == null) {
                curr.setHeight(curr.getLeft().getHeight() + 1);
                if (curr.getLeft().getHeight() + 1 > 1) {
                    if (curr.getLeft().getBalanceFactor() < 0) {
                        LeftRight(curr);
                    } else {
                        rightRotation(curr);
                    }
                } else {
                    curr.setBalanceFactor(curr.getLeft().getHeight() + 1);
                }
            }
            else {
                curr.setHeight((curr.getRight().getHeight() > curr.getLeft().getHeight() ?
                        curr.getRight().getHeight() + 1:curr.getLeft().getHeight() + 1));
                if (curr.getLeft().getHeight() - curr.getRight().getHeight() > 1) {
                    if (curr.getLeft().getBalanceFactor() < 0) {
                        LeftRight(curr);
                    } else {
                        rightRotation(curr);
                    }
                } else if (curr.getLeft().getBalanceFactor() - curr.getRight().getBalanceFactor() < -1) {
                    if (curr.getRight().getBalanceFactor() > 0) {
                        RightLeft(curr);
                    } else {
                        leftRotation(curr);
                    }
                } else {
                    curr.setBalanceFactor(curr.getLeft().getHeight() - curr.getRight().getHeight());
                }
            }
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
                AVLNode<T> dummy2 = new AVLNode<>(null);
                curr.setLeft(removePredecessor(curr.getLeft(), dummy2));
                curr.setData(dummy2.getData());

                if (curr.getLeft() == null) {

                    curr.setHeight(curr.getRight().getHeight() + 1);
                    if (-1 - curr.getRight().getHeight() < -1) {
                        if (curr.getRight().getBalanceFactor() > 0) {
                            RightLeft(curr);
                        } else {
                            leftRotation(curr);
                        }
                    } else {
                        curr.setBalanceFactor(-1 - curr.getRight().getHeight());
                    }
                } else {
                    curr.setHeight((curr.getRight().getHeight() > curr.getLeft().getHeight() ?
                            curr.getRight().getHeight() + 1:curr.getLeft().getHeight() + 1));
                    if (curr.getLeft().getHeight() - curr.getRight().getHeight() > 1) {
                        if (curr.getLeft().getBalanceFactor() < 0) {
                            LeftRight(curr);
                        } else {
                            rightRotation(curr);
                        }
                    } else if (curr.getLeft().getBalanceFactor() - curr.getRight().getBalanceFactor() < -1) {
                        if (curr.getRight().getBalanceFactor() > 0) {
                            RightLeft(curr);
                        } else {
                            leftRotation(curr);
                        }
                    } else {
                        curr.setBalanceFactor(curr.getLeft().getHeight() - curr.getRight().getHeight());
                    }
                }
            }
        }
        return curr;
    }
    /**
     * Removes predecessor from the BST.
     * @param curr current node (first node passed in is the first node to the left of the node that is to be removed)
     * @param dummy2 A dummy node to hold the successors data
     * @return BSTNode
     */
    private AVLNode<T> removePredecessor(AVLNode<T> curr, AVLNode<T> dummy2) {
        if (curr.getRight() == null) {
            dummy2.setData(curr.getData());

            return curr.getRight();
        } else {
            curr.setRight(removePredecessor(curr.getRight(), dummy2));

            if (curr.getRight() == null && curr.getLeft() == null) {
                curr.setBalanceFactor(0);
                curr.setHeight(0);
            } else if (curr.getLeft() == null) {
                curr.setHeight(curr.getRight().getHeight() + 1);
                if (-1 - curr.getRight().getHeight() < -1) {
                    if (curr.getRight().getBalanceFactor() > 0) {
                        RightLeft(curr);
                    } else {
                        leftRotation(curr);
                    }
                } else {
                    curr.setBalanceFactor(-1 - curr.getRight().getHeight());
                }
            } else if (curr.getRight() == null) {
                curr.setHeight(curr.getLeft().getHeight() + 1);
                if (curr.getLeft().getHeight() + 1 > 1) {
                    if (curr.getLeft().getBalanceFactor() < 0) {
                        LeftRight(curr);
                    } else {
                        rightRotation(curr);
                    }
                } else {
                    curr.setBalanceFactor(curr.getLeft().getHeight() + 1);
                }
            } else {
                curr.setHeight((curr.getRight().getHeight() > curr.getLeft().getHeight() ?
                        curr.getRight().getHeight() + 1:curr.getLeft().getHeight() + 1));
                if (curr.getLeft().getHeight() - curr.getRight().getHeight() > 1) {
                    if (curr.getLeft().getBalanceFactor() < 0) {
                        LeftRight(curr);
                    } else {
                        rightRotation(curr);
                    }
                } else if (curr.getLeft().getBalanceFactor() - curr.getRight().getBalanceFactor() < -1) {
                    if (curr.getRight().getBalanceFactor() > 0) {
                        RightLeft(curr);
                    } else {
                        leftRotation(curr);
                    }
                } else {
                    curr.setBalanceFactor(curr.getLeft().getHeight() - curr.getRight().getHeight());
                }
            }
        }
        return curr;
    }

    /**
     * Returns the element from the tree matching the given parameter.
     *
     * Hint: Should you use value equality or reference equality?
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the tree.
     *
     * @param data the data to search for in the tree
     * @return the data in the tree equal to the parameter
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if the data is not in the tree
     */
    public T get(T data) {
        if (data == null) {
            throw new NoSuchElementException("The data you provided is null.");
        }
        AVLNode<T> curr = root;
        curr = rGet(data, curr);
        return curr.getData();
    }

    /**
     * Private helper for get method
     * @param data data to get
     * @param curr current node (starts at root)
     * @return node with data to get
     */
    private AVLNode<T> rGet(T data, AVLNode<T> curr) {
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
     * Hint: Should you use value equality or reference equality?
     *
     * @param data the data to search for in the tree
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
     * Private helper for contains
     * @param data data to search for
     * @param curr current node (starts at root)
     * @return boolean representing whether the AVL contains the data
     */
    private boolean rContains(T data, AVLNode<T> curr) {
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
     * Returns the height of the root of the tree. Do NOT compute the height
     * recursively. This method should be O(1).
     *
     * @return the height of the root of the tree, -1 if the tree is empty
     */
    public int height() {
        if (size == 0) {
            return -1;
        } else {
            return root.getHeight();
        }
    }

    /**
     * Clears the tree.
     *
     * Clears all data and resets the size.
     */
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Find a path of letters in the tree that spell out a particular word,
     * if the path exists.
     *
     * Ex: Given the following AVL
     *
     *                   g
     *                 /   \
     *                e     i
     *               / \   / \
     *              b   f h   n
     *             / \         \
     *            a   c         u
     *
     * wordSearch([b, e, g, i, n]) returns the list [b, e, g, i, n],
     * where each letter in the returned list is from the tree and not from
     * the word array.
     *
     * wordSearch([h, i]) returns the list [h, i], where each letter in the
     * returned list is from the tree and not from the word array.
     *
     * wordSearch([a]) returns the list [a].
     *
     * wordSearch([]) returns an empty list [].
     *
     * wordSearch([h, u, g, e]) throws NoSuchElementException. Although all
     * 4 letters exist in the tree, there is no path that spells 'huge'.
     * The closest we can get is 'hige'.
     *
     * To do this, you must first find the deepest common ancestor of the
     * first and last letter in the word. Then traverse to the first letter
     * while adding letters on the path to the list while preserving the order
     * they appear in the word (consider adding to the front of the list).
     * Finally, traverse to the last letter while adding its ancestor letters to
     * the back of the list. Please note that there is no relationship between
     * the first and last letters, in that they may not belong to the same
     * branch. You will most likely have to split off to traverse the tree when
     * searching for the first and last letters.
     *
     * You may only use 1 list instance to complete this method. Think about
     * what type of list to use since you may have to add to the front and
     * back of the list.
     *
     * You will only need to traverse to the deepest common ancestor once.
     * From that node, go to the first and last letter of the word in one
     * traversal each. Failure to do so will result in a efficiency penalty.
     * Validating the path against the word array efficiently after traversing
     * the tree will NOT result in an efficiency penalty.
     *
     * If there exists a path between the first and last letters of the word,
     * there will only be 1 valid path.
     *
     * You may assume that the word will not contain duplicate letters.
     *
     * WARNING: Do not return letters from the passed-in word array!
     * If a path exists, the letters should be retrieved from the tree.
     * Returning any letter from the word array will result in a penalty!
     *
     * @param word array of T, where each element represents a letter in the
     * word (in order).
     * @return list containing the path of letters in the tree that spell out
     * the word, if such a path exists. Order matters! The ordering of the
     * letters in the returned list should match that of the word array.
     * @throws java.lang.IllegalArgumentException if the word array is null
     * @throws java.util.NoSuchElementException if the path is not in the tree
     */
    public List<T> wordSearch(T[] word) {
        if (word == null) {
            throw new IllegalArgumentException("The word array is null.");
        } else if (word.length == 0) {
            return new LinkedList<T>();
        }
        LinkedList<T> list = new LinkedList<>();
        rWord(root, list, word);
        return list;
    }
    private void rWord(AVLNode<T> curr, LinkedList<T> list, T[] word) {
        if (curr.getData().compareTo(word[0]) > 0 && curr.getData().compareTo(word[word.length - 1]) > 0) {
            rWord(curr.getLeft(), list, word);
        } else if (curr.getData().compareTo(word[0]) < 0 && curr.getData().compareTo(word[word.length - 1]) < 0) {
            rWord(curr.getRight(), list, word);
        } else {
            list.add(curr.getData());
            if (word.length > 1 && curr.getData().compareTo(word[0]) != 0 && curr.getData().compareTo(word[word.length - 1]) != 0) {
                DCAFront(curr, list, word);
                DCABack(curr, list, word);
            } else if (word.length > 1 && curr.getData().compareTo(word[0]) == 0) {
                DCABack(curr, list, word);
            } else if (word.length > 1) {
                DCAFront(curr, list, word);
            }
        }
    }

    private void DCAFront(AVLNode<T> curr, LinkedList<T> list, T[] word) {
        if (curr.getData().compareTo(word[0]) > 0) {
            boolean trip = false;
            for (T wordd : word) {
                if (wordd.compareTo(curr.getData()) == 0) {
                    trip = true;
                }
            }
            if (trip == false) {
                throw new NoSuchElementException("The word you are searching for does not exist");
            }
            list.addFirst(curr.getLeft().getData());
            DCAFront(curr.getLeft(), list, word);
        } else if (curr.getData().compareTo(word[0]) < 0) {
            boolean trip = false;
            for (T wordd : word) {
                if (wordd.compareTo(curr.getData()) == 0) {
                    trip = true;
                }
            }
            if (trip == false) {
                throw new NoSuchElementException("The word you are searching for does not exist");
            }
            list.addFirst(curr.getRight().getData());
            DCAFront(curr.getRight(), list, word);
        }
    }
    private void DCABack(AVLNode<T> curr, LinkedList<T> list, T[] word) {
        if (curr.getData().compareTo(word[word.length - 1]) > 0) {
            boolean trip = false;
            for (T wordd : word) {
                if (wordd.compareTo(curr.getData()) == 0) {
                    trip = true;
                }
            }
            if (trip == false) {
                throw new NoSuchElementException("The word you are searching for does not exist");
            }
            list.addLast(curr.getLeft().getData());
            DCABack(curr.getLeft(), list, word);
        } else if (curr.getData().compareTo(word[word.length - 1]) < 0) {
            boolean trip = false;
            for (T wordd : word) {
                if (wordd.compareTo(curr.getData()) == 0) {
                    trip = true;
                }
            }
            if (trip == false) {
                throw new NoSuchElementException("The word you are searching for does not exist");
            }
            list.addLast(curr.getRight().getData());
            DCABack(curr.getRight(), list, word);
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
    public AVLNode<T> getRoot() {
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
