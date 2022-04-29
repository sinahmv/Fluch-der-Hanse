package data;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Modelliert eine Route zwischen zwei Inseln.
 * @author ufufe
 * @version 1.0
 */
public class Route implements Comparable<Route> {

    private static final String DASH = "-";
    private static final int SIX = 6;
    private final Island startIsland;
    private final Island endIsland;
    private int pirateMarker;
    private boolean encounterHasHappened;
    private ArrayList<Player> players;

    /**
     * Gibt die Route mit ihrer Anfangsinsel, ihrer Endinsel und ihrem Seeraubendenmarker zurück.
     * @param startIsland Die Anfangsinsel.
     * @param endIsland Die Endinsel.
     * @param pirateMarker Anzahl der Seeraubendenmarker.
     */
    public Route(Island startIsland, Island endIsland, int pirateMarker) {
        this.startIsland = startIsland;
        this.endIsland = endIsland;
        this.pirateMarker = pirateMarker;
        this.encounterHasHappened = false;
        this.players = new ArrayList<>();
    }

    /**
     * Gibt die Anfangsinsel zurück.
     * @return Die Anfangsinsel.
     */
    public Island getStartIsland() {
        return startIsland;
    }

    /**
     * Gibt die Endinsel zurück.
     * @return Die Endinsel.
     */
    public Island getEndIsland() {
        return endIsland;
    }

    /**
     * Gibt den Seeraubendenmarker zurück.
     * @return Der Seeraubendenmarker.
     */
    public int getPirateMarker() {
        return pirateMarker;
    }

    /**
     * Legt einen neuen Seeraubendenmarker fest.
     * @param pirateMarker Neuer Seeraubendenmarker.
     */
    public void setPirateMarker(int pirateMarker) {
        if (pirateMarker <= SIX) {
            this.pirateMarker = pirateMarker;
        }
    }

    /**
     * Gibt die Anzahl der Spieler auf der Route zurück.
     * @return Anzahl.
     */
    public int sizeOfPlayerList() {
        return this.players.size();
    }

    /**
     * Fügt den Spieler zu der Liste von Spielern hinzu, welche sich gerade auf der Route befinden.
     * @param player Spieler.
     */
    public void addPlayerToRoute(Player player) {
        this.players.add(player);
    }

    /**
     * Löscht einen Spieler von einer Route, nachdem er validiert wurde.
     * @param player Spieler.
     */
    public void deletePlayerFromRoute(Player player) {
        this.players.remove(player);
    }

    /**
     * Gibt zurück, ob alle Spieler auf dieser Route unter derselben Flagge reisen.
     * @return wahr, wenn die Flagge gleich ist.
     */
    public boolean sameFlag() {
        for (int i = 0; i < this.players.size() - 1; i++) {
            if (!(this.players.get(i).getFlag() == this.players.get(i + 1).getFlag())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gibt den Spieler vom gegebenen Index zurück.
     * @param index Index.
     * @return Spieler.
     */
    public Player getPlayerFromList(int index) {
        return this.players.get(index);
    }

    /**
     * Gibt den Index von dem gegebenen Spieler zurück.
     * @param player Spieler.
     * @return Index.
     */
    public int getIndexOfPlayer(Player player) {
        return this.players.indexOf(player);
    }

    /**
     * Gibt zurück, ob bereits ein Aufeinandertreffen auf dieser Route stattgefunden hat.
     * @return wahr, wenn es ein Aufeinandertreffen gab.
     */
    public boolean hasEncounterHappened() {
        return encounterHasHappened;
    }

    /**
     * Legt fest, ob ein Aufeinandertreffen auf dieser Route stattgefunden hat.
     * @param encounterHasHappened bla
     */
    public void setEncounterHasHappened(boolean encounterHasHappened) {
        this.encounterHasHappened = encounterHasHappened;
    }

    @Override
    public String toString() {
        return startIsland.getName() + DASH + pirateMarker + DASH + endIsland.getName();
    }

    @Override
    public int compareTo(Route route) {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        if (Objects.isNull(object)) {
            return false;
        }
        if (!(object instanceof Route)) {
            return false;
        }
        Route route = (Route) object;
        return (this.pirateMarker == route.getPirateMarker());
    }

}
