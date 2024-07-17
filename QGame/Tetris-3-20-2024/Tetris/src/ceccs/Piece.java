package ceccs;

import javafx.scene.paint.Color;

public class Piece {
    Block[] blocks;
    int x,y;
    Tetris app;
    char type;

    public Piece(char type, Tetris app){
        this.app = app;
        this.type = type;
        blocks = new Block[4];
        y = 1;
        x = 4;
        blocks[0] = app.gameGrid.blocks[x][y];
        switch (type){
            case 'o':
                blocks[1] = app.gameGrid.blocks[x+1][y];
                blocks[2] = app.gameGrid.blocks[x+1][y-1];
                blocks[3] = app.gameGrid.blocks[x][y-1];
                break;
            case 'i':
                blocks[1] = app.gameGrid.blocks[x][y-1];
                blocks[2] = app.gameGrid.blocks[x][y+1];
                blocks[3] = app.gameGrid.blocks[x][y+2];
                break;
            case 's':
                blocks[1] = app.gameGrid.blocks[x-1][y];
                blocks[2] = app.gameGrid.blocks[x][y-1];
                blocks[3] = app.gameGrid.blocks[x+1][y-1];
                break;
            case 'z':
                blocks[1] = app.gameGrid.blocks[x+1][y];
                blocks[2] = app.gameGrid.blocks[x][y-1];
                blocks[3] = app.gameGrid.blocks[x-1][y-1];
                break;
            case 'l':
                blocks[1] = app.gameGrid.blocks[x][y+1];
                blocks[2] = app.gameGrid.blocks[x][y-1];
                blocks[3] = app.gameGrid.blocks[x+1][y+1];
                break;
            case 'j':
                blocks[1] = app.gameGrid.blocks[x][y+1];
                blocks[2] = app.gameGrid.blocks[x][y-1];
                blocks[3] = app.gameGrid.blocks[x-1][y+1];
                break;
            case 't':
                blocks[1] = app.gameGrid.blocks[x][y+1];
                blocks[2] = app.gameGrid.blocks[x+1][y];
                blocks[3] = app.gameGrid.blocks[x-1][y];
                break;

        }
        fillBlocks();

    }
    public void moveDown(){
        emptyBlocks();
        for (int i = 0; i < 4; i++) {
            blocks[i] = app.gameGrid.blocks[blocks[i].x][blocks[i].y + 1];
        }
        fillBlocks();
        y++;
    }


    public void fillBlocks(){
        for (int i = 0; i < 4; i++) {
            blocks[i].setEmpty(false);
        }
    }
    public void emptyBlocks(){
        for (int i = 0; i < 4; i++) {
            blocks[i].setEmpty(true);
        }
    }

}

    /*
    public void rightLBlock(Tetris app) {
        //graph is 0 - 19 for y and 0-9 for x
        this.app = app;

        blocks = new Block[4];
        for (int i = 0; i < 4; i++) {
            Block block1 = new Block(4, 0 + i, app);
            block1.setFill(Color.BLUE);
            this.app = app;
            Block block2 = new Block(4, 1+ i, app);
            block2.setFill(Color.BLUE);
            this.app = app;
            Block block3 = new Block(4, 2 + i, app);
            block3.setFill(Color.BLUE);
            this.app = app;
            Block block4 = new Block(5, 0 + i, app);
            block4.setFill(Color.BLUE);
            *//*blocks[i] = block1;
            blocks[i] = block2;
            blocks[i] = block3;
            blocks[i] = block4;*//*
                i++;
        }*/

        /*Block block1 = new Block(4, 0, app);
        block1.setFill(Color.BLUE);
        this.app = app;
        Block block2 = new Block(4, 1, app);
        block2.setFill(Color.BLUE);
        this.app = app;
        Block block3 = new Block(4, 2, app);
        block3.setFill(Color.BLUE);
        this.app = app;
        Block block4 = new Block(5, 0, app);
        block4.setFill(Color.BLUE);*/
