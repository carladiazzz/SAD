//package P0;

import java.io.IOException;
import java.io.Reader;
import java.io.BufferedReader;

public class EditableBufferedReader extends BufferedReader{

private View vista;

public EditableBufferedReader(Reader text){
    super(text);
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
        /*if(caracter==Keys.ESTILO){
            caracter= Keys.xESTILO;
            return caracter;
        }*/

        caracter= super.read();
        if(caracter>64){//Si després llegim una lletra
            switch (caracter) {
                case Keys.INSERT: caracter = Keys.xINSERT;
                    break;
                case Keys.RIGHT: caracter = Keys.xRIGHT;
                    break;
                case Keys.LEFT: caracter = Keys.xLEFT;
                    break;
                case Keys.INICIO: caracter= Keys.xINICIO;
                    break;
                case Keys.FIN: caracter= Keys.xFIN;
                    break;
                case Keys.UP: caracter= Keys.xUP;
                    break;
                case Keys.DOWN: caracter= Keys.xDOWN;
                    break;
            } 
        }else{ //Si llegim un número
            switch (caracter){
                case Keys.INSERT: caracter=Keys.xINSERT;
                    break;
                case Keys.SUPR: caracter= Keys.xSUPR;
                    break;
                case Keys.AVPAG: caracter= Keys.xAVPAG;
                    break;
                case Keys.REPAG: caracter= Keys.xREPAG;
                    break;
            }
            super.read(); //Obviem el "~"
        }
    }

    return caracter;
}

public String readLine() throws IOException{  
    setRaw();
    Line line =new Line(); 
    int caracter=0;
    while(caracter!='\r'){
        caracter= read();
         switch(caracter){

            case '\r': break;

            case Keys.BACKSPACE: 
                System.out.print("\u001b[1D"); //Cursor a la esquerra
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
                line.insert((char)caracter);
                break;
            
            case Keys.xSUPR:
                System.out.print("\u001b[1C");
                System.out.print("\u001b[P");
                System.out.print("\u001b[1D");
                line.supr();
                break;

            case Keys.xINICIO:
                int col= line.getcol();
                System.out.print("\u001b["+col+"D"); //Movem el cursor a la esquerra 'col' cops
                line.moveToStart();
                break;

            case Keys.xFIN:
                int numLetters= line.getNumLetters();
                System.out.print("\u001b[0"+numLetters+"C"); //Movem el cursor al final de la frase
                line.moveToEnd();
                break;

            case Keys.xUP:
                System.out.print("\u001b[1A"); //Movem cursor a la adalt
                line.moveUp(1);
                break;

            case Keys.xDOWN:
                if(line.maxRow()) //maxRow() retorna true si estem a la columna màxima
                    System.out.print("\r\n");  //En aquest cas afegim una fila
                else
                    System.out.print("\u001b[1B"); //Movem cursor abaix
                line.moveDown(1);
                break;

            case Keys.xAVPAG:
                for(int i=0; i<=line.terminalrows();i++){
                    if(!line.maxRow()){
                        System.out.print("\u001b[1B"); //Movem cursor abaix
                        line.moveDown(1);
                    }
                    else{
                        System.out.print("\r\n");  //En aquest cas afegim una fila
                        line.moveDown(1);
                    }
                }

            case Keys.xREPAG:
                    System.out.print("\u001b["+line.terminalrows()+"A"); //Movem cursor a la adalt
                    line.moveUp(line.terminalrows());

            /*case Keys.xESTILO:
                System.out.println("\n\rQue estilo desea?\n\r0 (restablecer)\n\r1 (negrita)\n\r2 (dim)\n\r3 (cursiva)\n\r4 (subrayado)\n\r5 (colores de texto)\n\r6 (colores de fondo)\n\r");
                caracter=read();
                vista.style(caracter);
                caracter=0;
                break;*/

            default:
                line.write((char)caracter);
                System.out.print((char) caracter);
                break;
         }
    }

    unsetRaw();
    
return line.toString();  
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

