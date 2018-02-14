package gracula;

import processing.core.PApplet;

public class GraculaSphere extends GraculaBase implements GraculaDrawable {
	
	public GraculaSphere(PApplet applet) {
		super(applet);
	}
	public void draw() {
		applet.sphere(150);
	}
}
