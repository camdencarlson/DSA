import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Camden Carlson
 * @version 1.0
 * @userid XXXXXXXX
 * @GTID XXXXXXXX
 *
 * Collaborators: LIST ALL COLLABORATORS YOU WORKED WITH HERE
 *
 * Resources: LIST ALL NON-COURSE RESOURCES YOU CONSULTED HERE
 */

public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator you provided is null.");
        }
        for (int n = 1; n < arr.length; n++) {
            int i = n;
            while (i != 0 && comparator.compare(arr[i - 1], arr[i]) > 0) {
                T dummy = arr[i];
                arr[i] = arr[i - 1];
                arr[i - 1] = dummy;
                i--;
            }
        }
    }

    /**
     * Implement cocktail sort.
     *
     * It should be:
     * in-place
     * stable
     * adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n)
     *
     * NOTE: See pdf for last swapped optimization for cocktail sort. You
     * MUST implement cocktail sort with this optimization
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void cocktailSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator you provided is null.");
        }
        boolean swapsMade = true;
        int startInd = 0;
        int s = 0;
        int endInd = arr.length - 1;
        int e = arr.length - 1;
        while (swapsMade) {
            swapsMade = false;
            s = startInd;
            for (int i = s; i < e; i++) {
                if (comparator.compare(arr[i], arr[i + 1]) > 0) {
                    T dummy = arr[i];
                    arr[i] = arr[i + 1];
                    arr[i + 1] = dummy;
                    swapsMade = true;
                    endInd = i;
                }
            }
            e = endInd;
            if (swapsMade) {
                swapsMade = false;
                for (int i = e; i > s; i--) {
                    if (comparator.compare(arr[i - 1], arr[i]) > 0) {
                        T dummy = arr[i];
                        arr[i] = arr[i - 1];
                        arr[i - 1] = dummy;
                        swapsMade = true;
                        startInd = i;
                    }
                }
            }
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n log n)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * You can create more arrays to run merge sort, but at the end, everything
     * should be merged back into the original T[] which was passed in.
     *
     * When splitting the array, if there is an odd number of elements, put the
     * extra data on the right side.
     *
     * Hint: If two data are equal when merging, think about which subarray
     * you should pull from first
     *
     * @param <T>        data type to sort
     * @param arr        the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     * @throws java.lang.IllegalArgumentException if the array or comparator is
     *                                            null
     */
    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (arr == null || comparator == null) {
            throw new IllegalArgumentException("The array or comparator you provided is null.");
        }
        if (arr.length <= 1) {
            return;
        } else if (arr.length == 2) {
            if (comparator.compare(arr[0], arr[1]) > 0) {
                T dummy = arr[0];
                arr[0] = arr[1];
                arr[1] = dummy;
            }
            return;
        }
        int length = arr.length;
        int midIndex = length / 2;

        T[] left = (T[]) new Object[midIndex];
        T[] right = (T[]) new Object[length - midIndex];
        for (int i = 0; i < midIndex; i++) {
            left[i] = arr[i];
        }
        for (int i = midIndex; i < arr.length; i++) {
            right[i - midIndex] = arr[i];
        }

        mergeSort(left, comparator);
        mergeSort(right, comparator);

        int i = 0;
        int j = 0;

        while (i < left.length && j < right.length) {
            if (comparator.compare(right[j], left[i]) >= 0) {
                arr[i + j] = left[i];
                i++;
            } else {
                arr[i + j] = right[j];
                j++;
            }
        }
        while (i < left.length) {
            arr[i + j] = left[i];
            i++;
        }
        while (j < right.length) {
            arr[i + j] = right[j];
            j++;
        }
    }

    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not implement the one we have taught you!
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code! Doing so may result in a 0 for the implementation.
     *
     * It should be:
     * out-of-place
     * stable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(kn)
     *
     * And a best case running time of:
     * O(kn)
     *
     * You are allowed to make an initial O(n) passthrough of the array to
     * determine the number of iterations you need.
     *
     * At no point should you find yourself needing a way to exponentiate a
     * number; any such method would be non-O(1). Think about how how you can
     * get each power of BASE naturally and efficiently as the algorithm
     * progresses through each digit.
     *
     * Refer to the PDF for more information on LSD Radix Sort.
     *
     * You may use ArrayList or LinkedList if you wish, but it may only be
     * used inside radix sort and any radix sort helpers. Do NOT use these
     * classes with other sorts. However, be sure the List implementation you
     * choose allows for stability while being as efficient as possible.
     *
     * Do NOT use anything from the Math class except Math.abs().
     *
     * @param arr the array to be sorted
     * @throws java.lang.IllegalArgumentException if the array is null
     */
    public static void lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array you provided is null.");
        }
        LinkedList<Integer>[] buckets = (LinkedList<Integer>[]) new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<Integer>();
        }
        int biggestInt = 0;
        for (int i = 0; i < arr.length; i++) {
            if (Math.abs(arr[i]) > biggestInt) {
                biggestInt = Math.abs(arr[i]);
            }
        }
        int k = 0;
        while (biggestInt != 0) {
            k++;
            biggestInt = biggestInt / 10;
        }

        int dNum = 1;
        for (int j = 1; j <= k; j++) {

            for (int i = 0; i < arr.length; i++) {
                int digit = arr[i];
                digit = (digit / dNum) % 10;
                buckets[digit + 9].add(arr[i]);

//                for (int m = 2; m <= j; m++) { // inefficient, use dNum method
//                    digit = digit / 10;
//                }
            }


            int idx = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[idx] = bucket.removeFirst();
                    idx++;
                }

            }
            dNum *= 10;
        }


    }

    /**
     * Implement quick sort.
     *
     * Use the provided random object to select your pivots. For example if you
     * need a pivot between a (inclusive) and b (exclusive) where b > a, use
     * the following code:
     *
     * int pivotIndex = rand.nextInt(b - a) + a;
     *
     * If your recursion uses an inclusive b instead of an exclusive one,
     * the formula changes by adding 1 to the nextInt() call:
     *
     * int pivotIndex = rand.nextInt(b - a + 1) + a;
     *
     * It should be:
     * in-place
     * unstable
     * not adaptive
     *
     * Have a worst case running time of:
     * O(n^2)
     *
     * And a best case running time of:
     * O(n log n)
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not receive
     * credit if you do not use the one we have taught you!
     *
     * @param <T>        data type to sort
     * @param arr        the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     * @param rand       the Random object used to select pivots
     * @throws java.lang.IllegalArgumentException if the array or comparator or
     *                                            rand is null
     */
    public static <T> void quickSort(T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (arr == null || comparator == null || rand == null) {
            throw new IllegalArgumentException("The array or comparator you provided is null.");
        }
        int start = 0;
        int end = arr.length - 1;
        rQuickSort(arr, start, end, rand, comparator);
    }

    /**
     * Private helper for recursive quicksort
     * @param arr array
     * @param start starting index
     * @param end ending index
     * @param rand Random object
     * @param comparator Comparator object
     * @param <T> Generics
     */
    private static <T> void rQuickSort(T[] arr, int start, int end, Random rand, Comparator<T> comparator) {
        if (end - start < 1) {
            return;
        }
        int pivotIndex = rand.nextInt(end - start + 1) + start;
        T pivotVal = arr[pivotIndex];
        arr[pivotIndex] = arr[start];
        arr[start] = pivotVal;

        int i = start + 1;
        int j = end;
        while (i <= j) {
            while (i <= j && comparator.compare(pivotVal, arr[i]) >= 0) {
                i++;
            }
            while (i <= j && comparator.compare(arr[j], pivotVal) >= 0) {
                j--;
            }
            if (i <= j) {
                T dummy = arr[i];
                arr[i] = arr[j];
                arr[j] = dummy;
                i++;
                j--;
            }
        }
        T dummy = arr[start];
        arr[start] = arr[j];
        arr[j] = dummy;

        rQuickSort(arr, start, j - 1, rand, comparator);
        rQuickSort(arr, j + 1, end, rand, comparator);
    }

}
