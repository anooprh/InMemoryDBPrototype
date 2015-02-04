package com.anoop;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class InMemoryDBTest {

    @Test
    public void shouldTestBasicOperations() {
        InMemoryDB<String, Integer> inMemoryDB = new InMemoryDB<String, Integer>();

        inMemoryDB.set("ex", 10);

        assertEquals(Integer.valueOf(10), inMemoryDB.get("ex"));
        assertEquals(1, inMemoryDB.numEqualTo(10));

        inMemoryDB.set("ex", 20);

        assertEquals(Integer.valueOf(20), inMemoryDB.get("ex"));
        assertEquals(1, inMemoryDB.numEqualTo(20));

        inMemoryDB.set("gx", 20);
        inMemoryDB.set("fx", 20);

        assertEquals(Integer.valueOf(20) , inMemoryDB.get("gx"));
        assertEquals(Integer.valueOf(20) , inMemoryDB.get("fx"));
        assertEquals(3, inMemoryDB.numEqualTo(20));


        inMemoryDB.unset("fx");
        inMemoryDB.set("gx", 10);
        inMemoryDB.unset("ex");

        assertNull(inMemoryDB.get("ex"));
        assertEquals(Integer.valueOf(10) , inMemoryDB.get("gx"));
        assertNull(inMemoryDB.get("fx"));
        assertEquals(0, inMemoryDB.numEqualTo(20));
        assertEquals(1, inMemoryDB.numEqualTo(10));
    }
}