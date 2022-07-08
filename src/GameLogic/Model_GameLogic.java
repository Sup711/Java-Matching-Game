package GameLogic;

public class Model_GameLogic {

    private String gameState;
    public String[][] board;

    public Model_GameLogic(int numRows, int numCols){
        this.board = new String[numRows][numCols];
        board[0][0] = "Red";
    }

}
