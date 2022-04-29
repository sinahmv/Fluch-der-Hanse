package io.commands;

import persistence.HanseGame;

/**
 * Modelliert den Befehl zum Beenden des Programms.
 * @author ufufe
 * @version 1.0
 */
public class QuitCommand extends Command {

    private static final String REGEX = "^EXIT$";

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param game Die Administration in der dieser Befehl ausgeführt wird.
     */
    public QuitCommand(HanseGame game) {
        super(REGEX, game);
    }

    @Override
    public ExecutionResult execute(String input) {
        return new ExecutionResult(ResultState.SUCCESS_QUIT);
    }

}
