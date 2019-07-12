package Core;
import TileEngine.TERenderer;
import TileEngine.TETile;

public class Game {
    /* Feel free to change the width and height. */
    public static final int WIDTH = 80;
    public static final int HEIGHT = 40;
    WorldGenerator worldCreate;
    MoveControl mC;
    TERenderer ter = new TERenderer();
    long classseed;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
        FileIO file = new FileIO();
        ter.initialize(WIDTH, HEIGHT);
        String input = Interactivity.userInput(WIDTH, HEIGHT);
        parseString(file, input);
        if (!file.isIsloaded()) {
            worldCreate = new WorldGenerator(classseed);
            worldCreate.makeMap();
            mC = new MoveControl(worldCreate);
        }
        mC.drawEverything(ter, HEIGHT);
    }


    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // TO-DO: Fill out this method to run the game using the input passed in,
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        FileIO file = new FileIO();
        char[] direction = parseString(file, input);
        if (!file.isIsloaded()) {
            worldCreate = new WorldGenerator(classseed);
            worldCreate.makeMap();
        }
        for (int i = 0; i < direction.length; i++) {
            TETile[][] temp0 = worldCreate.getMyWorld();
            Location tempP = worldCreate.getPlayer1Pos();
            worldCreate.setMyWorld(Interactivity.player1Move(temp0, tempP, direction[i]));
        }

        if (input.contains(":q") || input.contains(":Q")) {
            FileIO.savefile(worldCreate);
        }
        return worldCreate.getMyWorld();
    }

    char[] parseString(FileIO file, String input) {
        int i = 1, seed = 0;
        String direction = "";
        char first = input.charAt(0);
        if (first == 'n' || first == 'N') {

            for (; Character.isDigit(input.charAt(i)) && i < input.length(); i++) {
                seed = seed * 10 + Integer.parseInt(input.substring(i, i + 1));
            }
            i++;
            classseed = seed;
        } else if (first == 'L' || first == 'l') {
            worldCreate = file.loadfile();
            mC = new MoveControl(worldCreate);
        }
        while (i < input.length() && input.charAt(i) != ':') {
            direction += input.charAt(i++);
        }
        return direction.toCharArray();
    }
}
