package pl.edu.agh.msm.dense.packing;

public class ForEachMinDistance implements MinDistance {
    @Override
    public double compute(Bin bin, Hole hole, Sphere sphere) {
        double minDistance = Double.MAX_VALUE;
        for (Sphere sphere1 : bin.getSpheres()) {
            if (sphere1 == hole.getParentSpheres().get(0) || sphere1 == hole.getParentSpheres().get(1)) {
                continue;
            }
            double currentDistance = Utils.computeSquaredDistance(sphere1.getCoords(), hole.getCoords());
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
            }
        }
        return minDistance;
    }
}
