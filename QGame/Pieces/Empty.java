package QGame.Pieces;

import QGame.Piece;

public class Empty implements Piece{
    public Piece runOperation(Piece og) {
        return this;
    }
    public String getSymbol() {
        return "";
    }
}
