package pl.edu.agh.msm.dense.packing.model;


public class ForEachMinDistance implements MinDistance {

    private final Bin bin;

    public ForEachMinDistance(Bin bin) {
        this.bin = bin;
    }

    @Override
    public double compute(Hole hole) {
        double minDistance = determineMinDistanceToPlanes(hole);
        for (Sphere sphere : bin.getSpheres()) {
            if (hole.getParentObjects().contains(sphere)) {
                continue;
            }
            double currentDistance = Utils.computeDistanceBetweenCircuits(sphere, hole);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
            }
        }
        return minDistance;
    }


    private double determineMinDistanceToPlanes(Hole hole) {
        double minDistance = Double.MAX_VALUE;
        for (Plane plane : bin.getPlanes()) {
            if (hole.getParentObjects().contains(plane)) {
                continue;
            }
            double currentDistance = plane.computeDistance(hole);
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
            }
        }
        return minDistance;
    }

}
