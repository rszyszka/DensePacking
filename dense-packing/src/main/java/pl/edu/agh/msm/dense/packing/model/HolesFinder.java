package pl.edu.agh.msm.dense.packing.model;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static java.lang.Math.pow;
import static pl.edu.agh.msm.dense.packing.model.Coords.coords;


public abstract class HolesFinder {

    public static double PENALTY_VALUE;
    public static PenaltyType penaltyType;
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


    public List<Hole> findForSphere(Sphere sphere) {
        this.sphere = sphere;
        solutionHoles = new ArrayList<>();
        determineCornerHolesIfExist();
        determineBoundaryHolesIfExists();
        determineHolesFromSpheresIfExist();
        return solutionHoles;
    }


    protected boolean possibleCoordsFromSpheresExist(Sphere s1, Sphere s2) {
        return 2 * sphere.getR() >= Utils.computeDistanceBetweenCircuits(s1, s2);
    }


    protected boolean possibleBoundaryCoordsExist(Plane p, Sphere s) {
        return 2 * sphere.getR() >= p.computeDistance(s);
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
        boolean penalty = determineIfPenaltyShouldBeApplied(hole);
        double degree = 1.0 - (this.minDistance.compute(hole) / sphere.getR());
        hole.setDegree(penalty ? degree - PENALTY_VALUE : degree);
    }


    protected abstract void determineCornerHolesIfExist();


    protected abstract void determineBoundaryHolesIfExists();


    protected abstract void determineHolesFromSpheresIfExist();


    protected Coords determineAuxiliaryCoords(Sphere s1, Sphere s2, double distanceBetweenMiddles) {
        int r1 = sphere.getR() + s1.getR();
        double auxiliaryX = r1 * (s2.getCoords().getX() - s1.getCoords().getX()) / distanceBetweenMiddles;
        double auxiliaryY = r1 * (s2.getCoords().getY() - s1.getCoords().getY()) / distanceBetweenMiddles;
        double auxiliaryZ = r1 * (s2.getCoords().getZ() - s1.getCoords().getZ()) / distanceBetweenMiddles;
        return coords(auxiliaryX, auxiliaryY, auxiliaryZ);
    }


    protected double determineCosA(Sphere c1, Sphere c2, double distanceBetweenMiddles) {
        int r1 = sphere.getR() + c1.getR();
        int r2 = sphere.getR() + c2.getR();
        return (pow(distanceBetweenMiddles, 2) + pow(r1, 2) - pow(r2, 2)) / (2 * distanceBetweenMiddles * r1);
    }


    private boolean determineIfPenaltyShouldBeApplied(Hole hole) {
        switch (penaltyType) {
            case GLOBAL:
                for (Object o : hole.getParentObjects()) {
                    if (o instanceof Plane) {
                        return true;
                    }
                }
                break;
            case ALL_EXCEPT_TOP:
                for (Object o : hole.getParentObjects()) {
                    if (o instanceof Plane && (((Plane) o).getPosition() != 0 || ((Plane) o).getType() != Plane.Type.XZ)) {
                        return true;
                    }
                }
                break;
            case ALL_EXCEPT_BOT:
                for (Object o : hole.getParentObjects()) {
                    if (o instanceof Plane && (((Plane) o).getPosition() != 100 || ((Plane) o).getType() != Plane.Type.XZ)) {
                        return true;
                    }
                }
                break;
        }
        return false;
    }

}
