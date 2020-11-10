public class Game implements Constants, Runnable {
	private Board theBoard;
	private Referee theRef;
	private Player xPlayer, oPlayer;

    public Game(Player xPlayer, Player oPlayer) {
        theBoard  = new Board();
    
        this.xPlayer = xPlayer;
        this.oPlayer = oPlayer;

	}
    
    public void appointReferee(Referee r) {
    	theRef.runTheGame();
    }
	
	public void setUpGame(){
	    xPlayer.setBoard(theBoard);
		oPlayer.setBoard(theBoard);
			
		theRef = new Referee();
		theRef.setoPlayer(oPlayer);
		theRef.setxPlayer(xPlayer);
	        
	    appointReferee(theRef);	
	}

	@Override
	public void run() {
		setUpGame();
		
	}
}
