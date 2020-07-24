package pl.edu.agh.msm.dense.packing.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LargestSphereGeneratorTest {

    private LargestSphereGenerator sphereGenerator;

    @BeforeEach
    void setup() {
        sphereGenerator = new LargestSphereGenerator(5, 10);
    }

    @Test
    void shouldGenerateNewSphereWithMaxRadius() {
        Sphere sphere = sphereGenerator.generateNewSphere();
        assertEquals(10, sphere.getR());
    }

    @Test
    void shouldGenerateNewSphereWithDiminishedRadius() {
        sphereGenerator.setLowerRadiusIfPossible(7);
        Sphere sphere = sphereGenerator.generateNewSphere();
        assertEquals(6, sphere.getR());
    }

    @Test
    void shouldGenerateNewSphereWithResetRadius() {
        sphereGenerator.setLowerRadiusIfPossible(7);
        sphereGenerator.resetMaxRadius();
        Sphere sphere = sphereGenerator.generateNewSphere();
        assertEquals(10, sphere.getR());
    }
}