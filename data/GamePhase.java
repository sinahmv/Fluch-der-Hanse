package data;

/**
 * Enum-Klasse, welche die Spielstadien modelliert.
 * @author ufufe
 * @version 1.0
 */
public enum GamePhase {
    /**
     * Phase, in der die Spieler handeln können und ihre Route planen.
     */
    TRADE_AND_DEPART,
    /**
     * Phase, in der sich der Pirat auf dem Spielbrett bewegt.
     */
    PIRATE_MOVE,
    /**
     * Phase, in der die Reise ausgewertet wird.
     */
    EVALUATION,
    /**
     * Phase, in der das Spiel auf den Wurf eines Würfels wartet.
     */
    WAITING_FOR_DICE;
}
