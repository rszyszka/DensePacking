package pl.edu.agh.msm.dense.packing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static pl.edu.agh.msm.dense.packing.Coords.coords;


public class HolesFinder {

    private Bin bin;
    private Sphere sphere;
    private List<Hole> solutionHoles;


    public HolesFinder(Bin bin) {
        this.bin = bin;
    }


    public Hole findHoleWithMaximumDegree() {
        return solutionHoles.stream()
                .max(Comparator.comparingDouble(Hole::getDegree))
                .orElse(null);
    }


    public List<Hole> findForCircle(Sphere sphere) {
        solutionHoles = new ArrayList<>();
        this.sphere = sphere;
        int numberOfSpheresInBin = bin.getSpheres().size();
        for (int i = 0; i < numberOfSpheresInBin - 1; i++) {
            Sphere s1 = bin.getSpheres().get(i);
            for (int j = i + 1; j < numberOfSpheresInBin; j++) {
                Sphere s2 = bin.getSpheres().get(j);
                determineNextHoleIfExist(sphere, s1, s2);
            }
        }
        return solutionHoles;
    }


    private void determineNextHoleIfExist(Sphere sphere, Sphere s1, Sphere s2) {
        if (possibleCoordsExist(sphere, s1, s2)) {
            List<Hole> possibleHoles = determineAllPossibleHoles(s1, s2);
            addNotOverlappingHolesToSolutionHolesList(possibleHoles);
        }
    }


    private boolean possibleCoordsExist(Sphere sphere, Sphere s1, Sphere s2) {
        return 2 * sphere.getR() >= Utils.computeDistanceBetweenCircuits(s1, s2);
    }


    private List<Hole> determineAllPossibleHoles(Sphere s1, Sphere s2) {
        double distanceBetweenMiddles = Utils.computeDistanceBetweenMiddles(s1, s2);
        Coords auxiliaryCoords = determineAuxiliaryCoords(s1, s2, distanceBetweenMiddles);
        double cosA = determineCosA(s1, s2, distanceBetweenMiddles);
        double sinA = sqrt(1 - pow(cosA, 2));

        double x1 = auxiliaryCoords.getX() * cosA - auxiliaryCoords.getY() * sinA + s1.getCoords().getX();
        double y1 = auxiliaryCoords.getX() * sinA + auxiliaryCoords.getY() * cosA + s1.getCoords().getY();
        double x2 = auxiliaryCoords.getX() * cosA + auxiliaryCoords.getY() * sinA + s1.getCoords().getX();
        double y2 = auxiliaryCoords.getY() * cosA - auxiliaryCoords.getX() * sinA + s1.getCoords().getY();

        List<Hole> possibleHoles = new ArrayList<>();
        possibleHoles.add(new Hole(Arrays.asList(s1, s2), coords(x1, y1)));
        possibleHoles.add(new Hole(Arrays.asList(s1, s2), coords(x2, y2)));

        return possibleHoles;
    }


    private void addNotOverlappingHolesToSolutionHolesList(List<Hole> possibleHoles) {
        createAndAddSolutionHoleIfSphereCoordsValid(possibleHoles.get(0));
        createAndAddSolutionHoleIfSphereCoordsValid(possibleHoles.get(1));
    }


    private void createAndAddSolutionHoleIfSphereCoordsValid(Hole possibleHole) {
        Sphere testSphere = new Sphere(sphere.getR());
        testSphere.setCoords(possibleHole.getCoords());
        if (Utils.isCircleAbleToBePlacedInBin(testSphere, bin)) {
            calculateHoleDegree(possibleHole);
            solutionHoles.add(possibleHole);
        }
    }


    private void calculateHoleDegree(Hole hole) {
        double minDistance = bin.getMinDistance().compute(bin, hole, sphere);
        hole.setDegree(1.0 - (sqrt(minDistance) / sphere.getR()));
    }


    private Coords determineAuxiliaryCoords(Sphere c1, Sphere c2, double distanceBetweenMiddles) {
        int r1 = sphere.getR() + c1.getR();
        double auxiliaryX = r1 * (c2.getCoords().getX() - c1.getCoords().getX()) / distanceBetweenMiddles;
        double auxiliaryY = r1 * (c2.getCoords().getY() - c1.getCoords().getY()) / distanceBetweenMiddles;
        return coords(auxiliaryX, auxiliaryY);
    }


    private double determineCosA(Sphere c1, Sphere c2, double distanceBetweenMiddles) {
        int r1 = sphere.getR() + c1.getR();
        int r2 = sphere.getR() + c2.getR();
        return (pow(distanceBetweenMiddles, 2) + pow(r1, 2) - pow(r2, 2)) / (2 * distanceBetweenMiddles * r1);
    }

}
