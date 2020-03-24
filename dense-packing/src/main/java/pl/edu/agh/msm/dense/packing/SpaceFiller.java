package pl.edu.agh.msm.dense.packing;

import pl.edu.agh.msm.core.Space;

import static java.lang.Math.*;


public class SpaceFiller {

    private Space space;
    private int numberOfFilledCells;


    public SpaceFiller(Space space) {
        this.space = space;
        numberOfFilledCells = 0;
    }


    public void fillWithAllCircles(Bin bin) {
        bin.getCircles().forEach(this::fillWithCircle);
    }


    public void fillWithCircle(Circle circle) {
        int xStart = (int) floor(circle.getCoords().getX()) - circle.getR();
        int xEnd = (int) ceil(circle.getCoords().getX()) + circle.getR();
        int yStart = (int) floor(circle.getCoords().getY()) - circle.getR();
        int yEnd = (int) ceil(circle.getCoords().getY()) + circle.getR();

        for (int i = xStart; i <= xEnd; i++) {
            for (int j = yStart; j <= yEnd; j++) {
                if (isInCircle(i, j, circle)) {
                    space.getCells()[i][j][0].setId(1);
                    numberOfFilledCells++;
                }
            }
        }
    }

    public int getNumberOfFilledCells() {
        return numberOfFilledCells;
    }

    private boolean isInCircle(int x, int y, Circle circle) {
        return pow(x - circle.getCoords().getX(), 2) + pow(y - circle.getCoords().getY(), 2) <= pow(circle.getR(), 2);
    }

}
