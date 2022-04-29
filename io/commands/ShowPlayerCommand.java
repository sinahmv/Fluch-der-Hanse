package io.commands;

import java.util.ArrayList;
import java.util.InputMismatchException;

import persistence.HanseException;
import persistence.HanseGame;

/**
 * Modelliert den Befehl zum Anzeigen eines Spielers und seiner Statistiken.
 * @author ufufe.
 * @version 1.0
 */
public class ShowPlayerCommand extends Command {

    private static final String REGEX = "^SHOW PLAYER";
    private static final int INDEX_PLAYER = 2;
    private static final int EXPECTED_INPUT_LENGTH = 3;

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param game Das Spiel in dem dieser Befehl ausgeführt wird.
     */
    public ShowPlayerCommand(HanseGame game) {
        super(REGEX, game);
    }

    @Override
    public ExecutionResult execute(String input) {
        ArrayList<String> message = new ArrayList<>();
        try {
            checkSplitter(input, Command.DELIMITER_COMMAND_DEFAULT);
        } catch (InputMismatchException exception) {
            message.add(exception.getMessage());
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }

        String[] splitInput = input.split(Command.DELIMITER_COMMAND_DEFAULT);

        if (splitInput.length != EXPECTED_INPUT_LENGTH) {
            message.add(Command.ERROR_WRONG_PARAMETER_COUNT);
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }

        String name = splitInput[INDEX_PLAYER];

        if (name.equals(REGEX_PIRATE)) {
            message.add(getGame().showPirate());
        } else if (name.matches(REGEX_PLAYER)) {
            try {
                message.add(getGame().showPlayer(name));
            } catch (HanseException exception) {
                message.add(exception.getMessage());
                return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
            }
            
        } else {
            message.add(ERROR_PLAYER_MALFORMED);
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }

        return new ExecutionResult(message, ResultState.SUCCESS_CONTINUE);
    }
}
