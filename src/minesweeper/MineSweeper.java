package minesweeper;

////////////////////////////////////////////////////////////////////////
// support.cse131 codes are provided by washU cse131 class instructor///
////////////////////////////////////////////////////////////////////////

import support.cse131.ArgsProcessor;

public class MineSweeper {
	public static void main(String[] args) {

		ArgsProcessor ap = new ArgsProcessor(args);
		int cols = ap.nextInt("How many columns?");
		int rows = ap.nextInt("How many rows?");
		double probability = ap.nextDouble("What is the probability of a mine?");
		boolean[][] mineField = new boolean[rows+2][cols+2];
		for(int r=1; r<=rows;++r) { //generate mineField based on input probability
			for(int c=1; c<=cols;++c) {
				mineField[r][c] = Math.random() < probability;
			}
		}
		
		for(int r=1; r<=rows;++r) { //print out the mine left mineField board
			for(int c=1; c<=cols; ++c) {
				if(mineField[r][c]==true) {
					System.out.print("* ");
				}
				else {
					System.out.print(". ");
				}
			}
			System.out.print("\t");
			for(int j=1;j<=cols;++j) { 
				if(mineField[r][j]==true) {
					System.out.print("* ");
				}
				else {
					int count=0;
					for(int i=r-1;i<=r+1;++i) {
						for(int k=j-1;k<=j+1;++k) {
							if(mineField[i][k]==true) {
								count = count+1;
							}
						}
					}
					System.out.print(count+" ");
				}
			}
			System.out.println();
			
		}
		
	}
}
