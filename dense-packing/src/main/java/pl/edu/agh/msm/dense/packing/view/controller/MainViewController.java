package pl.edu.agh.msm.dense.packing.view.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import pl.edu.agh.msm.core.Space;
import pl.edu.agh.msm.dense.packing.*;

import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {

    @FXML
    private Canvas canvas;
    private Bin bin;
    private GreedyPackingSimulation simulation;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int xSize = 1000;
        int ySize = 1000;
        int minR = 30;
        int maxR = 60;

        canvas.setWidth(xSize);
        canvas.setHeight(ySize);
        bin = new Bin(xSize, ySize);
        CircleGenerator circleGenerator = new RandomCircleGenerator(minR, maxR);
        InitialConfiguration initialConfiguration = new TangentialCirclesInitialConfiguration(bin, circleGenerator);
        HolesFinder holesFinder = new HolesFinder(bin);
        GreedyPacker packer = new GreedyPacker(initialConfiguration, holesFinder);
        Space space = new Space(xSize, ySize, 1);
        simulation = new GreedyPackingSimulation(space, packer);

        performSimulationAndShowResults();
    }


    private void performSimulationAndShowResults() {
        long startTime = Instant.now().toEpochMilli();
        simulation.simulateContinuously();
        long endTime = Instant.now().toEpochMilli();

        System.out.println("Time elapsed: " + (endTime - startTime) + " milliseconds");
        //drawUsingVoxelSpace();
        drawUsingStandardOvalFilling();
        System.out.println("Voxel density level: " + simulation.computeVoxelDensityLevel());
        System.out.println("Math density level: " + simulation.computeMathDensityLevel());
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
        bin.getCircles().forEach(circle -> {
            double x = circle.getCoords().getX();
            double y = circle.getCoords().getY();
            int r = circle.getR();
            int d = 2 * r;
            canvas.getGraphicsContext2D().fillOval(x - r, y - r, d, d);
        });
    }

}
