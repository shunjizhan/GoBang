// package com.gobang;
import java.lang.Character;

public class Player {
	public int color;			// 1 is blck, -1 is white
	public boolean isAI;

	public Player() {
		this.color = 1;
		this.isAI = false;
	}

	public Player(int color) {
		this.color = color;
		this.isAI = false;
	}	

	public Player(int color, boolean isAI) {
		this.color = color;
		this.isAI = isAI;
	}

	public int[] decidePosition(int[][] chessStatus) {
		int boardSize = chessStatus.length;
		int random1, random2;
		int[] result = new int[2];

		while(true) {
			random1 = (int)(Math.random() * boardSize);
			random2 = (int)(Math.random() * boardSize);
			if (chessStatus[random1][random2] == 0) {
				result[0] = random1;
				result[1] = random2;
				return result;
			}
		}
	}




}

















