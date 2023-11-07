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

	int cmax;
	Value model;
  
	ConsolePercent(Value value) throws IOException {
		model = value;
		
		// get max columns
		cmax= getMax();
		value.setMax(cmax);		
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
	
	public void update(Observable o, Object arg) {
		//restore();
		Command comm = (Command) arg;
		switch (comm.op) {
			case INC: 
			case DEC: 
				System.out.print("\u001b["+cmax/2+"C"+value.get()+'%');
				if(value.get()<cmax/2){
					System.out.print("\u001b["+(cmax/2-value.get())+"C");
				}else{
					System.out.print("\u001b["+(cmax/2-value.get())+"D");
				}
				// write Value%
				break;
			case BELL:
				System.out.print('\007');
				break;
		}
	}
}

