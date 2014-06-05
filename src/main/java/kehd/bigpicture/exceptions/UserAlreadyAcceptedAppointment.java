package kehd.bigpicture.exceptions;

/**
 *
 */
public class UserAlreadyAcceptedAppointment extends ParameterException{
    private final static String message = "There already is an user inside the given appointment.";
    /**
     * Exception mit erklaerender Nachricht sowie
     * einer id um die erkennung der Exception auf Client-Seite
     * zu vereinfachen.
     */
    public UserAlreadyAcceptedAppointment() {
        super(message, USER_ALREADY_ACCEPTED_APPOINTMENT_ERROR_ID);
    }

    /**
     * Konstruktor wenn die Exception durch eine andere
     * Exeption ausgeloest wurde.
     *
     * @param cause   ausloesende Exeception
     */
    public UserAlreadyAcceptedAppointment(Throwable cause) {
        super(message, USER_ALREADY_ACCEPTED_APPOINTMENT_ERROR_ID, cause);
    }
}
