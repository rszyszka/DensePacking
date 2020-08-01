package pl.edu.agh.msm.dense.packing.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import pl.edu.agh.msm.dense.packing.model.Bin;
import pl.edu.agh.msm.dense.packing.model.Sphere;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TwoDimensionalView implements View {
    private static long lastDrawingTime = 0;

    private final Canvas canvas;
    private final Bin bin;
    private final Map<Sphere, Color> sphereColorMap;

    public TwoDimensionalView(BorderPane borderPane, Bin bin) {
        this.bin = bin;
        canvas = new Canvas(bin.getXSize(), bin.getYSize());
        sphereColorMap = new HashMap<>();
        borderPane.setCenter(canvas);
    }

    @Override
    public void drawUsingStandardOvalFilling(List<Sphere> spheres) {
        long currentTime = Instant.now().toEpochMilli();
        if (currentTime - lastDrawingTime >= 16) {
            canvas.getGraphicsContext2D().clearRect(0, 0, bin.getXSize(), bin.getYSize());
            spheres.forEach(this::drawSphere);
            lastDrawingTime = Instant.now().toEpochMilli();
        }
    }

    @Override
    public void drawSphere(Sphere sphere) {
        double x = sphere.getCoords().getX();
        double y = sphere.getCoords().getY();
        int r = sphere.getR();
        int d = 2 * r;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Color color = sphereColorMap.get(sphere);
        if (color == null) {
            color = Color.color(Math.random(), Math.random(), Math.random());
            sphereColorMap.put(sphere, color);
        }
        gc.setFill(color);
        gc.fillOval(x - r, y - r, d, d);
        gc.setStroke(Color.BLACK);
        gc.strokeOval(x - r, y - r, d, d);
    }

}

