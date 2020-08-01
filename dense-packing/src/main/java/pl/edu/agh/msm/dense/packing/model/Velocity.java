package pl.edu.agh.msm.dense.packing.model;

public class Velocity extends Coords {

    public Velocity(double x, double y) {
        super(x, y, 0);
    }

    public Velocity(double x, double y, double z) {
        super(x, y, z);
    }

    public void negateX() {
        setX(-getX());
    }

    public void negateY() {
        setY(-getY());
    }

    public void negateZ() {
        setZ(-getZ());
    }

    public void addToX(double value) {
        setX(getX() + value);
    }

    public void addToY(double value) {
        setY(getY() + value);
    }

    public void addToZ(double value) {
        setZ(getZ() + value);
    }

    public void multiplyX(double value) {
        setX(getX() * value);
    }

    public void multiplyY(double value) {
        setY(getY() * value);
    }

    public void multiplyZ(double value) {
        setZ(getZ() * value);
    }
}
