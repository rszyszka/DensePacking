package pl.edu.agh.msm.dense.packing;


import java.util.List;

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
        addThirdTangentSphereIn3dCase();
    }

    private void addThirdTangentSphereIn3dCase() {
        if (bin.getZSize() > 1) {
            Sphere sphere = sphereGenerator.generateNewSphere();
            HolesFinder2D holesFinder2D = new HolesFinder2D(bin);
            List<Hole> holes = holesFinder2D.findForSphere(sphere);
            if (holes.isEmpty()) {
                if (sphereGenerator.setLowerRadiusIfPossible(sphere.getR())) {
                    addThirdTangentSphereIn3dCase();
                }
            }
            sphere.setCoords(holesFinder2D.findHoleWithMaximumDegree().getCoords());
            bin.addSphere(sphere);
            sphereGenerator.resetMaxRadius();
        }
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
