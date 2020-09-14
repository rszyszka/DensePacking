package pl.edu.agh.msm.dense.packing.model;


public abstract class InitialConfiguration {

    protected final Bin bin;
    protected final SphereGenerator sphereGenerator;

    public static InitialConfiguration create(Type type, Bin bin, SphereGenerator sphereGenerator) {
        switch (type) {
            case TANGENTIAL_SPHERES:
                return new TangentialCirclesInitialConfiguration(bin, sphereGenerator);
            case TWO_CORNERS_SPHERES:
                return new CirclesInCornersInitialConfiguration(bin, sphereGenerator);
            case ONE_CORNER_SPHERE:
                return new OneCornerSphereInitialConfiguration(bin, sphereGenerator);
            default:
                throw new UnsupportedOperationException("UNKNOWN INITIAL CONFIGURATION TYPE");
        }
    }

    public InitialConfiguration(Bin bin, SphereGenerator sphereGenerator) {
        this.bin = bin;
        this.sphereGenerator = sphereGenerator;
    }


    public abstract void init();


    protected void addNewCircleInUpperLeftCorner() {
        Sphere sphere = this.sphereGenerator.generateNewSphere();
        Coords coords = bin.getZSize() == 1 ? Coords.coords(sphere.getR(), sphere.getR()) : Coords.coords(sphere.getR(), sphere.getR(), sphere.getR());
        sphere.setCoords(coords);
        bin.addSphere(sphere);
    }


    public Bin getBin() {
        return bin;
    }


    public SphereGenerator getSphereGenerator() {
        return sphereGenerator;
    }

    public enum Type {
        TANGENTIAL_SPHERES,
        ONE_CORNER_SPHERE,
        TWO_CORNERS_SPHERES
    }

}
