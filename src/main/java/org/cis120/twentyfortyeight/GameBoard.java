package org.cis120.twentyfortyeight;

import org.cis120.twentyfortyeight.TwentyFortyEight;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Objects;

public class GameBoard extends JPanel {

    private TwentyFortyEight g; // model for the game
    private JLabel score; //Label for the score

    // Game constants
    public static final int BOARD_WIDTH = 600;
    public static final int BOARD_HEIGHT = 600;

    /**
     * Initializes the game board.
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

    public void undo() {
        g.undo();
        repaint();

        requestFocusInWindow();
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
        // Draws the grid @todo make it use file IO.
        g2.drawLine(150, 0, 150, 600);
        g2.drawLine(300, 0, 300, 600);
        g2.drawLine(450, 0, 450, 600);
        g2.drawLine(0, 150, 600, 150);
        g2.drawLine(0, 300, 600, 300);
        g2.drawLine(0, 450, 600, 450);

        g2.setColor(Color.BLACK);
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (!this.g.tileIsEmpty(i, j)) {
                    g.drawString(Integer.toString(this.g.tileValue(i, j)),
                            30 + 150 * j, 30 + 150 * i);
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
