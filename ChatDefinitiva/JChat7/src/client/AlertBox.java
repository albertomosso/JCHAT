package client;

import javafx.geometry.Insets; //Importa la libreria geometry
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

	static boolean bool;

	public static boolean display(int i) {

		int j = i;
		Stage stage = new Stage();
		Scene scene;
		GridPane gridpane = new GridPane();
		gridpane.setPadding(new Insets(10, 10, 10, 10));
		gridpane.setHgap(10);
		gridpane.setVgap(10);
		stage.initModality(Modality.APPLICATION_MODAL);

		switch (j) {
		case 1:

			Label NameNotValid = new Label("Please insert a valid name: it suffices for it not to be an empty String");
			Button Understood = new Button("Got it");

			Understood.setOnAction(e -> {
				CloseAlertBox(stage);
			});

			gridpane.add(NameNotValid, 0, 0);
			gridpane.add(Understood, 0, 1);

			scene = new Scene(gridpane);
			stage.setTitle("Name Not Valid!");
			stage.setScene(scene);
			stage.showAndWait();

			break;

		case 2:

			Label AreYouSure = new Label("Are You Sure?");
			Button YesButton = new Button("Yes");
			Button NoButton = new Button("No");

			YesButton.setOnAction(e -> {
				bool = true;
				CloseAlertBox(stage);
			});

			NoButton.setOnAction(e -> {
				bool = false;
				CloseAlertBox(stage);
			});

			gridpane.add(AreYouSure, 0, 0);
			gridpane.addRow(1, YesButton, NoButton);

			scene = new Scene(gridpane,370,100);
			stage.setTitle("Thanks for using TCFChat2018!");
			stage.setScene(scene);
			stage.showAndWait();
			break;

			
		case 3:
			
			Label ServerNotFound = new Label("Server unreachable");
			Button OK = new Button("OK");
			
			OK.setOnAction(e->{
				CloseAlertBox(stage);
			});
			
			gridpane.add(ServerNotFound, 0, 0);
			gridpane.add(OK, 0, 1);
			
			scene =new Scene(gridpane);
			stage.setScene(scene);
			stage.showAndWait();
			
			
		}
		return bool;
	}

	private static void CloseAlertBox(Stage stage) {

		stage.close();
	}

}
