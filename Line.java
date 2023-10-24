import java.io.*;

public class Line{

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
	}

	public void moveLeft(){
		this.cursorPosition= cursorPosition-1;
	}

	public void moveToStart(){
		cursorPosition = 0;
	}

	public void moveToEnd(){
		cursorPosition = numLetters;
	}

	public void insert(char letter){
        phrase[cursorPosition] = letter;
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

	public void write(char letter) {
			phrase[cursorPosition] = letter;
			numLetters ++;
			cursorPosition ++;
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

}





