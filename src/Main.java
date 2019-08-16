public class Main {
	public static void main(String[] args) {
		Board board = new Board();
		while (board.isSolved() == false) {
			System.out.println("Current h: " + board.getCurrentHeuristic());
			System.out.println("Current State");
			board.printBoard();
			System.out.println("Neighbors found with lower h: " + board.countLowerNeighbors());
			if (board.needsRestart()) {
				System.out.println("RESTART");
				board.resetQueens();
			} else {
				System.out.println("Setting new current state");
				board.changeState();
			}
			System.out.println("");
		}
		
		System.out.println("Current State");
		board.printBoard();
		System.out.println("Solution Found!");
		System.out.println("State Changes: " + board.getStateChanges());
		System.out.println("Restarts: " + board.getRestarts());
	}
}
