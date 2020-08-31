package pl.edu.agh.msm.dense.packing.view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import pl.edu.agh.msm.core.Space;
import pl.edu.agh.msm.dense.packing.model.*;

import java.time.Instant;


public class MainApp extends Application {
//    public static final int X_SIZE = 1000;
//    public static final int Y_SIZE = 1000;
//    public static final int Z_SIZE = 1;

    public static final int X_SIZE = 200;
    public static final int Y_SIZE = 200;
    public static final int Z_SIZE = 200;

    public static final int MIN_R = 20;
    public static final int MAX_R = 40;

    public static final double PENALTY_VALUE = 0.02;
    public static final PenaltyType PENALTY_TYPE = PenaltyType.GLOBAL;

    private BorderPane borderPane;
    public View view;
    private Bin bin;
    private SphereMixingSimulation mixer;

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
        bin = new Bin(X_SIZE, Y_SIZE, Z_SIZE);
        view = View.create(borderPane, bin);
        mixer = SphereMixingSimulation.create(bin);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.SPACE) {
                simulatePacking();
            }
            if (event.getCode() == KeyCode.A) {
                simulateMixing();
            }
            if (event.getCode() == KeyCode.S) {
                mixer.changeGravityStateAndResetSpheresVelocities();
            }
            if (event.getCode() == KeyCode.D) {
                mixer.stop();
            }
            if(event.getCode() == KeyCode.F){
                Thread thread = new Thread(() -> {
                    try { simulatePacking().join(); } catch (InterruptedException e) { e.printStackTrace(); }
                    int squaredDimension = bin.getZSize() == 1 ? 4 : 6;
                    for (int i = 0; i < squaredDimension; i++) {
                        int mixingPeriod = 8500;
                        long startTime = Instant.now().toEpochMilli();
                        while (Instant.now().toEpochMilli() - startTime <= 30000) {
                            long periodStartTime = Instant.now().toEpochMilli();
                            simulateMixing();
                            while (Instant.now().toEpochMilli() - periodStartTime <= mixingPeriod) { }
                            mixer.stop();
                            try { simulatePacking().join(); } catch (InterruptedException e) { e.printStackTrace(); }
                            mixingPeriod = mixingPeriod <= 1000 ? 1000 : mixingPeriod - 2500;
                        }
                        mixer.changeGravityStateAndResetSpheresVelocities();
                    }
                });
                thread.setDaemon(true);
                thread.start();
            }

        });
        primaryStage.show();
    }

    private Thread simulateMixing() {
        MixingBallsTask task = new MixingBallsTask(mixer, bin);
        task.valueProperty().addListener((observable, oldValue, newValue) -> view.drawUsingStandardOvalFilling(bin.getSpheres()));
        task.setOnSucceeded(event -> view.drawUsingStandardOvalFilling(bin.getSpheres()));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        return thread;
    }


    public Thread simulatePacking() {
        Space space = new Space(X_SIZE, Y_SIZE, Z_SIZE);
        SphereGenerator sphereGenerator = new RandomSphereGenerator(MIN_R, MAX_R);

        InitialConfiguration initialConfiguration = new TangentialCirclesInitialConfiguration(bin, sphereGenerator);
        HolesFinder holesFinder = HolesFinder.create(bin);
        HolesFinder.PENALTY_VALUE = PENALTY_VALUE;
        HolesFinder.penaltyType = PENALTY_TYPE;

        GreedyPacker packer = new GreedyPacker(initialConfiguration, holesFinder);
        GreedyPackingSimulation simulation = new GreedyPackingSimulation(space, packer);

        SimulationTask task = new SimulationTask(simulation, bin);
        task.valueProperty().addListener((observable, oldValue, newValue) -> view.drawUsingStandardOvalFilling(newValue));
        task.setOnSucceeded(event -> view.drawUsingStandardOvalFilling(bin.getSpheres()));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        return thread;
    }

}
