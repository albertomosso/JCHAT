package client;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ClientStarter7 extends Application { //Classe che estende Application. Fa partire i Client piu facilmente. non ha nlla in comune con la chat

	private Stage stage = new Stage();
	private Scene scene;
	private GridPane gridpane = new GridPane();
	private TextField textfield = new TextField();
	static TextArea textarea = new TextArea();
	private Button StartButton = new Button("Start Client!");
	private Button CloseButton = new Button("Exit");

	private Label AreaLabel = new Label("History: ");

	static int i = 0;

	public void start(Stage primaryStage) {  //Eventuale errore. Def un nuovo stage, non quello private

		stage.setOnCloseRequest(e -> {  //Espressioni lambda. Cosa accade quando si preme la X rossa
			e.consume();
			closeprog();
		});

		this.CloseButton.setOnAction(e -> { //Premi il pulsante di chiusara e si esce dalla chat
			closeprog();
		});

		this.StartButton.setOnAction(e -> {

			GUI7 gui = new GUI7();
			textfield.clear(); // Prima parte l-interfaccia grafica e poi il client

		});

		textarea.setEditable(false); //Impedisce all-utente di scrivere sulla textarea
		gridpane.setPadding(new Insets(10, 10, 10, 10));  //Fa in modoil bordo della finestra ci sia un margine di 10 pixel dalle 4 direzioni

		gridpane.add(StartButton, 0, 0); //aggiungi il pulsante alla griglia nella posizione 0 0
		gridpane.add(AreaLabel, 0, 1);
		gridpane.add(textarea, 0, 2);
		gridpane.add(CloseButton, 0, 3);

		scene = new Scene(gridpane);//Aggiungo il gridpane alla scena
		stage.setScene(scene);
		stage.setTitle("ClientStarter 7.0");
		stage.show();

	}

	private void closeprog() {
		if (AlertBox.display(2)) { //Mostra la finestra sei sicuro. Se restituisce true, allora chiusura stage
			stage.close(); 
			
		}

	}

	public static void main(String[] args) { //Parte il programma
		launch();//Lancia start, prima init, start e poi lo stop.
	}

}
