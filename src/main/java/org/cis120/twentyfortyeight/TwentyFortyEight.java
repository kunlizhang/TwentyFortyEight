package org.cis120.twentyfortyeight;

import java.util.Random;

public class TwentyFortyEight {
    private Tile[][] gb;
    private Random rand;

    /**
     * Constructor for a GameBoard. Just resets the game.
     */
    public TwentyFortyEight() {
        this.rand = new Random();
        this.newGame();
    }

    /**
     * Resets the game to start again.
     */
    public void newGame() {
        this.gb = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.gb[i][j] = new Tile();
            }
        }
        this.spawn();
    }

    /**
     * Spawns two random tiles with values of either 2 or 4 on
     * currently empty tiles.
     */
    public void spawn() {
        int x0;
        int y0;
        int x1;
        int y1;

        int t0;
        do {
            t0 = rand.nextInt(16);
            y0 = t0 / 4;
            x0 = t0 % 4;
        } while (!this.gb[x0][y0].isEmpty());

        int t1;
        do {
            t1 = rand.nextInt(16);
            y1 = t1 / 4;
            x1 = t1 % 4;
        } while (t0 == t1 || !this.gb[x1][y1].isEmpty());

        this.gb[x0][y0].setValue((int) Math.pow(2, (rand.nextInt(2) + 1)));
        this.gb[x1][y1].setValue((int) Math.pow(2, (rand.nextInt(2) + 1)));
    }

    /**
     * Gets the GameBoard Tile array
     * @return The GameBoard Tile array
     */
    public Tile[][] getGb() {
        return this.gb;
    }

    /**
     * This calculates the total value of the tiles on the board, which
     * is the current score.
     * @return int value of the score.
     */
    public int getScore() {
        int s = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                s += this.gb[i][j].getValue();
            }
        }
        return s;
    }

    /**
     * For one Tile, shifts it as far horizontally as possible in the
     * game board.
     * @param r The row of the tile to be shifted
     * @param c The column of the tile
     * @param d The direction (1 for right, -1 for left)
     */
    public void horizontalShift(int r, int c, int d) {
        if (c + d >= 0 && c + d < 4) {
            int t1 = this.gb[r][c].getValue();
            int t2 = this.gb[r][c + d].getValue();
            if (t1 == t2 || t2 == 0) {
                this.gb[r][c + d].setValue(t1 + t2);
                this.gb[r][c].setValue(0);
                horizontalShift(r, c + d, d);
            }
        }
    }

    /**
     * For one Tile, shifts it as far vertically as possible in the
     * game board.
     * @param r The row of the tile to be shifted
     * @param c The column of the tile to be shifted.
     * @param d The direction (1 for down, -1 for up).
     */
    public void verticalShift(int r, int c, int d) {
        if (r + d >= 0 && r + d < 4) {
            int t1 = this.gb[r][c].getValue();
            int t2 = this.gb[r + d][c].getValue();
            if (t1 == t2 || t2 == 0) {
                this.gb[r + d][c].setValue(t1 + t2);
                this.gb[r][c].setValue(0);
                verticalShift(r + d, c, d);
            }
        }
    }

    /**
     * Shifts the entire board left.
     */
    public void left() {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                horizontalShift(r, c, -1);
            }
        }
        this.spawn();
    }

    /**
     * Shifts the entire board right.
     */
    public void right() {
        for (int r = 0; r < 4; r++) {
            for (int c = 3; c >= 0; c--) {
                horizontalShift(r, c, 1);
            }
        }
        this.spawn();
    }

    /**
     * Shifts the entire board up.
     */
    public void up() {
        for (int c = 0; c < 4; c++) {
            for (int r = 0; r < 4; r++) {
                verticalShift(r, c, -1);
            }
        }
        this.spawn();
    }

    /**
     * Shifts the entire board down.
     */
    public void down() {
        for (int c = 0; c < 4; c++) {
            for (int r = 3; r >= 0; r--) {
                verticalShift(r, c, +1);
            }
        }
        this.spawn();
    }

    /**
     * Returns a string representation of the game board.
     * @todo implement it so that it shows the last move as well.
     * @return String representing board.
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                s += String.valueOf(this.gb[i][j].getValue()) + " ";
            }
            s += "\n";
        }
        return s;
    }

    public static void main(String args[]) {
        TwentyFortyEight g = new TwentyFortyEight();
        System.out.println(g);
        g.left();
        System.out.println(g);
        g.right();
        System.out.println(g);
        g.up();
        System.out.println(g);
        g.down();
        System.out.println(g);
    }
}

