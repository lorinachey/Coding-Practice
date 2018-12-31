import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * Your implementation of HashMap.
 * 
 * @author Lorin Achey
 * @version 1.0
 */
public class HashMap<K, V> implements HashMapInterface<K, V> {

    // Do not make any new instance variables.
    private LinkedList<MapEntry<K, V>>[] backingTable;
    private int size;

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code INITIAL_CAPACITY}.
     *
     * Do not use magic numbers!
     *
     * Use constructor chaining.
     */
    public HashMap() {
        this(INITIAL_CAPACITY);
    }

    /**
     * Create a hash map with no entries. The backing array has an initial
     * capacity of {@code initialCapacity}.
     *
     * You may assume {@code initialCapacity} will always be positive.
     *
     * @param initialCapacity initial capacity of the backing array
     */
    public HashMap(int initialCapacity) {
        backingTable =
                (LinkedList<MapEntry<K, V>>[]) new LinkedList[initialCapacity];
    }

    @Override
    public V put(K key, V value) {
        if (key == null || value == null) {
            throw new IllegalArgumentException("Key or value cannot be null.");
        }
        if (((size + 1.0) / backingTable.length) > MAX_LOAD_FACTOR) {
            int length = 2 * backingTable.length + 1;
            resizeBackingTable(length);
        }

        int hash = key.hashCode() % backingTable.length;
        if (hash < 0) {
            hash = Math.abs(hash);
            hash = hash % backingTable.length;
        }

        if (backingTable[hash] != null) {
            for (MapEntry<K, V> mapElement : backingTable[hash]) {
                if (mapElement.getKey().equals(key)) {
                    V valueToBeReturned = mapElement.getValue();
                    mapElement.setValue(value);
                    return valueToBeReturned;
                }
            }
            MapEntry<K, V> newEntry = new MapEntry<>(key, value);
            backingTable[hash].addFirst(newEntry);
            size++;
            return null;
        } else {
            LinkedList<MapEntry<K, V>> list = new LinkedList<>();
            MapEntry<K, V> newEntry = new MapEntry<>(key, value);
            backingTable[hash] = list;
            list.addFirst(newEntry);
            size++;
            return null;
        }
    }

    @Override
    public V remove(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key cannot be null.");
        }
        int hash = key.hashCode() % backingTable.length;
        if (hash < 0) {
            hash = Math.abs(hash);
            hash = hash % backingTable.length;
        }

        if (backingTable[hash] != null) {
            Iterator<MapEntry<K, V>> iteratorOfMapEntries =
                    backingTable[hash].iterator();
            while (iteratorOfMapEntries.hasNext()) {
                MapEntry<K, V> mapEntry = iteratorOfMapEntries.next();
                if (mapEntry.getKey().equals(key)) {
                    V valueToBeReturned = mapEntry.getValue();
                    iteratorOfMapEntries.remove();
                    size--;
                    return valueToBeReturned;
                }
            }
        }
        throw new NoSuchElementException("This key is not in the map.");
    }

    @Override
    public V get(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Key cannot be null.");
        }
        int hash = key.hashCode() % backingTable.length;
        if (hash < 0) {
            hash = Math.abs(hash);
            hash = hash % backingTable.length;
        }

        if (backingTable[hash] != null) {
            for (MapEntry<K, V> mapElement : backingTable[hash]) {
                if (mapElement.getKey().equals(key)) {
                    return (mapElement.getValue());
                }
            }
        }
        throw new NoSuchElementException("This key does not exist.");
    }

    @Override
    public boolean containsKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("The key cannot be null.");
        }
        int hash = key.hashCode() % backingTable.length;
        if (hash < 0) {
            hash = Math.abs(hash);
            hash = hash % backingTable.length;
        }

        if (backingTable[hash] == null) {
            return false;
        } else {
            for (MapEntry<K, V> mapElement : backingTable[hash]) {
                if (mapElement.getKey().equals(key)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void clear() {
        backingTable =
                (LinkedList<MapEntry<K, V>>[]) new LinkedList[INITIAL_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public Set<K> keySet() {
        Set<K> keyset = new HashSet<>();
        for (int i = 0; i < backingTable.length; i++) {
            if (backingTable[i] != null) {
                for (MapEntry<K, V> mapElement : backingTable[i]) {
                    keyset.add(mapElement.getKey());
                }
            }
        }
        return keyset;
    }

    @Override
    public List<V> values() {
        List<V> values = new LinkedList<>();
        for (int i = 0; i < backingTable.length; i++) {
            if (backingTable[i] != null) {
                for (MapEntry<K, V> mapElement : backingTable[i]) {
                    values.add(mapElement.getValue());
                }
            }
        }
        return values;
    }

    @Override
    public void resizeBackingTable(int length) {
        if (length < 1) {
            throw new IllegalArgumentException("The length cannot"
                    + " be less than 1.");
        }
        LinkedList<MapEntry<K, V>>[] resizedBackingArray =
                (LinkedList<MapEntry<K, V>>[]) new LinkedList[length];
        for (int i = 0; i < backingTable.length; i++) {
            if (backingTable[i] != null) {
                for (MapEntry<K, V> mapElement : backingTable[i]) {

                    K key = mapElement.getKey();
                    int newHash = key.hashCode() % resizedBackingArray.length;
                    if (newHash < 0) {
                        newHash = Math.abs(newHash);
                        newHash = newHash % backingTable.length;
                    }

                    if (resizedBackingArray[newHash] == null) {
                        MapEntry<K, V> newEntry =
                                new MapEntry<>(mapElement.getKey(),
                                        mapElement.getValue());
                        LinkedList<MapEntry<K, V>> list = new LinkedList<>();
                        resizedBackingArray[newHash] = list;
                        list.addFirst(newEntry);
                    } else {
                        resizedBackingArray[newHash].addFirst(mapElement);
                    }
                }
            }
        }
        backingTable = resizedBackingArray;
    }
    
    @Override
    public LinkedList<MapEntry<K, V>>[] getTable() {
        // DO NOT EDIT THIS METHOD!
        return backingTable;
    }
}
