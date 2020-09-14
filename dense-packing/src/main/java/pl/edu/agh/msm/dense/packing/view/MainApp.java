package pl.edu.agh.msm.dense.packing.view;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.edu.agh.msm.core.Space;
import pl.edu.agh.msm.dense.packing.model.*;

import javax.swing.*;
import java.awt.*;
import java.time.Instant;
import java.util.concurrent.atomic.AtomicBoolean;


public class MainApp extends Application {
    private BorderPane borderPane;
    private SimulationSettings settings;
    private SphereMixingSimulation mixer;
    private View view;
    private Bin bin;

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


        settings = new SimulationSettings();
        bin = new Bin(settings.getSizeX(), settings.getSizeY(), settings.getSizeZ());
        view = View.create(borderPane, bin);
        mixer = SphereMixingSimulation.create(bin);

        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                openSettingsWindow();
            }
            if (event.getCode() == KeyCode.SPACE) {
                simulateGreedyPacking();
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
            if (event.getCode() == KeyCode.F) {
                simulateDensePacking();
            }

        });
        primaryStage.show();
    }

    private void openSettingsWindow() {
        boolean saved = SettingsWindow.displayAndSetProperties(settings);
        if (saved) {
            bin = new Bin(settings.getSizeX(), settings.getSizeY(), settings.getSizeZ());
            view = View.create(borderPane, bin);
        }
    }


    private void simulateDensePacking() {
        Thread thread = new Thread(() -> {
            try {
                simulateGreedyPacking().join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int squaredDimension = bin.getZSize() == 1 ? 4 : 6;
            for (int i = 0; i < squaredDimension; i++) {
                int mixingPeriod = 8500;
                long startTime = Instant.now().toEpochMilli();
                while (Instant.now().toEpochMilli() - startTime <= 30000) {
                    long periodStartTime = Instant.now().toEpochMilli();
                    simulateMixing();
                    while (Instant.now().toEpochMilli() - periodStartTime <= mixingPeriod) {
                    }
                    mixer.stop();
                    try {
                        simulateGreedyPacking().join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    mixingPeriod = mixingPeriod <= 1000 ? 1000 : mixingPeriod - 2500;
                }
                mixer.changeGravityStateAndResetSpheresVelocities();
            }
        });
        thread.setDaemon(true);
        thread.start();
    }


    private void simulateMixing() {
        mixer = SphereMixingSimulation.create(bin);
        MixingBallsTask task = new MixingBallsTask(mixer, bin);
        task.valueProperty().addListener((observable, oldValue, newValue) -> view.drawUsingStandardOvalFilling(bin.getSpheres()));
        task.setOnSucceeded(event -> view.drawUsingStandardOvalFilling(bin.getSpheres()));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }


    public Thread simulateGreedyPacking() {
        Space space = new Space(settings.getSizeX(), settings.getSizeY(), settings.getSizeZ());
        SphereGenerator sphereGenerator = SphereGenerator.create(
                settings.getSphereGeneratorType(), settings.getMinR(), settings.getMaxR(), settings.getOscillationStep()
        );
        InitialConfiguration initialConfiguration = InitialConfiguration.create(settings.getInitialConfigurationType(), bin, sphereGenerator);
        HolesFinder holesFinder = HolesFinder.create(bin);
        HolesFinder.PENALTY_VALUE = settings.getPenaltyValue();
        HolesFinder.penaltyType = settings.getPenaltyType();

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
