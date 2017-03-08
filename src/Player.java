// package com.gobang;

public class Player {
	public int size;			// default 11
	public boolean light;		// default false
	public ChessBoard board;

	public Player() {
		this.size = 11;
		this.light = false;
		this.board = new ChessBoard(this.size);
	}

	public Player(int size, boolean light) {
		this.size = size;
		this.light = light;
		this.board = new ChessBoard(this.size);
	}

}

class ChessBoard {
	public int size;		// default to 11
	public int[][] chess;

	public ChessBoard() {
		this.size = 11;
		this.initialize();
	}

	public ChessBoard(int size) {
		this.size = size;
		this.initialize();
	}

	public void initialize() {
		this.chess = new int[this.size][this.size];
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				this.chess[i][j] = 0;
			}
		}
	}

	public void printBoard() {
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				System.out.print(this.chess[i][j]);
				System.out.print(' ');
			}
			System.out.println(' ');
		}
	}


}

















