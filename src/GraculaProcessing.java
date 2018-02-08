import processing.core.PApplet;

public class GraculaProcessing extends PApplet {

	public static void main(String[] args) {
		PApplet.main("GraculaProcessing");
	}
	public void settings() {
		size(500,500);
	}
	public void setup() {
		fill(120,50,240);
	}
	public void draw() {
		float a = 0.5f;
		ellipse(width/2,height/2,3*second(),a*second());
	}

}
