package io.commands;

import java.util.ArrayList;
import java.util.InputMismatchException;

import persistence.HanseException;
import persistence.HanseGame;

/**
 * Modelliert den Befehl zum Bewegen des Piraten.
 * @author ufufe.
 * @version 1.0
 */
public class MovePirateCommand extends Command {

    private static final String REGEX = "^MOVE PIRATE";
    private static final int EXPECTED_INPUT_LENGTH = 3;
    private static final int INDEX_ISLAND = 2;

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param game Das Spiel in dem dieser Befehl ausgeführt wird.
     */
    public MovePirateCommand(HanseGame game) {
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

        if (!splitInput[INDEX_ISLAND].matches(REGEX_ISLAND)) {
            message.add(ERROR_ISLAND_MALFORMED);
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }

        int island;
        try {
            island = Integer.parseInt(splitInput[INDEX_ISLAND].substring(1));
        } catch (NumberFormatException exception) {
            message.add(Command.ERROR_NOT_AN_INTEGER);
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }

        try {
            return getGame().movePirate(island);
        } catch (HanseException exception) {
            message.add(exception.getMessage());
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }
    }
}
