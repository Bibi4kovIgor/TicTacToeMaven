package gameplay;

import data.GameStates;
import data.CellState;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import static data.GameStates.*;
import static data.CellState.*;
import static draw.Draw.drawField;
import static java.lang.System.exit;

public class GamePlay {
    private static final String INPUT_SIGN_WELCOME =
            "Input sign [X | 0] (X default if any) or q (Q) for exit: ";
    private static final String INPUT_COORDINATES_X_Y =
            "Input coordinates [row,column] for %c\n";
    private static final String WRONG_INPUT_PARAMETERS =
            "Wrong coordinates parameters";

    private static final String QUIT = "q";
    public static final String SIGN_PATTERN = "[Xx0oOQq]";
    private final CellState[][] field;
    private final Integer size;

    private int signQuant;


    public GamePlay(Integer size) {
        this.size = size;
        field = new CellState[size][size];
        initializeWithEmpties();
    }

    public GamePlay(CellState[][] field, Integer size) {
        this.size = size;
        this.field = field;
    }

    private void initializeWithEmpties() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = EMPTY;
            }
        }
    }

    private GameStates getWinner(CellState cellState) {
        return cellState == CROSS ? WINNER_X : WINNER_0;
    }

    public GameStates checkGameState(CellState cellState) {
        if (isHorizontalCompleted(cellState)
                || isMainDiagonalCompleted(cellState)
                || isSideDiagonalCompleted(cellState)
                || isVerticalCompleted(cellState)) {
            return getWinner(cellState);
        } else if (isTie()) {
            return TIE;
        }

        return GAME_IS_CONTINUE;
    }

    private boolean isArrayValuesEquals(CellState[] temp, CellState cellState) {
        for (CellState c : temp) {
            if (c != cellState) {
                return false;
            }
        }
        return true;
    }

    private boolean isHorizontalCompleted(CellState cellState) {
        CellState[] line = new CellState[size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[i][j] == cellState) {
                    line[j] = field[i][j];
                }
            }
            if (isArrayValuesEquals(line, cellState)) {
                return true;
            }
//            Arrays.fill(line, EMPTY);
        }
        return false;
    }

    private boolean isVerticalCompleted(CellState cellState) {
        CellState[] column = new CellState[size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[j][i] == cellState) {
                    column[j] = field[j][i];
                }
            }
            if (isArrayValuesEquals(column, cellState)) {
                return true;
            }
            Arrays.fill(column, EMPTY);
        }
        return false;
    }

    private boolean isMainDiagonalCompleted(CellState cellState) {
        for (int i = 0; i < size; i++) {
            if (field[i][i] != cellState) {
                return false;
            }
        }
        return true;
    }

    private boolean isSideDiagonalCompleted(CellState cellState) {
        for (int i = 0; i < size; i++) {
            if (field[i][size - i - 1] != cellState) {
                return false;
            }
        }
        return true;
    }

    private boolean isTie() {
        for (CellState[] rows : field) {
            for (CellState element : rows) {
                if (element == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean inputTurnsCoordinates(int x, int y, CellState cellState) {
        if (x >= size || x < 0 || y >= size || y < 0) {
            return false;
        }

        if (field[x][y] == EMPTY) {
            field[x][y] = cellState;
        } else {
            return false;
        }
        return true;
    }

    private int[] inputCoordinates() {
        final Scanner scanner = new Scanner(System.in);
        int[] inputArray = new int[2];
        try {
            scanner.useDelimiter("\\s|,|\\n");
            inputArray[0] = scanner.nextInt();
            inputArray[1] = scanner.nextInt();
        } catch (InputMismatchException exception) {
            Arrays.fill(inputArray, -1);
        }
        return inputArray;
    }

    private CellState inputSign() {
        CellState cellState;
        Scanner scanner = new Scanner(System.in);

        final String input = scanner.hasNext(SIGN_PATTERN) ? scanner.next() : CROSS.toString();
        cellState = getSign(input.charAt(0));

        if (Objects.equals(input, QUIT)) {
            exit(0);
        }

        return cellState;
    }

    public void game() {
        GameStates currentGameState = GAME_IS_CONTINUE;

        drawField(field);
        System.out.print(INPUT_SIGN_WELCOME);

        CellState cellState = inputSign();
        System.out.printf(INPUT_COORDINATES_X_Y, cellState.getSign());

        do {
            int[] coordinates = inputCoordinates();
            int x = coordinates[0];
            int y = coordinates[1];

            if (!inputTurnsCoordinates(x, y, cellState)) {
                printErrorCoordinatesMessage();
                continue;
            }

            drawField(field);

            signQuant++;
            if(!isWinnerNotExist()) {
                currentGameState = checkGameState(cellState);
            }

            cellState = CellState.getOppositeSign(cellState);

            printCurrentGameState(String.format("%s %c", currentGameState.getGameStateMessage(), cellState.getSign()));
        } while (Objects.equals(currentGameState, GAME_IS_CONTINUE));
    }

    private void printCurrentGameState(String currentGameState) {
        System.out.println(currentGameState);
    }

    private void printErrorCoordinatesMessage() {
        printCurrentGameState(WRONG_INPUT_PARAMETERS);
    }

    private boolean isWinnerNotExist(){
        return signQuant < size * 2 - 1;
    }


}
