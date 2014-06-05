package kehd.bigpicture.exceptions;

public class DateHasToBeInFuture extends ParameterException {
    private static final String message = "Date has to be in future.";

    /**
     * Exception mit erklaerender Nachricht sowie
     * einer id um die erkennung der Exception auf Client-Seite
     * zu vereinfachen.
     */
    public DateHasToBeInFuture() {
        super(message, DATE_HAS_TO_BE_IN_FUTURE_ERROR_ID);
    }

    /**
     * Konstruktor wenn die Exception durch eine andere
     * Exeption ausgeloest wurde.
     *
     * @param cause   ausloesende Exeception
     */
    public DateHasToBeInFuture(Throwable cause) {
        super(message, DATE_HAS_TO_BE_IN_FUTURE_ERROR_ID, cause);
    }
}
