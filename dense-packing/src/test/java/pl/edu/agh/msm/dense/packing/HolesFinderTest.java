package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.edu.agh.msm.dense.packing.Coords.coords;


class HolesFinderTest {

    private HolesFinder holesFinder;

    private Bin bin;


    @BeforeEach
    void setUp() {
        bin = new Bin(100, 100);
        holesFinder = new HolesFinder(bin);
    }


    @Test
    void shouldFindHolesCorrectly() {
        Circle c1 = new Circle(3);
        Circle c2 = new Circle(2);
        c1.setCoords(coords(3, 3));
        c2.setCoords(coords(11, 3));
        bin.addCircle(c1);
        bin.addCircle(c2);

        Circle circle = new Circle(5);
        List<Coords> coordsList = holesFinder.findForCircle(circle);

        assertEquals(1, coordsList.size());
        double x = BigDecimal.valueOf(coordsList.get(0).getX()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        double y = BigDecimal.valueOf(coordsList.get(0).getY()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        assertEquals(coords(7.938, 9.295), coords(x, y));
    }

}