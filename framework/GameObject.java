package framework;

import java.awt.Graphics;
import javax.swing.*;

/**
 * An abstract class extending JPanel, implemented to keep each component in the game as its own GameObject
 * @author Erik L
 * @author Max M
 * @invariance x > 0
 * @invariance y > 0
 */

@SuppressWarnings("serial")
public abstract class GameObject extends JPanel{
	private ImageIcon objectIcon;
	private int x;
	private int y;
	/**
	 * Constructs a GameObject and sets a size determined in the extending class
	 */
	public GameObject() {
		setObjectSize();
	}
	public abstract void setObjectSize();
	/**
	 * Assigns an ImageIcon to a GameObject
	 * @param fileName Filepath to the image of the GameObject
	 * @precondition fileName must be a working directory
	 * @postcondition objectIcon != null
	 */
	public void setImageIcon(String fileName){
		objectIcon = new ImageIcon(fileName);
	}
	/**
	 * @return The ImageIcon of a GameObject
	 */
	public ImageIcon getImageIcon(){
		return objectIcon;
	}
	/**
	 * Paints or updates the image of a GameObject
	 * @param g Graphical component
	 */
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon imgIcon = getImageIcon();
		imgIcon.paintIcon(this, g, x, y);
	}
}
