package kehd.bigpicture.exceptions;

/**
 * Created by jakob on 6/2/14.
 */
public class UserAlreadyExists extends ParameterException {
    private final static String message = "User with this username already exists.";
    /**
     * Exception mit erklaerender Nachricht sowie
     * einer id um die erkennung der Exception auf Client-Seite
     * zu vereinfachen.
     */
    public UserAlreadyExists() {
        super(message, USER_ALREADY_EXISTS_ERROR_ID);
    }

    /**
     * Konstruktor wenn die Exception durch eine andere
     * Exeption ausgeloest wurde.
     *
     * @param cause   ausloesende Exeception
     */
    public UserAlreadyExists(Throwable cause) {
        super(message, USER_ALREADY_EXISTS_ERROR_ID, cause);
    }
}
