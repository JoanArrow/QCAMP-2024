package QGame;

public class Qubit extends Space {
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

    public void notGate() {
        state = state.equals("0") ? "1" : state.equals("1") ? "0" : state;
    }

    public void cNotGate(Qubit control) {
        if(control.getSymbol().equals("1")) {
            notGate();
        } else if (!control.getSymbol().equals("0")) {
            state = "+"; //up to change
        } else {
            System.out.println("Error on cNot gate. Control: " + control.getSymbol() + ", qubit: " + state);
        }
    }

    public void swapGate(Qubit other) {
        String temp = state;
        state = other.getSymbol();
        other.setState(temp);
    }
}
