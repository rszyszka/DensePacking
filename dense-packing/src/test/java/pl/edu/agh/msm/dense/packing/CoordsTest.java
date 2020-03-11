package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static pl.edu.agh.msm.dense.packing.Coords.coords;

class CoordsTest {

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    public void shouldBeEqual(int x) {
        assertEquals(coords(x, 2), coords(x, 2));
        assertEquals(coords(x, 2).hashCode(), coords(x, 2).hashCode());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 4, 5})
    public void shouldBeDifferent(int value) {
        assertNotEquals(coords(value, 2), coords(3, value));
    }

}