package pl.edu.agh.msm.dense.packing.view;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.edu.agh.msm.core.Space;
import pl.edu.agh.msm.dense.packing.model.*;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.time.Instant;
import java.util.ResourceBundle;

public class MainViewController implements Initializable {

    public static final String RUNNING = "Running...";
    public static final String SAVING = "Saving...";
    public static final String READY = "Ready.  ";
    @FXML
    public Label stateLabel;
    @FXML
    public Label densityLabel;
    @FXML
    private AnchorPane rootAnchorPane;
    @FXML
    private BorderPane borderPane;

    private SimulationSettings settings;
    private SphereMixingSimulation mixer;
    private View view;
    private Bin bin;
    private Space space;

    enum Action {
        GREEDY, MIXING, DENSE, SAVING, NONE
    }

    private Action action = Action.NONE;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        settings = new SimulationSettings();
        bin = new Bin(settings.getSizeX(), settings.getSizeY(), settings.getSizeZ());
        view = View.create(borderPane, bin);
        mixer = SphereMixingSimulation.create(bin);
    }

    @FXML
    public void saveCASpace() {
        if (action == Action.NONE) {
            action = Action.SAVING;
            stateLabel.setText(SAVING);
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save CA space");
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("TXT", "*.txt"));
            fileChooser.setInitialDirectory(new File("."));
            File file = fileChooser.showSaveDialog(rootAnchorPane.getScene().getWindow());
            if (file != null) {
                try {
                    writeResultsToFile(file);
                } catch (IOException ex) {
                    raiseErrorAlert("Error while writing to file", ex.getMessage());
                } finally {
                    stateLabel.setText(READY);
                }
            }
            action = Action.NONE;
            stateLabel.setText(READY);
        } else {
            raiseWarningAlert();
        }
    }

    @FXML
    public void closeApp() {
        Platform.exit();
    }

    @FXML
    public void showSettings() {
        if (action == Action.NONE) {
            openSettingsWindow();
        } else {
            raiseWarningAlert();
        }
    }

    @FXML
    public void runGreedyPacking() {
        if (action == Action.NONE) {
            action = Action.GREEDY;
            simulateGreedyPacking();
        } else {
            raiseWarningAlert();
        }
    }

    @FXML
    public void runMixing() {
        if (action == Action.NONE) {
            action = Action.MIXING;
            simulateMixing();
        } else {
            raiseWarningAlert();
        }
    }

    @FXML
    public void changeMixingState() {
        mixer.changeGravityStateAndResetSpheresVelocities();
    }

    @FXML
    public void stopMixing() {
        mixer.stop();
    }

    @FXML
    public void runDensePacking() {
        if (action == Action.NONE) {
            action = Action.DENSE;
            simulateDensePacking();
        } else {
            raiseWarningAlert();
        }
    }

    private void writeResultsToFile(File file) throws IOException {
        if (space != null) {
            new SpaceFiller(space).fillWithAllSpheres(bin);
            PrintWriter writer = new PrintWriter(file);
            writer.println(space.getXSize() + " " + space.getYSize() + " " + space.getZSize());
            for (int i = 0; i < space.getXSize(); i++) {
                for (int j = 0; j < space.getYSize(); j++) {
                    for (int k = 0; k < space.getZSize(); k++) {
                        int id = space.getCells()[i][j][k].getId();
                        if (id != 0) writer.println(i + " " + j + " " + k + " " + id);
                    }
                }
            }
            writer.close();
        }
    }

    private void openSettingsWindow() {
        boolean saved = SettingsWindow.displayAndSetProperties(settings);
        if (saved) {
            bin = new Bin(settings.getSizeX(), settings.getSizeY(), settings.getSizeZ());
            view = View.create(borderPane, bin);
            densityLabel.setText("0.0");
        }
    }

    private void simulateDensePacking() {
        Thread thread = new Thread(() -> {
            Platform.runLater(() -> stateLabel.setText(RUNNING));
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
            Platform.runLater(() -> {
                if (action == Action.DENSE) {
                    stateLabel.setText(READY);
                    action = Action.NONE;
                }
            });
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void simulateMixing() {
        mixer = SphereMixingSimulation.create(bin);
        MixingBallsTask task = new MixingBallsTask(mixer, bin);
        task.valueProperty().addListener((observable, oldValue, newValue) -> view.drawUsingStandardOvalFilling(bin.getSpheres()));
        task.setOnSucceeded(event -> {
            view.drawUsingStandardOvalFilling(bin.getSpheres());
            if (action == Action.MIXING) {
                stateLabel.setText(READY);
                action = Action.NONE;
            }
        });
        stateLabel.setText(RUNNING);
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    public Thread simulateGreedyPacking() {
        space = new Space(settings.getSizeX(), settings.getSizeY(), settings.getSizeZ());
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
        task.valueProperty().addListener((observable, oldValue, newValue) -> {
            view.drawUsingStandardOvalFilling(newValue);
            densityLabel.setText(String.valueOf(simulation.computeMathDensityLevel()));
        });
        task.setOnSucceeded(event -> {
                    view.drawUsingStandardOvalFilling(bin.getSpheres());
                    if (action == Action.GREEDY) {
                        stateLabel.setText(READY);
                        action = Action.NONE;
                    }
                }
        );
        Platform.runLater(() -> stateLabel.setText(RUNNING));
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
        return thread;
    }

    public static void raiseErrorAlert(String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/isim.png"));
        alert.setTitle("Error");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void raiseWarningAlert() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        ((Stage) alert.getDialogPane().getScene().getWindow()).getIcons().add(new Image("/isim.png"));
        alert.setTitle("WARNING");
        alert.setHeaderText("Can not perform called action");
        alert.setContentText("Called action cannot be performed because other simulation has not completed yet");
        alert.showAndWait();
    }
}
