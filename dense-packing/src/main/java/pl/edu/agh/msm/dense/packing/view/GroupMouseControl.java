package pl.edu.agh.msm.dense.packing.view;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.Group;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.transform.Rotate;

public class GroupMouseControl {
    private static final DoubleProperty ANGLE_X = new SimpleDoubleProperty(0);
    private static final DoubleProperty ANGLE_Y = new SimpleDoubleProperty(0);
    private static double anchorX, anchorY;
    private static double anchorAngleX = 0;
    private static double anchorAngleY = 0;

    public static void init(BorderPane pane, Group group) {
        Rotate xRotate;
        Rotate yRotate;
        group.getTransforms().addAll(
                xRotate = new Rotate(0, Rotate.X_AXIS),
                yRotate = new Rotate(0, Rotate.Y_AXIS)
        );
        xRotate.angleProperty().bind(ANGLE_X);
        yRotate.angleProperty().bind(ANGLE_Y);

        pane.setOnMousePressed(event -> {
            anchorX = event.getSceneX();
            anchorY = event.getSceneY();
            anchorAngleX = ANGLE_X.get();
            anchorAngleY = ANGLE_Y.get();
        });

        pane.setOnMouseDragged(event -> {
            ANGLE_X.set(anchorAngleX - (anchorY - event.getSceneY()));
            ANGLE_Y.set(anchorAngleY + (anchorX - event.getSceneX()));
        });

        pane.addEventHandler(ScrollEvent.SCROLL, event -> {
            group.translateZProperty().set(group.getTranslateZ() + event.getDeltaY());
        });
    }

}

