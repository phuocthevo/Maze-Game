package Core;
import TileEngine.TETile;
import TileEngine.Tileset;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WorldGenerator implements Serializable {
    private static final int WIDTH = 80;
    private static final int HEIGHT = 40;
    TETile[][] myWorld;
    private int numLockDoor;
    private int numRoom;
    private int numkey;
    private Random RANDOM;
    private List<Integer> centerX = new ArrayList<>();
    private List<Integer> centerY = new ArrayList<>();
    private int[] exitDoor = new int[2];
    private Location player1Pos;
    private Location player2Pos;
    private Location hunterPos;
    private List<Location> blinkPos;
    private List<Location> lockDoorPos;
    private List<Location> keyPos;
    private long classSeed = 0;

    //Constructor
    WorldGenerator(long seed) {
        classSeed = seed;
        RANDOM = new Random(seed);
        numLockDoor = 2 + RANDOM.nextInt(5);
        numkey = numLockDoor;
        numRoom = 10 + RANDOM.nextInt(100);
        centerX.add(((int) WIDTH / 5) + RANDOM.nextInt(6));
        centerX.add(((int) WIDTH / 3) + RANDOM.nextInt(6));
        centerX.add(((int) WIDTH * 3 / 5) + RANDOM.nextInt(6));
        centerX.add(((int) WIDTH * 5 / 6) + RANDOM.nextInt(6));
        player1Pos = new Location();
        player2Pos = new Location();
        hunterPos = new Location();
        blinkPos = new ArrayList<>();
        lockDoorPos = new ArrayList<>();
        keyPos = new ArrayList<>();
        myWorld = new TETile[WIDTH][HEIGHT];
        for (int i = 0; i < 4; i++) {
            centerY.add(1 + RANDOM.nextInt(10) + RANDOM.nextInt(HEIGHT - 15));
        }
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                myWorld[x][y] = Tileset.NOTHING;
            }
        }
    }

    //Setter methods
    public void setHunterPos(Location hunterPos) {
        this.hunterPos = hunterPos;
        if (!hunterPos.getLose()) {
            myWorld[hunterPos.getX()][hunterPos.getY()] = Tileset.HUNTER;
        }
    }

    public void setPlayer2Pos(Location pos) {
        player2Pos = pos;
        if (!player2Pos.getLose()) {
            myWorld[player2Pos.getX()][player2Pos.getY()] = Tileset.PLAYER2;
        }
    }

    public void setPlayer1Pos(Location pos) {
        player1Pos = pos;
        if (!player1Pos.getLose()) {
            myWorld[player1Pos.getX()][player1Pos.getY()] = Tileset.PLAYER;
        }
    }

    public void setMyWorld(TETile[][] myWorld) {
        this.myWorld = myWorld;
    }

    //Getter methods
    public Location getHunterPos() {
        return hunterPos;
    }

    public Random getRANDOM() {
        return RANDOM;
    }

    public List<Location> getKeyPos() {
        return keyPos;
    }

    public List<Location> getBlinkPos() {
        return blinkPos;
    }

    public List<Location> getLockDoorPos() {
        return lockDoorPos;
    }

    public Location getPlayer2Pos() {
        return player2Pos;
    }

    public Location getPlayer1Pos() {
        return player1Pos;
    }

    public TETile[][] getMyWorld() {
        if (this.myWorld == null) {
            System.out.println("Null in GEn");
        }
        return this.myWorld;
    }

    //Generate the world.
    public TETile[][] makeMap() {
        //some calculations
        MapEngineer creator = new MapEngineer();
        creator.roomMaxWidth(WIDTH, HEIGHT - 2, numRoom, RANDOM);
        creator.roomMaxHeight(WIDTH, HEIGHT - 2, numRoom);
        creator.estateDividor(WIDTH, HEIGHT - 2, RANDOM);

        //create rooms
        Room roomCreate = new Room(creator.getRoomMW(), creator.getRoomMH());
        roomCreate.roomGenerator(myWorld, Tileset.FLOOR, creator.getXPos(), creator.getYPos(),
                RANDOM, numRoom, WIDTH, HEIGHT - 2);

        //create hall ways
        Hallway hallwayCreate = new Hallway();
        hallwayCreate.hallwayGenerator(myWorld, roomCreate.getDoorXPos(), roomCreate.getDoorYPos(),
                Tileset.FLOOR, centerX, centerY, RANDOM);

        //decorate the map
        Decoration.setWall(myWorld, WIDTH, HEIGHT - 2);
        Decoration.setExit(myWorld, WIDTH, HEIGHT - 2, RANDOM, exitDoor);
        Decoration.createForest(myWorld, WIDTH, HEIGHT - 2);
        Decoration.createPool(myWorld, WIDTH, HEIGHT - 2, RANDOM);

        player1Pos = Decoration.setPlayer(myWorld, RANDOM, WIDTH, HEIGHT, Tileset.PLAYER);
        player2Pos = Decoration.setPlayer(myWorld, RANDOM, WIDTH, HEIGHT, Tileset.PLAYER2);
        hunterPos = Decoration.setHunter(myWorld, RANDOM, WIDTH, HEIGHT, Tileset.HUNTER);
        Decoration.setBlink(myWorld, RANDOM, WIDTH, HEIGHT, blinkPos);
        Decoration.setLockDoor(myWorld, WIDTH, HEIGHT, RANDOM, numLockDoor, lockDoorPos);
        Decoration.setKey(myWorld, WIDTH, HEIGHT, keyPos, RANDOM, numkey);

        return myWorld;
    }
}



