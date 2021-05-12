package Tetris;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class PlayTetris {
	
	private static int UP = 0;
	private static int DOWN = 1;
	private static int LEFT = 2;
	private static int RIGHT = 3;
	private static int NO_RESPONSE = 4;

	
	public static void setupDrawing() {
		StdDraw.setCanvasSize(360, 720);
		StdDraw.setXscale(0, 0.5);
		StdDraw.setYscale(0, 1.1);
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
	
	public static void playGame() {
		while(true) {
		setupDrawing();
		TetrisBoard tetris= new TetrisBoard();
		tetris.drawBoard();
		StdDraw.pause(500);
		
		while(true) {
			if (!tetris.isAlive()) {
				break;
			}
			tetris.drawBoard();
			StdDraw.pause(50);
			int keyCode = waitForArrowKey(200);
			if (keyCode == LEFT) {
				tetris.moveLeft();
				}
			else if(keyCode == RIGHT) {
				tetris.moveRight();

			}
			else if(keyCode == UP) {
				tetris.rotate();
			
		}
			else if(keyCode == DOWN) {
				tetris.moveDown();
				tetris.moveDown();
				tetris.moveDown();
			}
			else {
				tetris.moveDown();
			}

	}	

		StdDraw.setPenColor(Color.white);
		StdDraw.text(0.25, 1.03, "Game over, click to restart ");
		StdDraw.show();
		while(true) {
			if(StdDraw.isMousePressed()) {
				break;
			}
		}
		}
	}
	
	public static void main(String[] args) {
		playGame();
}
	}
