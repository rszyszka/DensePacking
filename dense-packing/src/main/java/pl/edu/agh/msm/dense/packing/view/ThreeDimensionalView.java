package pl.edu.agh.msm.dense.packing.view;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import pl.edu.agh.msm.dense.packing.model.Bin;
import pl.edu.agh.msm.dense.packing.model.GreedyPackingSimulation;

import java.time.Instant;

public class ThreeDimensionalView extends Task<Bin> {
    private final int xTranslate;
    private final int yTranslate;
    private final int zTranslate;
    private final GreedyPackingSimulation simulation;
    private final Bin bin;
    private final Group group;

    public ThreeDimensionalView(BorderPane borderPane, GreedyPackingSimulation simulation, Bin bin) {
        this.simulation = simulation;
        this.bin = bin;

        xTranslate = bin.getXSize() >> 1;
        yTranslate = bin.getYSize() >> 1;
        zTranslate = bin.getZSize() >> 1;

        group = new Group();
        group.setTranslateX(500);
        group.setTranslateY(500);
        group.setTranslateZ(-1000);

        SubScene scene = new SubScene(group, 1000, 1000, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.SILVER);
        scene.setCamera(new PerspectiveCamera());
        GroupMouseControl.init(borderPane, group);
        group.getChildren().add(new AxisBox(bin.getXSize(), bin.getYSize(), bin.getZSize()));
        borderPane.setCenter(scene);
    }

    public void performSimulationAndShowResults() {
        long startTime = Instant.now().toEpochMilli();
        simulation.simulateContinuously();
        long endTime = Instant.now().toEpochMilli();

        System.out.println("Time elapsed: " + (endTime - startTime) + " milliseconds");
        System.out.println("Voxel density level: " + simulation.computeVoxelDensityLevel());
        System.out.println("Math density level: " + simulation.computeMathDensityLevel());

        Platform.runLater(this::drawUsingStandardOvalFilling);
    }

    private void drawUsingStandardOvalFilling() {
        bin.getSpheres().forEach(this::drawSphere);
    }

    private void drawSphere(pl.edu.agh.msm.dense.packing.model.Sphere sphere) {
        Sphere element = new Sphere(sphere.getR());
        element.setTranslateX(sphere.getCoords().getX() - xTranslate);
        element.setTranslateY(sphere.getCoords().getY() - yTranslate);
        element.setTranslateZ(sphere.getCoords().getZ() - zTranslate);

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(Color.GOLD);
        element.setMaterial(material);
        group.getChildren().add(element);
    }

    @Override
    protected Bin call() {
        performSimulationAndShowResults();
        return bin;
    }
}
