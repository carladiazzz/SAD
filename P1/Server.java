import java.lang.*;
import java.io.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/*
Aquesta classe serveix per crear un servidor. Per tal d'atendre als clients, s'executa constanrment
el mètode accept() de MyServerSocket. Cada Client compta amb el seu propi Thread. Al principi se li
pregunta el nick. D'aquesta manera, s'afegeix al diccionari el client, amb el seu nick i el seu Socket.
Després, s'executa el mètode broadcast, que mostra per pantalla els missatges enviats per qualsevol altre client. 
Finalment, quan el client surt del programa, tanquem el seu Socket pertinent i l'eliminiem del diccionari.
Per tal de fer servir sincronitzadament al diccionari, aquest s'ha creat fent servir la classe ConcurrentHashMap.

Per executar, es fa de la següent manera: java Server <port>
*/


public class Server{
	private static ConcurrentHashMap<String, MySocket> usuaris = new ConcurrentHashMap<>();
	
	public static void main(String[] args){

		MyServerSocket ss = new MyServerSocket(Integer.parseInt(args[0]));
		System.out.println("Servidor inciat.");

		while(true){
			MySocket client = ss.accept();

			new Thread(){
				public void run(){
					client.write("Introdueix el teu nick: ");
					String nick = client.readLine();
					client.write("T'has connectat al xat!");
					addUsuari(nick, client);
					String text;
					while((text = client.readLine()) != null){
						broadcast(text, nick);
						System.out.println(nick + " diu: "+ text);

					}
					removeUsuari(nick);
					client.close();
				}
			}.start();
		}
	}


	public static void addUsuari(String usuari, MySocket s){
		System.out.println(usuari+" ha entrat al xat");
		usuaris.put(usuari, s);
	}


	public static void removeUsuari(String usuari){
		System.out.println(usuari+" ha sortit del xat");
		usuaris.remove(usuari);
	}


	public static void broadcast(String missatge, String nick){
		MySocket client= usuaris.get(nick);
		/*Iterator iterator = usuaris.entrySet().iterator();
		while (iterator.hasNext()) {
    		Map.Entry entry = (Map.Entry) iterator.next();
    			if(nick== entry.getKey())
					client.write(nick+"> "+missatge);
		}*/
		for (Map.Entry<String, MySocket> entry : usuaris.entrySet()) {
            String usuarioActual = entry.getKey();
            MySocket socketActual = entry.getValue();
			if(usuarioActual!=nick){
				socketActual.write(nick+"> "+missatge);
			}
		}
	}
}