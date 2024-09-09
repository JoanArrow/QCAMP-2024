package QGame.Pieces;

import QGame.Piece;

public class Hadamard implements Piece {

    public String getSymbol() {
        return "H";
    }

    public Piece runOperation(Piece input) {
        switch (input.getSymbol()) {
            case "0":
                return new Qubit("+");
            case "1":
                return new Qubit("-");
            case "+":
                return new Qubit("0");
            case "-":
                return new Qubit("1");
            default:
                System.out.println("Error on hadamard gate");
                return input;
        }
    }
}
