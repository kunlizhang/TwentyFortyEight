package org.game.twentyfortyeight;

import org.junit.jupiter.api.*;

import java.io.*;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.TreeMap;

import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    @Test
    public void testConstructor() {
        TwentyFortyEight g = new TwentyFortyEight();
        // Testing that it generates two tiles, each of value 2 or 4

        Tile[][] gameBoard = g.getGb();
        LinkedList<Tile> tileList = new LinkedList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile currTile = gameBoard[i][j];
                if (!currTile.isEmpty()) {
                    tileList.add(currTile);
                }
                assertFalse(currTile.getHasCombined());
            }
        }

        assertEquals(2, tileList.size());
        for (Tile t : tileList) {
            boolean valid = t.getValue() == 2 || t.getValue() == 4;
            assertTrue(valid);
        }
    }

    @Test
    public void testSpawn() {
        TwentyFortyEight g = new TwentyFortyEight();
        // Spawn in another tile, which must have a value of either 2 or 4
        g.spawn();
        Tile[][] gameBoard = g.getGb();
        LinkedList<Tile> tileList = new LinkedList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile currTile = gameBoard[i][j];
                if (!currTile.isEmpty()) {
                    tileList.add(currTile);
                }
                assertFalse(currTile.getHasCombined());
            }
        }

        assertEquals(3, tileList.size());
        for (Tile t : tileList) {
            boolean valid = t.getValue() == 2 || t.getValue() == 4;
            assertTrue(valid);
        }
    }

    @Test
    public void testHorizontalShiftEmpty() {
        TwentyFortyEight g = new TwentyFortyEight();
        Integer[] valuesArr = {
            2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };
        g.setGB(valuesArr);
        g.horizontalShift(0, 0, 1);

        for (int i = 0; i < 3; i++) {
            assertEquals(0, g.getGb()[0][i].getValue());
        }
        assertEquals(2, g.getGb()[0][3].getValue());

        g.horizontalShift(1, 0, 1);

        for (int i = 0; i < 3; i++) {
            assertEquals(0, g.getGb()[1][i].getValue());
        }
        assertEquals(2, g.getGb()[1][3].getValue());

        g.horizontalShift(0, 3, -1);
        for (int i = 1; i < 4; i++) {
            assertEquals(0, g.getGb()[0][i].getValue());
        }
        assertEquals(2, g.getGb()[0][0].getValue());
    }

    @Test
    public void testVerticalShiftEmpty() {
        TwentyFortyEight g = new TwentyFortyEight();
        Integer[] valuesArr = {
            2, 0, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };
        g.setGB(valuesArr);
        g.verticalShift(0, 0, 1);

        for (int i = 0; i < 3; i++) {
            assertEquals(0, g.getGb()[i][0].getValue());
        }
        assertEquals(2, g.getGb()[3][0].getValue());

        g.verticalShift(0, 1, 1);

        for (int i = 0; i < 3; i++) {
            assertEquals(0, g.getGb()[i][1].getValue());
        }
        assertEquals(2, g.getGb()[3][1].getValue());

        g.verticalShift(3, 0, -1);
        for (int i = 1; i < 4; i++) {
            assertEquals(0, g.getGb()[i][0].getValue());
        }
        assertEquals(2, g.getGb()[0][0].getValue());
    }

    @Test
    public void testHorizontalShiftNonEmpty() {
        TwentyFortyEight g = new TwentyFortyEight();
        Integer[] valuesArr = {
            2, 2, 0, 0, 0, 2, 0, 2, 2, 4, 0, 0, 0, 0, 0, 0
        };
        g.setGB(valuesArr);

        g.horizontalShift(0, 0, 1);
        g.horizontalShift(1, 3, -1);
        g.horizontalShift(2, 1, -1);

        Tile[][] currBoard = g.getGb();
        assertEquals(0, currBoard[0][0].getValue());
        assertEquals(0, currBoard[0][1].getValue());
        assertEquals(0, currBoard[0][2].getValue());
        assertEquals(4, currBoard[0][3].getValue());

        assertEquals(4, currBoard[1][0].getValue());
        assertEquals(0, currBoard[1][1].getValue());
        assertEquals(0, currBoard[1][2].getValue());
        assertEquals(0, currBoard[1][3].getValue());

        assertEquals(2, currBoard[2][0].getValue());
        assertEquals(4, currBoard[2][1].getValue());
        assertEquals(0, currBoard[2][2].getValue());
        assertEquals(0, currBoard[2][3].getValue());

    }

    @Test
    public void testVerticalShiftNonEmpty() {
        TwentyFortyEight g = new TwentyFortyEight();
        Integer[] valuesArr = {
            4, 0, 0, 0, 0, 2, 0, 0, 4, 0, 2, 8, 0, 2, 0, 16,
        };

        g.setGB(valuesArr);

        g.verticalShift(0, 0, 1);
        g.verticalShift(3, 1, -1);
        g.verticalShift(3, 3, -1);

        Tile[][] currBoard = g.getGb();
        assertEquals(0, currBoard[0][0].getValue());
        assertEquals(0, currBoard[1][0].getValue());
        assertEquals(0, currBoard[2][0].getValue());
        assertEquals(8, currBoard[3][0].getValue());

        assertEquals(4, currBoard[0][1].getValue());
        assertEquals(0, currBoard[1][1].getValue());
        assertEquals(0, currBoard[2][1].getValue());
        assertEquals(0, currBoard[3][1].getValue());

        assertEquals(8, currBoard[0][3].getValue());
        assertEquals(0, currBoard[1][3].getValue());
        assertEquals(0, currBoard[2][3].getValue());
        assertEquals(16, currBoard[3][3].getValue());
    }

    @Test
    public void testUp() {
        TwentyFortyEight g = new TwentyFortyEight();
        Integer[] valuesArr = {
            0, 4, 8, 0,
            0, 0, 8, 0,
            0, 4, 4, 2,
            2, 0, 4, 2,
        };

        g.setGB(valuesArr);

        g.up(false);
        Tile[][] currBoard = g.getGb();

        assertEquals(2, currBoard[0][0].getValue());
        assertEquals(0, currBoard[1][0].getValue());
        assertEquals(0, currBoard[2][0].getValue());
        assertEquals(0, currBoard[3][0].getValue());

        assertEquals(8, currBoard[0][1].getValue());
        assertEquals(0, currBoard[1][1].getValue());
        assertEquals(0, currBoard[2][1].getValue());
        assertEquals(0, currBoard[3][1].getValue());

        assertEquals(16, currBoard[0][2].getValue());
        assertEquals(8, currBoard[1][2].getValue());
        assertEquals(0, currBoard[2][2].getValue());
        assertEquals(0, currBoard[3][2].getValue());

        assertEquals(4, currBoard[0][3].getValue());
        assertEquals(0, currBoard[1][3].getValue());
        assertEquals(0, currBoard[2][3].getValue());
        assertEquals(0, currBoard[3][3].getValue());
    }

    @Test
    public void testDown() {
        TwentyFortyEight g = new TwentyFortyEight();
        Integer[] valuesArr = {
            0, 4, 8, 32,
            0, 0, 8, 0,
            0, 4, 4, 2,
            2, 8, 4, 2,
        };

        g.setGB(valuesArr);

        g.down(false);
        Tile[][] currBoard = g.getGb();

        assertEquals(0, currBoard[0][0].getValue());
        assertEquals(0, currBoard[1][0].getValue());
        assertEquals(0, currBoard[2][0].getValue());
        assertEquals(2, currBoard[3][0].getValue());

        assertEquals(0, currBoard[0][1].getValue());
        assertEquals(0, currBoard[1][1].getValue());
        assertEquals(8, currBoard[2][1].getValue());
        assertEquals(8, currBoard[3][1].getValue());

        assertEquals(0, currBoard[0][2].getValue());
        assertEquals(0, currBoard[1][2].getValue());
        assertEquals(16, currBoard[2][2].getValue());
        assertEquals(8, currBoard[3][2].getValue());

        assertEquals(0, currBoard[0][3].getValue());
        assertEquals(0, currBoard[1][3].getValue());
        assertEquals(32, currBoard[2][3].getValue());
        assertEquals(4, currBoard[3][3].getValue());
    }

    @Test
    public void testRight() {
        TwentyFortyEight g = new TwentyFortyEight();
        Integer[] valuesArr = {
            32, 32, 0, 8,
            8, 8, 2, 2,
            4, 4, 4, 4,
            2, 2, 0, 0
        };

        g.setGB(valuesArr);

        g.right(false);
        Tile[][] currBoard = g.getGb();

        assertEquals(0, currBoard[0][0].getValue());
        assertEquals(0, currBoard[1][0].getValue());
        assertEquals(0, currBoard[2][0].getValue());
        assertEquals(0, currBoard[3][0].getValue());

        assertEquals(0, currBoard[0][1].getValue());
        assertEquals(0, currBoard[1][1].getValue());
        assertEquals(0, currBoard[2][1].getValue());
        assertEquals(0, currBoard[3][1].getValue());

        assertEquals(64, currBoard[0][2].getValue());
        assertEquals(16, currBoard[1][2].getValue());
        assertEquals(8, currBoard[2][2].getValue());
        assertEquals(0, currBoard[3][2].getValue());

        assertEquals(8, currBoard[0][3].getValue());
        assertEquals(4, currBoard[1][3].getValue());
        assertEquals(8, currBoard[2][3].getValue());
        assertEquals(4, currBoard[3][3].getValue());
    }

    @Test
    public void testLeft() {
        TwentyFortyEight g = new TwentyFortyEight();
        Integer[] valuesArr = {
            32, 32, 0, 8,
            8, 8, 2, 2,
            4, 4, 4, 4,
            2, 2, 0, 0
        };

        g.setGB(valuesArr);

        g.left(false);
        Tile[][] currBoard = g.getGb();
        assertEquals(64, currBoard[0][0].getValue());
        assertEquals(16, currBoard[1][0].getValue());
        assertEquals(8, currBoard[2][0].getValue());
        assertEquals(4, currBoard[3][0].getValue());

        assertEquals(8, currBoard[0][1].getValue());
        assertEquals(4, currBoard[1][1].getValue());
        assertEquals(8, currBoard[2][1].getValue());
        assertEquals(0, currBoard[3][1].getValue());

        assertEquals(0, currBoard[0][2].getValue());
        assertEquals(0, currBoard[1][2].getValue());
        assertEquals(0, currBoard[2][2].getValue());
        assertEquals(0, currBoard[3][2].getValue());

        assertEquals(0, currBoard[0][3].getValue());
        assertEquals(0, currBoard[1][3].getValue());
        assertEquals(0, currBoard[2][3].getValue());
        assertEquals(0, currBoard[3][3].getValue());
    }

    @Test
    public void testContainsEmpty() {
        TwentyFortyEight g = new TwentyFortyEight();
        // Initially empty
        Integer[] valuesArr = {
            32, 32, 0, 8,
            8, 8, 2, 2,
            4, 4, 4, 4,
            2, 2, 0, 0
        };

        g.setGB(valuesArr);
        g.left();
        assertTrue(g.containsEmpty());

        // Not empty after move.
        Integer[] valuesArr1 = {
            64, 32, 4, 8,
            128, 32, 2, 4,
            16, 8, 4, 2,
            32, 2, 4, 8
        };

        g.setGB(valuesArr1);
        g.right();
        assertFalse(g.containsEmpty());

        // Not empty before but empty after.
        Integer[] valuesArr2 = {
            64, 32, 4, 8,
            128, 32, 2, 4,
            16, 8, 4, 2,
            32, 2, 4, 8
        };

        g.setGB(valuesArr2);
        g.down(false);
        assertTrue(g.containsEmpty());
    }

    @Test
    public void testUndo() {
        TwentyFortyEight g = new TwentyFortyEight();
        // Nothing happens after undo
        Integer[] valuesArr = {
            64, 32, 4, 8,
            128, 32, 2, 4,
            16, 8, 4, 2,
            32, 2, 4, 8
        };

        g.setGB(valuesArr);
        g.right();
        assertEquals(1, g.getGbList().size());
        g.undo();
        Tile[][] currBoard = g.getGb();
        assertEquals(1, g.getGbList().size());

        assertEquals(64, currBoard[0][0].getValue());
        assertEquals(128, currBoard[1][0].getValue());
        assertEquals(16, currBoard[2][0].getValue());
        assertEquals(32, currBoard[3][0].getValue());

        assertEquals(32, currBoard[0][1].getValue());
        assertEquals(32, currBoard[1][1].getValue());
        assertEquals(8, currBoard[2][1].getValue());
        assertEquals(2, currBoard[3][1].getValue());

        assertEquals(4, currBoard[0][2].getValue());
        assertEquals(2, currBoard[1][2].getValue());
        assertEquals(4, currBoard[2][2].getValue());
        assertEquals(4, currBoard[3][2].getValue());

        assertEquals(8, currBoard[0][3].getValue());
        assertEquals(4, currBoard[1][3].getValue());
        assertEquals(2, currBoard[2][3].getValue());
        assertEquals(8, currBoard[3][3].getValue());

        Integer[] valuesArr1 = {
            64, 32, 4, 4,
            128, 32, 2, 4,
            16, 8, 4, 2,
            32, 2, 4, 8
        };

        g.setGB(valuesArr1);
        g.right();
        assertEquals(2, g.getGbList().size());
        g.undo();
        Tile[][] currBoard1 = g.getGb();
        assertEquals(1, g.getGbList().size());

        assertEquals(64, currBoard1[0][0].getValue());
        assertEquals(128, currBoard1[1][0].getValue());
        assertEquals(16, currBoard1[2][0].getValue());
        assertEquals(32, currBoard1[3][0].getValue());

        assertEquals(32, currBoard1[0][1].getValue());
        assertEquals(32, currBoard1[1][1].getValue());
        assertEquals(8, currBoard1[2][1].getValue());
        assertEquals(2, currBoard1[3][1].getValue());

        assertEquals(4, currBoard1[0][2].getValue());
        assertEquals(2, currBoard1[1][2].getValue());
        assertEquals(4, currBoard1[2][2].getValue());
        assertEquals(4, currBoard1[3][2].getValue());

        assertEquals(4, currBoard1[0][3].getValue());
        assertEquals(4, currBoard1[1][3].getValue());
        assertEquals(2, currBoard1[2][3].getValue());
        assertEquals(8, currBoard1[3][3].getValue());
    }

    @Test
    public void testNewGameReset() {
        TwentyFortyEight g = new TwentyFortyEight();
        // Performing some random moves.
        g.up();
        g.down();
        g.left();
        g.right();
        // Testing that it generates two tiles, each of value 2 or 4

        g.newGame();

        assertEquals(1, g.getGbList().size());
        Tile[][] gameBoard = g.getGb();
        LinkedList<Tile> tileList = new LinkedList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile currTile = gameBoard[i][j];
                if (!currTile.isEmpty()) {
                    tileList.add(currTile);
                }
                assertFalse(currTile.getHasCombined());
            }
        }

        assertEquals(2, tileList.size());
        for (Tile t : tileList) {
            boolean valid = t.getValue() == 2 || t.getValue() == 4;
            assertTrue(valid);
        }
    }

    @Test
    public void testSpawnsNewTileAfterMove() {
        TwentyFortyEight g = new TwentyFortyEight();

        Integer[] valuesArr = {
            2, 2, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
        };

        g.setGB(valuesArr);
        g.left();

        Tile[][] gameBoard = g.getGb();
        LinkedList<Tile> tileList = new LinkedList<>();

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile currTile = gameBoard[i][j];
                if (!currTile.isEmpty()) {
                    tileList.add(currTile);
                }
                assertFalse(currTile.getHasCombined());
            }
        }

        assertEquals(2, tileList.size());
        for (Tile t : tileList) {
            boolean valid = t.getValue() == 2 || t.getValue() == 4;
            assertTrue(valid);
        }

        Integer[] valuesArr1 = {
            2, 4, 8, 16,
            0, 0, 0, 0,
            0, 0, 0, 0,
            0, 0, 0, 0,
        };

        g.setGB(valuesArr1);
        g.down();

        Tile[][] gameBoard1 = g.getGb();
        LinkedList<Tile> tileList1 = new LinkedList<>();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                Tile currTile = gameBoard1[i][j];
                if (!currTile.isEmpty()) {
                    tileList1.add(currTile);
                }
                assertFalse(currTile.getHasCombined());
            }
        }

        assertEquals(1, tileList1.size());
        for (Tile t : tileList1) {
            boolean valid = t.getValue() == 2 || t.getValue() == 4;
            assertTrue(valid);
        }

        Integer[] valuesArr2 = {
            2, 2, 8, 16,
            2, 4, 8, 32,
            8, 16, 32, 64,
            128, 256, 512, 2,
        };

        g.setGB(valuesArr2);
        g.left();

        Tile[][] gameBoard2 = g.getGb();

        assertEquals(4, gameBoard2[0][0].getValue());
        assertEquals(8, gameBoard2[0][1].getValue());
        assertEquals(16, gameBoard2[0][2].getValue());
        boolean b = gameBoard2[0][3].getValue() == 2
                || gameBoard2[0][3].getValue() == 4;
        assertTrue(b);

        for (int i = 1; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(valuesArr2[4 * i + j], gameBoard2[i][j].getValue());
            }
        }

    }

    @Test
    public void testUndoChangesScore() {
        TwentyFortyEight g = new TwentyFortyEight();

        Integer[] valuesArr = {
            2, 2, 4, 0,
            4, 4, 8, 0,
            2, 2, 0, 0,
            32, 32, 64, 0,
        };

        g.setGB(valuesArr);
        g.right();
        int prevScore = g.getScore();
        g.left();
        assertNotEquals(prevScore, g.getScore());
        g.undo();
        assertEquals(prevScore, g.getScore());
        assertEquals(1, g.getScoreList().size());
    }

    @Test
    public void testIsGameOver() {
        TwentyFortyEight g = new TwentyFortyEight();

        // Game over
        Integer[] valuesArr = {
            2, 4, 2, 8,
            4, 8, 4, 2,
            16, 4, 8, 32,
            256, 8, 16, 64,
        };

        g.setGB(valuesArr);
        assertTrue(g.isGameOver());

        // Contains empty not game over.
        Integer[] valuesArr1 = {
            2, 4, 2, 0,
            4, 8, 4, 2,
            16, 4, 8, 32,
            256, 8, 16, 64,
        };

        g.setGB(valuesArr1);
        assertFalse(g.isGameOver());

        // Non-empty not game over.
        Integer[] valuesArr2 = {
            2, 4, 2, 2,
            4, 8, 4, 2,
            16, 4, 8, 32,
            256, 8, 16, 64,
        };

        g.setGB(valuesArr2);
        assertFalse(g.isGameOver());
    }

    // These test cases throw exceptions because exception
    // handling is done by the GameBoard class with buttons.
    // If an exception is thrown, this means that an error has occurred
    // within the test.
    @Test
    public void testWriteSaveFile() throws IOException {
        TwentyFortyEight g = new TwentyFortyEight();

        Integer[] valuesArr = {
            2, 2, 0, 0,
            2, 4, 8, 16,
            0, 0, 0, 0,
            8, 8, 2, 0,
        };

        Integer[] postMoveArr = {
            4, 0, 0, 0,
            2, 4, 8, 16,
            0, 0, 0, 0,
            16, 2, 0, 0,
        };

        g.setGB(valuesArr);
        g.left(false);

        try {
            g.writeSaveFile();
        } catch (IOException e) {
            fail("Exception thrown, couldn't write file.");
        }

        String saveFile = "files/save.txt";
        BufferedReader br = new BufferedReader(new FileReader(saveFile));

        assertEquals(
                20,
                Integer.parseInt(br.readLine().split(",")[0])
        );

        String[] fstLine = br.readLine().split(",");
        String[] sndLine = br.readLine().split(",");
        for (int i = 0; i < 16; i++) {
            assertEquals(valuesArr[i], Integer.parseInt(fstLine[i]));
            assertEquals(postMoveArr[i], Integer.parseInt(sndLine[i]));
        }
    }

    @Test
    public void testWriteSaveFileAfterUndo() throws IOException {
        TwentyFortyEight g = new TwentyFortyEight();

        Integer[] valuesArr = {
            2, 2, 0, 0,
            2, 4, 8, 16,
            0, 0, 0, 0,
            8, 8, 2, 0,
        };

        g.setGB(valuesArr);
        g.left();
        g.undo();
        try {
            g.writeSaveFile();
        } catch (IOException e) {
            fail("Exception thrown, couldn't write file.");
        }

        String saveFile = "files/save.txt";
        BufferedReader br = new BufferedReader(new FileReader(saveFile));

        assertEquals(
                "",
                Integer.parseInt(br.readLine().split(",")[0])
        );

        String[] fstLine = br.readLine().split(",");
        for (int i = 0; i < 16; i++) {
            assertEquals(valuesArr[i], Integer.parseInt(fstLine[i]));
        }
    }

    @Test
    public void testReadSaveFile() throws IOException {
        TwentyFortyEight g = new TwentyFortyEight();

        Integer[] valuesArr = {
            2, 2, 0, 0,
            2, 4, 8, 16,
            0, 0, 0, 0,
            8, 8, 2, 0,
        };

        g.setGB(valuesArr);
        g.writeSaveFile();

        g.newGame();
        g.up();
        g.left();

        assertNotEquals(0, g.getScoreList().size());
        assertNotEquals(0, g.getGbList().size());

        g.readSaveFile();

        assertEquals(0, g.getScoreList().size());
        assertEquals(0, g.getScore());

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                assertEquals(valuesArr[4 * i + j], g.getGb()[i][j].getValue());
            }
        }
    }

    @Test
    public void testReadSaveFileErrors() throws IOException {
        TwentyFortyEight g = new TwentyFortyEight();

        String saveFile = "files/save.txt";

        File file = Paths.get(saveFile).toFile();
        BufferedWriter bw = new BufferedWriter(new FileWriter(file, false));
        bw.write("hmm this will make big problems");

        assertThrows(IOException.class, () -> g.readSaveFile());

        BufferedWriter bw1 = new BufferedWriter(new FileWriter(file, false));
        bw1.write("0 \n 2, 2, 2, 2, 2, 2, 2, 2, ducks, 2, 2, 2, 2, 2, 2");

        assertThrows(IOException.class, () -> g.readSaveFile());
    }

    @Test
    public void testSaveHighScore() throws IOException {
        TwentyFortyEight g = new TwentyFortyEight();

        Integer[] valuesArr = {
            2, 2, 0, 0,
            2, 4, 8, 16,
            0, 0, 0, 0,
            8, 8, 2, 0,
        };

        g.setGB(valuesArr);
        g.left();

        assertEquals(20, g.getScore());

        g.saveHighScore("Testing", g.getScore(), false);

        String highScoreFile = "files/high.txt";
        BufferedReader br = new BufferedReader(new FileReader(highScoreFile));

        String[] score = br.readLine().split(",");
        assertEquals(score[0], "20");
        assertEquals(score[1], "Testing");
    }

    @Test
    public void testReadHighScore() throws IOException {
        TwentyFortyEight g = new TwentyFortyEight();

        g.saveHighScore("Kunli", 20000, false);
        g.saveHighScore("Vincent", 3, true);

        TreeMap<Integer, String> scoreMap = g.readHighScore();
        assertTrue(scoreMap.containsValue("Kunli"));
        assertEquals("Kunli", scoreMap.get(20000));
        assertTrue(scoreMap.containsValue("Vincent"));
        assertEquals("Vincent", scoreMap.get(3));

        assertEquals(2, scoreMap.size());
    }

    @Test
    public void testReadHighScoreErrors() throws IOException {
        TwentyFortyEight g = new TwentyFortyEight();

        // Two players with the same score (only most recent is saved).
        g.saveHighScore("Kunli", 20000, false);
        g.saveHighScore("Will", 10000, true);
        g.saveHighScore("Vincent", 20000, true);

        TreeMap<Integer, String> scoreMap = g.readHighScore();
        assertTrue(scoreMap.containsValue("Will"));
        assertEquals("Will", scoreMap.get(10000));
        assertFalse(scoreMap.containsValue("Kunli"));
        assertTrue(scoreMap.containsValue("Vincent"));
        assertEquals("Vincent", scoreMap.get(20000));
    }

    @Test
    public void testUndoAfterOpenSave() throws IOException {
        TwentyFortyEight g = new TwentyFortyEight();

        g.up();
        g.right();
        g.down();
        g.left();

        LinkedList<Tile[][]> prevMoves = g.getGbList();
        LinkedList<Integer> prevScores = g.getScoreList();

        g.writeSaveFile();

        g.newGame();
        g.up();
        g.right();
        g.down();
        g.left();

        g.readSaveFile();
        Integer l = prevMoves.size();

        for (int k = 0; k < l; k++) {
            Tile[][] oldBoard = prevMoves.pollLast();
            Integer prevScore = prevScores.pollLast();

            if (prevScore == null && k == l - 1) {
                prevScore = 0;
            }

            assertEquals(prevScore, g.getScore());
            for (int i = 0; i < 4; i ++) {
                for (int j = 0; j < 4; j++) {
                    assertEquals(oldBoard[i][j].getValue(), g.getGb()[i][j].getValue());
                }
            }
            g.undo();
        }

    }

    @Test
    public void testScoringSingle() {
        TwentyFortyEight g = new TwentyFortyEight();
        Integer[] valuesArr = {
            2, 0, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0
        };
        g.setGB(valuesArr);
        assertEquals(0, g.getScore());
        g.up();
        assertEquals(4, g.getScore());
    }

}
