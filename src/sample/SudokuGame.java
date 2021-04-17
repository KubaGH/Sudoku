package sample;

import javafx.scene.Scene;

import java.util.*;

public class SudokuGame {
    private Integer[][] numbers = new Integer[9][9];

    public void handleNewNumber(Integer number, int columnPosition, int rowPosistion) {
        numbers[columnPosition][rowPosistion] = number;


    }
    //1. Sprawdzenie czy w każdej kolumnie są cyfry 1-9 (bez powtórzeń)
    //2. Sprawdzenie czy w każdym wierszu są cyfry 1-9 (bez powtórzeń)
    //3. Sprawdzenie czy w każdym kwadracie 3x3 są cyfry 1-9 (bez powtórzeń)
    //4. Sprawdzenie czy wszystkie pola są wypełnione.


    // 0,0 jest 2
    // 0,1 jest nullem

    public boolean checkIfArrayHasNull() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                Integer element = numbers[i][j];
                if (element == null) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkDuplicate(Integer[] array) {
        Set<Integer> container = new HashSet<>();
        for (int i = 0; i < 9; i++) {
            Integer number = array[i];
            if (number!=null){
                if (container.contains(number)) {
                    return true;
                } else {
                    container.add(number);
                }
            }
        }
        return false;
    }

    public boolean checkIfColumnHasDuplicate() {
        for (int i = 0; i < 9; i++) {
            Integer[] column = new Integer[9];
            for (int j = 0; j < 9; j++) {
                Integer element = numbers[i][j];
                column[j] = element;
            }
            boolean duplicate = checkDuplicate(column);
            if (duplicate) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfRowHasDuplicate() {
        for (int j = 0; j < 9; j++) {
            Integer[] row = new Integer[9];
            for (int i = 0; i < 9; i++) {
                Integer element = numbers[i][j];
                row[i] = element;
            }
            boolean duplicate = checkDuplicate(row);
            if (duplicate) {
                return true;
            }
        }
        return false;
    }

    //public boolean checkIfRowHasDuplicate() {
    //    for (int i = 0; i < 9; i++) {
    //        Integer[] row = numbers[i];
    //        boolean duplicate = checkDuplicate(row);
    //        if (duplicate) {
    //            return true;
    //        }
    //    }
    //    return false;
    //}
//
    public boolean checkIfSquareHasDuplicate(Integer squareIndex) {

        Integer[] square = new Integer[9];
        for (int i = 0; i < 9; i++) {
            Integer indexRow = (3 * (squareIndex / 3)) + (i / 3);
            Integer indexColumn = (i % 3 + (3 * (squareIndex % 3)));
            square[i] = numbers[indexColumn][indexRow];


        }
        return checkDuplicate(square);
    }

    public boolean checkSquares() {
        for (int i = 0; i < 9; i++) {
            if (checkIfSquareHasDuplicate(i)) {
                return true;
            }
        }
        return false;
    }

    public boolean isGameIncomplete() {
//        if (checkIfArrayHasNull()) {
//            System.out.println("Nie wszystkie pola są wypełnione");
//            return true;
//
//        }
        if (checkIfColumnHasDuplicate()) {
            System.out.println("W kolumnie są duplikaty");
            return true;
        }
        if (checkIfRowHasDuplicate()) {
            return true;
        }
        return checkSquares();
    }

    public void clearGame() {
       numbers = new Integer[9][9];
    }

    public void fillCells(int numberOfCells) {
        List<Integer> positions = new ArrayList<>();
        for (int i=0; i<81; i++){

        }
    }





}
//pętla 0-9; i = wartość, na podstawie i wyciągamy
//będziemy liczyć indeks kolumny i indeks wiersza (np. przy pomocy modulo; dzieleniu całkowitym)
//dla 0:
// 0,0  1,0 2,0
// 0,1  1,1 2,1
// 0,2  1,2 2,2

// dla 1:


//for (int i = 0; i < 9; i += 3) {
//for (int j = 0; j < 9; j += 3) {
//for (int k = 0; k < 9; k++) {
//for (int l = 0; l < 9; l++) {
//Integer element = numbers[i + k % 3][j + l % 3];
//        for (int i = 0; i < 9; i += 3) {
//            for (int j = 0; j < 9; j += 3) {
//                for (int k = i; k < i + 3; k++) {
//                    for (int l = j; l < j + 3; l++) {
//                        Integer element = numbers[k][l];
//                        System.out.println(""+k+");
//                    }
//
//
//                }
//
//            }
//        }