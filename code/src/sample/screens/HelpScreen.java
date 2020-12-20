package sample.screens;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import sample.ScreenManager;

import java.io.IOException;

public class HelpScreen extends Screen {

    private Label infoLabel;
    AnchorPane helpScreen = FXMLLoader.load(getClass().getResource("../layouts/MainMenuScreen.fxml"));

    public HelpScreen(ScreenManager screenManager) throws IOException {
        super(screenManager);
        infoLabel = new Label("HELP");
        initializeScene();
    }

    private void initializeScene() {
        VBox vbox = (VBox) helpScreen.getChildren().get(1);
        infoLabel.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(infoLabel);
        vbox.setAlignment(Pos.CENTER);
        scene = new Scene(helpScreen);
    }

    @Override
    public Scene getScene() {
        return scene;
    }
}
