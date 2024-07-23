package QGame;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.swing.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class GatesGame implements Runnable {
    private Space[][] grid;
    private String correctAnswer;
    private String[] nonSpAnswers;
    private String guess;
    private String[] guesses;
    private int speed;
    private int layers;
    private int rows;
    private int lives;
    private JFrame frame;
    private JTextArea text;
    private JPanel panel;

    public GatesGame() {
        grid = new Space[3][20];
        guesses = new String[] {"00", "01", "10", "11"};
        nonSpAnswers = new String[] {"000", "001", "010", "011", "100", "101", "110", "111"};
        guess = "NaN";
        speed = 650;
        layers = 1;
        rows = 3;
        lives = 3;
        
        ImageIcon WImg = new ImageIcon("C:/Users/dhall/OneDrive/Documents/GitHub/QCAMP-2024/QGame/WImagr.jpg");
        
        
        frame = new JFrame("Gates Game");
        frame.add(new JLabel(WImg));
        frame.pack();
        frame.setVisible(true);
        frame.setSize(new Dimension(500, 300));
        frame.setLocation(new Point(300, 300));
        text = new JTextArea("helloooo");
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
                }
            }
        });

        for(int col = 0; col < grid[0].length; col++) {
            if(col >= 18 - layers && col < 18) {
                for(int row = 0; row < rows; row++) {
                    grid[row][col] = new Gate("I");
                }
            } else {
                for(int row = 0; row < rows; row++) {
                    grid[row][col] = new Space();
                }
            }
        }
    }

    public void spawnNewQubits() {
        correctAnswer = "";
        guess = "NaN";
        for(int i = 0; i < rows; i++) {
            String num = Math.random() > 0.5 ? "0" : "1";
            grid[i][0] = new Qubit(num);
            correctAnswer += num; //temp
        }
        fillGuesses();
    }

    public void fillGuesses() {
        //if you dont use the h gate:
        ArrayList<String> unused = new ArrayList<>();
        for(int i = 0; i < nonSpAnswers.length; i++) {
            unused.add(nonSpAnswers[i]);
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
        if(getQubitPosition() == 17) {
            if(!currentGuessCorrect()) {
                lives--;
                //System.out.println("Game Over!");
                text.setText("Game Over!");
                if(lives == 0) {
                    panel.remove(text);
                }
            }
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
        //Sorry for the awful method, I just wanted a quick display :)
        String newFrame = "";
/*
        for(int i = 0; i < 100; i++) {
            //System.out.println();
            newFrame += "\n";
        }
            */
        


        for(int i = 0; i < 39; i++) {
            //System.out.print("-");
            newFrame += "-";
        }
        
        //System.out.println();
        newFrame += "\n";
        //System.out.println("Correct answer (for testing): |" + correctAnswer + ">");
        newFrame += "Correct answer (for testing): |" + correctAnswer + ">\n";
        //System.out.println("Lives Remaining: " + lives);
        newFrame += "Lives Remaining: " + lives + "\n";
        //System.out.println("Q: |" + guesses[0] + ">, W: |" + guesses[1] + ">, E: |" + guesses[2] + ">, R: |" + guesses[3] + ">");
        newFrame += "Q: |" + guesses[0] + ">,W: |" + guesses[1] + ">, E: |" + guesses[2] + ">, R: |" + guesses[3] + ">\n";
        for(int i = 0; i < 39; i++) {
        //  System.out.print("-");
            newFrame += "-";
        }
        //System.out.println();
        newFrame += "\n";
        for(int i = 0; i < rows; i++) {
            Space[] row = grid[i];
            //System.out.print("|");
            newFrame += "|";
            for(Space s : row) {
                String symbol = s.getSymbol();
                if(symbol.equals("0") || symbol.equals("1") || symbol.equals("+") || symbol.equals("-")) {
                    //System.out.print("|" + symbol + ">");
                    newFrame += "|" + symbol + ">";
                } else {
                    //System.out.print(" " + s.getSymbol());
                    newFrame += " " + symbol;
                    if(symbol.equals("")) {
                        //System.out.print(" ");
                        newFrame += " ";
                    }
                }
            }
            //System.out.println(guess.equals("NaN") ? "|" : "|   |" + guess.substring(i, i + 1) + ">");
            newFrame += guess.equals("NaN") ? "|\n" : "|   |" + guess.substring(i, i + 1) + ">\n";
        }
        for(int i = 0; i < 39; i++) {
            //System.out.print("-");
            newFrame += "-";
        }
        //System.out.println();
        newFrame += "\n";
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
