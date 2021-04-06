package org.fujion.chartjs.common;

public enum CartesianAxisEnum {
    X, Y;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
