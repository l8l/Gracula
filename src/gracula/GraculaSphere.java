package gracula;

import processing.core.PApplet;

public class GraculaSphere extends GraculaBase {
	
	protected float size = 150f;
	protected float x = 0f;
	protected float y = 0f;
	protected float z = 0f;
	
	protected float colorR = 255;
	protected float colorG = 255;
	protected float colorB = 255;
	
	
	public GraculaSphere(PApplet applet) {
		super(applet);
	}
	
	public GraculaSphere(PApplet applet, float size) {
		super(applet);
		this.size = size;
	}
	
	public GraculaSphere(PApplet applet, float size, float x, float y, float z) {
		super(applet);
		this.size = size;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public void setRgbColor(float r, float g, float b) {
		this.colorR = r;
		this.colorG = g;
		this.colorB = b;
	}
	
	@Override
	final public void draw() {
		this.beforeDraw();
		
		this.applet.pushMatrix();
			this.applet.translate(this.x, this.y, this.z);
			this.applet.fill(this.colorR, this.colorG, this.colorB);
			this.applet.sphere(this.size);
		this.applet.popMatrix();
	}
	
	protected void beforeDraw() {}
}
