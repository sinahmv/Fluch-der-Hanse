package data;

/**
 * Enum-Klasse, welche alle erforderten Ausgaben der Hanse-Game-Klasse hält.
 * @author ufufe
 * @version 1.0
 */
public enum Output {

    /**
     * Ausgabe, wenn die Spieler traden können.
     */
    TRADE_TIME  ("The players can trade now!"),
    /**
     * Ausgabe, wenn ein Würfelwurf erfordert wird.
     */
    MUST_ROLL_TO_RESOLVE_AN_ENCOUNTER (" must ROLL to resolve an encounter:"),
    /**
     * Aufforderung den Piraten zu bewegen.
     */
    USE_MOVE_PIRATE ("Use MOVE PIRATE <Island> now to move the pirate!"),
    /**
     * Ausgabe, wenn etwas verkauft wird.
     */
    SELLS (" sells "),
    /**
     * Ausgabe, um die Rundenzahl anzugeben.
     */
    ROUND ("Round: "),
    /**
     * Ausgabe, um die Spieler auszugeben.
     */
    PLAYERS ("Players:"),
    /**
     * Ausgabe, wenn ein Slash erforderlich ist.
     */
    SLASH ("/"),
    /**
     * Ausgabe, wenn ein Doppelpunkt erforderlich ist.
     */
    COLON (": "),
    /**
     * Ausgabe, wenn ein Komma erforderlich ist.
     */
    COMMA (", "),
    /**
     * Ausgabe, wenn ein leerer String erforderlich ist.
     */
    EMPTY (""),
    /**
     * Ausgabe, wenn die Auflistung der Routen beginnt.
     */
    ROUTES ("Routes:"),
    /**
     * Ausgabe, wenn etwas gekauft wird.
     */
    BUYS (" buys a "),
    /**
     * Ausgabe, wenn 5 Gold benutzt wurden.
     */
    FOR_GOLD (" for 5 gold"),
    /**
     * Ausgabe, wenn eine Kanone gekauft wird.
     */
    FOR_GOLD_CANNON (" buys a cannon for 10 gold"),
    /**
     * Ausgabe, wenn ein "für" erforderlich ist.
     */
    FOR (" for "),
    /**
     * Ausgabe, wenn ein "Gold" erforderlich ist.
     */
    GOLD (" gold"),
    /**
     * Ausgabe, wenn ein Pfeil erforderlich ist.
     */
    ARROW ("->"),
    /**
     * Ausgabe, wenn eine Klammer auf erforderlich ist.
     */
    BRACKETS_OPEN ("("),
    /**
     * Ausgabe, wenn eine Klammer zu erforderlich ist.
     */
    BRACKETS_CLOSE (")"),
    /**
     * Ausgabe, wenn ein "vs" erforderlich ist.
     */
    VS (") vs. "),
    /**
     * Ausgabe, wenn eine Insel genannt wird.
     */
    ISLAND ("Island: "),
    /**
     * Ausgabe, wenn ein Spieler abgelegt hat.
     */
    DEPARTED_FROM (" departed from "),
    /**
     * Ausgabe, wenn das Spiel endet.
     */
    THE_BEST_PLAYERS_WERE ("The best players were: "),
    /**
     * Ausgabe, wenn ein "Cannon" erforderlich ist.
     */
    CANNON ("CANNON"),
    /**
     * Ausgabe, wenn ein "Resource" erforderlich ist.
     */
    RESOURCE ("RESOURCE");

    private final String name;

    /**
     * Erstellt die Fehlermeldungen und deren Ausgabe.
     * @param name Name.
     */
    Output(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
