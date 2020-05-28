package pl.edu.agh.msm.dense.packing;

import java.util.Objects;

public class Coords {
    private double x;
    private double y;
    private double z;

    private Coords(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public static Coords coords(double x, double y) {
        return new Coords(x, y, 0);
    }

    public static Coords coords(double x, double y, double z) {
        return new Coords(x, y, z);
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

    @Override
    public String toString() {
        return "Coords{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }
}
