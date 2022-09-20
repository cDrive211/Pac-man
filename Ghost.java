import javax.swing.ImageIcon;

@SuppressWarnings("Serial")

public class Ghost extends Mover {
	
	/**
	 * creates an array of ImageIcons representing various ghosts
	 */
	public static final ImageIcon[] IMAGE = {
			
			new ImageIcon("images/Ghost0.bmp"),
			new ImageIcon("images/Ghost1.bmp"),
			new ImageIcon("images/Ghost2.bmp")
			
	};
	
	/**
	 * Ghost constructor
	 * 
	 * @param gNum ghost number - 0, 1 or 2
	 */
	public Ghost(int gNum) {
		
		this.setIcon(IMAGE[gNum]);
		
	}

}
