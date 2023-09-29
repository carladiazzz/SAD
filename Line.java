import java.io.*;

public class Line{
	// maintains the status of the line in edition with its corresponding methods.

	private char[] phrase;
	private int cursorPosition;
	private int maxSize = 500;  
	private int numLetters;
	//private int insert;
	//private boolean insert;


	public Line(){
		phrase = new char[maxSize];
		cursorPosition = 0;
		numLetters = 0;
		//insert = 0;
		//insert = false;

	}

	public void moveRight(){
		cursorPosition ++;
	}

	public void moveLeft(){
		cursorPosition --;
	}

	public void moveToInicio(){
		cursorPosition = 0;
	}

	public void moveToFin(){
		cursorPosition = numLetters;
	}

	public void insert(){
        
	}

	public void suprimir(){
		for(int i = cursorPosition; i < numLetters; i++){
			phrase[i] = phrase[i + 1];
		}

		numLetters --;
	}

	public void backspace(){
		for(int i = cursorPosition; i < numLetters; i++){
			phrase[i - 1] = phrase[i]; 
		}

		cursorPosition --;
		numLetters --;
	}

	public void write(int letterId) {
            char letter= (char) letterId; 
			phrase[cursorPosition] = letter;
			numLetters ++;
			cursorPosition ++;
		}


	public String getPhrase(){
		return String.valueOf(phrase);
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