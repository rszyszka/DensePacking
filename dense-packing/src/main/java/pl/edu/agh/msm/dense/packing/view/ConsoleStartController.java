package pl.edu.agh.msm.dense.packing.view;

import pl.edu.agh.msm.core.Simulation;
import pl.edu.agh.msm.core.Space;
import pl.edu.agh.msm.dense.packing.model.DensePackingSimulation;

public class ConsoleStartController {
    public static void main(String[] args) {
        Simulation simulation = new DensePackingSimulation(new Space(1000, 1000, 1), 30, 60);
        simulation.simulateContinuously();
    }
}
