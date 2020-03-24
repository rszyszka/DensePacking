package pl.edu.agh.msm.dense.packing.view;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class MainApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader mainLoader = new FXMLLoader(this.getClass().getResource("/MainView.fxml"));
        Parent mainNode = mainLoader.load();
        Scene scene = new Scene(mainNode);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Dense Packing");
        primaryStage.show();
    }

}
