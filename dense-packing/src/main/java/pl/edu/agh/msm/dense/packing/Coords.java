package pl.edu.agh.msm.dense.packing;

import java.util.Objects;

public class Coords {
    private double x;
    private double y;

    private Coords(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public static Coords coords(double x, double y) {
        return new Coords(x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return x == coords.x &&
                y == coords.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "Coords{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
