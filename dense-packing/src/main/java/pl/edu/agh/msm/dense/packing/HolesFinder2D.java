package pl.edu.agh.msm.dense.packing;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.*;
import static pl.edu.agh.msm.dense.packing.Coords.coords;


public class HolesFinder2D extends HolesFinder {

    public HolesFinder2D(Bin bin) {
        super(bin);
    }


    @Override
    protected void determineCornerHolesIfExist() {
        Plane xz = bin.getPlanes().get(0);
        Plane yz = bin.getPlanes().get(1);
        int x1 = sphere.getR();
        int y1 = sphere.getR();
        int x2 = bin.getXSize() - x1;
        int y2 = bin.getYSize() - y1;

        List<Hole> possibleHoles = new ArrayList<>();
        possibleHoles.add(new Hole(Arrays.asList(xz, yz), coords(x1, y1)));
        possibleHoles.add(new Hole(Arrays.asList(xz, yz), coords(x2, y1)));
        possibleHoles.add(new Hole(Arrays.asList(xz, yz), coords(x1, y2)));
        possibleHoles.add(new Hole(Arrays.asList(xz, yz), coords(x2, y2)));

        addNotOverlappingHolesToSolutionHolesList(possibleHoles);
    }


    @Override
    protected void determineBoundaryHolesIfExists() {
        for (Plane p : bin.getPlanes()) {
            for (Sphere s : bin.getSpheres()) {
                if (possibleBoundaryCoordsExist(p, s)) {
                    determineNextBoundaryHole(p, s);
                }
            }
        }
    }


    private void determineNextBoundaryHole(Plane p, Sphere s) {
        List<Hole> possibleHoles = new ArrayList<>();
        int r1 = sphere.getR();
        int r2 = s.getR();
        if (p.getType() == Plane.Type.XZ) {
            double y = abs(r1 - p.getPosition());
            double sqrtValue = sqrt(pow(r1 + r2, 2) - pow(y - s.getCoords().getY(), 2));
            double x1 = sqrtValue + s.getCoords().getX();
            double x2 = -sqrtValue + s.getCoords().getX();
            possibleHoles.add(new Hole(Arrays.asList(p, s), Coords.coords(x1, y)));
            possibleHoles.add(new Hole(Arrays.asList(p, s), Coords.coords(x2, y)));
        } else {
            double x = abs(r1 - p.getPosition());
            double sqrtValue = sqrt(pow(r1 + r2, 2) - pow(x - s.getCoords().getX(), 2));
            double y1 = sqrtValue + s.getCoords().getY();
            double y2 = -sqrtValue + s.getCoords().getY();
            possibleHoles.add(new Hole(Arrays.asList(p, s), Coords.coords(x, y1)));
            possibleHoles.add(new Hole(Arrays.asList(p, s), Coords.coords(x, y2)));
        }
        addNotOverlappingHolesToSolutionHolesList(possibleHoles);
    }


    @Override
    protected void determineHolesFromSpheresIfExist() {
        int numberOfSpheresInBin = bin.getSpheres().size();
        for (int i = 0; i < numberOfSpheresInBin - 1; i++) {
            Sphere s1 = bin.getSpheres().get(i);
            for (int j = i + 1; j < numberOfSpheresInBin; j++) {
                Sphere s2 = bin.getSpheres().get(j);
                determineNextHoleFromSpheresIfExist(s1, s2);
            }
        }
    }


    private void determineNextHoleFromSpheresIfExist(Sphere s1, Sphere s2) {
        if (possibleCoordsFromSpheresExist(s1, s2)) {
            List<Hole> possibleHoles = determineAllPossibleHoles(s1, s2);
            addNotOverlappingHolesToSolutionHolesList(possibleHoles);
        }
    }


    private List<Hole> determineAllPossibleHoles(Sphere s1, Sphere s2) {
        double distanceBetweenMiddles = Utils.computeDistanceBetweenMiddles(s1, s2);
        Coords auxiliaryCoords = determineAuxiliaryCoords(s1, s2, distanceBetweenMiddles);
        double cosA = determineCosA(s1, s2, distanceBetweenMiddles);
        double sinA = sqrt(1 - pow(cosA, 2));

        double x1 = auxiliaryCoords.getX() * cosA - auxiliaryCoords.getY() * sinA + s1.getCoords().getX();
        double y1 = auxiliaryCoords.getX() * sinA + auxiliaryCoords.getY() * cosA + s1.getCoords().getY();
        double x2 = auxiliaryCoords.getX() * cosA + auxiliaryCoords.getY() * sinA + s1.getCoords().getX();
        double y2 = auxiliaryCoords.getX() * -sinA + auxiliaryCoords.getY() * cosA + s1.getCoords().getY();


        List<Hole> possibleHoles = new ArrayList<>();
        possibleHoles.add(new Hole(Arrays.asList(s1, s2), coords(x1, y1, bin.getZSize() > 1 ? sphere.getR() : 0)));
        possibleHoles.add(new Hole(Arrays.asList(s1, s2), coords(x2, y2, bin.getZSize() > 1 ? sphere.getR() : 0)));

        return possibleHoles;
    }


    protected Coords determineAuxiliaryCoords(Sphere c1, Sphere c2, double distanceBetweenMiddles) {
        int r1 = sphere.getR() + c1.getR();
        double auxiliaryX = r1 * (c2.getCoords().getX() - c1.getCoords().getX()) / distanceBetweenMiddles;
        double auxiliaryY = r1 * (c2.getCoords().getY() - c1.getCoords().getY()) / distanceBetweenMiddles;
        double auxiliaryZ = r1 * (c2.getCoords().getZ() - c1.getCoords().getZ()) / distanceBetweenMiddles;
        return coords(auxiliaryX, auxiliaryY, auxiliaryZ);
    }


    protected double determineCosA(Sphere c1, Sphere c2, double distanceBetweenMiddles) {
        int r1 = sphere.getR() + c1.getR();
        int r2 = sphere.getR() + c2.getR();
        return (pow(distanceBetweenMiddles, 2) + pow(r1, 2) - pow(r2, 2)) / (2 * distanceBetweenMiddles * r1);
    }

}
