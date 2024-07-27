package QGame;

public class Hadamard extends Gate{
    public Hadamard() {
        super("H");
    }

    public Space runOperation(Space input) {
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
