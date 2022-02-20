package application.gameobjects;

import java.awt.Color;
import javax.swing.BorderFactory;
import framework.GameObject;

/**
 * A GameObject of type TTTPlate
 * @author Erik L
 * @author Max M
 */

@SuppressWarnings("serial")
public class TTTPlate extends GameObject{
	/**
	 * Constructs a TicTacToePlate GameObject with its corresponding image
	 * @precondition src/icons/empty.png must be a working directory
	 */
	public TTTPlate(){
		setImageIcon("src/icons/empty.png");
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}
	/**
	 * Sets the size of the GameObject to a fixed size of 100*100
	 */
	@Override
	public void setObjectSize() {
		setSize(100,100);
	}
}