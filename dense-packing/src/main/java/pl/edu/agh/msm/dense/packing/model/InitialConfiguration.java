package pl.edu.agh.msm.dense.packing.model;


public abstract class InitialConfiguration {

    protected final Bin bin;
    protected final SphereGenerator sphereGenerator;


    InitialConfiguration(Bin bin, SphereGenerator sphereGenerator) {
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

}
