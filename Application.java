import io.Session;

/**
 * Modelliert die Klasse mit der Main-Methode des Programms.
 * @author ufufe
 * @version 1.0
 */
public final class Application {

    /**
     * Leerer Utility-Konstruktor.
     */
    private Application() {
        //wird nicht aufgerufen
    }

    /**
     * Initialisiert eine Session und ruft die run Methode für die Session auf.
     * @param args Die Kommandozeilenargumente.
     */
    public static void main(String[] args) {
        Session session = new Session(args);
        session.run();
    }

}