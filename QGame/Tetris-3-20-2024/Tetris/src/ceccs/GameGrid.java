package ceccs;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class GameGrid extends GridPane {
    Tetris app;
    Block[][] blocks;
    static int gridHeight = 20, gridWidth = 10;

    public GameGrid(Tetris app){
        blocks = new Block[gridWidth][gridHeight];
        this.app = app;
        for (int i = 0; i < gridWidth; i++) {
            getColumnConstraints().add(new ColumnConstraints(Block.size));
        }
        for (int i = 0; i < gridHeight; i++) {
            getRowConstraints().add(new RowConstraints(Block.size));
        }
        setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID,
                null, new BorderWidths(5))));

        for (int i = 0; i < gridWidth; i++) {
            for (int j = 0; j < gridHeight; j++) {
                Block b = new Block(i, j, app);
                getChildren().add(b);
                blocks[i][j] = b;

//                b.setFill(Color.TRANSPARENT);
            }
        }
    }
}
