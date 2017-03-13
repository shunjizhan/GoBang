// package com.gobang;
import java.lang.Character;

public class Player {
	public int color;			// 1 is blck, -1 is white
	public boolean isAI;
	public int depth;

	public Player() {
		this.color = 1;
		this.isAI = false;
		this.depth = 2;
	}

	public Player(int color) {
		this.color = color;
		this.isAI = false;
		this.depth = 2;
	}	

	public Player(int color, boolean isAI, int depth) {
		this.color = color;
		this.isAI = isAI;
		this.depth = depth;
	}

	public int[] decidePosition(int[][] chessStatus) {
		System.out.println("deciding position... depth: " + this.depth);
		return this.randomPosition(chessStatus);
	}

	public int[] randomPosition(int[][] chessStatus) {
		int boardSize = chessStatus.length;
		int random1, random2;
		int[] result = new int[2];

		while(true) {
			random1 = (int)(Math.random() * boardSize);
			random2 = (int)(Math.random() * boardSize);
			if (chessStatus[random1][random2] == 0) {
				result[0] = random2;		// letter
				result[1] = (random1 + 1);	// number
				System.out.println("decided: " + intToChar(random2) + result[1]);
				return result;
			}
		}	
	}

	public int charToInt(char c) {
      return (int) c - 97;
    }

    public char intToChar(int i) {
      return (char) (i + 97);
    }




}

















