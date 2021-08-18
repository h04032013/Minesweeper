package project2;

import java.util.Random;//used to generate random bombs


//GUI class is separate, this class is for grading/back end

public class Grid {

	private boolean [][] bombGrid;
	private int [][] countGrid;
	private int numRows;
	private int numColumns;
	private int numBombs;
	
	public Grid() {
		this.numRows = 10; //default value used to create grid dimensions
		this.numColumns = 10;
		this.countGrid = new int [10][10]; //initialize 2d grid array 
		this.numBombs = 25; //25 default bombs
		createBombGrid();
		createCountGrid();
		
	}
	
	public Grid(int rows, int columns, int bombs) {
		this.numRows = rows; //parameter value used to create grid dimensions
		this.numColumns = columns;
		this.countGrid = new int [rows][columns]; //initialize 2d grid array 
		this.numBombs = bombs; //player given num of bombs
		createBombGrid();
		createCountGrid();
	}
	
	public int getNumRows () {
		return this.numRows;
	}
	
	public int getNumColumns() {
		return this.numColumns;
	}
	
	public int getNumBombs() {
		return this.numBombs;
	}
	
	public boolean[][] getBombGrid(){
		boolean [][]gridcopy = new boolean [this.numRows][this.numColumns];
		//create new boolean grid to be copied and returned
		
		for (int i =0; i<this.bombGrid.length;i++) { //loop to copy elements from original
			for(int j=0;j<this.bombGrid[i].length;j++) {
				gridcopy[i][j]=this.bombGrid[i][j];   //double loop for 2d arrays
			}
		}
		
		return gridcopy;
	}
	
	public int [][] getCountGrid(){
		int [][] copycount= new int[this.numRows][this.numColumns];
		//new array copy to return from original
		
		for (int i =0; i<this.countGrid.length;i++) { //loop to copy elements from original
			for(int j=0;j<this.countGrid[i].length;j++) {
				copycount[i][j]=this.countGrid[i][j];   //double loop for 2d arrays
			}
		}
		return copycount;
	}
	
	public boolean isBombAtLocation(int nrow, int ncolumn) {
		return bombGrid[nrow][ncolumn];
	}
	
	public int getCountAtLocation(int nrow, int ncolumn) {
		int count = 0;
		
		for (int a = nrow-1; a <= nrow +1;a++) { //location will be based on param values
			for (int k = ncolumn-1; k<= ncolumn+1;k++) {
				if (isValid(a,k) && bombGrid[a][k]) {   //if location is valid and bomb is adj. 
					count++; // number on tile will increase
				}
			}
		}
		return count; //returns val based on adj. bombs and params/indeces
	}
	
	
	//this method is to assure index is not out of bounds
	public boolean isValid(int l, int k) {
		if ((l>=0 && l<numRows) && (k>=0 && k<numColumns)) {
			return true;
		} else return false;
	}
	
	
	
	private void createBombGrid() {
		bombGrid = new boolean[this.numRows][this.numColumns];
		for (int i =0;i<bombGrid.length;i++) {
			for (int j =0;j<bombGrid.length;j++) {
				bombGrid[i][j]=false;
				//Initially create false/empty boolean grid (2d) of bombs
			}
		}
		
		Random ran = new Random(); //used to generate random bombs;populate grid
		
		int bombs = 0; //keeps track of bombs generated
		
		while (!(bombs==numBombs)) {       //until bombs made matches numBombs
			int rown = ran.nextInt(numRows);   //generate random position for bomb
			int coln = ran.nextInt(numColumns);
			boolean isbomb = bombGrid[rown][coln]; //bomb boolean value;status
			
			if (!isbomb) {
				bombGrid[rown][coln]=true;
				bombs++;     
			}
			
		}
	}
	
	private void createCountGrid() {
		countGrid = new int [numRows][numColumns]; //initials blank grid
		for (int i =0;i<numRows;i++) {
			for(int j =0;j<numColumns;j++) {
				if (countGrid[i][j]!=-1) {
					countGrid[i][j]=getCountAtLocation(i,j);
					//getCount() already calculate tile num
				}
			}
		}
		
	}
	
	
	
	
	
	
	
	
	
}


