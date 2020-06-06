package pl.edu.agh.msm.dense.packing;

import java.util.List;


public class GreedyPacker {

    private final InitialConfiguration initialConfiguration;
    private final Bin bin;
    private final SphereGenerator sphereGenerator;
    private final HolesFinder holesFinder;


    public GreedyPacker(InitialConfiguration initialConfiguration, HolesFinder holesFinder) {
        this.initialConfiguration = initialConfiguration;
        this.bin = initialConfiguration.getBin();
        this.sphereGenerator = initialConfiguration.getSphereGenerator();
        this.holesFinder = holesFinder;
    }


    public void createInitialConfiguration() {
        initialConfiguration.init();
    }


    public Bin getBin() {
        return bin;
    }


    public boolean tryToPackNextCircle() {
        Sphere sphere = sphereGenerator.generateNewSphere();
        List<Hole> holes = holesFinder.findForSphere(sphere);

        if (holes.isEmpty()) {
            return tryToGenerateAndPackNewSphereWithDiminishedRadius(sphere);
        }

        Hole bestHole = holesFinder.findHoleWithMaximumDegree();
        sphere.setCoords(bestHole.getCoords());
        sphereGenerator.resetMaxRadius();
        return bin.addSphere(sphere);
    }


    private boolean tryToGenerateAndPackNewSphereWithDiminishedRadius(Sphere sphere) {
        boolean diminished = sphereGenerator.setLowerRadiusIfPossible(sphere.getR());
        if (diminished) {
            return tryToPackNextCircle();
        }
        return false;
    }

}
