package Model;

import java.util.*;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.stage.FileChooser;

import java.io.*;

/**
 * Class used to save and load save-game
 *
 *
 */

public class FileIO {

    /**
     * Creates new file and serializes SudokuBoard to file
     *
     * @return True if SudokuBoard was successfully saved, else false
     * @param sudokuModel the board that gets serialized to file
     *
     * @throws RuntimeException if file read/write fails
     */

    public static boolean saveGame(SudokuBoard sudokuModel) throws IOException {

        ObjectOutputStream saveStream = null;

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("SER files (*.ser)", "*.ser");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extensionFilter);

        File f = fileChooser.showSaveDialog(null);

        //If file chooser is closed/canceled
        if (f == null) {
            return false;
        }

        try {
            ButtonType doOverwrite;
            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);

            if (f.exists()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setContentText("A save-game with this filename already exists at this location, do you want to overwrite selected save?");
                alert.getButtonTypes().setAll(yesButton, noButton);
                doOverwrite = alert.showAndWait().get();
            } else {
                doOverwrite = yesButton;
            }

            if (doOverwrite == yesButton) {
                FileOutputStream fout = new FileOutputStream(f, false);
                saveStream = new ObjectOutputStream(fout);

                saveStream.writeObject(sudokuModel);

                fout.close();
                saveStream.close();
            }

        } catch (IOException e) {
            throw new IOException(e);
        }
        return saveStream != null;
    }

    /**
     * Creates new file and serializes SudokuModel to file
     *
     * @return Sudokumodel if SudokuModel was successfully loaded, else null
     *
     * @throws RuntimeException if file read/load process goes wrong or if SudokuModel class was not found
     */

    public static SudokuBoard loadGame() {

        ObjectInputStream loadStream = null;

        SudokuBoard savedModel = new SudokuBoard();

        savedModel = null;

        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("SER files (*.ser)", "*.ser");
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(extensionFilter);

        File f = fileChooser.showOpenDialog(null);

        if (f == null) {
            return null;
        }

        try {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setContentText("Loading saved game will overwrite current game, proceed?");
            ButtonType yesButton = new ButtonType("Yes", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("No", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(yesButton, noButton);

            if(f.exists()){
                if(alert.showAndWait().get() == yesButton){
                    FileInputStream fin = new FileInputStream(f);
                    loadStream = new ObjectInputStream(fin);
                    savedModel = (SudokuBoard) loadStream.readObject();
                    loadStream.close();
                }
            }else{
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setHeaderText("Error");
                alert2.setTitle("Error");
                alert2.setContentText("No save-game found!");
                alert2.showAndWait();
            }

        } catch (IOException | ClassNotFoundException e){
            throw new RuntimeException(e);
        }

        if (loadStream != null)
            return savedModel;

        return null;
    }


}
