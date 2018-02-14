package gracula;

import processing.core.PApplet;

public class GraculaObject extends GraculaBase implements GraculaDrawable {
	
	public GraculaObject(PApplet applet) {
		super(applet);
	}
	public void draw() {
		applet.box(100);
	}
}
