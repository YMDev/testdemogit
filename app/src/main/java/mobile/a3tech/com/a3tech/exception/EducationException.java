package mobile.a3tech.com.a3tech.exception;

public class EducationException extends Exception {
    private static final long serialVersionUID = -4967006993863359916L;

    public EducationException(String message) {
        super(message);
    }

    public EducationException(Throwable throwable) {
        super(throwable);
    }

    public EducationException(String message, Throwable throwable) {
        super(message, throwable);
    }
}
