import org.junit.Before;
import org.junit.Test;

import java.util.Comparator;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertTrue;

/**
 * Student tests for sorting algorithms.
 *
 * @author CS 1332 TAs
 * @version 1.0
 */
public class SortingStudentTests {
    private TeachingAssistant[] tas;
    private TeachingAssistant[] tasByName;
    private Integer[] kArray;
    private Integer[] kPartial;
    private ComparatorPlus<TeachingAssistant> comp;
    private ComparatorPlus<Integer> kComp;
    private static final int TIMEOUT = 200;

    @Before
    public void setUp() {
        tas = new TeachingAssistant[10];
        tas[0] = new TeachingAssistant("Alex");
        tas[1] = new TeachingAssistant("Chad");
        tas[2] = new TeachingAssistant("Eric");
        tas[3] = new TeachingAssistant("Mary");
        tas[4] = new TeachingAssistant("Andrew");
        tas[5] = new TeachingAssistant("Youn");
        tas[6] = new TeachingAssistant("Yuli");
        tas[7] = new TeachingAssistant("Grayson");
        tas[8] = new TeachingAssistant("Samuel");
        tas[9] = new TeachingAssistant("Ashley");
        tasByName = new TeachingAssistant[10];
        tasByName[0] = tas[0];
        tasByName[1] = tas[4];
        tasByName[2] = tas[9];
        tasByName[3] = tas[1];
        tasByName[4] = tas[2];
        tasByName[5] = tas[7];
        tasByName[6] = tas[3];
        tasByName[7] = tas[8];
        tasByName[8] = tas[5];
        tasByName[9] = tas[6];

        kArray = new Integer[7];
        kArray[0] = 1;
        kArray[1] = 5;
        kArray[2] = 9;
        kArray[3] = 6;
        kArray[4] = 7;
        kArray[5] = 4;
        kArray[6] = 3;

        kPartial = new Integer[7];
        kPartial[0] = 1;
        kPartial[1] = 4;
        kPartial[2] = 3;
        kPartial[3] = 5;
        kPartial[4] = 6;
        kPartial[5] = 7;
        kPartial[6] = 9;


        comp = TeachingAssistant.getNameComparator();
        kComp = this.kComparator();
    }

    @Test(timeout = TIMEOUT)
    public void testInsertionSort() {
        Sorting.insertionSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 30);
    }

    @Test(timeout = TIMEOUT)
    public void testKthSelect() {
        Random rand = new Random(234);
        Sorting.kthSelect(5, kArray, kComp, rand);
        assertArrayEquals(kPartial, kArray);
        assertTrue("Number of comparisons: " + kComp.getCount(),
                kComp.getCount() <= 19);
    }

    @Test(timeout = TIMEOUT)
    public void testMergeSort() {
        Sorting.mergeSort(tas, comp);
        assertArrayEquals(tasByName, tas);
        assertTrue("Number of comparisons: " + comp.getCount(),
                comp.getCount() <= 48);
    }

    @Test(timeout = TIMEOUT)
    public void testLsdRadixSort() {
        int[] unsortedArray = new int[] {54, -28, 58, -84, 20, 122, -8050, 3};
        int[] sortedArray = new int[] {-8050, -84, -28, 3, 20, 54, 58, 122};
        assertArrayEquals(sortedArray, Sorting.lsdRadixSort(unsortedArray));
    }

    /**
     * New Comparator for kth select algorithm
     * @return a comparator object for use in kth select
     */
    public static ComparatorPlus<Integer> kComparator() {
        return new ComparatorPlus<Integer>() {
            @Override
            public int compare(Integer int1,
                               Integer int2) {
                incrementCount();
                return int1.compareTo(int2);
            }
        };
    }

    /**
     * Class for testing proper sorting.
     */
    private static class TeachingAssistant {
        private String name;

        /**
         * Create a teaching assistant.
         *
         * @param name name of TA
         */
        public TeachingAssistant(String name) {
            this.name = name;
        }

        /**
         * Get the name of the teaching assistant.
         *
         * @return name of teaching assistant
         */
        public String getName() {
            return name;
        }

        /**
         * Set the name of the teaching assistant.
         *
         * @param name name of the teaching assistant
         */
        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "Name: " + name;
        }

        /**
         * Create a comparator that compares the names of the teaching
         * assistants.
         *
         * @return comparator that compares the names of the teaching assistants
         */
        public static ComparatorPlus<TeachingAssistant> getNameComparator() {
            return new ComparatorPlus<TeachingAssistant>() {
                @Override
                public int compare(TeachingAssistant ta1,
                                   TeachingAssistant ta2) {
                    incrementCount();
                    return ta1.getName().compareTo(ta2.getName());
                }
            };
        }
    }

    /**
     * Inner class that allows counting how many comparisons were made.
     */
    private abstract static class ComparatorPlus<T> implements Comparator<T> {
        private int count;

        /**
         * Get the number of comparisons made.
         * @return number of comparisons made
         */
        public int getCount() {
            return count;
        }

        /**
         * Increment the number of comparisons made by one. Call this method in
         * your compare() implementation.
         */
        public void incrementCount() {
            count++;
        }
    }
}
