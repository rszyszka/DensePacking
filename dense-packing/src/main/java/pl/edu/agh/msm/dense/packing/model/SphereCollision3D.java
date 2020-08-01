package pl.edu.agh.msm.dense.packing.model;


public class SphereCollision3D extends SphereCollision {
    private double angle1;
    private double angle2;

    @Override
    protected void determineAuxiliaryVelocitiesByRotatingForward(Sphere sphere, Sphere otherSphere) {
        angle1 = computeXYAngle(sphere.getCoords(), otherSphere.getCoords());
        u1 = rotateByXY(sphere.getVelocity(), angle1);
        angle2 = computeXZAngle(sphere.getCoords(), u1);
        u1 = rotateByXZ(u1, angle2);
        u2 = rotateByXZ(rotateByXY(otherSphere.getVelocity(), angle1), angle2);
    }

    @Override
    protected Velocity determineFinalVelocityByRotatingBackward(Velocity v) {
        return rotateByXY(rotateByXZ(v, -angle2), -angle1);
    }

    @Override
    protected double computeMass(Sphere sphere) {
        return 1.3333333333333333333 * Math.PI * Math.pow(sphere.getR(), 3);
    }

}
