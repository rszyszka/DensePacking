package pl.edu.agh.msm.dense.packing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Bin {
    private int xSize;
    private int ySize;
    private int zSize;
    private List<Sphere> spheres;
    private MinDistance minDistance;

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

    public boolean addCircle(Sphere sphere) {
        if (Utils.isCircleAbleToBePlacedInBin(sphere, this)) {
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
