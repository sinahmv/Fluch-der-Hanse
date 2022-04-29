package io.commands;

import persistence.HanseGame;

import java.util.InputMismatchException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Modelliert einen Befehl für das Programm.
 * @author ufufe
 * @version 1.0
 */
public abstract class Command {

    /** Ausgabe, wenn die Anzahl an Argumenten falsch ist. */
    protected static final String ERROR_WRONG_NUMBER_OF_ARGUMENTS = "wrong numbers of arguments.";
    /** Ausgabe, wenn die Anzahl an Parametern falsch ist. */
    protected static final String ERROR_WRONG_PARAMETER_COUNT = "the parameter count was unexpected.";
    /** Ausgabe, wenn die Eingabe des Spielers falsch ist. */
    protected static final String ERROR_PLAYER_MALFORMED = "player parameter malformed";
    /** Ausgabe, wenn die Eingabe der Insel falsch ist. */
    protected static final String ERROR_ISLAND_MALFORMED = "island parameter malformed";
    /** Ausgabe, wenn die Eingabe der Flagge falsch ist. */
    protected static final String ERROR_FLAG_MALFORMED = "flag parameter malformed";
    /** Ausgabe, wenn die Eingabe der Flagge falsch ist. */
    protected static final String ERROR_RESOURCE_MALFORMED = "resource parameter malformed";
    /** Ausgabe bei fehlerhaftem Input. */
    protected static final String ERROR_NOT_AN_INTEGER = "given numeric parameter is not an integer.";
    /** Ausgabe bei falscher Anzahl von Parametern im Input. */
    protected static final String ERROR_PARAMETERS_DO_NOT_MATCH = "in parameters.";
    /** Gibt an, an welchem Zeichen Strings des Inputs aufgeteilt werden sollen. */
    protected static final String DELIMITER_COMMAND_DEFAULT = " ";
    /** Regex für die Bezeichnung eines Players in der Eingabe */
    protected static final String REGEX_PLAYER = "^[p]{1}[1-9]{1}[0-9]*$";
    /** Regex für die Bezeichnung einer Island in der Eingabe */
    protected static final String REGEX_ISLAND = "^[v]{1}[1-9]{1}[0-9]*$";
    /** Regex für die Bezeichnung einer Resource in der Eingabe */
    protected static final String REGEX_RESOURCE = "^[r]{1}[1-9]{1}[0-9]*$";
    /** Regex für die Bezeichnung des pirate in der Eingabe */
    protected static final String REGEX_PIRATE = "pirate";
    /** Antwort, gefunden */
    private static final String RESPONSE_DETECTED_A = "detected a \"";
    /** Antwort, nicht gewünschtes */
    private static final String RESPONSE_UNEXPECTED = "unexpected \"";
    /** Antwort, Leerzeichen nach dem Kommando */
    private static final String RESPONSE_END = "\" at the end";
    /** Antwort, unerwünschtes Leerzeichen im Kommando */
    private static final String RESPONSE_STRING_POSITION = "\" at position ";
    /** int Null */
    private static final int ZERO = 0;
    /** Shift um Eins */
    private static final int SHIFT_ONE = 1;
    /** Shift um zwei */
    private static final int SHIFT_TWO = 2;

    private final String regex;
    private final HanseGame game;

    /**
     * Gibt den Befehl mit seinem Input-Regex und der zugehörigen Administration zurück.
     * @param regex Vorgabe, wie der Input semantisch aussehen sollte.
     * @param game Spiel.
     */
    protected Command(String regex, HanseGame game) {
        this.regex = regex;
        this.game = game;
    }

    /**
     * Schaut, ob der Input richtig ist.
     * @param input Der Input aus den Kommandozeilenargumenten.
     * @return wahr, falls der Input richtig ist.
     */
    public boolean matchesInput(String input) {
        Matcher matcher = Pattern.compile(regex).matcher(input);
        return matcher.find();
    }

    /**
     * Gibt das Spiel zurück.
     * @return Spiel.
     */
    public HanseGame getGame() {
        return game;
    }

    /**
     * Checkt die Eingabe durch Splitten.
     * @param stringToCheck Eingabe
     * @param splitter Zeichen, an dem gesplittet wird.
     * @throws InputMismatchException, wenn Eingabe nicht passt.
     */
    public void checkSplitter(String stringToCheck, String splitter) throws InputMismatchException {
        if (stringToCheck.substring(stringToCheck.length() - SHIFT_ONE).equals(splitter)) {
            throw new InputMismatchException(RESPONSE_DETECTED_A + splitter + RESPONSE_END);
        }

        for (int i = ZERO; i < stringToCheck.length() - SHIFT_ONE; i++) {
            if (stringToCheck.substring(i, i + SHIFT_ONE).equals(splitter)
                    && stringToCheck.substring(i + SHIFT_ONE, i + SHIFT_TWO).equals(splitter)) {
                throw new InputMismatchException(
                        RESPONSE_UNEXPECTED + splitter + RESPONSE_STRING_POSITION + (i + SHIFT_ONE));
            }
        }
    }

    /**
     * Abstrakte Methode zum Ausführen.
     * @param input Der Input aus den Kommandozeilenargumenten.
     * @return den Status des Programms.
     */
    public abstract ExecutionResult execute(String input);

}
