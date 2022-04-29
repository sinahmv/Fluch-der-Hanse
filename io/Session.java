package io;

import io.commands.ResultState;
import io.commands.ExecutionResult;
import io.commands.Command;
import io.commands.BuyCommand;
import io.commands.DepartCommand;
import io.commands.MovePirateCommand;
import io.commands.QuitCommand;
import io.commands.RollCommand;
import io.commands.SellResourceCommand;
import io.commands.ShowBoardCommand;
import io.commands.ShowPlayerCommand;
import persistence.HanseGame;

import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;

/**
 * Modelliert die Session.
 * @author ufufe
 * @version 1.0
 */
public class Session {

    private static final String ERROR_NO_COMMAND_GIVEN = "Error, no command was given.";
    private static final String ERROR = "Error, ";
    private static final String THE_PLAYERS_CAN_TRADE_NOW = "The players can trade now!";
    private HanseGame hanseGame;
    private String[] arguments;
    private LinkedList<Command> commands;

    /**
     * Gibt das Session-Objekt zurück.
     * @param args Kommandozeilenargumente.
     */
    public Session(String[] args) {
        this.arguments = args;
    }

    /**
     * Startet das Programm und lässt es laufen.
     */
    public void run() {

        InitializeGame newGame = new InitializeGame(arguments);
        ExecutionResult result = newGame.getResult();
        if (result.getState() == ResultState.FAILURE_QUIT) {
            for (String message: result.getMessage()) {
                System.out.println(ERROR + message);
            }
            return;
        } else {
            this.hanseGame = newGame.getHanseGame();
            this.commands = new LinkedList<>();
            this.setCommands();
        }

        System.out.println(THE_PLAYERS_CAN_TRADE_NOW);

        while (true) {
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();

            Command command = findMatchingCommand(input);
            if (Objects.isNull(command)) {
                System.out.println(ERROR_NO_COMMAND_GIVEN);
                continue;
            }

            ExecutionResult executionResult = command.execute(input);

            if (executionResult.getState() == ResultState.SUCCESS_CONTINUE && executionResult.hasMessage()) {
                for (String message: executionResult.getMessage()) {
                    System.out.println(message);
                }
            } else if (executionResult.getState() == ResultState.FAILURE_CONTINUE && executionResult.hasMessage()) {
                for (String message: executionResult.getMessage()) {
                    System.out.println(ERROR + message);
                }

            }
            if (executionResult.getState() == ResultState.FAILURE_QUIT
                || executionResult.getState() == ResultState.SUCCESS_QUIT) {
                for (String message: executionResult.getMessage()) {
                    System.out.println(message);
                }
                break;
            }
        }
    }


    /**
     * Legt die Befehle fest und fügt sie zum System hinzu.
     */
    private void setCommands() {
        this.commands.add(new QuitCommand(this.hanseGame));
        this.commands.add(new ShowBoardCommand(this.hanseGame));
        this.commands.add(new ShowPlayerCommand(this.hanseGame));
        this.commands.add(new BuyCommand(this.hanseGame));
        this.commands.add(new SellResourceCommand(this.hanseGame));
        this.commands.add(new RollCommand(this.hanseGame));
        this.commands.add(new MovePirateCommand(this.hanseGame));
        this.commands.add(new DepartCommand(this.hanseGame));
    }

    /**
     * Gibt den gesuchten Befehl zurück.
     * @param input Kommandozeilenargument.
     * @return Den Befehl.
     */
    private Command findMatchingCommand(String input) {
        for (Command command : commands) {
            if (command.matchesInput(input)) {
                return command;
            }
        }
        return null;
    }
}

