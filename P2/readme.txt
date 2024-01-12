En aquesta pràctia s'ha fet un xat client-servidor utilitzant la bilblioteca Swing i la API NIO (Non-blocking I/O) de Java. 

El servidor utilitza NIO per manejar múltiples clients de manera eficient mitjançant un Selector. Accepta noves connexions i registra clients per a operacions de lectura/escriptura. També maneja la lectura de missatges dels clients i en fa una difusió (broadcast) dels missatges a tots els clients connectats.

La interfície gràfica del client (SwingClient), utlilitza Swing per crear una interfície gràfica d'usuari (GUI) amb una àrea de missatges i una llista d'usuaris. Permet als usuaris enviar missatges al servidor i actualitza la llista d'usuaris connectats cada cop que es rep un missatge.

La llista d'usuaris a la interfície gràfica (JListSW) gestiona una llista d'usuaris connectats amb un JList. Permet afegir i treure usuaris de la llista.

La connexió del client (ClientConnection) estableix una connexió amb el servidor mitjançant la classe MySocket ja implementada a la pràctica anterior. Utilitza SwingClient per gestionar la interfície gràfica del client. Aquesta inicia un fil per llegir missatges del servidor i actualitzar la interfície gràfica.



