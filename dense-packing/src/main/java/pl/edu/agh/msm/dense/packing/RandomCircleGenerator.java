package pl.edu.agh.msm.dense.packing;

import java.util.Random;

public class RandomCircleGenerator implements CircleGenerator {
    private int minRadius;
    private int maxRadius;


    public RandomCircleGenerator(int minRadius, int maxRadius) {
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
    }


    @Override
    public Circle generateNewCircle() {
        int diff = maxRadius - minRadius;
        int radius = diff == 0 ? minRadius : new Random().nextInt(diff) + minRadius;
        return new Circle(radius);
    }


    @Override
    public boolean setLowerRadiusIfPossible(int currentCircleRadius) {
        if (currentCircleRadius <= minRadius) {
            return false;
        }
        maxRadius = currentCircleRadius - 1;
        return true;
    }

}
