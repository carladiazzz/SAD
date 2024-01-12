import java.io.*;
import java.net.*;

/*
Aquesta classe és una versió de la classe ServerSocket, però que encapsula totes les excpecions.
S'ha creat el seu pertint constructor, i els mètodes pertinets per a escoltar conexions (accept)
i tancar la connexió (close).
*/

public class MyServerSocket implements AutoCloseable{

	private ServerSocket ss;

	public MyServerSocket(int port){
		try{
			this.ss = new ServerSocket(port);
		}catch(IOException e){
			e.printStackTrace();
		}
	}


	public MySocket accept(){
		try{
			return new MySocket(ss.accept());
		}catch(IOException e){
			e.printStackTrace();
		}
		return null;
	}


	public void close(){
		try{
			this.ss.close();
		}catch(IOException e){
			e.printStackTrace();
		}
	}

}

