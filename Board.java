/*
 * Class represents the game board and includes methods to handle keyboard events and game actions
 */

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import javax.swing.*;

@SuppressWarnings("Serial")

public class Board extends JPanel implements KeyListener, ActionListener {
	
	// timer for game movement
	private Timer gameTimer = new Timer(250, this);
	
	// timer for pacman movement
	private Timer animateTimer = new Timer(50, this);
	
	// imageIcon constant for wall
	private static final ImageIcon WALL = new ImageIcon("images/StdWall.bmp");
	
	// imageIcon constant for food
	private static final ImageIcon FOOD = new ImageIcon("images/StdFood.bmp");
	
	// imageIcon constant for blank
	private static final ImageIcon BLANK = new ImageIcon("images/StdBlank.bmp");
	
	// imageIcon constant for door
	private static final ImageIcon DOOR = new ImageIcon("images/StdDoor.bmp");
	
	// imageIcon constant for skull
	private static final ImageIcon SKULL = new ImageIcon("images/StdSkull.bmp");
		
	// array to hold the game board characters from the text file
	private char[][] maze = new char[25][27];
	
	// array to hold the game board images
	private JLabel[][] cell = new JLabel[25][27];
	
	// pacman object
	private PacMan pacMan;
	
	// array of Ghost objects
	private Ghost[] ghost = new Ghost[3];
	
	// track amount of food on the board
	private int pellets = 0;
	
	// track game score (1pt per food item eaten)
	private int score=0;
	
	// steps for animating pacman's chomp
	private int pStep;
	
	// Construct the game board including the layout, background, pacman and ghosts and calls the loadboard method
	public Board() {
		
		setLayout(new GridLayout(25,27));
		setBackground(Color.BLACK);
		
		pacMan = new PacMan();
		
		ghost[0] = new Ghost(0);
		ghost[1] = new Ghost(1);
		ghost[2] = new Ghost(2);
		
		loadBoard();

	}

// loads the maze onto the screen from a text file
private void loadBoard() {
	
	int r = 0;
	
	Scanner input;
	
	try {
		
		input = new Scanner(new File("maze.txt"));
		
		while(input.hasNext()) {
			
			maze[r] = input.nextLine().toCharArray();
			
			for(int c = 0; c < maze[r].length; c++) {
				
				cell[r][c]=new JLabel();
				
				// depending on the symbol in the maze file assign the appropriate picture
				// Note: this only has to be done for squares that are not black since the background is black
				
				if(maze[r][c] == 'W')
					cell[r][c].setIcon(WALL);
					
				else if(maze[r][c] == 'F') {
					cell[r][c].setIcon(FOOD);
					pellets++; 		// keep track of the amount of food
						
				}
				
				else if(maze[r][c] == 'P') {
					cell[r][c].setIcon(pacMan.getIcon());
					pacMan.setRow(r);
					pacMan.setColumn(c);
					pacMan.setDirection(0); 	// start left
					
				}
				
				else if(maze[r][c] == '0'||(maze[r][c] == '1'||(maze[r][c] == '2'))) {
					
					// int gNum = Character.getNumericalValue(maze[r][c]);
					int gNum = (int)(maze[r][c])-48;
					
					cell[r][c].setIcon(ghost[gNum].getIcon());
					ghost[gNum].setRow(r);
					ghost[gNum].setColumn(c);
					
				}
				
				else if (maze[r][c] == 'D') {
					cell[r][c].setIcon(DOOR);
					
				}
				
				add(cell[r][c]);
				
			}
			
			r++;
			
		}
		
		input.close();
		
	} catch (FileNotFoundException e) {
		
		System.out.println("File not found");
		
	}
	
}

// Handles keyboard entries - to start the game and control pacman's movements
public void keyPressed(KeyEvent key) {
	
	if (gameTimer.isRunning() == false && pacMan.isDead() == false)
		gameTimer.restart();
	
	if (pacMan.isDead() == false && score != pellets) {
		
		int direction = key.getKeyCode()-37;
		
		if (direction == 0 && maze[pacMan.getRow()][pacMan.getColumn()-1] != 'W')
			pacMan.setDirection(0);
		else if (direction == 1 && maze[pacMan.getRow()-1][pacMan.getColumn()] != 'W')
			pacMan.setDirection(1);
		else if (direction == 2 && maze[pacMan.getRow()][pacMan.getColumn()+1] != 'W')
			pacMan.setDirection(2);
		else if (direction == 3 && maze[pacMan.getRow()+1][pacMan.getColumn()] != 'W')
			pacMan.setDirection(3);
		
	}

}

// mandatory method to implement KeyListener interface
public void keyReleased(KeyEvent key) {
	// not used, but must remain in order to run
}

public void keyTyped(KeyEvent key) {
	// not used, but must remain in order to run
}

/**
 * Allows objects to move and updates both on the maze and screen based on:
 * the object, direction, adn change in row and column
 * 
 * @param mover either PacMan or a Ghost
 */
private void performMove(Mover mover) {
	
	if (mover.getColumn() == 1) {
		mover.setColumn(24);
	} else if (mover.getColumn() == 25) {
		mover.setColumn(2);
		cell[12][25].setIcon(DOOR);
	}
	
	if (maze[mover.getNextRow()][mover.getNextColumn()] != 'W') {
		
		if (mover == pacMan)
			animateTimer.start();
		
		else {
			
			if (maze[mover.getRow()][mover.getColumn()] == 'F')
				cell[mover.getRow()][mover.getColumn()].setIcon(FOOD);
			
			else
				cell[mover.getRow()][mover.getColumn()].setIcon(BLANK);
			
			mover.move();
			
			if (collided())
				death();
			
			else
				cell[mover.getRow()][mover.getColumn()].setIcon(mover.getIcon());
		}
		
	}
	
}

/**
 * Determine if PacMan has collided with a Ghost
 * 
 * @return collided
 */
private boolean collided() {
	
	// for (Ghost g: ghost) {
	for (int g = 0; g < 3; g++) {
		if (ghost[g].getRow() == pacMan.getRow() && ghost[g].getColumn() == pacMan.getColumn())
			return true;
		
	}
	return false;
	
}

/**
 * Stop the game when PacMan and a Ghost 'collide'
 */
private void death() {
	
	pacMan.setDead(true);
	
	stopGame();
	
	cell[pacMan.getRow()][pacMan.getColumn()].setIcon(SKULL);
	
}

/**
 * Stops the game timer
 */
private void stopGame() {
	
	if (pacMan.isDead() || score == pellets) {
		animateTimer.stop();
		gameTimer.stop();
	}
}

/**
 * Moves the ghosts in a random pattern
 */
private void moveGhosts() {
	
	for (Ghost g: ghost) {
		
		int dir=0;
		
		do {
			dir = (int)(Math.random()*4);
		} while (Math.abs(g.getDirection() - dir) == 2);
		
		g.setDirection(dir);
		
		performMove(g);
		
	}
}

/** 
 * Determines the source of the action as either the game timer or the animation timer and then performs the corresponding actions
 */
public void actionPerformed(ActionEvent e) {
	
	if (e.getSource() == gameTimer) {
		
		performMove(pacMan);
		moveGhosts();
		
	} else if (e.getSource() == animateTimer) {
		
		animatePacMan();
		
		pStep++;
		
		if (pStep == 3)
			pStep = 0;
	}
}

/**
* Animates PacMan in 3 steps: open mouth, draw black square, move and close mouth
*/
private void animatePacMan() {
	
	if (pStep == 0) {
		
		cell[pacMan.getRow()][pacMan.getColumn()].setIcon
			(PacMan.IMAGE[pacMan.getDirection()][1]);
		
		animateTimer.setDelay(100);
		
	}
	else if (pStep == 1)
		
		cell[pacMan.getRow()][pacMan.getColumn()].setIcon(BLANK);
	
	else if (pStep == 2) {
		
		pacMan.move();
		
		if (maze[pacMan.getRow()][pacMan.getColumn()] == 'F') {
			
			score++;
			
			maze[pacMan.getRow()][pacMan.getColumn()] = 'E';	
		}
		
		animateTimer.stop();
		
		if (pacMan.isDead())
			cell[pacMan.getRow()][pacMan.getColumn()].setIcon(SKULL);
		
		else
			cell[pacMan.getRow()][pacMan.getColumn()].setIcon(PacMan.IMAGE[pacMan.getDirection()][0]);	
	}
}
}