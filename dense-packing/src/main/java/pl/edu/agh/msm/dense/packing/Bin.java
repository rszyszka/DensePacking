package pl.edu.agh.msm.dense.packing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Bin {
    private final int xSize;
    private final int ySize;
    private final int zSize;
    private final List<Sphere> spheres;
    private final MinDistance minDistance;

    public Bin(int xSize, int ySize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = 1;
        spheres = new ArrayList<>();
        minDistance = new ForEachMinDistance();
    }

    public Bin(int xSize, int ySize, int zSize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;
        spheres = new ArrayList<>();
        minDistance = new ForEachMinDistance();
    }

    public boolean addSphere(Sphere sphere) {
        if (Utils.isSphereAbleToBePlacedInBin(sphere, this)) {
            spheres.add(sphere);
            return true;
        }
        return false;
    }

    public MinDistance getMinDistance() {
        return minDistance;
    }

    public int getNumberOfCirclesPacked() {
        return spheres.size();
    }

    public int getXSize() {
        return xSize;
    }

    public int getYSize() {
        return ySize;
    }

    public int getZSize() {
        return zSize;
    }

    public List<Sphere> getSpheres() {
        return Collections.unmodifiableList(spheres);
    }
}
