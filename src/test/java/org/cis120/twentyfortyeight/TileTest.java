package org.cis120.twentyfortyeight;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;


public class TileTest {

    @Test
    public void testEmptyTileConstructor() {
        Tile t = new Tile();
        assertEquals(0, t.getValue());
        assertFalse(t.getHasCombined());
        assertTrue(t.isEmpty());
    }

    @Test
    public void testNonEmptyConstructor() {
        Tile t = new Tile(2);
        assertEquals(2, t.getValue());
        assertFalse(t.getHasCombined());
        assertFalse(t.isEmpty());
    }

    @Test
    public void testInvalidValueConstructor() {
        Tile t = new Tile(15);
        assertEquals(0, t.getValue());
        assertFalse(t.getHasCombined());
        assertTrue(t.isEmpty());
    }

    @Test
    public void testSetValue() {
        Tile t = new Tile();
        assertEquals(0, t.getValue());
        t.setValue(2);
        assertEquals(2, t.getValue());
        t.setValue(-10);
        assertEquals(2, t.getValue());
        assertFalse(t.isEmpty());
    }

}
