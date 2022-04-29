package data;

/**
 * Erstellt ein Enum von allen möglichen Schiffstypen.
 * @author ufufe
 * @version 1.0
 */
public enum ShipEnum {

    /**
     * Kogge.
     */
    COG (2, 2, 20, "cog"),
    /**
     * Karacke.
     */
    CARRACK (4, 1, 28, "carrack"),
    /**
     * Karavelle.
     */
    CARAVEL (3, 3, 30, "caravel"),
    /**
     * Fregatte.
     */
    FRIGATE (2, 4, 28, "frigate");

    private final int storage;
    private final int cannons;
    private final int price;
    private final String name;
    private final String outputShip = "Ship: ";

    /**
     * Erstellt die verfügbaren Schiffe.
     * @param storage Lagerplatz.
     * @param cannons Kanonen.
     * @param price Preis.
     * @param name Name.
     */
    ShipEnum(int storage, int cannons, int price, String name) {
        this.storage = storage;
        this.cannons = cannons;
        this.price = price;
        this.name = name;
    }

    /**
     * Gibt den Lagerplatz zurück.
     * @return Lagerplatz.
     */
    public int getStorage() {
        return storage;
    }

    /**
     * Gibt die Kanonenanzahl zurück.
     * @return Kanonenanzahl.
     */
    public int getCannons() {
        return cannons;
    }

    /**
     * Gibt den Preis zurück.
     * @return Preis.
     */
    public int getPrice() {
        return price;
    }

    /**
     * Gibt den Namen zurück.
     * @return Name.
     */
    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return outputShip + name;
    }

}
