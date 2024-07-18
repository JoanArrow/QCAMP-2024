package QGame;

public class Gate extends Space {
    public String gateSymbol;

    public Gate(String symbol) {
        gateSymbol = symbol;
    }

    public String getSymbol() {
        return gateSymbol;
    }
}
