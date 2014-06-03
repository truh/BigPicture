package kehd.bigpicture.exceptions;

public class NotAuthorized extends ParameterException {
    private final static String message = "Not authorized to: ";

    private String task;

    /**
     * Exception mit erklaerender Nachricht sowie
     * einer id um die erkennung der Exception auf Client-Seite
     * zu vereinfachen.
     *
     * @param task
     */
    public NotAuthorized(String task) {
        super(message, NOT_AUTHORIZED_ERROR_ID);
        this.task = task;
    }

    /**
     * Konstruktor wenn die Exception durch eine andere
     * Exeption ausgeloest wurde.
     *
     * @param cause   ausloesende Exeception
     * @param task
     */
    public NotAuthorized(String task, Throwable cause) {
        super(message, NOT_AUTHORIZED_ERROR_ID, cause);
        this.task = task;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + this.task;
    }
}
