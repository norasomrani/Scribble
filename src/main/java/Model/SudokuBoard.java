package Model;
import java.io.Serializable;
import java.util.Random;
import static Model.SudokuUtilities.GRID_SIZE;


public class SudokuBoard implements Serializable {
    private SingleSquare[][][] numberTiles; //the 3 D board

    private SudokuUtilities.SudokuLevel currentDifficulty;

    /**
     * constructor
     */
    public SudokuBoard() {

        initNewBoard(SudokuUtilities.SudokuLevel.MEDIUM);
    }



    /**
     * initiates the board
     * @param difficulty
     */

    public void initNewBoard(SudokuUtilities.SudokuLevel difficulty){
        this.currentDifficulty = difficulty;
        this.numberTiles = new SingleSquare[GRID_SIZE][GRID_SIZE][2];

        /**Resets whole SudokuBoard*/
        for(int x = 0;x < 2;x++) {
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    numberTiles[row][col][x] = new SingleSquare(0);
                    numberTiles[row][col][x].setLockage(false);
                }
            }
        }

        /**Fills SudokuBoard with reference from generated matrix*/
        int generatedMatrix[][][] = SudokuUtilities.generateSudokuMatrix(difficulty);
        for(int x = 0;x < 2;x++) {
            for (int row = 0; row < GRID_SIZE; row++) {
                for (int col = 0; col < GRID_SIZE; col++) {
                    numberTiles[row][col][x] = new SingleSquare(generatedMatrix[row][col][x]);
                    if(!numberTiles[row][col][x].equals(0)){
                        numberTiles[row][col][x].setLockage(false);
                    }
                }
            }
        }
    }



    /**
     * Returns copy of SudokuBoard that we did, not the array with true values!!
     * @return 2-dimensional SudokuSquare array
     *
     */

    public SingleSquare[][] getCopyOfBoard(){
        SingleSquare[][] copyBoard = new SingleSquare[GRID_SIZE][GRID_SIZE];

        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                copyBoard[row][col] = numberTiles[row][col][0];
                copyBoard[row][col].setLockage(numberTiles[row][col][0].getLockage());
                copyBoard[row][col].setVisebility(numberTiles[row][col][0].getVisebility());
            }
        }
        return copyBoard;
    }


    /**
     * Checks if all numbers in number sheet is set
     * if we find zero in some SingelSquare -> not all the numbers are set
     * @return true if all numbers are set, else false
     */
    public boolean isAllNumbersSet(){
        for(int i = 0;i < GRID_SIZE;i++){
            for(int j = 0;j < GRID_SIZE;j++){
                if(numberTiles[i][j][0].equals(0)){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Inserts requested number to SudokuModel
     * @return true if set number is allowed, else false
     */

    public boolean setNumberTile(int row, int col, int number){
        if(numberTiles[row][col][0].equals(0)){
            numberTiles[row][col][0].setBoxValue(number);
            numberTiles[row][col][0].setVisebility(true);
            numberTiles[row][col][0].setLockage(false);
            return true; // it is allowed to set a number
        }else{
            return false; //it is not allowed
        }
    }

    /**
     * Removes requested number from SudokuModel
     *
     * @return true if removed number is allowed, else false
     *
     */

    public boolean removeNumberTile(int row, int col){
        if((!numberTiles[row][col][0].equals(0))){
            numberTiles[row][col][0].setBoxValue(0);
            numberTiles[row][col][0].setVisebility(false);
            numberTiles[row][col][0].setLockage(true);
            return true; //is allowed to remove
        }else{
            return false; //is not allowed to remove cause there is alredy NumberNotSet = 0
        }
    }

    /**
     * Compares answer sheet to number sheet
     *
     * @return number count not matching the correct answer, or -1 if all numbers are set and correct
     *
     */

    public int numbersWrong(){
        int wrongNumbers = 0;
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                if(numberTiles[row][col][0] != numberTiles[row][col][1]){
                    wrongNumbers++;
                }
            }
        }
        if(isAllNumbersSet() && (wrongNumbers == 0)){
            return -1;
        }
        return wrongNumbers;
    }

    /**
     * Fills one correct number into number sheet
     *
     * @return true if action was successful, false if all numbers are already set
     *
     */

    public boolean giveHint(){
        if(isAllNumbersSet() && numbersWrong()==-1 ){
            return false;
        }
        while(true){
            Random random = new Random();
            int row = random.nextInt(9);
            int col = random.nextInt(9);
            if(!numberTiles[row][col][0].getVisebility() && !numberTiles[row][col][0].getLockage()){
                setNumberTile(row, col, numberTiles[row][col][1].getValue());
                return true;
            }
            return true;
        }
    }


    public SudokuUtilities.SudokuLevel getCurrentDifficulty(){
        return currentDifficulty;
    }

}

