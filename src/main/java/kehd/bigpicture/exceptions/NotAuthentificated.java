package kehd.bigpicture.exceptions;

public class NotAuthentificated extends ParameterException {
    private final static String message = "Not authentificated.";
    /**
     * Exception mit erklaerender Nachricht sowie
     * einer id um die erkennung der Exception auf Client-Seite
     * zu vereinfachen.
     */
    public NotAuthentificated() {
        super(message, NOT_AUTHENTIFICATED_ERROR_ID);
    }

    /**
     * Konstruktor wenn die Exception durch eine andere
     * Exeption ausgeloest wurde.
     *
     * @param cause   ausloesende Exeception
     */
    public NotAuthentificated(Throwable cause) {
        super(message, NOT_AUTHENTIFICATED_ERROR_ID, cause);
    }
}
