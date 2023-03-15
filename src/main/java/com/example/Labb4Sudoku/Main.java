package com.example.Labb4Sudoku;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import View.SudokuView;


/**
 * Creates a window and ties model, view and controller together.
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        SudokuView view = new SudokuView();
        Scene scene = new Scene(view, 420, 350);
        primaryStage.setTitle("Sudoku Game");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
