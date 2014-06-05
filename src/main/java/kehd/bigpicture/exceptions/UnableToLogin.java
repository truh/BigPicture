package kehd.bigpicture.exceptions;

/**
 * Created by jakob on 6/5/14.
 */
public class UnableToLogin extends ParameterException {
    private static final String message = "Unable to login.";

    public UnableToLogin() {
        super(message, UNABLE_TO_LOGIN_ERROR_ID);
    }

    public UnableToLogin(Throwable cause) {
        super(message, UNABLE_TO_LOGIN_ERROR_ID, cause);
    }
}
