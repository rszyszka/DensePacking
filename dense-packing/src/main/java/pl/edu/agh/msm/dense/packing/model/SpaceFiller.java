package pl.edu.agh.msm.dense.packing.model;

import pl.edu.agh.msm.core.Space;

import static java.lang.Math.*;


public class SpaceFiller {

    private final Space space;
    private int numberOfFilledCells;


    public SpaceFiller(Space space) {
        this.space = space;
        numberOfFilledCells = 0;
    }


    public void fillWithAllSpheres(Bin bin) {
        int id = 1;
        for (Sphere sphere : bin.getSpheres()) {
            fillWithSphere(sphere, id);
            id++;
        }
    }


    public void fillWithSphere(Sphere sphere, int id) {
        int xStart = (int) floor(sphere.getCoords().getX()) - sphere.getR();
        int xEnd = (int) ceil(sphere.getCoords().getX()) + sphere.getR();
        int yStart = (int) floor(sphere.getCoords().getY()) - sphere.getR();
        int yEnd = (int) ceil(sphere.getCoords().getY()) + sphere.getR();
        int zStart = space.getZSize() == 1 ? 0 : (int) floor(sphere.getCoords().getZ()) - sphere.getR();
        int zEnd = space.getZSize() == 1 ? 0 : (int) ceil(sphere.getCoords().getZ()) + sphere.getR();

        for (int i = xStart; i <= xEnd; i++) {
            for (int j = yStart; j <= yEnd; j++) {
                for (int k = zStart; k <= zEnd; k++) {
                    if (isInCircle(i, j, k, sphere)) {
                        try {
                            space.getCells()[i][j][k].setId(id);
                        } catch (ArrayIndexOutOfBoundsException ignored) {
                        }
                        numberOfFilledCells++;
                    }
                }
            }
        }
    }

    public int getNumberOfFilledCells() {
        return numberOfFilledCells;
    }

    private boolean isInCircle(int x, int y, int z, Sphere sphere) {
        return pow(x - sphere.getCoords().getX(), 2) + pow(y - sphere.getCoords().getY(), 2) + pow(z - sphere.getCoords().getZ(), 2) <= pow(sphere.getR(), 2);
    }

}
