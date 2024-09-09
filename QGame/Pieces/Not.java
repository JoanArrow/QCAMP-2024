package QGame.Pieces;

import QGame.Piece;

public class Not implements Piece {
    public String getSymbol() {
        return "X";
    }

    public Piece runOperation(Piece input) {
        if(input.isSuperposition()) {
            return input;
        }
        if(input.getSymbol().equals("0")) {
            return new Qubit("1");
        } else {
            return new Qubit("0");
        }
    }
}
