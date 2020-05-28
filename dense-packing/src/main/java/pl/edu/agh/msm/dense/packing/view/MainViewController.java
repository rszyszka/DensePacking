package pl.edu.agh.msm.dense.packing.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.BorderPane;
import pl.edu.agh.msm.core.Space;
import pl.edu.agh.msm.dense.packing.*;

import java.net.URL;
import java.util.ResourceBundle;


public class MainViewController implements Initializable {

    @FXML
    private BorderPane borderPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        int xSize = 1000;
        int ySize = 1000;
        int zSize = 1;
        int minR = 30;
        int maxR = 60;

        Bin bin = new Bin(xSize, ySize, zSize);
        SphereGenerator sphereGenerator = new RandomSphereGenerator(minR, maxR);
        InitialConfiguration initialConfiguration = new TangentialCirclesInitialConfiguration(bin, sphereGenerator);
        HolesFinder holesFinder = new HolesFinder(bin);

        GreedyPacker packer = new GreedyPacker(initialConfiguration, holesFinder);
        Space space = new Space(xSize, ySize, zSize);
        GreedyPackingSimulation simulation = new GreedyPackingSimulation(space, packer);

        // new TwoDimensionalView(borderPane, simulation, bin).performSimulationAndShowResults();
        new ThreeDimensionalView(borderPane, simulation, bin).performSimulationAndShowResults();
    }


}
