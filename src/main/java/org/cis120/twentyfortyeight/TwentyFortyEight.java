package org.cis120.twentyfortyeight;


import javax.swing.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.Random;
import java.util.TreeMap;

public class TwentyFortyEight {
    private Tile[][] gb;
    private final Random rand;
    private LinkedList<Tile[][]> gbList;
    private boolean tilesMoved;
    private int score;
    private final String saveFile = "files/save.txt";
    private final String highScoreFile = "files/high.txt";
    private BufferedReader br;
    private BufferedWriter bw;

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
        this.gbList = new LinkedList<>();
        this.gb = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.gb[i][j] = new Tile();
            }
        }
        this.tilesMoved = false;
        this.spawn();
        this.spawn();
        this.gbList.add(this.getGb());
        this.score = 0;
    }

    /**
     * Spawns a random tile with a value of either 2 or 4 on
     * currently empty tiles.
     */
    public void spawn() {
        int x0;
        int y0;

        int t0;
        do {
            t0 = rand.nextInt(16);
            y0 = t0 / 4;
            x0 = t0 % 4;
        } while (!this.gb[x0][y0].isEmpty());

        this.gb[x0][y0].setValue((int) Math.pow(2, (rand.nextInt(2) + 1)));
    }

    /**
     * Gets the GameBoard Tile array, and prevents aliasing.
     * @return The GameBoard Tile[][] array
     */
    public Tile[][] getGb() {
        return deepCopyGb(this.gb);
    }

    /**
     * Creates a deep copy of the given Tile[][] array: must be 4x4.
     * @param ogb   The input array.
     * @return      A deep copy of the input array.
     */
    public Tile[][] deepCopyGb(Tile[][] ogb) {
        Tile[][] temp = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                temp[i][j] = new Tile(ogb[i][j].getValue());
            }
        }
        return temp;
    }

    /**
     * Returns the score of the board.
     * @return int value of the score.
     */
    public int getScore() {
        return score;
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
            Tile t1 = this.gb[r][c];
            Tile t2 = this.gb[r][c + d];
            if (t1.getValue() == t2.getValue() && t2.getValue() != 0
                    && !t2.getHasCombined() && !t1.getHasCombined()) {
                this.gb[r][c + d].setValue(t1.getValue() + t2.getValue());
                this.gb[r][c].setValue(0);
                t2.setHasCombined(true);
                this.score += t2.getValue();
                tilesMoved = true;
            } else if (t1.getValue() != 0 && t2.getValue() == 0) {
                this.gb[r][c + d].setValue(t1.getValue() + t2.getValue());
                this.gb[r][c].setValue(0);
                tilesMoved = true;
            }
            horizontalShift(r, c + d, d);
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
            Tile t1 = this.gb[r][c];
            Tile t2 = this.gb[r + d][c];
            if (t1.getValue() == t2.getValue() && t2.getValue() != 0
                    && !t2.getHasCombined() && !t1.getHasCombined()) {
                this.gb[r + d][c].setValue(t1.getValue() + t2.getValue());
                this.gb[r][c].setValue(0);
                t2.setHasCombined(true);
                this.score += t2.getValue();
                tilesMoved = true;
            } else if (t1.getValue() != 0 && t2.getValue() == 0) {
                this.gb[r + d][c].setValue(t1.getValue() + t2.getValue());
                this.gb[r][c].setValue(0);
                tilesMoved = true;
            }
            verticalShift(r + d, c, d);
        }
    }

    /**
     * Shifts the entire board left by calling onto horizontal shift. Does not
     * combine tiles that have already been combined.
     */
    public void left() {
        for (int r = 0; r < 4; r++) {
            for (int c = 0; c < 4; c++) {
                horizontalShift(r, c, -1);
            }
        }
        postMove();
    }

    /**
     * Shifts the entire board right by calling onto horizontal shift. Does not
     * combine tiles that have already been combined.
     */
    public void right() {
        for (int r = 0; r < 4; r++) {
            for (int c = 3; c >= 0; c--) {
                horizontalShift(r, c, 1);
            }
        }
        postMove();
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
        postMove();
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
        postMove();
    }

    /**
     * Methods to be run after a move is executed: spawns if possible
     * and adds state to state list if someting was spawned.
     */
    public void postMove() {
        if (this.canSpawn()) {
            this.spawn();
            this.gbList.add(this.getGb());
        }
        resetCombined();
    }

    /**
     * Checks if a given tile is empty.
     * @param i Row of the tile
     * @param j Col of the tile
     * @return  Boolean, true if it is empty
     */
    public boolean tileIsEmpty(int i, int j) {
        return this.gb[i][j].isEmpty();
    }

    /**
     * Finds the value of a given cell.
     * @param i Row of the tile
     * @param j Col of the tile
     * @return  The integer value of the tile.
     */
    public int tileValue(int i, int j) {
        return this.gb[i][j].getValue();
    }

    /**
     * Makes all the tiles not combined (resets their status).
     */
    public void resetCombined() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                this.gb[i][j].setHasCombined(false);
            }
        }
    }

    /**
     * Checks whether it is possible to spawn a new tile on the board.
     * @return True if it is possible to spawn a new tile.
     */
    public boolean canSpawn() {
        if (!tilesMoved) {
            return false;
        }
        tilesMoved = false;
        return containsEmpty();
    }

    /**
     * Causes the board to go back one move.
     */
    public void undo() {
        if (this.gbList.size() > 1) {
            this.gbList.removeLast();
            this.gb = deepCopyGb(this.gbList.peekLast());
        }
    }

    /**
     * Checks if the board contains an empty tile.
     * @return true if there is empty tile.
     */
    public boolean containsEmpty() {
        return containsValue(0);
    }

    /**
     * Checks if the board contains a tile of a specific value.
     * @param v The value that we want to check if it is present.
     * @return  True if there is a tile of that value.
     */
    public boolean containsValue(int v) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (this.gb[i][j].getValue() == v) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if it is possible to make a valid move at that point.
     * @return True if it is not possible to make a move (game over).
     */
    public boolean isGameOver() {
        if (containsEmpty()) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (couldCombine(i, j)) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if it is possible for a tile to combine in the current board state.
     * @param i The row of the tile
     * @param j The col of the tile
     * @return  True if the tile can combine with one of its adjacent tiles.
     */
    public boolean couldCombine(int i, int j) {
        boolean b1 = false;
        boolean b2 = false;
        boolean b3 = false;
        boolean b4 = false;
        if (i > 0) {
            b1 = gb[i][j].getValue() == gb[i - 1][j].getValue();
        }
        if (i < 3) {
            b2 = gb[i][j].getValue() == gb[i + 1][j].getValue();
        }
        if (j > 0) {
            b3 = gb[i][j].getValue() == gb[i][j - 1].getValue();
        }
        if (j < 3) {
            b4 = gb[i][j].getValue() == gb[i][j + 1].getValue();
        }
        return b1 || b2 || b3 || b4;
    }

    /**
     * Reads the save file and sets the current board state to that state.
     * @throws FileNotFoundException If there is no file at the given path.
     * @throws IOException           If the file formatting is incorrect.
     */
    public void readSaveFile() throws FileNotFoundException, IOException {
        String currLine;
        this.br = new BufferedReader(new FileReader(saveFile));
        LinkedList<Tile[][]> tempState = new LinkedList<>();

        currLine = br.readLine();
        try {
            this.score = Integer.parseInt(currLine.trim());
        } catch (Exception e) {
            throw new IOException();
        }

        while ((currLine = br.readLine()) != null) {
            String[] lineArr = currLine.split(",");

            Tile[][] currTiles = new Tile[4][4];

            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    try {
                        currTiles[i][j] = new Tile(Integer.parseInt(lineArr[4 * i + j].trim()));
                    } catch (Exception e) {
                        throw new IOException();
                    }
                }
            }
            tempState.add(currTiles);
        }
        this.gbList = new LinkedList<>();
        this.gbList.addAll(tempState);
        this.gb = this.deepCopyGb(tempState.peekLast());
    }

    /**
     * Writes the current game state to the save file, along with all the
     * previous moves. The tiles are stored in a csv format, with 16 values
     * in each line.
     *
     * The first line is current score.
     *
     * @throws IOException If the buffered writer is unable to complete the execution.
     */
    public void writeSaveFile() throws IOException {
        File file = Paths.get(saveFile).toFile();
        this.bw = new BufferedWriter(new FileWriter(file, false));

        bw.write(String.valueOf(this.score));
        bw.write("\n");
        for (Tile[][] t : this.gbList) {
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 4; j++) {
                    this.bw.write(t[i][j].getValue() + ",");
                }
            }
            this.bw.write("\n");
        }
        this.bw.close();
    }

    /**
     * Saves a high score with an associated nickname.
     * @param nickname      The nickname of the saver.
     * @throws IOException
     */
    public void saveHighScore(String nickname, boolean append) throws IOException {
        File file = Paths.get(highScoreFile).toFile();
        this.bw = new BufferedWriter(new FileWriter(file, append));
        bw.write(nickname + ",");
        bw.write(String.valueOf(this.score));
        bw.write("\n");
        this.bw.close();
    }

    /**
     * Reads the high score file.
     * @return  A TreeMap with nicknames as keys and the score as values.
     * @throws IOException
     */
    public TreeMap<String, Integer> readHighScore() throws IOException {
        String currLine;
        TreeMap<String, Integer> scores = new TreeMap<>();
        this.br = new BufferedReader(new FileReader(highScoreFile));

        while ((currLine = br.readLine()) != null) {
            String[] lineArr = currLine.split(",");
            scores.put(lineArr[0], Integer.parseInt(lineArr[1]));
        }
        return scores;
    }

    /**
     * Returns a string representation of the game board.
     * @return String representing board.
     */
    @Override
    public String toString() {
        String s = "";
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                s += this.gb[i][j].getValue() + " ";
            }
            s += "\n";
        }
        return s;
    }


    public static void main(String[] args) {
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

