package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.edu.agh.msm.dense.packing.Coords.coords;


class HolesFinderTest {

    private HolesFinder holesFinder;

    private Bin bin;


    @BeforeEach
    void setUp() {
        bin = new Bin(100, 100);
        holesFinder = new HolesFinder(bin);

        Circle c1 = new Circle(3);
        Circle c2 = new Circle(2);
        c1.setCoords(coords(3, 3));
        c2.setCoords(coords(11, 3));
        bin.addCircle(c1);
        bin.addCircle(c2);
    }


    @Test
    void shouldFindHolesCorrectly() {
        Circle circle = new Circle(5);
        List<Hole> holes = holesFinder.findForCircle(circle);

        assertEquals(1, holes.size());
        double x = BigDecimal.valueOf(holes.get(0).getCoords().getX()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        double y = BigDecimal.valueOf(holes.get(0).getCoords().getY()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        assertEquals(coords(7.938, 9.295), coords(x, y));
    }


    @Test
    void shouldCalculateHoleDegreeCorrectly() {
        Circle additionalCircle = new Circle(5);
        additionalCircle.setCoords(coords(7.9375, 9.294528874347945));
        bin.addCircle(additionalCircle);

        Circle circle = new Circle(5);
        List<Hole> holes = holesFinder.findForCircle(circle);

        assertEquals(1, holes.size());
        double degree = BigDecimal.valueOf(holes.get(0).getDegree()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        assertEquals(-1.9305, degree);
    }


    @Test
    void shouldFindHoleWithMaximumDegreeCorrectly() {
        Circle c1 = new Circle(5);
        c1.setCoords(coords(50, 50));
        bin.addCircle(c1);
        Circle c2 = new Circle(5);
        c2.setCoords(coords(40, 40));
        bin.addCircle(c2);
        Circle c3 = new Circle(5);
        c3.setCoords(coords(40, 50));
        bin.addCircle(c3);
        Circle c4 = new Circle(3);
        c4.setCoords(coords(3, 9));
        bin.addCircle(c4);
        Circle c5 = new Circle(3);
        c5.setCoords(coords(3, 15));
        bin.addCircle(c5);

        holesFinder.findForCircle(new Circle(5));

        assertEquals(-0.8037829604081463, holesFinder.findHoleWithMaximumDegree().getDegree());
    }


    @Test
    void eachCircleShouldHaveOwnHoles() {
        Circle c1 = new Circle(5);
        c1.setCoords(coords(50, 50));
        bin.addCircle(c1);
        Circle c2 = new Circle(5);
        c2.setCoords(coords(40, 40));
        bin.addCircle(c2);

        List<Hole> holes = holesFinder.findForCircle(new Circle(5));
        assertEquals(3, holes.size());

        holes = holesFinder.findForCircle(new Circle(6));
        assertEquals(3, holes.size());
    }

    @Test
    public void distanceTest() {
        Circle c1 = new Circle(5);
        c1.setCoords(coords(5, 5));
        Circle c2 = new Circle(10);
        c2.setCoords(coords(20, 5));

        System.out.println(Utils.computeDistanceBetweenCircuits(c1, c2));

        double x1 = 5;
        double y1 = 5;
        double r1 = 5;
        double x2 = 20;
        double y2 = 5;
        double r2 = 10;

        System.out.println(sqrt(pow(x1 - x2, 2) + pow(y1 - y2, 2) + pow(r1 - r2, 2)));

    }

}