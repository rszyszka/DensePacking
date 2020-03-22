package pl.edu.agh.msm.dense.packing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static pl.edu.agh.msm.dense.packing.Coords.coords;


public class HolesFinder {

    private Bin bin;
    private Circle circle;
    private List<Hole> solutionHoles;


    public HolesFinder(Bin bin) {
        this.bin = bin;
        solutionHoles = new ArrayList<>();
    }


    public Hole findHoleWithMaximumDegree() {
        return new Hole(null, coords(0.0, 0.0));
    }


    public List<Hole> findForCircle(Circle circle) {
        this.circle = circle;
        int numberOfCirclesInBin = bin.getCircles().size();
        for (int i = 0; i < numberOfCirclesInBin - 1; i++) {
            Circle c1 = bin.getCircles().get(i);
            for (int j = i + 1; j < numberOfCirclesInBin; j++) {
                Circle c2 = bin.getCircles().get(j);
                determineNextHoleIfExist(circle, c1, c2);
            }
        }
        return solutionHoles;
    }


    private void determineNextHoleIfExist(Circle circle, Circle c1, Circle c2) {
        if (possibleCoordsExist(circle, c1, c2)) {
            List<Hole> possibleHoles = determineAllPossibleHoles(c1, c2);
            addNotOverlappingHolesToSolutionHolesList(possibleHoles);
        }
    }


    private boolean possibleCoordsExist(Circle circle, Circle c1, Circle c2) {
        return 2 * circle.getR() >= Utils.computeDistanceBetweenCircuits(c1, c2);
    }


    private List<Hole> determineAllPossibleHoles(Circle c1, Circle c2) {
        double distanceBetweenMiddles = Utils.computeDistanceBetweenMiddles(c1, c2);
        Coords auxiliaryCoords = determineAuxiliaryCoords(c1, c2, distanceBetweenMiddles);
        double cosA = determineCosA(c1, c2, distanceBetweenMiddles);
        double sinA = sqrt(1 - pow(cosA, 2));

        double x1 = auxiliaryCoords.getX() * cosA - auxiliaryCoords.getY() * sinA + c1.getCoords().getX();
        double y1 = auxiliaryCoords.getX() * sinA + auxiliaryCoords.getY() * cosA + c1.getCoords().getY();
        double x2 = auxiliaryCoords.getX() * cosA + auxiliaryCoords.getY() * sinA + c1.getCoords().getX();
        double y2 = auxiliaryCoords.getY() * cosA - auxiliaryCoords.getX() * sinA + c1.getCoords().getY();

        List<Hole> possibleHoles = new ArrayList<>();
        possibleHoles.add(new Hole(Arrays.asList(c1, c2), coords(x1, y1)));
        possibleHoles.add(new Hole(Arrays.asList(c1, c2), coords(x2, y2)));

        return possibleHoles;
    }


    private void addNotOverlappingHolesToSolutionHolesList(List<Hole> possibleHoles) {
        createAndAddSolutionHoleIfCircleCoordsValid(possibleHoles.get(0));
        createAndAddSolutionHoleIfCircleCoordsValid(possibleHoles.get(1));
    }


    private void createAndAddSolutionHoleIfCircleCoordsValid(Hole possibleHole) {
        Circle testCircle = new Circle(circle.getR());
        testCircle.setCoords(possibleHole.getCoords());
        if (Utils.isCircleAbleToBePlacedInBin(testCircle, bin)) {
            calculateHoleDegree(possibleHole);
            solutionHoles.add(possibleHole);
        }
    }


    private void calculateHoleDegree(Hole hole) {
        double minDistance = Double.MAX_VALUE;
        for (Circle circle : bin.getCircles()) {
            if (circle == hole.getParentCircles().get(0) || circle == hole.getParentCircles().get(1)) {
                continue;
            }
            double currentDistance = Utils.computeDistance(circle.getCoords(), hole.getCoords());
            if (currentDistance < minDistance) {
                minDistance = currentDistance;
            }
        }
        hole.setDegree(1.0 - (minDistance / circle.getR()));
    }


    private Coords determineAuxiliaryCoords(Circle c1, Circle c2, double distanceBetweenMiddles) {
        int r1 = circle.getR() + c1.getR();
        double auxiliaryX = r1 * (c2.getCoords().getX() - c1.getCoords().getX()) / distanceBetweenMiddles;
        double auxiliaryY = r1 * (c2.getCoords().getY() - c1.getCoords().getY()) / distanceBetweenMiddles;
        return coords(auxiliaryX, auxiliaryY);
    }


    private double determineCosA(Circle c1, Circle c2, double distanceBetweenMiddles) {
        int r1 = circle.getR() + c1.getR();
        int r2 = circle.getR() + c2.getR();
        return (pow(distanceBetweenMiddles, 2) + pow(r1, 2) - pow(r2, 2)) / (2 * distanceBetweenMiddles * r1);
    }

}
