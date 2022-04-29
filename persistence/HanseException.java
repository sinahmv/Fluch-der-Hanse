package persistence;

/**
 * Modelliert eine eigene Exception für das Programm.
 * @author ufufe
 * @version 1.0
 */
public class HanseException extends Throwable {

    /**
     * Gibt das Exception-Objekt zurück.
     * @param message Ausgabe.
     */
    public HanseException(String message) {
        super(message);
    }

}
