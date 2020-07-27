package pl.edu.agh.msm.dense.packing.model;

public class Plane {
    private final Type type;
    private final double position;

    Plane(Type type, double position) {
        this.type = type;
        this.position = position;
    }

    public double computeDistance(Sphere s) {
        switch (type) {
            case YZ:
                return Math.abs(s.coords.getX() - position) - s.getR();
            case XZ:
                return Math.abs(s.coords.getY() - position) - s.getR();
            default:
                return Math.abs(s.coords.getZ() - position) - s.getR();
        }
    }

    public Type getType() {
        return type;
    }

    public double getPosition() {
        return position;
    }

    enum Type {
        YZ, XZ, XY
    }

    @Override
    public String toString() {
        return "Plane{" +
                "type=" + type +
                ", position=" + position +
                '}';
    }
}
