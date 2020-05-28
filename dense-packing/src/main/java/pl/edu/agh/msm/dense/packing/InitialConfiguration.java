package pl.edu.agh.msm.dense.packing;


public abstract class InitialConfiguration {

    protected final Bin bin;
    protected final SphereGenerator sphereGenerator;


    InitialConfiguration(Bin bin, SphereGenerator sphereGenerator) {
        this.bin = bin;
        this.sphereGenerator = sphereGenerator;
    }


    public abstract void init();


    protected void addNewCircleInUpperLeftCorner() {
        Sphere sphere = this.sphereGenerator.generateNewCircle();
        sphere.setCoords(Coords.coords(sphere.getR(), sphere.getR()));
        bin.addCircle(sphere);
    }


    public Bin getBin() {
        return bin;
    }


    public SphereGenerator getSphereGenerator() {
        return sphereGenerator;
    }

}
