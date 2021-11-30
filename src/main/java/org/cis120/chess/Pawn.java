package org.cis120.chess;

/**
 * The class that handles the behaviour of the Pawn piece.
 */
public class Pawn implements ChessPiece {
    private boolean white;
    private int x;
    private int y;
    private boolean hasMoved;
    private PieceType pieceType;

    public Pawn(boolean white, int x, int y) {
        this.white = white;
        this.hasMoved = false;
        this.pieceType = PieceType.PAWN;
        this.x = x;
        this.y = y;
    }

    /**
     * Checks whether a given move is valid for this chess piece.
     * @param x1    New x value position
     * @param y1    New y value position
     * @param board The board with the chess pieces.
     * @return      Boolean for whether a given move is valid
     */
    @Override
    public boolean validMove(int x1, int y1, ChessPiece[][] board) {
        if (x1 < 0 || x1 >= 9 || y1 < 0 || y1 >= 9) {
            return false;
        }
        if (y1 == this.y + 2 && this.x == x1 && !this.hasMoved && board[this.x][this.y + 1] == null) {
            return true;
        } else if (y1 == this.y + 1 && this.x == x1 && board[this.x][this.y + 1] == null) {
            return true;
        } else if (y1 == this.y + 1 && (
                this.x + 1 == x1 || this.x - 1 == x1) &&
                board[x1][this.y].getType() == PieceType.PAWN && board[x1][this.y].isWhite() != this.isWhite()
                // @todo fix en passant so that it is the fifth rank only
        ) {
            return true;
        } else if (y1 == this.y + 1 && (
                this.x + 1 == x1 || this.x - 1 == x1) && board[x1][y1].isWhite() != this.isWhite()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Removes a piece from the chess board
     * @param c The chess game that the piece is being removed from.
     */
    @Override
    public void remove(Chess c) {
        c.remove(this.x, this.y);
    }

    /**
     * Moves a piece, with capturing and en passant.
     * @param x1    The new x coordinate
     * @param y1    The new y coordinate
     * @param board The game board
     */
    @Override
    public void move(int x1, int y1, ChessPiece[][] board) {
        this.hasMoved = true;

        if (this.validMove(x1, y1, board)) {
            if (y1 == this.y + 1 && (this.x + 1 == x1 || this.x - 1 == x1)) {
                board[x1][this.y] = null;
            }
            board[this.x][this.y] = null;
            this.x = x1;
            this.y = y1;
            board[this.x][this.y] = this;
        }
    }

    /**
     * Tells us whether a piece is white
     * @return Boolean, true if it is white
     */
    @Override
    public boolean isWhite() {
        return this.white;
    }

    /**
     * Tells us the type of the piece
     * @return Enum of the piece type.
     */
    @Override
    public PieceType getType() {
        return this.pieceType;
    }
}
