/**
 * Class creates a pacman GUI that extends the JFrame class
 */

import javax.swing.*;

@SuppressWarnings("Serial")

public class PacManGUI extends JFrame{
	
	private Board board = new Board();
	
	public PacManGUI() {
		
		setSize(600, 600);
		setTitle("PacMan");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		addKeyListener(board);
		add(board);
		
		setVisible(true);
	}

}
