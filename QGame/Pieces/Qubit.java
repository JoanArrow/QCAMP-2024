package QGame.Pieces;

import QGame.Piece;

public class Qubit implements Piece {
    private String state; //not using ints becuase superposition (+, -, i, -i) (are we doing i and -i??)

    public Qubit(String state) {
        this.state = state;
    }

    public Qubit() {
        this.state = "0";
    }

    public String getSymbol() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public boolean isSuperposition() {
        return state.equals("+") || state.equals("-");
    }
}
