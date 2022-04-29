package io.commands;

import java.util.ArrayList;
import java.util.InputMismatchException;

import persistence.HanseException;
import persistence.HanseGame;

/**
 * Klasse zum Modellieren des Befehls, welcher es Spielern erlaubt den Würfel zu werfen.
 * @author ufufe
 * @version 1.0
 */
public class RollCommand extends Command {

    private static final String ERROR_DICE_MALFORMED = "dice parameter malformed";
    private static final String REGEX = "^ROLL";
    private static final String REGEX_DICE =  "^[1-6]{1}$";
    private static final int EXPECTED_INPUT_LENGTH = 2;
    private static final int INDEX_NUMBER = 1;


    /**
     * Gibt das Befehl-Objekt zurück.
     * @param game Das Spiel in dem dieser Befehl ausgeführt wird.
     */
    public RollCommand(HanseGame game) {
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

        if (!splitInput[INDEX_NUMBER].matches(REGEX_DICE)) {
            message.add(ERROR_DICE_MALFORMED);
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }

        int number;
        try {
            number = Integer.parseInt(splitInput[INDEX_NUMBER]);
        } catch (NumberFormatException exception) {
            message.add(Command.ERROR_NOT_AN_INTEGER);
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }
        
        try {
            return getGame().rollExecution(number); 
        } catch (HanseException exception) {
            message.add(exception.getMessage());
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }
    }
}
