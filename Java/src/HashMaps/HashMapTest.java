import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Sample JUnit test cases for HashMaps
 *
 * @version 1.0
 * @author Anonymous
 */
public class HashMapTest {
    private HashMap<Integer, String> map;
    private HashMap<Integer, String> map1;
    private LinkedList<MapEntry<Integer, String>>[] expected =
            (LinkedList<MapEntry<Integer, String>>[]) new
                    LinkedList[HashMapInterface.INITIAL_CAPACITY];
    public static final int TIMEOUT = 200;

    @Before
    public void setup() {
        map = new HashMap<>(5);
        map1 = new HashMap<>();
    }

    //test add()
    @Test(timeout = TIMEOUT)
    public void testAdd() {
        expected = (LinkedList<MapEntry<Integer, String>>[])
                new LinkedList[11];

        expected[1] = new LinkedList<>();
        expected[1].addLast(new MapEntry(1, "A"));
        expected[1].addLast(new MapEntry(6, "B"));

        expected[3] = new LinkedList<>();
        expected[3].addLast(new MapEntry(3, "C"));

        map.put(6, "B");
        map.put(1, "A");
        map.put(3, "C");

        assertEquals(expected[1], map.getTable()[1]);
        assertEquals(expected[3], map.getTable()[3]);

    }

    //test add() with same Key
    @Test(timeout = TIMEOUT)
    public void testAddSame() {
        expected = (LinkedList<MapEntry<Integer, String>>[])
                new LinkedList[11];

        expected[1] = new LinkedList<>();
        expected[1].addLast(new MapEntry(1, "A"));

        expected[3] = new LinkedList<>();
        expected[3].addLast(new MapEntry(3, "C"));

        map.put(1, "B");
        map.put(1, "A");
        map.put(3, "C");

        assertEquals(expected[1], map.getTable()[1]);
        assertEquals(expected[3], map.getTable()[3]);

    }

    //test add() with null key
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddKeyNull() {
        try {
            map.put(null, "A");
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            throw e;
        }
    }

    //test add() with null value
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testAddValueNull() {
        try {
            map.put(1, null);
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            throw e;
        }
    }

    //test add() using resize()
    @Test(timeout = TIMEOUT)
    public void testAddMore() {
        expected = (LinkedList<MapEntry<Integer, String>>[])
                new LinkedList[11];
        expected[1] = new LinkedList<>();
        expected[1].addLast(new MapEntry(1, "A"));

        expected[6] = new LinkedList<>();
        expected[6].addLast(new MapEntry(6, "B"));

        expected[3] = new LinkedList<>();
        expected[3].addLast(new MapEntry(3, "C"));

        expected[2] = new LinkedList<>();
        expected[2].addLast(new MapEntry(2, "D"));

        expected[4] = new LinkedList<>();
        expected[4].addLast(new MapEntry(4, "E"));

        map.put(6, "B");
        map.put(1, "A");
        map.put(3, "C");
        map.put(2, "D");
        map.put(4, "E");

        assertEquals(expected[1], map.getTable()[1]);
        assertEquals(expected[3], map.getTable()[3]);
        assertEquals(expected[2], map.getTable()[2]);
        assertEquals(expected[4], map.getTable()[4]);
        assertEquals(expected[6], map.getTable()[6]);
        assertEquals(expected.length, map.getTable().length);
    }

    //test remove()
    @Test(timeout = TIMEOUT)
    public void testRemove() {
        expected[2] = new LinkedList<>();
        expected[2].addLast(new MapEntry(15, "G"));
        expected[1] = new LinkedList<>();
        expected[11] = new LinkedList<>();
        expected[9] = new LinkedList<>();
        expected[9].addLast(new MapEntry(35, "D"));
        expected[7] = new LinkedList<>();
        expected[7].addLast(new MapEntry(7, "E"));
        expected[4] = new LinkedList<>();

        map1.put(24, "C");
        map1.put(69, "F");
        map1.put(1, "B");
        map1.put(15, "G");
        map1.put(2, "A");
        map1.put(35, "D");
        map1.put(7, "E");

        map1.remove(24);
        map1.remove(69);
        map1.remove(1);
        map1.remove(2);

        assertEquals(expected[1], map1.getTable()[1]);
        assertEquals(expected[2], map1.getTable()[2]);
        assertEquals(expected[4], map1.getTable()[4]);
        assertEquals(expected[7], map1.getTable()[7]);
        assertEquals(expected[9], map1.getTable()[9]);
        assertEquals(expected[11], map1.getTable()[11]);

    }

    //test remove() null key
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testRemoveNull() {
        try {
            map.remove(null);
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            throw e;
        }
    }

    //test remove() value not exist
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testRemoveNotExist() {
        try {
            map.remove(4);
        } catch (Exception e) {
            assertEquals(NoSuchElementException.class, e.getClass());
            throw e;
        }
    }

    //test get() null value
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testGetNull() {
        try {
            map.get(null);
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            throw e;
        }
    }

    //test get() value not exist
    @Test(timeout = TIMEOUT, expected = NoSuchElementException.class)
    public void testGetNotExist() {
        try {
            map.get(4);
        } catch (Exception e) {
            assertEquals(NoSuchElementException.class, e.getClass());
            throw e;
        }
    }

    //test get()
    @Test(timeout = TIMEOUT)
    public void testGet() {
        expected[2] = new LinkedList<>();
        expected[2].addLast(new MapEntry(2, "A"));
        expected[2].addLast(new MapEntry(15, "G"));
        expected[1] = new LinkedList<>();
        expected[1].addLast(new MapEntry(1, "B"));
        expected[7] = new LinkedList<>();
        expected[7].addLast(new MapEntry(7, "E"));

        map1.put(24, "C");
        map1.put(69, "F");
        map1.put(1, "B");
        map1.put(15, "G");
        map1.put(2, "A");
        map1.put(35, "D");
        map1.put(7, "E");

        assertEquals(expected[2].get(0).getValue(), map1.get(2));
        assertEquals(expected[2].get(1).getValue(), map1.get(15));
        assertEquals(expected[1].get(0).getValue(), map1.get(1));
        assertEquals(expected[7].get(0).getValue(), map1.get(7));

    }

    //test contains() null key
    @Test(timeout = TIMEOUT, expected = IllegalArgumentException.class)
    public void testcontainsNull() {
        try {
            map.containsKey(null);
        } catch (Exception e) {
            assertEquals(IllegalArgumentException.class, e.getClass());
            throw e;
        }
    }

    //test contains() = true
    @Test(timeout = TIMEOUT)
    public void testContainsTrue() {
        expected[2] = new LinkedList<>();
        expected[2].addLast(new MapEntry(2, "A"));
        expected[2].addLast(new MapEntry(15, "G"));

        map1.put(15, "G");
        map1.put(2, "A");

        assertEquals(true, map1.containsKey(2));
        assertEquals(true, map1.containsKey(15));
    }

    //test contains() = false
    @Test(timeout = TIMEOUT)
    public void testContainsFalse() {
        expected[2] = new LinkedList<>();
        expected[2].addLast(new MapEntry(2, "A"));
        expected[2].addLast(new MapEntry(15, "G"));

        map1.put(15, "G");
        map1.put(2, "A");

        assertEquals(false, map1.containsKey(3));
        assertEquals(false, map1.containsKey(11));
    }

    //test clear()
    @Test(timeout = TIMEOUT)
    public void testClear() {
        expected[2] = new LinkedList<>();
        expected[2].addLast(new MapEntry(2, "A"));
        expected[2].addLast(new MapEntry(15, "G"));

        map1.put(15, "G");
        map1.put(2, "A");

        map1.clear();

        assertEquals(0, map1.size());
    }

    //test size()
    @Test(timeout = TIMEOUT)
    public void testSize() {
        expected[2] = new LinkedList<>();
        expected[2].addLast(new MapEntry(2, "A"));
        expected[2].addLast(new MapEntry(15, "G"));

        map1.put(15, "G");
        map1.put(2, "A");

        assertEquals(2, map1.size());
    }

    //test keySet()
    @Test(timeout = TIMEOUT)
    public void testKeySet() {
        expected[2] = new LinkedList<>();
        expected[2].addLast(new MapEntry(2, "A"));
        expected[1] = new LinkedList<>();
        expected[1].addLast(new MapEntry(1, "B"));
        expected[11] = new LinkedList<>();
        expected[11].addLast(new MapEntry(24, "C"));
        expected[9] = new LinkedList<>();
        expected[9].addLast(new MapEntry(35, "D"));
        expected[7] = new LinkedList<>();
        expected[7].addLast(new MapEntry(7, "E"));
        expected[4] = new LinkedList<>();
        expected[4].addLast(new MapEntry(69, "F"));

        map.put(24, "C");
        map.put(69, "F");
        map.put(1, "B");
        map.put(2, "A");
        map.put(35, "D");
        map.put(7, "E");

        Set<Integer> actual = map.keySet();
        Set<Integer> expected = new HashSet<>();
        expected.add(1);
        expected.add(2);
        expected.add(7);
        expected.add(24);
        expected.add(35);
        expected.add(69);

        assertEquals(expected, actual);
    }

    //test valueList()
    @Test(timeout = TIMEOUT)
    public void testValueList() {

        map.put(11, "D");
        map.put(6, "B");
        map.put(1, "A");
        map.put(3, "C");
        //map.put(4, "E");

        List<String> actual = map.values();
        List<String> expected = new LinkedList<>();
        expected.add("D");
        expected.add("A");
        expected.add("C");
        //expected.add("E");
        expected.add("B");

        assertEquals(expected, actual);
    }

    //test resize()
    @Test(timeout = TIMEOUT)
    public void testReSize() {
        expected = (LinkedList<MapEntry<Integer, String>>[])
                new LinkedList[11];

        expected[1] = new LinkedList<>();
        expected[1].addLast(new MapEntry(1, "A"));
        expected[6] = new LinkedList<>();
        expected[6].addLast(new MapEntry(6, "B"));

        expected[3] = new LinkedList<>();
        expected[3].addLast(new MapEntry(3, "C"));

        map.put(6, "B");
        map.put(1, "A");
        map.put(3, "C");


        map.resizeBackingTable(15);


        assertEquals(15, map.getTable().length);
        assertEquals(expected[1], map.getTable()[1]);
        assertEquals(expected[3], map.getTable()[3]);
        assertEquals(expected[6], map.getTable()[6]);
    }

}
