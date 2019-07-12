package Core;

import TileEngine.TERenderer;
import TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;

public class MoveControl {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    WorldGenerator myWorld;

    MoveControl(WorldGenerator w) {
        myWorld = w;
    }

    //set the move for player 1
    private void helpPlayer1Move(char c) {
        Interactivity.player1Move(myWorld.getMyWorld(), myWorld.getPlayer1Pos(), c);
        if (myWorld.getPlayer1Pos().getBlink()) {
            for (int i = 0; i < myWorld.getBlinkPos().size(); i++) {
                if (myWorld.getBlinkPos().get(i).getX() == myWorld.getPlayer1Pos().getX()
                        && myWorld.getBlinkPos().get(i).getY() == myWorld.getPlayer1Pos().getY()) {
                    myWorld.getBlinkPos().remove(i);
                }
            }
            myWorld.getMyWorld()[myWorld.getPlayer1Pos().getX()][myWorld.getPlayer1Pos().getY()]
                    = Tileset.FLOOR;
            myWorld.setPlayer1Pos(Decoration.setPlayer(myWorld.getMyWorld(), myWorld.getRANDOM(),
                    WIDTH, HEIGHT, Tileset.PLAYER));
        }
        if (myWorld.getPlayer1Pos().getKey()) {
            for (int i = 0; i < myWorld.getKeyPos().size(); i++) {
                if (myWorld.getKeyPos().get(i).getX() == myWorld.getPlayer1Pos().getX()
                        && myWorld.getKeyPos().get(i).getY() == myWorld.getPlayer1Pos().getY()) {
                    myWorld.getKeyPos().remove(i);
                }
            }
            myWorld.getMyWorld()[myWorld.getPlayer1Pos().getX()][myWorld.getPlayer1Pos().getY()]
                    = Tileset.PLAYER;
            boolean tempCheck1 = false;
            int count = 0;
            while (!tempCheck1) {
                if (myWorld.getMyWorld()[myWorld.getLockDoorPos().get(count).getX()]
                        [myWorld.getLockDoorPos().get(count).getY()].description().equals
                        ("locked door")) {
                    myWorld.getMyWorld()[myWorld.getLockDoorPos().get(count).getX()]
                            [myWorld.getLockDoorPos().get(count).getY()] = Tileset.UNLOCKED_DOOR;
                    tempCheck1 = true;
                }
                count++;
            }
            myWorld.getPlayer1Pos().setKey(false);
        }
        if (myWorld.getMyWorld()[myWorld.getPlayer1Pos().getX()][myWorld.getPlayer1Pos().getY()]
                == Tileset.HUNTER) {
            myWorld.getPlayer1Pos().setLose();
        }
    }

    //set a move for the player2
    private void helpPlayer2Move(char c) {
        Interactivity.player2Move(myWorld.getMyWorld(), myWorld.getPlayer2Pos(), c);
        if (myWorld.getPlayer2Pos().getBlink()) {
            for (int i = 0; i < myWorld.getBlinkPos().size(); i++) {
                if (myWorld.getBlinkPos().get(i).getX() == myWorld.getPlayer2Pos().getX()
                        && myWorld.getBlinkPos().get(i).getY() == myWorld.getPlayer2Pos().getY()) {
                    myWorld.getBlinkPos().remove(i);
                }
            }
            myWorld.getMyWorld()[myWorld.getPlayer2Pos().getX()][myWorld.getPlayer2Pos().getY()]
                    = Tileset.FLOOR;
            myWorld.setPlayer2Pos(Decoration.setPlayer(myWorld.getMyWorld(), myWorld.getRANDOM(),
                    WIDTH, HEIGHT, Tileset.PLAYER));
        }
        if (myWorld.getPlayer2Pos().getKey()) {
            for (int i = 0; i < myWorld.getKeyPos().size(); i++) {
                if (myWorld.getKeyPos().get(i).getX() == myWorld.getPlayer2Pos().getX()
                        && myWorld.getKeyPos().get(i).getY() == myWorld.getPlayer2Pos().getY()) {
                    myWorld.getKeyPos().remove(i);
                }
            }
            myWorld.getMyWorld()[myWorld.getPlayer2Pos().getX()][myWorld.getPlayer2Pos().getY()]
                    = Tileset.PLAYER2;
            boolean tempCheck = false;
            int i = 0;
            while (!tempCheck) {
                if (myWorld.getMyWorld()[myWorld.getLockDoorPos().get(i).getX()]
                        [myWorld.getLockDoorPos().get(i).getY()].description().equals
                        ("locked door")) {
                    myWorld.getMyWorld()[myWorld.getLockDoorPos().get(i).getX()]
                            [myWorld.getLockDoorPos().get(i).getY()] = Tileset.UNLOCKED_DOOR;
                    tempCheck = true;
                }
                i++;
            }
            myWorld.getPlayer2Pos().setKey(false);
        }
        if (myWorld.getMyWorld()[myWorld.getPlayer2Pos().getX()]
                [myWorld.getPlayer2Pos().getY()] == Tileset.HUNTER) {
            myWorld.getPlayer2Pos().setLose();
        }
    }

    //set a move for the hunter
    private void helpHunterMove(char c) {
        Interactivity.hunterMove(myWorld.getMyWorld(), myWorld.getHunterPos(), c);
        if (myWorld.getHunterPos().getBlink()) {

            for (int i = 0; i < myWorld.getBlinkPos().size(); i++) {
                if (myWorld.getBlinkPos().get(i).getX() == myWorld.getHunterPos().getX()
                        && myWorld.getBlinkPos().get(i).getY() == myWorld.getHunterPos().getY()) {
                    myWorld.getBlinkPos().remove(i);
                }
            }
            myWorld.getMyWorld()[myWorld.getHunterPos().getX()]
                    [myWorld.getHunterPos().getY()] = Tileset.FLOOR;
            myWorld.setHunterPos(Decoration.setPlayer(myWorld.getMyWorld(),
                    myWorld.getRANDOM(), WIDTH, HEIGHT, Tileset.PLAYER));
        }
        if (myWorld.getHunterPos().getKey()) {
            for (int i = 0; i < myWorld.getKeyPos().size(); i++) {
                if (myWorld.getKeyPos().get(i).getX() == myWorld.getHunterPos().getX()
                        && myWorld.getKeyPos().get(i).getY()
                        == myWorld.getHunterPos().getY()) {
                    myWorld.getKeyPos().remove(i);
                }
            }
            myWorld.getMyWorld()[myWorld.getHunterPos().getX()]
                    [myWorld.getHunterPos().getY()] = Tileset.HUNTER;
        }
    }

    //set the moves for players in general
    private void setMove(char c) {
        helpPlayer1Move(c);
        helpPlayer2Move(c);
        helpHunterMove(c);
    }

    public void drawEverything(TERenderer t, int height) {
        char c = 0;
        if (StdDraw.hasNextKeyTyped()) {
            c = StdDraw.nextKeyTyped();
        }
        while (true) { //c != 'q') {
            t.renderFrame(myWorld.getMyWorld());
            if ((myWorld.getPlayer1Pos().getWin() && myWorld.getPlayer2Pos().getWin())
                    || (myWorld.getPlayer1Pos().getLose() && myWorld.getPlayer2Pos().getLose())
                    || (myWorld.getPlayer1Pos().getLose() && myWorld.getPlayer2Pos().getWin())
                    || (myWorld.getPlayer1Pos().getWin() && myWorld.getPlayer2Pos().getLose())) {
                StdDraw.clear();
                StdDraw.setPenColor(Color.BLUE);
                Font font = new Font("Arial", Font.BOLD, 35);
                StdDraw.setFont(font);
                StdDraw.text(WIDTH / 2, HEIGHT / 2 + 1, "END GAME");
                StdDraw.text(WIDTH / 2, HEIGHT / 2 - 1, "Thank you!");
                StdDraw.show();
                StdDraw.pause(2000);
                System.exit(0);
            }
            if (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                if (c == 'q') {
                    FileIO.savefile(myWorld);
                    StdDraw.clear(Color.BLACK);
                    System.exit(0);
                }

                //set move for players
                setMove(c);
            }
            Interactivity.mouseLocation(myWorld.getMyWorld(), height);
            StdDraw.show();
        }
    }
}
