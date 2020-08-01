package pl.edu.agh.msm.dense.packing.view;

import javafx.concurrent.Task;
import pl.edu.agh.msm.dense.packing.model.Bin;
import pl.edu.agh.msm.dense.packing.model.GreedyPackingSimulation;
import pl.edu.agh.msm.dense.packing.model.Sphere;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;


public class SimulationTask extends Task<List<Sphere>> {
    private final GreedyPackingSimulation simulation;
    private final Bin bin;

    public SimulationTask(GreedyPackingSimulation simulation, Bin bin) {
        this.simulation = simulation;
        this.bin = bin;
    }

    @Override
    protected List<Sphere> call() {
        performSimulation();
        return getSphereListCopy();
    }

    public void performSimulation() {
        long startTime = Instant.now().toEpochMilli();
        while (simulation.performStep()) {
            updateValue(getSphereListCopy());
        }
        long endTime = Instant.now().toEpochMilli();

        System.out.println("Time elapsed: " + (endTime - startTime) + " milliseconds");
        System.out.println("Voxel density level: " + simulation.computeVoxelDensityLevel());
        System.out.println("Math density level: " + simulation.computeMathDensityLevel());
    }

    private List<Sphere> getSphereListCopy() {
        return new LinkedList<>(bin.getSpheres());
    }

}
