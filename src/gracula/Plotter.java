package gracula;

import processing.core.PApplet;
import processing.core.PVector;
import processing.event.MouseEvent;

public class Plotter extends PApplet {

	private float i = 0;
	
	public void settings() {
		size(800, 500, P3D);
	}
	
	public void setup() {
		frameRate(70);
		noStroke();
	}
			
	public void draw() {
		background(0);
		lights();
		
		pushMatrix();
		translate(width/2, height/2, 0);
		i=i+0.01f;
		rotateY(i);
		rotateX(-0.4f);
		
		box(100); //here we should insert a general shape
		
		popMatrix();
	}
}
