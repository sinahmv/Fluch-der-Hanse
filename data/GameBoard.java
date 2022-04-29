package data;

import java.util.ArrayList;

/**
 * Erstellt ein Spielbrett.
 * @author ufufe
 * @version 1.0
 */
public class GameBoard {

    private final ArrayList<Island> islands;
    private final ArrayList<ArrayList<Route>> routes;

    /**
     * Erstellt das Spielbrett-Objekt.
     * @param islands Liste aller Inseln.
     * @param routes Liste aller Seerouten.
     */
    public GameBoard(ArrayList<Island> islands, ArrayList<ArrayList<Route>> routes) {
        this.islands = islands;
        this.routes = routes;
    }

    /**
     * Gibt die Route zurück, welche anhand von zwei gegebenen Inseln durch ihre Kennung ermittelt wird.
     * @param firstNode Startinsel.
     * @param secondNode Endinsel.
     * @return Route.
     */
    public Route getRouteByNodes(int firstNode, int secondNode) {
        int firstNodeCorrected = firstNode - 1;
        int secondNodeCorrected = secondNode - 1;
        if (firstNodeCorrected < secondNodeCorrected) {
            return routes.get(firstNodeCorrected).get(secondNodeCorrected - firstNodeCorrected - 1);
        } else {
            return routes.get(secondNodeCorrected).get(firstNodeCorrected - secondNodeCorrected - 1);
        }
    }

    /**
     * Gibt die Route zurück, welche anhand von zwei gegebenen Inseln ermittelt wird.
     * @param firstIsland Erste Insel.
     * @param secondIsland Zweite Insel.
     * @return Die Route zwischen den Inseln.
     */
    public Route getRouteByIsland(Island firstIsland, Island secondIsland) {
        int firstNode = firstIsland.getNumber();
        int secondNode = secondIsland.getNumber();
        if (firstNode < secondNode) {
            return routes.get(firstNode).get(secondNode - firstNode - 1);
        } else {
            return routes.get(secondNode).get(firstNode - secondNode - 1);
        }
    }

    /**
     * Gibt die Insel der gegebenen Nummer zurück.
     * @param number Nummer der Insel.
     * @return Die Insel.
     */
    public Island getIslandByNumber(int number) {
        return this.islands.get(number - 1);
    }

    /**
     * Überprüft, ob es die Insel mit dieser Kennung gibt.
     * @param unCorrectedNumber Kennung.
     * @return wahr, wenn es die Insel gibt.
     */
    public boolean hasIsland(int unCorrectedNumber) {
        int number = unCorrectedNumber - 1;
        if (number >= this.islands.size() || number < 0) {
            return false;
        }
        return true;
    }

    /**
     * Gibt die Routen für die Ausgabe zurück.
     * @return Die Ausgabe zum Auflisten der Routen.
     */
    public ArrayList<String> printRoutes() {
        ArrayList<String> printRoutes = new ArrayList<>();
        for (ArrayList<Route> routeList: this.routes) {
            for (Route route : routeList) {
                printRoutes.add(route.toString());
            }
        }
        return printRoutes;
    }

}
