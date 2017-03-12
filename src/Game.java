// package com.gobang;

public class Game {
    public static void main(String[] args) {
      InputHelper h = new InputHelper();

      int i = 0;
      int argLength = args.length;
      String currentArg;
      while (i < argLength) {
        switch (args[i]) {
          case "-l":
            h.first = false;
            break;
          case "-n":
            if (i++ < argLength) {
              h.size = Integer.parseInt(args[i]);
            }
            break;
          case "-d":
            if (i++ < argLength) {
              h.depth = Integer.parseInt(args[i]);
              i++;
            }
            break;
          default:
            break;
        }
        i++;
      }

    	Play game = new Play(h.size, h.first, h.depth);
    	game.start();
    }
}

class InputHelper {
  public boolean first;
  public int size;
  public int depth;

  InputHelper() {
    this.first = true;
    this.size = 11;
    this.depth = 2;
  }
}

class Play {
    public ChessBoard board;
    public Player p1, p2;
    public Player currentPlayer;
    public boolean humanFirst;
    public int depth;

    Play() {
    	this.board = new ChessBoard(15);
      this.humanFirst = true;
      this.depth = 2;
      this.initializeGame();
    }

    Play(int size) {
      if (size < 5) { size = 5; }
      if (size > 26) { size = 26; }

    	this.board = new ChessBoard(size);
      this.humanFirst = true;
      this.depth = 2;
      this.initializeGame(); 
    }

    Play(int size, boolean first, int depth) {
      if (size < 5) { size = 5; }
      if (size > 26) { size = 26; }

      this.board = new ChessBoard(size);
      this.humanFirst = first;
      this.depth = depth;
      this.initializeGame();      
    }

    public void initializeGame() {
      if (this.humanFirst) {
        this.p1 = new Player(1);          // black human
        this.p2 = new Player(-1, true);   // white computer
      } else {
        this.p1 = new Player(-1, true);   // black computer
        this.p2 = new Player(1);          // white human
      }
      this.board.initialize();
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
      y--;
    	if (this.board.put(x, y, color) == true) {
        this.switchPlayer();

        if (this.board.checkGameOver(x, y, color) == true) {
          System.out.println(color + " won!");
          this.initializeGame();
        } else if (this.board.checkDraw() == true) {
          System.out.println("Draw!");
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




















