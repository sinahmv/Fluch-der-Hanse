package data;

/**
 * Enum Klasse mit allen möglichen Flaggen unter denen die Spieler segeln können.
 * @author ufufe
 * @version 1.0
 */
public enum Flag {
    /**
     * Flagge des Handelnden.
     */
    MERCHANT ("merchant"),
    /**
     * Flagge des Seeraubenden.
     */
    PIRATE ("pirate"),
    /**
     * Flagge der Patrouille.
     */
    PATROL ("patrol");

    private final String name;

    /**
     * Erstellt die Flaggen unter denen gesegelt werden kann.
     * @param name Name.
     */
    Flag(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }

}
