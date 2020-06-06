package pl.edu.agh.msm.dense.packing.view;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.paint.PhongMaterial;
import javafx.scene.shape.Box;

public class AxisBox extends Group {
    private static final int THICKNESS = 1;
    private final int xTranslate;
    private final int yTranslate;
    private final int zTranslate;
    private final int width;
    private final int height;
    private final int depth;

    public AxisBox(int width, int height, int depth) {
        this.width = width;
        this.height = height;
        this.depth = depth;

        xTranslate = width >> 1;
        yTranslate = height >> 1;
        zTranslate = depth >> 1;

        buildAxes();
    }

    private void buildAxes() {
        PhongMaterial xMat = new PhongMaterial();
        xMat.setDiffuseColor(Color.BLUE);
        PhongMaterial yMat = new PhongMaterial();
        yMat.setDiffuseColor(Color.GREEN);
        PhongMaterial zMat = new PhongMaterial();
        zMat.setDiffuseColor(Color.RED);

        Box xAxis1 = new Box(width, THICKNESS, THICKNESS);
        xAxis1.setMaterial(xMat);
        xAxis1.setTranslateY(-yTranslate);
        xAxis1.setTranslateZ(-zTranslate);
        Box xAxis2 = new Box(width, THICKNESS, THICKNESS);
        xAxis2.setMaterial(xMat);
        xAxis2.setTranslateY(yTranslate);
        xAxis2.setTranslateZ(-zTranslate);
        Box xAxis3 = new Box(width, THICKNESS, THICKNESS);
        xAxis3.setMaterial(xMat);
        xAxis3.setTranslateY(-yTranslate);
        xAxis3.setTranslateZ(zTranslate);
        Box xAxis4 = new Box(width, THICKNESS, THICKNESS);
        xAxis4.setMaterial(xMat);
        xAxis4.setTranslateY(yTranslate);
        xAxis4.setTranslateZ(zTranslate);

        Box yAxis1 = new Box(THICKNESS, height, THICKNESS);
        yAxis1.setMaterial(yMat);
        yAxis1.setTranslateX(-xTranslate);
        yAxis1.setTranslateZ(-zTranslate);
        Box yAxis2 = new Box(THICKNESS, height, THICKNESS);
        yAxis2.setMaterial(yMat);
        yAxis2.setTranslateX(xTranslate);
        yAxis2.setTranslateZ(-zTranslate);
        Box yAxis3 = new Box(THICKNESS, height, THICKNESS);
        yAxis3.setMaterial(yMat);
        yAxis3.setTranslateX(-xTranslate);
        yAxis3.setTranslateZ(zTranslate);
        Box yAxis4 = new Box(THICKNESS, height, THICKNESS);
        yAxis4.setMaterial(yMat);
        yAxis4.setTranslateX(xTranslate);
        yAxis4.setTranslateZ(zTranslate);

        Box zAxis1 = new Box(THICKNESS, THICKNESS, depth);
        zAxis1.setMaterial(zMat);
        zAxis1.setTranslateX(-xTranslate);
        zAxis1.setTranslateY(-yTranslate);
        Box zAxis2 = new Box(THICKNESS, THICKNESS, depth);
        zAxis2.setMaterial(zMat);
        zAxis2.setTranslateX(xTranslate);
        zAxis2.setTranslateY(-yTranslate);
        Box zAxis3 = new Box(THICKNESS, THICKNESS, depth);
        zAxis3.setMaterial(zMat);
        zAxis3.setTranslateX(-xTranslate);
        zAxis3.setTranslateY(yTranslate);
        Box zAxis4 = new Box(THICKNESS, THICKNESS, depth);
        zAxis4.setMaterial(zMat);
        zAxis4.setTranslateX(xTranslate);
        zAxis4.setTranslateY(yTranslate);

        getChildren().add(xAxis1);
        getChildren().add(xAxis2);
        getChildren().add(xAxis3);
        getChildren().add(xAxis4);
        getChildren().add(yAxis1);
        getChildren().add(yAxis2);
        getChildren().add(yAxis3);
        getChildren().add(yAxis4);
        getChildren().add(zAxis1);
        getChildren().add(zAxis2);
        getChildren().add(zAxis3);
        getChildren().add(zAxis4);
    }

}
