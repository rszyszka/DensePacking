package pl.edu.agh.msm.dense.packing.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class OscillatingSphereGeneratorTest {

    OscillatingSphereGenerator sphereGenerator;

    @Test
    void resetMaxRadius() {
        sphereGenerator = new OscillatingSphereGenerator(8, 10, 2);
        assertEquals(10, sphereGenerator.generateNewSphere().getR());
        sphereGenerator.resetMaxRadius();
        assertEquals(10, sphereGenerator.generateNewSphere().getR());
        sphereGenerator.resetMaxRadius();
        assertEquals(9, sphereGenerator.generateNewSphere().getR());
        sphereGenerator.resetMaxRadius();
        assertEquals(9, sphereGenerator.generateNewSphere().getR());
        sphereGenerator.resetMaxRadius();
        assertEquals(8, sphereGenerator.generateNewSphere().getR());
        sphereGenerator.resetMaxRadius();
        assertEquals(8, sphereGenerator.generateNewSphere().getR());
        sphereGenerator.resetMaxRadius();
        assertEquals(9, sphereGenerator.generateNewSphere().getR());
        sphereGenerator.resetMaxRadius();
        assertEquals(9, sphereGenerator.generateNewSphere().getR());
        sphereGenerator.resetMaxRadius();
        assertEquals(10, sphereGenerator.generateNewSphere().getR());
        sphereGenerator.resetMaxRadius();
        assertEquals(10, sphereGenerator.generateNewSphere().getR());
        sphereGenerator.resetMaxRadius();
        assertEquals(9, sphereGenerator.generateNewSphere().getR());
        sphereGenerator.resetMaxRadius();
        assertEquals(9, sphereGenerator.generateNewSphere().getR());
    }
}