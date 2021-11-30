package org.cis120.chess;

/**
 * The interface for a chess piece.
 */
public interface ChessPiece {
    public boolean validMove(int x1, int y1, ChessPiece[][] board);
    public void remove(Chess c);
    public void move(int x1, int y1, ChessPiece[][] board);
    public boolean isWhite();
    public PieceType getType();
}
