//package P0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader{

public EditableBufferedReader(Reader text){
    super(text);
}

public void setRaw(){
 String[] comanda = {"/bin/sh", "-c", "stty raw -echo </dev/tty"}; // /bin/sh, per a executar el codi com a una cadena, i amb l'stty configurem el terminal en raw mode
    try{
        Runtime.getRuntime().exec(comanda).waitFor(); //Executem la comanda i esperem a que aquesta acabi
    }
    catch(IOException ex){
        System.err.println("Excepció a setRaw()");
            ex.printStackTrace();
    }
    catch(InterruptedException ex){
        System.err.println("Excepció a setRaw()");
            ex.printStackTrace();
    }
}

public void unsetRaw(){  //Fem el mateix procès que abans, pero ara per tornar al mode 'cooked'
 String[] comanda = {"/bin/sh", "-c", "stty cooked echo </dev/tty"};
    try{
        Runtime.getRuntime().exec(comanda).waitFor();
    }
    catch(IOException ex){
        System.err.println("Excepció a unsetRaw()");
        ex.printStackTrace();
    }
    catch(InterruptedException ex){
        System.err.println("Excepció a setRaw()");
            ex.printStackTrace();
    }
}

public int read(){
    int caracter=0;
    setRaw();
    try{
    caracter= super.read();
    }
    catch(IOException ex){
        ex.printStackTrace();
    }
    return caracter;
}

public String readLine(){
    setRaw();
    Line line = new Line();
    int car=0;
    while(car!=13){
        car= read();
        line.write(car);
        //System.out.print(line.getPhrase());
    }


    unsetRaw();
    
return String.valueOf(car);
    
}

}
