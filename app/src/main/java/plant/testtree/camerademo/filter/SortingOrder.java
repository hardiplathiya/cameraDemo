package plant.testtree.camerademo.filter;


public enum SortingOrder {
    ASCENDING(1),
    DESCENDING(0);
    
    int value;

    SortingOrder(int i) {
        this.value = i;
    }

    public int getValue() {
        return this.value;
    }

    public boolean isAscending() {
        return this.value == ASCENDING.getValue();
    }

    public static SortingOrder fromValue(boolean z) {
        return z ? ASCENDING : DESCENDING;
    }

    public static SortingOrder fromValue(int i) {
        return i == 0 ? DESCENDING : ASCENDING;
    }
}
