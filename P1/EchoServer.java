public class EchoServer {
    public static void main(String[] args){
        MyServerSocket ss= new MyServerSocket(Integer.parseInt(args[0]));
        while(true){
            //esperar següent client
            MySocket s = ss.accept();
            //crear un thread auxiliar que atengui el client
            new Thread(() -> {
                String line;
                while((line= s.readLine()) != null){
                    s.println(line);
                }
                s.close();
            }).start();

        }
    }
}