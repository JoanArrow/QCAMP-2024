package QGame;

public interface Piece {
    public default Piece runOperation(Piece og) {
        return og;
    }
    public String getSymbol();
    public default boolean isSuperposition() {
        return false;
    }
}
