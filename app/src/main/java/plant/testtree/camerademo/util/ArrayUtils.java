package plant.testtree.camerademo.util;


public final class ArrayUtils {
    public static <T> int getIndex(T[] tArr, T t) {
        for (int i = 0; i < tArr.length; i++) {
            if (tArr[i].equals(t)) {
                return i;
            }
        }
        return -1;
    }
}
