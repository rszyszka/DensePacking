package pl.edu.agh.msm.dense.packing.view;

import javafx.scene.layout.BorderPane;
import pl.edu.agh.msm.dense.packing.model.Bin;
import pl.edu.agh.msm.dense.packing.model.Sphere;

import java.util.List;

public interface View {
    static View create(BorderPane borderPane, Bin bin) {
        if (bin.getZSize() == 1) {
            return new TwoDimensionalView(borderPane, bin);
        } else {
            return new ThreeDimensionalView(borderPane, bin);
        }
    }

    void drawUsingStandardOvalFilling(List<Sphere> spheres);

    void drawSphere(Sphere sphere);
}
