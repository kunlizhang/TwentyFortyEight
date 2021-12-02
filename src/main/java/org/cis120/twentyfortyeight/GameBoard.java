package org.cis120.twentyfortyeight;

import org.cis120.twentyfortyeight.TwentyFortyEight;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Objects;
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
     * Initializes the game board, and initialises images.
     */
    public GameBoard(JLabel score) {
        // creates border around the court area, JComponent method
        setBorder(BorderFactory.createLineBorder(Color.BLACK));

        // Enable keyboard focus on the court area. When this component has the
        // keyboard focus, key events are handled by its key listener.
        setFocusable(true);

        g = new TwentyFortyEight(); // initializes model for the game
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
                score.setText("Current score: " + Integer.toString(g.getScore()));
                repaint();

                if (g.containsValue(2048)) {
                    gameWon();
                }
                if (g.isGameOver()) {
                    gameOver();
                    reset();
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
     * Undo one move.
     */
    public void undo() {
        g.undo();
        repaint();

        requestFocusInWindow();
    }

    /**
     * Makes a game won popup
     */
    public void gameWon() {
        JOptionPane.showMessageDialog(null, "You win! Honestly that's kinda nerdy?!? \n" +
                "Your score was: " + g.getScore()
                );
    }

    public void gameOver() {
        JOptionPane.showMessageDialog(null, "You lost... I'm not mad, just disappointed. \n" +
                "Your score was: " + g.getScore());
    }

    /**
     * Draws the game board.
     *
     * There are many ways to draw a game board. This approach
     * will not be sufficient for most games, because it is not
     * modular. All of the logic for drawing the game board is
     * in this method, and it does not take advantage of helper
     * methods. Consider breaking up your paintComponent logic
     * into multiple methods or classes, like Mushroom of Doom.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        int fontSize = 40;
        Font f = new Font("Comic Sans MS", Font.BOLD, fontSize);
        g.setFont(f);
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        g2.setColor(Color.LIGHT_GRAY);

        g2.drawLine(90, 0, 90, 360);
        g2.drawLine(180, 0, 180, 360);
        g2.drawLine(270, 0, 270, 360);
        g2.drawLine(0, 90, 360, 90);
        g2.drawLine(0, 180, 360, 180);
        g2.drawLine(0, 270, 360, 270);
        g2.setColor(Color.BLACK);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!this.g.tileIsEmpty(i, j)) {
                    g.drawImage(imgMap.get(this.g.tileValue(i, j)), j*90, i*90, null);
                }
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
