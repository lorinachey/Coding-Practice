import java.util.Comparator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Your implementation of various sorting algorithms.
 *
 * @author Lorin Achey
 * @version 1.0
 */
public class Sorting {

    /**
     * Implement insertion sort.
     *
     * It should be:
     *  in-place
     *  stable
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable).
     *
     * See the PDF for more info on this sort.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array that must be sorted after the method runs
     * @param comparator the Comparator used to compare the data in arr
     */
    public static <T> void insertionSort(T[] arr, Comparator<T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null.");
        } else if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null.");
        }

        for (int i = 1; i < arr.length; i++) {
            int j = i;
            while (j > 0) {
                int intReturnedByCompare =
                        comparator.compare(arr[j - 1], arr[j]);
                if (intReturnedByCompare > 0) {
                    T temp = arr[j - 1];
                    arr[j - 1] = arr[j];
                    arr[j] = temp;
                } else {
                    j = 0;
                }
                j--;
            }

        }
    }

    /**
     * Implement kth select.
     *
     * Use the provided random object to select your pivots.
     * For example if you need a pivot between a (inclusive)
     * and b (exclusive) where b > a, use the following code:
     *
     * int pivotIndex = r.nextInt(b - a) + a;
     *
     * It should be:
     *  in-place
     *
     * Have a worst case running time of:
     *  O(n^2)
     *
     * And a best case running time of:
     *  O(n)
     *
     * Note that there may be duplicates in the array.
     *
     * Make sure you code the algorithm as you have been taught it in class.
     * There are several versions of this algorithm and you may not get full
     * credit if you do not use the one we have taught you!
     *
     * @throws IllegalArgumentException if the array or comparator or rand is
     * null or k is not in the range of 1 to arr.length
     * @param <T> data type to sort
     * @param k the index + 1 (due to 0-indexing) to retrieve the data 
     * from as if the array were sorted; the 'k' in "kth select"
     * @param arr the array that should be modified after the method
     * is finished executing as needed
     * @param comparator the Comparator used to compare the data in arr
     * @param rand the Random object used to select pivots
     * @return the kth smallest element
     */
    public static <T> T kthSelect(int k, T[] arr, Comparator<T> comparator,
                                     Random rand) {
        if (rand == null || comparator == null) {
            throw new IllegalArgumentException("Cannot have"
                     + " null rand object or null comparator.");
        } else if (k < 1 || k > arr.length) {
            throw new IllegalArgumentException("K must be between 1 and "
                    + arr.length);
        }

        int leftBound = 0;
        int rightBound = arr.length;

        T element = quickSelect(k, arr, comparator,
                rand, leftBound, rightBound);
        return element;
    }

    /**
     * Method optimizes the selection of the kth smallest element.
     * @param k represents the index+1 of the item to be found
     * @param arr original array with all the data
     * @param comparator the way the data is compared
     * @param rand generator for pivot
     * @param leftBound the left bound of the array
     * @param rightBound the right bound of the array
     * @param <T> the data to be sorted
     * @return returns the data at the index+1
     */
    private static <T> T quickSelect(int k, T[] arr, Comparator<T> comparator,
                                   Random rand, int leftBound, int rightBound) {
        int pivotIndex = rand.nextInt(rightBound - leftBound) + leftBound;

        //swap the pivot to the front of the array
        T temp = arr[leftBound];
        arr[leftBound] = arr[pivotIndex];
        arr[pivotIndex] = temp;

        //declare and initialize pointers for the while loop
        int i = leftBound + 1;
        int j = rightBound - 1;

        while (i <= j) {
            while (i <= j && comparator.compare(arr[i], arr[leftBound]) <= 0) {
                i++;
            }
            while (j >= i && comparator.compare(arr[j], arr[leftBound]) >= 0) {
                j--;
            }
            if (i <= j) { //the condition if i and j can't move
                T temp2 = arr[i];
                arr[i] = arr[j];
                arr[j] = temp2;
                i++;
                j--;
            }
        }

        T temp3 = arr[leftBound];
        arr[leftBound] = arr[j];
        arr[j] = temp3;

        if (k - 1 == j) {
            return arr[j];
        } else if (k - 1 > j) {
            return (quickSelect(k, arr, comparator, rand, j + 1, rightBound));
        } else {
            return (quickSelect(k, arr, comparator, rand, leftBound, j));
        }
    }

    /**
     * Implement merge sort.
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(n log n)
     *
     * And a best case running time of:
     *  O(n log n)
     *
     * You can create more arrays to run mergesort, but at the end,
     * everything should be merged back into the original T[]
     * which was passed in.
     * 
     * When necessary due to an odd number of elements, the 
     * excess element MUST go on the right side!
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting.
     *
     * @throws IllegalArgumentException if the array or comparator is null
     * @param <T> data type to sort
     * @param arr the array to be sorted
     * @param comparator the Comparator used to compare the data in arr
     */

    public static <T> void mergeSort(T[] arr, Comparator<T> comparator) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null.");
        } else if (arr == null) {
            throw new IllegalArgumentException("Array cannot be null.");
        }
        if (arr.length > 1) {
            int middleIndex = arr.length / 2;

            T[] leftArray = (T[]) new Object[middleIndex];
            for (int i = 0; i < leftArray.length; i++) {
                leftArray[i] = arr[i];
            }

            T[] rightArray = (T[]) new Object[arr.length - middleIndex];
            int index = middleIndex;
            for (int i = 0; i < rightArray.length; i++) {
                rightArray[i] = arr[index];
                index++;
            }

            mergeSort(leftArray, comparator);
            mergeSort(rightArray, comparator);
            merge(leftArray, rightArray, arr, comparator);
        }
    }

    /**
     * Merges the left and right portions of the array
     *
     * @param leftArray the left array to be merged
     * @param rightArray the right array to be merged
     * @param arr the original array
     * @param comparator the way the data compares
     * @param <T> the data to be sorted
     */
    private static <T> void merge(
            T[] leftArray, T[] rightArray, T[] arr, Comparator<T> comparator) {
        int i = 0;
        int j = 0;
        int k = 0;

        while (i < leftArray.length && j < rightArray.length) {

            if (comparator.compare(leftArray[i], rightArray[j]) <= 0) {
                arr[k] = leftArray[i];
                i++;
            } else {
                arr[k] = rightArray[j];
                j++;
            }
            k++;
        }

        if (i < leftArray.length && !(j < rightArray.length)) {
            for (i = i; i < leftArray.length; i++) {
                arr[k] = leftArray[i];
                k++;
            }
        } else if (!(i < leftArray.length) && j < rightArray.length) {
            for (j = j; j < rightArray.length; j++) {
                arr[k] = rightArray[j];
                k++;
            }
        }
    }


    /**
     * Implement LSD (least significant digit) radix sort.
     *
     * Remember you CANNOT convert the ints to strings at any point in your
     * code!
     *
     * It should be:
     *  stable
     *
     * Have a worst case running time of:
     *  O(kn)
     *
     * And a best case running time of:
     *  O(kn)
     *
     * Any duplicates in the array should be in the same relative position after
     * sorting as they were before sorting. (stable)
     *
     * Do NOT use {@code Math.pow()} in your sort. Instead, if you need to, use
     * the provided {@code pow()} method below.
     *
     * You may use {@code java.util.ArrayList} or {@code java.util.LinkedList}
     * if you wish, but it may only be used inside radix sort and any radix sort
     * helpers. Do NOT use these classes with other sorts.
     *
     * @throws IllegalArgumentException if the array is null
     * @param arr the array to be sorted
     * @return the sorted array
     */
    public static int[] lsdRadixSort(int[] arr) {
        if (arr == null) {
            throw new IllegalArgumentException("The array cannot be null.");
        }

        int numOfIterations = findIterations(arr);

        LinkedList<Integer>[] buckets = new LinkedList[19];
        for (int i = 0; i < 19; i++) {
            buckets[i] = new LinkedList<Integer>();
        }

        int currentIterations = 0;
        int exponent = 0;

        while (currentIterations < numOfIterations) {

            for (int i = 0; i < arr.length; i++) {
                int bucket = (arr[i] / pow(10, exponent)) % 10;
                bucket = bucket + 9;
                buckets[bucket].add(arr[i]);
            }
            exponent++;
            int currentIndex = 0;
            for (LinkedList<Integer> bucket : buckets) {
                while (!bucket.isEmpty()) {
                    arr[currentIndex] = bucket.poll();
                    currentIndex++;
                }
            }
            currentIterations++;
        }
        return arr;
    }

    /**
     * Method finds the longest digit and determines iteration number.
     * @param arr the array of all integers to be sorted
     * @return returns the number of iterations necessary for sorting
     */
    private static int findIterations(int[] arr) {
        int iterationCount = 0;
        boolean iterationNumFound = false;

        int[] negativeArray = new int[arr.length];
        int minValue = Integer.MAX_VALUE;

        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 0) {
                negativeArray[i] = -arr[i];
            } else {
                negativeArray[i] = arr[i];
            }

            if (negativeArray[i] < minValue) {
                minValue = negativeArray[i];
            }
        }

        minValue = Math.abs(minValue);

        while (!iterationNumFound) {
            if (minValue / 10 != 0) {
                minValue = minValue / 10;
                iterationCount++;
            } else {
                iterationCount++;
                iterationNumFound = true;
            }
        }
        return iterationCount;
    }

    /**
     * Calculate the result of a number raised to a power. Use this method in
     * your radix sorts instead of {@code Math.pow()}.
     *
     * DO NOT MODIFY THIS METHOD.
     *
     * @throws IllegalArgumentException if both {@code base} and {@code exp} are
     * 0
     * @throws IllegalArgumentException if {@code exp} is negative
     * @param base base of the number
     * @param exp power to raise the base to. Must be 0 or greater.
     * @return result of the base raised to that power
     */
    private static int pow(int base, int exp) {
        if (exp < 0) {
            throw new IllegalArgumentException("Exponent cannot be negative.");
        } else if (base == 0 && exp == 0) {
            throw new IllegalArgumentException(
                    "Both base and exponent cannot be 0.");
        } else if (exp == 0) {
            return 1;
        } else if (exp == 1) {
            return base;
        }
        int halfPow = pow(base, exp / 2);
        if (exp % 2 == 0) {
            return halfPow * halfPow;
        } else {
            return halfPow * halfPow * base;
        }
    }
}