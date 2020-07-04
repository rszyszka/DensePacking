package pl.edu.agh.msm.dense.packing.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static pl.edu.agh.msm.dense.packing.model.Coords.coords;


public class Utils {

    public static boolean isSphereAbleToBePlacedInBin(Sphere sphere, Bin bin) {
        if (!isSphereNotOverlappingBin(sphere, bin)) {
            return false;
        }
        for (Sphere s : bin.getSpheres()) {
            if (!Utils.areSpheresNotOverlapping(sphere, s)) {
                return false;
            }
        }
        return true;
    }

    public static boolean areSpheresNotOverlapping(Sphere s1, Sphere s2) {
        return computeDistanceBetweenCircuits(s1, s2) >= 0.0;
    }


    public static boolean isSphereNotOverlappingBin(Sphere c1, Bin bin) {
        boolean notOverlappingWidth = isNotOverlappingSize(c1.getCoords().getX(), c1.getR(), bin.getXSize());
        boolean notOverlappingHeight = isNotOverlappingSize(c1.getCoords().getY(), c1.getR(), bin.getYSize());
        boolean notOverlappingDepth = bin.getZSize() == 1 || isNotOverlappingSize(c1.getCoords().getZ(), c1.getR(), bin.getZSize());


        return notOverlappingWidth && notOverlappingHeight && notOverlappingDepth;
    }


    public static double computeDistanceBetweenCircuits(Sphere s1, Sphere s2) {
        return computeDistanceBetweenMiddles(s1, s2) - s1.getR() - s2.getR();
    }


    public static double computeDistanceBetweenMiddles(Sphere s1, Sphere s2) {
        double distance = sqrt(computeSquaredDistance(s1.getCoords(), s2.getCoords()));
        return BigDecimal.valueOf(distance).setScale(10, RoundingMode.HALF_UP).doubleValue();
    }


    public static double computeSquaredDistance(Coords s1, Coords s2) {
        return pow(s1.getX() - s2.getX(), 2) + pow(s1.getY() - s2.getY(), 2) + pow(s1.getZ() - s2.getZ(), 2);
    }


    private static boolean isNotOverlappingSize(double coordsPart, int r, int size) {
        coordsPart = BigDecimal.valueOf(coordsPart).setScale(10, RoundingMode.HALF_UP).doubleValue();
        return coordsPart - r >= 0 && size - coordsPart - r >= 0.0;
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

}