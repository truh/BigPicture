package kehd.bigpicture.exceptions;

/**
 *
 */
public class AppointmentContainsUser extends ParameterException {
    private final static String message = "Appointment contains user.";
    /**
     * Exception mit erklaerender Nachricht sowie
     * einer id um die erkennung der Exception auf Client-Seite
     * zu vereinfachen.
     */
    public AppointmentContainsUser() {
        super(message, APPOINTMENT_CONTAINS_USER_ERROR_ID);
    }

    /**
     * Konstruktor wenn die Exception durch eine andere
     * Exeption ausgeloest wurde.
     *
     * @param cause   ausloesende Exeception
     */
    public AppointmentContainsUser(Throwable cause) {
        super(message, APPOINTMENT_CONTAINS_USER_ERROR_ID, cause);
    }
}
