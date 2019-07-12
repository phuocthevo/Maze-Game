package Core;
import TileEngine.TETile;
import TileEngine.Tileset;
import java.util.List;
import java.util.Random;
public class Decoration {

    //Setter methods
    public static Location setPlayer(TETile[][] world, Random r,
                                     int width, int height, TETile typeTile) {
        Location result = new Location();
        int playerX = 1 + r.nextInt(width - 1);
        int playerY = 1 + r.nextInt(height - 3);
        while (world[playerX][playerY] != Tileset.FLOOR) {
            playerX = 1 + r.nextInt(width - 1);
            playerY = 1 + r.nextInt(height - 3);
        }
        world[playerX][playerY] = typeTile;
        result.setX(playerX);
        result.setY(playerY);
        return result;
    }

    public static Location setHunter(TETile[][] world, Random r,
                                     int width, int height, TETile typeTile) {
        Location result = new Location();
        int hunterX = 1 + r.nextInt(width - 1);
        int hunterY = 1 + r.nextInt(height - 3);
        while (world[hunterX][hunterY] != Tileset.FLOOR) {
            hunterX = 1 + r.nextInt(width - 1);
            hunterY = 1 + r.nextInt(height - 3);
        }
        world[hunterX][hunterY] = typeTile;
        result.setX(hunterX);
        result.setY(hunterY);
        return result;
    }

    public static void setBlink(TETile[][] world, Random r,
                                int width, int height, List<Location> blinkPos) {
        int xP = 1 + r.nextInt(width - 1);
        int yP = 1 + r.nextInt(height - 3);
        int numBlink = 3 + r.nextInt(20);
        for (int i = 0; i < numBlink; i++) {
            while (world[xP][yP] != Tileset.FLOOR) {
                xP = 1 + r.nextInt(width - 1);
                yP = 1 + r.nextInt(height - 3);
            }
            world[xP][yP] = Tileset.FLY;
            blinkPos.add(new Location(xP, yP));
        }
    }

    public static void setExit(TETile[][] map, int width, int height, Random r, int[] exitDoor) {
        exitDoor[1] = 1 + r.nextInt(height - 2);
        exitDoor[0] = 0;
        while (exitDoor[0] < width - 2 && map[exitDoor[0]][exitDoor[1]] != Tileset.WALL) {
            exitDoor[0]++;
        }
        if (map[exitDoor[0] + 1][exitDoor[1]] == Tileset.WALL
                || map[exitDoor[0]][exitDoor[1]] != Tileset.WALL
                || (exitDoor[0] > 0 && map[exitDoor[0] - 1][exitDoor[1]] == Tileset.WATER)) {
            setExit(map, width, height, r, exitDoor);
        }
        map[exitDoor[0]][exitDoor[1]] = Tileset.UNLOCKED_DOOR;
    }

    private static boolean checkLockDoorPos(TETile[][] map, int x, int y, int w, int h) {
        if (x + 1 < w - 2 && y + 1 < h - 2) {
            if (map[x + 1][y].description().equals("floor")
                    || map[x - 1][y].description().equals("floor")
                    || map[x][y + 1].description().equals("floor")
                    || map[x][y - 1].description().equals("floor")) {
                return true;
            }
        }
        return false;
    }

    public static void setLockDoor(TETile[][] map, int w, int h, Random r,
                                   int numDoor, List<Location> lDoorPos) {
        int xPos = 2 + r.nextInt(w - 4);
        int yPos = 2 + r.nextInt(h - 4);
        for (int i = 0; i < numDoor; i++) {
            while (map[xPos][yPos] != Tileset.WALL || !checkLockDoorPos(map, xPos, yPos, w, h)) {
                xPos = 1 + r.nextInt(w - 1);
                yPos = 1 + r.nextInt(h - 3);
            }
            map[xPos][yPos] = Tileset.LOCKED_DOOR;
            lDoorPos.add(new Location(xPos, yPos));
        }
    }

    public static void setKey(TETile[][] map, int w, int h,
                              List<Location> keyPos, Random r, int numkey) {
        int xPos = 1 + r.nextInt(w - 1);
        int yPos = 1 + r.nextInt(h - 3);
        for (int i = 0; i < numkey; i++) {
            while (map[xPos][yPos] != Tileset.FLOOR) {
                xPos = 1 + r.nextInt(w - 1);
                yPos = 1 + r.nextInt(h - 3);
            }
            map[xPos][yPos] = Tileset.KEY;
            keyPos.add(new Location(xPos, yPos));
        }
    }

    public static void setWall(TETile[][] map, int wIDTH, int hEIGHT) {
        for (int x = 1; x < wIDTH - 1; x += 1) {
            for (int y = 1; y < hEIGHT - 1; y += 1) {
                if (map[x][y] == Tileset.FLOOR) {
                    for (int i = x - 1; i <= x + 1; i++) {
                        for (int j = y - 1; j <= y + 1; j++) {
                            map[i][j] = (map[i][j] == Tileset.FLOOR) ? Tileset.FLOOR : Tileset.WALL;
                        }
                    }
                    map[x][y] = Tileset.FLOOR;
                }
            }
        }
    }


    //Decorate the world
    public static void createPool(TETile[][] world, int width, int height, Random r) {
        int numPool = 2 + r.nextInt(3);
        int poolWidth = 5 + r.nextInt(3);
        int xStart;
        for (int i = 0; i < numPool; i++) {
            xStart = 2 + r.nextInt(width - 10);
            for (int j = 0; j < poolWidth; j++) {
                for (int k = 1; k < height - 1; k++) {
                    if (world[xStart + j][k] == Tileset.NOTHING) {
                        world[xStart + j][k] = Tileset.WATER;
                    }
                }
            }
        }
        for (int i = 1; i < width - 1; i++) {
            for (int j = 1; j < height - 1; j++) {
                if ((world[i][j] == Tileset.NOTHING && world[i + 1][j] == Tileset.WATER)
                        || (world[i][j] == Tileset.NOTHING && world[i - 1][j] == Tileset.WATER)) {
                    world[i][j] = Tileset.FLOWER;
                }
            }
        }
    }

    public static void createForest(TETile[][] world, int width, int height) {
        for (int i = 0; i < width; i++) {
            if (world[i][0] == Tileset.NOTHING) {
                world[i][0] = Tileset.TREE;
            }
            if (world[i][height - 1] == Tileset.NOTHING) {
                world[i][height - 1] = Tileset.TREE;
            }
        }
        for (int i = 0; i < height; i++) {
            if (world[0][i] == Tileset.NOTHING) {
                world[0][i] = Tileset.TREE;
            }
            if (world[width - 1][i] == Tileset.NOTHING) {
                world[width - 1][i] = Tileset.TREE;
            }
        }
    }
}
