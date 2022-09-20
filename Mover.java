import javax.swing.JLabel;

@SuppressWarnings("Serial")


public abstract class Mover extends JLabel {
	
	private int row;
	private int column;
	
	private int dRow;
	private int dColumn;
	
	private boolean isDead;
	
	public int getdRow() {
		return dRow;
	}
	
	public void setdRow(int dRow) {
		this.dRow = dRow;
	}
	
	public int getdColumn() {
		return dColumn;
	}
	
	public void setdColumn(int dColumn) {
		this.dColumn = dColumn;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int Row) {
		this.row = row;
	}
	
	public int getColumn() {
		return column;
	}
	
	public void setColumn(int column) {
		this.column = column;
	}
	
	public int getDRow() {
		return dRow;
	}
	
	public void setDRow(int dRow) {
		this.dRow = dRow;
	}
	
	public int getDColumn() {
		return dColumn;
	}
	
	public void setDColumn(int dColumn) {
		this.dColumn = dColumn;
	}
	
	public boolean isDead() {
		return isDead;
	}
	
	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}
	
	public void move() {
		
		row += dRow;
		column += dColumn;
		
	}
	
	/**
	 * Sets the direction of the mover based on a direction parameter
	 * 
	 * @param direction 0 - left, 1 - up, 2 - right, 3 - down
	 */
	public void setDirection(int direction) {
		
		dRow = 0;
		dColumn = 0;
		
		if(direction==0)
			dColumn = -1;
		else if(direction==1)
			dRow = -1;
		else if(direction==2)
			dColumn = 1;
		else if(direction==3)
			dRow = 1;
		
	}
	
	/**
	 * return the mover's direction based in their current row or column direction
	 * 
	 * @return direction
	 */
	public int getDirection() {
		
		if(dRow== 0 && dColumn== -1)
			return 0;
		else if(dRow== -1 && dColumn== 0)
			return 1;
		else if(dRow== 0 && dColumn== 1)
			return 2;
		else
			return 3;
		
	}
	
	/**
	 * return the mover's next row
	 * 
	 * @return next row
	 */
	public int getNextRow() {
		
		return row + dRow;
		
	}
	
	/**
	 * return the mover's next column
	 * 
	 * @return next column
	 */
	public int getNextColumn() {
		
		return column + dColumn;
		
	}
}
