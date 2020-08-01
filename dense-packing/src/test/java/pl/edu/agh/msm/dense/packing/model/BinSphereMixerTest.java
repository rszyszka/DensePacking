package pl.edu.agh.msm.dense.packing.model;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class BinSphereMixerTest {

    @Test
    public void shouldSwapVelocitiesBetweenSpheresWithDragApplied() {
        Bin bin = new Bin(100, 100);
        Sphere s1 = new Sphere(10);
        s1.setVelocity(new Velocity(0.4, 0));
        s1.setCoords(Coords.coords(20, 20));
        Sphere s2 = new Sphere(10);
        s2.setVelocity(new Velocity(-0.2, 0));
        s2.setCoords(Coords.coords(40, 20));
        Assumptions.assumeTrue(bin.addSphere(s1));
        Assumptions.assumeTrue(bin.addSphere(s2));

        BinSphereMixer mixer = new BinSphereMixer2D(bin);
        mixer.performStep();

        assertEquals(-0.99 * 0.2, s1.getVelocity().getX());
        assertEquals(0.99 * 0.4, s2.getVelocity().getX());
    }


    @Test
    public void shouldMakeSmallerBallMoveFaster() {
        Bin bin = new Bin(200, 200);
        Sphere s1 = new Sphere(10);
        s1.setVelocity(new Velocity(0.5, 0));
        s1.setCoords(Coords.coords(20, 60));
        Sphere s2 = new Sphere(40);
        s2.setVelocity(new Velocity(-0.5, 0));
        s2.setCoords(Coords.coords(70, 60));
        Assumptions.assumeTrue(bin.addSphere(s1));
        Assumptions.assumeTrue(bin.addSphere(s2));

        BinSphereMixer mixer = BinSphereMixer.create(bin);
        mixer.performStep();

        double v1 = Math.abs(s1.getVelocity().getX());
        double v2 = Math.abs(s2.getVelocity().getX());
        assertTrue(v1 > v2, "Smaller ball velocity: " + v1 + " should be greater than bigger ball velocity: " + v2);
    }


    @Test
    public void shouldResolveYZPlaneCollisionProperly() {
        Bin bin = new Bin(100, 100);
        Sphere s1 = new Sphere(10);
        s1.setVelocity(new Velocity(-0.5, 0));
        s1.setCoords(Coords.coords(10, 50));
        Assumptions.assumeTrue(bin.addSphere(s1));

        BinSphereMixer mixer = BinSphereMixer.create(bin);
        mixer.performStep();

        assertEquals(0.99 * 0.5, s1.getVelocity().getX());
    }


    @Test
    public void shouldResolveXZPlaneCollisionProperly() {
        Bin bin = new Bin(100, 100);
        Sphere s1 = new Sphere(10);
        s1.setVelocity(new Velocity(0, -0.5));
        s1.setCoords(Coords.coords(50, 10));
        Assumptions.assumeTrue(bin.addSphere(s1));

        BinSphereMixer mixer = BinSphereMixer.create(bin);
        mixer.performStep();

        assertEquals(0.9 * (0.5 + 0.2), s1.getVelocity().getY());
    }


    @Test
    public void shouldResolveXYPlaneCollisionProperly() {
        Bin bin = new Bin(100, 100, 100);
        Sphere s1 = new Sphere(10);
        s1.setVelocity(new Velocity(0, 0, -0.5));
        s1.setCoords(Coords.coords(50, 50, 10));
        Assumptions.assumeTrue(bin.addSphere(s1));

        BinSphereMixer mixer = BinSphereMixer.create(bin);
        mixer.performStep();

        assertEquals(0.99 * 0.5, s1.getVelocity().getZ());
    }


    @Test
    public void shouldReverseGravity() {
        Bin bin = new Bin(100, 100, 100);
        Sphere s1 = new Sphere(10);
        s1.setCoords(Coords.coords(50, 50, 50));
        Assumptions.assumeTrue(bin.addSphere(s1));

        BinSphereMixer mixer = BinSphereMixer.create(bin);
        double currentYVelocity = s1.getVelocity().getY();
        mixer.performStep();

        assertEquals(0.9 * (currentYVelocity + 0.2), s1.getVelocity().getY());

        mixer.reverseGravity();
        currentYVelocity = s1.getVelocity().getY();
        mixer.performStep();

        assertEquals(0.9 * (currentYVelocity - 0.2), s1.getVelocity().getY());
    }

    @Test
    public void shouldSimulateContinuouslyInOtherThreadUntilCallingStopMethod() {
        Bin bin = new Bin(200, 200, 200);
        Sphere s1 = new Sphere(10);
        s1.setVelocity(new Velocity(0.5, 0, 0));
        s1.setCoords(Coords.coords(20, 60, 50));
        Sphere s2 = new Sphere(40);
        s2.setVelocity(new Velocity(-0.5, 0));
        s2.setCoords(Coords.coords(70, 60, 50));
        Assumptions.assumeTrue(bin.addSphere(s1));
        Assumptions.assumeTrue(bin.addSphere(s2));

        BinSphereMixer mixer = BinSphereMixer.create(bin);
        Thread thread = new Thread(mixer::simulateContinuously);
        thread.setDaemon(true);
        thread.start();

        double start = Instant.now().toEpochMilli();
        double stop = start;
        while (stop - start < 500) {
            stop = Instant.now().toEpochMilli();
        }
        mixer.stop();
        try {
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        assertFalse(thread.isAlive());
    }
}