package io.commands;

import persistence.HanseGame;

/**
 * Modelliert den Befehl zum Anzeigen des Spielbrettes.
 * @author ufufe.
 * @version 1.0
 */
public class ShowBoardCommand extends Command {

    private static final String REGEX = "^SHOW BOARD$";

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param game Das Spiel in dem dieser Befehl ausgeführt wird.
     */
    public ShowBoardCommand(HanseGame game) {
        super(REGEX, game);
    }

    @Override
    public ExecutionResult execute(String input) {
        return new ExecutionResult(getGame().showBoard(), ResultState.SUCCESS_CONTINUE);
    }
}
