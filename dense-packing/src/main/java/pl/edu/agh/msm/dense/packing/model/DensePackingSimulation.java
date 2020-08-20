package pl.edu.agh.msm.dense.packing.model;

import pl.edu.agh.msm.core.Simulation;
import pl.edu.agh.msm.core.Space;

import java.time.Instant;


public class DensePackingSimulation extends Simulation {
    private final Bin bin;
    private final BinSphereMixer mixingSpheresSimulation;
    int minR;
    int maxR;
    private int numberOfFilledCells;

    public DensePackingSimulation(Space space, int minR, int maxR) {
        super(space);
        this.minR = minR;
        this.maxR = maxR;
        numberOfFilledCells = 0;
        bin = new Bin(space.getXSize(), space.getYSize(), space.getZSize());
        mixingSpheresSimulation = BinSphereMixer.create(bin);
    }

    @Override
    public boolean performStep() {
        System.out.println("STEP 1: Greedy packing algorithm");
        performGreedyPacking();
        System.out.println("STEP 1 finished.");
        System.out.println("STEP 2: Mixing balls and trying to pack additional spheres");

        for (int i = 0; i < 4; i++) {
            int mixingPeriod = 8500;
            long startTime = Instant.now().toEpochMilli();
            while (Instant.now().toEpochMilli() - startTime <= 30000) {
                long periodStartTime = Instant.now().toEpochMilli();
                while (Instant.now().toEpochMilli() - periodStartTime <= mixingPeriod) {
                    mixingSpheresSimulation.performStep();
                }
                performGreedyPacking();
                System.out.println("Step 2 continues mixing");
                mixingPeriod = mixingPeriod <= 1000 ? 1000 : mixingPeriod - 2500;
            }
            System.out.println("Changing gravity state");
            mixingSpheresSimulation.changeGravityStateAndResetSpheresVelocities();
        }
        System.out.println("STEP 2 finished.");
        System.out.println("STEP 3: Filling CA space");
        SpaceFiller filler = new SpaceFiller(space);
        filler.fillWithAllCircles(bin);
        numberOfFilledCells = filler.getNumberOfFilledCells();

        System.out.println("Simulation completed");
        System.out.println("Voxel density: " + computeVoxelDensityLevel());
        return false;
    }

    private void performGreedyPacking() {
        HolesFinder holesFinder = HolesFinder.create(bin);
        HolesFinder.penaltyType = PenaltyType.ALL_EXCEPT_TOP;
        SphereGenerator sphereGenerator = new OscillatingSphereGenerator(minR, maxR, 1);
        InitialConfiguration initialConfiguration = new OneCornerSphereInitialConfiguration(bin, sphereGenerator);
        GreedyPacker packer = new GreedyPacker(initialConfiguration, holesFinder);
        GreedyPackingSimulation greedyPackingSimulation = new GreedyPackingSimulation(space, packer);
        greedyPackingSimulation.simulateContinuously();
        System.out.println("Packing density: " + greedyPackingSimulation.computeMathDensityLevel());
    }

    public double computeVoxelDensityLevel() {
        return numberOfFilledCells / (double) (space.getXSize() * space.getYSize() * space.getZSize());
    }
}
