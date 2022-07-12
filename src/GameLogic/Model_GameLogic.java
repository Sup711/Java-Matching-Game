package GameLogic;

import javafx.scene.paint.Color;

import java.util.*;

public class Model_GameLogic{


    private final List<Observer<Model_GameLogic, String>> observers = new LinkedList<>();

    public void addObserver(Observer<Model_GameLogic, String> observer){
        this.observers.add(observer);
    }

    public void alertObservers(String gameState){
        for (var observer : observers){
            observer.update(this, gameState);
        }
    }

//-----------------------------------------------------------------------------------------------
    /** The list of colors that will be used for the game */
    public final Color[] COLORLIST = {Color.YELLOW, Color.RED, Color.GREEN, Color.BROWN, Color.AZURE,
                                      Color.TEAL, Color.PURPLE, Color.NAVY, Color.ORANGE, Color.MAROON,
                                      Color.CORAL, Color.FUCHSIA, Color.LIME, Color.HOTPINK, Color.GOLDENROD,
                                      Color.PLUM, Color.OLIVE, Color.CYAN};
    /** The board that contains the current colors and moves */
    public Color[][] board;
    /** Number of rows for the current game */
    public int numRows;
    /** Number of columns for the current game */
    public int numCols;
    /** The collection of colors being used in the game */
    private Object[] colorMap;
    /** The original board, used for reset */
    private Color[][] origBoard;
    /** The first user selection */
    public int[] select1;
    /** The second user selection */
    public int[] select2;
    /** The previous first user selection */
    public int[] prevSelect1;
    /** The current game score */
    public int score;

    /**
     * Contains all the logic needed to generate a new game
     * @param numRows the number of rows
     * @param numCols the number of columns
     */
    public void newGame(int numRows, int numCols){
        /* Basic value initiation */
        this.numRows = numRows;
        this.numCols = numCols;
        score = 0;
        select1 = new int[]{-1, -1};
        select2 = new int[]{0, 0};
        prevSelect1 = new int[]{0, 0};

        /* Create the board */
        numToIntMap();
        randomBoard();

        /* Used for reset */
        origBoard = board;

        /* Letting the GUI know the game is ready */
        this.alertObservers("load");
    }

    /**
     * Creates a list of colors to be used for the upcoming game
     */
    private void numToIntMap(){
        /* The set of colors to be used in the game */
        HashSet<Color> colors = new HashSet<Color>();
        /* Made so random can be used */
        Random rand = new Random();
        /* The random index variable */
        int randIndex;

        /* Creates a list of colors to be used in the game */
        while (colors.size() < (numRows*numCols)/2){
            /* Generates a random index */
            randIndex = rand.nextInt(18);
            /* Selects the color at the index from the color list */
            colors.add(COLORLIST[randIndex]);
        }
        /* Converts the set to an array for easier manipulation later */
        colorMap = colors.toArray();
    }

    /**
     * Used to generate random boards
     */
    private void randomBoard(){
        /* The board that gets filled in */
        this.board = new Color[numRows][numCols];
        /* Counts how many times each color appears on the board */
        HashMap<Color, Integer> count = new HashMap<>();
        /* Made so random can be used */
        Random rand = new Random();
        /* The random color variable */
        Color randColor;

        /* Fills in the count map giving each color a 0 (it hasn't been used yet) */
        for (Object col : colorMap) {
            count.put((Color) col, 0);
        }

        /* Goes through each space on the board */
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                /* Selects a random color */
                randColor = (Color) colorMap[rand.nextInt(colorMap.length)];
                /* Makes sure the color hasn't already been used twice, selects a new color if it has */
                while (count.get(randColor) > 1){
                    randColor = (Color) colorMap[rand.nextInt(colorMap.length)];
                }
                /* Adds the new color to the board */
                board[i][j] = randColor;
                /* Updates the times the color has been used */
                count.put(randColor, count.get(randColor)+1);
            }
        }
    }

    /**
     * Resets the game and keep the same board
     */
    public void reset(){
        /* Same value initiation as newGame() */
        board = origBoard;
        score = 0;
        select1 = new int[]{-1, -1};
        select2 = new int[]{0, 0};
        prevSelect1 = new int[]{0, 0};

        /* No board creation */

        /* Tells the GUI reset is done */
        this.alertObservers("reset");
    }

    /**
     * Handles the card selection and game logic
     * @param i the card i position
     * @param j the car j position
     */
    public void select(int i, int j){
        /* Stores the first selection */
        if (select1[0] == -1){
            select1[0] = i;
            select1[1] = j;
            /* Used to flip the card in GUI */
            this.alertObservers("select1");
        }
        /* Stores the second selection */
        else {
            select2[0] = i;
            select2[1] = j;
            /* Used to flip the card in GUI */
            this.alertObservers("select2");
            /* Checks to see if the card matches */
            if (board[select1[0]][select1[1]].equals(board[i][j])){
                score += 1;
                this.alertObservers("match");
            }
            else {
                this.alertObservers("noMatch");
            }
            /* Resets the selections */
            prevSelect1[0] = select1[0];
            prevSelect1[1] = select1[1];
            select1[0] = -1;
            select1[1] = -1;
        }
    }

    @Override
    public int hashCode(){
        return Arrays.deepHashCode(COLORLIST);
    }

}
