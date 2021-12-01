package org.cis120.twentyfortyeight;

import javax.swing.*;

public class Tile extends JComponent {
    private int value;
    private boolean hasCombined;

    public Tile() {
        this.value = 0;
        this.hasCombined = false;
    }

    public Tile(int value) {
        this.value = value;
        this.hasCombined = false;
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

    public void setHasCombined(boolean b) {
        hasCombined = b;
    }

    public boolean getHasCombined() {
        return hasCombined;
    }
}
