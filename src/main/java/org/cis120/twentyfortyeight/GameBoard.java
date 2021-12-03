package org.cis120.twentyfortyeight;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.TreeMap;

public class GameBoard extends JPanel {

    private TwentyFortyEight g; // model for the game
    private JLabel score; //Label for the score
    public static final String IMG_FILE = "files/drawTile.png";
    private BufferedImage img;
    private TreeMap<Integer, BufferedImage> imgMap;

    // Game constants
    public static final int BOARD_WIDTH = 360;
    public static final int BOARD_HEIGHT = 360;

    /**
     * Initializes the game board, and initialises images and action
     * listeners.
     */
    public GameBoard(JLabel score) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        this.g = new TwentyFortyEight(); // initializes model for the game
        this.score = score;
        this.score.setText("Current score: ");
        this.imgMap = new TreeMap<>();

        // Set up images for the tiles
        try {
            if (img == null) {
                img = ImageIO.read(new File(IMG_FILE));
            }
        } catch (IOException e) {
            System.out.println("Internal Error:" + e.getMessage());
        }

        imgMap.put(2, img.getSubimage(50, 50, 90, 90));
        imgMap.put(4, img.getSubimage(155, 50, 90, 90));
        imgMap.put(8, img.getSubimage(260, 50, 90, 90));
        imgMap.put(16, img.getSubimage(60, 185, 90, 90));
        imgMap.put(32, img.getSubimage(163, 185, 90, 90));
        imgMap.put(64, img.getSubimage(268, 185, 90, 90));
        imgMap.put(128, img.getSubimage(374, 185, 90, 90));
        imgMap.put(256, img.getSubimage(60, 316, 90, 90));
        imgMap.put(512, img.getSubimage(163, 316, 90, 90));
        imgMap.put(1024, img.getSubimage(268, 316, 90, 90));
        imgMap.put(2048, img.getSubimage(374, 316, 90, 90));


        /*
         * Listens for key clicks. Updates the model, then updates the game
         * board based off of the updated model.
         */
        addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 38) {
                    g.up();
                } else if (e.getKeyCode() == 40) {
                    g.down();
                } else if (e.getKeyCode() == 37) {
                    g.left();
                } else if (e.getKeyCode() == 39) {
                    g.right();
                }
                repaint();

                if (g.containsValue(2048)) {
                    gameWon();
                    g.newGame();
                }
                if (g.isGameOver()) {
                    gameOver();
                    g.newGame();
                }
            }
        });
    }

    /**
     * (Re-)sets the game to its initial state.
     */
    public void reset() {
        g.newGame();
        repaint();
        this.score.setText("Current score: " + Integer.toString(g.getScore()));

        // Makes sure this component has keyboard/mouse focus
        requestFocusInWindow();
    }

    /**
     * Undo one move. Calls on the game's undo method.
     */
    public void undo() {
        g.undo();
        repaint();

        requestFocusInWindow();
    }

    /**
     * Makes a game won popup.
     */
    public void gameWon() {
        String nick;
        nick = JOptionPane.showInputDialog(null, "You win! Honestly that's kinda nerdy?!? \n" +
                "Your score was " + g.getScore() +
                "\n Enter your name if you would like to save your score!"
                );
        saveScore(nick);
    }

    /**
     * Makes a game over popup.
     */
    public void gameOver() {
        String nick;
        nick = JOptionPane.showInputDialog(null,
                "You lost... I'm not made, just disappointed. :( \n" +
                "Your score was " + g.getScore() +
                "\n Enter your name if you would like to save your score you embarrassment."
        );
        saveScore(nick);
    }

    /**
     * Saves the current high score with the associated nickname by writing to
     * the high score file. If the nickname already has an associated score
     * it adds a number to the end of the nickname.
     * @param nick  The name to be associated with the score.
     */
    public void saveScore(String nick) {
        if (nick != null && !nick.equals("")) {
            try {
                TreeMap<String, Integer> currScores = g.readHighScore();
                int i = 1;
                while (currScores.containsKey(nick)) {
                    nick = nick + i;
                }
                g.saveHighScore(nick, true);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Lol it didn't save and idk why");
            }
        }
    }

    /**
     * Opens the saved game file, and generates a popup if an error occurs.
     * Otherwise, sets the game board to the saved state.
     */
    public void openSavedGame() {
        try {
            g.readSaveFile();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(null, "Bruh you haven't saved a game yet. \n" +
                    "The program nearly crashed cos of you...");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Bruh your save file is f***ed... \n" +
                    "The program nearly crashed cos of you...");
        }
        repaint();
        requestFocusInWindow();
    }

    /**
     * Saves the game to the save file, and generates a popup if an error occurs.
     */
    public void saveGame() {
        try {
            g.writeSaveFile();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Hmm. Something went wrong. \n" +
                    "The program nearly crashed cos of you...");
        }
        repaint();
        requestFocusInWindow();
    }

    /**
     * Draws the game board.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        score.setText("Current score: " + Integer.toString(this.g.getScore()));
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!this.g.tileIsEmpty(i, j)) {
                    g.drawImage(imgMap.get(this.g.tileValue(i, j)), j*90, i*90, null);
                } else {
                    g.setColor(Color.WHITE);
                    g.fillRect(j * 90, i * 90, 90, 90);
                    g.setColor(Color.BLACK);
                }
                g.drawRect(j * 90, i * 90, 90, 90);
            }
        }
    }

    /**
     * Returns the size of the game board.
     */
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
    }
}
