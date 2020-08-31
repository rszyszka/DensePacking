package pl.edu.agh.msm.dense.packing.model;

import pl.edu.agh.msm.core.Simulation;
import pl.edu.agh.msm.core.Space;

import java.time.Instant;


public class DensePackingSimulation extends Simulation {
    private final Bin bin;
    private final SphereMixingSimulation mixingSpheresSimulation;
    int minR;
    int maxR;
    private int numberOfFilledCells;

    public DensePackingSimulation(Space space, int minR, int maxR) {
        super(space);
        this.minR = minR;
        this.maxR = maxR;
        numberOfFilledCells = 0;
        bin = new Bin(space.getXSize(), space.getYSize(), space.getZSize());
        mixingSpheresSimulation = SphereMixingSimulation.create(bin);
    }

    @Override
    public boolean performStep() {
        performGreedyPacking();
        int squaredDimension = bin.getZSize() == 1 ? 4 : 6;
        for (int i = 0; i < squaredDimension; i++) {
            int mixingPeriod = 8500;
            long startTime = Instant.now().toEpochMilli();
            while (Instant.now().toEpochMilli() - startTime <= 30000) {
                long periodStartTime = Instant.now().toEpochMilli();
                while (Instant.now().toEpochMilli() - periodStartTime <= mixingPeriod) {
                    mixingSpheresSimulation.performStep();
                }
                performGreedyPacking();
                mixingPeriod = mixingPeriod <= 1000 ? 1000 : mixingPeriod - 2500;
            }
            mixingSpheresSimulation.changeGravityStateAndResetSpheresVelocities();
        }
        SpaceFiller filler = new SpaceFiller(space);
        filler.fillWithAllSpheres(bin);
        numberOfFilledCells = filler.getNumberOfFilledCells();
        return false;
    }

    private void performGreedyPacking() {
        HolesFinder holesFinder = HolesFinder.create(bin);
        HolesFinder.penaltyType = PenaltyType.GLOBAL;
        HolesFinder.PENALTY_VALUE = 0.02;
        SphereGenerator sphereGenerator = new RandomSphereGenerator(minR, maxR);
        InitialConfiguration initialConfiguration = new TangentialCirclesInitialConfiguration(bin, sphereGenerator);
        GreedyPacker packer = new GreedyPacker(initialConfiguration, holesFinder);
        GreedyPackingSimulation greedyPackingSimulation = new GreedyPackingSimulation(space, packer);
        greedyPackingSimulation.simulateContinuously();
    }

    public Bin getBin() {
        return bin;
    }

    public double computeVoxelDensityLevel() {
        return numberOfFilledCells / (double) (space.getXSize() * space.getYSize() * space.getZSize());
    }
}
