package QGame.Pieces;

import QGame.Piece;

public class Identity implements Piece {
    //This class is really long and complicated :) /s
    public String getSymbol() {
        return "I";
    }

    public Piece runOperation(Piece og) {
        return og;
    }
}
