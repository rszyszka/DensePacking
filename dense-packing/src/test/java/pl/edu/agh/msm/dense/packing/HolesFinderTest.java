package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.api.Assumptions;
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
        holesFinder = HolesFinder.create(bin);

        Sphere c1 = new Sphere(3);
        Sphere c2 = new Sphere(2);
        c1.setCoords(coords(3, 3));
        c2.setCoords(coords(11, 3));
        bin.addSphere(c1);
        bin.addSphere(c2);
    }


    @Test
    void shouldFindHolesCorrectly() {
        Sphere sphere = new Sphere(5);
        List<Hole> holes = holesFinder.findForSphere(sphere);

        assertEquals(1, holes.size());
        double x = BigDecimal.valueOf(holes.get(0).getCoords().getX()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        double y = BigDecimal.valueOf(holes.get(0).getCoords().getY()).setScale(3, BigDecimal.ROUND_HALF_UP).doubleValue();
        assertEquals(coords(7.938, 9.295), coords(x, y));
    }


    @Test
    void shouldCalculateHoleDegreeCorrectly() {
        Sphere additionalSphere = new Sphere(5);
        additionalSphere.setCoords(coords(7.9375, 9.294528874347945));
        Assumptions.assumeTrue(bin.addSphere(additionalSphere));

        Sphere sphere = new Sphere(5);
        List<Hole> holes = holesFinder.findForSphere(sphere);

        assertEquals(1, holes.size());
        double degree = BigDecimal.valueOf(holes.get(0).getDegree()).setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
        assertEquals(-1.9305, degree);
    }


    @Test
    void shouldFindHoleWithMaximumDegreeCorrectly() {
        Sphere c1 = new Sphere(5);
        c1.setCoords(coords(50, 50));
        bin.addSphere(c1);
        Sphere c2 = new Sphere(5);
        c2.setCoords(coords(40, 40));
        bin.addSphere(c2);
        Sphere c3 = new Sphere(5);
        c3.setCoords(coords(40, 50));
        bin.addSphere(c3);
        Sphere c4 = new Sphere(3);
        c4.setCoords(coords(3, 9));
        bin.addSphere(c4);
        Sphere c5 = new Sphere(3);
        c5.setCoords(coords(3, 15));
        bin.addSphere(c5);

        holesFinder.findForSphere(new Sphere(5));

        assertEquals(-0.8037829604081463, holesFinder.findHoleWithMaximumDegree().getDegree());
    }


    @Test
    void eachCircleShouldHaveOwnHoles() {
        Sphere c1 = new Sphere(5);
        c1.setCoords(coords(50, 50));
        bin.addSphere(c1);
        Sphere c2 = new Sphere(5);
        c2.setCoords(coords(40, 40));
        bin.addSphere(c2);

        List<Hole> holes = holesFinder.findForSphere(new Sphere(5));
        assertEquals(3, holes.size());

        holes = holesFinder.findForSphere(new Sphere(6));
        assertEquals(3, holes.size());
    }

    @Test
    public void distanceTest() {
        Sphere c1 = new Sphere(5);
        c1.setCoords(coords(5, 5));
        Sphere c2 = new Sphere(10);
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