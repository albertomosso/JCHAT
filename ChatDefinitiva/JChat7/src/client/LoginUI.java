package client;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class LoginUI {

	public static void display(GUI7 gui) {

		String name = null;
		Stage stage = new Stage();
		Scene scene;
		GridPane gridpane = new GridPane();
		Label NameLabel = new Label("Please enter your name here:");
		TextField textfield = new TextField();
		Button OKButton = new Button("Start Chatting!");

		stage.setOnCloseRequest(e -> {
			e.consume();
			stage.close();
		});

		OKButton.setOnAction(e -> {
			if (!textfield.getText().trim().isEmpty()) {
				gui.GUIname = textfield.getText().trim();
				stage.close();
			} else {
				AlertBox.display(1);
				textfield.clear();
			}
		});

		stage.initModality(Modality.APPLICATION_MODAL);

		textfield.setPromptText("Name");

		gridpane.setPadding(new Insets(10, 10, 10, 10));

		gridpane.addRow(0, NameLabel, textfield);
		gridpane.addRow(1, OKButton);

		scene = new Scene(gridpane);
		stage.setScene(scene);
		stage.setTitle("Login");
		stage.showAndWait();

	}
}
