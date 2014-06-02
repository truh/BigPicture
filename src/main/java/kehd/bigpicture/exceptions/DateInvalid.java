package kehd.bigpicture.exceptions;

public class DateInvalid extends ParameterException {
    private static final String message = "Date invalid.";

    /**
     * Exception mit erklaerender Nachricht sowie
     * einer id um die erkennung der Exception auf Client-Seite
     * zu vereinfachen.
     */
    public DateInvalid() {
        super(message, DATE_INVALID_ERROR_ID);
    }

    /**
     * Konstruktor wenn die Exception durch eine andere
     * Exeption ausgeloest wurde.
     *
     * @param cause   ausloesende Exeception
     */
    public DateInvalid(Throwable cause) {
        super(message, DATE_INVALID_ERROR_ID, cause);
    }
}
