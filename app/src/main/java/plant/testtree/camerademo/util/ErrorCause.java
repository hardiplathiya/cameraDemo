package plant.testtree.camerademo.util;

import java.util.ArrayList;
import java.util.Iterator;


public class ErrorCause {
    private ArrayList<String> causes;
    private String title;

    public ErrorCause(String str, ArrayList<String> arrayList) {
        this.title = str;
        this.causes = arrayList;
    }

    public ErrorCause(String str) {
        this.title = str;
        this.causes = new ArrayList<>(1);
    }

    public void addCause(String str) {
        this.causes.add(str);
    }

    public String getTitle() {
        return this.title;
    }

    public boolean hasErrors() {
        return this.causes.size() > 0;
    }

    public ErrorCause get() {
        if (hasErrors()) {
            return this;
        }
        return null;
    }

    public ArrayList<String> getCauses() {
        return this.causes;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.title);
        sb.append("\n");
        Iterator<String> it2 = this.causes.iterator();
        while (it2.hasNext()) {
            sb.append(it2.next());
            sb.append("\n");
        }
        return sb.toString();
    }

    public static ErrorCause fromThrowable(Throwable th) {
        if (th instanceof ProgressException) {
            return ((ProgressException) th).getError();
        }
        return new ErrorCause(th.getMessage());
    }
}
