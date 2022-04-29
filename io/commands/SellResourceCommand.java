package io.commands;

import java.util.ArrayList;
import java.util.InputMismatchException;

import persistence.HanseException;
import persistence.HanseGame;

/**
 * Modelliert den Befehl zum Verkauf einer Resource.
 * @author ufufe.
 * @version 1.0
 */
public class SellResourceCommand extends Command {

    private static final String REGEX = "^SELL";
    private static final int INDEX_PLAYER = 1;
    private static final int INDEX_RESOURCE = 2;
    private static final int EXPECTED_INPUT_LENGTH = 3;

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param game Das Spiel in dem dieser Befehl ausgeführt wird.
     */
    public SellResourceCommand(HanseGame game) {
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
        String resourceName = splitInput[INDEX_RESOURCE];

        if (!name.matches(REGEX_PLAYER)) {
            message.add(ERROR_PLAYER_MALFORMED);
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }

        int player;
        try {
            player = Integer.parseInt(splitInput[INDEX_PLAYER].substring(1));
        } catch (NumberFormatException exception) {
            message.add(ERROR_NOT_AN_INTEGER);
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }

        if (!resourceName.matches(REGEX_RESOURCE)) {
            message.add(ERROR_RESOURCE_MALFORMED);
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }

        int resource;
        try {
            resource = Integer.parseInt(resourceName.substring(1));
        } catch (NumberFormatException exception) {
            message.add(ERROR_NOT_AN_INTEGER);
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }

        try {
            message.add(getGame().sellResource(player, resourceName, resource));
        } catch (HanseException exception) {
            message.add(exception.getMessage());
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }
        return new ExecutionResult(message, ResultState.SUCCESS_CONTINUE);
    }
}
