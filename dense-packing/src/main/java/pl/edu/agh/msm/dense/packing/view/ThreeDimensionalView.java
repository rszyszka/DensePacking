package pl.edu.agh.msm.dense.packing.view;

import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.SceneAntialiasing;
import javafx.scene.SubScene;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Sphere;
import pl.edu.agh.msm.dense.packing.model.Bin;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThreeDimensionalView implements View {
    private static long lastDrawingTime = 0;

    private final int xTranslate;
    private final int yTranslate;
    private final int zTranslate;
    private final Group group;
    private final Bin bin;
    private final AxisBox axisBox;
    private final Map<pl.edu.agh.msm.dense.packing.model.Sphere, Color> sphereColorMap;

    public ThreeDimensionalView(BorderPane borderPane, Bin bin) {
        this.bin = bin;
        int xSize = bin.getXSize();
        int ySize = bin.getYSize();
        int zSize = bin.getZSize();
        xTranslate = xSize >> 1;
        yTranslate = ySize >> 1;
        zTranslate = zSize >> 1;

        group = new Group();
        group.setTranslateX(500);
        group.setTranslateY(500);
        group.setTranslateZ(-1000);
        sphereColorMap = new HashMap<>();

        SubScene scene = new SubScene(group, 1000, 1000, true, SceneAntialiasing.BALANCED);
        scene.setFill(Color.SILVER);
        scene.setCamera(new PerspectiveCamera());
        GroupMouseControl.init(borderPane, group);
        axisBox = new AxisBox(xSize, ySize, zSize);
        group.getChildren().add(axisBox);
        borderPane.setCenter(scene);
    }

    @Override
    public void drawUsingStandardOvalFilling(List<pl.edu.agh.msm.dense.packing.model.Sphere> spheres) {
        long currentTime = Instant.now().toEpochMilli();
        if (currentTime - lastDrawingTime >= 16) {
            group.getChildren().clear();
            group.getChildren().add(axisBox);
            spheres.forEach(this::drawSphere);
            lastDrawingTime = Instant.now().toEpochMilli();
        }
    }

    @Override
    public void drawSphere(pl.edu.agh.msm.dense.packing.model.Sphere sphere) {
        Sphere element = new Sphere(sphere.getR());
        element.setTranslateX(sphere.getCoords().getX() - xTranslate);
        element.setTranslateY(sphere.getCoords().getY() - yTranslate);
        element.setTranslateZ(sphere.getCoords().getZ() - zTranslate);

        Color color = sphereColorMap.get(sphere);
        if (color == null) {
            color = Color.color(Math.random(), Math.random(), Math.random());
            sphereColorMap.put(sphere, color);
        }

        PhongMaterial material = new PhongMaterial();
        material.setDiffuseColor(color);
        element.setMaterial(material);
        group.getChildren().add(element);
    }

}
