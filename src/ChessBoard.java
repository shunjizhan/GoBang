// package com.gobang;

public class ChessBoard {
	public int size;		// default to 11
	public int[][] chess;	// chess status
	public int color;		// current color, 1 : black, -1 : white

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
		this.color = this.color * (-1);
	}

	public void printBoard() {
		// first line
		String chars = "abcdefghijklmnopqrstuvwxyz";
		System.out.print("     ");
		for (int a = 0; a < this.size; a++) {
			System.out.print(chars.charAt(a % 17));
			System.out.print(' ');
		}
		System.out.println(' ');

		// second line
		System.out.print("    ");
		for (int a = 0; a < this.size; a++) {
			System.out.print("__");
		}
		System.out.println(' ');

		// other lines
		for (int i = 0; i < this.size; i++) {
			if (i < 10) {System.out.print(' ');}
			System.out.print(i);
			System.out.print(" | ");
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

	public void put(char xChar, int y) {
		int x = charToInt(xChar);
		if (x > this.size || y > this.size || x < 0 || y < 0) {
			System.out.println("error! Position not in the board");
		} else if (this.chess[y][x] != 0) {
			System.out.println("error! This position is already taken");
		} else {
			this.chess[y][x] = this.color;
			printBoard();			
		}
		swichColor();
	}

	public int charToInt(char c) {
		return (int) c - 97;
	}


}