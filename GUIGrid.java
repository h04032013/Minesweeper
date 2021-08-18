package project2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//All establish GUI (swing library components)
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;

public class GUIGrid extends Grid  { //extend to call many methods and create objects
	
	
		//global variables
	    public static boolean[][] opencells; //keeps track of which cell has been clicked
	    public static int rounds = 0; //used to control new/reset board/rounds
	    public static int losses;
	    
	    public static void main(String[] args) { //main algorithm that runs game
	        runGame();
	    }
	    
	    public static void runGame() { //main algorithm runs whole game
	    	
	        Grid gameGrid = new Grid(10, 10, 30); //creates 10x10 grid with 15 bombs using Grid constructor
	        opencells = new boolean[gameGrid.getNumColumns()][gameGrid.getNumRows()];//create boolean grid
	        losses = 0;
	        //start on brand new game
	        
	        
	        JFrame frame = new JFrame(); //window for game 
	        
	        frame.setTitle("Haylin's MineSweeper");
	        frame.setSize(gameGrid.getNumColumns() * 65, gameGrid.getNumRows() * 65 + 20);
	        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	        
	        for (int l = 0; l < gameGrid.getNumRows(); l++) {    //for loop creates tiles (button) to click on
	            for (int j = 0; j < gameGrid.getNumColumns(); j++) {
	                JButton blankcell = new JButton();  //create cells as buttons
	                
	                blankcell.setBounds(l * 65, j * 65, 65, 65); //creates space between cells
	                blankcell.setVisible(true);
	                
	                frame.add(blankcell);
	                frame.setLayout(null);
	                
	                blankcell.addActionListener(new ActionListener() { //cells become clickable
	                    public void actionPerformed(ActionEvent a) {
	                    updateCell(blankcell, frame, gameGrid);    
	                    }
	                });
	            }
	        }
	        
	        for (int i = 0; i < gameGrid.getNumRows(); i++) {       //assures cell value remains hidden
	            for (int j = 0; j < gameGrid.getNumColumns(); j++) {
	                opencells[i][j] = false;
	            } }
	        
	        
	        frame.setVisible(true);
	    }
	    
	    //exposes number under cell when clicked
	    public static void showcell(JButton emptyCell, JFrame appFrame, Grid bombGrid) {
	    	
	        int numRow = emptyCell.getLocation().x / 65;
	        int column = emptyCell.getLocation().y / 65;

	       String s = Integer.toString(bombGrid.getCountAtLocation(numRow, column)); 
	        //gets count value to reveal when clicked
	        
	        opencells[numRow][column] = true; //cell has been clicked so show is now true
	        
	        JButton bvalue = new JButton(s);
	        
	        bvalue.setBounds(numRow * 65, column * 65, 65, 65); //new cell will appear with num value
	        bvalue.setText(s);
	        bvalue.setVisible(true);
	        appFrame.add(bvalue);   //adds cells to game window
	        
	        if (bombGrid.isBombAtLocation((numRow), column)) { //method for bomb clicked
	            rounds = 1;
	            losses++;
	            bvalue.setText("*boom*");   
	            bvalue.setVisible(true);
	            bvalue.setBackground(Color.orange); //tile changes to bomb tile
	           
	            for (int i = 0; i < bombGrid.getNumRows(); i++) {      //creates new bomb tile that will be shown
	                for (int j = 0; j < bombGrid.getNumColumns(); j++) {
	                    if (!opencells[i][j]) {
	                        JButton cell = new JButton();
	                        cell.setBounds(i * 65, j * 65, 65, 65);
	                        showcell(cell, appFrame, bombGrid);   //recursion; prevents repeatedly showing a cell
	                    }
	                }
	            }
	            
	            if (rounds == 1) { //refer to 87
	                rounds++;
	                
	                JFrame mainFrame = new JFrame("Game Over");
	                mainFrame.setSize(300, 265);
	               
	                int input = JOptionPane.showOptionDialog(null, "Game Over. Start Over?", "Game Over", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
	               
	                if (input == JOptionPane.CANCEL_OPTION) {
	                    appFrame.setVisible(false);
	                } if (input == JOptionPane.OK_OPTION) {
	                    appFrame.setVisible(false);
	                    runGame();
	                }
	            }
	        }
	        
	        else {
	            int numBlanks=0;
				numBlanks--;
	            int numRounds=0;
				numRounds++;
				
	            bvalue.setText(s);
	            bvalue.setBackground(Color.blue);
	            bvalue.setOpaque(true);
	            
	            if (bombGrid.getCountAtLocation(numRow, column) == 0) {
	                zero(numRow, column, bombGrid, appFrame);
	            }
	            if (numRounds == bombGrid.getNumColumns() * bombGrid.getNumRows() - bombGrid.getNumBombs() && losses == 0) {
	                JFrame mainFrame = new JFrame("Game Over");
	                mainFrame.setSize(400,400);
	                
	                int input = JOptionPane.showOptionDialog(null, "Winner! Start Over?", "Game Over", JOptionPane.OK_CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
	                if (input == JOptionPane.OK_OPTION) {
	                    appFrame.setVisible(false);
	                    runGame();
	                }
	            }
	        }
	        bvalue.setVisible(true);
	    }
	    
	    //
	    public static void updateCell(JButton emptyCell, JFrame appFrame, Grid bombGrid) {
	   
	        JButton bomb = new JButton();
	        
	        bomb.setBounds(emptyCell.getLocation().x, emptyCell.getLocation().y, 65, 65);
	        bomb.setVisible(true);
	        appFrame.add(bomb);
	        emptyCell.setVisible(false);
	        
	        int numBombs=0;
			numBombs++; 
			
	        bomb.addActionListener(new ActionListener() { //uses actionListener to show cell value when clicked
	            public void actionPerformed(ActionEvent e) {
	                bomb.setVisible(false);
	                showcell(emptyCell, appFrame, bombGrid);
	            }
	        }) ;
	    } 
	    
	  //what will happen if user presses on a 0
	    public static void zero(int row, int column, Grid bombGrid, JFrame appFrame) {
	        for (int i = -1; i < 2; i++) {
	            for (int j = -1; j < 2; j++) {
	                if (!(i == 0 && j == 0) && row + i >= 0 && row + i < bombGrid.getNumRows() && column + j >= 0 && column + j < bombGrid.getNumColumns() && !opencells[row + i][column + j]) { //if not showing, show 0 cells 
	                	
	                    JButton ZButton = new JButton(Integer.toString(bombGrid.getCountAtLocation(row + i, column + j)));
	                    ZButton.setBounds((row + i) * 65, (column + j) * 65, 65, 65);
	                    
	                    showcell(ZButton, appFrame, bombGrid);
	                }
	            }
	        }
	    }
	    
	
}

