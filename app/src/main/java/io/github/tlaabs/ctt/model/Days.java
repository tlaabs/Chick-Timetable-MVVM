package io.github.tlaabs.ctt.model;

public enum Days {
    MON(1), TUE(2), WED(3), THU(4), FRI(5);
    private int value;

    private Days(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
