package io;

import data.Flag;
import data.GameBoard;
import data.Island;
import data.Player;
import data.Route;
import data.ShipEnum;
import io.commands.ExecutionResult;
import io.commands.ResultState;
import persistence.HanseGame;

import java.util.ArrayList;

/**
 * Modelliert eine Klasse, die zum Erstellen des Spiels nach den Benutzereingaben benötigt wird.
 * @author ufufe
 * @version 1.0
 */
public class InitializeGame {

    private static final String REGEX_NUMBER = "^[1-9]{1}[0-9]*$";
    private static final String P = "p";
    private static final String PIRATE = "pirate";
    private static final String V = "v";
    private static final String PARAMETER = "parameter ";
    private static final String IS_NOT_AN_INTEGER = " is not an integer";
    private static final String IS_NOT_AN_NUMBER = " is not an number";
    private static final String WRONG_NUMBER_OF_PARAMETERS = "wrong number of parameters";
    private static final int SIX = 6;
    private static final int THREE = 3;
    private static final int TWO = 2;
    private static final String THE_PLAYERS_CAN_TRADE_NOW = "The players can trade now!";
    private static final String ERROR_THERE_NEED_TO_BE_AT_LEAST_TWO_PLAYERS = "there need to be at least two players.";
    private int startGold;
    private int playerCount;
    private int playingRounds;
    private GameBoard board;
    private HanseGame game;
    private ExecutionResult result;

    /**
     * Erstellt das Objekt zum Initialisieren des Spiels.
     * @param args Kommandozeilenargumente.
     */
    public InitializeGame(String[] args) {
        this.buildGame(args);
    }

    /**
     * Gibt das Spiel zurück.
     * @return Spiel.
     */
    public HanseGame getHanseGame() {
        return this.game;
    }

    /**
     * Gibt das Result des Ausführens zurück.
     * @return Result.
     */
    public ExecutionResult getResult() {
        return this.result;
    }

    /**
     * Kreiert das Spiel, nachdem es die Kommandozeilenargumente und den Aufbau des Spielfeldes checkt
     * @param args Kommandozeilenargumente.
     */
    private void buildGame(String[] args) {
        ExecutionResult result = this.checkArguments(args);
        if (result.getState() == ResultState.FAILURE_QUIT) {
            this.result = result;
            return;
        }
        result = this.buildGameBoard();
        if (result.getState() == ResultState.FAILURE_QUIT) {
            this.result = result;
            return;
        }
        result = this.createHanseGame();
        if (result.getState() == ResultState.FAILURE_QUIT) {
            this.result = result;
            return;
        }
        this.result = new ExecutionResult(ResultState.SUCCESS_CONTINUE);
    }

    /**
     * Überprüft die Kommandozeilenargumente.
     * @param args Kommandozeilenargumente.
     * @return Result.
     */
    private ExecutionResult checkArguments(String[] args) {
        ArrayList<String> message = new ArrayList<>();
        if (args.length != THREE) {
            message.add(WRONG_NUMBER_OF_PARAMETERS);
            return new ExecutionResult(message, ResultState.FAILURE_QUIT);
        }
        int[] arguments = new int[THREE];
        for (int i = 0; i < args.length; i++) {
            if (!args[i].matches(REGEX_NUMBER)) {
                message.add(PARAMETER + (i + 1) + IS_NOT_AN_NUMBER);
                return new ExecutionResult(message, ResultState.FAILURE_QUIT);
            }
            try {
                arguments[i] = Integer.parseInt(args[i]);
            } catch (NumberFormatException notParsableToInt) {
                message.add(PARAMETER + (i + 1) + IS_NOT_AN_INTEGER);
                return new ExecutionResult(message, ResultState.FAILURE_QUIT);
            }
        }
        
        this.playerCount = arguments[0];
        this.playingRounds = arguments[1];
        this.startGold = arguments[TWO];
        if (!(this.playerCount > 1)) {
            message.add(ERROR_THERE_NEED_TO_BE_AT_LEAST_TWO_PLAYERS);
            return new ExecutionResult(message, ResultState.FAILURE_QUIT);
        }
        return new ExecutionResult(ResultState.SUCCESS_CONTINUE);
    }

    /**
     * Baut das Spielfeld auf.
     * @return Spielfeld.
     */
    private ExecutionResult buildGameBoard() {
        ArrayList<Island> islands = new ArrayList<>();
        for (int i = 1; i < playerCount + TWO; i++) {
            islands.add(new Island((V + i)));
        }
        ArrayList<ArrayList<Route>> routes = new ArrayList<>();
        for (int i = 0; i < islands.size() - 1; i++) {
            ArrayList<Route> temporaryRoutes = new ArrayList<>();
            for (int j = i + 1; j < islands.size(); j++) {
                int pirateMarker = Math.min((j - i), ((playerCount + 1) - (j - i)));
                if (pirateMarker > SIX) {
                    pirateMarker = SIX;
                }
                Route route = new Route(islands.get(i), islands.get(j), pirateMarker);
                temporaryRoutes.add(route);
            }
            routes.add(temporaryRoutes);
        }
        this.board = new GameBoard(islands, routes);
        return new ExecutionResult(ResultState.SUCCESS_CONTINUE);
    }

    /**
     * Kreiert das Spiel.
     */
    private ExecutionResult createHanseGame() {
        ArrayList<String> output = new ArrayList<>();
        output.add(THE_PLAYERS_CAN_TRADE_NOW);
        ArrayList<Player> players = new ArrayList<>();
        for (int i = 1; i <= this.playerCount; i++) {
            Player player = new Player((P + i), this.startGold, ShipEnum.COG, this.board.getIslandByNumber(i), 0);
            players.add(player);
            this.board.getIslandByNumber(i).insertPlayer(player);
        }
        Player pirate = new Player(PIRATE, 0, ShipEnum.COG, this.board.getIslandByNumber(this.playerCount + 1), 1);
        pirate.setFlag(Flag.PIRATE);
        this.game = new HanseGame(this.board, players, pirate, this.playingRounds);
        return new ExecutionResult(output, ResultState.SUCCESS_CONTINUE);
    }
}
