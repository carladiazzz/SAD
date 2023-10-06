//package P0;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
/*import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;*/

public class EditableBufferedReader extends BufferedReader{
   /* public interface CLibc extends Library {
        int system(String command);
    }
*/
public EditableBufferedReader(Reader text){
    super(text);
}

public void setRaw(){
 String[] comanda = {"/bin/sh", "-c", "stty raw -echo </dev/tty"}; // /bin/sh, per a executar el codi com a una cadena, i amb l'stty configurem el terminal en raw mode
    
    /*CLibc libc = Native.load("c", CLibc.class);
    int resultado = libc.system("stty raw -echo </dev/tty");
    if(resultado==0){
        System.err.println("no s ha pogut setRaw()");
    }
    else{
        System.out.print("mode Raw");
    }*/
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
         switch(car){
            case 127: //Backspace
            System.out.print("\u001b[1D");
            System.out.print("\u001b[P");
            line.backspace(); //(solo borra una vez en line)
            break;

        //default:
         }


        line.write(car);
        System.out.print((char) car);
        
    }


    unsetRaw();
    
return line.getPhrase();  
}

}





/* APUNTES

    Mover el cursor:
        \u001b[<n>A: Mueve el cursor hacia arriba n líneas.
        \u001b[<n>B: Mueve el cursor hacia abajo n líneas.
        \u001b[<n>C: Mueve el cursor hacia la derecha n columnas.
        \u001b[<n>D: Mueve el cursor hacia la izquierda n columnas.

    Posicionar el cursor:
        \u001b[<fila>;<columna>H: Posiciona el cursor en la fila y columna especificadas (1-based).

    Borrar la pantalla:
        \u001b[2J: Borra toda la pantalla.
        \u001b[1J: Borra desde el cursor hasta el principio de la pantalla.
        \u001b[0J: Borra desde el cursor hasta el final de la pantalla.

    Borrar la línea actual:
        \u001b[2K: Borra toda la línea actual.
        \u001b[1K: Borra desde el cursor hasta el principio de la línea.
        \u001b[0K: Borra desde el cursor hasta el final de la línea.

    Cambiar el color del texto y fondo:
        \u001b[<estilo>m: Cambia el estilo de texto y fondo. Algunos valores comunes son 0 (restablecer), 1 (negrita), 2 (dim), 3 (cursiva), 4 (subrayado), 30-37 (colores de texto) y 40-47 (colores de fondo).

    Restablecer los atributos:
        \u001b[0m: Restablece todos los atributos de estilo y color a sus valores predeterminados.



*/