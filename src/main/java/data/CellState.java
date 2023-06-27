package data;

import java.util.Objects;

public enum CellState {
    CROSS('X'),
    ZERO('0'),
    EMPTY(' ');

    private final char sign;

    CellState(char sign) {
        this.sign = sign;

    }

    public char getSign() {
        return sign;
    }


    public static CellState getSign(char sign) {
        return switch (sign) {
            case 'X', 'x' -> CROSS;
            case '0', 'O', 'o' -> ZERO;
            default -> EMPTY;
        };
    }

    public static CellState getOppositeSign(CellState cellState) {
        return Objects.equals(cellState, CROSS) ? ZERO : CROSS;
    }
}
