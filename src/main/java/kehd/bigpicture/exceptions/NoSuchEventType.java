package kehd.bigpicture.exceptions;

public class NoSuchEventType extends ParameterException {
    private static final String message = "";

    /**
     * Exception mit erklaerender Nachricht sowie
     * einer id um die erkennung der Exception auf Client-Seite
     * zu vereinfachen.
     */
    public NoSuchEventType() {
        super(message, NO_SUCH_EVENT_TYPE_ERROR_ID);
    }

    /**
     * Konstruktor wenn die Exception durch eine andere
     * Exeption ausgeloest wurde.
     *
     * @param cause   ausloesende Exeception
     */
    public NoSuchEventType(Throwable cause) {
        super(message, NO_SUCH_EVENT_TYPE_ERROR_ID, cause);
    }
}
