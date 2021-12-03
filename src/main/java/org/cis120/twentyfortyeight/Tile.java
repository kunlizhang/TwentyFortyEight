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
        if (value == 2 || value == 4 || value == 8 || value == 16 ||
                value == 32 || value == 64 || value == 128 || value == 256 ||
                value == 512 || value == 1024 || value == 2048 || value == 0
        ) {
            this.value = value;
        } else {
            this.value = 2;
        }
        this.hasCombined = false;
    }

    public int getValue() {
        return this.value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isEmpty() {
        return this.value == 0;
    }

    public void setHasCombined(boolean b) {
        hasCombined = b;
    }

    public boolean getHasCombined() {
        return hasCombined;
    }
}
