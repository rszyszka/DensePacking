package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static pl.edu.agh.msm.dense.packing.Coords.coords;
import static pl.edu.agh.msm.dense.packing.Utils.areCirclesNotOverlapping;
import static pl.edu.agh.msm.dense.packing.Utils.isCircleNotOverlappingBin;


public class UtilsTest {

    @Test
    public void shouldConfirmTwoCirclesNotOverlapping() {
        Circle c1 = new Circle(10);
        c1.setCoords(coords(0, 0));
        Circle c2 = new Circle(10);
        c2.setCoords(coords(25, 25));

        assertTrue(areCirclesNotOverlapping(c1, c2));
    }

    @Test
    public void shouldConfirmTwoCirclesOverlapping() {
        Circle c1 = new Circle(10);
        c1.setCoords(coords(0, 0));
        Circle c2 = new Circle(4);
        c2.setCoords(coords(13, 0));

        assertFalse(areCirclesNotOverlapping(c1, c2));
    }


    @ParameterizedTest
    @ValueSource(ints = {10, 90})
    public void shouldConfirmCircleNotOverlappingTheSpace(int x) {
        Circle circle = new Circle(10);
        circle.setCoords(coords(x, 10));
        Bin bin = new Bin(100, 100);

        assertTrue(isCircleNotOverlappingBin(circle, bin));
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 95})
    public void shouldConfirmCircleOverlappingTheSpace(int x) {
        Circle circle = new Circle(10);
        circle.setCoords(coords(x, 10));
        Bin bin = new Bin(100, 100);

        assertFalse(isCircleNotOverlappingBin(circle, bin));
    }

}
