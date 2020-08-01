package pl.edu.agh.msm.dense.packing.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.edu.agh.msm.dense.packing.model.Coords.coords;
import static pl.edu.agh.msm.dense.packing.model.Utils.areSpheresOverlapping;
import static pl.edu.agh.msm.dense.packing.model.Utils.isSphereOverlappingBin;


public class UtilsTest {

    @Test
    public void shouldConfirmTwoCirclesNotOverlapping() {
        Sphere c1 = new Sphere(10);
        c1.setCoords(coords(0, 0));
        Sphere c2 = new Sphere(10);
        c2.setCoords(coords(25, 25));

        assertFalse(areSpheresOverlapping(c1, c2));
    }

    @Test
    public void shouldConfirmTwoCirclesOverlapping() {
        Sphere c1 = new Sphere(10);
        c1.setCoords(coords(0, 0));
        Sphere c2 = new Sphere(4);
        c2.setCoords(coords(13, 0));

        assertTrue(areSpheresOverlapping(c1, c2));
    }


    @ParameterizedTest
    @ValueSource(ints = {10, 90})
    public void shouldConfirmCircleNotOverlappingTheSpace(int x) {
        Sphere sphere = new Sphere(10);
        sphere.setCoords(coords(x, 10));
        Bin bin = new Bin(100, 100);

        assertFalse(isSphereOverlappingBin(sphere, bin));
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 95})
    public void shouldConfirmCircleOverlappingTheSpace(int x) {
        Sphere sphere = new Sphere(10);
        sphere.setCoords(coords(x, 10));
        Bin bin = new Bin(100, 100);

        assertTrue(isSphereOverlappingBin(sphere, bin));
    }


    @Test
    public void shouldCheckOnlyOneCondition() {
        Bin bin = new Bin(10, 10);
        Sphere sphere = new Sphere(5);

        assertFalse(Utils.isSphereAbleToBePlacedInBin(sphere, bin));
    }

}
