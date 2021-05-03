package Tetris;

import java.awt.Color;

public interface MovingBlock {
	public int[] getCurForm();
	public Color getBlockColor();
	public void setBlockSequence(int offset);
	public void rotate();
	}

