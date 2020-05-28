package pl.edu.agh.msm.dense.packing;

public interface SphereGenerator {

    Sphere generateNewCircle();

    boolean setLowerRadiusIfPossible(int currentRadiusOfFailedCirclePackingAttempt);

    void resetMaxRadius();

}
