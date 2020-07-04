package pl.edu.agh.msm.dense.packing.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.*;
import static pl.edu.agh.msm.dense.packing.model.Coords.coords;
import static pl.edu.agh.msm.dense.packing.model.Utils.norm;
import static pl.edu.agh.msm.dense.packing.model.Utils.vectorsSubtraction;


public class BoundaryHoleFinderHelper3D {

    private final Sphere sphere;


    public BoundaryHoleFinderHelper3D(Sphere sphere) {
        this.sphere = sphere;
    }


    public List<Hole> determineAllPossibleHoles(Sphere s, Plane p1, Plane p2) {
        if (p1.getType() == Plane.Type.YZ) {
            if (p2.getType() == Plane.Type.XZ) {
                return determineHolesInAxisZ(s, p1, p2);
            } else { // P2 == XY
                return determineHolesInAxisY(s, p1, p2);
            }
        } else if (p1.getType() == Plane.Type.XZ) {
            if (p2.getType() == Plane.Type.YZ) {
                return determineHolesInAxisZ(s, p2, p1);
            } else { // P2 == XY
                return determineHolesInAxisX(s, p1, p2);
            }
        } else { //P1 == XY
            if (p2.getType() == Plane.Type.YZ) {
                return determineHolesInAxisY(s, p2, p1);
            } else { // P2 == XZ
                return determineHolesInAxisX(s, p2, p1);
            }
        }
    }

    private List<Hole> determineHolesInAxisZ(Sphere s, Plane p1, Plane p2) {
        int r1 = sphere.getR();
        int r2 = s.getR();
        double x = abs(r1 - p1.getPosition());
        double y = abs(r1 - p2.getPosition());
        double sqrtValue = sqrt(pow(r1 + r2, 2) - pow(x - s.getCoords().getX(), 2) - pow(y - s.getCoords().getY(), 2));
        double z1 = sqrtValue + s.getCoords().getZ();
        double z2 = -sqrtValue + s.getCoords().getZ();
        List<Hole> possibleHoles = new ArrayList<>();
        possibleHoles.add(new Hole(Arrays.asList(p1, p2, s), coords(x, y, z1)));
        possibleHoles.add(new Hole(Arrays.asList(p1, p2, s), coords(x, y, z2)));
        return possibleHoles;
    }


    private List<Hole> determineHolesInAxisY(Sphere s, Plane p1, Plane p2) {
        int r1 = sphere.getR();
        int r2 = s.getR();
        double x = abs(r1 - p1.getPosition());
        double z = abs(r1 - p2.getPosition());
        double sqrtValue = sqrt(pow(r1 + r2, 2) - pow(x - s.getCoords().getX(), 2) - pow(z - s.getCoords().getZ(), 2));
        double y1 = sqrtValue + s.getCoords().getY();
        double y2 = -sqrtValue + s.getCoords().getY();
        List<Hole> possibleHoles = new ArrayList<>();
        possibleHoles.add(new Hole(Arrays.asList(p1, p2, s), coords(x, y1, z)));
        possibleHoles.add(new Hole(Arrays.asList(p1, p2, s), coords(x, y2, z)));
        return possibleHoles;
    }


    private List<Hole> determineHolesInAxisX(Sphere s, Plane p1, Plane p2) {
        int r1 = sphere.getR();
        int r2 = s.getR();
        double y = abs(r1 - p1.getPosition());
        double z = abs(r1 - p2.getPosition());
        double sqrtValue = sqrt(pow(r1 + r2, 2) - pow(y - s.getCoords().getY(), 2) - pow(z - s.getCoords().getZ(), 2));
        double x1 = sqrtValue + s.getCoords().getX();
        double x2 = -sqrtValue + s.getCoords().getX();
        List<Hole> possibleHoles = new ArrayList<>();
        possibleHoles.add(new Hole(Arrays.asList(p1, p2, s), coords(x1, y, z)));
        possibleHoles.add(new Hole(Arrays.asList(p1, p2, s), coords(x2, y, z)));
        return possibleHoles;
    }


    public List<Hole> determineAllPossibleHoles(Plane p, Sphere s1, Sphere s2) {
        if (p.getType() == Plane.Type.XY) {
            return determineHolesForXY(p, s1, s2);
        } else if (p.getType() == Plane.Type.XZ) {
            return determineHolesForXZ(p, s1, s2);
        } else {
            return determineHolesForYZ(p, s1, s2);
        }
    }


    private List<Hole> determineHolesForXY(Plane p, Sphere s1, Sphere s2) {
        int r1 = s1.getR() + sphere.getR();
        int r2 = s2.getR() + sphere.getR();

        Coords temp1 = vectorsSubtraction(s2.getCoords(), s1.getCoords());
        double d = norm(temp1);
        Coords e_x = coords(temp1.getX() / d, temp1.getY() / d, temp1.getZ() / d);
        Coords e_y = coords(-e_x.getY(), e_x.getX(), 0);
        double x = (pow(r1, 2) - pow(r2, 2) + pow(d, 2)) / (2 * d);
        double y = sqrt(pow(r1, 2) - pow(x, 2));

        double x1 = s1.getCoords().getX() + x * e_x.getX() + y * e_y.getX();
        double y1 = s1.getCoords().getY() + x * e_x.getY() + y * e_y.getY();
        double x2 = s1.getCoords().getX() + x * e_x.getX() - y * e_y.getX();
        double y2 = s1.getCoords().getY() + x * e_x.getY() - y * e_y.getY();
        double z = abs(sphere.getR() - p.getPosition());

        List<Hole> possibleHoles = new ArrayList<>();
        possibleHoles.add(new Hole(Arrays.asList(p, s1, s2), coords(x1, y1, z)));
        possibleHoles.add(new Hole(Arrays.asList(p, s1, s2), coords(x2, y2, z)));
        return possibleHoles;
    }


    private List<Hole> determineHolesForXZ(Plane p, Sphere s1, Sphere s2) {
        int r1 = s1.getR() + sphere.getR();
        int r2 = s2.getR() + sphere.getR();

        Coords temp1 = vectorsSubtraction(s2.getCoords(), s1.getCoords());
        double d = norm(temp1);
        Coords e_x = coords(temp1.getX() / d, temp1.getY() / d, temp1.getZ() / d);
        Coords e_z = coords(-e_x.getZ(), 0, e_x.getX());
        double x = (pow(r1, 2) - pow(r2, 2) + pow(d, 2)) / (2 * d);
        double z = sqrt(pow(r1, 2) - pow(x, 2));

        double x1 = s1.getCoords().getX() + x * e_x.getX() + z * e_z.getX();
        double z1 = s1.getCoords().getZ() + x * e_x.getZ() + z * e_z.getZ();
        double x2 = s1.getCoords().getX() + x * e_x.getX() - z * e_z.getX();
        double z2 = s1.getCoords().getZ() + x * e_x.getZ() - z * e_z.getZ();
        double y = abs(sphere.getR() - p.getPosition());

        List<Hole> possibleHoles = new ArrayList<>();
        possibleHoles.add(new Hole(Arrays.asList(p, s1, s2), coords(x1, y, z1)));
        possibleHoles.add(new Hole(Arrays.asList(p, s1, s2), coords(x2, y, z2)));
        return possibleHoles;
    }


    private List<Hole> determineHolesForYZ(Plane p, Sphere s1, Sphere s2) {
        int r1 = s1.getR() + sphere.getR();
        int r2 = s2.getR() + sphere.getR();

        Coords temp1 = vectorsSubtraction(s2.getCoords(), s1.getCoords());
        double d = norm(temp1);
        Coords e_y = coords(temp1.getX() / d, temp1.getY() / d, temp1.getZ() / d);
        Coords e_z = coords(0, -e_y.getZ(), e_y.getY());
        double y = (pow(r1, 2) - pow(r2, 2) + pow(d, 2)) / (2 * d);
        double z = sqrt(pow(r1, 2) - pow(y, 2));

        double y1 = s1.getCoords().getY() + y * e_y.getY() + z * e_z.getY();
        double z1 = s1.getCoords().getZ() + y * e_y.getZ() + z * e_z.getZ();
        double y2 = s1.getCoords().getY() + y * e_y.getY() - z * e_z.getY();
        double z2 = s1.getCoords().getZ() + y * e_y.getZ() - z * e_z.getZ();
        double x = abs(sphere.getR() - p.getPosition());

        List<Hole> possibleHoles = new ArrayList<>();
        possibleHoles.add(new Hole(Arrays.asList(p, s1, s2), coords(x, y1, z1)));
        possibleHoles.add(new Hole(Arrays.asList(p, s1, s2), coords(x, y2, z2)));
        return possibleHoles;
    }
}
