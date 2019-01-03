package client;

import java.io.IOException;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class GUI7 {  //fx=thread

	public Client7 client7;
	private Stage stage = new Stage(); //Finestra vuota
	private Scene scene;  //Insieme di tutti i nodi=elementi in una finestra
	private BorderPane borderpane = new BorderPane(); //Modo per disporre nodi nella scena. 2 fasce orizzontali e 3 verticali
	private HBox TopBox = new HBox(10); //Regione orizzontale della finestra
	private HBox BottomBox = new HBox(10); //10=distanza tra tutti gli elementi dell-Hbox
	public VBox LeftBox = new VBox(10);  // 10= 
	private VBox RightBox = new VBox(10);
	public TextArea chatarea = new TextArea();
	public TextArea usersarea = new TextArea();
	public TextField textfield = new TextField();
	private Button SendButton = new Button("Send!");
	public Button CloseButton = new Button("Quit chat!");
	private Button EmojiButton = new Button("Emote!");
	private Label UsersLabel = new Label("Users Online");//Etichetta
	private Canvas canvas = new Canvas(500, 52); //Tela nell Hbox poi nel borderpane, poi nella scena, poi nello stage
	private GraphicsContext gc = canvas.getGraphicsContext2D();//Tela 2D. GrahicsContext setta le proprieta grafiche del canvas
	public String GUIname = null;
	private EmoticonBox emoticonbox;
	public boolean emoticonbool = true;

	public GUI7() { //Costruttore

		LoginUI.display(this);  //Chiamata statica. Chiama display e gli passa tutta la GUI. 
		client7 = new Client7(this);

		stage.setOnCloseRequest(e -> { //Cosa fare quando si preme X
			e.consume(); //Consuma l evento (non capita)
			closegui();
		});

		EmojiButton.setOnAction(e -> {//Stabiliscono la funzionalita di un bottone
			if (emoticonbool) { //emoticonbool vera quando non aperte, falso quando aperta
				emoticonbox = new EmoticonBox(this);
			} else {
				e.consume();
			}
		});

		CloseButton.setOnAction(e -> {
			closegui();
		});

		SendButton.setOnAction(e -> {
			client7.Send(textfield.getText() + "\n");
			textfield.clear();
		});

		chatarea.setEditable(false); //Impedisce di scrivere nella textarea
		chatarea.setWrapText(true);//se il testo eccede la larghezza della textarea va a capo
		
		usersarea.setEditable(false);
	

		textfield.setPromptText("Be kind!");

		Image image = new Image("client/Immagine.png",100,40,false,false);//Due false per non rispettare le proporzioni dell immagini
		
		gc.setFill(Color.RED);//Scritta rossa
		gc.setStroke(Color.BLACK); //bordo scritta nero
		gc.setLineWidth(2); 
		Font theFont = Font.font("Times New Roman", FontWeight.BOLD, 48);
		gc.setFont(theFont);
		gc.fillText("TCFChat2018!", 60, 50);
		gc.strokeText("TCFChat2018!", 60, 50);
		gc.drawImage(image, 390, 10);
		

		TopBox.getChildren().addAll(canvas); // aggiunge i pezzi
		LeftBox.getChildren().addAll(UsersLabel, usersarea);
		RightBox.getChildren().addAll(CloseButton);
		BottomBox.getChildren().addAll(textfield, SendButton, EmojiButton);

		borderpane.setPadding(new Insets(10, 10, 10, 10));

		borderpane.setTop(TopBox);
		borderpane.setLeft(LeftBox);
		borderpane.setCenter(chatarea);
		borderpane.setRight(RightBox);
		borderpane.setBottom(BottomBox);
		
	

		BorderPane.setMargin(TopBox, new Insets(10, 10, 10, 10));
		BorderPane.setMargin(LeftBox, new Insets(10, 10, 10, 10));
		BorderPane.setMargin(RightBox, new Insets(10, 10, 10, 10));
		BorderPane.setMargin(BottomBox, new Insets(10, 10, 10, 10));
		BorderPane.setMargin(borderpane.getCenter(), new Insets(10, 10, 10, 10));

		scene = new Scene(borderpane);
		
		stage.setMinWidth(1000);
		stage.setMinHeight(500);
		stage.setScene(scene);
		stage.setTitle(GUIname);
		stage.show();

		if (GUIname == null) {
			closegui();
		}

	}

	public void closegui() {
		if(GUIname != null) {
		try {

			client7.sout.write("quit \n".getBytes());
			client7.Nameout.write("quit \n".getBytes());
		} catch (IOException e) {
			
		}
		}
		
		if (!emoticonbool) {
			emoticonbox.stage.close();
		}
		
		stage.close();
	}
	
	
	
	
	
	
}
