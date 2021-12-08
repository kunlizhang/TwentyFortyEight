package org.cis120.twentyfortyeight;

import javax.swing.*;
import java.util.Arrays;
import java.util.TreeSet;

public class Tile extends JComponent {
    private int value;
    private boolean hasCombined;
    private final Integer[] validValuesArr = {
        0, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048
    };
    private final TreeSet<Integer> validValues = new TreeSet<>(Arrays.asList(validValuesArr));

    /**
     * Constructor for the tile class. Creates a new empty tile.
     */
    public Tile() {
        this(0);
    }

    /**
     * Creates a new tile with a set value. If the value is not valid, it
     * generates an empty tile.
     * 
     * @param value The value to set for this tile.
     */
    public Tile(int value) {
        if (validValues.contains(value)) {
            this.value = value;
        } else {
            this.value = 0;
        }
        this.hasCombined = false;
    }

    /**
     * Gets the current value of the tile.
     * 
     * @return The int value of the current tile.
     */
    public int getValue() {
        return this.value;
    }

    /**
     * Sets the value of the tile. Does not do anything if it not a valid value.
     * 
     * @param value The int value to set the tile.
     */
    public void setValue(int value) {
        if (validValues.contains(value)) {
            this.value = value;
        }
    }

    /**
     * Checks if this tile is empty.
     * 
     * @return boolean, true if it is empty.
     */
    public boolean isEmpty() {
        return this.value == 0;
    }

    /**
     * Sets the hasCombined boolean to the given boolean.
     * 
     * @param b boolean to set hasCombined to.
     */
    public void setHasCombined(boolean b) {
        hasCombined = b;
    }

    /**
     * Checks if it hasCombined.
     * 
     * @return boolean value of hasCombined.
     */
    public boolean getHasCombined() {
        return hasCombined;
    }
}
