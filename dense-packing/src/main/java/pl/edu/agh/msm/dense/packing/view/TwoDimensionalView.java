package pl.edu.agh.msm.dense.packing.view;

import javafx.concurrent.Task;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import pl.edu.agh.msm.dense.packing.model.Bin;
import pl.edu.agh.msm.dense.packing.model.GreedyPackingSimulation;
import pl.edu.agh.msm.dense.packing.model.Sphere;

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
        drawSphere(bin.getSpheres().get(0));
        drawSphere(bin.getSpheres().get(1));
        while (simulation.performStep()) {
            drawSphere(bin.getSpheres().get(bin.getSpheres().size() - 1));
        }
        long endTime = Instant.now().toEpochMilli();

        System.out.println("Time elapsed: " + (endTime - startTime) + " milliseconds");
        System.out.println("Voxel density level: " + simulation.computeVoxelDensityLevel());
        System.out.println("Math density level: " + simulation.computeMathDensityLevel());
    }


    private void drawUsingVoxelSpace() {
        for (int i = 0; i < simulation.getSpace().getXSize(); i++) {
            for (int j = 0; j < simulation.getSpace().getYSize(); j++) {
                if (simulation.getSpace().getCells()[i][j][0].getId() >= 1) {
                    canvas.getGraphicsContext2D().fillRect(i, j, 1, 1);
                }
            }
        }
    }


    private void drawUsingStandardOvalFilling() {
        bin.getSpheres().forEach(this::drawSphere);
    }

    private void drawSphere(Sphere sphere) {
        double x = sphere.getCoords().getX();
        double y = sphere.getCoords().getY();
        int r = sphere.getR();
        int d = 2 * r;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setFill(Color.color(Math.random(), Math.random(), Math.random()));
        gc.fillOval(x - r, y - r, d, d);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - r, y - r, d, d);
    }

    @Override
    protected Bin call() {
        performSimulationAndShowResults();
        return bin;
    }
}
