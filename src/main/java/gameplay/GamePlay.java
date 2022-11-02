package gameplay;

import data.GameStates;
import data.Sign;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Objects;
import java.util.Scanner;

import static data.GameStates.*;
import static data.Sign.*;
import static draw.Draw.drawField;
import static java.lang.System.exit;

public class GamePlay {
    public static final String INPUT_SIGN_WELCOME = "Input sign [X | 0] (X default if any) or q (Q) for exit: ";
    public static final String INPUT_COORDINATES_X_Y = "Input coordinates [row,column] for %c\n";
    public static final String WRONG_INPUT_PARAMETERS =
            "Wrong coordinates parameters";
    public static final String WRONG_INPUT_PARAMETERS_SET_X =
            "Wrong sign parameter set 'X' by default";

    private final Sign[][] field;
    private final Integer size;
    private final Scanner scanner;

    public GamePlay(Integer size) {
        this.size = size;
        field = new Sign[size][size];
        scanner = new Scanner(System.in);
        initializeWithEmpties();
    }

    public GamePlay(Sign[][] field, Integer size) {
        this.size = size;
        this.field = field;
        scanner = new Scanner(System.in);
    }

    private void initializeWithEmpties() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                field[i][j] = EMPTY;
            }
        }
    }

    public Sign[][] getField() {
        return field;
    }


    private GameStates getWinner(Sign sign) {
        return sign == CROSS ? WINNER_X : WINNER_0;
    }

    public GameStates checkGameState(Sign sign) {
        if (isHorizontalCompleted(sign)
                || isMainDiagonalCompleted(sign)
                || isSideDiagonalCompleted(sign)
                || isVerticalCompleted(sign)) {
            return getWinner(sign);
        } else if (isTie()) {
            return TIE;
        }

        return GAME_IS_CONTINUE;
    }

    private boolean isArrayValuesEquals(Sign[] temp, Sign sign) {
        for (Sign c : temp) {
            if (c != sign) {
                return false;
            }
        }
        return true;
    }

    private boolean isHorizontalCompleted(Sign sign) {
        Sign[] line = new Sign[size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[i][j] == sign) {
                    line[j] = field[i][j];
                }
            }
            if (isArrayValuesEquals(line, sign)) {
                return true;
            }
            Arrays.fill(line, EMPTY);
        }
        return false;
    }

    private boolean isVerticalCompleted(Sign sign) {
        Sign[] column = new Sign[size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[j][i] == sign) {
                    column[j] = field[j][i];
                }
            }
            if (isArrayValuesEquals(column, sign)) {
                return true;
            }
            Arrays.fill(column, EMPTY);
        }
        return false;
    }

    private boolean isMainDiagonalCompleted(Sign sign) {
        for (int i = 0; i < size; i++) {
            if (field[i][i] != sign) {
                return false;
            }
        }
        return true;
    }

    private boolean isSideDiagonalCompleted(Sign sign) {
        for (int i = 0; i < size; i++) {
            if (field[i][size - i - 1] != sign) {
                return false;
            }
        }
        return true;
    }

    private boolean isTie() {
        for (Sign[] rows : field) {
            for (Sign element : rows) {
                if (element == EMPTY) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean inputTurnsCoordinates(int x, int y, Sign sign) {
        if (x >= size || x < 0 || y >= size || y < 0) {
            return false;
        }

        if (field[x][y] == EMPTY) {
            field[x][y] = sign;
        } else {
            return false;
        }
        return true;
    }

    private int[] inputValues() {
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

    public void game() {
        GameStates currentGameState = GAME_IS_CONTINUE;

        drawField(getField());

        Sign sign = getSignFromInput();

        do {
            int[] coordinates = inputValues();
            int x = coordinates[0];
            int y = coordinates[1];
            if (!inputTurnsCoordinates(x, y, sign)) {
                printErrorCoordinatesMessage();
                continue;
            }
            drawField(getField());

            currentGameState = checkGameState(sign);

            sign = Sign.getOppositeSign(sign);

            printCurrentGameState(currentGameState.getGameStateMessage());
        } while (Objects.equals(currentGameState, GAME_IS_CONTINUE));
    }

    private void printCurrentGameState(String currentGameState) {
        System.out.println(currentGameState);
    }

    private void printErrorCoordinatesMessage() {
        printCurrentGameState(WRONG_INPUT_PARAMETERS);
    }

    private Sign getSignFromInput() {

        System.out.print(INPUT_SIGN_WELCOME);
        Sign sign;

        final String input = scanner.hasNext("X|x|0|o|O") ? scanner.next() : "X";
        sign = getSign(input.charAt(0));
        System.out.printf(INPUT_COORDINATES_X_Y, sign.getSign1());

        if (Objects.equals(sign, QUIT)) {
            exit(0);
        }

        return sign;
    }
}
