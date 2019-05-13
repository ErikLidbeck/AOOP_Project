package framework;

import javax.swing.ImageIcon;

public abstract class gameObject{
	private ImageIcon model;
	private int x;
	private int y;
	
	void setIcon(String fileName){
		model = new ImageIcon(fileName);
	}
	
	void move(int newX, int newY){
		x = newX;
		y = newY;
	}
	
	int getX(){
		return x;
	}
	
	int getY(){
		return y;
	}
}
