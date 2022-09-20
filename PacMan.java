import javax.swing.ImageIcon;

@SuppressWarnings("Serial")

public class PacMan extends Mover {
	
	/**
	 * creates an array of ImageIcons representing various states of PacMan
	 */
	public static final ImageIcon[][] IMAGE = {
			
			{ new ImageIcon("images/PacLeftClosed.bmp"), new ImageIcon("images/PacLeftOpen.bmp") },
			{ new ImageIcon("images/PacUpClosed.bmp"), new ImageIcon("images/PacUpOpen.bmp") },
			{ new ImageIcon("images/PacRightClosed.bmp"), new ImageIcon("images/PacRightOpen.bmp") },
			{ new ImageIcon("images/PacDownClosed.bmp"), new ImageIcon("images/PacDownOpen.bmp") }
			
	};
	
	/**
	 * PacMan constructor
	 */
	public PacMan() {
		
		this.setIcon(IMAGE[0][0]);
		
	}

}
