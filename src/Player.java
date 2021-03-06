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
		this.depth = 3;
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

	public ArrayList<int[]> decidePosition(int[][] chessStatus) {
		// if (this.nextPositions.size() < 10) {
		// 	System.out.println("AAAAA 4");
		// 	ArrayList<int[]> fourStepResult = this.decidePosition(chessStatus, this.depth + 2, this.color);
		// 	System.out.println("BBBBB 4");

		// 	// emulate next move 
		// 	int x = fourStepResult.get(0)[0];
		// 	int y = fourStepResult.get(0)[1];
		// 	int[][] newStatus = this.deepCopy(chessStatus);
		// 	newStatus[x][y] = color;

		// 	int opponentNextScore = this.decidePosition(newStatus, 1, this.color * (-1)).get(1)[0];
		// 	System.out.println("CCCCC 4");

		// 	if ( opponentNextScore != 88888888 && opponentNextScore != 7777777) {
		// 		return fourStepResult;
		// 	} else {
		// 		return this.decidePosition(chessStatus, this.depth, this.color);
		// 	}

		// } else if (this.nextPositions.size() < 30) {
		// if (this.nextPositions.size() < 25) {
		// 	// System.out.println("AAAAA 3 ");
		// 	ArrayList<int[]> fourStepResult = this.decidePosition(chessStatus, this.depth + 1, this.color);
		// 	// System.out.println("BBBBB 3");

		// 	// emulate this move 
		// 	int x = fourStepResult.get(0)[1] - 1;
		// 	int y = fourStepResult.get(0)[0];
		// 	int[][] n = this.deepCopy(chessStatus);
		// 	n[x][y] = this.color;

		// 	ArrayList<int[]> opponentNextMove = this.decidePosition(n, 1, this.color * (-1));
		// 	int opponentNextScore = opponentNextMove.get(1)[0];
		// 	// int a = opponentNextMove.get(0)[0];
		// 	// int b = opponentNextMove.get(0)[1];
		// 	// System.out.println("opponentNextScore: " + opponentNextScore + " " + intToChar(a) + b);
			
		// 	// if ( opponentNextScore != 88888888 && opponentNextScore != 7777777) {
		// 	if ( opponentNextScore < 6666666) {
		// 		return fourStepResult;
		// 	} else {
		// 		return this.decidePosition(chessStatus, this.depth, this.color);
		// 	}
		// 	// return this.decidePosition(chessStatus, this.depth + 1, this.color);
		// } else {
			return this.decidePosition(chessStatus, this.depth, this.color);
		// }
	}

	public double weight(int[] position, int size) {
		double centerX = size / 2;
		double centerY = size / 2;
		double x = position[0];
		double y = position[1];

		double distance = Math.sqrt ((x - centerX) * (x - centerX) + (y - centerY) * (y - centerY)) + 1;

		return (10 / distance + 10000.0) / 10000.0;
	}

	public ArrayList<int[]> decidePosition(int[][] chessStatus, int depth, int color) {
		if (depth == 0) {
			return this.randomPosition(chessStatus);
		}

		int size = chessStatus.length;

		// .get(0) : best position (int[2]) , .get(1)[0] : best score
		ArrayList<int[]> best = new ArrayList<int[]>();

		// if (depth >= 2) {
		// 	System.out.println("deciding position... depth: " + depth);
		// }
		this.updatePotentialPositions(chessStatus);
		// System.out.println("deciding in: " + this.nextPositions.size() + "position");

		// this.printPotentialPositions();

		int temp[] = new int[2];
		int temp2[] = new int[1];

		double maxScore = -99999999;
		double currentScore;
		int bestPosition[] = new int[2];
		for (int[] position : this.nextPositions) {
			// emulate next move
			int[][] newStatus = this.deepCopy(chessStatus);
			newStatus[position[0]][position[1]] = color;

			if (depth == 1 || this.nextPositions.size() <= 0) {
				// evaluate this steps score
				// System.out.println("current weight: " + this.weight(position, size));
				int boardScore = this.getBoardScore(newStatus, color);
				if (boardScore > 6666666) {
					// if five or four, return this move directly

					temp[0] = position[1];
					temp[1] = position[0] + 1;
					temp2[0] = (int) boardScore;
					// System.out.println("depth: +" + depth+ "bestPosition: " + intToChar(temp[0]) + Integer.toString(temp[1]) + " score: " + temp2[0]);

					best.add(temp);
					best.add(temp2);
					return best;
				}

				int aa = position[0] + 1;
				// if (boardScore > 7777700) {System.out.println("color: " + color + " 88888888: " + intToChar(position[1]) + aa);}
				// System.out.println("color: " + color + " position: " + intToChar(position[1]) + aa + "boardScore: " + boardScore);
				currentScore = boardScore * this.weight(position, size);
				// System.out.println("Positon: " + intToChar(position[1]) + "" + Integer.toString(position[0] + 1) + " Score: " + currentScore);
			} else {
				currentScore = (-1) * this.decidePosition(newStatus, depth - 1, color * (-1)).get(1)[0];

				if (currentScore > 6666666) {
					temp[0] = position[1];
					temp[1] = position[0] + 1;
					temp2[0] = (int) currentScore;
					// System.out.println("depth: +" + depth+ "bestPosition: " + intToChar(temp[0]) + Integer.toString(temp[1]) + " score: " + temp2[0]);

					best.add(temp);
					best.add(temp2);
					return best;

				}
				// if (depth == 2)
				// 	System.out.println("depth: +" + depth+ "Position: " + intToChar(position[1]) + Integer.toString(position[0] + 1) + " score: " + currentScore);
			}

			// System.out.println("current score: " + currentScore);
			if (currentScore > maxScore) {
				maxScore = currentScore;
				bestPosition = position;
			}

		}

		temp[0] = bestPosition[1];
		temp[1] = bestPosition[0] + 1;
		temp2[0] = (int) maxScore;

		// if (depth == 2)
		// 	System.out.println("depth: +" + depth+ "bestPosition: " + intToChar(temp[0]) + Integer.toString(temp[1]) + " score: " + temp2[0]);

		best.add(temp);
		best.add(temp2);
		return best;
	}

	public String getSymble(int i) {
		if (i == 1)
			return "X";
		else if (i == -1) 
			return "O";
		else 
			return "-";
	}

	String modify(String s) {
		String result = "";
		for (char c : s.toCharArray()) {
			if (c == 'X')
				result += 'O';
			if (c == 'O')
				result += 'X';
			if (c == '-')
				result += '-';
		}
		return result;
	}

	public char intToXO(int i) {
		if (i == 1)
			return 'X';
		if (i == -1)
			return 'O';
		if (i == 0)
			return '-';
		else 
			return '#';
	}	

	public int evaluate(String s, int color) {
		// System.out.println("evalueting: " + s);
		if (s.length() < 6) {
			return 0;
		}

		if (color == -1) {
			s = this.modify(s);
		}

		int score = 0;

		// 五连 999999
		if (subStringNum(s, "XXXXX-") > 0) { return 88888888; }
		if (subStringNum(s, "-XXXXX") > 0) { return 88888888; }
		if (subStringNum(s, "XXXXXO") > 0) { return 88888888; }
		if (subStringNum(s, "OXXXXX") > 0) { return 88888888; }

		// 活四 10000
		if (subStringNum(s, "-XXXX-") > 0) {
			return 7777777;
		}
		// score += 10000 * subStringNum(s, "-XXXX-"); 

		// 冲四 5000
		score += 5000 * subStringNum(s, "-XXXXO"); 
		score += 5000 * subStringNum(s, "X-XXXO"); 
		score += 5000 * subStringNum(s, "XX-XXO"); 
		score += 5000 * subStringNum(s, "XXX-XO"); 
		score += 5000 * subStringNum(s, "XXXX-O"); 
		score += 5000 * subStringNum(s, "O-XXXX"); 
		score += 5000 * subStringNum(s, "OX-XXX"); 
		score += 5000 * subStringNum(s, "OXX-XX"); 
		score += 5000 * subStringNum(s, "OXXX-X"); 
		score += 5000 * subStringNum(s, "OXXXX-");

		// 活三 1000
		score += 1000 * subStringNum(s, "-XXX--"); 
		score += 1000 * subStringNum(s, "-XX-X-"); 
		score += 1000 * subStringNum(s, "-X-XX-"); 
		score += 1000 * subStringNum(s, "--XXX-"); 

		// 眠三 500
		score += 500 * subStringNum(s, "OXXX--"); 
		score += 500 * subStringNum(s, "OXX-X-"); 
		score += 500 * subStringNum(s, "OX-XX-"); 
		score += 500 * subStringNum(s, "O-XXX-");
		score += 500 * subStringNum(s, "OXX--X"); 
		score += 500 * subStringNum(s, "OX-X-X"); 
		score += 500 * subStringNum(s, "O-XX-X"); 
		score += 500 * subStringNum(s, "OX--XX"); 

		score += 500 * subStringNum(s, "XXX--O"); 
		score += 500 * subStringNum(s, "XX-X-O"); 
		score += 500 * subStringNum(s, "X-XX-O"); 
		score += 500 * subStringNum(s, "-XXX-O");
		score += 500 * subStringNum(s, "XX--XO"); 
		score += 500 * subStringNum(s, "X-X-XO"); 
		score += 500 * subStringNum(s, "-XX-XO"); 
		score += 500 * subStringNum(s, "X--XXO");

		// 活二 100
		score += 100 * subStringNum(s, "--XX--"); 
		score += 100 * subStringNum(s, "-X-X--"); 
		score += 100 * subStringNum(s, "-XX---"); 
		score += 100 * subStringNum(s, "-X--X-");
		score += 100 * subStringNum(s, "--X-X-");
		score += 100 * subStringNum(s, "---XX-"); 

		// 眠二 10
		score += 50 * subStringNum(s, "OXX---"); 
		score += 50 * subStringNum(s, "OX-X--"); 
		score += 50 * subStringNum(s, "O-XX--"); 
		score += 50 * subStringNum(s, "O---XX"); 
		score += 50 * subStringNum(s, "OX--X-"); 
		score += 50 * subStringNum(s, "O-X-X-"); 
		score += 50 * subStringNum(s, "O--XX-");
		score += 50 * subStringNum(s, "OX---X"); 
		score += 50 * subStringNum(s, "O-X--X"); 
		score += 50 * subStringNum(s, "O--X-X"); 
		score += 50 * subStringNum(s, "XX---O"); 
		score += 50 * subStringNum(s, "X-X--O"); 
		score += 50 * subStringNum(s, "X--X-O"); 
		score += 50 * subStringNum(s, "X---XO"); 
		score += 50 * subStringNum(s, "-XX--O"); 
		score += 50 * subStringNum(s, "-X-X-O"); 
		score += 50 * subStringNum(s, "-X--XO"); 
		score += 50 * subStringNum(s, "--XX-O"); 
		score += 50 * subStringNum(s, "--X-XO"); 
		score += 50 * subStringNum(s, "---XXO");

		// 活一 100
		score += 30 * subStringNum(s, "----X-");
		score += 30 * subStringNum(s, "---X--"); 
		score += 30 * subStringNum(s, "--X---"); 
		score += 30 * subStringNum(s, "-X----"); 

		// 眠一 100
		score += 10 * subStringNum(s, "----XO");
		score += 10 * subStringNum(s, "---X-O"); 
		score += 10 * subStringNum(s, "--X--O"); 
		score += 10 * subStringNum(s, "-X---O"); 		
		score += 10 * subStringNum(s, "O---X-");
		score += 10 * subStringNum(s, "O--X--"); 
		score += 10 * subStringNum(s, "O-X---"); 
		score += 10 * subStringNum(s, "OX----"); 

		// 死四 -5
		score += (-20) * subStringNum(s, "OXXXXO");
		score += (-20) * subStringNum(s, "OXXX-O");
		score += (-20) * subStringNum(s, "OXX-XO");
		score += (-20) * subStringNum(s, "OX-XXO");
		score += (-20) * subStringNum(s, "O-XXXO");

		// 死三 -5
		score += (-20) * subStringNum(s, "OXXXO");
		score += (-20) * subStringNum(s, "OXX-O");
		score += (-20) * subStringNum(s, "OX-XO");
		score += (-20) * subStringNum(s, "O-XXO");

		// 死二 -5
		score += (-20) * subStringNum(s, "OXXO");		
		score += (-20) * subStringNum(s, "OX-O");		
		score += (-20) * subStringNum(s, "O-XO");		

		// 死二 -5
		score += (-20) * subStringNum(s, "OXO");
		score += (-20) * subStringNum(s, "O-O");

		return score;
	}

	public int subStringNum(String str, String sub) {
		// if (sub.equals("OXXXXX") && str.length() > 9) {
		// 	System.out.println(str);
		// }

		if (str.length() < 6 || sub.length() < 6) {
			return 0;
		}

		// System.out.print("String = " + str + " " + "sub = " + sub + " ");

		int count = 0;
		String six = "";
		for (int i = 0; i < str.length() - 5; i++) {
			// System.out.print("six = " + six + " ");
			six = str.substring(i, i + 6);
			if (six.equals(sub)) {
				count++;
			}
		}
		// System.out.println("count = " + count);

		return count ;
	}

	public void printStringArray(ArrayList<String> arr) {
		for (int k = 0; k < arr.size(); k++) {
			System.out.println(arr.get(k));
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
					// System.out.print("checking: " + intToChar(j) + i + ' ');
					position[0] = i;
					position[1] = j;
					if (this.isPotential(position, chessStatus)) {
						// System.out.println("position: " + Arrays.toString(position));
						// System.out.println("good!");
						this.nextPositions.add(position);
					} else {
						// System.out.println("bad!");
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

		int maxDistance = 1;
		int x = position[0];
		int y = position[1];
		int leftTopX = x - maxDistance;
		int leftTopY = y - maxDistance;

		// System.out.println("checking: " + this.intToChar(position[1]) + position[0]);
		// System.out.println("in board: " + this.isInBoard(i, j, chessStatus.length));


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

	public ArrayList<int[]> randomPosition(int[][] chessStatus) {
		ArrayList<int[]> r = new ArrayList<int[]>();
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
				r.add(result);
				return r;
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

  	public int getBoardScore(int[][] newStatus, int color) {
		/*************** ASSERT size >= 6 *******************/

		int size = newStatus.length;

		// ArrayList<String> rows = new ArrayList<String>();
		// ArrayList<String> columes = new ArrayList<String>();
		// ArrayList<String> diagnal1 = new ArrayList<String>();
		// ArrayList<String> diagnal2 = new ArrayList<String>();
		String s;

		int good = 0;
		int bad = 0;
		int g = 0;
		int b = 0;

		// rows
		// System.out.println("rows");
		for (int i = 0; i < size; i++) {
			s = "";
			for (int j = 0; j < size; j++) {
				s += this.intToXO(newStatus[i][j]);
			}
			g = this.evaluate(s, color);
			if (g == 88888888) {
				// System.out.println("88888888");
				return 88888888;
			}
			if (g == 7777777) {
				// System.out.println("7777777");
				return 7777777;
			}

			b = this.evaluate(s, color * (-1));
			// if (g == 88888888) return -88888888;
			// if (g == 7777777) return -7777777;

			good += g;
			bad += b;
			// rows.add(s);
		}

		// System.out.println("Columes");
		// columes
		for (int i = 0; i < size; i++) {
			s = "";
			for (int j = 0; j < size; j++) {
				s += this.intToXO(newStatus[j][i]);
			}
			g = this.evaluate(s, color);
			if (g == 88888888) {
				// System.out.println("88888888");
				return 88888888;
			}
			if (g == 7777777) {
				// System.out.println("7777777");
				return 7777777;
			}

			b = this.evaluate(s, color * (-1));
			// if (g == 88888888) return -88888888;
			// if (g == 7777777) return -7777777;

			good += g;
			bad += b;
			// columes.add(s);
		}

		// System.out.println("diagnal11");
		// diagnal 1
		for (int i = 0; i < size; i++) {
			s = "";
			int x = 0;
			int y = i;
			for (int j = 0; j < size - i; j++) {
				if (this.isInBoard(x, y, size)) {
					s += this.intToXO(newStatus[x][y]);
					x++;
					y++;
				}
			}
			g = this.evaluate(s, color);
			if (g == 88888888) {
				// System.out.println("88888888");
				return 88888888;
			}
			if (g == 7777777) {
				// System.out.println("7777777");
				return 7777777;
			}

			b = this.evaluate(s, color * (-1));
			// if (g == 88888888) return -88888888;
			// if (g == 7777777) return -7777777;

			good += g;
			bad += b;
			
			// diagnal1.add(s);
		}
		// System.out.println("diagnal12");
		for (int i = 1; i < size; i++) {
			s = "";
			int x = i;
			int y = 0;
			for (int j = 0; j < size - i; j++) {
				if (this.isInBoard(x, y, size)) {
					// System.out.println("intTOXO: " + newStatus[x][y] + ' ' + this.intToXO(newStatus[x][y]));
					s += this.intToXO(newStatus[x][y]);
					x++;
					y++;
				}
			}
			g = this.evaluate(s, color);
			if (g == 88888888) {
				// System.out.println("88888888");
				return 88888888;
			}
			if (g == 7777777) {
				// System.out.println("7777777");
				return 7777777;
			}

			b = this.evaluate(s, color * (-1));
			// if (g == 88888888) return -88888888;
			// if (g == 7777777) return -7777777;

			good += g;
			bad += b;
			// diagnal1.add(s);
		}

		// diagnal 2
		// System.out.println("diagnal21");
		for (int i = 0; i < size; i++) {
			s = "";
			int x = size - i - 1;
			int y = 0;
			for (int j = 0; j < size - i; j++) {
				if (this.isInBoard(x, y, size)) {
					s += this.intToXO(newStatus[x][y]);
					x--;
					y++;
				}
			}
			g = this.evaluate(s, color);
			if (g == 88888888) {
				// System.out.println("88888888");
				return 88888888;
			}
			if (g == 7777777) {
				// System.out.println("7777777");
				return 7777777;
			}

			b = this.evaluate(s, color * (-1));
			// if (g == 88888888) return -88888888;
			// if (g == 7777777) return -7777777;

			good += g;
			bad += b;
			// diagnal2.add(s);
		}
		// System.out.println("diagnal22");
		for (int i = 1; i < size; i++) {
			s = "";
			int x = size - 1;
			int y = i;
			for (int j = 0; j < size - i; j++) {
				if (this.isInBoard(x, y, size)) {
					s += this.intToXO(newStatus[x][y]);
					x--;
					y++;
				}
			}
			g = this.evaluate(s, color);
			if (g == 88888888) {
				// System.out.println("88888888");
				return 88888888;
			}
			if (g == 7777777) {
				// System.out.println("7777777");
				return 7777777;
			}

			b = this.evaluate(s, color * (-1));
			// if (g == 88888888) return -88888888;
			// if (g == 7777777) return -7777777;

			good += g;
			bad += b;
			// diagnal2.add(s);
		}

		
		// System.out.println("rows------------------");
		// this.printStringArray(rows);
		// System.out.println("Columes---------------");
		// this.printStringArray(columes);
		// System.out.println("diagnal 1---------------");
		// this.printStringArray(diagnal1);		
		// System.out.println("diagnal 2---------------");
		// this.printStringArray(diagnal2);

		// System.out.println(Integer.toString(good) + ' ' + Integer.toString(bad));
		return good - bad;
	}

}

	// public int evaluate(String s, int color) {
	// 	// System.out.print("evaluating: " + s + " " + Integer.toString(color) + " score: ");
	// 	if (color == 1) {
	// 		int totalScore = 0;
	// 		int subScore = 1;
	// 		for (int i = 0; i < s.length(); i++) {
	// 			char c = s.charAt(i);
	// 			if (c == 'X') {
	// 				subScore *= 6;
	// 			} else if (c == '-') {
	// 				subScore *= 2;
	// 			} else if (c == 'O'){
	// 				totalScore += subScore;
	// 				subScore = 1;
	// 			}
	// 		}
	// 		totalScore += subScore;
	// 		// System.out.println(totalScore);
	// 		return totalScore;
	// 	} else {
	// 		int totalScore = 0;
	// 		int subScore = 1;
	// 		for (int i = 0; i < s.length(); i++) {
	// 			char c = s.charAt(i);
	// 			if (c == 'O') {
	// 				subScore *= 6;
	// 			} else if (c == '-') {
	// 				subScore *= 2;
	// 			} else if (c == 'X') {
	// 				totalScore += subScore;
	// 				subScore = 1;
	// 			}
	// 		}
	// 		totalScore += subScore;
	// 		// System.out.println(totalScore);
	// 		return totalScore;
	// 	}
	// }
















