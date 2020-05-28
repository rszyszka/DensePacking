package pl.edu.agh.msm.dense.packing.view;

import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import pl.edu.agh.msm.dense.packing.Bin;
import pl.edu.agh.msm.dense.packing.GreedyPackingSimulation;

import java.time.Instant;

public class ThreeDimensionalView {
    private final int xTranslate;
    private final int yTranslate;
    private final int zTranslate;
    private GreedyPackingSimulation simulation;
    private Bin bin;
    private Group group;

    public ThreeDimensionalView(BorderPane borderPane, GreedyPackingSimulation simulation, Bin bin) {
        this.simulation = simulation;
        this.bin = bin;

        xTranslate = bin.getXSize() >> 1;
        yTranslate = bin.getYSize() >> 1;
        zTranslate = bin.getZSize() >> 1;

        group = new Group();
        group.setTranslateX(xTranslate);
        group.setTranslateY(yTranslate);
        group.setTranslateZ(zTranslate);

        SubScene scene = new SubScene(group, bin.getXSize(), bin.getYSize(), true, SceneAntialiasing.BALANCED);
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

        drawUsingStandardOvalFilling();
    }

    private void drawUsingStandardOvalFilling() {
        bin.getSpheres().forEach(sphere -> {
            Sphere element = new Sphere(sphere.getR());
            element.setTranslateX(sphere.getCoords().getX() - xTranslate);
            element.setTranslateY(sphere.getCoords().getY() - yTranslate);
            element.setTranslateZ(sphere.getCoords().getZ() - zTranslate);

            PhongMaterial material = new PhongMaterial();
            material.setDiffuseColor(Color.ROYALBLUE);
            element.setMaterial(material);
            group.getChildren().add(element);
        });
    }

}
