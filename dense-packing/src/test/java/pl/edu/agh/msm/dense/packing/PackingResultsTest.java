package pl.edu.agh.msm.dense.packing;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import pl.edu.agh.msm.core.Space;
import pl.edu.agh.msm.dense.packing.model.*;

import java.time.Instant;


public class PackingResultsTest {
    public static final int X_SIZE = 1000;
    public static final int Y_SIZE = 1000;
    public static final int Z_SIZE = 1;
    public static final int MIN_R = 20;
    public static final int MAX_R = 40;

    @RepeatedTest(10)
    public void generatePackingTest() {
        Bin bin = new Bin(X_SIZE, Y_SIZE, Z_SIZE);
        SphereGenerator sphereGenerator = new LargestSphereGenerator(MIN_R, MAX_R);
        InitialConfiguration initialConfiguration = new TangentialCirclesInitialConfiguration(bin, sphereGenerator);
        HolesFinder holesFinder = HolesFinder.create(bin);

        GreedyPacker packer = new GreedyPacker(initialConfiguration, holesFinder);
        Space space = new Space(X_SIZE, Y_SIZE, Z_SIZE);
        GreedyPackingSimulation simulation = new GreedyPackingSimulation(space, packer);

        long startTime = Instant.now().toEpochMilli();
        simulation.simulateContinuously();
        long endTime = Instant.now().toEpochMilli();

        System.out.println("\n\nTime elapsed: " + (endTime - startTime) + " milliseconds");
        System.out.println("Voxel density level: " + simulation.computeVoxelDensityLevel());
        System.out.println("Math density level: " + simulation.computeMathDensityLevel());
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

        System.out.println("\n\nTime elapsed: " + (endTime - startTime) + " milliseconds");
        System.out.println("Voxel density level: " + simulation.computeVoxelDensityLevel());
        System.out.println("Math density level: " + simulation.computeMathDensityLevel());
    }
}
