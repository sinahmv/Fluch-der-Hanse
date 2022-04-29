package data;

/**
 * Klasse, welche alle Fehlermeldungen für die HanseGame Klasse hält.
 * @author ufufe
 * @version 1.0
 */
public enum Error {

    /**
     * Fehlermeldung, der Spieler darf nicht zu seiner derzeitigen Insel segeln.
     */
    ERROR_PLAYER_CANT_SAIL_TO_SAME_ISLAND ("player can't sail to the same island again."),
    /**
     * Fehlermeldung, der Spieler darf den Rohstoff nicht an die Insel verkaufen.
     */
    ERROR_CANT_SELL_ON_SAME_ISLAND ("player can't sell the resource of the island it came from."),
    /**
     * Fehlermeldung, der Spieler hat den Rohstoff nicht.
     */
    ERROR_NO_SUCH_RESOURCE ("player does not have that resource."),
    /**
     * Fehlermeldung, der Spieler befindet sich nicht mehr auf einer Insel.
     */
    ERROR_PLAYER_ALREADY_DEPARTED ("player already departed."),
    /**
     * Fehlermeldung, der Pirat befindet sich auf der Insel.
     */
    ERROR_PIRATE_IS_ON_ISLAND ("the pirate is on this island."),
    /**
     * Fehlermeldung, der Spieler ist nicht vorgesehen.
     */
    ERROR_THIS_IS_NOT_THE_EXPECTED_PLAYER ("this is not the expected player."),
    /**
     * Fehlermeldung, es wird kein Würfelwurf benötigt.
     */
    ERROR_DID_NOT_EXPECT_ROLL_COMMAND ("did not expect ROLL command"),
    /**
     * Fehlermeldung, die Spielphase lässt keinen Handel zu.
     */
    ERROR_NOT_TRADE_TIME ("there can't be any purchases or disposals right now."),
    /**
     * Fehlermeldung, der Pirat darf sich gerade nicht bewegen.
     */
    ERROR_NOT_PIRATES_MOVE ("it's not the pirates move"),
    /**
     * Fehlermeldung, der Spieler hat zu wenig Gold.
     */
    ERROR_NOT_ENOUGH_GOLD ("the player does not have enough gold."),
    /**
     * Fehlermeldung, der Spieler hat zu wenig Lagerplatz.
     */
    ERROR_NOT_ENOUGH_STORAGE ("theres not enough storage."),
    /**
     * Fehlermeldung, der Spieler hat bereits alle Kanonen, die er haben kann.
     */
    ERROR_CANNON_SPACE ("theres no more room for any more cannons."),
    /**
     * Fehlermeldung, den Spieler gibt es nicht.
     */
    ERROR_PLAYER_NOT_FOUND ("did not find player."),
    /**
     * Fehlermeldung, die Spielphase lässt gerade kein Ablegen der Spieler zu.
     */
    ERROR_THE_PLAYERS_CAN_NOT_DEPART ("the players can not depart at the moment."),
    /**
     * Fehlermeldung, die Insel existiert nicht auf dem Spielbrett.
     */
    ERROR_THIS_ISLAND_DOES_NOT_EXIST ("this island does not exist.");

    private final String name;

    /**
     * Erstellt die Fehlermeldungen und deren Ausgabe.
     * @param name Name.
     */
    Error(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
