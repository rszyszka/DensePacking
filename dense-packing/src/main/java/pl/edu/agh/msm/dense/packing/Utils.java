package pl.edu.agh.msm.dense.packing;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


public class Utils {

    public static boolean isCircleAbleToBePlacedInBin(Circle circle, Bin bin) {
        if (!isCircleNotOverlappingBin(circle, bin)) {
            return false;
        }
        for (Circle c : bin.getCircles()) {
            if (!Utils.areCirclesNotOverlapping(circle, c)) {
                return false;
            }
        }
        return true;
    }

    public static boolean areCirclesNotOverlapping(Circle c1, Circle c2) {
        return computeDistanceBetweenCircuits(c1, c2) >= 0.0;
    }


    public static boolean isCircleNotOverlappingBin(Circle c1, Bin bin) {
        boolean notOverlappingWidth = isNotOverlappingSize(c1.getCoords().getX(), c1.getR(), bin.getXSize());
        boolean notOverlappingHeight = isNotOverlappingSize(c1.getCoords().getY(), c1.getR(), bin.getYSize());

        return notOverlappingWidth && notOverlappingHeight;
    }


    public static double computeDistanceBetweenCircuits(Circle c1, Circle c2) {
        return computeDistanceBetweenMiddles(c1, c2) - c1.getR() - c2.getR();
    }


    public static double computeDistanceBetweenMiddles(Circle c1, Circle c2) {
        Coords c1Coords = c1.getCoords();
        Coords c2Coords = c2.getCoords();
        double distance = sqrt(pow(c1Coords.getX() - c2Coords.getX(), 2) + pow(c1Coords.getY() - c2Coords.getY(), 2));
        return BigDecimal.valueOf(distance).setScale(10, RoundingMode.HALF_UP).doubleValue();
    }


    private static boolean isNotOverlappingSize(double coordsPart, int r, int size) {
        coordsPart = BigDecimal.valueOf(coordsPart).setScale(10, RoundingMode.HALF_UP).doubleValue();
        return coordsPart - r >= 0 && size - coordsPart - r >= 0.0;
    }

}
