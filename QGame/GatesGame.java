package QGame;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GatesGame implements Runnable {
    private Space[][] grid;
    private String correctAnswer;
    private String[] nonSpAnswers;
    private String[] hasSpAnswers;
    private String[] hasSp2Row;
    private String[] nonSp2Row;
    private String[] allStates;
    private String guess;
    private String[] guesses;
    private int speed;
    private int layers;
    private int rows;
    private int lives;
    private JFrame frame;
    private JTextArea text;
    private JPanel panel;
    private int amountDone;
    private int score;
    private boolean instantGuessRequested;

    public GatesGame() {
        grid = new Space[3][20];
        guesses = new String[] {"00", "01", "10", "11"};
        nonSpAnswers = new String[] {"000", "001", "010", "011", "100", "101", "110", "111"};
        hasSpAnswers = new String[64];
        hasSp2Row = new String[] {"00", "01", "0+", "0-", "10", "11", "1+", "1-", "+0", "+1", "++", "+-", "-0", "-1", "-+", "--"};
        nonSp2Row = new String[] {"00", "01", "10", "11"};
        amountDone = 0;
        instantGuessRequested = false;
        score = 0;
        int numSoFar = 0;
        allStates = new String[] {"0", "1", "+", "-"};
        for(int i = 0; i < 4; i++) {
            for(int j = 0; j < 4; j++) {
                for(int l = 0; l < 4; l++) {
                    String toAdd = "";
                    toAdd += allStates[i];
                    toAdd += allStates[j];
                    toAdd += allStates[l];
                    hasSpAnswers[numSoFar] = toAdd;
                    toAdd = "";
                    numSoFar++;
                }
            }
        }
        guess = "NaN";
        speed = 650;
        layers = 1;
        rows = 1;
        lives = 3;
        frame = new JFrame("Gates Game");
        frame.setVisible(true);
        frame.setSize(new Dimension(1000, 600));
        frame.setLocation(new Point(300, 300));
        text = new JTextArea("helloooo");
        text.setFont(new Font("Consolas", Font.PLAIN, 20));
        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.getContentPane().add(panel);
        panel.add(text, BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        text.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent e) {
                switch (Character.toString(e.getKeyChar())) {
                    case "q":
                        guess = guesses[0];
                        break;
                    case "w":
                        guess = guesses[1];
                        break;
                    case "e":
                        guess = guesses[2];
                        break;
                    case "r":
                        guess = guesses[3];
                        break;
                    case " ":
                        instantGuessRequested = true;
                        break;
                }
            }
        });

        for(int col = 0; col < grid[0].length; col++) {
            if(col >= 18 - layers && col < 18) {
                for(int row = 0; row < rows; row++) {
                    if(Math.random() < 0.33) {
                        grid[row][col] = new Identity();
                    } else if (Math.random() < 0.5) {
                        grid[row][col] = new Not();
                    } else {
                        grid[row][col] = new Hadamard();
                    }
                }
            } else {
                for(int row = 0; row < rows; row++) {
                    grid[row][col] = new Space();
                }
            }
        }
    }

    public void clearBoard() {
        for(int i = 0; i < rows; i++) {
            for(int j = 0; j < 20; j++) {
                grid[i][j] = new Space();
            }
        }
    }

    public void spawnNewQubits() {
        correctAnswer = "";
        guess = "NaN";
        if(amountDone < 6) {
            clearBoard();
            switch(amountDone) {
                case 0: grid[0][17] = new Identity(); grid[0][0] = new Qubit("0"); break;
                case 1: grid[0][17] = new Identity(); grid[0][0] = new Qubit("1"); break;
                case 2: grid[0][17] = new Not(); grid[0][0] = new Qubit("0"); break;
                case 3: grid[0][17] = new Not(); grid[0][0] = new Qubit("1"); break;
                case 4: grid[0][17] = new Hadamard(); grid[0][0] = new Qubit("0"); break;
                case 5: grid[0][17] = new Hadamard(); grid[0][0] = new Qubit("1"); break;
                default: System.out.println("Error in spawning qubits and gates (first 6 rounds)");
            }
            correctAnswer += grid[0][17].runOperation(grid[0][0]).getSymbol();
        } else {
            clearBoard();
            rows = 3;
            if(amountDone < 12 && amountDone >= 6) {
                rows = 2;
            }
            if(amountDone == 6 || amountDone == 12) {
                for(int i = 0; i < rows; i++) {
                    for(int j = 0; j < 20; j++) {
                        grid[i][j] = new Space();
                    }
                }
            }
            for(int row = 0; row < rows; row++) {
                if(Math.random() < 0.33) {
                    grid[row][18 - layers] = new Identity();
                } else if (Math.random() < 0.5) {
                    grid[row][18 - layers] = new Not();
                } else {
                    grid[row][18 - layers] = new Hadamard();
                }
            }
            for(int i = 0; i < rows; i++) {
                String num = Math.random() > 0.75 ? "0" : Math.random() > 0.67 ? "1" : Math.random() > 0.5 ? "+" : "-";
                grid[i][0] = new Qubit(num);
                //correctAnswer += num; //temp
                correctAnswer += grid[i][18 - layers].runOperation(grid[i][0]).getSymbol();
            }
        }
        fillGuesses();
        amountDone++;
    }

    public boolean hasSuperposition() {
        for(int i = 0; i < rows; i++) {
            if(grid[i][18 - layers].getSymbol().equals("H")) {
                return true;
            }
        }
        for(int i = 0; i < rows; i++) {
            if(grid[i][getQubitPosition()].isSuperposition()) {
                return true;
            }
        }
        return false;
    }

    public void fillGuesses() {
        if(amountDone < 6) {
            guesses = allStates;
        } else{
            String[] spList = rows == 2 ? hasSp2Row : hasSpAnswers;
            String[] nonSpList = rows == 2 ? nonSp2Row : nonSpAnswers;
            ArrayList<String> unused = new ArrayList<>();
            if(hasSuperposition()) {
                for(int i = 0; i < spList.length; i++) {
                    unused.add(spList[i]);
                }
            } else {
                for(int i = 0; i < nonSpList.length; i++) {
                    unused.add(nonSpList[i]);
                }
            }
            int cPos = (int) (Math.random() * 4);
            unused.remove(unused.indexOf(correctAnswer));
            guesses[cPos] = correctAnswer;
            for(int i = 0; i < 4; i++) {
                if(i != cPos) {
                    int ranUnused = (int) (Math.random() * unused.size());
                    guesses[i] = unused.get(ranUnused);
                    unused.remove(ranUnused);
                }
            }
        }
    }

    public void shiftRight() {
        for(int i = 17 - layers; i >= 1; i--) {
            for(int j = 0; j < rows; j++) {
                grid[j][i] = grid[j][i - 1];
            }
        }
        for(int i = 0; i < rows; i++) {
            grid[i][0] = new Space();
        }
    }

    public boolean currentGuessCorrect() {
        return guess.equals(correctAnswer);
    }

    public int getQubitPosition() {
        for(int i = 0; i < 18; i++) {
            if(grid[0][i].getSymbol().length() > 0) {
                return i;
            }
        }
        return -1;
    }

    public void checkWinOrLoss() {
        if(getQubitPosition() == 17 || instantGuessRequested) {
            if(!currentGuessCorrect()) {
                lives--;
                //System.out.println("Game Over!");
                text.setText("Game Over!");
                if(lives == 0) {
                    panel.remove(text);
                }
            } else {
                score += rows;
            }
            instantGuessRequested = false;
            spawnNewQubits();
        }
    }

    public void ticker() {
        TimerTask step = new TimerTask() {
            public void run() {
                checkWinOrLoss();
                shiftRight();
                print();
            }
        };

        Timer timeToNext = new Timer("Time to Next Tick");
        timeToNext.schedule(step, 0, speed);
    }

    public void print() {
        String newFrame = "";
        newFrame += "+";
        for(int i = 0; i < 47; i++) {
            newFrame += "-";
        }
        newFrame += "+\n";
        newFrame += "|Lives Remaining: " + lives + "                             |\n";
        newFrame += "|Score:           " + score;
        newFrame += score < 10 ? "                             |\n" : "                            |\n";
        newFrame += "|Q: |" + guesses[0] + ">, W: |" + guesses[1] + ">, E: |" + guesses[2] + ">, R: |" + guesses[3] + ">";
        newFrame += rows == 1 ? "                 |\n" : rows == 2 ? "             |\n" : "         |\n";
        newFrame += "+";
        for(int i = 0; i < 47; i++) {
            newFrame += "-";
        }
        newFrame += "+\n";
        for(int i = 0; i < rows; i++) {
            Space[] row = grid[i];
            newFrame += "|";
            if(getQubitPosition() == 17) {
                newFrame += " ";
            }
            for(Space s : row) {
                String symbol = s.getSymbol();
                if(symbol.equals("0") || symbol.equals("1") || symbol.equals("+") || symbol.equals("-")) {
                    newFrame += "|" + symbol + ">";
                } else {
                    newFrame += " " + symbol;
                    if(symbol.equals("")) {
                        newFrame += " ";
                    }
                }
            }
            newFrame += guess.equals("NaN") ? "=     |\n" : "= |" + guess.substring(i, i + 1) + "> |\n";
        }
        newFrame += "+";
        for(int i = 0; i < 47; i++) {
            newFrame += "-";
        }
        newFrame += "+\n";
        text.setText(newFrame);
    }

    public void run() {
        spawnNewQubits();
        print();
        ticker();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new GatesGame());
    }
}
