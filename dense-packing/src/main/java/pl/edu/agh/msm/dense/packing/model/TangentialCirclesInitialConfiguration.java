package pl.edu.agh.msm.dense.packing.model;

import static java.lang.Math.pow;
import static java.lang.Math.sqrt;


public class TangentialCirclesInitialConfiguration extends InitialConfiguration {

    public TangentialCirclesInitialConfiguration(Bin bin, SphereGenerator sphereGenerator) {
        super(bin, sphereGenerator);
    }


    @Override
    public void init() {
        addNewCircleInUpperLeftCorner();
        addNewCircleNextToFirstOne();
    }


    private void addNewCircleNextToFirstOne() {
        Sphere sphere = sphereGenerator.generateNewSphere();
        Sphere sphereInBin = bin.getSpheres().get(0);

        int rC = sphere.getR();
        int rA = sphereInBin.getR();

        Coords coordsA = sphereInBin.getCoords();
        double y = sqrt(pow(rC + rA, 2) - pow(rC - coordsA.getX(), 2)) + coordsA.getY();

        sphere.setCoords(Coords.coords(rC, y, sphereInBin.getCoords().getZ()));
        bin.addSphere(sphere);
    }

}
