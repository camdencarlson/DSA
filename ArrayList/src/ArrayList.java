import java.util.NoSuchElementException;

/**
 * Your implementation of an ArrayList.
 *
 * @author Camden Carlson
 * @version 1.0
 * @userid ccarlson43
 * @GTID 903550413
 * <p>
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 * <p>
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */
public class ArrayList<T> {

    /**
     * The initial capacity of the ArrayList.
     * <p>
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 9;

    // Do not add new instance variables or modify existing ones.
    private T[] backingArray;
    private int size;

    /**
     * Constructs a new ArrayList.
     * <p>
     * Java does not allow for regular generic array creation, so you will have
     * to cast an Object[] to a T[] to get the generic typing.
     */
    public ArrayList() {
        // We will not be deducted for warnings like these that occur when we don't check if
        // the type is valid for the cast.
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Adds the element to the specified index.
     * <p>
     * Remember that this add may require elements to be shifted.
     * <p>
     * Must be amortized O(1) for index size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("The index is not within the bounds of the array. "
                    + "Try a different index.");
        } else if (data == null) {
            throw new IllegalArgumentException("The data you provided is null. Put in your data.");
        }


        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else {
            T[] copyArray = (T[]) new Object[backingArray.length];
            for (int i = 0; i < size; i++) {
                copyArray[i] = backingArray[i];
            }
            resizeMe();
            backingArray[index] = data;
            for (int i = index; i < size; i++) {
                backingArray[i + 1] = copyArray[i];
            }
            size++;

        }

    }

    /**
     * Adds the element to the front of the list.
     * <p>
     * Remember that this add may require elements to be shifted.
     * <p>
     * Must be O(n).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you provided is null. Put in your data.");
        }
        if (size == 0) {
            backingArray[0] = data;
        } else {
            this.resizeMe();
            T[] copyArray = (T[]) new Object[backingArray.length];
            for (int i = 0; i < size; i++) {
                copyArray[i] = backingArray[i];
            }
            System.out.println();
            backingArray[0] = data;
            for (int i = 0; i < size; i++) {
                backingArray[i + 1] = copyArray[i];
            }
        }
        size++;
    }

    /**
     * Adds the element to the back of the list.
     * <p>
     * Must be amortized O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you provided is null. Put in your data.");
        }
        resizeMe();
        backingArray[size] = data;
        size++;
    }

    /**
     * Removes and returns the element at the specified index.
     * <p>
     * Remember that this remove may require elements to be shifted.
     * <p>
     * Must be O(1) for index size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index is not within the bounds of the array. "
                    + "Try a different index.");
        }
        T dataToRemove = backingArray[index];
        if (index == 0) {
            removeFromFront();
        } else if (index == size - 1) {
            removeFromBack();
        } else {
            backingArray[index] = null;
            for (int i = index + 1; i < size; i++) {
                backingArray[i - 1] = backingArray[i];
            }
            backingArray[size - 1] = null;
            size--;
        }


        return dataToRemove;
    }

    /**
     * Removes and returns the first element of the list.
     * <p>
     * Remember that this remove may require elements to be shifted.
     * <p>
     * Must be O(n).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty, you can't remove something that is not there.");
        }
        T dataToRemove = backingArray[0];
        for (int i = 0; i < size; i++) {
            backingArray[i] = backingArray[i + 1];
        }
        backingArray[size - 1] = null;
        size--;
        return dataToRemove;
    }

    /**
     * Removes and returns the last element of the list.
     * <p>
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (size == 0) {
            throw new NoSuchElementException("The list is empty, you can't remove something that is not there.");
        }
        T dataToRemove;
        dataToRemove = backingArray[size - 1];
        backingArray[size - 1] = null;
        size--;
        return dataToRemove;
    }

    /**
     * Returns the element at the specified index.
     * <p>
     * Must be O(1).
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index is not within the bounds of the array. "
                    + "Try a different index.");
        }
        return backingArray[index];
    }

    /**
     * Returns whether or not the list is empty.
     * <p>
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return backingArray[0] == null;
    }

    /**
     * Clears the list.
     * <p>
     * Resets the backing array to a new array of the initial capacity and
     * resets the size.
     * <p>
     * Must be O(1).
     */
    public void clear() {
        backingArray = (T[]) new Object[INITIAL_CAPACITY];
        size = 0;
    }

    /**
     * Returns the backing array of the list.
     * <p>
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
     * Returns the size of the list.
     * <p>
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    /**
     * This is a helper method that will resize the array if the size of the array is
     * greater than or equal to the the length of the backing array. It increases the
     * array's size by twice it's initial, as stated in the HW description.
     */
    public void resizeMe() {
        if (size >= backingArray.length) {
            // We will not be deducted for warnings like these.
            T[] copyArray = (T[]) new Object[backingArray.length];
            for (int i = 0; i < size; i++) {
                copyArray[i] = backingArray[i];
            }
            backingArray = (T[]) new Object[backingArray.length * 2];
            for (int i = 0; i < size; i++) {
                backingArray[i] = copyArray[i];
            }
        }
    }
}
