package Core;

import TileEngine.TETile;
import TileEngine.Tileset;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Room {
    private static int maxW = 80;
    private static int maxH = 40;
    private int maxRoomWidth;
    private int maxRoomHeight;
    private List<Integer> doorXPos = new ArrayList<>();
    private List<Integer> doorYPos = new ArrayList<>();

    Room(int x, int y) {
        maxRoomWidth = x;
        maxRoomHeight = y;
    }

    public List<Integer> getDoorXPos() {
        return doorXPos;
    }

    public List<Integer> getDoorYPos() {
        return doorYPos;
    }

    private void add1Room(TETile[][] world, int xPos, int yPos, TETile typeTile, Random r,
                          int roomW, int roomH) {
        for (int i = xPos; i < (xPos + roomW); i++) {
            if (i == maxW - 1) {
                continue;
            }
            for (int j = yPos; j < (yPos + roomH); j++) {
                if (j == maxH - 1) {
                    continue;
                }
                world[i][j] = typeTile;
            }
        }
        //set a random point in the room to build hall way
        int tempX = xPos + r.nextInt(roomW - 1);
        int tempY = yPos + r.nextInt(roomH - 1);
        world[tempX][tempY] = Tileset.WATER;
        doorXPos.add(tempX);
        doorYPos.add(tempY);
    }

    public void roomGenerator(TETile[][] world, TETile typeTile, List<Integer> xPos,
                              List<Integer> yPos, Random r, int numRoom, int worldW, int worldH) {
        int ranW;
        int ranH;
        for (int i = 0; i < xPos.size(); i++) {

            for (int j = 0; j < yPos.size(); j++) {
                if (i == xPos.size() - 1 && j != yPos.size() - 1) {
                    ranW = 2 + r.nextInt(maxRoomWidth / 2);
                    ranH = 2 + r.nextInt(maxRoomHeight - 3);
                } else if (j == yPos.size() - 1 && i != xPos.size() - 1) {
                    ranH = 2 + r.nextInt(maxRoomHeight / 2);
                    ranW = 2 + r.nextInt(maxRoomWidth - 3);
                } else if (j == yPos.size() - 1 && i == xPos.size() - 1) {
                    ranW = 2 + r.nextInt(maxRoomWidth / 2);
                    ranH = 2 + r.nextInt(maxRoomHeight / 2);
                } else {
                    ranH = 2 + r.nextInt(maxRoomHeight - 3);
                    ranW = 2 + r.nextInt(maxRoomWidth - 3);
                }
                add1Room(world, 1 + xPos.get(i) + r.nextInt(3),
                        1 + yPos.get(j) + r.nextInt(3), typeTile, r, ranW, ranH);
            }
        }
    }
}
