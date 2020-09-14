package pl.edu.agh.msm.dense.packing.model;

public interface SphereGenerator {

    Sphere generateNewSphere();

    boolean setLowerRadiusIfPossible(int currentRadiusOfFailedCirclePackingAttempt);

    void resetMaxRadius();

    static SphereGenerator create(Type type, int minRadius, int maxRadius, int counter){
        switch (type){
            case RANDOM:
                return new RandomSphereGenerator(minRadius, maxRadius);
            case LARGEST:
                return new LargestSphereGenerator(minRadius, maxRadius);
            case OSCILLATING:
                return new OscillatingSphereGenerator(minRadius, maxRadius, counter);
            default:
                throw new UnsupportedOperationException("UNKNOWN SPHERE GENERATOR TYPE");
        }
    }

    enum Type {
        RANDOM, LARGEST, OSCILLATING
    }

}
