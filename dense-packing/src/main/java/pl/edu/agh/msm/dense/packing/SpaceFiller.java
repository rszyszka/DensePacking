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
        bin.getSpheres().forEach(this::fillWithCircle);
    }


    public void fillWithCircle(Sphere sphere) {
        int xStart = (int) floor(sphere.getCoords().getX()) - sphere.getR();
        int xEnd = (int) ceil(sphere.getCoords().getX()) + sphere.getR();
        int yStart = (int) floor(sphere.getCoords().getY()) - sphere.getR();
        int yEnd = (int) ceil(sphere.getCoords().getY()) + sphere.getR();

        for (int i = xStart; i <= xEnd; i++) {
            for (int j = yStart; j <= yEnd; j++) {
                if (isInCircle(i, j, sphere)) {
                    space.getCells()[i][j][0].setId(1);
                    numberOfFilledCells++;
                }
            }
        }
    }

    public int getNumberOfFilledCells() {
        return numberOfFilledCells;
    }

    private boolean isInCircle(int x, int y, Sphere sphere) {
        return pow(x - sphere.getCoords().getX(), 2) + pow(y - sphere.getCoords().getY(), 2) <= pow(sphere.getR(), 2);
    }

}
