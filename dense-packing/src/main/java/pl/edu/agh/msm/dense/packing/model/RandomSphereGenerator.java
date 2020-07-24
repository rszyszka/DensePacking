package pl.edu.agh.msm.dense.packing.model;

import java.util.Random;

public class RandomSphereGenerator extends AbstractSphereGenerator {

    public RandomSphereGenerator(int minRadius, int maxRadius) {
        super(minRadius, maxRadius);
    }

    @Override
    public Sphere generateNewSphere() {
        int diff = maxRadius - minRadius;
        int radius = diff == 0 ? minRadius : new Random().nextInt(diff) + minRadius;
        return new Sphere(radius);
    }

}
