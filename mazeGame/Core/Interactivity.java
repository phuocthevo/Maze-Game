package Core;
import TileEngine.TETile;
import TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;
import java.awt.Color;
import java.awt.Font;

public class Interactivity {
    public static void mouseLocation(TETile[][] world, int height) {
        StdDraw.setPenColor(StdDraw.WHITE);
        int mX = (int) StdDraw.mouseX();
        int mY = (int) StdDraw.mouseY();
        if (mX < world.length && mY < world[0].length) {
            StdDraw.text(15, height - 1, world[mX][mY].description());
        }
    }

    private static boolean moveCheck(TETile[][] w, int x, int y) {
        String temp = w[x][y].description();
        if (!temp.equals("wall") && !temp.equals(("locked door"))) {
            return true;
        }
        return false;
    }

    private static TETile[][] updatePlayerMove(TETile[][] w, int newX, int newY, int oldX,
                                               int oldY, Location p, TETile typeTile) {
        if (w[newX][newY].description().equals("plane")) {
            p.setX(newX);
            p.setY(newY);
            p.setBlink();
            w[oldX][oldY] = Tileset.FLOOR;
            w[newX][newY] = Tileset.FLOOR;
            return w;
        }
        if (w[newX][newY].description().equals("key")) {
            p.setX(newX);
            p.setY(newY);
            p.setKey(true);
            w[oldX][oldY] = Tileset.FLOOR;
            w[newX][newY] = typeTile;
            return w;

        }
        if (w[newX][newY].description().equals("unlocked door")) {
            w[oldX][oldY] = Tileset.FLOOR;
            p.setX(newX);
            p.setY(newY);
            p.setWin();
            return w;
        }
        if (w[newX][newY].description().equals("hunter")) {
            w[oldX][oldY] = Tileset.FLOOR;
            p.setX(newX);
            p.setY(newY);
            p.setLose();
            return w;
        }
        w[newX][newY] = typeTile;
        w[oldX][oldY] = Tileset.FLOOR;
        p.setX(newX);
        p.setY(newY);
        return w;
    }

    public static TETile[][] player1Move(TETile[][] world, Location playerPos, char c) {
        int previousX, previousY, newX, newY;
        previousX = playerPos.getX();
        previousY = playerPos.getY();
        if (!playerPos.getWin() && !playerPos.getLose()) {
            if (c == 'W' || c == 'w') {
                if (moveCheck(world, previousX, previousY + 1)) {
                    newX = previousX;
                    newY = previousY + 1;
                    world = updatePlayerMove(world, newX, newY, previousX,
                            previousY, playerPos, Tileset.PLAYER);
                }
            } else if (c == 'A' || c == 'a') {
                if (moveCheck(world, previousX - 1, previousY)) {
                    newX = previousX - 1;
                    newY = previousY;
                    world = updatePlayerMove(world, newX, newY, previousX,
                            previousY, playerPos, Tileset.PLAYER);
                }
            } else if (c == 'S' || c == 's') {
                if (moveCheck(world, previousX, previousY - 1)) {
                    newY = previousY - 1;
                    newX = previousX;
                    updatePlayerMove(world, newX, newY, previousX,
                            previousY, playerPos, Tileset.PLAYER);
                }
            } else if (c == 'D' || c == 'd') {
                if (moveCheck(world, previousX + 1, previousY)) {
                    newX = previousX + 1;
                    newY = previousY;
                    world = updatePlayerMove(world, newX, newY, previousX,
                            previousY, playerPos, Tileset.PLAYER);
                }
            }
        }
        return world;
    }

    public static void player2Move(TETile[][] world, Location playerPos, char c) {
        int previousX, previousY, newX, newY;
        previousX = playerPos.getX();
        previousY = playerPos.getY();
        if (!playerPos.getWin() && !playerPos.getLose()) {
            if (c == 'I' || c == 'i') {
                if (moveCheck(world, previousX, previousY + 1)) {
                    newX = previousX;
                    newY = previousY + 1;
                    updatePlayerMove(world, newX, newY, previousX,
                            previousY, playerPos, Tileset.PLAYER2);
                }
            } else if (c == 'J' || c == 'j') {
                if (moveCheck(world, previousX - 1, previousY)) {
                    newX = previousX - 1;
                    newY = previousY;
                    updatePlayerMove(world, newX, newY, previousX,
                            previousY, playerPos, Tileset.PLAYER2);
                }
            } else if (c == 'K' || c == 'k') {
                if (moveCheck(world, previousX, previousY - 1)) {
                    newY = previousY - 1;
                    newX = previousX;
                    updatePlayerMove(world, newX, newY, previousX,
                            previousY, playerPos, Tileset.PLAYER2);
                }
            } else if (c == 'L' || c == 'l') {
                if (moveCheck(world, previousX + 1, previousY)) {
                    newX = previousX + 1;
                    newY = previousY;
                    updatePlayerMove(world, newX, newY, previousX,
                            previousY, playerPos, Tileset.PLAYER2);
                }
            }
        }
    }

    private static void updateHunterMove(TETile[][] w, int newX, int newY, int oldX,
                                         int oldY, Location p, TETile typeTile) {
        if (w[newX][newY].description().equals("unlocked door")) {
            return;
        }
        if (w[newX][newY].description().equals("plane")) {
            w[newX][newY] = Tileset.FLOOR;
            p.setBlink();
            return;
        }
        if (w[newX][newY].description().equals("key")) {
            w[newX][newY] = typeTile;
            p.setX(newX);
            p.setY(newY);
            p.setKey(true);
            w[oldX][oldY] = Tileset.FLOOR;
            return;
        }
        w[newX][newY] = typeTile;
        w[oldX][oldY] = Tileset.FLOOR;
        p.setX(newX);
        p.setY(newY);
    }

    public static void hunterMove(TETile[][] world, Location playerPos, char c) {
        int previousX, previousY, newX, newY;
        previousX = playerPos.getX();
        previousY = playerPos.getY();
        if (c == 'T' || c == 't') {
            if (moveCheck(world, previousX, previousY + 1)) {
                newX = previousX;
                newY = previousY + 1;
                updateHunterMove(world, newX, newY, previousX,
                        previousY, playerPos, Tileset.HUNTER);
            }
        } else if (c == 'F' || c == 'f') {
            if (moveCheck(world, previousX - 1, previousY)) {
                newX = previousX - 1;
                newY = previousY;
                updateHunterMove(world, newX, newY, previousX,
                        previousY, playerPos, Tileset.HUNTER);
            }
        } else if (c == 'G' || c == 'g') {
            if (moveCheck(world, previousX, previousY - 1)) {
                newY = previousY - 1;
                newX = previousX;
                updateHunterMove(world, newX, newY, previousX,
                        previousY, playerPos, Tileset.HUNTER);
            }
        } else if (c == 'H' || c == 'h') {
            if (moveCheck(world, previousX + 1, previousY)) {
                newX = previousX + 1;
                newY = previousY;
                updateHunterMove(world, newX, newY, previousX,
                        previousY, playerPos, Tileset.HUNTER);
            }
        }
    }

    public static String userInput(int width, int height) {
        String input = "";
        boolean state = false;
        char c = 0;
        while (Character.toLowerCase(c) != 's' && Character.toLowerCase(c) != 'l') {
            if (StdDraw.hasNextKeyTyped()) {
                c = StdDraw.nextKeyTyped();
                if (Character.toLowerCase(c) == 'q') {
                    System.exit(0);
                }
                if (Character.toLowerCase(c) == 'n') {
                    drawFrame("Start New Game", width, height, true);
                    StdDraw.pause(700);
                    drawFrame("Please Enter A Random Number ", width, height, true);
                    StdDraw.pause(900);
                    state = true;
                }
                if (Character.isDigit(c) || (c == 'l' && input.equals(""))) {
                    input += c;
                }
                if ((Character.toLowerCase(c) != 'n' || Character.toLowerCase(c) != 'l')
                        && input.equals("")) {
                    c = 0;
                    drawFrame("Please Start Game First", width, height, true);
                    StdDraw.pause(700);
                }
            }
            drawFrame(input, width, height, state);
            StdDraw.pause(10);
        }
        return state ? 'n' + input + 's' : input;
    }

    public static void drawFrame(String s, int width, int height, boolean state) {
        StdDraw.clear(Color.black);
        // Title
        Font font = new Font("Monaco", Font.BOLD, 40);
        StdDraw.setFont(font);
        StdDraw.text(width / 2, height / 2 + height * 0.1, "Maze Runner Game!");
        // Main menu
        Font smallFont = new Font("Monaco", Font.BOLD, 20);
        StdDraw.setFont(smallFont);
        StdDraw.text(width / 2, height / 2 - height * 0.1, "New Game (N)");
        StdDraw.text(width / 2, height / 2 - height * 0.15, "Load Game (L)");
        StdDraw.text(width / 2, height / 2 - height * 0.20, "Quit Game (Q)");
        if (state) {
            Font newfont = new Font("Monaco", Font.BOLD, 20);
            StdDraw.setFont(newfont);
            StdDraw.text(width / 2, height / 2 - height * 0.3, s);
            newfont = new Font("Monaco", Font.ITALIC, 20);
            StdDraw.setFont(newfont);
            if (!s.equals("Please Start Game First")) {
                StdDraw.text(width / 2, height / 2 - height * 0.35, "(Press 'S' to Start)");
            }
        }
        StdDraw.setPenColor(Color.white);
        StdDraw.show();
    }
}
