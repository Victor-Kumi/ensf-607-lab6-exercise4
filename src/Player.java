import java.io.*;
public class Player {
    private String name;
    private Board board;
    private Player opponent;
    private char mark;
    private BufferedReader socketIn;
    private PrintWriter socketOut;
    public Player(String name, char mark){
        this.name = name;
        this.mark = mark;
    }

    public void play() {
    	printBoardToBothSocketOuts();

        while (!isWinner()){
            this.makeMove();
            printBoardToBothSocketOuts();

            if(!isWinner())
            	opponent.play();
        }
    }

    public void makeMove(){
        int row;
        int col;
        socketOut.println(this.name + ", enter the row, and enter column separated by empty space to play");
        try {
			String[] rowCol = socketIn.readLine().split(" ");
			row = Integer.parseInt(rowCol[0]);
			col = Integer.parseInt(rowCol[1]);
	        if (board.getMark(row, col) == board.SPACE_CHAR)
	            board.addMark(row, col, mark);
	        else
	            makeMove();
		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
    }
    
    public void setOpponent(Player oPlayer) {
        this.opponent = oPlayer;
    }

    public void setBoard(Board theBoard) {
        this.board = theBoard;
    }

    public boolean isWinner(){
        boolean winner = false;
        boolean tie = board.isFull();
        boolean xWins = board.xWins();
        boolean oWins = board.oWins();
        if (tie || xWins || oWins){
            winner = true;
            if (tie) {
            	socketOut.println("GAME OVER!!! \nA TIE GAME");
            	opponent.socketOut.println("GAME OVER!!! \nA TIE GAME");
            }   
            else {
            	socketOut.println("GAME OVER!!! \n" + this.name + " wins");
            	opponent.socketOut.println("GAME OVER!!! \n" + this.name + " wins");
            }
            //System.out.println("Game between " + this.getName() + " and " + opponent.getName() + " is over! Thread is free");
            socketOut.println("GAME OVER");
            opponent.socketOut.println("GAME OVER");
        }
        return winner;
    }
    
    public void setSocket(BufferedReader socketIn, PrintWriter socketOut) {
    	this.socketIn = socketIn;
    	this.socketOut = socketOut;
    }
    public PrintWriter getSocketOut() {
    	return this.socketOut;
    }
    
    public String getName() {
    	return this.name;
    }
    
    public void printBoardToBothSocketOuts() {
    	board.setSocket(socketOut);
    	board.display();

    	board.setSocket(opponent.getSocketOut());
    	board.display();
    }
}
