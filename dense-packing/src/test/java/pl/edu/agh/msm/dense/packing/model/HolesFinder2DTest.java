package pl.edu.agh.msm.dense.packing.model;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static pl.edu.agh.msm.dense.packing.model.Coords.coords;

public class HolesFinder2DTest {

    @Test
    void shouldFindBoundaryHolesCorrectly() {
        Bin bin = new Bin(100, 100);
        Sphere additionalSphere = new Sphere(9);
        additionalSphere.setCoords(coords(91, 9));
        Assumptions.assumeTrue(bin.addSphere(additionalSphere));
        HolesFinder2D holesFinder = new HolesFinder2D(bin);

        Sphere sphere = new Sphere(5);
        List<Hole> holes = holesFinder.findForSphere(sphere);

        assertEquals(5, holes.size());
        assertEquals(95, holes.get(3).getCoords().getX());
        assertEquals(5, holes.get(4).getCoords().getY());
    }
}
