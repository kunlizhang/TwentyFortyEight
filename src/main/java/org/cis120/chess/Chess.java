package org.cis120.chess;

/**
 * This class models the game of chess.
 */
public class Chess {
    private ChessPiece[][] board;
    private int numTurns;
    private boolean player1;

    public Chess() {
        board = new ChessPiece[9][9];
        reset();
    }

    public void reset() {
        // @todo write this function
    }

    public ChessPiece[][] getChessBoard() {
        ChessPiece[][] returnBoard = new ChessPiece[9][9];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                returnBoard[i][j] = this.board[i][j];
            }
        }
        return returnBoard;
    }

    public void remove(int x, int y) {
        board[x][y] = null;
    }
}
