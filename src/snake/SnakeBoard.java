package snake;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import edu.princeton.cs.introcs.StdDraw;

public class SnakeBoard {
	/**
	 *
	 * @param: boardsize: user input NXN:
	 *         blockwidth: 1/boardsize
	 *         idToIdentity: list contains identity of each block
	 *         idToCoordinate: list of coordinates of each block               
	 */
	private int boardSize;
	private double blockwidth;
	private int numFood;
	private int numBricks;
	private int snakeSize;
	private static int UP = 0;
	private static int DOWN = 1;
	private static int LEFT = 2;
	private static int RIGHT = 3;
	private static int FOOD = 0;
	private static int BRICK = 1;
	private static int EMPTY_BLOCK = 2;
	private static int SNAKE = 3;
	private int headToward = RIGHT;

	private static Color BRICK_COLOR = new Color(165, 42, 42);
	private static Color FOOD_COLOR = new Color(255, 153, 51);
	private static Color SNAKE_COLOR = new Color(102, 178, 255);
	private static Color SNAKE_HEAD_COLOR = Color.pink;
	private static Color BACKGROUND_COLOR_IN = new Color(204,255,153);
	private static Color BACKGROUND_COLOR_OUT = new Color(76,153,0);
	
	private double[][] idToCoordinate;
	private int[] idToIdentity;
	
	/**
	 * Use a list to store the blockID of empty block
	 */
	private List<Integer> emptyBlock = new ArrayList<Integer>();
	
	/**
	 * use a list to store the blokcID that represent snakeBody
	 * eat a new food: append the blockID to the end
	 * just moving, food ahead: append the ahead blockID to the end, and remove the first block in the list
	 *
	 */
	private List<Integer> snakeBody = new LinkedList<Integer>(); 
	

	public SnakeBoard(int boardSize, int snakeSize, int numFood, int numBricks) {

		this.boardSize = boardSize;
		this.snakeSize = snakeSize;
		this.numFood = numFood;
		this.numBricks = numBricks;
		this.blockwidth = 1.0 / (this.boardSize);
		this.idToCoordinate = new double[this.boardSize * this.boardSize][2];
		this.idToIdentity = new int[this.boardSize * this.boardSize];
		initSnakebody();
		initIdToCoordinate();
		initIdToIdentity();
	}

	public void initSnakebody() {
		for (int i = 0; i < this.snakeSize; i++) {
			this.snakeBody.add(i);
		}
	}

	public void initIdToCoordinate() {
		for (int r = 0; r < this.boardSize; r++) {
			for (int c = 0; c < this.boardSize; c++) {
				this.idToCoordinate[r * this.boardSize + c][0] = this.blockwidth / 2.0 + c * this.blockwidth;
				this.idToCoordinate[r * this.boardSize + c][1] = this.blockwidth / 2.0 + r * this.blockwidth;
			}

		}
	}

	public void initIdToIdentity() {
		Set brickSet = new HashSet();
		Set foodSet = new HashSet();
		while (brickSet.size() < this.numBricks) {
			int num = (int) (Math.random() * Math.pow(this.boardSize, 2));
			if (num <= this.snakeSize) {
				continue;
			}
			brickSet.add(num);
		}

		while (foodSet.size() < this.numFood) {
			int num2 = (int) (Math.random() * Math.pow(this.boardSize, 2));
			if (num2 < this.snakeSize || brickSet.contains(num2)) {
				continue;
			}
			foodSet.add(num2);
		}

		for (int i = 0; i < Math.pow(boardSize, 2); ++i) {
			if (i < this.snakeSize) {
				this.idToIdentity[i] = SNAKE;
			} else if (brickSet.contains(i)) {
				this.idToIdentity[i] = BRICK;
			}

			else if (foodSet.contains(i)) {
				this.idToIdentity[i] = FOOD;
			}

			else {
				this.idToIdentity[i] = EMPTY_BLOCK;
				this.emptyBlock.add(i);
			}

		}
	}

	public int getBoardSize() {
		return boardSize;
	}

	public double getBlockwidth() {
		return blockwidth;
	}

	public double[][] getIdToCoordinate() {
		return idToCoordinate;
	}

	public int[] getIdToIdentity() {
		return idToIdentity;
	}

	public int getHeadToward() {
		return headToward;
	}

	public static int getUP() {
		return UP;
	}

	public static int getDOWN() {
		return DOWN;
	}

	public static int getLEFT() {
		return LEFT;
	}

	public static int getRIGHT() {
		return RIGHT;
	}

	public void drawBoard() {

		StdDraw.enableDoubleBuffering();
		StdDraw.clear();
		drawBoardBackground();
		for (int idx = 0; idx < this.idToIdentity.length; ++idx) {
			if (this.idToIdentity[idx] == FOOD) {
				drawFood(this.idToCoordinate[idx][0], this.idToCoordinate[idx][1]);
			}

			else if (this.idToIdentity[idx] == BRICK) {
				drawBricks(this.idToCoordinate[idx][0], this.idToCoordinate[idx][1]);
			}

			else if (this.idToIdentity[idx] == SNAKE) {
				if (idx != this.snakeBody.get(this.snakeBody.size() - 1)) {
					drawSnakeBody(this.idToCoordinate[idx][0], this.idToCoordinate[idx][1]);
				} else {
					this.drawSnakeHead(this.idToCoordinate[idx][0], this.idToCoordinate[idx][1]);
				}
			}

		}
		drawFood(this.blockwidth, 1.05);
		StdDraw.setPenColor(Color.white);
		StdDraw.text(2*this.blockwidth, 1.05, Integer.toString(this.snakeBody.size()-this.snakeSize));
		StdDraw.show();

	}

	public void drawBricks(double x, double y) {
		StdDraw.setPenColor(BRICK_COLOR);
		StdDraw.filledSquare(x, y, this.blockwidth / 2);
		StdDraw.setPenColor(Color.black);
		StdDraw.line(x - this.blockwidth / 2., y, x + this.blockwidth / 2., y);
		StdDraw.line(x - this.blockwidth / 4., y, x - this.blockwidth / 4., y + this.blockwidth / 2.);
		StdDraw.line(x + this.blockwidth / 4., y, x + this.blockwidth / 4., y - this.blockwidth / 2.);

	}

	public void drawFood(double x, double y) {
		StdDraw.setPenColor(FOOD_COLOR);
		StdDraw.filledCircle(x, y, this.blockwidth / 2);
	}

	public void drawSnakeBody(double x, double y) {
		StdDraw.setPenColor(SNAKE_COLOR);
		StdDraw.filledCircle(x, y, this.blockwidth / 2.);
	}

	public void drawSnakeHead(double x, double y) {
		StdDraw.setPenColor(SNAKE_HEAD_COLOR);
		StdDraw.filledCircle(x, y, this.blockwidth / 2.);
		if(this.headToward == UP) {
			StdDraw.setPenColor(Color.white);
			StdDraw.filledCircle(x-this.blockwidth/4, y+this.blockwidth/4, this.blockwidth/6);  //upper left
			StdDraw.filledCircle(x+this.blockwidth/4, y+this.blockwidth/4, this.blockwidth/6);	//upper right
			StdDraw.setPenColor(Color.black);
			StdDraw.filledCircle(x-this.blockwidth/4, y+this.blockwidth/4, this.blockwidth/8);
			StdDraw.filledCircle(x+this.blockwidth/4, y+this.blockwidth/4, this.blockwidth/8);
		}
		else if(this.headToward == DOWN) {
			StdDraw.setPenColor(Color.white);
			StdDraw.filledCircle(x-this.blockwidth/4, y-this.blockwidth/4, this.blockwidth/6); //lower left
			StdDraw.filledCircle(x+this.blockwidth/4, y-this.blockwidth/4, this.blockwidth/6); //lower right
			StdDraw.setPenColor(Color.black);
			StdDraw.filledCircle(x-this.blockwidth/4, y-this.blockwidth/4, this.blockwidth/8);
			StdDraw.filledCircle(x+this.blockwidth/4, y-this.blockwidth/4, this.blockwidth/8);

		}
		
		else if(this.headToward == LEFT) {
			StdDraw.setPenColor(Color.white);
			StdDraw.filledCircle(x-this.blockwidth/4, y+this.blockwidth/4, this.blockwidth/6); //upper left
			StdDraw.filledCircle(x-this.blockwidth/4, y-this.blockwidth/4, this.blockwidth/6); //lower left
			StdDraw.setPenColor(Color.black);
			StdDraw.filledCircle(x-this.blockwidth/4, y+this.blockwidth/4, this.blockwidth/8);
			StdDraw.filledCircle(x-this.blockwidth/4, y-this.blockwidth/4, this.blockwidth/8);

		}
		else {
			StdDraw.setPenColor(Color.white);
			StdDraw.filledCircle(x+this.blockwidth/4, y+this.blockwidth/4, this.blockwidth/6);	
			StdDraw.filledCircle(x+this.blockwidth/4, y-this.blockwidth/4, this.blockwidth/6);
			StdDraw.setPenColor(Color.black);
			StdDraw.filledCircle(x+this.blockwidth/4, y+this.blockwidth/4, this.blockwidth/8);	
			StdDraw.filledCircle(x+this.blockwidth/4, y-this.blockwidth/4, this.blockwidth/8);

		}
	}
	
	
	public void drawBoardBackground() {
		StdDraw.clear(BACKGROUND_COLOR_OUT);
		StdDraw.setPenColor(BACKGROUND_COLOR_IN);
		StdDraw.filledSquare(0.5, 0.5, 0.5);
	}
	
	
	/**
	 * Use a list to store the blockID of empty block, and each time randomly generate a number between 0 - (N-1),
	 * and swap this.emptyBlock[selected_idx] with this.emptyBlock[N-1] and remove this.emptyBlock[N-1] from the list as
	 * the chosen block for food
	 * 
	 */
	public void randomGenerateFood() {
		if (this.emptyBlock.size() > 0) {
			int idx = (int) (Math.random() * this.emptyBlock.size());// random generate a idx between 0-size(empty_block)-1
			int temp = this.emptyBlock.get(idx); // temp is chosen entry
			this.idToIdentity[temp] = FOOD; // set chosen entry to food

			this.emptyBlock.set(idx, emptyBlock.get(this.emptyBlock.size() - 1));// swap the chosen entry with the last
																					// entry
			this.emptyBlock.set(this.emptyBlock.size() - 1, temp);

			this.emptyBlock.remove(this.emptyBlock.size() - 1); // remove the last entry
		}
	}

	public boolean moveRight() {
		this.headToward = RIGHT;

		int headId = this.snakeBody.get(this.snakeBody.size() - 1);
		int tailId = this.snakeBody.get(0);
		if ((headId + 1) % this.boardSize != 0 && this.idToIdentity[headId + 1] != BRICK
				&& this.idToIdentity[headId + 1] != SNAKE) { // if head is not at end and the ahead block is not brick
			this.snakeBody.add(headId + 1);
			if (this.idToIdentity[headId + 1] != FOOD) { // if forward block is not food
				this.snakeBody.remove(0);
				this.idToIdentity[tailId] = EMPTY_BLOCK;

			} else {
				randomGenerateFood(); // if forward block is food, then eat it and randomly generate a new food at
										// empty block
			}
			this.idToIdentity[headId + 1] = SNAKE;
			return true;
		}
		return false;

	}

	public boolean moveUp() {
		this.headToward = UP;

		int headId = this.snakeBody.get(this.snakeBody.size() - 1);
		int tailId = this.snakeBody.get(0);
		if ((headId / this.boardSize < this.boardSize - 1) && this.idToIdentity[headId + this.boardSize] != BRICK
				&& this.idToIdentity[headId + this.boardSize] != SNAKE) {
			this.snakeBody.add(headId + this.boardSize);
			if (this.idToIdentity[headId + this.boardSize] != FOOD) { // if up block is not food
				this.snakeBody.remove(0);
				this.idToIdentity[tailId] = EMPTY_BLOCK;
			} else {
				randomGenerateFood();
			}
			this.idToIdentity[headId + this.boardSize] = SNAKE;
			return true;
		}
		return false;

	}

	public boolean moveLeft() {
		this.headToward = LEFT;

		int headId = this.snakeBody.get(this.snakeBody.size() - 1);
		int tailId = this.snakeBody.get(0);
		if ((headId % this.boardSize != 0) && this.idToIdentity[headId - 1] != BRICK
				&& this.idToIdentity[headId - 1] != SNAKE) {
			this.snakeBody.add(headId - 1);
			if (this.idToIdentity[headId - 1] != FOOD) { // if left block is not food
				this.snakeBody.remove(0);
				this.idToIdentity[tailId] = EMPTY_BLOCK;
			} else {
				randomGenerateFood();
			}
			this.idToIdentity[headId - 1] = SNAKE;
			return true;
		}
		return false;

	}

	public boolean moveDown() {
		this.headToward = DOWN;

		int headId = this.snakeBody.get(this.snakeBody.size() - 1);
		int tailId = this.snakeBody.get(0);
		if ((headId / this.boardSize > 0) && this.idToIdentity[headId - this.boardSize] != BRICK
				&& this.idToIdentity[headId - this.boardSize] != SNAKE) {
			this.snakeBody.add(headId - this.boardSize);
			if (this.idToIdentity[headId - this.boardSize] != FOOD) { // if up block is not food
				this.snakeBody.remove(0);
				this.idToIdentity[tailId] = EMPTY_BLOCK;
			} else {
				randomGenerateFood();
			}
			this.idToIdentity[headId - this.boardSize] = SNAKE;
			return true;
		}
		return false;

	}

	public boolean keepMoving() {
		if (this.headToward == UP) {
			if (!moveUp()) {
				return false;
			}
		}

		else if (this.headToward == DOWN) {
			if (!moveDown()) {
				return false;
			}
		}

		else if (this.headToward == LEFT) {
			if (!moveLeft()) {
				return false;
			}
		}

		else {
			if (!moveRight()) {
				return false;
			}
		}

		return true;

	}

	
	public static void main(String[] args) {
		StdDraw.setXscale(-0.1, 1.1);
		StdDraw.setYscale(-0.1, 1.2);
		StdDraw.setPenColor(new Color(204,255,153));
		StdDraw.filledSquare(0.5, 0.5, 0.5);
		StdDraw.setPenColor(new Color(76,153,0));
		StdDraw.filledRectangle(-0.1/2, 0.5, 0.1/2, 0.6);  //0.1->this.blocksize
		StdDraw.filledRectangle(1+0.1/2, 0.5, 0.1/2, 0.6);
		
		StdDraw.filledRectangle(0.5, -0.1/2, 0.6, 0.1/2);
		StdDraw.filledRectangle(0.5, 1+0.1/2, 0.6, 0.1);

	}

}
