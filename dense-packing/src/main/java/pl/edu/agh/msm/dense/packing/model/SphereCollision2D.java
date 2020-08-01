package pl.edu.agh.msm.dense.packing.model;


public class SphereCollision2D extends SphereCollision {
    private double angle;

    @Override
    protected void determineAuxiliaryVelocitiesByRotatingForward(Sphere sphere, Sphere otherSphere) {
        angle = computeXYAngle(sphere.getCoords(), otherSphere.getCoords());
        u1 = rotateByXY(sphere.getVelocity(), angle);
        u2 = rotateByXY(otherSphere.getVelocity(), angle);
    }

    @Override
    protected Velocity determineFinalVelocityByRotatingBackward(Velocity v) {
        return rotateByXY(v, -angle);
    }

    @Override
    protected double computeMass(Sphere sphere) {
        return Math.PI * Math.pow(sphere.getR(), 2);
    }

}
