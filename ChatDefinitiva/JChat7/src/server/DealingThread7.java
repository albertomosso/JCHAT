package server;

import java.io.BufferedReader; //Importa librerie varie
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class DealingThread7 implements Runnable { //Runnable=Interfaccia propria di java=richiede l implementazione del metodo run. 

	private Thread DealingThread;//Quando si crea un oggetto DealingThread parte il corrispondente metodo run, che parte assieme agli altri metodi run, ad essi collegati
	private Socket DealingSocket;//Def oggetti di classi importate
	private Socket NameSocket;
	private BufferedReader br;
	private BufferedReader Namebr;
	public OutputStream Nameout; //A ogni socket  associato un inputstream ed un outputstream, byte in input e output
	public OutputStream out;
	public String name = null;
	private boolean running = true;

	public DealingThread7(Socket socket, Socket namesocket) {//Costruttore della classe DealingThread. 2 socket -1 nomi 1 messaggi
		DealingThread = new Thread(this, "DealingThread"); //Vedi classe Thread. This=Oggetto DealingThread appena creato 
		this.DealingSocket = socket;
		this.NameSocket = namesocket;
		try {
			this.br = new BufferedReader(new InputStreamReader(DealingSocket.getInputStream()));//Inizializza alcune variabili private definite prima
			this.Namebr = new BufferedReader(new InputStreamReader(NameSocket.getInputStream()));//1 restituisce IS 2 lettura 3 salva in un buffer
			this.out = DealingSocket.getOutputStream(); //Restituzione outputstream=flusso di dati in uscita
			this.Nameout = NameSocket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		getCheer();
		
		DealingThread.start(); //Parte il metodo run della riga 81
	}//Chiusura costruttore

	private void getCheer() { //Riceve il nome(login) dal client. Il metodo hiamato una sola volta
		try {
			String ReceivedName = br.readLine();
			this.name = ReceivedName.substring(ReceivedName.indexOf(")") + 2); // Trimmo ora
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	private void Names() {
		Thread NameThread = new Thread("NameThread") {//Def classe anonima
			public void run() { 
				while (running) { //per ora true. Verr' settato a false quando clicco quit
					String names = "";
					for (DealingThread7 deal1 : Server7.dealingthreads) {//
						names += deal1.name + " ! "; //Stringa di nomi online separati da un !
					}
					names += "\n"; //Aggiunge i nomi dei contatti online alla textarea andando a capo

					try {
						Nameout.write(names.getBytes()); //Manda la stringa di nomi al Client
					} catch (IOException e) {
						e.printStackTrace();
					}
					try {  //Aspetta 5 secondi
						Thread.sleep(5000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}//Chiusura While
				try { //Il running=false solo se nel run dell Override un utente lascia la conversazione
					Nameout.write("quit \n".getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		NameThread.start();
	}//Chiusura Names

	@Override
	public void run() { //Riga 36

		Names();

		String ReceivedText = null;  //Il codice successivo accade in contemporanea con Names, che e un Thread
		while (true) {
			try {
				ReceivedText = br.readLine() + "\n";

			} catch (IOException e) {
				//e.printStackTrace();
				
				
			}
			if (!ReceivedText.startsWith("quit")) {
				for (DealingThread7 deal : Server7.dealingthreads) {//deal=iteratore
					try {
						deal.out.write(ReceivedText.getBytes());
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

			} else {
				try {

					this.out.write("quit \n".getBytes());
				} catch (IOException e) {
					e.printStackTrace();
				}

				Server7.dealingthreads.remove(this);

				try {
					running = false;
					br.close();
					DealingSocket.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

				break;
			}

		}

	}

}
