package pl.edu.agh.msm.dense.packing.model;

import org.junit.jupiter.api.Test;
import pl.edu.agh.msm.core.Space;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static pl.edu.agh.msm.dense.packing.model.Coords.coords;

public class SpaceFillerTest {

    @Test
    public void shouldFillSpaceWithAllCircles() {
        int[][] expected = new int[][]{
                {0, 1, 0, 0, 0, 0, 0, 0, 0},
                {1, 1, 1, 0, 1, 0, 0, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        Space space = new Space(9, 9, 1);
        SpaceFiller filler = new SpaceFiller(space);
        Sphere c1 = new Sphere(3);
        c1.setCoords(coords(4, 4));
        Sphere c2 = new Sphere(1);
        c2.setCoords(coords(1, 1));
        Bin bin = new Bin(space.getXSize(), space.getYSize());
        bin.addSphere(c1);
        bin.addSphere(c2);

        filler.fillWithAllCircles(bin);

        assertArrayEquals(expected, retrieveResult(space));
    }

    @Test
    public void shouldFillSpaceWithCircle() {
        int[][] expected = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 1, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0}
        };
        Space space = new Space(9, 9, 1);
        SpaceFiller filler = new SpaceFiller(space);
        Sphere sphere = new Sphere(3);
        sphere.setCoords(coords(4, 4));

        filler.fillWithCircle(sphere);

        assertArrayEquals(expected, retrieveResult(space));
    }


    private int[][] retrieveResult(Space space) {
        int[][] result = new int[space.getXSize()][space.getYSize()];
        for (int i = 0; i < space.getXSize(); i++) {
            for (int j = 0; j < space.getYSize(); j++) {
                result[i][j] = space.getCells()[i][j][0].getId();
            }
        }
        return result;
    }

}
