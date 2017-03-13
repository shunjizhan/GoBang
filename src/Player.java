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

		int maxScore = -99999;
		int currentScore;
		int bestPosition[] = new int[2];
		for (int[] position : this.nextPositions) {
			// emulate next move
			int[][] newStatus = this.deepCopy(chessStatus);
			newStatus[position[0]][position[1]] = color;

			// evaluate this steps score
			currentScore = this.getBoardScore(newStatus, this.color);
			System.out.println("Positon: " + intToChar(position[1]) + "" + Integer.toString(position[0] + 1) + " Score: " + currentScore);

			if (currentScore > maxScore) {
				maxScore = currentScore;
				bestPosition = position;
			}

		}

		int temp[] = new int[2];
		temp[0] = bestPosition[1];
		temp[1] = bestPosition[0] + 1;
		System.out.println("bestPosition: " + intToChar(temp[0]) + Integer.toString(temp[1]));
		return temp;
	}

	public int getBoardScore(int[][] newStatus, int color) {
		/*************** ASSERT size >= 6 *******************/

		int size = newStatus.length;
		int good = 0;
		int bad = 0;
		String s = "";
		
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				// left
				s = "";
				for (int k = 0; k < 6; k++) {
					if (this.isInBoard(i, (j - k), size))
						s += this.getSymble(newStatus[i][j - k]);
				}
				good += this.evaluate(s, color);
				bad += this.evaluate(s, color * (-1));

				// right
				s = "";
				for (int k = 0; k < 6; k++) {
					if (this.isInBoard(i, (j + k), size))
						s += this.getSymble(newStatus[i][j + k]);
				}
				good += this.evaluate(s, color);
				bad += this.evaluate(s, color * (-1));			

				// up
				s = "";
				for (int k = 0; k < 6; k++) {
					if (this.isInBoard((i - k), j, size))
						s += this.getSymble(newStatus[i - k][j]);
				}
				good += this.evaluate(s, color);
				bad += this.evaluate(s, color * (-1));	

				// down
				s = "";
				for (int k = 0; k < 6; k++) {
					if (this.isInBoard((i + k), j, size))
						s += this.getSymble(newStatus[i + k][j]);
				}
				good += this.evaluate(s, color);
				bad += this.evaluate(s, color * (-1));	

				// 左上
				s = "";
				for (int k = 0; k < 6; k++) {
					if (this.isInBoard((i - k), (j - k), size))
						s += this.getSymble(newStatus[i - k][j - k]);
				}
				good += this.evaluate(s, color);
				bad += this.evaluate(s, color * (-1));	

				// 右上
				s = "";
				for (int k = 0; k < 6; k++) {
					if (this.isInBoard((i - k), (j + k), size))
						s += this.getSymble(newStatus[i - k][j + k]);
				}
				good += this.evaluate(s, color);
				bad += this.evaluate(s, color * (-1));	

				// 左下
				s = "";
				for (int k = 0; k < 6; k++) {
					if (this.isInBoard((i + k), (j - k), size))
						s += this.getSymble(newStatus[i + k][j - k]);
				}
				good += this.evaluate(s, color);
				bad += this.evaluate(s, color * (-1));		

				// 右下
				s = "";
				for (int k = 0; k < 6; k++) {
					if (this.isInBoard((i + k), (j + k), size))
						s += this.getSymble(newStatus[i + k][j + k]);
				}
				good += this.evaluate(s, color);
				bad += this.evaluate(s, color * (-1));		

			}
		}

		return good - bad + 1000;
	}

	public String getSymble(int i) {
		if (i == 1)
			return "X";
		else if (i == -1) 
			return "O";
		else 
			return "-";
	}

	public int evaluate(String s, int color) {
		// System.out.print("evaluating: " + s + " " + Integer.toString(color) + " score: ");
		if (color == 1) {
			int totalScore = 0;
			int subScore = 1;
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (c == 'X') {
					subScore *= 6;
				} else if (c == '-') {
					subScore *= 2;
				} else if (c == 'O'){
					totalScore += subScore;
					subScore = 1;
				}
			}
			totalScore += subScore;
			// System.out.println(totalScore);
			return totalScore;
		} else {
			int totalScore = 0;
			int subScore = 1;
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (c == 'O') {
					subScore *= 6;
				} else if (c == '-') {
					subScore *= 2;
				} else if (c == 'X') {
					totalScore += subScore;
					subScore = 1;
				}
			}
			totalScore += subScore;
			// System.out.println(totalScore);
			return totalScore;
		}
	}

	public void printStringArray(String[] arr) {
		for (int k = 0; k < arr.length; k++) {
			System.out.println(arr[k]);
		}
		System.out.println(" ");
	}

	public int[][] deepCopy(int[][] arr) {
		int size = arr.length;
		int[][] copy = new int[size][size];
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				copy[i][j] = arr[i][j];
			}
		}
		return copy;
	}

	// change to return int[][]
	public void updatePotentialPositions(int[][] chessStatus) {
					// System.out.println(this.nextPositions.size() == 0);

		if (this.nextPositions.size() == 0 && this.color == 1) {
			// System.out.println("first position!!!!!!!");
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
						// System.out.println("position: " + Arrays.toString(position));
						this.nextPositions.add(position);
					}
				}
			}
		}
	}

	public void printPotentialPositions() {
		System.out.print("potential positions: ");
		for (int[] p : this.nextPositions) {
			char letter = intToChar(p[1]);
			int number = p[0] + 1;
			System.out.print(letter + Integer.toString(number) + " ");
			// System.out.print(Arrays.toString(p));
		}
		System.out.println(" ");
	}

	public boolean isPotential(int[] position, int[][] chessStatus) {
		int maxDistance = 2;
		int x = position[0];
		int y = position[1];
		int leftTopX = x - maxDistance;
		int leftTopY = y - maxDistance;

		// go through all distance within limit
		for (int i = leftTopX; i < leftTopX + 2 * maxDistance + 1; i++) {
			for (int j = leftTopY; j < leftTopY + 2 * maxDistance + 1; j++) {
				if (
					  this.isInBoard(i, j, chessStatus.length) &&
						chessStatus[x][y] == 0 &&
					  chessStatus[i][j] != 0
					) {
					return true;
				}
			}
		}
		return false;
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

	public boolean isInBoard(int x, int y, int size) {
		return (x >= 0 && y >= 0 && x < size && y < size);
	}

	public int charToInt(char c) {
      return (int) c - 97;
    }

  public char intToChar(int i) {
    return (char) (i + 97);
  }

}

	// public int getBoardScore(int[][] chessStatus, int[] position, int color, int depth) {
	// 	/*************** ASSERT size >= 6 *******************/

	// 	// emulate next move
	// 	int[][] newStatus = this.deepCopy(chessStatus);
	// 	newStatus[position[0]][position[1]] = color;

	// 	int size = newStatus.length;
	// 	String[] rows = new String[size];
	// 	String[] columes = new String[size];
	// 	String[] diagnal1 = new String[2 * (size - 5) - 1];
	// 	String[] diagnal2 = new String[2 * (size - 5) - 1];
	// 	String s;

	// 	// rows
	// 	for (int i = 0; i < size; i++) {
	// 		s = "";
	// 		for (int j = 0; j < size; j++) {
	// 			s += Integer.toString(newStatus[i][j]);
	// 		}
	// 		rows[i] = s;
	// 	}

	// 	// columes
	// 	for (int i = 0; i < size; i++) {
	// 		s = "";
	// 		for (int j = 0; j < size; j++) {
	// 			s += Integer.toString(newStatus[j][i]);
	// 		}
	// 		columes[i] = s;
	// 	}

	// 	// diagnal 1
	// 	int count = 0;
	// 	for (int i = 0; i <= size - 6; i++) {
	// 		s = "";
	// 		int x = 0;
	// 		int y = i;
	// 		for (int j = 0; j < 6; j++) {
	// 			s += newStatus[x][y];
	// 			x++;
	// 			y++;
	// 		}
	// 		diagnal1[count] = s;
	// 		count++;
	// 	}
	// 	for (int i = 1; i <= size - 6; i++) {
	// 		s = "";
	// 		int x = i;
	// 		int y = 0;
	// 		for (int j = 0; j < 6; j++) {
	// 			s += newStatus[x][y];
	// 			x++;
	// 			y++;
	// 		}
	// 		diagnal1[count] = s;
	// 		count++;
	// 	}

	// 	// diagnal 2
	// 	count = 0;
	// 	for (int i = size - 6; i < size; i++) {
	// 		s = "";
	// 		int x = 0;
	// 		int y = i;
	// 		for (int j = 0; j < 6; j++) {
	// 			s += newStatus[x][y];
	// 			x--;
	// 			y++;
	// 		}
	// 		diagnal1[count] = s;
	// 		count++;
	// 	}
	// 	for (int i = 1; i <= size - 6; i++) {
	// 		s = "";
	// 		int x = size;
	// 		int y = i;
	// 		for (int j = 0; j < 6; j++) {
	// 			s += newStatus[x][y];
	// 			x--;
	// 			y++;
	// 		}
	// 		diagnal1[count] = s;
	// 		count++;
	// 	}

		
	// 	System.out.println("rows------------------");
	// 	this.printStringArray(rows);
	// 	// System.out.println("Columes---------------");
	// 	// this.printStringArray(columes);
	// 	System.out.println("diagnal 1---------------");
	// 	this.printStringArray(diagnal1);		
	// 	System.out.println("diagnal 2---------------");
	// 	this.printStringArray(diagnal2);

	// 	// if (size >= 7) {
	// 	// 	ArrayList<String> allStrings = new ArrayList<String>();
	// 	// 	int good = 0;
	// 	// 	int bad = 0;

	// 	// 	// horizontal
	// 	// 	good += getScore()
	// 	// }

	// 	return 1;
	// }
















