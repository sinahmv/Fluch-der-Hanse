package data;

import java.util.Objects;

/**
 * Modelliert eine Ressource.
 * @author ufufe
 * @version 1.0
 */
public class Resource implements Comparable<Resource> {

    private static final String RESOURCE = "r";
    private final String name;
    private final Island origin;

    /**
     * Erzeugt eine Ressource.
     * @param origin Herkunftsinsel.
     */
    public Resource(Island origin) {
        this.origin = origin;
        this.name = this.createRessourceName();
    }

    /**
     * Erstellt den Namen für die Ressource der Insel.
     * @return Name.
     */
    private String createRessourceName() {
        String number = this.origin.getName().substring(1);
        return RESOURCE + number;
    }

    /**
     * Gibt den Namen zurück.
     * @return Name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gibt die Herkunft zurück.
     * @return Herkunftsinsel.
     */
    public Island getOrigin() {
        return origin;
    }

    @Override
    public int compareTo(Resource ressource) {
        return 0;
    }

    @Override
    public boolean equals(Object object) {
        if (Objects.isNull(object)) {
            return false;
        }
        if (!(object instanceof Resource)) {
            return false;
        }
        Resource ressource = (Resource) object;
        return (Objects.equals(this.name, ressource.getName()));
    }
}
