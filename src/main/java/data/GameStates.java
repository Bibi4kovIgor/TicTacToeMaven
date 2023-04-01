package data;

public enum GameStates {
    WINNER_X("X won's!"),
    WINNER_0("0 won's!"),
    TIE("Tie!"),
    GAME_IS_CONTINUE("Waiting for your move: ");

    private final String gameStateMessage;

    GameStates(String gameState) {
        this.gameStateMessage = gameState;
    }

    public String getGameStateMessage() {
        return gameStateMessage;
    }
}
