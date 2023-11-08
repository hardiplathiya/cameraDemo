package plant.testtree.camerademo.util;


public class ProgressException extends Exception {
    private ErrorCause error;

    public ProgressException(ErrorCause errorCause) {
        this.error = errorCause;
    }

    public ProgressException(String str) {
        this.error = new ErrorCause(str);
    }

    public ErrorCause getError() {
        return this.error;
    }

    @Override 
    public String toString() {
        return this.error.toString();
    }

    @Override 
    public String getMessage() {
        return toString();
    }

    @Override 
    public String getLocalizedMessage() {
        return toString();
    }
}
