package io.commands;

import java.util.ArrayList;
import java.util.InputMismatchException;

import data.ShipEnum;
import persistence.HanseException;
import persistence.HanseGame;

/**
 * Modelliert den Befehl zum Kaufen einer Kanone.
 * @author ufufe
 * @version 1.0
 */
public class BuyCommand extends Command {

    private static final String REGEX = "^BUY";
    private static final int EXPECTED_INPUT_LENGTH_MIN = 3;
    private static final int EXPECTED_INPUT_LENGTH_MAX = 4;
    private static final int INDEX_PLAYER = 1;
    private static final int INDEX_COMMAND_TYPE = 2;
    private static final int INDEX_SHIP_TYPE = 3;
    private static final String REGEX_RESOURCE = "RESOURCE";
    private static final String REGEX_CANNON = "CANNON";
    private static final String REGEX_SHIP = "SHIP";
    private static final String ERROR_NOT_A_VALID_BUY_COMMAND = "not a valid BUY command";
    private static final String ERROR_SHIP_CLASS_MALFORMED = "ship class malformed";

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param game Das Spiel in dem dieser Befehl ausgeführt wird.
     */
    public BuyCommand(HanseGame game) {
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

        if (!splitInput[INDEX_PLAYER].matches(Command.REGEX_PLAYER)) {
            message.add(Command.ERROR_PLAYER_MALFORMED);
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }

        int player;
        try {
            player = Integer.parseInt(splitInput[INDEX_PLAYER].substring(1));
        } catch (NumberFormatException exception) {
            message.add(ERROR_NOT_AN_INTEGER);
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }

        if (splitInput[INDEX_COMMAND_TYPE].equals(REGEX_RESOURCE)
                || splitInput[INDEX_COMMAND_TYPE].equals(REGEX_CANNON)) {
            if (splitInput.length != EXPECTED_INPUT_LENGTH_MIN) {
                message.add(Command.ERROR_WRONG_PARAMETER_COUNT);
                return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
            }
            try {
                message.add(getGame().buySomething(splitInput[INDEX_COMMAND_TYPE], player, null));
                return new ExecutionResult(message, ResultState.SUCCESS_CONTINUE);
            } catch (HanseException exception) {
                message.add(exception.getMessage());
                return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
            }
        }

        if (splitInput[INDEX_COMMAND_TYPE].equals(REGEX_SHIP)) {
            if (splitInput.length != EXPECTED_INPUT_LENGTH_MAX) {
                message.add(Command.ERROR_WRONG_PARAMETER_COUNT);
                return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
            }
            for (ShipEnum ship : ShipEnum.values()) {
                if (splitInput[INDEX_SHIP_TYPE].equals(ship.getName().toLowerCase())) {
                    try {
                        message.add(getGame().buySomething(splitInput[INDEX_COMMAND_TYPE],
                                player, splitInput[INDEX_SHIP_TYPE]));
                        return new ExecutionResult(message, ResultState.SUCCESS_CONTINUE);
                    } catch (HanseException exception) {
                        message.add(exception.getMessage());
                        return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
                    }
                }
            }
            message.add(ERROR_SHIP_CLASS_MALFORMED);
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }

        message.add(ERROR_NOT_A_VALID_BUY_COMMAND);
        return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);


       
    }
}
