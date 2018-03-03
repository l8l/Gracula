package gracula;

import processing.core.PApplet;

public class GraculaSphere extends GraculaBase {
	
	public GraculaSphere(PApplet applet) {
		super(applet);
	}
	
	@Override
	public void draw() {
		applet.sphere(150);
	}
}
