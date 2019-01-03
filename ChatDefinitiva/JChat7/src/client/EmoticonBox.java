package client;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class EmoticonBox {

	public Stage stage = new Stage();
	private GUI7 gui;

	public EmoticonBox(GUI7 gui7) {
		this.gui = gui7;
		gui.emoticonbool = false;
		display();
	}

	private void display() {

		Scene scene;
		GridPane gridpane = new GridPane();
		ArrayList<Button> buttons = new ArrayList<Button>();
		Button b00 = new Button(":)");
		Button b01 = new Button(";)");
		Button b02 = new Button(":(");
		Button b10 = new Button(":0");
		Button b11 = new Button("XD");
		Button b12 = new Button("B)");
		Button b20 = new Button(":3");
		Button b21 = new Button(":$");
		Button b22 = new Button("''_''");

		buttons.add(b00);
		buttons.add(b01);
		buttons.add(b02);
		buttons.add(b10);
		buttons.add(b11);
		buttons.add(b12);
		buttons.add(b20);
		buttons.add(b21);
		buttons.add(b22);

		for (Button b : buttons) {
			b.setOnAction(e -> {
				gui.textfield.appendText(b.getText());
			});
		}

		stage.setOnCloseRequest(e -> {
			gui.emoticonbool = true;
			stage.close();
		});

		gridpane.setPadding(new Insets(10, 10, 10, 10));
		gridpane.setVgap(1);
		gridpane.setHgap(1);

		gridpane.addRow(0, b00, b01, b02);
		gridpane.addRow(1, b10, b11, b12);
		gridpane.addRow(2, b20, b21, b22);

		scene = new Scene(gridpane,250,100);
		stage.setScene(scene);
		stage.setTitle("Emoticon");
		stage.show();
	}

}
