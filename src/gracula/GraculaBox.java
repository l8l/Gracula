package gracula;

import processing.core.PApplet;

public class GraculaBox extends GraculaBase implements GraculaDrawable {
	
	public GraculaBox(PApplet applet) {
		super(applet);
	}
	public void draw() {
		applet.box(100);
	}
}
