package pl.edu.agh.msm.dense.packing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GravityTest {

    private Gravity gravity;

    @Test
    public void shouldApplyGravityInXZCorrectly() {
        Sphere sphere = new Sphere(10);
        sphere.setVelocity(new Velocity(10, 20, 30));

        gravity = new GravityXZ();

        gravity.applyToSphereVelocity(sphere);

        assertEquals(0.99 * 10, sphere.getVelocity().getX());
        assertEquals(0.9 * (20 + 0.2), sphere.getVelocity().getY());
        assertEquals(0.99 * 30, sphere.getVelocity().getZ());
    }

    @Test
    public void shouldApplyGravityInYZCorrectly() {
        Sphere sphere = new Sphere(10);
        sphere.setVelocity(new Velocity(10, 20, 30));

        gravity = new GravityYZ();

        gravity.applyToSphereVelocity(sphere);

        assertEquals(0.9 * (10 + 0.2), sphere.getVelocity().getX());
        assertEquals(0.99 * 20, sphere.getVelocity().getY());
        assertEquals(0.99 * 30, sphere.getVelocity().getZ());
    }

    @Test
    public void shouldApplyGravityInXYCorrectly() {
        Sphere sphere = new Sphere(10);
        sphere.setVelocity(new Velocity(10, 20, 30));

        gravity = new GravityXY();

        gravity.applyToSphereVelocity(sphere);

        assertEquals(0.99 * 10, sphere.getVelocity().getX());
        assertEquals(0.99 * 20, sphere.getVelocity().getY());
        assertEquals(0.9 * (30 + 0.2), sphere.getVelocity().getZ());
    }

}