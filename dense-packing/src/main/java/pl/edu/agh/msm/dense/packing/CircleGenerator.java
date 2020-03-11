package pl.edu.agh.msm.dense.packing;

public interface CircleGenerator {

    Circle generateNewCircle();

    boolean setLowerRadiusIfPossible(int currentRadiusOfFailedCirclePackingAttempt);

}
