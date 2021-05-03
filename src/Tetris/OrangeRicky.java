package Tetris;

import java.awt.Color;

public class OrangeRicky implements MovingBlock{
	
	private int boardCols = 10;
	private int[][] sequence;
	private int[] curForm;
	private int initLeftmostID;
	private int curFormID = 0;
	private Color blockColor;

	
	
	public OrangeRicky(int initLeftmostID, Color blockColor) {
		this.initLeftmostID = initLeftmostID;
		this.blockColor = blockColor;
		initRickySequence();
		this.curForm = this.sequence[this.curFormID];
	}
	
	
	
	public void initRickySequence() {
		int[][] sequence = {{initLeftmostID,initLeftmostID-boardCols,initLeftmostID-boardCols+1,initLeftmostID-boardCols+2},
				{initLeftmostID+boardCols+1,initLeftmostID+boardCols,initLeftmostID,initLeftmostID-boardCols},
				{initLeftmostID+boardCols,initLeftmostID+boardCols+1,initLeftmostID+boardCols+2,initLeftmostID+2},
				{initLeftmostID+boardCols+2,initLeftmostID+2,initLeftmostID-boardCols+2,initLeftmostID-boardCols+1}
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
		for (int i=0;i<4;++i) {
			for( int j=0; j<4;++j) {
				this.sequence[i][j] += offset;
			}
		}
		this.curForm = this.sequence[this.curFormID];
	}
	
	public void rotate() {
		this.curFormID = (this.curFormID + 1) % 4;
		this.curForm = this.sequence[this.curFormID];
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
