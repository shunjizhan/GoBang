// package com.gobang;
import java.lang.Character;
import java.util.ArrayList;
import java.util.Arrays;

public class Player {
	public boolean isAI;
	public int color;			// 1 is blck, -1 is white
	public int depth;
	public ArrayList<int[]> nextPositions;

	public Player() {
		this.color = 1;
		this.isAI = false;
		this.depth = 2;
		this.nextPositions = new ArrayList<int[]>();
	}

	public Player(int color) {
		this.color = color;
		this.isAI = false;
		this.depth = 2;
		this.nextPositions = new ArrayList<int[]>();

	}	

	public Player(int color, boolean isAI, int depth) {
		this.color = color;
		this.isAI = isAI;
		this.depth = depth;
		this.nextPositions = new ArrayList<int[]>();
	}

	public int[] decidePosition(int[][] chessStatus) {
		System.out.println("deciding position... depth: " + this.depth);
		this.updatePotentialPositions(chessStatus);
		this.printPotentialPositions();


		return this.randomPosition(chessStatus);
	}

	public void updatePotentialPositions(int[][] chessStatus) {
		if (this.nextPositions.size() == 0 && this.color == 1) {
			int position[] = new int[2];
			int center = chessStatus.length / 2;		// in the center
			position[0] = center;
			position[1] = center;
			this.nextPositions.add(position);
		} else {
			this.nextPositions = new ArrayList<int[]>();
			for (int i = 0; i < chessStatus.length; i++) {
				for (int j = 0; j < chessStatus.length; j++) {
					int position[] = new int[2];
					position[0] = i;
					position[1] = j;
					if (this.isPotential(position, chessStatus)) {
						System.out.println("position: " + Arrays.toString(position));
						this.nextPositions.add(position);
					}
				}
			}
		}
		// System.out.println("nextPositions: " + this.nextPositions.toString());

	}

	public void printPotentialPositions() {
		System.out.print("potential positions: ");
		for (int[] p : this.nextPositions) {
			// System.out.print(intToChar(position[1]) + (position[0] + 1) + " ");
			System.out.print(Arrays.toString(p));
		}
		System.out.println(" ");
	}

	public boolean isPotential(int[] position, int[][] chessStatus) {
		return true;
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

















