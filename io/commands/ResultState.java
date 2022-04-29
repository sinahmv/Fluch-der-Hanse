package io.commands;

/**
 * Enum Klasse mit allen möglichen Ergebnissen, welche das Programm nach der Ausführung eines Befehls haben kann.
 * @author ufufe
 * @version 1.0
 */
public enum ResultState {
    /**
     * Erfolgreiche Ausführung, das Programm arbeitet weiter.
     */
    SUCCESS_CONTINUE,
    /**
     * Erfolgreiche Ausführung, das Programm wird beendet.
     */
    SUCCESS_QUIT,
    /**
     * Keine erfolgreiche Ausführung, das Programm arbeitet weiter.
     */
    FAILURE_CONTINUE,
    /**
     * Keine erfolgreiche Ausführung, das Programm wird beendet.
     */
    FAILURE_QUIT
}
