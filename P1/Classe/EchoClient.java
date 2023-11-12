import java.io.*;

public class EchoClient{

    public static void main(String[] args){
        MySocket sc = new MySocket(args[0],Integer.parseInt(args[1]));
        //Input thread (keyboard)
        new Thread()({
            public void run() {
                BufferedReader kbd = new BufferedReader(new InputStreamReader(System.in));
                String line;
                try{
                    while((line = kbd.readLine()) != null){
                        sc.println(line); //Métode a MySocket: println(Character.valueOf(c).toString);
                    }
                //close for writing
                }catch(IOexception ex){
                    ex.printStackTrace();
                }
            }.start();
        });


        //Output thread (console)

        new Thread()({
            public void run() {
                String line;
                while((line = sc.readLine()) != null){
                    System.out.println(line);
                    }
            }.start();
        });
        //new Thread(() -> {
    }
         
}
