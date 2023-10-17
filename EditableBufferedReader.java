//package P0;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;

public class EditableBufferedReader extends BufferedReader{

private Line line;
private View vista;

public EditableBufferedReader(Reader text){
    super(text);
    this.line = new Line();
    this.vista= new View();
}

public void setRaw() throws IOException{ 
    try{
        String[] comanda = {"/bin/sh", "-c", "stty raw -echo </dev/tty"}; // /bin/sh, per a executar el codi com a una cadena, i amb l'stty configurem el terminal en raw mode
        Runtime.getRuntime().exec(comanda).waitFor(); //Executem la comanda i esperem a que aquesta acabi

    }catch(InterruptedException ex){
        ex.printStackTrace();
    }
}

public void unsetRaw() throws IOException{  //Fem el mateix procès que abans, pero ara per tornar al mode 'cooked'
 try{
 String[] comanda = {"/bin/sh", "-c", "stty cooked echo </dev/tty"};
 Runtime.getRuntime().exec(comanda).waitFor();
 
 }catch(InterruptedException ex){
    ex.printStackTrace();
 }
}

public int read() throws IOException{
    int caracter=0;
    setRaw();
    caracter= super.read();
    if(caracter==Keys.ESC){
        caracter=super.read(); //Obviem el "["
        if(caracter==Keys.ESTILO){
            caracter= Keys.xESTILO;
            return caracter;
        }

        caracter= super.read();
        if(caracter>64){//Si després llegim una lletra
            switch (caracter) {
                case Keys.INSERT:
                    caracter = Keys.xINSERT;
                    break;
                case Keys.RIGHT:
                    caracter = Keys.xRIGHT;
                    break;
                case Keys.LEFT:
                    caracter = Keys.xLEFT;
                    break;
                case Keys.INICIO:
                    caracter= Keys.xINICIO;
                    break;
                case Keys.FIN:
                    caracter= Keys.xFIN;
                    break;
            } 
        }else{ //Si llegim un número
            switch (caracter){
                case Keys.INSERT:
                caracter=Keys.xINSERT;
                break;
            }
            super.read(); //Obviem el "~"
        }
    }

    return caracter;
}

public String readLine() throws IOException{  //Al tirar para atras se buguea y en vez de imprimir al final "line is ..." en line imprime valores escritos antes
    setRaw();
    int caracter=0;
    while(caracter!=Keys.RETURN){
        caracter= read();
         switch(caracter){
            case Keys.BACKSPACE: 
                System.out.print("\u001b[1D"); //Cursor a la dreta
                System.out.print("\u001b[P"); //Esborrem el contingut del cursor
                line.backspace();
                break;

            case Keys.xRIGHT:
                line.moveRight();
                System.out.print("\u001b[1C");//Movem cursor a la dreta
                break;

            case Keys.xLEFT:
                line.moveLeft();
                System.out.print("\u001b[1D"); //Movem cursor a la esquerra
                break;

            case Keys.xINSERT:
                System.out.print("\u001b[0P"); //Esborrem el contingut del cursor
                caracter= read(); //Llegim el seguent caracter insertat
                line.insert((char)caracter);
                break;

            case Keys.xINICIO:
                int cursorPosition= line.getCursorPosition();
                System.out.print("\u001b["+cursorPosition+"D"); //Movem el cursor a la esquerra 'cursorPosition' cops
                line.moveToStart();
                break;

            case Keys.xFIN:
                int numLetters= line.getNumLetters();
                System.out.print("\u001b[0;"+numLetters+"H"); //Movem el cursor al final de la frase
                line.moveToEnd();
                break;

            case Keys.xESTILO:
                vista.restablish();
                System.out.println("\n\rQue estilo desea?\n\r0 (restablecer)\n\r1 (negrita)\n\r2 (dim)\n\r3 (cursiva)\n\r4 (subrayado)\n\r5 (colores de texto)\n\r6 (colores de fondo)\n\r");
                caracter=read();
                switch(caracter){
                    case 48: //0
                        System.out.println("Restablecido\n\r");
                        vista.restablish();
                        caracter=0; //Para que no se muestre el número escrito en pantalla
                        break;
                    case 49: //1
                        vista.bold();
                        caracter=0; //Para que no se muestre el número escrito en pantalla
                        break;
                    case 50: //2
                        vista.dim();
                        caracter=0; //Para que no se muestre el número escrito en pantalla
                        break;
                    case 51: //3
                        vista.italics();
                        caracter=0; //Para que no se muestre el número escrito en pantalla
                        break;
                    case 52: //4
                        vista.underline();
                        caracter=0; //Para que no se muestre el número escrito en pantalla
                        break;
                    case 53: //5
                        System.out.println("\n\rSelecciona un color de texto:\n\r0: Negro\n\r1: Rojo \n\r2: Verde \n\r3: Amarillo \n\r4: Azul \n\r5: Magenta (púrpura) \n\r6: Cian (verde azulado) \n\r7: Blanco\n\r");
                        caracter=read();
                           if(caracter>47 && caracter<56) //Si el caracter es un nombre entre 0 i 7
                                vista.changeColor(caracter);
                           else
                                System.out.print("Caracter incorrecto!\n\r");
                        caracter=0; //Para que no se muestre el número escrito en pantalla
                        break;
                    case 54: //6
                        System.out.println("\n\rSelecciona un color de fondo:\n\r0: Negro\n\r1: Rojo \n\r2: Verde \n\r3: Amarillo \n\r4: Azul \n\r5: Magenta (púrpura) \n\r6: Cian (verde azulado) \n\r7: Blanco\n\r");
                        caracter=read();
                           if(caracter>47 && caracter<56) //Si el caracter es un nombre entre 0 i 7
                                vista.changeBackgroundColor(caracter);
                           else
                                System.out.print("Caracter incorrecto!\n\r");
                        caracter=0; //Para que no se muestre el número escrito en pantalla
                        break;
                    default:
                        System.out.println("Caracter incorrecto!\n\r");
                        break;
                }
            default:
                line.write(caracter);
                System.out.print((char) caracter);
                break;
         }
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

