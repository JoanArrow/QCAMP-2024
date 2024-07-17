package ceccs;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Tetris extends Application {

    AnimationTimer aTimer;
    Button[] controls;
    double width = 350, height = 450;
    //ArrayList<Block> blocks;
    GameGrid gameGrid;

    public static void main(String[] args) {launch(args);}

    @Override
    public void start(Stage primaryStage) throws Exception {
        gameGrid = new GameGrid(this);

        Piece pi = new Piece('i',this);


        Scene scn = new Scene(gameGrid);
        primaryStage.setScene(scn);
        primaryStage.setTitle("Blue Version of Tetris ");

        //primaryStage.setResizable(false);
        gameGrid.resize(10, 20);
        primaryStage.show();

        aTimer = new AnimationTimer() {
            long prevTimeNanos, blockSpeed;

            @Override
            public void handle(long now) {
                prevTimeNanos = now;
                pi.moveDown();
            }

            @Override
            public void start() {
                super.start();
                prevTimeNanos = System.nanoTime();
            }
        };
        aTimer.start();
    }
}

/*todo:
    -Tetris Class
        -time control
        -play controls
    -Piece class:
        -fill out case switch for block types
        -rotate cw / ccw
    -To be determined class...
        -check for move down okay?
        -check rotate okay?
        -fixBlocks method
 */

