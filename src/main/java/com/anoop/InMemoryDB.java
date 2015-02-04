package com.anoop;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * This class represents the InMemory Database, exposing the public methods which are
 * most often used.
 *
 * Can be used to store any generic <KEY, VALUE> pairs
 *
 * Author : Anoop Hallur
 * Date   : 02/04/2014
 */

public class InMemoryDB<K, V> {
    /**
     * Uses two maps to store the values required for the specified queries
     *
     * Auxillary space complexity = O(n+n) = O(2n) = O(n)
     */
    private Map<K, V> map;
    private Map<V, Integer> countMap;

    /**
     * Default Constructor, initializes the data map and the count map
     */
    public InMemoryDB() {
        this.map = new HashMap<K, V>();
        this.countMap = new HashMap<V, Integer>();
    }

    /**
     * Sets the value of the specified key in the in memory database
     * Returns the old value of the key , if it was previously set to
     * something else, otherwise NULL is returned
     *
     * @param key
     * @param value
     * @return Returns the old value of the key , if it was previously
     * set to something else, otherwise NULL is returned
     *
     * Worst case expected time complexity : O(1)
     */
    public V set(K key, V value) {
        V oldValue = unset(key);

        map.put(key, value);
        if (!countMap.containsKey(value))
            countMap.put(value, 1);
        else {
            Integer valueCount = countMap.get(value);
            countMap.put(value, ++valueCount);
        }
        return oldValue;
    }

    /**
     * Gets the value of the specified key in the in memory DB.
     * If no value was set corresponding to this particular key, a NULL is returned
     *
     * @param key
     * @return the value of the specified key in the in memory DB.
     * If no value was set corresponding to this particular key, a NULL is returned
     *
     * Worst case expected time complexity : O(1)
     */
    public V get(K key) {
        if(map.containsKey(key))
            return map.get(key);
        else
            return null;
    }

    /**
     * Unsets the specified key (i.e value corresponding to that key is made NULL) in the in memory
     * database and returns the previous value of the key.
     * If the key is not set, it does not throw an exception, instead it returns null
     * @param key
     * @return the previous value of the key.
     * If the key is not set, it does not throw an exception, instead it returns null
     *
     * Worst case expected time complexity : O(1)
     */
    public V unset(K key) {
        if(!map.containsKey(key))
            return null;

        V value = map.get(key);
        Integer valueCount = countMap.get(value);
        if(valueCount == 1){
            countMap.remove(value);
        } else{
            countMap.put(value, valueCount-1);
        }
        map.remove(key);
        return value;
    }

    /**
     * Finds the number of keys corresponding to a specified value. If no keys are associated to the
     * specified value(i.e key, value is not stored in db), then 0 is returned, instead of throwing
     * an exception.
     *
     * @param value
     * @return the number of keys corresponding to a specified value. If no keys are associated to the
     * specified value(i.e key, value is not stored in db), then 0 is returned, instead of throwing
     * an exception.
     *
     * Worst case expected time complexity : O(1)
     */
    public int numEqualTo(V value) {
        if(countMap.containsKey(value))
            return countMap.get(value);
        else
            return 0;
    }
}
