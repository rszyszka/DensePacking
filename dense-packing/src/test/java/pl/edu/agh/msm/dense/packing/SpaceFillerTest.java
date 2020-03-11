package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.api.Test;
import pl.edu.agh.msm.core.Space;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static pl.edu.agh.msm.dense.packing.Coords.coords;

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
        Circle c1 = new Circle(3);
        c1.setCoords(coords(4, 4));
        Circle c2 = new Circle(1);
        c2.setCoords(coords(1, 1));
        Bin bin = new Bin(space.getXSize(), space.getYSize());
        bin.addCircle(c1);
        bin.addCircle(c2);

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
        Circle circle = new Circle(3);
        circle.setCoords(coords(4, 4));

        filler.fillWithCircle(circle);

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
