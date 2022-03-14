import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.Random;

import java.lang.reflect.Method;
import java.lang.reflect.Field;

import java.util.Arrays;

/**
 * Thank you to everyone who has contributed unit tests thus far! Here's what I have so far.
 *
 * Right now, these tests don't really do much testing against your sorting algorithms
 * but they'll test if you're following the guidelines.
 *
 * @author Jim James
 * @version 1.0
 */
public class SortingJimTest {
    private static final int TIMEOUT = 500;
    private Comparator<Integer> comparator;
    
    @Before
    public void setUp() {
        comparator = Comparator.comparingInt(a -> a);
    }

    /**
     * This test checks to see if you accidentally modified or added any instance fields.
     * If you're failing this test, check your instance field declarations! This homework shouldn't have any.
     */
    @Test(timeout = TIMEOUT)
    public void testInstanceFields() {
       Class sortingClass = Sorting.class;
       Field[] fields = sortingClass.getDeclaredFields();
       Field[] validFields = new Field[0];
       assertArrayEquals(fields, validFields);
    }

    /**
     * This test checks to see if you accidentally made any helper methods public.
     * If you're failing this test, check your access specifiers! All helpers need to be private.
     */
    @Test(timeout = TIMEOUT)
    public void testPublicMethods() {
        Class sortingClass = Sorting.class;
        Method[] methods = sortingClass.getMethods();
        String[] methodStrings = new String[methods.length];
        for (int i = 0; i < methods.length; i++) {
            methodStrings[i] = methods[i].getName();
        }
        String[] validMethodStrings = {
            "mergeSort",
            "insertionSort",
            "cocktailSort",
            "lsdRadixSort",
            "quickSort",
            "wait",
            "wait",
            "wait",
            "equals",
            "toString",
            "hashCode",
            "getClass",
            "notify",
            "notifyAll",
        };
        Arrays.sort(methodStrings);
        Arrays.sort(validMethodStrings);
        assertArrayEquals(methodStrings, validMethodStrings);
    }

    /**
     * This test checks to see if you're handling sorting null arrays properly.
     * If you're failing this test, make sure your check in insertion sort is correct!
     */
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testInsertionNull() {
        Integer[] arr = {4, 2, 1, 3};
        Sorting.insertionSort(null, comparator);
        Sorting.insertionSort(arr, null);
    }

    /**
     * This test checks to see if you're handling sorting null arrays properly.
     * If you're failing this test, make sure your check in cocktail sort is correct!
     */
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testCocktailNull() {
        Integer[] arr = {4, 2, 1, 3};
        Sorting.cocktailSort(null, comparator);
        Sorting.cocktailSort(arr, null);
    }

    /**
     * This test checks to see if you're handling sorting null arrays properly.
     * If you're failing this test, make sure your check in merge sort is correct!
     */
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testMergeNull() {
        Integer[] arr = {4, 2, 1, 3};
        Sorting.mergeSort(null, comparator);
        Sorting.mergeSort(arr, null);
    }

    /**
     * This test checks to see if you're handling sorting null arrays properly.
     * If you're failing this test, make sure your check in LSD radix sort is correct!
     */
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRadixNull() {
        Sorting.lsdRadixSort(null);
    }

    /**
     * This test checks to see if you're handling sorting null arrays properly.
     * If you're failing this test, make sure your check in quicksort is correct!
     */
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testQuickNull() {
        Random random = new Random(1332);
        Integer[] arr = {4, 2, 1, 3};
        Sorting.quickSort(null, comparator, random);
        Sorting.quickSort(arr, null, random);
        Sorting.quickSort(arr, comparator, null);
    }

    /**
     * This tests checks the overflow condition of Math.abs(Integer.MIN_VALUE)
     * Math.abs(Integer.MIN_VALUE) will return Integer.MIN_VALUE, so make sure to handle that!
     */
    @Test(timeout = TIMEOUT)
    public void testRadixOverflow() {
        int[] arr = {Integer.MIN_VALUE, 2, 1, -50};
        int[] original = {Integer.MIN_VALUE, 2, 1, -50};
        Sorting.lsdRadixSort(arr);
        Arrays.sort(original);
        assertArrayEquals(arr, original);
    }
}
