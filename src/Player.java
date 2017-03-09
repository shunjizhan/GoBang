// package com.gobang;
import java.lang.Character;

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
		this.board = new ChessBoard(this.size, light);
	}

	public void play() {
		int color = 1;
		while(true) {
			System.out.println("enter position >> ");
			String position = System.console().readLine();
			board.put(Character.getNumericValue(position.charAt(0)), Character.getNumericValue(position.charAt(1)));
		}
	}

}

class ChessBoard {
	public int size;		// default to 11
	public int[][] chess;
	public int color;		// current color, 1 : black, 2 : white

	public ChessBoard() {
		this.size = 11;
		this.color = 1;		
		initialize();
		printBoard();
	}

	public ChessBoard(int size, boolean... light) {
		this.size = size;
		this.color = (light.length > 0 && light[0] == true) ? -1 : 1;
		initialize();
		printBoard();
	}

	public void initialize() {
		this.chess = new int[this.size][this.size];
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				this.chess[i][j] = 0;
			}
		}
	}

	public void swichColor() {
		if (color == 1) 
			color = -1;
		else 
			color = 1;
	}

	public void printBoard() {
		// first line
		String chars = "abcdefghiuklmnopqrstuvwxyz";
		System.out.print("   ");
		for (int a = 0; a < this.size; a++) {
			System.out.print(chars.charAt(a));
			System.out.print(' ');
		}
		System.out.println(' ');

		for (int i = 0; i < this.size; i++) {
			if (i < 10) {System.out.print(' ');}
			System.out.print(i);
			System.out.print(' ');
			for (int j = 0; j < this.size; j++) {
				printSingleChess(this.chess[i][j]);	
			}
			System.out.println(' ');
		}
	}

	public void printSingleChess(int chess) {
		if (chess == 1)
			System.out.print("O");
		else if (chess == -1)
			System.out.print("X");
		else
			System.out.print("-");
		System.out.print(' ');
	}

	public void put(int x, int y) {
		if (x > this.size || y > this.size || x < 0 || y < 0) {
			System.out.println("error! Position not in the board");
		} else if (this.chess[x][y] != 0) {
			System.out.println("error! This position is already taken");
		} else {
			this.chess[x][y] = this.color;
			printBoard();			
		}
		swichColor();
	}


}

















