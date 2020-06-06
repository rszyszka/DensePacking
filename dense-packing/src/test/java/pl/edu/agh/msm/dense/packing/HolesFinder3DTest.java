package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class HolesFinder3DTest {

    @Test
    public void find3DHoleTest() {
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
        HolesFinder3D holesFinder = new HolesFinder3D(bin);

        Sphere newSphere = new Sphere(5);
        List<Hole> holes = holesFinder.findForSphere(newSphere);
        Hole hole = holes.get(0);

        assertEquals(1, holes.size());
        assertEquals(Coords.coords(10.0, 8.75, 12.806247497997997), hole.getCoords());
    }

}
