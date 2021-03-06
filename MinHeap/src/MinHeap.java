import java.util.ArrayList;
import java.util.NoSuchElementException;

/**
 * Your implementation of a MinHeap.
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
public class MinHeap<T extends Comparable<? super T>> {

    /**
     * The initial capacity of the MinHeap when created with the default
     * constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new MinHeap.
     *
     * The backing array should have an initial capacity of INITIAL_CAPACITY.
     */
    public MinHeap() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
    }

    /**
     * Creates a properly ordered heap from a set of initial values.
     *
     * You must use the BuildHeap algorithm that was taught in lecture! Simply
     * adding the data one by one using the add method will not get any credit.
     * As a reminder, this is the algorithm that involves building the heap
     * from the bottom up by repeated use of downHeap operations.
     *
     * Before doing the algorithm, first copy over the data from the
     * ArrayList to the backingArray (leaving index 0 of the backingArray
     * empty). The data in the backingArray should be in the same order as it
     * appears in the passed in ArrayList before you start the BuildHeap
     * algorithm.
     *
     * The backingArray should have capacity 2n + 1 where n is the
     * number of data in the passed in ArrayList (not INITIAL_CAPACITY).
     * Index 0 should remain empty, indices 1 to n should contain the data in
     * proper order, and the rest of the indices should be empty.
     *
     * @param data a list of data to initialize the heap with
     * @throws java.lang.IllegalArgumentException if data or any element in data
     *                                            is null
     */
    public MinHeap(ArrayList<T> data) {
        if (data == null) {
            throw new IllegalArgumentException("The list you provided is null.");
        }
        backingArray = (T[]) new Comparable[2 * data.size() + 1];
        for (int i = 0; i < data.size(); i++) {
            if (data.get(i) == null) {
                throw new IllegalArgumentException("The list you provided contains null data.");
            }
            backingArray[i + 1] = data.get(i);
        }
        size = data.size();
        int index = size / 2;
        for (int i = index; i > 0; i--) {
            downheap(i);
        }

    }

    /**
     * Adds an item to the heap. If the backing array is full (except for
     * index 0) and you're trying to add a new item, then double its capacity.
     *
     * @param data the data to add
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void add(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you provided is null.");
        } else if (size + 1 == backingArray.length) {
            T[] copyArray = (T[]) new Comparable[(size + 1) * 2];
            for (int i = 1; i <= size; i++) {
                copyArray[i] = backingArray[i];
            }
            backingArray = copyArray;
        }
        int index = size + 1;
        backingArray[index] = data;
        if (index > 1) {
            upheap(index);
        }
        size++;
    }

    /**
     * Private heapify/upheap method
     * @param index int of the index of the newly added data
     */
    private void upheap(int index) {
        if (backingArray[index].compareTo(backingArray[(index) / 2]) < 0) {
            T dummy = backingArray[index];
            backingArray[index] = backingArray[index / 2];
            backingArray[index / 2] = dummy;
            if (index / 2 > 1) {
                upheap(index / 2);
            }
        }
    }

    /**
     * Removes and returns the min item of the heap. As usual for array-backed
     * structures, be sure to null out spots as you remove. Do not decrease the
     * capacity of the backing array.
     *
     * @return the data that was removed
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T remove() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty.");
        }
        T dataToReturn = null;
        if (size == 1) {
            dataToReturn = backingArray[1];
            backingArray[1] = null;
        } else {
            dataToReturn = backingArray[1];
            backingArray[1] = backingArray[size];
            backingArray[size] = null;
            downheap(1);
        }
        size--;
        return dataToReturn;
    }

    /**
     * Private down heap method
     * @param index int of index to downheap at - always starts at the root
     */
    private void downheap(int index) {
        if (backingArray.length > index * 2) {
            if (backingArray.length > index * 2 + 1) {
                if (backingArray[index * 2 + 1] != null) {
                    if (backingArray[index].compareTo(backingArray[index * 2]) > 0
                            || backingArray[index].compareTo(backingArray[index * 2 + 1]) > 0) {
                        T dummy = backingArray[index];
                        if (backingArray[index * 2].compareTo(backingArray[index * 2 + 1]) < 0) {
                            backingArray[index] = backingArray[index * 2];
                            backingArray[index * 2] = dummy;
                            downheap(index * 2);
                        } else {
                            backingArray[index] = backingArray[index * 2 + 1];
                            backingArray[index * 2 + 1] = dummy;
                            downheap(index * 2 + 1);
                        }
                    }
                } else {
                    if (backingArray[index * 2] != null) {
                        if (backingArray[index * 2].compareTo(backingArray[index]) < 0) {
                            T dummy = backingArray[index];
                            backingArray[index] = backingArray[index * 2];
                            backingArray[index * 2] = dummy;
                            downheap(index * 2);
                        }
                    }
                }
            }
        }
    }

    /**
     * Returns the minimum element in the heap.
     *
     * @return the minimum element
     * @throws java.util.NoSuchElementException if the heap is empty
     */
    public T getMin() {
        if (size == 0) {
            throw new NoSuchElementException("The heap is empty.");
        }
        return backingArray[1];
    }

    /**
     * Returns whether or not the heap is empty.
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Clears the heap.
     *
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     */
    public void clear() {
        backingArray = (T[]) new Comparable[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the backing array of the list
     */
    public T[] getBackingArray() {
        // DO NOT MODIFY THIS METHOD!
        return backingArray;
    }

    /**
     * Returns the size of the heap.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }
}
