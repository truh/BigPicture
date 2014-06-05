package kehd.bigpicture.exceptions;

/**
 *
 */
public class NotANumber extends ParameterException {
    private final static String message = "Not a number.";
    /**
     * Exception mit erklaerender Nachricht sowie
     * einer id um die erkennung der Exception auf Client-Seite
     * zu vereinfachen.
     */
    public NotANumber() {
        super(message, NOT_A_NUMBER_ERROR_ID);
    }

    /**
     * Konstruktor wenn die Exception durch eine andere
     * Exeption ausgeloest wurde.

     * @param cause   ausloesende Exeception
     */
    public NotANumber(Throwable cause) {
        super(message, NOT_A_NUMBER_ERROR_ID, cause);
    }
}
