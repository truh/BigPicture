package kehd.bigpicture.exceptions;

public class UserDoesNotExist extends NoSuchElement {
    private static final String elementType = "User";

    /**
     * Exception mit erklaerender Nachricht sowie
     * einer id um die erkennung der Exception auf Client-Seite
     * zu vereinfachen.
     */
    public UserDoesNotExist() {
        super(elementType);
    }

    /**
     * Konstruktor wenn die Exception durch eine andere
     * Exeption ausgeloest wurde.
     *
     * @param cause        ausloesende Exeception
     */
    public UserDoesNotExist(Throwable cause) {
        super(elementType, cause);
    }
}
