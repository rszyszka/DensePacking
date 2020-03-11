package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;
import static pl.edu.agh.msm.dense.packing.Coords.coords;


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
        Circle circle = new Circle(radius);
        circle.setCoords(coords(radius, radius));

        assertTrue(bin.addCircle(circle));
    }

    @ParameterizedTest
    @ValueSource(ints = {5, 19, 91})
    public void shouldFailOnCircleAddition(int x) {
        preAddCircleToBin();

        Circle c2 = new Circle(10);
        c2.setCoords(coords(x, 10));

        assertFalse(bin.addCircle(c2));
    }

    @ParameterizedTest
    @ValueSource(ints = {30, 55, 90})
    public void shouldAddSecondCircleSuccessfully(int x) {
        preAddCircleToBin();

        Circle circle = new Circle(10);
        circle.setCoords(coords(x, 10));

        assertTrue(bin.addCircle(circle));
    }

    private void preAddCircleToBin() {
        Circle c1 = new Circle((10));
        c1.setCoords(coords(10, 10));
        Assumptions.assumeTrue(bin.addCircle(c1), "Problem in addition of valid circle");
    }

    @Test
    void shouldGetUnmodifiableListOfCircles() {
        preAddCircleToBin();
        assertEquals(1, bin.getCircles().size());
        assertThrows(UnsupportedOperationException.class, () -> bin.getCircles().add(new Circle(10)));
    }

}