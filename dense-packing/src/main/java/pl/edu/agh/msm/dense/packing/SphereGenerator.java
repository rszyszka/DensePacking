package pl.edu.agh.msm.dense.packing;

public interface SphereGenerator {

    Sphere generateNewSphere();

    boolean setLowerRadiusIfPossible(int currentRadiusOfFailedCirclePackingAttempt);

    void resetMaxRadius();

}
