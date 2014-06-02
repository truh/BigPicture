package kehd.bigpicture.exceptions;

public class NoSuchElement extends ParameterException {
    private static final String message = "No such element: ";

    private String elementTypes;

    /**
     * Exception mit erklaerender Nachricht sowie
     * einer id um die erkennung der Exception auf Client-Seite
     * zu vereinfachen.
     *
     * @param elementTypes
     */
    public NoSuchElement(String elementTypes) {
        super(message, NO_SUCH_ELEMENT_ERROR_ID);
        this.elementTypes = elementTypes;
    }

    /**
     * Konstruktor wenn die Exception durch eine andere
     * Exeption ausgeloest wurde.
     *
     * @param elementTypes
     * @param cause   ausloesende Exeception
     */
    public NoSuchElement(String elementTypes, Throwable cause) {
        super(message, NO_SUCH_ELEMENT_ERROR_ID, cause);
        this.elementTypes = elementTypes;
    }

    @Override
    public String getMessage() {
        return super.getMessage() + this.elementTypes;
    }
}
