package persistence;

import data.*;
import data.Error;
import io.commands.ExecutionResult;
import io.commands.ResultState;

import java.util.ArrayList;
import java.util.Objects;

/** Klasse zum Verwalten des gesamten Spiels.
 * @author ufufe
 * @version 1.0 */
public class HanseGame {
    private static final int TWO = 2;
    private static final int FIVE = 5;
    private static final int TEN = 10;
    private final GameBoard currentBoard;
    private ArrayList<Player> players;
    private final Player pirate;
    private int round;
    private final int maxRound;
    private Player currentPlayer;
    private Player secondPlayer;
    private GamePhase currentPhase;

    /** Erstellt das gesamte Spiel-Objekt.
     * @param currentBoard Spielfeld.
     * @param players Spieler.
     * @param pirate Pirat.
     * @param maxRound Maximale Rundenzahl. */
    public HanseGame(GameBoard currentBoard, ArrayList<Player> players, Player pirate, int maxRound) {
        this.currentBoard = currentBoard;
        this.players = players;
        this.pirate = pirate;
        this.maxRound = maxRound;
        this.round = 1;
        this.currentPlayer = null;
        this.currentPhase = GamePhase.TRADE_AND_DEPART;
    }

    /** Gibt eine Route aufgrund von zwei Nummern zurück.
     * @param start Nummer der Startinsel.
     * @param end Nummer der Zielinsel.
     * @return Die Route. */
    public Route getRoute(int start, int end) {
        return this.currentBoard.getRouteByNodes(start, end);
    }

    /** Zeigt das aktuelle Spielbrett.
     * @return Das aktuelle Spielbrett als Ausgabe. */
    public ArrayList<String> showBoard() {
        ArrayList<String> output = new ArrayList<>();
        output.add(Output.ROUND.toString() + round + Output.SLASH.toString() + maxRound);
        output.add(Output.PLAYERS.toString());
        for (Player player : players) {
            output.add(player.toStringBoard());
        }
        output.add(this.pirate.getName() + Output.COLON.toString() + this.pirate.getIsland().getName());
        output.add(Output.ROUTES.toString());
        output.addAll(this.currentBoard.printRoutes());
        return output;
    }

    /** Zeigt die Statistiken eines Spielers.
     * @param name Name des Spielers.
     * @return Ausgabe.
     * @throws HanseException die eigene Exception des Spiels. */
    public String showPlayer(String name) throws HanseException {
        for (Player player : players) {
            if (Objects.equals(player.getName(), name)) {
                return (player.toString());
            }
        } throw new HanseException(Error.ERROR_PLAYER_NOT_FOUND.toString());
    }

    /** Ausgabe für den Piraten wird erzeugt.
     * @return Ausgabe. */
    public String showPirate() {
        return (Output.ISLAND.toString() + this.pirate.getIsland().getName());
    }

    /** Gibt einen Spieler an einem gegebenen Index zurück.
     * @param indexUncorrected Index.
     * @return Ausgabe.
     * @throws HanseException eigene Exception des Spiels. */
    private Player getPlayerByNumber(int indexUncorrected) throws HanseException {
        int index = indexUncorrected - 1;
        if (index >= this.players.size() || index < 0) {
            throw new HanseException(Error.ERROR_PLAYER_NOT_FOUND.toString());
        } return this.players.get(index);
    }

    /** Methode, um alle Käufe durchzuführen.
     * @param type Art des zu kaufenden Objekts.
     * @param playerNumber Spieler.
     * @param shipType Im Falle des Kaufs eines Schiffs der Schiffstyp.
     * @return Ausgabe.
     * @throws HanseException eigene Exception des Spiels. */
    public String buySomething(String type, int playerNumber, String shipType) throws HanseException {
        if (this.currentPhase != GamePhase.TRADE_AND_DEPART) {
            throw new HanseException(Error.ERROR_NOT_TRADE_TIME.toString());
        }
        Player player = this.getPlayerByNumber(playerNumber);
        if (player.getRoute() != null) {
            throw new HanseException(Error.ERROR_PLAYER_ALREADY_DEPARTED.toString());
        }
        if (this.pirate.getIsland() == player.getIsland()) {
            throw new HanseException(Error.ERROR_PIRATE_IS_ON_ISLAND.toString());
        }
        if (type.equals(Output.RESOURCE.toString())) {
            if (player.getResources() >= player.getShipState().getStorage()) {
                throw new HanseException(Error.ERROR_NOT_ENOUGH_STORAGE.toString());
            }
            if (player.getResources() >= player.getShipState().getStorage()) {
                throw new HanseException(Error.ERROR_NOT_ENOUGH_STORAGE.toString());
            }
            Island island = player.getIsland();
            Resource newResource = new Resource(island);
            player.addResource(newResource);
            player.setGold(player.getGold() - FIVE);
            return (player.getName() + Output.BUYS.toString() + newResource.getName() + Output.FOR_GOLD.toString());
        } else if (type.equals(Output.CANNON.toString())) {
            if (player.getGold() < TEN) {
                throw new HanseException(Error.ERROR_NOT_ENOUGH_GOLD.toString());
            }
            if (player.getShipState().getCannons() <= player.getCannons()) {
                throw new HanseException(Error.ERROR_CANNON_SPACE.toString());
            }
            player.setCannons(player.getCannons() + 1);
            player.setGold(player.getGold() - TEN);
            return (player.getName() + Output.FOR_GOLD_CANNON.toString());
        } else {
            ShipEnum newShip = ShipEnum.valueOf(shipType.toUpperCase());
            if (player.getGold() >= newShip.getPrice()) {
                if (player.getCannons() > newShip.getCannons()) {
                    player.setCannons(newShip.getCannons());
                } else if (player.getResources() > newShip.getStorage()) {
                    throw new HanseException(Error.ERROR_NOT_ENOUGH_STORAGE.toString());
                }
                player.setShipState(newShip);
                player.setGold(player.getGold() - newShip.getPrice());
                return (player.getName() + Output.BUYS.toString() + newShip.getName() + Output.FOR.toString()
                        + newShip.getPrice()
                        + Output.GOLD.toString());
            } else {
                throw new HanseException(Error.ERROR_NOT_ENOUGH_GOLD.toString());
            }
        }
    }

    /** Ermöglicht es einem Spieler eine Resource zu verkaufen.
     * @param playerNumber Name des Spielers.
     * @param resourceName Resource.
     * @param resource Kennung der Resource.
     * @return Ausgabe.
     * @throws HanseException eigene Exception des Spiels. */
    public String sellResource(int playerNumber, String resourceName, int resource) throws HanseException {
        if (this.currentPhase != GamePhase.TRADE_AND_DEPART) {
            throw new HanseException(Error.ERROR_NOT_TRADE_TIME.toString());
        }
        Player player = this.getPlayerByNumber(playerNumber);
        if (player.getRoute() != null) {
            throw new HanseException(Error.ERROR_PLAYER_ALREADY_DEPARTED.toString());
        }
        if (this.pirate.getIsland() == player.getIsland()) {
            throw new HanseException(Error.ERROR_PIRATE_IS_ON_ISLAND.toString());
        }
        if (!player.hasResource(resourceName)) {
            throw new HanseException(Error.ERROR_NO_SUCH_RESOURCE.toString());
        }
        if (player.getIsland() == this.currentBoard.getIslandByNumber(resource)) {
            throw new HanseException(Error.ERROR_CANT_SELL_ON_SAME_ISLAND.toString());
        }
        player.removeResource(resourceName);
        int price = getRoute(player.getIsland().getNumber(), resource).getPirateMarker() + FIVE;
        player.setGold(player.getGold() + price);
        return (player.getName() + Output.SELLS.toString() + resourceName + Output.FOR.toString() + price
                + Output.GOLD.toString());
    }

    /** Ermöglicht einem Spieler den Eintrag in das Logbuch.
     * @param playerNumber Name des Spielers.
     * @param island Zielinsel.
     * @param flag Flagge unter der er reisen möchte.
     * @return Ausgabe.
     * @throws HanseException eigene Exception des Spiels. */
    public ArrayList<String> depart(int playerNumber, int island, String flag) throws HanseException {
        if (this.currentPhase != GamePhase.TRADE_AND_DEPART) {
            throw new HanseException(Error.ERROR_THE_PLAYERS_CAN_NOT_DEPART.toString());
        }
        ArrayList<String> message = new ArrayList<>();
        Player player = this.getPlayerByNumber(playerNumber);
        if (player.getRoute() != null) {
            throw new HanseException(Error.ERROR_PLAYER_ALREADY_DEPARTED.toString());
        }
        if (this.currentBoard.getIslandByNumber(island) == player.getIsland()) {
            throw new HanseException(Error.ERROR_PLAYER_CANT_SAIL_TO_SAME_ISLAND.toString());
        }
        player.setRoute(getRoute(player.getIsland().getNumber(), island));
        player.setFlag(Flag.valueOf(flag.toUpperCase()));
        player.getRoute().addPlayerToRoute(player);
        message.add(player.getName() + Output.DEPARTED_FROM.toString() + player.getIsland().getName());
        if (this.routesSet()) {
            this.currentPhase = GamePhase.PIRATE_MOVE;
            message.add(Output.USE_MOVE_PIRATE.toString());
        } return message;
    }

    /** Gibt zurück, ob alle Spieler in Häfen eingefahren sind.
     * @return wahr, wenn sich kein Spieler mehr auf einer Route befindet. */
    private boolean routesSet() {
        for (Player player : this.players) {
            if (player.getRoute() == null) {
                return false;
            }
        } return true;
    }

    /** Ermöglicht es den Piraten zu bewegen.
     * @param island Zielinsel.
     * @return Ausgabe.
     * @throws HanseException eigene Exception des Spiels. */
    public ExecutionResult movePirate(int island) throws HanseException {
        if (this.currentPhase == GamePhase.PIRATE_MOVE) {
            if (this.currentBoard.hasIsland(island) && this.pirate.getIsland()
                    != this.currentBoard.getIslandByNumber(island)) {
                this.pirate.setRoute(getRoute(pirate.getIsland().getNumber(), island));
                this.pirate.getRoute().addPlayerToRoute(pirate);
                this.currentPhase = GamePhase.EVALUATION;
                this.currentPlayer = null;
                this.secondPlayer = null;
                return validateRound();
            } throw new HanseException(Error.ERROR_THIS_ISLAND_DOES_NOT_EXIST.toString());
        } throw new HanseException(Error.ERROR_NOT_PIRATES_MOVE.toString());
    }

    /** Wertet die Reisen der Spieler aus.
     * @return Die Ausgabe zum Validieren der Runde. */
    private ExecutionResult validateRound() {
        ArrayList<String> print = new ArrayList<>();
        if (this.secondPlayer != null) {
            print.addAll(travelEncounter());
            if (this.secondPlayer != null) {
                return new ExecutionResult(print, ResultState.SUCCESS_CONTINUE);
            }
        } 
        
        int startIndex;
        if (this.currentPlayer == null) {
            startIndex = 0;
            this.currentPlayer = this.players.get(startIndex);
        } else {
            startIndex = this.players.indexOf(this.currentPlayer) + 1;
        }

        for (int i = startIndex; i < this.players.size(); i++) {
            Route route = this.players.get(i).getRoute();
            this.currentPlayer = this.players.get(i);
            if (route.sizeOfPlayerList() < TWO || route.sameFlag()) {
                return new ExecutionResult(travelAlone(), ResultState.SUCCESS_CONTINUE);
            } else {
                print.addAll(travelEncounter());
                if (this.secondPlayer != null) {
                    return new ExecutionResult(print, ResultState.SUCCESS_CONTINUE);
                }
            }
        }
        this.currentPlayer = this.pirate;
        if (this.pirate.getRoute().sizeOfPlayerList() < TWO  || this.pirate.getRoute().sameFlag()) {
            this.pirate.getRoute().setPirateMarker(this.pirate.getRoute().getPirateMarker() + 1);
            print.add(getCorrectFromToOrderString() + this.pirate.getName() + Output.BRACKETS_OPEN.toString()
                + this.currentPlayer.getFlag().name().toLowerCase() + Output.BRACKETS_CLOSE.toString());
        } else {
            print.addAll(travelEncounter());
            this.pirate.getRoute().setPirateMarker(this.pirate.getRoute().getPirateMarker() + 1);
        }
        
        if ((this.maxRound) == this.round) {
            print.add(endGame());
            return new ExecutionResult(print, ResultState.SUCCESS_QUIT);
        } print.add(endRound());
        return new ExecutionResult(print, ResultState.SUCCESS_CONTINUE);
    }
    
    /** Initialisiert die Spielphase, in der ein Spieler allein reist.
     * @return Ausgabe. */
    private ArrayList<String> travelAlone() {
        ArrayList<String> print = new ArrayList<>();
        this.currentPhase = GamePhase.WAITING_FOR_DICE;
        this.secondPlayer = null;
        print.add(this.getCorrectFromToOrderString() + this.currentPlayer.getName() 
            + Output.BRACKETS_OPEN.toString() + this.currentPlayer.getFlag().name().toLowerCase()
                + Output.BRACKETS_CLOSE.toString());
        print.add(this.currentPlayer.getName() + Output.MUST_ROLL_TO_RESOLVE_AN_ENCOUNTER.toString());
        return print;
    }

    /** Initialisiert das Auswerten beim Aufeinandertreffen von Spielern.
     * @return Ausgabe. */
    private ArrayList<String> travelEncounter() {
        ArrayList<String> print = new ArrayList<>();
        Route route = this.currentPlayer.getRoute();
        int startIndex;
        if (this.secondPlayer == null) {
            startIndex = 0;
        } else {
            startIndex = route.getIndexOfPlayer(this.secondPlayer) + 1;
        }
        for (int i = startIndex; i < route.sizeOfPlayerList(); i++) {
            Player secondPlayer = route.getPlayerFromList(i);
            if (this.currentPlayer.getFlag() == Flag.MERCHANT && secondPlayer.getFlag() == Flag.PATROL) {
                this.secondPlayer = secondPlayer;
                print.addAll(travelGamble(secondPlayer));
                return print;
            }
            if (this.currentPlayer.getFlag() == Flag.PIRATE && secondPlayer.getFlag() == Flag.MERCHANT) {
                if (secondPlayer.getResources() > 0 
                    && (this.currentPlayer.getShipState().getStorage() - this.currentPlayer.getResources()) > 0) {
                    Resource resource = secondPlayer.getLastResource();
                    secondPlayer.removeLastResource();
                    this.currentPlayer.addResource(resource);
                }
                print.add(this.getCorrectFromToOrderString() + this.currentPlayer.getName()
                        + Output.BRACKETS_OPEN.toString() + this.currentPlayer.getFlag().toString()
                        + Output.VS.toString() + secondPlayer.getName() + Output.BRACKETS_OPEN.toString()
                        + secondPlayer.getFlag().toString() + Output.BRACKETS_CLOSE.toString());
            }
            if (this.currentPlayer.getFlag() == Flag.PATROL && secondPlayer.getFlag() == Flag.PIRATE) {
                if (this.currentPlayer.getCannons() >= secondPlayer.getCannons()) {
                    int amount = FIVE;
                    if (secondPlayer != this.pirate) {
                        if (secondPlayer.getGold() < amount) {
                            amount = secondPlayer.getGold();
                        }
                        secondPlayer.setGold(secondPlayer.getGold() - amount);
                    }
                    this.currentPlayer.setGold(this.currentPlayer.getGold() + amount);
                }
                print.add(this.getCorrectFromToOrderString() + this.currentPlayer.getName()
                        + Output.BRACKETS_OPEN.toString() + this.currentPlayer.getFlag().toString()
                        + Output.VS.toString() + secondPlayer.getName() + Output.BRACKETS_OPEN.toString()
                        + secondPlayer.getFlag().toString() + Output.BRACKETS_CLOSE.toString());
            }
        } this.secondPlayer = null;
        return print;
    }

    

    /** Initialisiert die Spielphase, in der Spieler gegeneinander antreten.
     * @param secondPlayer der gegnerische Spieler.
     * @return Ausgabe. */
    private ArrayList<String> travelGamble(Player secondPlayer) {
        ArrayList<String> print = new ArrayList<>();
        this.currentPhase = GamePhase.WAITING_FOR_DICE;
        this.secondPlayer = secondPlayer;
        print.add(this.getCorrectFromToOrderString() + this.currentPlayer.getName() 
            + Output.BRACKETS_OPEN.toString() + this.currentPlayer.getFlag().name().toLowerCase()
                + Output.VS.toString() + this.secondPlayer.getName() + Output.BRACKETS_OPEN.toString()
                + this.secondPlayer.getFlag().name().toLowerCase() + Output.BRACKETS_CLOSE.toString());
        if (this.currentPlayer.getFlag() == Flag.PATROL) {
            print.add(this.currentPlayer.getName() + Output.MUST_ROLL_TO_RESOLVE_AN_ENCOUNTER.toString());
        } else {
            print.add(secondPlayer.getName() + Output.MUST_ROLL_TO_RESOLVE_AN_ENCOUNTER.toString());
        } return print;
    }

    /** Initialisiert das Warten auf den ROLL Befehl.
     * @param dice Würfel.
     * @return Ausgabe.
     * @throws HanseException die eigene Exception des Spiels. */
    public ExecutionResult rollExecution(int dice) throws HanseException {
        if (this.currentPhase == GamePhase.WAITING_FOR_DICE) {
            this.currentPhase = GamePhase.EVALUATION;
            if (this.secondPlayer == null) {
                this.rollAlone(dice);
            } else {
                this.rollEncounter(dice);
            } return validateRound();
        } throw new HanseException(Error.ERROR_DID_NOT_EXPECT_ROLL_COMMAND.toString());
    }

    /** Ermittelt das Ergebnis, wenn ein Spieler allein gewürfelt hat.
     * @param incorrectDice Würfel. */
    private void rollAlone(int incorrectDice) {
        int dice = incorrectDice + this.currentPlayer.getCannons();
        if (this.currentPlayer.getFlag() == Flag.MERCHANT) {
            if (dice <= this.currentPlayer.getRoute().getPirateMarker()) {
                this.currentPlayer.removeLastResource();
            }
        } else if (this.currentPlayer.getFlag() == Flag.PIRATE) {
            if (dice <= this.currentPlayer.getRoute().getPirateMarker() && this.currentPlayer.getResources() > 0) {
                this.currentPlayer.removeLastResource();
            }
            this.currentPlayer.getRoute().setPirateMarker(this.currentPlayer.getRoute().getPirateMarker() + 1);
        } else if (this.currentPlayer.getFlag() == Flag.PATROL) {
            if (this.currentPlayer.getRoute().getPirateMarker() > 1
                    && dice > this.currentPlayer.getRoute().getPirateMarker()) {
                this.currentPlayer.setGold(this.currentPlayer.getGold()
                        + this.currentPlayer.getRoute().getPirateMarker());
                this.currentPlayer.getRoute().setPirateMarker(this.currentPlayer.getRoute().getPirateMarker() - 1);
            } else if (dice <= this.currentPlayer.getRoute().getPirateMarker() 
                && this.currentPlayer.getResources() > 0) {
                this.currentPlayer.removeLastResource();
            }
        }
    }

    /** Wertet den Würfelwurf aus.
     * @param diceInput Würfel. */
    private void rollEncounter(int diceInput) {
        int dice = diceInput;
        if (this.secondPlayer.getGold() < dice) {
            dice = this.secondPlayer.getGold();
        } this.currentPlayer.setGold(this.currentPlayer.getGold() + dice);
        this.secondPlayer.setGold(this.secondPlayer.getGold() - dice);
    }

    /** Gibt die richtige Ausgabe für die Auswertung zurück.
     * @return Ausgabe. */
    private String getCorrectFromToOrderString() {
        if (this.currentPlayer.getIsland() == this.currentPlayer.getRoute().getStartIsland()) {
            return (this.currentPlayer.getRoute().getStartIsland().getName() + Output.ARROW.toString()
                + this.currentPlayer.getRoute().getEndIsland().getName() + Output.COLON.toString());
        } else {
            return (this.currentPlayer.getRoute().getEndIsland().getName() + Output.ARROW.toString()
                + this.currentPlayer.getRoute().getStartIsland().getName() + Output.COLON.toString());
        }
    }

    /** Methode um die Runde endgültig zu beenden und die Spieler in die neuen Häfen einfahren zu lassen.
     * @return Ausgabe. */
    private String endRound() {
        for (Player player : this.players) {
            Route route = player.getRoute();
            player.getIsland().deletePlayer(player);
            
            if (player.getIsland() == route.getStartIsland()) {
                player.setIsland(route.getEndIsland());
            } else {
                player.setIsland(route.getStartIsland());
            }
            player.getIsland().insertPlayer(player);
            route.deletePlayerFromRoute(player);
            player.setRoute(null);
            player.setFlag(null);
        }
        this.pirate.getIsland().deletePlayer(this.pirate);
        if (this.pirate.getIsland() == this.pirate.getRoute().getStartIsland()) {
            this.pirate.setIsland(this.pirate.getRoute().getEndIsland());
        } else {
            this.pirate.setIsland(this.pirate.getRoute().getStartIsland());
        }
        this.pirate.getIsland().insertPlayer(this.pirate);
        this.pirate.getRoute().deletePlayerFromRoute(this.pirate);
        this.pirate.setRoute(null);
        this.currentPhase = GamePhase.TRADE_AND_DEPART;
        this.currentPlayer = null;
        this.secondPlayer = null;
        this.round = round + 1;
        return (Output.TRADE_TIME.toString());
    }

    /** Beendet das Spiel.
     * @return Ausgabe. */
    private String endGame() {
        int maxGold = this.players.get(0).getGold();
        for (Player player : this.players) {
            if (player.getGold() > maxGold) {
                maxGold = player.getGold();
            }
        }
        StringBuilder bestPlayers = new StringBuilder();
        for (Player player : this.players) {
            if (player.getGold() == maxGold) {
                bestPlayers.append(Output.COMMA.toString()).append(player.getName());
            }
        } return (Output.THE_BEST_PLAYERS_WERE.toString() + bestPlayers.toString().replaceFirst(Output.COMMA.toString()
                , Output.EMPTY.toString()));
    }
}