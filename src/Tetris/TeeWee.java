package Tetris;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.introcs.StdDraw;

public class TeeWee implements MovingBlock{
	
	private int boardCols = 10;
	private int[][] teeweeSequence;
	private int[] curTeeWeeForm;
	private int initLeftmostID;
	private int curFormID = 0;
	private Color blockColor;
	
	
	
	public TeeWee(int initLeftmostID, Color blockColor) {
		this.initLeftmostID = initLeftmostID;
		this.blockColor = blockColor;
		initTeeWeeSequence();
		this.curTeeWeeForm = this.teeweeSequence[this.curFormID];		
	}
	
	
	
	public int[] getCurForm() {
		return this.curTeeWeeForm;
	}
	

	public Color getBlockColor() {
		return this.blockColor;
	}


	public void initTeeWeeSequence() {
		int[][] sequence = {{initLeftmostID,initLeftmostID+1,initLeftmostID+2,initLeftmostID+1+boardCols},
				{initLeftmostID+1,initLeftmostID+1+boardCols,initLeftmostID+1-boardCols,initLeftmostID+2},
				{initLeftmostID,initLeftmostID+1,initLeftmostID+2,initLeftmostID+1-boardCols},
				{initLeftmostID+1,initLeftmostID+1+boardCols,initLeftmostID+1-boardCols,initLeftmostID}};
		
		this.teeweeSequence = sequence;	
		
	}
	
	
	/**
	 * 
	 * @param offset: how much to move this block, left is negative integer, right is positive integer
	 */
	public void setBlockSequence(int offset) {
		for (int i=0;i<4;++i) {
			for( int j=0; j<4;++j) {
				this.teeweeSequence[i][j] += offset;
			}
		}
		this.curTeeWeeForm = this.teeweeSequence[this.curFormID];
	}
	
	public void rotate() {
		this.curFormID = (this.curFormID + 1) % 4;
		this.curTeeWeeForm = this.teeweeSequence[this.curFormID];
	}
	

}
