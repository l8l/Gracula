package gracula;

import processing.core.PApplet;

public abstract class GraculaBase {
	protected final PApplet applet;
	
	public GraculaBase(PApplet applet) {
		this.applet = applet;
	}
	
	public void lightz() {
		applet.noStroke();
	}
	
	public abstract void draw();
}
