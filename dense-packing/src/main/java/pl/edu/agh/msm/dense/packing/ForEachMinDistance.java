package pl.edu.agh.msm.dense.packing;

public class ForEachMinDistance implements MinDistance {
    @Override
    public double compute(Bin bin, Hole hole, Circle circle) {
        double minDistance = Double.MAX_VALUE;
        for (Circle circle1 : bin.getCircles()) {
            if (circle1 == hole.getParentCircles().get(0) || circle1 == hole.getParentCircles().get(1)) {
                continue;
            }
            double currentDistance = Utils.computeSquaredDistance(circle1.getCoords(), hole.getCoords());
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
            }
        }
        return minDistance;
    }
}
