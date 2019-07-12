package Core;
import java.io.Serializable;

public class Location implements Serializable {
    private int xLoc;
    private int yLoc;
    private boolean win;
    private boolean lose;
    private boolean blink;
    private boolean key;

    //Constructors
    public Location() {
        xLoc = yLoc = 0;
        win = false;
        lose = false;
        blink = false;
        key = false;
    }

    public Location(int x, int y) {
        xLoc = x;
        yLoc = y;

    }

    //Setter methods
    public void setKey(boolean val) {
        key = val;
    }

    public void setLose() {
        lose = true;
    }

    public void setBlink() {
        blink = true;
    }

    public void setWin() {
        win = true;
    }

    public void setX(int x) {
        xLoc = x;
    }

    public void setY(int y) {
        yLoc = y;
    }

    //Getter methods
    public boolean getKey() {
        return key;
    }

    public boolean getBlink() {
        return blink;
    }

    public boolean getLose() {
        return lose;
    }

    public boolean getWin() {
        return win;
    }

    public int getX() {
        return xLoc;
    }

    public int getY() {
        return yLoc;
    }
}
