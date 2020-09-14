package pl.edu.agh.msm.dense.packing.view;

import javafx.collections.FXCollections;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import pl.edu.agh.msm.dense.packing.model.InitialConfiguration;
import pl.edu.agh.msm.dense.packing.model.PenaltyType;
import pl.edu.agh.msm.dense.packing.model.SphereGenerator;

import java.util.concurrent.atomic.AtomicBoolean;

public class SettingsWindow {

    public static boolean displayAndSetProperties(SimulationSettings settings) {
        Stage settingsWindow = new Stage();

        settingsWindow.initModality(Modality.APPLICATION_MODAL);
        settingsWindow.setTitle("Choose simulation properties");
        settingsWindow.setMinWidth(150);

        HBox binSizeContainer = new HBox(5);
        binSizeContainer.setAlignment(Pos.CENTER);
        TextField xSizeTF = new TextField(String.valueOf(settings.getSizeX()));
        TextField ySizeTF = new TextField(String.valueOf(settings.getSizeY()));
        TextField zSizeTF = new TextField(String.valueOf(settings.getSizeZ()));
        binSizeContainer.getChildren().addAll(
                new Label("xSize: "), xSizeTF,
                new Label("ySize: "), ySizeTF,
                new Label("zSize: "), zSizeTF
        );

        HBox radiusRangeContainer = new HBox(5);
        radiusRangeContainer.setAlignment(Pos.CENTER);
        TextField minRTF = new TextField(String.valueOf(settings.getMinR()));
        TextField maxRTF = new TextField(String.valueOf(settings.getMaxR()));
        radiusRangeContainer.getChildren().addAll(
                new Label("minR: "), minRTF,
                new Label("maxR: "), maxRTF
        );

        HBox penaltyContainer = new HBox(5);
        penaltyContainer.setAlignment(Pos.CENTER);
        ComboBox<PenaltyType> penaltyTypeComboBox = new ComboBox<>();
        penaltyTypeComboBox.setItems(FXCollections.observableArrayList(PenaltyType.values()));
        penaltyTypeComboBox.getSelectionModel().select(settings.getPenaltyType());
        TextField penaltyValueTF = new TextField(String.valueOf(settings.getPenaltyValue()));
        penaltyContainer.getChildren().addAll(
                new Label("Type: "), penaltyTypeComboBox,
                new Label("Value: "), penaltyValueTF
        );

        HBox buttonContainer = new HBox(20);
        buttonContainer.setAlignment(Pos.CENTER);
        AtomicBoolean saved = new AtomicBoolean(false);
        Button saveButton = new Button("Save");
        saveButton.setOnAction(event -> {
            saved.set(true);
            settingsWindow.close();
        });
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> {
            saved.set(false);
            settingsWindow.close();
        });
        buttonContainer.getChildren().addAll(saveButton, cancelButton);

        HBox sphereGeneratorContainer = new HBox(5);
        sphereGeneratorContainer.setAlignment(Pos.CENTER);
        ComboBox<SphereGenerator.Type> sphereGeneratorComboBox = new ComboBox<>();
        sphereGeneratorComboBox.setItems(FXCollections.observableArrayList(SphereGenerator.Type.values()));
        sphereGeneratorComboBox.getSelectionModel().select(settings.getSphereGeneratorType());
        TextField oscillationStepTF = new TextField(String.valueOf(settings.getOscillationStep()));
        sphereGeneratorContainer.getChildren().addAll(
                new Label("Type: "), sphereGeneratorComboBox,
                new Label("Oscillation step: "), oscillationStepTF
        );

        ComboBox<InitialConfiguration.Type> initialConfigurationComboBox = new ComboBox<>();
        initialConfigurationComboBox.setItems(FXCollections.observableArrayList(InitialConfiguration.Type.values()));
        initialConfigurationComboBox.getSelectionModel().select(settings.getInitialConfigurationType());

        VBox layout = new VBox(10);
        layout.getChildren().addAll(
                new Separator(),
                new Label("Bin size"), binSizeContainer, new Separator(),
                new Label("Radius range"), radiusRangeContainer, new Separator(),
                new Label("Penalty"), penaltyContainer, new Separator(),
                new Label("Sphere generator"), sphereGeneratorContainer, new Separator(),
                new Label("Initial configuration"), initialConfigurationComboBox, new Separator(),
                buttonContainer, new Separator()
        );
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                saved.set(true);
                settingsWindow.close();
            } else if (event.getCode() == KeyCode.ESCAPE) {
                saved.set(false);
                settingsWindow.close();
            }
        });
        settingsWindow.setScene(scene);
        settingsWindow.showAndWait();

        if (saved.get()) {
            try {
                settings.setSize(Integer.parseInt(xSizeTF.getText()), Integer.parseInt(ySizeTF.getText()), Integer.parseInt(zSizeTF.getText()));
                settings.setRadiusRange(Integer.parseInt(minRTF.getText()), Integer.parseInt(maxRTF.getText()));
                settings.setPenalty(penaltyTypeComboBox.getValue(), Double.parseDouble(penaltyValueTF.getText()));
                settings.setSphereGeneratorProperties(sphereGeneratorComboBox.getValue(), Integer.parseInt(oscillationStepTF.getText()));
                settings.setInitialConfigurationType(initialConfigurationComboBox.getValue());
            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid input data");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                displayAndSetProperties(settings);
            }
        }
        return saved.get();
    }

}
