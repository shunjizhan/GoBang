// package com.gobang;

public class ChessBoard {
	public int size;		// default to 11
	public int totalPositions;
	public int chessCount;
	public int[][] chessStatus;		// [y][x] !!!!!!!

	public ChessBoard() {
		this.size = 11;
		this.totalPositions = this.size * this.size;
		this.chessCount = 0;
		initialize();
		printBoard();
	}

	public ChessBoard(int size) {
		this.size = size;
		this.totalPositions = this.size * this.size;
		this.chessCount = 0;
		initialize();
		printBoard();
	}

	public void initialize() {
		this.chessStatus = new int[this.size][this.size];
		for (int i = 0; i < this.size; i++) {
			for (int j = 0; j < this.size; j++) {
				this.chessStatus[i][j] = 0;
			}
		}
		this.chessCount = 0;
	}

	public void printBoard() {
	 	String chars = "abcdefghijklmnopqrstuvwxyz".substring(0, this.size);
	 	for (char c : chars.toCharArray()) {
	 		System.out.print("   " + c);
	 	}
	 	System.out.println(" ");

	 	System.out.print("   +");
	 	for (int i = 0; i < this.size; i++) {
	 		System.out.print("---+");
	 	}
	 	System.out.println(" ");

	 	for (int i = 0; i < this.size; i++) {
	 		for (int j = 0; j < this.size; j++) {
	 			this.printSingleChess(this.chessStatus[i][j]);
	 		}
	 		System.out.println(" ");

	 		System.out.print("   +");
	 		for (int k = 0; k < this.size; k++) {
	 			System.out.print("---+");
		 	}
		 	System.out.println(" ");
	 	}
	 	
	 	System.out.print("   +");
	 	for (int i = 0; i < this.size; i++) {
	 		System.out.print("---+");
	 	}
	 	System.out.println(" ");
	}

	public void printSingleChess(int chessStatus) {
		if (chessStatus == 1)
			System.out.print(" D |");
		else if (chessStatus == -1)
			System.out.print(" L |");
		else
			System.out.print("   |");
	}

	public boolean put(int x, int y, int color) {
		if (x >= this.size || y >= this.size || x < 0 || y < 0) {
			System.out.println("error! Position not in the board");
			this.printBoard();	
			return false;
		} 
		else if (this.chessStatus[y][x] != 0) {
			System.out.println("error! This position " + x + ' ' + y + "is already taken");
			System.out.println("status: " + this.chessStatus[y][x]);
			this.printBoard();	
			return false;
		} 
		else {
			this.chessStatus[y][x] = color;
			this.chessCount++;
			this.printBoard();			
			return true;
		}
	}

	public boolean checkGameOver(int x, int y, int color) {
		// horizontal
		int continueChess = 0;
		for (int i = 0; i < this.size; i++) {
			if (chessStatus[i][x] == color) {
				continueChess++;
				if (continueChess == 5)
					return true;
			} else {
				continueChess = 0;
			}
		}

		// vertical
		continueChess = 0;
		for (int j = 0; j < this.size; j++) {
			if (chessStatus[y][j] == color) {
				continueChess++;
				if (continueChess == 5)
					return true;
			} else {
				continueChess = 0;
			}
		}

		// diagnal 1
		continueChess = 0;
		int pX = x;
		int pY = y;
		while(this.isInBoard(pX, pY)) {
			pX--;
			pY--;
		}
		pX++;
		pY++;	// now it is the first position to check

		while(this.isInBoard(pX, pY)) {
			// System.out.println("111 checking: " + pX + ' ' + pY);
			if (chessStatus[pY][pX] == color) {
				continueChess++;
				if (continueChess == 5)
					return true;
			}
			else {
				continueChess = 0;
			}

			pX++;
			pY++;
		}

			// diagnal 1
		continueChess = 0;
		pX = x;
		pY = y;
		while(this.isInBoard(pX, pY)) {
			pX--;
			pY++;
		}
		pX++;
		pY--;	// now it is the first position to check

		while(this.isInBoard(pX, pY)) {
			// System.out.println("222 checking: " + pX + ' ' + pY);
			if (chessStatus[pY][pX] == color) {
				continueChess++;
				if (continueChess == 5)
					return true;
			}
			else {
				continueChess = 0;
			}

			pX++;
			pY--;
		}

		return false;
	}

	public boolean isInBoard(int x, int y) {
		return (x >= 0 && y >= 0 && x < this.size && y < this.size);
	}

	public boolean checkDraw() {
		// System.out.print("chessCount: " + this.chessCount);
		// System.out.println("total: " + this.totalPositions);
		if (this.chessCount == this.totalPositions)
			return true;
		else 
			return false;
	}




}

	// public void printBoard() {
	// 	// first line
	// 	String chars = "abcdefghijklmnopqrstuvwxyz";
	// 	System.out.print("     ");
	// 	for (int a = 0; a < this.size; a++) {
	// 		System.out.print(chars.charAt(a % 17));
	// 		System.out.print(' ');
	// 	}
	// 	System.out.println(' ');

	// 	// second line
	// 	System.out.print("    ");
	// 	for (int a = 0; a < this.size; a++) {
	// 		System.out.print("__");
	// 	}
	// 	System.out.println(' ');

	// 	// other lines
	// 	for (int i = 0; i < this.size; i++) {
	// 		if (i < 10) {System.out.print(' ');}
	// 		System.out.print(i);
	// 		System.out.print(" | ");
	// 		for (int j = 0; j < this.size; j++) {
	// 			printSingleChess(this.chessStatus[i][j]);	
	// 		}
	// 		System.out.println(' ');
	// 	}
	// }










