import java.util.*;
import java.io.*;

class ConsolePercent implements Observer {
	static enum Opcode {
	  INC, DEC, BELL
	}

	static class Command {
	  Opcode op;
	  
	  Command(Opcode op) {
	    this.op = op;
	  }
	}

	int cmax, rmax;
	int cursor;
	int per;
	Value model;
  
	ConsolePercent(Value value) throws IOException {
		model = value;
		cursor=0;
		per=0;
		// get max columns
		cmax= getMax();
		rmax= getRowMax();
		model.setMax(cmax);		
		// write 0%
		System.out.print("\u001b["+cmax/2+"C"+value.get()+'%');
		System.out.print("\u001b["+(cmax/2+2)+"D");
	}
	
	int getMax() throws IOException{
        Process process = new ProcessBuilder("tput", "cols").start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine();
            if (line != null) 
                cmax = Integer.parseInt(line);

		return cmax;
	}

	int getRowMax() throws IOException{
		Process process = new ProcessBuilder("tput", "lines").start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine();
            if (line != null) 
                rmax = Integer.parseInt(line);

		return rmax;
	}
	
	public void update(Observable o, Object arg) {
		//restore();
		cursor = model.get();
		per= (cursor*100/cmax);
		Command comm = (Command) arg;
		switch (comm.op) {
			case DEC: 
			case INC: 
				System.out.print("\033["+rmax+";"+(cmax/2+1)+"H"); //Posicionem el cursor al mig de la pantalla i la útlima fila
				System.out.print(per+"%");
				System.out.print("\033["+rmax+";"+cursor+"H"); //Posicionem el cursor a la seva posició
				// write Value%
				
				break;
			case BELL:
				System.out.print('\007');
				break;
		}
	}
}

