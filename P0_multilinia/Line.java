import java.io.*;
import java.util.ArrayList;

public class Line{
	
	private StringBuilder phrase = new StringBuilder("");
    private int numLetters;
	private ArrayList<StringBuilder> phrases;
	private int twidth;
	private int row;
	private int col;
	private int maxRows;

    public Line() throws IOException{
		phrases = new ArrayList<>();
		phrases.add(phrase);
        phrase = new StringBuilder();
		twidth= terminalwidth();
		row=0;
		col=0;
		maxRows=0;
    }

    public void moveRight(){ 
        if(col!=twidth)
		this.col = col+1;
	}

	public void moveLeft(){
        if(col!=0)
		col = col-1;
    }

	public void moveUp(int times){
		for(int i=0; i<times; i++){
			if(row!=0)
				row=row-1;
		}
	}

	public void moveDown(int times){
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

    public void moveToStart(){
		row = 0;
		col = 0;
	}

    public void moveToEnd(){
		// = numLetters;
	}

    public void insert(char letter){
        phrases.get(row).insert(col,letter);
	}

    public void backspace(){
        phrases.get(row).deleteCharAt(col-1);
        col=col-1;
	}

    public void write(char letter) {
		phrases.get(row).insert(col,letter);
        col=col+1;
	}

    public void supr(){
        phrases.get(row).deleteCharAt(col+1);
    }

    public String toString(){
		String str="";
			for(int i=0; i<=maxRows; i++){
				str+= phrases.get(i).toString()+"\n";
			}
		return str;
	}

	public boolean maxRow(){
		if(row==maxRows)
			return true;
		else return false;	
	}

	public int getNumLetters(){
		return phrases.get(row).length();
	}

	public int terminalwidth()  throws IOException{
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

	public static int terminalrows() {
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





