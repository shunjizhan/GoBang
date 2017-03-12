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

    Play() {
    	this.board = new ChessBoard(11);
    	this.p1 = new Player(1);		// black
    	this.p2 = new Player(-1);		// white
    }

    Play(int size) {
    	this.board = new ChessBoard(size);
    	this.p1 = new Player(1);		// black
    	this.p2 = new Player(-1);		// white
    }

    public void start() {
		while(true) {
			System.out.println("enter position >> ");
			String position = System.console().readLine();
			putChessOnBoard(position.charAt(0), Character.getNumericValue(position.charAt(1)));
		}
    }

    public void putChessOnBoard(char x, int y) {
    	this.board.put(x, y);
    }

}
