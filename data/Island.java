package data;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Modelliert eine Insel.
 * @author ufufe
 * @version 1.0
 */
public class Island implements Comparable<Island> {

    private final String name;
    private ArrayList<Player> players;

    /**
     * Erzeugt eine Insel mit ihrem Namen.
     * @param name Name.
     */
    public Island(String name) {
        this.name = name;
        this.players = new ArrayList<>();
    }

    /**
     * Gibt den Namen einer Insel zurück.
     * @return Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt die Nummer einer Insel zurück.
     * @return Nummer.
     */
    public int getNumber() {
        return Integer.parseInt(name.substring(1));
    }

    /**
     * Fügt einen Spieler zur Spielerliste hinzu.
     * @param player Spieler
     */
    public void insertPlayer(Player player) {
        this.players.add(player);
    }

    /**
     * Löscht einen Spieler von der Spielerliste.
     * @param player Spieler.
     */
    public void deletePlayer(Player player) {
        this.players.remove(player);
    }

    @Override
    public int compareTo(Island island) {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        if (Objects.isNull(object)) {
            return false;
        }
        if (!(object instanceof Island)) {
            return false;
        }
        Island island = (Island) object;
        return (Objects.equals(this.name, island.getName()));
    }

}
