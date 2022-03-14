import java.util.NoSuchElementException;

/**
 * Your implementation of a non-circular DoublyLinkedList with a tail pointer.
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
public class DoublyLinkedList<T> {

    // Do not add new instance variables or modify existing ones.
    private DoublyLinkedListNode<T> head;
    private DoublyLinkedListNode<T> tail;
    private int size;

    // Do not add a constructor.

    /**
     * Adds the element to the specified index. Don't forget to consider whether
     * traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size and O(n) for all other cases.
     *
     * @param index the index at which to add the new element
     * @param data  the data to add at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index > size
     * @throws java.lang.IllegalArgumentException  if data is null
     */
    public void addAtIndex(int index, T data) {
        if (index == 0) {
            addToFront(data);
        } else if (index == size) {
            addToBack(data);
        } else if (index < 0 || index > size()) {
            throw new IndexOutOfBoundsException("The index you provided was not"
                    + " within bounds of the DoublyLinkedList.");
        } else if (data == null) {
            throw new IllegalArgumentException("The data you provided was null. Add your data.");
        } else if (index < size / 2) {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode(data);
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            newNode.setNext(curr.getNext());
            newNode.setPrevious(curr);
            curr.getNext().setPrevious(newNode);
            curr.setNext(newNode);
            size++;
        } else {
            DoublyLinkedListNode<T> newNode = new DoublyLinkedListNode(data);
            DoublyLinkedListNode<T> curr = tail;
            for (int i = size - 1; i > index - 1; i--) {
                curr = curr.getPrevious();
            }
            newNode.setNext(curr.getNext());
            newNode.setPrevious(curr);
            curr.getNext().setPrevious(newNode);
            curr.setNext(newNode);
            size++;
        }
    }

    /**
     * Adds the element to the front of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the front of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToFront(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you provided is null. Add your data.");
        } else {
            head = new DoublyLinkedListNode<T>(data, null, head);
            if (size >= 1) {
                head.getNext().setPrevious(head);
            } else {
                tail = head;
            }
            size++;
        }
    }

    /**
     * Adds the element to the back of the list.
     *
     * Must be O(1).
     *
     * @param data the data to add to the back of the list
     * @throws java.lang.IllegalArgumentException if data is null
     */
    public void addToBack(T data) {
        if (data == null) {
            throw new IllegalArgumentException("The data you provided is null. Add your data.");
        } else {
            tail = new DoublyLinkedListNode<>(data, tail, null);
            if (size >= 1) {
                tail.getPrevious().setNext(tail);
            } else {
                head = tail;
            }
            size++;
        }
    }

    /**
     * Removes and returns the element at the specified index. Don't forget to
     * consider whether traversing the list from the head or tail is more
     * efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to remove
     * @return the data formerly located at the specified index
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T removeAtIndex(int index) {
        T dataRemoved = null;
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("The index you provided is not within the bounds of the array.");
        } else if (index == 0) {
            dataRemoved = head.getData();
            removeFromFront();
        } else if (index == size - 1) {
            dataRemoved = tail.getData();
            removeFromBack();
        } else if (index < size / 2) {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index - 1; i++) {
                curr = curr.getNext();
            }
            dataRemoved = curr.getNext().getData();
            curr.getNext().getNext().setPrevious(curr);
            curr.setNext(curr.getNext().getNext());
            size--;
        } else {
            DoublyLinkedListNode<T> curr = tail;
            for (int i = size - 1; i > index - 1; i--) {
                curr = curr.getPrevious();
            }
            dataRemoved = curr.getNext().getData();
            curr.getNext().getNext().setPrevious(curr);
            curr.setNext(curr.getNext().getNext());
            size--;
        }
        return dataRemoved;
    }

    /**
     * Removes and returns the first element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the front of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromFront() {
        if (isEmpty()) {
            throw new NoSuchElementException("There is nothing to remove, the list is empty.");
        }
        T dataRemoved = null;
        if (size == 1) {
            dataRemoved = head.getData();
            head = null;
            tail = null;
            size--;
        } else {
            dataRemoved = head.getData();
            head = head.getNext();
            head.setPrevious(null);
            size--;
        }
        return dataRemoved;
    }

    /**
     * Removes and returns the last element of the list.
     *
     * Must be O(1).
     *
     * @return the data formerly located at the back of the list
     * @throws java.util.NoSuchElementException if the list is empty
     */
    public T removeFromBack() {
        if (isEmpty()) {
            throw new NoSuchElementException("There is nothing to remove, the list is empty.");
        }
        T dataRemoved = null;
        if (size == 1) {
            dataRemoved = tail.getData();
            tail = null;
            head = null;
        } else {
            dataRemoved = tail.getData();
            tail = tail.getPrevious();
            tail.setNext(null);
        }

        size--;
        return dataRemoved;
    }

    /**
     * Returns the element at the specified index. Don't forget to consider
     * whether traversing the list from the head or tail is more efficient!
     *
     * Must be O(1) for indices 0 and size - 1 and O(n) for all other cases.
     *
     * @param index the index of the element to get
     * @return the data stored at the index in the list
     * @throws java.lang.IndexOutOfBoundsException if index < 0 or index >= size
     */
    public T get(int index) {
        T dataToGet = null;
        if (index > size - 1 || index < 0) {
            throw new IndexOutOfBoundsException("The index you tried to get is not within bounds of the list.");
        } else if (index < size / 2) {
            DoublyLinkedListNode<T> curr = head;
            for (int i = 0; i < index; i++) {
                curr = curr.getNext();
            }
            dataToGet = curr.getData();

        } else {
            DoublyLinkedListNode<T> curr = tail;
            for (int i = size - 1; i > index; i--) {
                curr = curr.getPrevious();
            }
            dataToGet = curr.getData();
        }
        return dataToGet;
    }

    /**
     * Returns whether or not the list is empty.
     *
     * Must be O(1).
     *
     * @return true if empty, false otherwise
     */
    public boolean isEmpty() {
        return head == null;
    }

    /**
     * Clears the list.
     *
     * Clears all data and resets the size.
     *
     * Must be O(1).
     */
    public void clear() {
        head = null;
        tail = null;
        size = 0;
    }

    /**
     * Removes and returns the last copy of the given data from the list.
     *
     * Do not return the same data that was passed in. Return the data that
     * was stored in the list.
     *
     * Must be O(1) if data is in the tail and O(n) for all other cases.
     *
     * @param data the data to be removed from the list
     * @return the data that was removed
     * @throws java.lang.IllegalArgumentException if data is null
     * @throws java.util.NoSuchElementException   if data is not found
     */
    public T removeLastOccurrence(T data) {
        if (data == null) {
            throw new IllegalArgumentException("Can't search for null data. Please provide actual data.");
        }
        DoublyLinkedListNode<T> curr = head;
        T dataRemoved = null;
        int count = 0;
        int index = -1;
        while (curr != null) {
            if (curr.getData().equals(data)) {
                dataRemoved = curr.getData();
                index = count;
            }
            count++;
            curr = curr.getNext();
        }
        if (index == -1) {
            throw new NoSuchElementException("The data was not found in the list.");
        } else {
            removeAtIndex(index);
        }
        return dataRemoved;
    }

    /**
     * Returns an array representation of the linked list.
     *
     * Must be O(n) for all cases.
     *
     * @return an array of length size holding all of the objects in the
     * list in the same order
     */
    public Object[] toArray() {
        DoublyLinkedListNode<T> curr = head;
        Object[] array = new Object[size];
        for (int i = 0; i < size; i++) {
            array[i] = curr.getData();
            curr = curr.getNext();
        }
        return array;
    }

    /**
     * Returns the head node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the head of the list
     */
    public DoublyLinkedListNode<T> getHead() {
        // DO NOT MODIFY!
        return head;
    }

    /**
     * Returns the tail node of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the node at the tail of the list
     */
    public DoublyLinkedListNode<T> getTail() {
        // DO NOT MODIFY!
        return tail;
    }

    /**
     * Returns the size of the list.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return the size of the list
     */
    public int size() {
        // DO NOT MODIFY!
        return size;
    }
}
