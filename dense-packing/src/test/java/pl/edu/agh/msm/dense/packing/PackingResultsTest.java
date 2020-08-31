package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.edu.agh.msm.core.Space;
import pl.edu.agh.msm.dense.packing.model.*;

import java.time.Instant;


public class PackingResultsTest {
//        public static final int X_SIZE = 1000;
//    public static final int Y_SIZE = 1000;
//    public static final int Z_SIZE = 1;
    public static final int X_SIZE = 200;
    public static final int Y_SIZE = 200;
    public static final int Z_SIZE = 200;
    public static final int MIN_R = 30;
    public static final int MAX_R = 30;

    private void countSpheresAndTheirAvgRadius(Bin bin) {
        int numberOfSpheres = 0;
        double radiiSum = 0;
        for (Sphere sphere : bin.getSpheres()) {
            int r = sphere.getR();
            radiiSum += r;
            numberOfSpheres++;
        }
        double avgRadii = radiiSum / numberOfSpheres;
        System.out.print(avgRadii + "\t" + numberOfSpheres + "\t");
//        System.out.println("\n\nAvg radii: " + avgRadii);
//        System.out.println("Spheres in bin: " + numberOfSpheres);
    }

    public double computeMathDensityLevel(Bin bin) {
        return bin.getZSize() == 1 ? computeMathCircleDensityLevel(bin) : computeMathSpheresDensityLevel(bin);
    }

    private double computeMathSpheresDensityLevel(Bin bin) {
        return bin.getSpheres().stream()
                .mapToDouble(circle -> 1.3333333333333333333 * Math.PI * Math.pow(circle.getR(), 3))
                .sum() / (double) (bin.getXSize() * bin.getYSize() * bin.getZSize());
    }

    private double computeMathCircleDensityLevel(Bin bin) {
        return bin.getSpheres().stream()
                .mapToDouble(circle -> Math.PI * circle.getR() * circle.getR())
                .sum() / (double) (bin.getXSize() * bin.getYSize() * bin.getZSize());
    }


    @Test
    public void densePackingTest() {
        for (int i = 0; i < 10; i++) {
            Space space = new Space(X_SIZE, Y_SIZE, Z_SIZE);
            DensePackingSimulation simulation = new DensePackingSimulation(space, MIN_R,MAX_R);
            Bin bin = simulation.getBin();

            long startTime = Instant.now().toEpochMilli();
            simulation.simulateContinuously();
            long endTime = Instant.now().toEpochMilli();

            countSpheresAndTheirAvgRadius(bin);
            double density = computeMathDensityLevel(bin);
            System.out.println((endTime - startTime) + "\t" + density + "\t" + density);
//            System.out.println("Time elapsed: " + (endTime - startTime) + " milliseconds");
//            System.out.println("Math density level: " + simulation.computeMathDensityLevel());
        }
    }

    @Test
    public void densePackingTest2020() {
        for (int i = 0; i < 10; i++) {
            Space space = new Space(X_SIZE, Y_SIZE, Z_SIZE);
            DensePackingSimulation simulation = new DensePackingSimulation(space, 20,20);
            Bin bin = simulation.getBin();

            long startTime = Instant.now().toEpochMilli();
            simulation.simulateContinuously();
            long endTime = Instant.now().toEpochMilli();

            countSpheresAndTheirAvgRadius(bin);
            double density = computeMathDensityLevel(bin);
            System.out.println((endTime - startTime) + "\t" + density + "\t" + density);
//            System.out.println("Time elapsed: " + (endTime - startTime) + " milliseconds");
//            System.out.println("Math density level: " + simulation.computeMathDensityLevel());
        }
    }

    @Test
    public void generatePackingTest() {
        for (int i = 0; i < 10; i++) {
            Bin bin = new Bin(X_SIZE, Y_SIZE, Z_SIZE);
            SphereGenerator sphereGenerator = new RandomSphereGenerator(MIN_R, MAX_R);
            InitialConfiguration initialConfiguration = new TangentialCirclesInitialConfiguration(bin, sphereGenerator);
            HolesFinder holesFinder = HolesFinder.create(bin);
            HolesFinder.penaltyType = PenaltyType.GLOBAL;
            HolesFinder.PENALTY_VALUE = 0.22;

            GreedyPacker packer = new GreedyPacker(initialConfiguration, holesFinder);
            Space space = new Space(X_SIZE, Y_SIZE, Z_SIZE);
            GreedyPackingSimulation simulation = new GreedyPackingSimulation(space, packer);

            long startTime = Instant.now().toEpochMilli();
            simulation.simulateContinuously();
            long endTime = Instant.now().toEpochMilli();

            countSpheresAndTheirAvgRadius(bin);
            double density = computeMathDensityLevel(bin);
            System.out.println((endTime - startTime) + "\t" + density + "\t" + density);
//            System.out.println("Time elapsed: " + (endTime - startTime) + " milliseconds");
//            System.out.println("Math density level: " + simulation.computeMathDensityLevel());
        }
    }

    @Test
    public void penaltyTest() {
        final int X_SIZE = 200;
        final int Y_SIZE = 200;
        final int Z_SIZE = 200;
        final int MIN_R = 20;
        final int MAX_R = 40;

        double penaltyValue = 0.31;
        for (int j = 0; j < 31; j++) {
            double densitySum = 0;
            //for (int i = 0; i < 10; i++) {
                Bin bin = new Bin(X_SIZE, Y_SIZE, Z_SIZE);
                SphereGenerator sphereGenerator = new OscillatingSphereGenerator(MIN_R,MAX_R ,5);
                InitialConfiguration initialConfiguration = new TangentialCirclesInitialConfiguration(bin, sphereGenerator);
                HolesFinder holesFinder = HolesFinder.create(bin);
                HolesFinder.penaltyType = PenaltyType.GLOBAL;
                HolesFinder.PENALTY_VALUE = penaltyValue;

                GreedyPacker packer = new GreedyPacker(initialConfiguration, holesFinder);
                Space space = new Space(X_SIZE, Y_SIZE, Z_SIZE);
                GreedyPackingSimulation simulation = new GreedyPackingSimulation(space, packer);

                simulation.simulateContinuously();
                densitySum = simulation.computeMathDensityLevel();
            //}
            System.out.println(penaltyValue + "\t" + (densitySum));
            penaltyValue = Utils.roundUp(penaltyValue + 0.01, 2);
        }
    }


    @ParameterizedTest
    @ValueSource(doubles = {0.35, 0.4, 0.02, 0.03, 0.04, 0.05, 0.06, 0.07, 0.08, 0.09, 0.1
            , 0.11, 0.12, 0.13, 0.14, 0.15, 0.16, 0.17, 0.18, 0.19, 0.2
            , 0.21, 0.22, 0.23, 0.24, 0.25, 0.26, 0.27, 0.28, 0.29, 0.3
    })
    public void generatePackingTest(double penaltyValue) {
        Bin bin = new Bin(X_SIZE, Y_SIZE, Z_SIZE);
        SphereGenerator sphereGenerator = new LargestSphereGenerator(MIN_R, MAX_R);
        InitialConfiguration initialConfiguration = new TangentialCirclesInitialConfiguration(bin, sphereGenerator);
        HolesFinder holesFinder = HolesFinder.create(bin);
        HolesFinder.PENALTY_VALUE = penaltyValue;

        GreedyPacker packer = new GreedyPacker(initialConfiguration, holesFinder);
        Space space = new Space(X_SIZE, Y_SIZE, Z_SIZE);
        GreedyPackingSimulation simulation = new GreedyPackingSimulation(space, packer);

        long startTime = Instant.now().toEpochMilli();
        simulation.simulateContinuously();
        long endTime = Instant.now().toEpochMilli();

        countSpheresAndTheirAvgRadius(bin);
        System.out.println("Time elapsed: " + (endTime - startTime) + " milliseconds");
        System.out.println("Math density level: " + simulation.computeMathDensityLevel());
    }
}
