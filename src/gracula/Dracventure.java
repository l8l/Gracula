package gracula;

import java.util.ArrayList;

import processing.core.PApplet;
import queasycam.QueasyCam;

public class Dracventure extends PApplet {
	
	private ArrayList<GraculaSphere> spheres = new ArrayList<>();

	public void settings() {
		fullScreen(P3D);
		//size(1280, 720, P3D);
	}
	
	public void setup() {
		frameRate(60);
		noStroke();
		
		QueasyCam queasyCam = new QueasyCam(this);
		queasyCam.sensitivity = 0.5f;
		queasyCam.speed = 2.0f;
		
		float perspectiveZ = (this.height / 2.0f) / tan( PI * 60.0f / 360.0f);
		perspective( PI / 3, (float) width / height, perspectiveZ / 10.0f, perspectiveZ * 100.0f);
		//For perspective explanation, see https://processing.org/reference/perspective_.html
		
		GraculaSphere g1 = new GraculaSphere(this, 10, 0, 0, 0);
		g1.setRgbColor(255, 0, 0);
		this.spheres.add(g1);
		
		GraculaSphere g2 = new GraculaSphere(this, 10, 500, 0, 0);
		g2.setRgbColor(255, 0, 0);
		this.spheres.add(g2);
		
		GraculaSphere g3 = new GraculaSphere(this, 10, 500, 500, 0);
		g3.setRgbColor(255, 0, 0);
		this.spheres.add(g3);
		
		GraculaSphere g4 = new GraculaSphere(this, 10, 0, 500, 0);
		g4.setRgbColor(255, 0, 0);
		this.spheres.add(g4);
		
		GraculaSphere g5 = new GraculaSphere(this, 10, 0, 0, 500);
		g5.setRgbColor(255, 0, 0);
		this.spheres.add(g5);
		
		GraculaSphere g6 = new GraculaSphere(this, 10, 500, 0, 500);
		g6.setRgbColor(255, 0, 0);
		this.spheres.add(g6);
		
		GraculaSphere g7 = new GraculaSphere(this, 10, 500, 500, 500);
		g7.setRgbColor(255, 0, 0);
		this.spheres.add(g7);
		
		GraculaSphere g8 = new GraculaSphere(this, 10, 0, 500, 500);
		g8.setRgbColor(255, 0, 0);
		this.spheres.add(g8);
		
		
		for (int i = 0 ; i < 200 ; i++) {
			this.spheres.add(new DizzySphere(this, 20, 250, 250, 250));
		}
	}
			
	public void draw() {
		lights();
		background(0,0,0);
		
		noCursor();
		
		for (GraculaSphere g : this.spheres) {
			g.draw();
		}
	}
}
