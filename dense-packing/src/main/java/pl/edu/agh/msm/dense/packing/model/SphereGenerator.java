package pl.edu.agh.msm.dense.packing.model;

public interface SphereGenerator {

    Sphere generateNewSphere();

    boolean setLowerRadiusIfPossible(int currentRadiusOfFailedCirclePackingAttempt);

    void resetMaxRadius();

}
