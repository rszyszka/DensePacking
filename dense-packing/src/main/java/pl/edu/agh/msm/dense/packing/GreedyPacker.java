package pl.edu.agh.msm.dense.packing;


import java.util.List;

public class GreedyPacker {

    private InitialConfiguration initialConfiguration;
    private Bin bin;
    private CircleGenerator circleGenerator;
    private HolesFinder holesFinder;


    public GreedyPacker(InitialConfiguration initialConfiguration, HolesFinder holesFinder) {
        this.initialConfiguration = initialConfiguration;
        this.bin = initialConfiguration.getBin();
        this.circleGenerator = initialConfiguration.getCircleGenerator();
        this.holesFinder = holesFinder;
    }


    public void createInitialConfiguration() {
        initialConfiguration.init();
    }


    public Bin getBin() {
        return bin;
    }


    public boolean tryToPackNextCircle() {
        Circle circle = circleGenerator.generateNewCircle();
        List<Hole> holes = holesFinder.findForCircle(circle);

        if (holes.isEmpty()) {
            return tryToGenerateAndPackNewCircleWithDiminishedRadius(circle);
        }

        Hole bestHole = holesFinder.findHoleWithMaximumDegree();
        circle.setCoords(bestHole.getCoords());
        circleGenerator.resetMaxRadius();
        return bin.addCircle(circle);
    }


    private boolean tryToGenerateAndPackNewCircleWithDiminishedRadius(Circle circle) {
        boolean diminished = circleGenerator.setLowerRadiusIfPossible(circle.getR());
        if (diminished) {
            return tryToPackNextCircle();
        }
        return false;
    }

}
