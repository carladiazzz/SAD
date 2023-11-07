import java.util.*;
import java.io.*;

class ConsoleBar implements Observer {
	static enum Opcode {
	  INC, DEC, BELL
	}

	static class Command {
	  Opcode op;
	  
	  Command(Opcode op) {
	    this.op = op;
	  }
	}

	int cmax;
	Value model;
	static final char BLOCK = '\u2588';
  
	ConsoleBar(Value value) throws IOException{
		model = value;

		// get max columns
		cmax= getMax();
		value.setMax(cmax);	
		
	}
	
	int getMax() throws IOException{
        Process process = new ProcessBuilder("tput", "cols").start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String line = reader.readLine();
            if (line != null) 
                cmax = Integer.parseInt(line);

		return cmax;
	}
	
	public void update(Observable o, Object arg) {
		Command comm = (Command) arg;
		switch (comm.op) {
			case INC:
				System.out.print(BLOCK);
				break;
			case DEC:
				// delete char to the left
				
				break;
			case BELL:
				System.out.print('\007');
				break;
		}
	}
}

