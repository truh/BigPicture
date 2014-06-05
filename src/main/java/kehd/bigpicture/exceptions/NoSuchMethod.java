package kehd.bigpicture.exceptions;

public class NoSuchMethod extends ParameterException {
    private final static String message = "Requested method does not exist: ";
    private String methodName;
    /**
     * Exception mit erklaerender Nachricht sowie
     * einer id um die erkennung der Exception auf Client-Seite
     * zu vereinfachen.
     */
    public NoSuchMethod(String methodName) {
        super(message, NO_SUCH_METHOD_ERROR_ID);
    }

    /**
     * Konstruktor wenn die Exception durch eine andere
     * Exeption ausgeloest wurde.
     *
     * @param methodName
     * @param cause   ausloesende Exeception
     */
    public NoSuchMethod(String methodName, Throwable cause) {
        super(message, NO_SUCH_METHOD_ERROR_ID, cause);
    }

    @Override
    public String getMessage() {
        return super.getMessage() + methodName;
    }
}
