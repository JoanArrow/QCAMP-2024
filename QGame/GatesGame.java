package QGame;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class GatesGame {
    private Scanner input;
    private Space[][] grid;
    private String[] correctAnswer;
    private String[] guess;
    private int speed;
    private int layers;

    public GatesGame() {
        grid = new Space[3][20];
        correctAnswer = new String[3];
        input = new Scanner(System.in);
        guess = new String[] {"0", "0", "0"};
        speed = 650;
        layers = 1;

        for(int col = 0; col < grid[0].length; col++) {
            if(col >= 18 - layers && col < 18) {
                for(int row = 0; row < 3; row++) {
                    grid[row][col] = new Gate("I");
                }
            } else {
                for(int row = 0; row < 3; row++) {
                    grid[row][col] = new Space();
                }
            }
        }
    }

    public void spawnNewQubits() {
        for(int i = 0; i < 3; i++) {
            String num = Math.random() > 0.5 ? "0" : "1";
            grid[i][0] = new Qubit(num);
            correctAnswer[i] = num;
        }

    }

    public void shiftRight() {
        for(int i = 17 - layers; i >= 1; i--) {
            for(int j = 0; j < 3; j++) {
                grid[j][i] = grid[j][i - 1];
            }
        }
        for(int i = 0; i < 3; i++) {
            grid[i][0] = new Space();
        }
    }

    public void getNextGuess() {
        //unfunctional rn but Im tired so good bye
        String entered = input.nextLine();
        entered = entered.substring(0, 3);
        guess = entered.split("");
        getNextGuess();
    }

    public void checkSpeedRequested() {
    }

    public void ticker() {
        TimerTask step = new TimerTask() {
            public void run() {
                shiftRight();
                print();
            }
        };

        Timer timeToNext = new Timer("Time to Next Tick");
        timeToNext.schedule(step, 0, speed);
    }

    public void print() {
        //Sorry for the awful method, I just wanted a quick display :)
        for(int i = 0; i < 100; i++) {
            System.out.println();
        }

        for(int i = 0; i < 42; i++) {
            System.out.print("-");
        }
        System.out.println();
        for(int i = 0; i < 3; i++) {
            Space[] row = grid[i];
            System.out.print("|");
            for(Space s : row) {
                System.out.print(" " + s.getSymbol());
                if(s.getSymbol().equals("")) {
                    System.out.print(" ");
                }
            }
            System.out.println("| " + guess[i]);
        }
        for(int i = 0; i < 42; i++) {
            System.out.print("-");
        }
        System.out.println();
        for(int i = 0; i < 3; i++) {
            System.out.print(guess[i]);
        }
    }

    public static void main(String[] args) {
        GatesGame game = new GatesGame();
        game.spawnNewQubits();
        game.print();
        game.ticker();
        game.getNextGuess();
    }
}
