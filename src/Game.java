// package com.gobang;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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
    public BufferedReader br;

    Play() {
    	this.board = new ChessBoard(15);
      this.humanFirst = true;
      this.initializeGame(2);
    }

    Play(int size) {
      if (size < 5) { size = 5; }
      if (size > 26) { size = 26; }

    	this.board = new ChessBoard(size);
      this.humanFirst = true;
      this.initializeGame(2); 
    }

    Play(int size, boolean first, int depth) {
      if (size < 5) { size = 5; }
      if (size > 26) { size = 26; }

      this.board = new ChessBoard(size);
      this.humanFirst = first;
      this.initializeGame(depth);      
    }

    public void initializeGame(int depth) {
	    this.br = new BufferedReader(new InputStreamReader(System.in));
      if (this.humanFirst) {
        // System.out.println("1111111");
        this.p1 = new Player(1);          // black human
        this.p2 = new Player(-1, true, depth);   // white computer
      } else {
        // System.out.println("2222222");
        this.p1 = new Player(1, true, depth);   // black computer
        this.p2 = new Player(-1);          // white human
      }
      this.board.initialize();
      this.currentPlayer = p1;

      System.out.print("Size: " + this.board.size + ", ");
    	System.out.println("depth: " + depth);
    	this.board.printBoard();
    	System.out.println("Move played: --");
    }

    public void start() {
  		while(true) {
        if (currentPlayer.isAI) {
        	this.printPlayer();
          this.aiPlay();
        } else {
        	this.printPlayer();
          this.humanPlay();
        }
      }
    }

    public void printPlayer() {
    	String color, type, message1, message2;
    	boolean ai = this.currentPlayer.isAI;
    	if (ai)
    		type = "(COM) ";
    	else 
    		type = "(human) ";
    	
    	if (this.currentPlayer.color == 1) 
    		color = "Dark player ";
    	else 
    		color = "Light player ";

    	message1 = color + type + "plays now";
    	System.out.println(message1); 

    	if (ai) {
	    	message2 = color + type + "is calculating its next move... (this might take up to 30 seconds)";
	    	System.out.println(message2);   	
	    }
    }

    public void putChessOnBoard(int x, int y, int color) {
    	// x correspond to letter
    	// y correspond to number
    	if (this.board.put(x, y, color) == true) {
        System.out.println("Move played: " + intToChar(x) + y);

        this.switchPlayer();

        if (this.board.checkGameOver(x, y, color) == true) {
          this.printResult(color);
          System.out.println("Game Over!");
          System.exit(0);
        } else if (this.board.checkDraw() == true) {
          System.out.println("Draw!");
          System.out.println("Game Over!");
          System.exit(0);  
        }
      }
    }

    public void printResult(int color) {
    	String colorWin, typeWin, message;
    	if (this.currentPlayer.isAI)
    		typeWin = "(COM) ";
    	else 
    		typeWin = "(human) ";
    	
    	if (color == 1) 
    		colorWin = "Dark player ";
    	else 
    		colorWin = "Light player ";

    	message = colorWin + typeWin + "Wins!";
    	System.out.println(message);
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
      System.out.print("> ");
      System.out.flush();
      // return System.console().readLine();
      String input = "";
      try {
      	input = this.br.readLine();
      	
      } catch (IOException e) {
      	e.printStackTrace();
      }
      return input; 
    }

    public void aiPlay() {
      int[] position = this.currentPlayer.decidePosition(this.board.chessStatus);
      this.putChessOnBoard(position[0], position[1], this.currentPlayer.color);
    }

    public int charToInt(char c) {
      return (int) c - 97;
    }

    public char intToChar(int i) {
      return (char) (i + 97);
    }

}




















