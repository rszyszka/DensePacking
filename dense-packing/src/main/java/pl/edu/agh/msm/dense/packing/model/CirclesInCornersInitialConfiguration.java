package pl.edu.agh.msm.dense.packing.model;

public class CirclesInCornersInitialConfiguration extends InitialConfiguration {
    public CirclesInCornersInitialConfiguration(Bin bin, SphereGenerator sphereGenerator) {
        super(bin, sphereGenerator);
    }

    @Override
    public void init() {
        addNewCircleInUpperLeftCorner();
        addNewCircleInLowerLeftCorner();
    }


    private void addNewCircleInLowerLeftCorner() {
        Sphere sphere = sphereGenerator.generateNewSphere();
        sphere.setCoords(Coords.coords(sphere.getR(), bin.getYSize() - sphere.getR()));
        bin.addSphere(sphere);
    }

}
