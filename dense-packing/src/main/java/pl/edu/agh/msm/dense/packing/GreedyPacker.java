package pl.edu.agh.msm.dense.packing;


import java.util.List;

public class GreedyPacker {

    private InitialConfiguration initialConfiguration;
    private Bin bin;
    private SphereGenerator sphereGenerator;
    private HolesFinder holesFinder;


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
        Sphere sphere = sphereGenerator.generateNewCircle();
        List<Hole> holes = holesFinder.findForCircle(sphere);

        if (holes.isEmpty()) {
            return tryToGenerateAndPackNewSphereWithDiminishedRadius(sphere);
        }

        Hole bestHole = holesFinder.findHoleWithMaximumDegree();
        sphere.setCoords(bestHole.getCoords());
        sphereGenerator.resetMaxRadius();
        return bin.addCircle(sphere);
    }


    private boolean tryToGenerateAndPackNewSphereWithDiminishedRadius(Sphere sphere) {
        boolean diminished = sphereGenerator.setLowerRadiusIfPossible(sphere.getR());
        if (diminished) {
            return tryToPackNextCircle();
        }
        return false;
    }

}
