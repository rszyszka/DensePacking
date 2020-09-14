package pl.edu.agh.msm.dense.packing.model;

import java.math.BigDecimal;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static pl.edu.agh.msm.dense.packing.model.Coords.coords;


public class Utils {

    public static boolean isSphereAbleToBePlacedInBin(Sphere sphere, Bin bin) {
        return !(isSphereOverlappingBin(sphere, bin) || isSphereOverlappingAnyOtherSphereInBin(sphere, bin));
    }


    public static boolean isSphereOverlappingBin(Sphere c1, Bin bin) {
        boolean overlappingWidth = isOverlappingSize(c1.getCoords().getX(), c1.getR(), bin.getXSize());
        boolean overlappingHeight = isOverlappingSize(c1.getCoords().getY(), c1.getR(), bin.getYSize());
        boolean overlappingDepth = bin.getZSize() > 1 && isOverlappingSize(c1.getCoords().getZ(), c1.getR(), bin.getZSize());

        return overlappingWidth || overlappingHeight || overlappingDepth;
    }


    public static boolean isOverlappingSize(double coordsPart, int r, int size) {
        coordsPart = roundUp(coordsPart, 10);
        return coordsPart - r < 0 || coordsPart + r > size;
    }

    public static boolean isSphereOverlappingAnyOtherSphereInBin(Sphere sphere, Bin bin) {
        for (Sphere s : bin.getSpheres()) {
            if (sphere.equals(s)) {
                continue;
            }
            if (Utils.areSpheresOverlapping(sphere, s)) {
                return true;
            }
        }
        return false;
    }


    public static boolean areSpheresOverlapping(Sphere s1, Sphere s2) {
        return computeDistanceBetweenCircuits(s1, s2) < 0;
    }


    public static double computeDistanceBetweenCircuits(Sphere s1, Sphere s2) {
        return computeDistanceBetweenMiddles(s1, s2) - s1.getR() - s2.getR();
    }


    public static double computeDistanceBetweenMiddles(Sphere s1, Sphere s2) {
        double distance = sqrt(computeSquaredDistance(s1.getCoords(), s2.getCoords()));
        return roundUp(distance, 10);
    }


    public static double computeSquaredDistance(Coords s1, Coords s2) {
        return pow(s1.getX() - s2.getX(), 2) + pow(s1.getY() - s2.getY(), 2) + pow(s1.getZ() - s2.getZ(), 2);
    }


    public static Coords vectorsSubtraction(Coords c1, Coords c2) {
        return coords(c1.getX() - c2.getX(), c1.getY() - c2.getY(), c1.getZ() - c2.getZ());
    }


    public static double norm(Coords p) {
        return sqrt(pow(p.getX(), 2) + pow(p.getY(), 2) + pow(p.getZ(), 2));
    }


    public static double dot(Coords c1, Coords c2) {
        return c1.getX() * c2.getX() + c1.getY() * c2.getY() + c1.getZ() * c2.getZ();
    }


    public static Coords cross(Coords c1, Coords c2) {
        double x = c1.getY() * c2.getZ() - c1.getZ() * c2.getY();
        double y = c1.getZ() * c2.getX() - c1.getX() * c2.getZ();
        double z = c1.getX() * c2.getY() - c1.getY() * c2.getX();
        return coords(x, y, z);
    }


    public static double roundUp(double value, int scale) {
        //FIXME: rounding makes results better but causes HUGE performance lose
        return BigDecimal.valueOf(value).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }

}
