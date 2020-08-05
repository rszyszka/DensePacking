package pl.edu.agh.msm.dense.packing.model;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.edu.agh.msm.dense.packing.model.Coords.coords;


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

        assertEquals(6, holes.size());
        double x = Utils.roundUp(holes.get(5).getCoords().getX(), 3);
        double y = Utils.roundUp(holes.get(5).getCoords().getY(), 3);
        assertEquals(coords(7.938, 9.295), coords(x, y));
    }


    @Test
    void shouldCalculateHoleDegreeCorrectly() {
        Sphere additionalSphere = new Sphere(5);
        additionalSphere.setCoords(coords(7.9375, 9.294528874347945));
        Assumptions.assumeTrue(bin.addSphere(additionalSphere));

        Sphere sphere = new Sphere(5);
        List<Hole> holes = holesFinder.findForSphere(sphere);

        assertEquals(6, holes.size());
        Hole hole = holesFinder.findHoleWithMaximumDegree();
        double degree = Utils.roundUp(hole.getDegree(), 4);
        assertEquals(0.8133, degree);
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

        assertEquals(0.72379776258, holesFinder.findHoleWithMaximumDegree().getDegree());
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
        assertEquals(8, holes.size());

        holes = holesFinder.findForSphere(new Sphere(6));
        assertEquals(8, holes.size());
    }

    @Test
    public void distanceTest() {
        Sphere c1 = new Sphere(5);
        c1.setCoords(coords(5, 5));
        Sphere c2 = new Sphere(10);
        c2.setCoords(coords(20, 5));

        double x1 = 5;
        double y1 = 5;
        double r1 = 5;
        double x2 = 20;
        double y2 = 5;
        double r2 = 10;

        assertEquals(0.0, Utils.computeDistanceBetweenCircuits(c1, c2));

        System.out.println(sqrt(pow(x1 - x2, 2) + pow(y1 - y2, 2) + pow(r1 - r2, 2)));
    }


    @Test
    public void shouldApplyPenaltyToHolesFromPlanesExceptTop() {
        Sphere s1 = new Sphere(5);
        s1.setCoords(Coords.coords(50, 95));
        Assumptions.assumeTrue(bin.addSphere(s1));

        Sphere sphere = new Sphere(3);
        HolesFinder.PENALTY_VALUE = 0.0;
        List<Hole> holes = holesFinder.findForSphere(sphere);

        double expected1 = holes.get(3).getDegree();
        double expected2 = holes.get(4).getDegree();
        double expected3 = holes.get(5).getDegree();

        HolesFinder.penaltyType = PenaltyType.ALL_EXCEPT_TOP;
        HolesFinder.PENALTY_VALUE = 0.5;
        holes = holesFinder.findForSphere(sphere);


        assertEquals(expected1 - HolesFinder.PENALTY_VALUE, holes.get(3).getDegree());
        assertEquals(expected2, holes.get(4).getDegree());
        assertEquals(expected3 - HolesFinder.PENALTY_VALUE, holes.get(5).getDegree());
    }

    @Test
    public void shouldApplyPenaltyToHolesFromPlanesExceptBot() {
        Sphere s1 = new Sphere(5);
        s1.setCoords(Coords.coords(50, 95));
        Assumptions.assumeTrue(bin.addSphere(s1));

        Sphere sphere = new Sphere(3);
        HolesFinder.PENALTY_VALUE = 0.0;
        List<Hole> holes = holesFinder.findForSphere(sphere);

        double expected1 = holes.get(3).getDegree();
        double expected2 = holes.get(4).getDegree();
        double expected3 = holes.get(5).getDegree();

        HolesFinder.penaltyType = PenaltyType.ALL_EXCEPT_BOT;
        HolesFinder.PENALTY_VALUE = 0.5;
        holes = holesFinder.findForSphere(sphere);


        assertEquals(expected1 - HolesFinder.PENALTY_VALUE, holes.get(3).getDegree());
        assertEquals(expected2 - HolesFinder.PENALTY_VALUE, holes.get(4).getDegree());
        assertEquals(expected3, holes.get(5).getDegree());
    }

    @Test
    public void shouldNotApplyAnyPenalty() {
        Sphere s1 = new Sphere(5);
        s1.setCoords(Coords.coords(50, 95));
        Assumptions.assumeTrue(bin.addSphere(s1));

        Sphere sphere = new Sphere(3);
        HolesFinder.PENALTY_VALUE = 0.0;
        List<Hole> holes = holesFinder.findForSphere(sphere);

        double expected1 = holes.get(3).getDegree();
        double expected2 = holes.get(4).getDegree();
        double expected3 = holes.get(5).getDegree();

        HolesFinder.penaltyType = PenaltyType.NO_PENALTY;
        HolesFinder.PENALTY_VALUE = 0.5;
        holes = holesFinder.findForSphere(sphere);


        assertEquals(expected1, holes.get(3).getDegree());
        assertEquals(expected2, holes.get(4).getDegree());
        assertEquals(expected3, holes.get(5).getDegree());
    }

}