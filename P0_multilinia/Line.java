import java.io.*;
import java.util.ArrayList;

public class Line{
	
	private StringBuilder phrase = new StringBuilder("");
    private int numLetters;
	private ArrayList<StringBuilder> phrases; //Cada linia tindrà un propi StringBuilder de l'Arraylist
	private int twidth; //Variable que ens dirà el número de columnes del terminal
	private int row; //El valor de la linea/fila on es troba el cursor actualment
	private int col; //El valor de la columna on es troba el cursor actualment
	private int maxRows; //El nombre total de linies creades

    public Line() throws IOException{
		phrases = new ArrayList<>(); 
		phrases.add(phrase);
        phrase = new StringBuilder();
		twidth= terminalwidth();
		row=0;
		col=0;
		maxRows=0;
    }

    public void moveRight(){ //Movem el cursor una columna cap a la dreta si es pot
        if(col!=twidth)
		this.col = col+1;
	}

	public void moveLeft(){ ////Movem el cursor una columna cap a la esquerra si es pot
        if(col!=0)
		col = col-1;
    }

	public void moveUp(int times){ //Passem per argument quants cops es vol moure el cursor cap adalt i ho fem
		for(int i=0; i<times; i++){ 
			if(row!=0)
				row=row-1;
		}
	}

	public void moveDown(int times){  //Passem per argument quants cops es vol moure el cursor cap abaix i ho fem
		for(int i=0; i<times; i++){
			if(row==maxRows){
				StringBuilder phrase= new StringBuilder("");
				phrases.add(phrase);
				maxRows=maxRows+1;
			    col=0;
			}
		row=row+1;
		}
	}

    public void moveToStart(){ //Movem el cursor al principi de la linia actual
		col = 0;
	}

    public void moveToEnd(){ //Movem el cursor al final de la linia actual
		col=phrases.get(row).length();
	}

    public void insert(char letter){  //Insertem el char passat per argument al lloc de la linia on estigui situat el cursor
        phrases.get(row).insert(col,letter);
	}

    public void backspace(){ //Esborrem el caracter anterior al cursor i el movem cap a la esquerra
        phrases.get(row).deleteCharAt(col-1);
        col=col-1;
	}

    public void write(char letter) { //Inserim el char letter allà on estigui situat el cursor
	if(col!=twidth){
		phrases.get(row).insert(col,letter);
        col=col+1;
	}else{
		if(row==maxRows){
				StringBuilder phrase= new StringBuilder("");
				phrases.add(phrase);
				maxRows=maxRows+1;
			    col=0;
				row= row+1;
			}
		else{
		col=0;
		row= row+1;
		}
	}
	}

    public void supr(){ //Esborrem el caracter situat a la dreta del cursor
        phrases.get(row).deleteCharAt(col+1);
    }

    public String toString(){
		String str="";
			for(int i=0; i<=maxRows; i++){
				str+= phrases.get(i).toString()+"\n";
			}
		return str;
	}

	public boolean maxRow(){ //Retorna true si el cursor es troba a la última linia
		if(row==maxRows)
			return true;
		else return false;	
	}

	public int getNumLetters(){
		return phrases.get(row).length();
	}

	public int terminalwidth()  throws IOException{ //Funció que retorna el nombre de columnes del terminal
		 int width = 0;
        try {
            Process process = new ProcessBuilder("tput", "cols").start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null) {
                width = Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            // Manejo de excepciones
            e.printStackTrace();
        }
        return width;
	}

	public static int terminalrows() { //Funció que retorna el nombre de files del terminal
        int rows = 0;
        try {
            Process process = new ProcessBuilder("tput", "lines").start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line = reader.readLine();
            if (line != null) {
                rows = Integer.parseInt(line);
            }
        } catch (IOException | NumberFormatException e) {
            // Manejo de excepciones
            e.printStackTrace();
        }
        return rows;
    }

    public int getcol(){
		return col;
	}

	public int getrow(){
		return row;
	}





}





