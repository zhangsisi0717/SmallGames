package snake;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.event.KeyEvent;
import support.cse131.ArgsProcessor;

public class PlaySnake {

	private static int UP = 0;
	private static int DOWN = 1;
	private static int LEFT = 2;
	private static int RIGHT = 3;
	private static int NO_RESPONSE = 4;

	public static void main(String[] args) {
		ArgsProcessor ap = new ArgsProcessor(args);
		int boardSize = ap.nextInt("Please input size of the board (N X N)");
		int snakeSize = ap.nextInt("Please input initial size of the snake");
		int numFood = ap.nextInt("How many food?");
		int numBricks = ap.nextInt("How many bricks");
		while (true) {
			if (numFood + numBricks < boardSize * boardSize) {
				break;
			} else {
				numFood = ap.nextInt("How many food? (sum of food and bricks should be smaller than NXN");
				numBricks = ap.nextInt("How many brick? (sum of food and bricks should be smaller than NXN");
			}
		}
		
		StdDraw.setXscale(-0.1, 1.1);
		StdDraw.setYscale(-0.1, 1.1);

		while (true) {
			playGame(boardSize, snakeSize, numFood, numBricks);
			StdDraw.setPenColor(Color.white);
			StdDraw.text(0.5, 1.05, "Game over, click to restart ");
			StdDraw.show();
			while (!StdDraw.isMousePressed()) {
				StdDraw.pause(10);
			}
		}

	}

	public static int waitForArrowKey(int timePaused) {
		boolean keyPressed = false;
		int timeDelta = 5;
		int keyCode = NO_RESPONSE;

		for (int i = 0; i < (timePaused / timeDelta); i++) {
			if (!keyPressed) {
				if (StdDraw.isKeyPressed(KeyEvent.VK_LEFT)) {
					keyPressed = true;
					keyCode = LEFT;
				} else if (StdDraw.isKeyPressed(KeyEvent.VK_RIGHT)) {
					keyPressed = true;
					keyCode = RIGHT;
				} else if (StdDraw.isKeyPressed(KeyEvent.VK_UP)) {
					keyPressed = true;
					keyCode = UP;
				} else if (StdDraw.isKeyPressed(KeyEvent.VK_DOWN)) {
					keyPressed = true;
					keyCode = DOWN;
				}
			}
			StdDraw.pause(timeDelta);
		}
		return keyCode;
	}

	public static void playGame(int boardSize, int snakeSize, int numFood, int numfBrick) {
		SnakeBoard snakeBoard = new SnakeBoard(boardSize, snakeSize, numFood, numfBrick); //initialize game board
		snakeBoard.drawBoard();
		StdDraw.pause(500);

		while (true) {
			snakeBoard.drawBoard();

			int keyCode = waitForArrowKey(200); //wait for user to respond
			
			
			if (keyCode == LEFT && snakeBoard.getHeadToward() != RIGHT) {
				if (!snakeBoard.moveLeft()) {
					break;
				}

			} else if (keyCode == RIGHT && snakeBoard.getHeadToward() != LEFT) {
				if (!snakeBoard.moveRight()) {
					break;
				}

			} else if (keyCode == UP && snakeBoard.getHeadToward() != DOWN) {
				if (!snakeBoard.moveUp()) {
					break;
				}

			} else if (keyCode == DOWN && snakeBoard.getHeadToward() != UP) {
				if (!snakeBoard.moveDown()) {
					break;
				}

			} else {
				if (!snakeBoard.keepMoving()) {
					break;
				}
			}
		}

	}
}
