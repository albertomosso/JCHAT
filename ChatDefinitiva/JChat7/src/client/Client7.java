package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.time.Instant;
import java.util.Calendar;

public class Client7 {

	private Socket ClientSocket;
	private Socket NameSocket;
	private int ClientPort = 1600;
	private int NamePort = 1601;
	public String name;
	private BufferedReader br;
	private BufferedReader Namebr;
	public OutputStream sout;
	public OutputStream Nameout;
	private GUI7 gui;
	private boolean running= true;

	public Client7(GUI7 passedGui) {
		try {
			
			this.gui = passedGui;
			this.name = gui.GUIname;
			if(name != null) {
			ClientSocket = new Socket(InetAddress.getLocalHost(), ClientPort);
			NameSocket = new Socket(InetAddress.getLocalHost(), NamePort);
			
			
			this.br = new BufferedReader(new InputStreamReader(ClientSocket.getInputStream()));
			this.Namebr = new BufferedReader(new InputStreamReader(NameSocket.getInputStream()));
			this.Nameout = NameSocket.getOutputStream();
			this.sout = ClientSocket.getOutputStream();
			if (name != null) {
				ClientStarter7.textarea.appendText(ClientStarter7.i++ + ") " + "Name: " + name + " LocalAddress: "
						+ ClientSocket.getInetAddress() + " Local Port: " + ClientSocket.getLocalPort()
						+ " Remote Port: " + ClientSocket.getPort() + "\n");
			}
			funzioni();
			}
		} catch (IOException e) {
			gui.GUIname = null;
			AlertBox.display(3);
		}
	}

	public void Send(String msg) {
		if(running) {
		try { 
			
			String msg1 = name + " (" + time() + ")" + ": " + msg;
			sout.write(msg1.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		}
		
	}

	public void Listen() {
		Thread ListenThread = new Thread("ListenThread") {
			public void run() {

				while (true) {
					String ReceivedText = null;
					try {
						ReceivedText = br.readLine();
						if (!ReceivedText.startsWith("quit")) {

							gui.chatarea.appendText(ReceivedText + "\n");
						}
						if (ReceivedText.startsWith("quit")) {
							br.close();
							ClientSocket.close();
                          
                         
							break;
						}

					} catch (IOException e) {
						
						running = false;
						 gui.chatarea.appendText("*********************************** \nSYSTEM: Server closed, please quit \n***********************************");
						break;
					}

				}

			}
		};
		ListenThread.start();
	}

	private void Names() {
		Thread NamesThread = new Thread("NamesThread") {
			public void run() {
				while (true) {
					String names = null;
					String names1 = null;
					try {
						names = Namebr.readLine();
						names1= names.replace("null", "");
					} catch (IOException e) {
						
						break;
						
					}
					if (!names.startsWith("quit")) {
						gui.usersarea.clear();
						gui.usersarea.appendText(names1.replace("!", "\n"));
					} else {
						try {
							Namebr.close();
							NameSocket.close();
							break;
						} catch (IOException e) {
							e.printStackTrace();
							
						}

					}
				}
			}

		};
		NamesThread.start();
	}

	private String time() {
		Instant now = Instant.now();
		return now.toString().substring(now.toString().indexOf("T") + 1, now.toString().indexOf("."));
	}

	private void funzioni() {
		Send(name + "\n");
		Listen();
		Names();
	}
}
