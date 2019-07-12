package Core;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MapEngineer {
    private List<Integer> xPosition;
    private List<Integer> yPosition;
    private double roomMW;
    private double roomMH;
    private int numRoomX;
    private int numRoomY;

    MapEngineer() {
        xPosition = new ArrayList<>();
        yPosition = new ArrayList<>();
        roomMH = 0;
        roomMW = 0;
        numRoomX = 0;
        numRoomY = 0;
    }

    public int getRoomMW() {
        return (int) roomMW;
    }

    public int getRoomMH() {
        return (int) roomMH;
    }

    public List<Integer> getXPos() {
        return xPosition;
    }

    public List<Integer> getYPos() {
        return yPosition;
    }

    public void roomMaxWidth(int width, int height, int numRoom, Random r) {
        numRoomX = (int) Math.pow(numRoom * 2, 0.5);
        roomMW = width / numRoomX;
    }

    public void roomMaxHeight(int width, int height, int numRoom) {
        numRoomY = (int) numRoom / numRoomX;
        roomMH = height / numRoomY;
    }

    public void estateDividor(int worldWidth, int worldHeight, Random r) {
        for (int i = 0; i < numRoomX; i++) {
            xPosition.add(i * (int) roomMW);
        }
        for (int i = 0; i < numRoomY; i++) {
            int x = i * (int) roomMH;
            yPosition.add(x);
        }
    }
}
