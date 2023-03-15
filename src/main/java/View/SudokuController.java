package View;

import Model.FileIO;
import Model.SudokuBoard;
import Model.SudokuUtilities;
import javafx.scene.control.Alert;

import java.io.IOException;

public class SudokuController {
    private SudokuBoard sudokuModel;
    private SudokuView sudokuView;

    protected SudokuController(SudokuView sudokuView){
        this.sudokuModel = new SudokuBoard();
        this.sudokuView = sudokuView;
        updateGridView();
    }

    /**
     * Retrieves copy of SudokuBoards SingleSquare and updates grid view with reference
     *
     *
     */

    private void updateGridView(){
        sudokuView.getGridView().setNumberTiles(sudokuModel.getCopyOfBoard());
    }

    /**
     * Initiates new round in SudokuBoard
     *
     * @param difficulty the chosen difficulty level for the new sudoku game
     */

    protected void onInitNewGameRound(SudokuUtilities.SudokuLevel difficulty){
        sudokuModel.initNewBoard(difficulty);
        updateGridView();
    }

    /**
     * Requests a number change/set to SudokuBoard
     *
     * @return true if action was allowed and performed, else false
     */

    protected boolean onNumberSet(int row, int col, int numberSet){
        if(!sudokuModel.setNumberTile(row,col,numberSet)){
            return false;
        }
        updateGridView();
        if(sudokuModel.isAllNumbersSet()){
            if(sudokuModel.numbersWrong() == -1){
                onAllNumbersSet(true);
            }else{
                onAllNumbersSet(false);
            }
        }
        return true;
    }

    /**
     * Requests a number removal to SudokuBoard
     *
     * @return true if action was allowed and performed, else false
     */

    protected boolean onNumberRemove(int row, int col){
        if(!sudokuModel.removeNumberTile(row,col)){
            return false;
        }
        updateGridView();
        return true;
    }

    /**
     * Request count of numbers input wrong compared to correct answer
     *
     * @return int with count on numbers wrong
     */

    protected int onCheckCorrectNumbers(){
        return sudokuModel.numbersWrong();
    }

    public void onAllNumbersSet(boolean result){
        if(result){
            sudokuView.showAlert("Sudoku game completed! :)", "Congratulations!", Alert.AlertType.INFORMATION);
        }else{
            sudokuView.showAlert("Sudoku not completed", "Information", Alert.AlertType.ERROR);
        }
    }

    /**
     * Requests a show hint from SudokuBoard
     *
     * @return true if action was allowed and performed
     */

    protected boolean onShowHint(){
        if(!sudokuModel.giveHint()){
            return false;
        }
        updateGridView();
        if(sudokuModel.isAllNumbersSet()){
            if(sudokuModel.numbersWrong() == -1){
                onAllNumbersSet(true);
            }else{
                onAllNumbersSet(false);
            }
        }
        return true;
    }

    /**
     * Saves SudokuBoard to file
     *
     * @return true if action was successfully performed
     */

    protected boolean onSaveGame() throws IOException {
        boolean isSaved;
        isSaved = FileIO.saveGame(sudokuModel);
        return isSaved;
    }

    /**
     * Loads SudokuBoard from file
     *
     * @return true if action was successfully performed
     */

    protected boolean onLoadGame() throws Exception {
        SudokuBoard savedModel = FileIO.loadGame();
        if(savedModel == null){
            return false;
        }else{
            sudokuModel = savedModel;
            updateGridView();
            return true;
        }
    }

    protected SudokuUtilities.SudokuLevel getCurrentDifficulty(){
        return sudokuModel.getCurrentDifficulty();
    }
}
