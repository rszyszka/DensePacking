package pl.edu.agh.msm.dense.packing;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;
import static pl.edu.agh.msm.dense.packing.Coords.coords;


public class HolesFinder3D extends HolesFinder {

    public HolesFinder3D(Bin bin) {
        super(bin);
    }


    @Override
    protected void determineCornerHolesIfExist() {
        Plane xz = bin.getPlanes().get(0);
        Plane yz = bin.getPlanes().get(1);
        Plane xy = bin.getPlanes().get(2);
        int x1 = sphere.getR();
        int y1 = sphere.getR();
        int z1 = sphere.getR();
        int x2 = bin.getXSize() - x1;
        int y2 = bin.getYSize() - y1;
        int z2 = bin.getYSize() - z1;

        List<Hole> possibleHoles = new ArrayList<>();
        possibleHoles.add(new Hole(Arrays.asList(xz, yz, xy), coords(x1, y1, z1)));
        possibleHoles.add(new Hole(Arrays.asList(xz, yz, xy), coords(x1, y1, z2)));
        possibleHoles.add(new Hole(Arrays.asList(xz, yz, xy), coords(x1, y2, z1)));
        possibleHoles.add(new Hole(Arrays.asList(xz, yz, xy), coords(x1, y2, z2)));
        possibleHoles.add(new Hole(Arrays.asList(xz, yz, xy), coords(x2, y1, z1)));
        possibleHoles.add(new Hole(Arrays.asList(xz, yz, xy), coords(x2, y1, z2)));
        possibleHoles.add(new Hole(Arrays.asList(xz, yz, xy), coords(x2, y2, z1)));
        possibleHoles.add(new Hole(Arrays.asList(xz, yz, xy), coords(x2, y2, z2)));

        addNotOverlappingHolesToSolutionHolesList(possibleHoles);
    }


    @Override
    protected void determineBoundaryHolesIfExists() {

    }


    @Override
    protected void determineHolesFromSpheresIfExist() {
        int numberOfSpheresInBin = bin.getSpheres().size();
        for (int i = 0; i < numberOfSpheresInBin - 2; i++) {
            Sphere s1 = bin.getSpheres().get(i);
            for (int j = i + 1; j < numberOfSpheresInBin - 1; j++) {
                Sphere s2 = bin.getSpheres().get(j);
                for (int k = j + 1; k < numberOfSpheresInBin; k++) {
                    Sphere s3 = bin.getSpheres().get(k);
                    determineNextHoleIfExist(s1, s2, s3);
                }
            }
        }
    }


    private void determineNextHoleIfExist(Sphere s1, Sphere s2, Sphere s3) {
        if (possibleCoordsFromSpheresExist(s1, s2) && possibleCoordsFromSpheresExist(s2, s3) && possibleCoordsFromSpheresExist(s1, s3)) {
            List<Hole> possibleHoles = determineAllPossibleHoles(s1, s2, s3);
            addNotOverlappingHolesToSolutionHolesList(possibleHoles);
        }
    }


    private List<Hole> determineAllPossibleHoles(Sphere s1, Sphere s2, Sphere s3) {
        int r1 = s1.getR() + sphere.getR();
        int r2 = s2.getR() + sphere.getR();
        int r3 = s3.getR() + sphere.getR();

        Coords temp1 = vectorsSubtraction(s2.getCoords(), s1.getCoords());
        double d = norm(temp1);
        Coords e_x = Coords.coords(temp1.getX() / d, temp1.getY() / d, temp1.getZ() / d);

        Coords temp2 = vectorsSubtraction(s3.getCoords(), s1.getCoords());
        double i = dot(e_x, temp2);
        Coords temp3 = vectorsSubtraction(temp2, Coords.coords(i * e_x.getX(), i * e_x.getY(), i * e_x.getZ()));
        Coords e_y = Coords.coords(temp3.getX() / norm(temp3), temp3.getY() / norm(temp3), temp3.getZ() / norm(temp3));
        Coords e_z = cross(e_x, e_y);
        double j = dot(e_y, temp2);

        double x = (pow(r1, 2) - pow(r2, 2) + pow(d, 2)) / (2 * d);
        double y = (pow(r1, 2) - pow(r3, 2) + pow(i, 2) + pow(j, 2) - 2 * i * x) / (2 * j);
        double z = sqrt(pow(r1, 2) - pow(x, 2) - pow(y, 2));

        double x1 = s1.getCoords().getX() + x * e_x.getX() + y * e_y.getX() + z * e_z.getX();
        double y1 = s1.getCoords().getY() + x * e_x.getY() + y * e_y.getY() + z * e_z.getY();
        double z1 = s1.getCoords().getZ() + x * e_x.getZ() + y * e_y.getZ() + z * e_z.getZ();

        double x2 = s1.getCoords().getX() + x * e_x.getX() + y * e_y.getX() - z * e_z.getX();
        double y2 = s1.getCoords().getY() + x * e_x.getY() + y * e_y.getY() - z * e_z.getY();
        double z2 = s1.getCoords().getZ() + x * e_x.getZ() + y * e_y.getZ() - z * e_z.getZ();

        List<Hole> possibleHoles = new ArrayList<>();
        possibleHoles.add(new Hole(Arrays.asList(s1, s2, s3), coords(x1, y1, z1)));
        possibleHoles.add(new Hole(Arrays.asList(s1, s2, s3), coords(x2, y2, z2)));

        return possibleHoles;
    }


    private Coords vectorsSubtraction(Coords c1, Coords c2) {
        return Coords.coords(c1.getX() - c2.getX(), c1.getY() - c2.getY(), c1.getZ() - c2.getZ());
    }


    private double norm(Coords p) {
        return sqrt(pow(p.getX(), 2) + pow(p.getY(), 2) + pow(p.getZ(), 2));
    }


    private double dot(Coords c1, Coords c2) {
        return c1.getX() * c2.getX() + c1.getY() * c2.getY() + c1.getZ() * c2.getZ();
    }


    private Coords cross(Coords c1, Coords c2) {
        double x = c1.getY() * c2.getZ() - c1.getZ() * c2.getY();
        double y = c1.getZ() * c2.getX() - c1.getX() * c2.getZ();
        double z = c1.getX() * c2.getY() - c1.getY() * c2.getX();
        return Coords.coords(x, y, z);
    }

}
