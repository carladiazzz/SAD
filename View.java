import java.io.*;
/*
Puede cambiar la vista de la consola
*/ 
public class View{

    public void restablish(){
        System.out.println("\u001b[0m\n\r");
    }

    public void bold(){
        restablish();
        System.out.println("\n\rNegrita:\n\r\u001b[1m");
    }

    public void dim(){
        restablish();
        System.out.println("\n\rdim:\n\r\u001b[2m");
    }

    public void italics(){
        restablish();
        System.out.println("\n\rCursiva:\n\r\u001b[3m");
    }

    public void underline(){
        restablish();
        System.out.println("\n\rSubrayado:\n\r\u001b[4m");
    }

    public void changeColor(int color){
        char numcolor= (char) color;
        switch(color){
            case 48: //0 Black
            case 49: //1 Red
            case 50: //2 Green
            case 51: //3 Yellow
            case 52: //4 Blue
            case 53: //5 Magenta
            case 54: //6 Cyan
            case 55: //7 White
                System.out.print("\n\r\u001b[3"+numcolor+"m");
                break;
            default:
                System.out.print("Caracter incorrecto!\n\r");
                break; 
        }
    }

    public void changeBackgroundColor(int color){
        char numcolor= (char) color;
        switch(color){
            case 48: //0 Black
            case 49: //1 Red
            case 50: //2 Green
            case 51: //3 Yellow
            case 52: //4 Blue
            case 53: //5 Magenta
            case 54: //6 Cyan
            case 55: //7 White
                System.out.print("\n\r\u001b[4"+numcolor+"m");
                break;
            default:
                System.out.print("Caracter incorrecto!\n\r");
                break; 
        }
    }
    
}