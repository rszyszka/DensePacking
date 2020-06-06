package pl.edu.agh.msm.dense.packing;

import java.util.Comparator;
import java.util.List;

import static java.lang.Math.sqrt;


public abstract class HolesFinder {

    protected Bin bin;
    protected Sphere sphere;
    protected List<Hole> solutionHoles;


    public static HolesFinder create(Bin bin) {
        if (bin.getZSize() > 1) {
            return new HolesFinder3D(bin);
        } else {
            return new HolesFinder2D(bin);
        }
    }


    public Hole findHoleWithMaximumDegree() {
        return solutionHoles.stream()
                .max(Comparator.comparingDouble(Hole::getDegree))
                .orElse(null);
    }


    public abstract List<Hole> findForSphere(Sphere sphere);


    protected boolean possibleCoordsExist(Sphere sphere, Sphere s1, Sphere s2) {
        return 2 * sphere.getR() >= Utils.computeDistanceBetweenCircuits(s1, s2);
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
        double minDistance = bin.getMinDistance().compute(bin, hole, sphere);
        hole.setDegree(1.0 - (sqrt(minDistance) / sphere.getR()));
    }

}
