package pl.edu.agh.msm.dense.packing.view;

import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.BorderPane;
import pl.edu.agh.msm.dense.packing.Bin;
import pl.edu.agh.msm.dense.packing.GreedyPackingSimulation;

import java.time.Instant;

public class TwoDimensionalView extends Task<Bin> {
    private final Canvas canvas;
    private final GreedyPackingSimulation simulation;
    private final Bin bin;


    public TwoDimensionalView(BorderPane borderPane, GreedyPackingSimulation simulation, Bin bin) {
        canvas = new Canvas(bin.getXSize(), bin.getYSize());
        borderPane.setCenter(canvas);
        this.simulation = simulation;
        this.bin = bin;
    }


    public void performSimulationAndShowResults() {
        long startTime = Instant.now().toEpochMilli();
        simulation.simulateContinuously();
        long endTime = Instant.now().toEpochMilli();

        System.out.println("Time elapsed: " + (endTime - startTime) + " milliseconds");
        System.out.println("Voxel density level: " + simulation.computeVoxelDensityLevel());
        System.out.println("Math density level: " + simulation.computeMathDensityLevel());

        drawUsingStandardOvalFilling();
    }


    private void drawUsingVoxelSpace() {
        for (int i = 0; i < simulation.getSpace().getXSize(); i++) {
            for (int j = 0; j < simulation.getSpace().getYSize(); j++) {
                if (simulation.getSpace().getCells()[i][j][0].getId() == 1) {
                    canvas.getGraphicsContext2D().fillRect(i, j, 1, 1);
                }
            }
        }
    }


    private void drawUsingStandardOvalFilling() {
        bin.getSpheres().forEach(circle -> {
            double x = circle.getCoords().getX();
            double y = circle.getCoords().getY();
            int r = circle.getR();
            int d = 2 * r;
            canvas.getGraphicsContext2D().fillOval(x - r, y - r, d, d);
        });
    }

    @Override
    protected Bin call() throws Exception {
        performSimulationAndShowResults();
        return bin;
    }
}
