package ceccs;

import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Block extends Rectangle {
        Tetris app;
        int x, y;
        private boolean empty, fixed;
        static double size = 40;
        static Color colorEmpty = Color.BLACK;
        static Color colorNotEmpty = Color.BLUE;
        static Color colorBorder = Color.LIGHTBLUE;

        public Block(int x , int y, Tetris app){
                this.app = app;
                this.x = x;
                this.y = y;
                setWidth(size);
                setHeight(size);
                //app.gameGrid.getChildren().add(this);
                //updating the position
                GridPane.setColumnIndex(this, x);
                GridPane.setRowIndex(this, y);
                setStroke(colorBorder);
                setStrokeWidth(4);
        }

        void setEmpty(boolean empty){
                this.empty = empty;
                if(empty) setFill(colorEmpty);
                else setFill(colorNotEmpty);
        }

        boolean isEmpty(){
                return empty;
        }
/*
        public Block(Tetris app) {
                this.app = app;
                x = Math.random() * app.wPhys;
                y = Math.random() * app.hPhys;
                vx = 0;
                vy = 0;
                h = defaultSize;
                w = defaultSize;

                rect = new Rectangle(w, h);
                rect.setFill(Color.DARKCYAN);

                app.physPane.getChildren().add(rect);
                updateGraphics();
        }
        public Block(double x, double y, Tetris app){
                this.app = app;
                this.x = x;
                this.y = y;
                vx = 0;
                vy = 0;

                rect = new Rectangle(defaultSize, defaultSize);
                Color randColor = new Color(Math.random(), Math.random(), Math.random(), 1);
                rect.setFill(randColor);
                app.physPane.getChildren().add(rect);
                updateGraphics();
        }

 */


}
