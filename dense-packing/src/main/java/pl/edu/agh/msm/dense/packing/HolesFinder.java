package pl.edu.agh.msm.dense.packing;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static pl.edu.agh.msm.dense.packing.Coords.coords;


public class HolesFinder {

    private Bin bin;
    private Circle circle;
    private List<Coords> solutionCoordsList;


    public HolesFinder(Bin bin) {
        this.bin = bin;
        solutionCoordsList = new ArrayList<>();
    }


    public Coords findCoordsWithMaximumHoleDegree() {
        return null;
    }


    public List<Coords> findForCircle(Circle circle) {
        this.circle = circle;
        int numberOfCirclesInBin = bin.getCircles().size();
        for (int i = 0; i < numberOfCirclesInBin - 1; i++) {
            Circle c1 = bin.getCircles().get(i);
            for (int j = i + 1; j < numberOfCirclesInBin; j++) {
                Circle c2 = bin.getCircles().get(j);
                determineNextSolutionCoordsIfExist(circle, c1, c2);
            }
        }
        return solutionCoordsList;
    }


    private void determineNextSolutionCoordsIfExist(Circle circle, Circle c1, Circle c2) {
        if (possibleCoordsExist(circle, c1, c2)) {
            List<Coords> possibleCoordsList = determineAllPossibleCoords(c1, c2);
            addNotOverlappingCoordsToSolutionCoordsList(possibleCoordsList);
        }
    }


    private boolean possibleCoordsExist(Circle circle, Circle c1, Circle c2) {
        return 2 * circle.getR() >= Utils.computeDistanceBetweenCircuits(c1, c2);
    }


    private List<Coords> determineAllPossibleCoords(Circle c1, Circle c2) {
        List<Coords> possibleCoordsList = new ArrayList<>();

        double distanceBetweenMiddles = Utils.computeDistanceBetweenMiddles(c1, c2);
        Coords auxiliaryCoords = determineAuxiliaryCoords(c1, c2, distanceBetweenMiddles);
        double cosA = determineCosA(c1, c2, distanceBetweenMiddles);
        double sinA = sqrt(1 - pow(cosA, 2));

        double x1 = auxiliaryCoords.getX() * cosA - auxiliaryCoords.getY() * sinA + c1.getCoords().getX();
        double y1 = auxiliaryCoords.getX() * sinA + auxiliaryCoords.getY() * cosA + c1.getCoords().getY();
        double x2 = auxiliaryCoords.getX() * cosA + auxiliaryCoords.getY() * sinA + c1.getCoords().getX();
        double y2 = auxiliaryCoords.getY() * cosA - auxiliaryCoords.getX() * sinA + c1.getCoords().getY();

        possibleCoordsList.add(coords(x1, y1));
        possibleCoordsList.add(coords(x2, y2));

        return possibleCoordsList;
    }


    private void addNotOverlappingCoordsToSolutionCoordsList(List<Coords> possibleCoordsList) {
        Circle testCircle = new Circle(circle.getR());
        testCircle.setCoords(possibleCoordsList.get(0));
        if (Utils.isCircleAbleToBePlacedInBin(testCircle, bin)) {
            solutionCoordsList.add(testCircle.getCoords());
        }
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
