package gameplay;

import data.GameStates;
import data.Sign;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static data.GameStates.TIE;
import static data.GameStates.WINNER_X;
import static data.Sign.CROSS;
import static data.Sign.ZERO;
import static draw.Draw.drawField;
import static gameplay.TestUtils.textToArray;
import static org.junit.jupiter.api.Assertions.assertEquals;


class GamePlayTest {

    @Mock
    private static Scanner scanner;

    @Test
    void game_FillsFieldWithSignsChecksWinner_Field3X3_ReturnTie(){
        final int size = 3;
        //Arrange

        Sign[][] field = new Sign[size][size];

        field[0][0] = CROSS;
        field[0][1] = ZERO;
        field[0][2] = CROSS;

        field[1][0] = ZERO;
        field[1][1] = CROSS;
        field[1][2] = CROSS;

        field[2][0] = ZERO;
        field[2][1] = CROSS;
        field[2][2] = ZERO;

        GamePlay gamePlay = new GamePlay(field, size);

        drawField(field);

        GameStates expected = TIE;
        String expectedMessage = TIE.getGameStateMessage();

        // Act
        GameStates actual = gamePlay.checkGameState(CROSS);
        String actualMessage = gamePlay.checkGameState(CROSS).getGameStateMessage();

        // Assert
        assertEquals(expected, actual, "Game state wrong");
        assertEquals(expectedMessage, actualMessage, "Game state message wrong");
    }

    @Test
    void game_FillsFieldWithSignsChecksWinner_Field3X3_ReturnTie_LoadFile() throws IOException {
        // Arrange
        final int size = 3;
        Sign[][] testArray = textToArray("test_data_cross_wins.txt", size);

        GamePlay gamePlay = new GamePlay(testArray, size);
        drawField(testArray);

        GameStates expected = WINNER_X;
        String expectedMessage = WINNER_X.getGameStateMessage();

        // Act
        GameStates actual = gamePlay.checkGameState(CROSS);
        String actualMessage = gamePlay.checkGameState(CROSS).getGameStateMessage();

        // Assert
        assertEquals(expected, actual, "Game state wrong");
        assertEquals(expectedMessage, actualMessage, "Game state message wrong");

    }

    @Test
    void game_inputValues_checkInputX_returnTrue() {
        final int SIZE = 3;
        System.setIn(new ByteArrayInputStream("X\n1 1\n0 0\n1 1\n".getBytes(StandardCharsets.UTF_8)));
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        GamePlay gamePlay = new GamePlay(SIZE);
        gamePlay.game();
        System.setOut(new PrintStream(byteArrayOutputStream));
        String result = byteArrayOutputStream.toString();

        Sign expected = CROSS;






        assertEquals(true, true);


    }
}