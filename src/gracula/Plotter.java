package gracula;

import processing.core.PApplet;
import queasycam.QueasyCam;

public class Plotter extends PApplet {
	private GraculaBox graBox;
	private QueasyCam cam;
	private float i = 0;
	private float cameraZ = (height/2.0f) / tan(PI*60.0f/360.0f);
	
	public void settings() {
		fullScreen(P3D);
	}
	
	public void setup() {
	frameRate(70);
	noStroke();

	cam = new QueasyCam(this);
	cam.sensitivity = (float) 0.5;
	cam.speed = 2.0f;
	perspective(PI/3, (float)width/height, cameraZ/10.0f, cameraZ*100.0f);
	//For perspective explanation, see https://processing.org/reference/perspective_.html
	
	graBox = new GraculaBox(this);
	}
			
	public void draw() {
		background(0);
		lights();
		
		pushMatrix();
		translate(width/2, 0, 0);
		i=i+0.01f;
		rotateY(i);
		rotateX(-0.4f);
		
		graBox.draw(); //here we should insert a general shape
		translate(100,0,0);
		
		popMatrix();
	}
}
