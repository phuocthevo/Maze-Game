package Core;
import TileEngine.TETile;
import java.util.List;
import java.util.Random;

public class Hallway {

    private void connectHorizontal(TETile[][] world, int length, int xPos, int yPos,
                                   TETile typeTile) {
        if (length > 0) {
            for (int i = xPos; i <= xPos + length; i++) {
                world[i][yPos] = typeTile;
            }
        } else if (length < 0) {
            for (int i = xPos; i >= xPos + length; i--) {
                world[i][yPos] = typeTile;
            }
        }
    }

    private void connectVertical(TETile[][] world, int length, int xCenter, int yCenter,
                                 TETile typeTile) {
        if (length > 0) {
            for (int i = yCenter; i <= yCenter + length; i++) {
                world[xCenter][i] = typeTile;
            }
        } else if (length < 0) {
            for (int i = yCenter; i >= yCenter + length; i--) {
                world[xCenter][i] = typeTile;
            }
        }
    }

    private void lastConnect(TETile[][] world, int x1, int y1, int x2, TETile typeTile) {
        connectHorizontal(world, x2 - x1, x1, y1, typeTile);
    }

    public void hallwayGenerator(TETile[][] world, List<Integer> xDoor, List<Integer> yDoor,
                                 TETile typeTile, List<Integer> xList,
                                 List<Integer> yList, Random r) {
        int tempLength;
        int index = yDoor.size() / 4;
        for (int i = 0; i < index; i++) {
            tempLength = yDoor.get(i) - yList.get(0);
            connectVertical(world, tempLength, xList.get(0), yList.get(0), typeTile);
            tempLength = xList.get(0) - xDoor.get(i);
            connectHorizontal(world, tempLength, xDoor.get(i), yDoor.get(i), typeTile);
        }
        for (int i = index; i < 2 * index; i++) {
            tempLength = yDoor.get(i) - yList.get(1);
            connectVertical(world, tempLength, xList.get(1), yList.get(1), typeTile);
            tempLength = xList.get(1) - xDoor.get(i);
            connectHorizontal(world, tempLength, xDoor.get(i), yDoor.get(i), typeTile);
        }
        for (int i = 2 * index; i < 3 * index; i++) {
            tempLength = yDoor.get(i) - yList.get(2);
            connectVertical(world, tempLength, xList.get(2), yList.get(2), typeTile);
            tempLength = xList.get(2) - xDoor.get(i);
            connectHorizontal(world, tempLength, xDoor.get(i), yDoor.get(i), typeTile);

        }
        for (int i = 3 * index; i < yDoor.size(); i++) {
            tempLength = yDoor.get(i) - yList.get(3);
            connectVertical(world, tempLength, xList.get(3), yList.get(3), typeTile);
            tempLength = xList.get(3) - xDoor.get(i);
            connectHorizontal(world, tempLength, xDoor.get(i), yDoor.get(i), typeTile);
        }
        for (int i = 0; i < 3; i++) {
            lastConnect(world, xList.get(i), yList.get(i), xList.get(i + 1), typeTile);
        }
    }
}
