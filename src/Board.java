import java.io.BufferedReader;
import java.io.PrintWriter;

//STUDENTS SHOULD ADD CLASS COMMENTS, METHOD COMMENTS, FIELD COMMENTS 


public class Board implements Constants {
	private char theBoard[][];
	private int markCount;
    //private BufferedReader socketIn;
    private PrintWriter socketOut;
	public Board() {
//		this.socketIn = socketIn;
//		this.socketOut = socketOut;
		markCount = 0;
		theBoard = new char[3][];
		for (int i = 0; i < 3; i++) {
			theBoard[i] = new char[3];
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		}
	}

	public char getMark(int row, int col) {
		return theBoard[row][col];
	}

	public boolean isFull() {
		return markCount == 9;
	}

	public boolean xWins() {
		if (checkWinner(LETTER_X) == 1)
			return true;
		else
			return false;
	}

	public boolean oWins() {
		if (checkWinner(LETTER_O) == 1)
			return true;
		else
			return false;
	}

	public void display() {
		displayColumnHeaders();
		addHyphens();
		for (int row = 0; row < 3; row++) {
			addSpaces();
			socketOut.print("    row " + row + ' ');
			for (int col = 0; col < 3; col++)
				socketOut.print("|  " + getMark(row, col) + "  ");
			socketOut.println("|");
			addSpaces();
			addHyphens();
		}
	}

	public void addMark(int row, int col, char mark) {
		
		theBoard[row][col] = mark;
		markCount++;
	}

	public void clear() {
		for (int i = 0; i < 3; i++)
			for (int j = 0; j < 3; j++)
				theBoard[i][j] = SPACE_CHAR;
		markCount = 0;
	}

	int checkWinner(char mark) {
		int row, col;
		int result = 0;

		for (row = 0; result == 0 && row < 3; row++) {
			int row_result = 1;
			for (col = 0; row_result == 1 && col < 3; col++)
				if (theBoard[row][col] != mark)
					row_result = 0;
			if (row_result != 0)
				result = 1;
		}

		
		for (col = 0; result == 0 && col < 3; col++) {
			int col_result = 1;
			for (row = 0; col_result != 0 && row < 3; row++)
				if (theBoard[row][col] != mark)
					col_result = 0;
			if (col_result != 0)
				result = 1;
		}

		if (result == 0) {
			int diag1Result = 1;
			for (row = 0; diag1Result != 0 && row < 3; row++)
				if (theBoard[row][row] != mark)
					diag1Result = 0;
			if (diag1Result != 0)
				result = 1;
		}
		if (result == 0) {
			int diag2Result = 1;
			for (row = 0; diag2Result != 0 && row < 3; row++)
				if (theBoard[row][3 - 1 - row] != mark)
					diag2Result = 0;
			if (diag2Result != 0)
				result = 1;
		}
		return result;
	}

	void displayColumnHeaders() {
		socketOut.print("          ");
		for (int j = 0; j < 3; j++)
			socketOut.print("|col " + j);
		socketOut.println();
	}

	void addHyphens() {
		socketOut.print("          ");
		for (int j = 0; j < 3; j++)
			socketOut.print("+-----");
		socketOut.println("+");
	}

	void addSpaces() {
		socketOut.print("          ");
		for (int j = 0; j < 3; j++)
			socketOut.print("|     ");
		socketOut.println("|");
	}
	
	public void setSocket(PrintWriter socketOut) {
    	//this.socketIn = socketIn;
    	this.socketOut = socketOut;
    }
}
