package kehd.bigpicture.exceptions;

/**
 * Basisklasse fuer BigPicture Exceptions die mit ungueltigen
 * eingaben zu tun haben.
 */
public abstract class ParameterException extends Exception {
    public static final int DATE_HAS_TO_BE_IN_FUTURE_ERROR_ID = 1;
    public static final int DATE_INVALID_ERROR_ID = 2;
    public static final int FIELD_MISSING_ERROR_ID = 3;
    public static final int NO_SUCH_ELEMENT_ERROR_ID = 4;
    public static final int USER_ALREADY_EXISTS_ERROR_ID = 5;
    public static final int NO_SUCH_EVENT_TYPE_ERROR_ID = 6;
    public static final int NOT_AUTHORIZED_ERROR_ID = 7;
    public static final int NOT_AUTHENTIFICATED_ERROR_ID = 8;
    public static final int NO_SUCH_METHOD_ERROR_ID = 9;
    public static final int NOT_A_NUMBER_ERROR_ID = 10;
    public static final int APPOINTMENT_CONTAINS_USER_ERROR_ID = 11;
    public static final int USER_ALREADY_ACCEPTED_APPOINTMENT_ERROR_ID = 12;

    private int errorId;

    /**
     * Exception mit erklaerender Nachricht sowie
     * einer id um die erkennung der Exception auf Client-Seite
     * zu vereinfachen.
     *
     * @param message Nachricht
     * @param errorId id
     */
    public ParameterException(String message, int errorId) {
        this(message, errorId, null);
    }

    /**
     * Konstruktor wenn die Exception durch eine andere
     * Exeption ausgeloest wurde.
     *
     * @param message Nachricht
     * @param errorId id
     * @param cause ausloesende Exeception
     */
    public ParameterException(String message, int errorId, Throwable cause) {
        super(message, cause);
        this.errorId = errorId;
    }

    /**
     * @return id
     */
    public int getErrorId() {
        return errorId;
    }
}
