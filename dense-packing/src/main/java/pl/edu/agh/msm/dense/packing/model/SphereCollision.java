package pl.edu.agh.msm.dense.packing.model;

public abstract class SphereCollision {
    protected Velocity u1;
    protected Velocity u2;
    protected Velocity v1;
    protected Velocity v2;


    public void resolve(Sphere sphere, Sphere otherSphere) {
        determineAuxiliaryVelocitiesByRotatingForward(sphere, otherSphere);
        computeNewVelocitiesUsingOneDimNewtonian(sphere, otherSphere);
        sphere.setVelocity(determineFinalVelocityByRotatingBackward(v1));
        otherSphere.setVelocity(determineFinalVelocityByRotatingBackward(v2));
    }


    protected abstract void determineAuxiliaryVelocitiesByRotatingForward(Sphere sphere, Sphere otherSphere);


    protected void computeNewVelocitiesUsingOneDimNewtonian(Sphere sphere, Sphere otherSphere) {
        double m1 = computeMass(sphere);
        double m2 = computeMass(otherSphere);
        v1 = new Velocity(u1.getX() * (m1 - m2) / (m1 + m2) + u2.getX() * 2 * m2 / (m1 + m2), u1.getY(), u1.getZ());
        v2 = new Velocity(u2.getX() * (m1 - m2) / (m1 + m2) + u1.getX() * 2 * m1 / (m1 + m2), u2.getY(), u2.getZ());
    }


    protected abstract Velocity determineFinalVelocityByRotatingBackward(Velocity v);


    protected double computeXYAngle(Coords coords, Coords otherCoords) {
        double sphere1XPos = coords.getX();
        double sphere1YPos = coords.getY();
        double sphere2YPos = otherCoords.getY();
        double sphere2XPos = otherCoords.getX();
        return -Math.atan2(sphere2YPos - sphere1YPos, sphere2XPos - sphere1XPos);
    }


    protected Velocity rotateByXY(Velocity velocity, double angle) {
        double x = velocity.getX() * Math.cos(angle) - velocity.getY() * Math.sin(angle);
        double y = velocity.getX() * Math.sin(angle) + velocity.getY() * Math.cos(angle);
        return new Velocity(x, y, velocity.getZ());
    }


    protected double computeXZAngle(Coords coords, Coords otherCoords) {
        double sphere1XPos = coords.getX();
        double sphere1ZPos = coords.getZ();
        double sphere2ZPos = otherCoords.getZ();
        double sphere2XPos = otherCoords.getX();
        return -Math.atan2(sphere2ZPos - sphere1ZPos, sphere2XPos - sphere1XPos);
    }


    protected Velocity rotateByXZ(Velocity velocity, double angle) {
        double x = velocity.getX() * Math.cos(angle) - velocity.getZ() * Math.sin(angle);
        double z = velocity.getX() * Math.sin(angle) + velocity.getZ() * Math.cos(angle);
        return new Velocity(x, velocity.getY(), z);
    }


    protected abstract double computeMass(Sphere sphere);

}
