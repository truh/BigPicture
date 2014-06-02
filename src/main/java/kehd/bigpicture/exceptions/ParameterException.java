package kehd.bigpicture.exceptions;

/**
 * Basisklasse fuer BigPicture Exceptions die mit ungueltigen
 * eingaben zu tun haben.
 */
public abstract class ParameterException extends Exception {

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
