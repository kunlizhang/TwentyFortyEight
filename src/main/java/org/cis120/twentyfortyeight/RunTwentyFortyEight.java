package org.cis120.twentyfortyeight;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * This class sets up the top-level frame and widgets for the GUI.
 *
 */
public class RunTwentyFortyEight implements Runnable {
    public void run() {
        // NOTE: the 'final' keyword denotes immutability even for local variables.

        // Top-level frame in which game components live
        final JFrame frame = new JFrame("2048");
        frame.setLocation(600, 600);

        // Status panel
        final JPanel status_panel = new JPanel();
        frame.add(status_panel, BorderLayout.SOUTH);
        final JLabel score = new JLabel();
        status_panel.add(score);

        // Game board
        final GameBoard board = new GameBoard(score);
        frame.add(board, BorderLayout.CENTER);

        // Game control panel with buttons
        final JPanel control_panel = new JPanel();
        frame.add(control_panel, BorderLayout.NORTH);

        // Reset button
        final JButton reset = new JButton("Reset");
        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.reset();
            }
        });
        control_panel.add(reset);

        // Undo button
        final JButton undo = new JButton("Undo");
        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.undo();
            }
        });
        control_panel.add(undo);

        // Open saved game button
        final JButton open = new JButton("Open save");
        open.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.openSavedGame();
            }
        });
        control_panel.add(open);

        // Save game button
        final JButton save = new JButton("Save");
        save.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.saveGame();
            }
        });
        control_panel.add(save);

        // Show the high score button
        final JButton scores = new JButton("Scores");
        scores.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                board.getSavedScores();
            }
        });
        control_panel.add(scores);

        // Put the frame on the screen
        frame.setResizable(false);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Start the game
        board.reset();
    }
}