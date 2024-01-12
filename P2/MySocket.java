import java.io.*;
import java.net.*;


/*
Aquesta classe és una versió de la classe Socket, però que encapsula totes les excepcions.
S'han creat dos constructors, dels quals la seva funcionalitat és la mateixa però els hi passem diferents paràmetres.
També s'han creat els mètodes pertinets per a llegir (readLine), escriure (write) i tancar la connexió (close).
*/

public class MySocket implements AutoCloseable{

	private Socket socket;
	private BufferedReader br; 
	private PrintWriter pw;
	//private String nick; 

	public MySocket(String hostAddress, int hostPort){
		try{ 
			//this.nick=nick;
			this.socket = new Socket(hostAddress, hostPort);
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream(), true); //Activem l'autoflush
		}catch(IOException e){
			e.printStackTrace();
		}
	}


	public MySocket(Socket s){
		try{
			this.socket = s;
			br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			pw = new PrintWriter(socket.getOutputStream(), true);
		}catch(IOException e){
			e.printStackTrace();
		}
	}

	public void write(String text){
		pw.println(text);
	}


	public String readLine(){
	/*
	Si es llença la excepció, retorna null, sinó retorna  el text llegit
	*/
		String text = null;
		try{
			text = br.readLine();
		}catch(IOException ex){
			ex.printStackTrace();
		}
		return text;
	}


	public void close(){
	/*
	Hem de tancar la connexió, pel que també tanquem el BufferedReader i PrintWriter corresponents
	*/
		try{
			br.close();
			pw.close();
			socket.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}