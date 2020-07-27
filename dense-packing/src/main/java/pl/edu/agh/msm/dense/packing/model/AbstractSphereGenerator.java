package pl.edu.agh.msm.dense.packing.model;

public abstract class AbstractSphereGenerator implements SphereGenerator {
    protected final int minRadius;
    protected int originMaxRadius;
    protected int maxRadius;

    public AbstractSphereGenerator(int minRadius, int maxRadius) {
        this.minRadius = minRadius;
        this.maxRadius = maxRadius;
        originMaxRadius = maxRadius;
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
