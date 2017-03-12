// package com.gobang;

public class Game {
    public static void main(String[] args) {
    	Play game = new Play();
    	game.start();
    }
}

class Play {
    public ChessBoard board;
    public Player p1, p2;
    public Player currentPlayer;

    Play() {
    	this.board = new ChessBoard(11);
      this.initializeGame();
    }

    Play(int size) {
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
      System.out.println("enter position >> ");
      String position = System.console().readLine();

      while (position.length() < 2) {
        System.out.println("please enter a valid position!");
        System.out.println("enter position >> ");
        position = System.console().readLine();
      }
      int x = charToInt(position.charAt(0));
      int y = Integer.parseInt(position.substring(1));
      this.putChessOnBoard(x, y, currentPlayer.color);
    }

    public void aiPlay() {
      int[] position = this.currentPlayer.decidePosition(this.board.chessStatus);
      this.putChessOnBoard(position[0], position[1], this.currentPlayer.color);
    }

    public int charToInt(char c) {
      return (int) c - 97;
    }

}




















