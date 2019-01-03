package server; //Nuovo pacchetto

import java.io.BufferedReader;  //Importiamo le librerie seguenti
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server7 {

	private ServerSocket serversocket; //
	private ServerSocket NameSocket;
	private int ServerPort = 1600;  //Aver inizializzato il socket del client sulla stessa porta del server costituisce la connessione
	private int NamePort = 1601;//Due serversocket diversi
	static ArrayList<DealingThread7> dealingthreads = new ArrayList<DealingThread7>(); //ArrayList (array di qualsiasi oggetto con lunghezza indefinita) di oggetti DealingThreads 
	public boolean running = true;

	public Server7() {  //Costruttore della classe Server7
		try { //Esegue le espressioni
			serversocket = new ServerSocket(ServerPort);  //Def la variabile, oggetto, serversocket, importando la classe, Crea un serversochet e lo associa alla porta 1600
			 System.out.println("serversocket established on IP: " +                     //Parte il Server
			  serversocket.getInetAddress() + " on port: " + serversocket.getLocalPort() +
			  "\n");    //Solo un debug, con metodi classe ServerSocket
			 
			NameSocket = new ServerSocket(NamePort); //Claudio Perche due Porte
			funzioni(); //Chiama ListenForJoin attreverso funzioni 
		} catch (IOException e) { //Se avvengono determinate condizioni, si esegue quello nel catch
			e.printStackTrace(); //Vedi Help
		}

	}
	

	
	
	public void ListenForJoin() { //Metodo pubblico
		Thread ListenForJoinThread = new Thread("ListenForJoinThread") {  //Nella classe anonima implementiamo il run del Thread, Classe anonima= implementa il metodo run. Classe anonima=
			public void run() { // Implementazione metodo run della classe Thread
				while (running) {  //running=true, definito sopra
					try {

						Socket newSocket = serversocket.accept(); //Blocca l-esecuzione del thread e attende che uno dei due socket del client si connetta (che avviene nella classe Client7 riga32)
						Socket nameSocket = NameSocket.accept();  //2 socket= 1 si occuppa della messaggistica e una dei nomi dei contatti online
						dealingthreads.add(new DealingThread7(newSocket, nameSocket)); //Aggiungo all ArrayList un oggetto DealingThread7
                                                                                       //Riga 21 della classe DealingThread7
					} catch (IOException e) {
						e.printStackTrace();
					}
				} //Chiusura While
				System.out.println("ListebnForJoin expired");
			} //Chiusura Run
		};
		ListenForJoinThread.start(); //Usi il metodo Start della classe Thread sull oggetto ListenForJoinThread facendo partire il metodo run di ListenForJoin Thread
	}//Chiusura ListenForJoin

	private void funzioni() {  //Non sapevamo a priori numero funzioni di chiamare
		ListenForJoin(); //Vedi sopra

	}

	public static void main(String[] args) {
		Server7 server7 = new Server7(); //Creiamo un oggetto Server7 con il costruttore

	}

}
