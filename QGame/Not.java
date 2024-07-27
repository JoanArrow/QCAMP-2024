package QGame;

public class Not extends Gate{
    public Not() {
        super("X");
    }

    public Space runOperation(Space input) {
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
