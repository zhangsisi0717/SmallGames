package Tetris;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.introcs.StdDraw;

public class TetrisBoard {
	
	
	private double blockwidth;	
	private int numColumns = 10;
	private int numRows = 20;
	private double[][] idToCoordinate = new double[numColumns*numRows][2];
	private List<Color> idToColor = new ArrayList<Color>();
	private List<Integer> idToIdentity = new ArrayList<Integer>();
	private List<Integer> fixedBlockId = new ArrayList<Integer>();
	private MovingBlock movingBlock;
	
	private static int UP = 0;
	private static int DOWN = 1;
	private static int LEFT = 2;
	private static int RIGHT = 3;
	private static int EMPTY = 0;
	private static int BRICK = 1;
	private static int X = 0;
	private static int Y = 1;
	private static Color[] colorPalette;
	private static Color BACKGROUND_COLOR = new Color(204,229,255);
	private static Color LINE_COLOR = new Color(255,255,255);


	
	
	public TetrisBoard() {
		this.blockwidth = 0.5 / this.numColumns;
		initTetrisBoard();
	}
	
	
	public void initTetrisBoard() {
		initIdToCoordinate();
		initIdToColor();
		initIdToIdentity();
		initColorPalette();
		initMovingBlock();	
	}
	
	public void initColorPalette() {
		Color[] p ={new Color(255,102,102),new Color(255,178,102),new Color(255,255,102),
				new Color(102,255,178),new Color(153,255,255),new Color(102,178,255),
				new Color(153,153,255),new Color(255,153,204)};
		this.colorPalette = p;
	}
		
	
	public void initIdToCoordinate() {
//		int num = 0;
		for (int r = 0; r < numRows; r++) {
			for (int c = 0; c < numColumns; c++) {
				this.idToCoordinate[r * numColumns + c][0] = this.blockwidth / 2.0 + c * this.blockwidth;
				this.idToCoordinate[r * numColumns + c][1] = this.blockwidth / 2.0 + r * this.blockwidth;
//				String message = String.format("(%d, %d) = (%f,%f)", r,c,this.blockwidth / 2.0 + c * this.blockwidth, this.blockwidth / 2.0 + r * this.blockwidth);
//				System.out.println(num);
//				System.out.println(message);
//				num +=1;
			}

		}
	}
	
	
	public void initIdToColor(){
		for (int i=0; i<numColumns*numRows; ++i) {
//			this.idToColor.set(i, BACKGROUND_COLOR);
			this.idToColor.add(BACKGROUND_COLOR);
		}
	}
	
	
	public void initIdToIdentity(){
		for (int i=0; i<numColumns*numRows; ++i) {
//			idToIdentity[i] = EMPTY;
			this.idToIdentity.add(EMPTY);
		}
	}
	
	public void initMovingBlock() {
		Color newColor = this.colorPalette[(int) (Math.random()* this.colorPalette.length)];
		this.movingBlock = new TeeWee(183,newColor);
	}
	
	public void drawBoard() {
		StdDraw.enableDoubleBuffering();
		StdDraw.clear();
		drawbackground();
		drawFixedBlocks();
		drawMovingBlock();
		StdDraw.show();
	}
	
	public void drawbackground(){
		StdDraw.setPenColor(BACKGROUND_COLOR);
		StdDraw.filledRectangle(0.25, 0.5, 0.25, 0.5);
		for (int i=0; i<this.idToCoordinate.length;++i) {
			StdDraw.setPenColor(LINE_COLOR);
			StdDraw.square(idToCoordinate[i][X], idToCoordinate[i][Y], blockwidth/2);
		}
	}
	
	public void drawFixedBlocks() {
		for(Integer id: this.fixedBlockId) {
			StdDraw.setPenColor(this.idToColor.get(id));
			StdDraw.filledSquare(idToCoordinate[id][X], idToCoordinate[id][Y], blockwidth/2);
			
			StdDraw.setPenColor(Color.black);
			StdDraw.setPenRadius(0.005);
			StdDraw.square(idToCoordinate[id][X], idToCoordinate[id][Y], blockwidth/2);
			
		}
		
	}
	
	public void drawMovingBlock() {
		for(int id:movingBlock.getCurForm()) {
			StdDraw.setPenColor(movingBlock.getBlockColor());
			StdDraw.filledSquare(idToCoordinate[id][X], idToCoordinate[id][Y],blockwidth/2);
			StdDraw.setPenColor(Color.black);
			StdDraw.setPenRadius(0.005);
			StdDraw.square(idToCoordinate[id][X], idToCoordinate[id][Y],blockwidth/2);
		}
	}
	
	
	public void moveLeft() {
		boolean couldMove = true;
		for(int blockId : movingBlock.getCurForm()) {
			if(blockId % numColumns ==0 || idToIdentity.get(blockId-1) == BRICK) {
				couldMove = false;
				break;	
			}
		}
		if(couldMove) {
			movingBlock.setBlockSequence(-1);//move one unit left
		}
	}
	
	
	public void moveRight() {
		boolean couldMove = true;
		for(int blockId : movingBlock.getCurForm()) {
			if((blockId+1) % numColumns ==0 || idToIdentity.get(blockId+1) == BRICK) {
				couldMove = false;
				break;	
			}
		}
		if(couldMove) {
			movingBlock.setBlockSequence(1);//move one unit left
		}
	}
	
	
	public void moveDown() {
		boolean couldMove = true;
		for(int blockId : movingBlock.getCurForm()) {
			if(blockId < numColumns || idToIdentity.get(blockId - numColumns)==BRICK) {
				couldMove = false;
				fixMovingBlock();
				tryEliminate();
				break;	
			}
		}
		if(couldMove) {
			this.movingBlock.setBlockSequence((-1)*numColumns);//move one unit left
		}
	}
	
	public void rotate() {
		boolean couldRotate = true;
		for(int blockId : movingBlock.getCurForm()) { 
			if(blockId < numColumns || idToIdentity.get(blockId - numColumns)==BRICK) { //if at the bottom 
				couldRotate = false;
				break;	
			}
		}
		if(couldRotate) {
			this.movingBlock.rotate();
		}
	}
	
	public void fixMovingBlock() {
		for(int i: this.movingBlock.getCurForm()) {
			this.fixedBlockId.add(i);
			this.idToColor.set(i, this.movingBlock.getBlockColor());
			this.idToIdentity.set(i, BRICK);
		}
		updateMovingBlock();
	}
	
	public void updateMovingBlock() { ///not finished here
		Color newColor = this.colorPalette[(int) (Math.random()* this.colorPalette.length)];
		this.movingBlock = new TeeWee(183,newColor);

	}
	
	public void tryEliminate() {
		List<Color> newIdToColor = new ArrayList<Color>();
		List<Integer> newIdToIdentity = new ArrayList<Integer>();
		List<Integer> newfixedBlockId = new ArrayList<Integer>();
		for (int r = 0; r < numRows; r++) {
			List<Color> tempColor = new ArrayList<Color>();
			List<Integer> tempIdentity = new ArrayList<Integer>();
			boolean needEliminated = true;
			for (int c = 0; c < numColumns; c++) {
				int curId = r * numColumns + c;
				tempColor.add(this.idToColor.get(curId));
				tempIdentity.add(this.idToIdentity.get(curId));
				if (this.idToIdentity.get(curId) == this.EMPTY){
					needEliminated = false;
				}
			}
			
			if(!needEliminated) {
				newIdToColor.addAll(tempColor);
				newIdToIdentity.addAll(tempIdentity);
			}
	}
		for(int idx=0; idx<this.numColumns*this.numRows;++idx) {
			if(idx<newIdToIdentity.size() && newIdToIdentity.get(idx) == BRICK) {
				newfixedBlockId.add(idx);
			}
			else if(idx>=newIdToIdentity.size()) {
				newIdToIdentity.add(this.EMPTY);
				newIdToColor.add(BACKGROUND_COLOR);
			}
		}
		
		
		this.idToColor = newIdToColor;
		this.idToIdentity = newIdToIdentity;
		this.fixedBlockId = newfixedBlockId;
	}
	
	public boolean isAlive() {
		for (Integer i: this.fixedBlockId) {
			if(i+this.numColumns>this.numColumns*this.numRows-1) {
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
	}
}
