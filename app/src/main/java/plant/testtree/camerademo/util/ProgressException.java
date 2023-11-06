package plant.testtree.camerademo.util;

/* loaded from: classes.dex */
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

    @Override // java.lang.Throwable
    public String toString() {
        return this.error.toString();
    }

    @Override // java.lang.Throwable
    public String getMessage() {
        return toString();
    }

    @Override // java.lang.Throwable
    public String getLocalizedMessage() {
        return toString();
    }
}
