package org.cis120.twentyfortyeight;

public class Tile {
    private int value;

    public Tile() {
        this.value = 0;
    }

    public Tile(int value) {
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isEmpty() {
        if (this.value == 0) {
            return true;
        } else {
            return false;
        }
    }
}
