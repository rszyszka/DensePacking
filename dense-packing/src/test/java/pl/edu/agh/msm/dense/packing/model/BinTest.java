package pl.edu.agh.msm.dense.packing.model;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static pl.edu.agh.msm.dense.packing.model.Coords.coords;


class BinTest {

    Bin bin;

    @BeforeEach
    public void setup() {
        bin = new Bin(100, 100);
    }

    @Test
    public void shouldStoreAddedCircle() {
        preAddCircleToBin();
        assertEquals(1, bin.getNumberOfCirclesPacked());
    }

    @ParameterizedTest
    @ValueSource(ints = {10, 20, 30, 5, 7})
    public void shouldAddFirstCircleSuccessfully(int radius) {
        Sphere sphere = new Sphere(radius);
        sphere.setCoords(coords(radius, radius));

        assertTrue(bin.addSphere(sphere));
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 19, 91})
    public void shouldFailOnCircleAddition(int x) {
        preAddCircleToBin();

        Sphere c2 = new Sphere(10);
        c2.setCoords(coords(x, 10));

        assertFalse(bin.addSphere(c2));
    }

    @ParameterizedTest
    @ValueSource(ints = {30, 55, 90})
    public void shouldAddSecondCircleSuccessfully(int x) {
        preAddCircleToBin();

        Sphere sphere = new Sphere(10);
        sphere.setCoords(coords(x, 10));

        assertTrue(bin.addSphere(sphere));
    }

    private void preAddCircleToBin() {
        Sphere c1 = new Sphere((10));
        c1.setCoords(coords(10, 10));
        Assumptions.assumeTrue(bin.addSphere(c1), "Problem in addition of valid circle");
    }

    @Test
    void shouldGetUnmodifiableListOfCircles() {
        preAddCircleToBin();
        assertEquals(1, bin.getSpheres().size());
        assertThrows(UnsupportedOperationException.class, () -> bin.getSpheres().add(new Sphere(10)));
    }

}