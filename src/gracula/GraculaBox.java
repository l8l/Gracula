package gracula;

import processing.core.PApplet;

public class GraculaBox extends GraculaBase {
	
	public GraculaBox(PApplet applet) {
		super(applet);
	}
	
	@Override
	public void draw() {
		applet.box(100);
	}
}
