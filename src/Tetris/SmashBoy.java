package Tetris;

import java.awt.Color;

public class SmashBoy implements MovingBlock {
	private int boardCols = 10;
	private int[][] sequence;
	private int[] curForm;
	private int initLeftmostID;
	private int curFormID = 0;
	private Color blockColor;
	
	public SmashBoy(int initLeftmostID, Color blockColor) {
		this.initLeftmostID = initLeftmostID;
		this.blockColor = blockColor;
		initSequence();
		this.curForm = this.sequence[this.curFormID];
	}
	
	
	public void initSequence() {
		int[][] sequence = {{initLeftmostID,initLeftmostID+1,initLeftmostID-boardCols,initLeftmostID-boardCols+1}	
		};
		
		this.sequence = sequence;
		
	}
	
	public int[] getCurForm() {
		return curForm;
	}



	public Color getBlockColor() {
		return blockColor;
	}


	/**
	 * 
	 * @param offset: how much to move this block, left is negative integer, right is positive integer
	 */
	public void setBlockSequence(int offset) {
		for (int i=0;i<1;++i) {
			for( int j=0; j<4;++j) {
				this.sequence[i][j] += offset;
			}
		}
		this.curForm = this.sequence[this.curFormID];
	}
	
	public void rotate() {
		this.curFormID = 0;
		this.curForm = this.sequence[this.curFormID];
	}
	
}
