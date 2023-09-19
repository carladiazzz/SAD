package P0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

public class EditableBufferedReader extends BufferedReader

public EditableBufferedReader(Reader text){
    super(text);
}

public void setRaw(){
 String[] comanda = {"/bin/sh", "-c", "stty raw </dev/tty"}; // /bin/sh, per a executar el codi com a una cadena, i amb l'stty configurem el terminal en raw mode
 Runtime.getRuntime().exec(comanda).waitFor(); //Executem la comanda i esperem a que aquesta acabi
}

public unsetRaw(){  //Fem el mateix procès que abans, pero ara per tornar al mode 'cooked'
 String[] comanda = {"/bin/sh", "-c", "stty cooked </dev/tty"}
 Runtime.getRuntime().exec(comanda).waitFor();
}

public int read(){
    int caracter= super.read();
    return caracter;
}

public readLine(){

}