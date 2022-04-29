package io.commands;

import java.util.ArrayList;
import java.util.InputMismatchException;

import data.Flag;
import persistence.HanseException;
import persistence.HanseGame;

/**
 * Modelliert der Befehl zum Eintrag in ein Logbuch.
 * @author ufufe.
 * @version 1.0
 */
public class DepartCommand extends Command {

    private static final String REGEX = "^DEPART";
    private static final int INDEX_PLAYER = 1;
    private static final int INDEX_ISLAND = 2;
    private static final int INDEX_FLAG = 3;
    private static final int EXPECTED_INPUT_LENGTH = 4;

    /**
     * Gibt das Befehl-Objekt zurück.
     * @param game Das Spiel in dem dieser Befehl ausgeführt wird.
     */
    public DepartCommand(HanseGame game) {
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
        String islandName = splitInput[INDEX_ISLAND];
        String flag = splitInput[INDEX_FLAG];

        if (!name.matches(REGEX_PLAYER)) {
            message.add(ERROR_PLAYER_MALFORMED);
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }

        int player;
        try {
            player = Integer.parseInt(name.substring(1));
        } catch (NumberFormatException exception) {
            message.add(ERROR_NOT_AN_INTEGER);
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }

        if (!islandName.matches(REGEX_ISLAND)) {
            message.add(ERROR_ISLAND_MALFORMED);
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }

        int island;
        try {
            island = Integer.parseInt(islandName.substring(1));
        } catch (NumberFormatException exception) {
            message.add(ERROR_NOT_AN_INTEGER);
            return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
        }

        for (Flag forFlag : Flag.values()) {
            if (flag.equals(forFlag.name().toLowerCase())) {
                try {
                    message.addAll(getGame().depart(player, island, flag));
                    return new ExecutionResult(message, ResultState.SUCCESS_CONTINUE);
                } catch (HanseException exception) {
                    message.add(exception.getMessage());
                    return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
                }
            }
        }
        message.add(ERROR_FLAG_MALFORMED);
        return new ExecutionResult(message, ResultState.FAILURE_CONTINUE);
    }
}
