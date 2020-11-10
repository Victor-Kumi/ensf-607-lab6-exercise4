public class Referee {
    private Player xPlayer;
    private Player oPlayer;

    public Referee(){
    }

    public void runTheGame() {
        xPlayer.setOpponent(oPlayer);
        oPlayer.setOpponent(xPlayer);
  
        xPlayer.play();
    }

    public void setoPlayer(Player oPlayer){
        this.oPlayer = oPlayer;
    }

    public void setxPlayer(Player xPlayer){
        this.xPlayer = xPlayer;
    }
}
