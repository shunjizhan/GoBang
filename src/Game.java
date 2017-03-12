// package com.gobang;

public class Game {
    public static void main(String[] args) {
    	Play game = new Play(15);
    	game.start();
    }
}

class Play {
    public ChessBoard board;
    public Player p1, p2;
    public Player currentPlayer;

    Play() {
    	this.board = new ChessBoard(15);
      this.initializeGame();
    }

    Play(int size) {
      if (size < 5) { size = 5; }
      if (size > 26) { size = 26; }

    	this.board = new ChessBoard(size);
      this.initializeGame();
    }

    public void initializeGame() {
      this.board.initialize();
      this.p1 = new Player(1);          // black
      this.p2 = new Player(-1, true);   // white 
      this.currentPlayer = p1;
    }

    public void start() {
  		while(true) {
        if (currentPlayer.isAI)
          this.aiPlay();
        else
          this.humanPlay();
      }
    }

    public void putChessOnBoard(int x, int y, int color) {
    	if (this.board.put(x, y, color) == true) {
        this.switchPlayer();

        if (this.board.checkGameOver(x, y, color) == true) {
          System.out.println(color + " won!");
          this.initializeGame();
        }
      }

    }

    public void switchPlayer() {
      if (this.currentPlayer == this.p1)
        this.currentPlayer = this.p2;
      else
        this.currentPlayer = this.p1;
    }

    public void humanPlay() {
      String position = this.readInput();

      int x = 0, y = 0;
      while(true) {
        try {
          while (position.length() < 2) {
            System.out.println("please enter a valid position!");
            position = this.readInput();
          }      
          x = charToInt(position.charAt(0));
          y = Integer.parseInt(position.substring(1));
          break;
        } 
        catch (NumberFormatException e) {
          System.out.println("please enter a valid position!");
          position = this.readInput();
        }
      }

      this.putChessOnBoard(x, y, currentPlayer.color);
    }

    public String readInput() {
      System.out.println("enter position >> ");
      return System.console().readLine();
    }

    public void aiPlay() {
      int[] position = this.currentPlayer.decidePosition(this.board.chessStatus);
      this.putChessOnBoard(position[0], position[1], this.currentPlayer.color);
    }

    public int charToInt(char c) {
      return (int) c - 97;
    }

}




















