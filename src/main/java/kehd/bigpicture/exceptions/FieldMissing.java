package kehd.bigpicture.exceptions;

public class FieldMissing extends ParameterException {
    private static final String message = "Missing field: ";

    private String field;

    /**
     * Exception mit erklaerender Nachricht sowie
     * einer id um die erkennung der Exception auf Client-Seite
     * zu vereinfachen.
     *
     * @param field
     */
    public FieldMissing(String field) {
        super(message, FIELD_MISSING_ERROR_ID);
        this.field = field;
    }

    /**
     * Konstruktor wenn die Exception durch eine andere
     * Exeption ausgeloest wurde.
     *
     * @param field
     * @param cause   ausloesende Exeception
     */
    public FieldMissing(String field, Throwable cause) {
        super(message, FIELD_MISSING_ERROR_ID, cause);
        this.field = field;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + this.field;
    }
}
