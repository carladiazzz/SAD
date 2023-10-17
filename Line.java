import java.io.*;
//import java.util.Observable;

public class Line/*extends Observable*/{

	private char[] phrase;
	private int cursorPosition;
	private int maxSize = 1000;  
	private int numLetters;


	public Line(){
		phrase = new char[maxSize];
		cursorPosition = 0;
		numLetters = 0;

	}

	public void moveRight(){
		this.cursorPosition = cursorPosition+1;
		//this.setChanged();
	}

	public void moveLeft(){
		this.cursorPosition= cursorPosition-1;
		//this.setChanged();
	}

	public void moveToStart(){
		cursorPosition = 0;
		//this.setChanged();
	}

	public void moveToEnd(){
		cursorPosition = numLetters;
		//this.setChanged();
	}

	public void insert(char letter){
        phrase[cursorPosition] = letter;
		//this.setChanged();
	}

	public void suprimir(){
		for(int i = cursorPosition; i < numLetters; i++){
			phrase[i] = phrase[i + 1];
		}
		numLetters --;
		//this.setChanged();
	}

	public void backspace(){
		for(int i = cursorPosition; i < numLetters; i++){
			phrase[i - 1] = phrase[i]; 
		}
		cursorPosition --;
		numLetters --;
		//this.setChanged();
	}

	public void write(int letterId) {
            char letter= (char) letterId; 
			phrase[cursorPosition] = letter;
			numLetters ++;
			cursorPosition ++;
			//this.setChanged();
		}


	public String getPhrase(){
		return String.valueOf(phrase)+"\n"+cursorPosition;
	}

	public int getCursorPosition(){
		return cursorPosition;
	}

	public int getNumLetters(){
		return numLetters;
	}

	/*public boolean getInsertarState(){
		return insert;
	}*/

}





