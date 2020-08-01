package pl.edu.agh.msm.dense.packing.model;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HolesFinder3DTest {

    @Test
    public void findInnerHoleFromSpheresTest() {
        Sphere s1 = new Sphere(5);
        Sphere s2 = new Sphere(5);
        Sphere s3 = new Sphere(5);
        s1.setCoords(Coords.coords(5, 5, 5));
        s2.setCoords(Coords.coords(15, 5, 5));
        s3.setCoords(Coords.coords(10, 15, 5));

        Bin bin = new Bin(100, 100, 100);
        bin.addSphere(s1);
        bin.addSphere(s2);
        bin.addSphere(s3);
        HolesFinder holesFinder = HolesFinder.create(bin);

        Sphere newSphere = new Sphere(5);
        List<Hole> holes = holesFinder.findForSphere(newSphere);
        int size = holes.size();
        Hole hole = holes.get(size - 1);

        assertEquals(13, size);
        assertEquals(Coords.coords(10.0, 8.75, 12.806247497997997), hole.getCoords());
    }


    @Test
    public void findBoundaryHoleXYTest() {
        Sphere s1 = new Sphere(1);
        Sphere s2 = new Sphere(1);
        s1.setCoords(Coords.coords(1, 1, 1));
        s2.setCoords(Coords.coords(3, 3, 1));

        Bin bin = new Bin(100, 100, 100);
        bin.addSphere(s1);
        bin.addSphere(s2);
        HolesFinder holesFinder = HolesFinder.create(bin);

        Sphere newSphere = new Sphere(1);
        List<Hole> holes = holesFinder.findForSphere(newSphere);
        int size = holes.size();

        Hole hole1 = holes.get(size - 2);
        Hole hole2 = holes.get(size - 1);

        assertEquals(16, size);
        assertEquals(Coords.coords(1.0000000000000004, 2.9999999999999996, 1), hole1.getCoords());
        assertEquals(Coords.coords(2.9999999999999996, 1.0000000000000004, 1), hole2.getCoords());
    }

    @Test
    public void findBoundaryHoleYZTest() {
        Sphere s1 = new Sphere(1);
        Sphere s2 = new Sphere(1);
        s1.setCoords(Coords.coords(1, 1, 1));
        s2.setCoords(Coords.coords(1, 3, 3));

        Bin bin = new Bin(100, 100, 100);
        bin.addSphere(s1);
        bin.addSphere(s2);
        HolesFinder holesFinder = HolesFinder.create(bin);

        Sphere newSphere = new Sphere(1);
        List<Hole> holes = holesFinder.findForSphere(newSphere);
        int size = holes.size();

        Hole hole1 = holes.get(size - 2);
        Hole hole2 = holes.get(size - 1);

        assertEquals(16, size);
        assertEquals(Coords.coords(1, 1.0000000000000004, 2.9999999999999996), hole1.getCoords());
        assertEquals(Coords.coords(1, 2.9999999999999996, 1.0000000000000004), hole2.getCoords());
    }

    @Test
    public void findBoundaryHoleXZTest() {
        Sphere s1 = new Sphere(1);
        Sphere s2 = new Sphere(1);
        s1.setCoords(Coords.coords(1, 1, 1));
        s2.setCoords(Coords.coords(3, 1, 3));

        Bin bin = new Bin(100, 100, 100);
        bin.addSphere(s1);
        bin.addSphere(s2);
        HolesFinder holesFinder = HolesFinder.create(bin);

        Sphere newSphere = new Sphere(1);
        List<Hole> holes = holesFinder.findForSphere(newSphere);
        int size = holes.size();

        Hole hole1 = holes.get(size - 2);
        Hole hole2 = holes.get(size - 1);

        assertEquals(16, size);
        assertEquals(Coords.coords(1.0000000000000004, 1, 2.9999999999999996), hole1.getCoords());
        assertEquals(Coords.coords(2.9999999999999996, 1, 1.0000000000000004), hole2.getCoords());
    }

}
