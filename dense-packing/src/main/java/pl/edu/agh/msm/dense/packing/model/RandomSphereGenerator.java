package pl.edu.agh.msm.dense.packing.model;

import java.util.Random;

public class RandomSphereGenerator implements SphereGenerator {
    private final int minRadius;
    private int maxRadius;
    private final int originMaxRadius;


    public RandomSphereGenerator(int minRadius, int maxRadius) {
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        originMaxRadius = maxRadius;
    }


    @Override
    public Sphere generateNewSphere() {
        int diff = maxRadius - minRadius;
        int radius = diff == 0 ? minRadius : new Random().nextInt(diff) + minRadius;
        return new Sphere(radius);
    }


    @Override
    public boolean setLowerRadiusIfPossible(int currentCircleRadius) {
        if (currentCircleRadius <= minRadius) {
            return false;
        }
        maxRadius = currentCircleRadius - 1;
        return true;
    }

    @Override
    public void resetMaxRadius() {
        maxRadius = originMaxRadius;
    }
}
