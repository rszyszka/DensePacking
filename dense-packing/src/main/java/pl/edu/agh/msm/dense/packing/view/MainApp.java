package pl.edu.agh.msm.dense.packing.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.edu.agh.msm.core.Space;
import pl.edu.agh.msm.dense.packing.*;


public class MainApp extends Application {
    public static final int X_SIZE = 200;
    public static final int Y_SIZE = 200;
    public static final int Z_SIZE = 200;
    public static final int MIN_R = 10;
    public static final int MAX_R = 40;

    private BorderPane borderPane;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        borderPane = new BorderPane();
        borderPane.setStyle("-fx-border-color: BLACK;");
        AnchorPane rootNode = new AnchorPane(borderPane);
        rootNode.setPrefWidth(1000);
        rootNode.setPrefHeight(1000);
        Scene scene = new Scene(rootNode);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dense Packing");
        primaryStage.setOnShown(event -> simulate());
        primaryStage.show();
    }


    public void simulate() {
        Bin bin = new Bin(X_SIZE, Y_SIZE, Z_SIZE);
        SphereGenerator sphereGenerator = new RandomSphereGenerator(MIN_R, MAX_R);
        InitialConfiguration initialConfiguration = new TangentialCirclesInitialConfiguration(bin, sphereGenerator);
        HolesFinder holesFinder = HolesFinder.create(bin);

        GreedyPacker packer = new GreedyPacker(initialConfiguration, holesFinder);
        Space space = new Space(X_SIZE, Y_SIZE, Z_SIZE);
        GreedyPackingSimulation simulation = new GreedyPackingSimulation(space, packer);

        Thread thread;
        if (Z_SIZE == 1) {
            thread = new Thread(new TwoDimensionalView(borderPane, simulation, bin));
        } else {
            thread = new Thread(new ThreeDimensionalView(borderPane, simulation, bin));
        }
        thread.setDaemon(true);
        thread.start();
    }

}
