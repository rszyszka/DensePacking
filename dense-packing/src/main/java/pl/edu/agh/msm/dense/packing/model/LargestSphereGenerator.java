package pl.edu.agh.msm.dense.packing.model;


public class LargestSphereGenerator extends AbstractSphereGenerator {

    public LargestSphereGenerator(int minRadius, int maxRadius) {
        super(minRadius, maxRadius);
    }

    @Override
    public Sphere generateNewSphere() {
        return new Sphere(maxRadius);
    }

}
