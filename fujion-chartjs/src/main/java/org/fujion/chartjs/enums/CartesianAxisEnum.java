package org.fujion.chartjs.enums;

public enum CartesianAxisEnum {
    X, Y;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
