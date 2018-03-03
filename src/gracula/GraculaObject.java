package gracula;

import processing.core.PApplet;

public class GraculaObject extends GraculaBase {
	
	public GraculaObject(PApplet applet) {
		super(applet);
	}
	
	@Override
	public void draw() {
		applet.box(100);
	}
}
