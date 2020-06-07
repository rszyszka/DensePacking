package pl.edu.agh.msm.dense.packing;

import java.util.Comparator;
import java.util.List;

import static java.lang.Math.sqrt;


public abstract class HolesFinder {

    protected Bin bin;
    protected Sphere sphere;
    protected List<Hole> solutionHoles;
    protected MinDistance minDistance;


    public static HolesFinder create(Bin bin) {
        if (bin.getZSize() > 1) {
            return new HolesFinder3D(bin);
        } else {
            return new HolesFinder2D(bin);
        }
    }

    protected HolesFinder(Bin bin) {
        this.bin = bin;
        minDistance = new ForEachMinDistance(bin);
    }


    public Hole findHoleWithMaximumDegree() {
        return solutionHoles.stream()
                .max(Comparator.comparingDouble(Hole::getDegree))
                .orElse(null);
    }


    public abstract List<Hole> findForSphere(Sphere sphere);


    protected boolean possibleCoordsFromSpheresExist(Sphere s1, Sphere s2) {
        return 2 * sphere.getR() >= Utils.computeDistanceBetweenCircuits(s1, s2);
    }


    protected boolean possibleBoundaryCoordsExist(Plane p, Sphere s) {
        return 2 * sphere.getR() >= p.computeDistance(s.getCoords());
    }


    protected void addNotOverlappingHolesToSolutionHolesList(List<Hole> possibleHoles) {
        possibleHoles.forEach(this::createAndAddSolutionHoleIfSphereCoordsValid);
    }


    private void createAndAddSolutionHoleIfSphereCoordsValid(Hole possibleHole) {
        Sphere testSphere = new Sphere(sphere.getR());
        testSphere.setCoords(possibleHole.getCoords());
        if (Utils.isSphereAbleToBePlacedInBin(testSphere, bin)) {
            calculateHoleDegree(possibleHole);
            solutionHoles.add(possibleHole);
        }
    }


    private void calculateHoleDegree(Hole hole) {
        double minDistance = this.minDistance.compute(hole);
        hole.setDegree(1.0 - (sqrt(minDistance) / sphere.getR()));
    }

}
