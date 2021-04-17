package sample;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.util.*;
import java.util.List;

public class SudokuController {
    @FXML
    private GridPane gridPane;

    private TextField[] textFields = new TextField[81];
    private Integer[] numbers = new Integer[81];
    private Integer[] positions = new Integer[81];

    private SudokuGame sudokuGame = new SudokuGame();
    private SudokuGame2 sudokuGame2 = new SudokuGame2();

    public void initialize() {


        for (int i = 0; i < 81; i++) {

            TextField textField = new TextField();
            textField.setPrefHeight(60.0);
            textField.setPrefWidth(60.0);
            textField.getStyleClass().add("text-field");

            int finalPosition = i;

            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.isEmpty()) {
                    sudokuGame2.handleNewNumber(null, finalPosition);
                } else {
                    String number = newValue.substring(newValue.length() - 1);
                    if (number.equals("0") || !number.matches("\\d*")) {
                        textField.setText(oldValue);
                    } else {
                        textField.setText(number);
                        sudokuGame2.handleNewNumber(Integer.valueOf(number), finalPosition);
                    }
                }
            });
            int columnIndex = (i % 9);
            int rowIndex = (i / 9);
            gridPane.add(textField, columnIndex+columnIndex/3, rowIndex+rowIndex/3);
            textFields[finalPosition] = textField;
        }
    }

    public void check() {

        if (sudokuGame2.isGameIncomplete()) {
            notFinishedWindow();
            System.out.println("Błąd - gra nie jest skończona");
        } else {
            gratsWindow();
            System.out.println("Gratuluję");
        }
    }

    public void clear(Integer position) {

            TextField textField = this.textFields[position];
            textField.setText("");

    }


    public void fillCell(Integer position, Integer number) {
        TextField textField = textFields[position];
        textField.setText(number.toString());
    }


    public void generate() {
        for (int i = 0; i < 81; i++) {
            clear(i);
            unblockTextField(i);
        }
        Integer[] values = sudokuGame2.generate();
        for (int i = 0; i < 81; i++) {
            if (values[i] != null) {
                fillCell(i, values[i]);
            }
        }
        List<Integer> positions = sudokuGame2.createRandomPositions(50);
        for (int index : positions) {
            clear(index);
        }
        blockGeneratedTextFields();
    }

    public void blockGeneratedTextFields(){
        for (int i = 0; i < 81; i++) {
            if( !textFields[i].getText().isEmpty()) {
                textFields[i].setDisable(true);
            }
        }
    }

    public void unblockTextField(Integer position) {
        TextField textField = this.textFields[position];
        textField.setDisable(false);

    }

    public void informationWindow() {
        Stage information = new Stage();
        information.initModality(Modality.WINDOW_MODAL);
        information.setTitle("Zasady gry");

        Text text = new Text("Zasady gry Sudoku są niezwykle proste. Kwadratowa plansza " +
                " jest podzielona na dziewięć identycznych kwadratów 3 x 3 - w każdym z nich znajduje" +
                " się dziewięć komórek. Twoim zadaniem jest wypełnienie wszystkich komórek planszy cyframi" +
                " od 1 do 9. W każdym wierszu i każdej kolumnie dana cyfra może występować jedynie raz. " +
                "Podobnie w każdym z dziewięciu kwadratów 3 x 3 - cyfry nie mogą się powtarzać.");
        text.wrappingWidthProperty().set(500);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        FlowPane window = new FlowPane(text);


        window.setAlignment(Pos.CENTER);
        window.setPrefHeight(200);
        window.setPrefWidth(600);

        information.setScene(new Scene(window));
        information.show();
    }

    public void authorWindow() {
        Stage author = new Stage();
        author.initModality(Modality.WINDOW_MODAL);
        author.setTitle("O autorze");

        Text text = new Text("Jakub Dutkiewicz\n" +
                "Java EE(2020Z) - grupa JA1-B");
        text.wrappingWidthProperty().set(500);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        FlowPane window = new FlowPane(text);


        window.setAlignment(Pos.CENTER);
        window.setPrefHeight(200);
        window.setPrefWidth(600);

        author.setScene(new Scene(window));
        author.show();
    }

    public void notFinishedWindow() {
        Stage notFinished = new Stage();
        notFinished.initModality(Modality.WINDOW_MODAL);
        notFinished.setTitle("Ups!");

        Text text = new Text("Błąd - gra nie jest zakończona");
        text.wrappingWidthProperty().set(300);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        FlowPane window = new FlowPane(text);


        window.setAlignment(Pos.CENTER);
        window.setPrefHeight(100);
        window.setPrefWidth(400);

        notFinished.setScene(new Scene(window));
        notFinished.show();
    }

    public void gratsWindow() {
        Stage grats = new Stage();
        grats.initModality(Modality.WINDOW_MODAL);
        grats.setTitle("Brawo");

        Text text = new Text("Gratulacje - plansza rozwiązana");
        text.wrappingWidthProperty().set(300);
        text.setTextAlignment(TextAlignment.JUSTIFY);
        FlowPane window = new FlowPane(text);


        window.setAlignment(Pos.CENTER);
        window.setPrefHeight(100);
        window.setPrefWidth(400);

        grats.setScene(new Scene(window));
        grats.show();
    }


}


