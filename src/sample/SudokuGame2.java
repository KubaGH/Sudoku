package sample;

import java.util.*;

public class SudokuGame2 {
    private Integer[] numbers = new Integer[81];
    private Map<Integer, List<Integer>> map = new HashMap<>();
    // struktura danych przechowująca dane <klucz, wartość>
    //klucz = pozycja w tablicy
    //wartość = zbiór liczb do wpisania

    public void handleNewNumber(Integer number, int position) {

        numbers[position] = number;
    }

    public int encodeIndex(Integer columnIndex, Integer rowIndex) {
        int index = rowIndex * 9 + columnIndex;
        // System.out.println(index);
        return index;
    }

    public void decodeIndex(Integer index) {
        int columnIndex = (index % 9);
        int rowIndex = (index / 9);
        System.out.println(columnIndex);
        System.out.println(rowIndex);
    }

    public Integer[] getRow(int rowIndex) {
        Integer[] rowArray = new Integer[9];
        for (int i = 0; i < 9; i++) {
            rowArray[i] = numbers[rowIndex * 9 + i];
        }
        return rowArray;
    }

    public Integer[] getColumn(int columnIndex) {
        Integer[] columnArray = new Integer[9];
        for (int i = 0; i < 9; i++) {
            columnArray[i] = numbers[columnIndex + i * 9];
        }
        return columnArray;
    }

    public Integer[] getSquare(int squareIndex) {
        Integer[] squareArray = new Integer[9];
        for (int i = 0; i < 9; i++) {
            squareArray[i] = numbers[(squareIndex * 9) - ((squareIndex % 3) * 6) + (6 * (i / 3)) + i];
        }
        return squareArray;
    }

    public static Integer randomZeroToEight() {
        double random = Math.random();
        return (int) Math.round((random * 8.0));
    }

    public List<Integer> possibilitiesForPosition(Integer position) {
        Integer rowIndex = position / 9;
        Integer columnIndex = position % 9;
        Integer squareIndex = ((rowIndex / 3) * 3) + (columnIndex / 3);

        Integer[] rowValues = getRow(rowIndex);
        Integer[] columnValues = getColumn(columnIndex);
        Integer[] squareValues = getSquare(squareIndex);

        List<Integer> set = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9));

        for (Integer element : rowValues) {
            if (element != null) {
                set.remove(element);
            }
        }
        for (Integer element : columnValues) {
            if (element != null) {
                set.remove(element);
            }
        }
        for (Integer element : squareValues) {
            if (element != null) {
                set.remove(element);
            }
        }
        return set;

    }

    // 1.pobieramy z tablicy numbers element o określonym indexie
    // 2.sprawdzamy czy element z pkt. 1 jest równy null (komórka nie jest uzupełniona)
    // 3.jeśli jest równy null to losujemy liczbę z tablicy liczb, które możemy wpisać w to pole
    // 4.jeśli tablica liczb jest pusta to cofamy się w pętli o "1", czyli index-- i przechodzimy do kolejnej iteracji w pętli
    // 5.jesli nie jest pusta to w komórkę wpisujemy wylosowaną liczbę
    // 6. z Listy możliwości usuwamy wylosowaną liczbę
    // 7. do Naszej mapy wpisujemy pozycje (index) i pozostałe wartości do wpisania jako wartość
    // 8. inkrementujemy ten index o "1"

    // 9. jeśli element z pkt. 2 nie jest nullem to pobieramy listę możliwości z mapy na podstawie indexu
    // 10. jeżeli Lista możliwości będzie pusta to usuwamy z tej komórki wpisaną liczbę (ustawiamy ją na null)
    // 11. index--
    // 12. przechodzimy do kolejnej iteracji w pętli
    // 13. jeżeli lista możliwości z pkt. 10 nie jest pusta (ma chociaż 1 element) ustawiamy komórkę na kolejną wartość z tej Listy
    // 14. usuwamy tę liczbę z Listy możliwości
    // 15. index ++

    public Integer[] generate() {
        map = new HashMap<>();
        int index = 0;
        while (index <= 80) {
            Integer element = numbers[index];
            if (element == null) {
                List<Integer> possibleValues = possibilitiesForPosition(index);
                Collections.shuffle(possibleValues);
                if (possibleValues.isEmpty()) {
                    index--;
                    continue;
                }
                Integer possibleValue = possibleValues.get(0);
                numbers[index] = possibleValue;
                possibleValues.remove(possibleValue);
                map.put(index, possibleValues);
                index++;

            } else {
                List<Integer> possibleValues = map.get(index);
                if (possibleValues.isEmpty()) {
                    numbers[index] = null;
                    index--;
                    continue;
                }
                Integer possibleValue = possibleValues.get(0);
                possibleValues.remove(possibleValue);
                numbers[index] = possibleValue;
                index++;

            }
        }
        return numbers;
    }

    // 1. Stworzyć listę wszystkich pozycji (od 0 - 80)
    // 2. Schuffle listę
    // 3. Nowa pusta lista
    // 4. Do nowej listy dodajemy liczbę parametrów = "numberOfPositions"

    public List<Integer> createRandomPositions(int numberOfPositions){
        if (numberOfPositions>80) {
            numberOfPositions=80;
        }
        else if (numberOfPositions<1) {
            numberOfPositions=1;
        }
        List<Integer> positions = new ArrayList<>();
        for (int i = 0; i < 81; i++) {
            positions.add(i);
        }
        Collections.shuffle(positions);
        List<Integer> targetPositions = new ArrayList<>();
        for (int i = 0; i < numberOfPositions; i++) {
           targetPositions.add(positions.get(i));
        }
        return targetPositions;
    }

    public void clear() {
        numbers = new Integer[81];
        map = new HashMap<>();
    }

    public boolean checkIfArrayHasNull() {
        for (int i = 0; i < 81; i++) {
            Integer element = numbers[i];
            if (element == null) {
                return true;
            }
        }
        return false;
    }

    public boolean checkDuplicate(Integer[] array) {
        Set<Integer> container = new HashSet<>();
        for (Integer number : array) {
            if (number != null) {
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
            Integer[] column = getColumn(i);
            boolean duplicate = checkDuplicate(column);
            if (duplicate) {
                return true;
            }
        }
        return false;
    }

    public boolean checkIfRowHasDuplicate() {
        for (int i = 0; i < 9; i++) {
            Integer[] row = getRow(i);
            boolean duplicate = checkDuplicate(row);
            if (duplicate) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSquares() {
        for (int i = 0; i < 9; i++) {
            if (checkDuplicate(getSquare(i))) {
                return true;
            }
        }
        return false;
    }
    public boolean isGameIncomplete() {
        if (checkIfArrayHasNull()) {
            System.out.println("Nie wszystkie pola są wypełnione");
            return true;
        }
        if (checkIfColumnHasDuplicate()) {
            System.out.println("W kolumnie są duplikaty");
            return true;
        }
        if (checkIfRowHasDuplicate()) {
            System.out.println("W rzędzie są duplikaty");
            return true;
        }
        return checkSquares();
    }
}
