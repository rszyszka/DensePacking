package pl.edu.agh.msm.dense.packing.model;

public class Plane {
    private final Type type;
    private final double position;

    Plane(Type type, double position) {
        this.type = type;
        this.position = position;
    }

    public double computeDistance(Coords coords) {
        return Math.sqrt(computeSquaredDistance(coords));
    }

    public double computeSquaredDistance(Coords coords) {
        switch (type) {
            case YZ:
                return Math.pow(coords.getX() - position, 2);
            case XZ:
                return Math.pow(coords.getY() - position, 2);
            default:
                return Math.pow(coords.getZ() - position, 2);
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
}
