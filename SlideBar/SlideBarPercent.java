import java.io.*;
import static java.lang.System.in;

public class SlideBarPercent {

  private static void setRaw() throws IOException{
    try{
        String[] comanda = {"/bin/sh", "-c", "stty raw -echo </dev/tty"}; // /bin/sh, per a executar el codi com a una cadena, i amb l'stty configurem el terminal en raw mode
        Runtime.getRuntime().exec(comanda).waitFor(); //Executem la comanda i esperem a que aquesta acabi

    }catch(InterruptedException ex){
        ex.printStackTrace();
    }
  }
  
  private static void unsetRaw() throws IOException{
    try{
  String[] comanda = {"/bin/sh", "-c", "stty cooked echo </dev/tty"};
  Runtime.getRuntime().exec(comanda).waitFor();
 
  }catch(InterruptedException ex){
    ex.printStackTrace();
 }
  }
  
  static final int RIGHT = 0, LEFT = 1;
  
  public static int readArrow() throws IOException {
    int ch;
    
    do {
      BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
      setRaw();
      ch = in.read();
        if(ch==Keys.ESC)
          ch=in.read(); //Obviem [

      ch= in.read();
        if(ch==Keys.RIGHT){
          return RIGHT;
        }else if(ch==Keys.LEFT){
          return LEFT;
        }

    } while (ch != '\r');
    unsetRaw();
    return ch;
  }
  
  public static void main(String[] args) throws IOException {
    int arrow;
    ConsoleBar conBar = null;
    ConsolePercent conPer = null;
    Value value = null;
    
    try {
      setRaw();
      value = new Value();
      conBar = new ConsoleBar(value);
      
      conPer = new ConsolePercent(value);
      //...
      value.addObserver(conBar);
      //value.addObserver(conPer);
      
      while ((arrow = readArrow()) != '\r'){
        if (arrow == RIGHT)
          value.inc(conPer);
        else
          value.dec(conPer);
      }
    } finally {
      unsetRaw();
      //...
    }
  }
}

