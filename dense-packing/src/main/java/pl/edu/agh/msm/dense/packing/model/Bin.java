package pl.edu.agh.msm.dense.packing.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class Bin {
    private final int xSize;
    private final int ySize;
    private final int zSize;
    private final List<Plane> planes;
    private final List<Sphere> spheres;

    public Bin(int xSize, int ySize) {
        this(xSize, ySize, 1);
    }

    public Bin(int xSize, int ySize, int zSize) {
        this.xSize = xSize;
        this.ySize = ySize;
        this.zSize = zSize;
        planes = new ArrayList<>();
        spheres = new ArrayList<>();
        addAllBinPlanes();
    }

    private void addAllBinPlanes() {
        planes.add(new Plane(Plane.Type.YZ, 0));
        planes.add(new Plane(Plane.Type.YZ, xSize));
        planes.add(new Plane(Plane.Type.XZ, 0));
        planes.add(new Plane(Plane.Type.XZ, ySize));
        if (zSize > 1) {
            planes.add(new Plane(Plane.Type.XY, 0));
            planes.add(new Plane(Plane.Type.XY, zSize));
        }
    }

    public boolean addSphere(Sphere sphere) {
        if (Utils.isSphereAbleToBePlacedInBin(sphere, this)) {
            spheres.add(sphere);
            return true;
        }
        return false;
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

    public List<Plane> getPlanes() {
        return Collections.unmodifiableList(planes);
    }
}
