package draw;

import data.CellState;

final public class Draw {
    public static void drawField(CellState[][] field) {
        drawHorizontalCoordinates(field);
        for (int i = 0; i < field.length; i++) {
            System.out.printf("|-%d-", i);
            for (int j = 0; j < field.length; j++) {
                System.out.print("| ");
                System.out.print(field[i][j].getSign());
                System.out.print(" ");
            }
            System.out.println("|");
        }
    }

    private static void drawHorizontalCoordinates(CellState[][] field) {
        System.out.print("|---");
        for (int i = 0; i < field.length; i++) {
            System.out.printf("|-%d-", i);
        }
        System.out.print("|");
        System.out.println();
    }
}
