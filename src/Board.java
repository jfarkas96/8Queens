public class Board {
	int[][] board = new int[8][8];
	int[][] heuristicsBoard = new int[8][8];
	int stateChanges = 0;
	int restarts = 0;
	int lowerNeighbors = 0;

	public Board() {
		for (int c=0; c<8; c++) {
			board[c][(int)(Math.random()*7)] = 1;
		}
		getNeighborStates();
	}


	public void printBoard() {
		for (int r=0; r<8; r++) {
			for (int c=0; c<8; c++) {
				System.out.print(board[c][r]);
				if (c<7) {
					System.out.print(",");
				}
			}
			System.out.println();
		}
		System.out.println();
	}

	public void resetQueens() {
		board = new int[8][8];
		for (int c=0; c<8; c++) {
			board[c][(int)(Math.random()*7)] = 1;
		}
		restarts++;
		getNeighborStates();
	}
	
	public int getRestarts() {
		return restarts;
	}
	
	public int getStateChanges() {
		return stateChanges;
	}

	public int getCurrentHeuristic() {
		int h=0;
		h = checkDiagonals()+checkColumns()+checkRows();
		return h;
	}

	public void getNeighborStates() {
		heuristicsBoard = new int[8][8];
		int[][] copyBoard = copy8DimArray(board);
		int[][] boardHolder = new int[8][8];
		for (int c=0; c<8; c++) {
			for (int r=0; r<8; r++) {
				copyBoard[c][r]=0;
			}
			for (int r=0; r<8; r++) {
				copyBoard[c][r]=1;
				boardHolder = copy8DimArray(board);
				board = copy8DimArray(copyBoard);
				heuristicsBoard[c][r] = getCurrentHeuristic();
				board = copy8DimArray(boardHolder);
				copyBoard[c][r]=0;
			}
			copyBoard = copy8DimArray(board);
		}
	}

	public int countLowerNeighbors() {
		lowerNeighbors = 0;
		int currentH = getCurrentHeuristic();
		for (int r=0; r<8; r++) {
			for (int c=0; c<8; c++) {
				if (heuristicsBoard[c][r]<currentH) {
					lowerNeighbors++;
				}
			}
		}
		return lowerNeighbors;
	}

	public void changeState() {
		int newC = -1;
		int newR = -1;
		int currentH = getCurrentHeuristic();
		int minH = currentH;
		for (int r=0; r<8; r++) {
			for (int c=0; c<8; c++) {
				if (heuristicsBoard[c][r]<minH) {
					newC=c;
					newR=r;
					minH=heuristicsBoard[c][r];
				}
			}
		}
		
		for (int r=0; r<8; r++) {
			board[newC][r]=0;
		}
		board[newC][newR]=1;
		stateChanges++;
		getNeighborStates();
	}


	public int checkDiagonals() {
		int h=0;

		//Diagonals going one way
		for(int k=0; k<8*2-1; k++) {
			int count = 0;
			for(int c=0; c<=k; c++) {
				int r=k-c;
				if( r<8 && c<8 ) {
					if (board[c][r]==1){
						count++;
					}
				}	
			}
			if (count>1) {
				int problems = (count*(count-1))/2;
				h= h+problems;
			}
		}

		//Diagonals going other way
		for (int k=-7; k<8; k++) {
			int count=0;
			for(int c=0; c<=k+7; c++) {
				int r=c-k;
				if( r<8 && c<8 && r>-1 && c >-1) {
					if (board[c][r]==1){
						count++;
					}
				}	
			}
			if (count>1) {
				int problems = (count*(count-1))/2;
				h= h+problems;
			}
		}

		return h;
	}

	public int checkRows() {
		int h=0;
		for (int r=0; r<8; r++) {
			int count = 0;
			for (int c=0; c<8; c++) {
				if (board[c][r]==1) {
					count++;
				}
			}
			if (count>1) {
				int problems = (count*(count-1))/2;
				h= h+problems;
			}
		}
		return h;
	}

	public int checkColumns() {
		int h=0;
		for (int c=0; c<8; c++) {
			int count = 0;
			for (int r=0; r<8; r++) {
				if (board[c][r]==1) {
					count++;
				}
			}
			if (count>1) {
				int problems = (count*(count-1))/2;
				h= h+problems;
			}
		}
		return h;
	}

	public int[][] copy8DimArray(int[][] array){
		int[][] copy = new int[8][8];
		for (int r=0; r<8; r++) {
			for (int c=0; c<8; c++) {
				copy[c][r]=array[c][r];
			}
		}
		return copy;
	}
	
	public boolean isSolved() {
		if (getCurrentHeuristic()==0) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean needsRestart() {
		if (getCurrentHeuristic()>0 && countLowerNeighbors()==0) {
			return true;
		} else {
			return false;
		}
	}
}
