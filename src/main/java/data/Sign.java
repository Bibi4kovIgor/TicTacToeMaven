package data;

import java.util.Objects;

public enum Sign {
    CROSS('X'),
    ZERO('0'),
    EMPTY(' '),
    QUIT('Q');

    private final char sign;

    Sign(char sign) {
        this.sign = sign;

    }

    public char getSign() {
        return sign;
    }


    public static Sign getSign(char sign) {
        return switch (sign) {
            case 'X', 'x' -> CROSS;
            case '0', 'O', 'o' -> ZERO;
            case 'Q', 'q' -> QUIT;
            default -> EMPTY;
        };
    }

    public static Sign getOppositeSign(Sign sign) {
        return Objects.equals(sign, CROSS) ? ZERO : CROSS;
    }
}
