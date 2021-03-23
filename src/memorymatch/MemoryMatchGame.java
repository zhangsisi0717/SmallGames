package memorymatch;

import java.awt.Color;

import edu.princeton.cs.introcs.StdDraw;

public class MemoryMatchGame {
	private static final int X = 0;
	private static final int Y = 1;

	private static void defineWorldBoundaries() {
		// change values to a more convenient space if you so desire
		double xMin = 0.0;
		double xMax = 1.0;
		double yMin = 0.0;
		double yMax = 1.0;

		
		StdDraw.setXscale(xMin, xMax);
		StdDraw.setYscale(yMin, yMax);
	}

	private static double[] waitForMouseClick() {
		// wait for the mouse to be pressed
		while (!StdDraw.isMousePressed()) {
			StdDraw.pause(10);
		}
		double x = StdDraw.mouseX();
		double y = StdDraw.mouseY();
		// wait for the mouse to be released
		while (StdDraw.isMousePressed()) {
			StdDraw.pause(10);
		}
		// return the current mouse position
		
		double[] pos = {x,y};
		return pos;
		
	}

	private static int toRow(double[] xy, int numRows) {
		double y = xy[X];
		int rowIdx = (int) (y*numRows);
		
		return rowIdx;
		
	}

	private static int toColumn(double[] xy, int numCols) {
		double x = xy[Y];
		int colIdx = (int) (x*numCols);
		return colIdx;
			
	}

	private static int[] waitForClickOnRowColumn() {
		int numRows = 4;
		int numCols = 4;
		double[] xy = waitForMouseClick();
		int r = toRow(xy,numRows);
		int c = toColumn(xy,numCols);
		return new int[] { r, c };
}


	private static void drawBoard(Color[][] board, boolean[][] masked) {
		 StdDraw.clear();
		 StdDraw.enableDoubleBuffering();
		 double halfLength = 1.0/(board.length*2);
		 for(int i=0; i<board.length;++i) {
			 for(int j=0; j<board[0].length;++j){
					 double curX = halfLength * (2 * i + 1);
					 double curY = halfLength * (2 * j + 1);
					 if(masked[i][j]) {
						 StdDraw.setPenColor(Color.white);
					 }
					 else {
						 StdDraw.setPenColor(board[i][j]);
					 }
					 StdDraw.filledSquare(curX, curY, halfLength);
					 StdDraw.setPenColor(Color.black);
					 StdDraw.square(curX, curY, halfLength);
				 }
			 }
		 StdDraw.show();
	}

	
	public static boolean isGameAlive(boolean[][] masked) {
		for(int i = 0; i < masked.length; i++) {
			for(int j = 0; j < masked.length; j++) {
				if(masked[i][j]) {
					return true;
				}
			}
		}
		return false;
	}
	
	private static void drawWinningPage(int totalClicks) {
		 StdDraw.clear();
		 StdDraw.text(0.5, 0.5, "You win! Total clicks: " + totalClicks);
		 StdDraw.show();
	}
	
	public static void playGame() {
		int timeToPause = 500;
		defineWorldBoundaries();
		Color[] palette = MemoryMatchBoard.getPalette();
		
		while(true) {
			Color[][] board = MemoryMatchBoard.generateShuffled4x4Board(palette);
			
			boolean[][] masked = new boolean[4][4];
			for(int i=0; i<board.length;++i) {
				 for(int j=0; j<board[0].length;++j){
					 masked[i][j] = true;
				 }
			}
			
			boolean waitingForSecondClick = false;
			int previousR = -1;
			int previousC = -1;
			
			int totalClicks = 0;
			
			// game begins
			drawBoard(board, masked);	//Draw initial board
			while(isGameAlive(masked)) { // consider if game is alive
				int[] rc = waitForClickOnRowColumn();
				int r = rc[X];
				int c = rc[Y];
				if(!masked[r][c]) { //if current block is already revealed, then continue
					continue;
				}
				if(!waitingForSecondClick) { //if not waiting for the second click
					masked[r][c] = false;
					waitingForSecondClick = true;
					previousR = r;	//assign current row/columns to previous row/columns
					previousC = c;
					drawBoard(board, masked); //show current block
				}
				else {
					masked[r][c] = false;
					drawBoard(board, masked);
					if(board[r][c] != board[previousR][previousC]) {
						StdDraw.pause(timeToPause);  // pause for some time
						masked[r][c] = true;
						masked[previousR][previousC] = true;
						drawBoard(board, masked);
					}
					waitingForSecondClick = false;
					previousR = -1;
					previousC = -1;
				}
				totalClicks += 1;
			}
			StdDraw.pause(timeToPause);
			drawWinningPage(totalClicks);
			waitForClickOnRowColumn(); //click again to start a new game
		}
	}

	public static void main(String[] args) {
		playGame();
	}

}
