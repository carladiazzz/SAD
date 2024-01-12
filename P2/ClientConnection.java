import java.util.Scanner;


public class ClientConnection{

	public static void main(String[] args){
                
		MySocket mSocket = new MySocket("localhost", Integer.parseInt(args[0]));
                Scanner sc= new Scanner(System.in); 
                System.out.print("Introdueix el teu nom: ");
                String nick= sc.nextLine();  
 
		SwingClient client = new SwingClient(nick, mSocket);
		sc.close();
		client.createAndShowGUI(nick);


		// Output Thread
		new Thread(){
			public void run(){
				String message;
				while((message = mSocket.readLine()) != null){
					 client.addMessage(message); 
				}
			}
		}.start();
	}
}


