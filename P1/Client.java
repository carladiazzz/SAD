import java.lang.*;
import java.io.*;
import java.util.*;

/*
Aquesta classe serveix per crear un Client. Aquest, compta amb dos Threads; un que llegeix el text
per terminal i l'envia al servidor i un altre que rep els missatges del servidor i els mostra per pantalla.
Per executar, es fa de la següent manera: java Client <host> <port>
*/

public class Client{

	public static void main(String[] args){
		MySocket s = new MySocket(args[0], Integer.parseInt(args[1]));  //host, port
		
		//Thread d'entrada
		new Thread(){
			public void run(){
				BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
				String missatge;
				try{
					while((missatge = in.readLine()) != null){
						s.write(missatge);
					}
					s.close();
				}catch(IOException ex){
					ex.printStackTrace();
				}
			}}.start();


		//Thread de sortida
		new Thread(){
			public void run(){
				String missatge;
				while((missatge = s.readLine()) != null){
					System.out.println(missatge); 
				}
			}}.start();
	}

}
