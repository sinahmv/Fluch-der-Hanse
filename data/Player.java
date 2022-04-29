package data;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Modelliert einen Spieler des Spiels Fluch der Hanse.
 * @author ufufe
 * @version 1.0
 */
public class Player implements Comparable<Player> {

    private static final String COLON = ": ";
    private static final String DASH = "-";
    private static final String KOMMA = ",";
    private static final String ISLAND = "Island: ";
    private static final String NEW_LINE = "\n";
    private static final String GOLD = "Gold: ";
    private static final String CANNONS = "Cannons: ";
    private static final String SHIP = "Ship: ";
    private static final String STORAGE = "Storage: ";
    private static final String TAP = " ";
    private static final String EMPTY = "";
    private static final String LOG = "Log: ";
    private final String name;
    private ShipEnum shipState;
    private int gold;
    private int cannons;
    private Island island;
    private Route route;
    private Flag flag;
    private ArrayList<Resource> resources;

    /**
     * Gibt das Spieler-Objekt zurück.
     * @param name Der Name des Spielers.
     * @param gold Der Goldvorrat des Spielers.
     * @param shipState Schiff.
     * @param island Die Insel von dem der Spieler aus das Spiel startet.
     * @param cannons Kanonen.
     */
    public Player(String name, int gold, ShipEnum shipState, Island island, int cannons) {
        this.name = name;
        this.gold = gold;
        this.shipState = shipState;
        this.island = island;
        this.cannons = cannons;
        this.resources = new ArrayList<>();
    }

    /**
     * Gibt die Anzahl an Kanonen zurück.
     * @return Kanonen.
     */
    public int getCannons() {
        return cannons;
    }

    /**
     * Legt die Anzahl an Kanonen fest.
     * @param cannons Kanonen.
     */
    public void setCannons(int cannons) {
        this.cannons = cannons;
    }

    /**
     * Gibt den Namen des Spielers zurück.
     * @return Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt das Schiff des Spielers zurück.
     * @return Das Schiff.
     */
    public ShipEnum getShipState() {
        return shipState;
    }

    /**
     * Legt für den Spieler ein neues Schiff fest.
     * @param newShipState Das neue Schiff.
     */
    public void setShipState(ShipEnum newShipState) {
        this.shipState = newShipState;
    }

    /**
     * Gibt den Goldvorrat des Spielers zurück.
     * @return Der Goldvorrat.
     */
    public int getGold() {
        return gold;
    }

    /**
     * Legt eine neue Menge Gold für den Spieler fest.
     * @param gold Die Menge von Gold.
     */
    public void setGold(int gold) {
        this.gold = gold;
    }

    /**
     * Gibt die Insel aus, auf der sich der Spieler gerade befindet.
     * @return Die Insel.
     */
    public Island getIsland() {
        return island;
    }

    /**
     * Legt die Insel fest, auf der sich der Spieler gerade befindet.
     * @param island Die Insel.
     */
    public void setIsland(Island island) {
        this.island = island;
    }

    /**
     * Gibt die Route zurück, auf der sich der Spieler gerade befindet.
     * @return Die Route.
     */
    public Route getRoute() {
        return route;
    }

    /**
     * Legt die Route fest, auf der sich der Spieler als Nächstes befinden wird.
     * @param route Die Route.
     */
    public void setRoute(Route route) {
        this.route = route;

    }

    /**
     * Gibt die Flagge aus, unter der dieser Spieler gerade segelt.
     * @return Die Flagge.
     */
    public Flag getFlag() {
        return flag;
    }

    /**
     * Legt die Flagge fest, unter der dieser Spieler als Nächstes segelt.
     * @param flag Die Flagge.
     */
    public void setFlag(Flag flag) {
        this.flag = flag;
    }

    /**
     * Gibt die letzte hinzugefügte Resource des Spielers zurück.
     * @return Die Resource.
     */
    public Resource getLastResource() {
        return this.resources.get(resources.size() - 1);
    }

    /**
     * Gibt die Menge an bereits vorhandenen Ressourcen zurück.
     * @return Die Anzahl.
     */
    public int getResources() {
        return this.resources.size();
    }

    /**
     * Fügt eine Resource hinzu.
     * @param ressource Resource
     */
    public void addResource(Resource ressource) {
        this.resources.add(ressource);
    }

    /**
     * Löscht eine Resource.
     * @param ressourceName Resource.
     */
    public void removeResource(String ressourceName) {
        if (!this.resources.isEmpty()) {
            this.resources.removeIf(resource -> resource.getName().equals(ressourceName));
        }
    }

    /**
     * Gibt zurück, ob es eine Resource mit diesem Namen gibt.
     * @param resourceName Resource.
     * @return Ausgabe.
     */
    public boolean hasResource(String resourceName) {
        for (Resource resource : resources) {
            if (resource.getName().equals(resourceName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Löscht die letzte Resource des Spielers.
     */
    public void removeLastResource() {
        this.resources.remove(resources.size() - 1);
    }

    /**
     * Gibt die Daten des Spielers zurück für den showBoard Befehl.
     * @return Ausgabe der Daten in String-Form.
     */
    public String toStringBoard() {
        return getName() + COLON + getIsland().getName();
    }

    /**
     * Gibt die Ausgabe für die Validation zurück.
     * @return Der String für die Ausgabe.
     */
    public String toStringValidate() {
        return getIsland().getName() + DASH + getRoute().getEndIsland().getName() + DASH + getFlag().toString();
    }

    /**
     * Gibt die Ausgabe für die Ressourcen eines Spielers zurück.
     * @return Ausgabe
     */
    private String toStringResources() {
        StringBuilder output = new StringBuilder();
        for (Resource resource : this.resources) {
            output.append(KOMMA + resource.getName());
        }
        return output.toString().replaceFirst(KOMMA, EMPTY);
    }

    @Override
    public String toString() {
        if (this.route == null && this.resources.isEmpty()) {
            return ISLAND + this.island.getName() + NEW_LINE + GOLD + this.gold + NEW_LINE + SHIP
                    + this.shipState.getName() + NEW_LINE + CANNONS
                    + this.cannons;
        } else if (this.route == null) {
            return ISLAND + this.island.getName() + NEW_LINE + GOLD + this.gold + NEW_LINE + SHIP
                    + this.shipState.getName() + NEW_LINE + CANNONS
                    + this.cannons + NEW_LINE + STORAGE + this.toStringResources();
        } else if (this.resources.isEmpty()) {
            if (this.island == this.route.getStartIsland()) {
                return ISLAND + this.island.getName() + NEW_LINE + GOLD + this.gold + NEW_LINE + SHIP
                    + this.shipState.getName() + NEW_LINE + CANNONS
                    + this.cannons + NEW_LINE + LOG
                    + this.route.getEndIsland().getName() + TAP + this.flag.name().toLowerCase();
            } else {
                return ISLAND + this.island.getName() + NEW_LINE + GOLD + this.gold + NEW_LINE + SHIP
                    + this.shipState.getName() + NEW_LINE + CANNONS
                    + this.cannons + NEW_LINE + LOG
                    + this.route.getStartIsland().getName() + TAP + this.flag.name().toLowerCase();
            }
        }
        else {
            if (this.island == this.route.getStartIsland()) {
                return ISLAND + this.island.getName() + NEW_LINE + GOLD + this.gold + NEW_LINE + SHIP
                    + this.shipState.getName() + NEW_LINE + CANNONS
                    + this.cannons + NEW_LINE + STORAGE + this.toStringResources() + NEW_LINE + LOG
                    + this.route.getEndIsland().getName() + TAP + this.flag.name().toLowerCase();
            } else {
                return ISLAND + this.island.getName() + NEW_LINE + GOLD + this.gold + NEW_LINE + SHIP
                    + this.shipState.getName() + NEW_LINE + CANNONS
                    + this.cannons + NEW_LINE + STORAGE + this.toStringResources() + NEW_LINE + LOG
                    + this.route.getStartIsland().getName() + TAP + this.flag.name().toLowerCase();
            }
            
        }
    }

    @Override
    public int compareTo(Player player) {
        if (Integer.parseInt(this.getName().substring(1)) < Integer.parseInt(player.getName().substring(1))) {
            return -1;
        } else if (Integer.parseInt(this.getName().substring(1)) > Integer.parseInt(player.getName().substring(1))) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public boolean equals(Object object) {
        if (Objects.isNull(object)) {
            return false;
        }
        if (!(object instanceof Player)) {
            return false;
        }
        Player player = (Player) object;
        return (Objects.equals(this.name, player.getName()));
    }
}
