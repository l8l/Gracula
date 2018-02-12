package gracula;

import processing.core.PApplet;

public class Plotter extends PApplet {
	private GraculaBox graBox;

	private float i = 0;
	
	public void settings() {
		size(800, 500, P3D);
	}
	
	public void setup() {
		frameRate(70);
		noStroke();
		graBox = new GraculaBox(this);
	}
			
	public void draw() {
		background(0);
		lights();
		
		pushMatrix();
		translate(width/2, height/2, 0);
		i=i+0.01f;
		rotateY(i);
		rotateX(-0.4f);
		
		graBox.draw(); //here we should insert a general shape
		
		popMatrix();
	}
}
