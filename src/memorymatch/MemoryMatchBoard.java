package memorymatch;

import java.awt.Color;

public class MemoryMatchBoard {
	/**
	 * @return an array of 8 discernible colors
	 */
	public static Color[] getPalette() {
		// http://mkweb.bcgsc.ca/colorblind/img/colorblindness.palettes.v11.pdf
		Color[] palette = new Color[] {
				new Color(0, 0, 0),
				new Color(34, 113, 178),
				new Color(61, 183, 233),
				new Color(247, 72, 165),
				new Color(53, 155, 115),
				new Color(213, 94, 0),
				new Color(230, 159, 0),
				new Color(240, 228, 66)
		};
		return palette;
	}

	/**
	 * Swaps the colors located at board[aRow][aCol] and board[bRow][bCol].
	 * 
	 * Note: this mutates (changes) the contents of the specified board.
	 * 
	 * @param board the 2D array of colors
	 */
	public static void swapCells(Color[][] board, int aRow, int aCol, int bRow, int bCol) {
		
			Color tempCell = board[aRow][aCol];
			board[aRow][aCol] = board[bRow][bCol];
			board[bRow][bCol] = tempCell;
			
	}

	

	/**
	 * Rearranges the colors in the specified board until they are in a reasonably
	 * random arrangement.
	 * 
	 * Note: this mutates (changes) the contents of the specified board.
	 * 
	 * @param board the 2D array of colors to shuffle
	 */
	public static void shuffle(Color[][] board) {
		int numRows = board.length;
		int numCols = board[0].length;
		for(int r=0; r<numRows; ++r) {
			shuffleArray(board[r]);
		}
		for(int i = numRows-1; i>=0;i--) {
			int c = (int)(Math.random()*(i+1));
			Color[] tempArray = board[c];
			board[c] = board[i];
			board[i] = tempArray;
		}
	
		}
			// TODO
		
	public static void shuffleArray(Color[] Array) {
		for (int i=Array.length-1;i>=0;i--) {
			int c = (int)(Math.random()*(i+1));
			Color temp = Array[c];
			Array[c] = Array[i];
			Array[i] = temp;
		}
	}

	/**
	 * @param palette an array of 8 Colors
	 * @return shuffled 4x4 2D array of Colors suitable for a memory match game
	 */
	public static Color[][] generateShuffled4x4Board(Color[] palette) {
		
		Color[][] shuffled4x4Board = new Color[4][4];
		int paletteIdx =0; //fill the empty board with colors in palette one by one
		for(int i=0;i<shuffled4x4Board.length;++i) {
			for(int j=0;j<shuffled4x4Board[0].length;++j) {
				if(paletteIdx>=palette.length) {
					paletteIdx = paletteIdx-palette.length;
				}
				shuffled4x4Board[i][j] = palette[paletteIdx];
				paletteIdx +=1;
			}
		}
		shuffle(shuffled4x4Board);
		return shuffled4x4Board;
		
		
	}
}
