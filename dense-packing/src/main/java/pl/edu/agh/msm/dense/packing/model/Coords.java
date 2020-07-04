package pl.edu.agh.msm.dense.packing.model;

import java.util.Objects;

import static java.lang.Double.isNaN;

public class Coords {
    private final double x;
    private final double y;
    private final double z;

    private Coords(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Coords coords(double x, double y) {
        return new Coords(x, y, 0);
    }

    public static Coords coords(double x, double y, double z) {
        return new Coords(isNaN(x) ? 0 : x, isNaN(y) ? 0 : y, isNaN(z) ? 0 : z);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coords coords = (Coords) o;
        return x == coords.x &&
                y == coords.y &&
                z == coords.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }
}
