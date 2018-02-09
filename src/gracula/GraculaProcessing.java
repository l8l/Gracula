package gracula;
import processing.core.*;
import processing.event.MouseEvent;

public class GraculaProcessing extends PApplet {
	
	private int grabbedX = -1;
	private int grabbedY = -1;
	private boolean showControls = true;
	
	private int cameraZ;
	private int phiAngle = 0;
	private int thetaAngle = 0;
	private float angle = 0;
	
	PVector[][] controlPoints;
	BezierSurface surface;
	
	public void settings() {
		size(1280, 720, P3D);
		smooth(8);
	}
	
	public void setup() {
		ellipseMode(CENTER);
		frameRate(100);
		cameraZ = height;

		surface = new BezierSurface(3, 3);
		controlPoints = new PVector[3][3];
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				controlPoints[y][x] = new PVector(x * 240 - 240, 0, y * 240 - 240);
			}
		}
	}
	
	public void draw() {
		background(255);
		
		//camera(width/2,0,cameraZ, cos(radians(camAngle))*width/2,height/2,sin(radians(camAngle))*cameraZ, 0,1,0);
		pushMatrix();
		translate(0, height/2, 0);
		translate(width/2, height/2, height/2/tan(PI/6));
		rotateY(radians(phiAngle));
		rotateX(radians(thetaAngle));
		translate(-width/2, -height/2, -height/2/tan(PI/6));
		translate(0, 0, -cameraZ);
		
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
					surface.setPoint(controlPoints[y][x], x, y);
			}
		}
		
		PVector[][] sPoints = surface.computeSurface(20);
		pushMatrix();
		translate(width/2, height/2, 0);
		rotateY(angle);
		
		if (showControls) { surface.showControls(); }
		
		strokeWeight(0.5f);
		beginShape(QUADS);
		for (int y = 0; y < sPoints.length - 1; y++) {
			for (int x = 0; x < sPoints[y].length - 1; x++) {
				fill(0, 255-255*y/sPoints.length, 255*y/sPoints.length); //Verlauf von gruen bis blau
				vertex(sPoints[y][x].x, sPoints[y][x].y, sPoints[y][x].z);
				vertex(sPoints[y][x + 1].x, sPoints[y][x + 1].y, sPoints[y][x + 1].z);
				fill(0, 255-255*(y+1)/sPoints.length, 255*(y+1)/sPoints.length); //Verlauf von gruen bis blau
				vertex(sPoints[y + 1][x + 1].x, sPoints[y + 1][x + 1].y, sPoints[y + 1][x + 1].z);
				vertex(sPoints[y + 1][x].x, sPoints[y + 1][x].y, sPoints[y + 1][x].z);
			}
		}
		endShape();
		popMatrix();
		
		pushMatrix();
		translate(0, 0, cameraZ);
		
		translate(width/2, height/2, height/2/tan(PI/6));
		rotateX(-radians(thetaAngle));
		rotateY(-radians(phiAngle));
		translate(-width/2, -height/2, -height/2/tan(PI/6));
		
		translate(0, -height/2, 0);
		
		fill(128);
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				rect(x * 30, y * 30, 30, 30);
			}
		}
		popMatrix();
		fill(255);
		strokeWeight(1);
		angle += 0.005;
		popMatrix();
		//println(frameRate);
	}
	
	public void mouseReleased() {
		grabbedX = -1;
		grabbedY = -1;
	}
	
	public void mouseWheel(MouseEvent e) {
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 3; x++) {
				if (mouseX > x * 30 & mouseX <= (x + 1) * 30 & mouseY > y * 30 & mouseY < (y + 1) * 30) {
					controlPoints[y][x].y += e.getCount() * 10f;
				}
			}
		}
	}
	
	public void keyReleased() {
		showControls ^= true;
	}

	public void keyPressed() {
		if(key == '+') { // + shall mean zooming in
			cameraZ -= 50; //but the processing z-axis goes out of the screen, therefore "cameraZ -= ..."
		} else if(key == '-') {
			cameraZ += 50;
		}
		
		if(keyCode == LEFT) {
			phiAngle -= 10;
		} else if(keyCode == RIGHT) {
			phiAngle += 10;
		}
		
		if(keyCode == UP) {
			thetaAngle += 10;
		} else if(keyCode == DOWN) {
			thetaAngle -= 10;
		}
	}
	
	/**
	 * Bezier Surface:
	 * - Constructor(int degreeX, int degreeY): Constructs a BezierSurface object with degreeX * degreeY control points.
	 * - void setPoint(PVector p, int x, int y): Sets the control point at coords (x, y).
	 * - void showControls(): Draws lines between adjacent control points.
	 * - PVector[][] computeSurface(int subdiv): Computes the surface based on the control points, 
	 *                                           then returns a 2D array of PVectors, each representing a vertex.
	 * - PVector computePoint(PVector[] pts, float w): Internal utility function. You won't be using this outside of the BezierSurface class.
	 */
	
	public class BezierSurface {
		private PVector[][] cPoints;
		private PVector[][] output;
		private int degreeX;
		private int degreeY;
		private int activePoints;
	
		public BezierSurface(int degreeX, int degreeY) {
			cPoints = new PVector[degreeY][degreeX];
			this.degreeX = degreeX;
			this.degreeY = degreeY;
		}
	
		public void setPoint(PVector p, int x, int y) {
			if (x < 0 | x >= degreeX | y < 0 | y >= degreeY) {
				println("Index out of bounds!");
				exit();
			}
		
			if (cPoints[y][x] == null) { activePoints++; }
			cPoints[y][x] = p;
		}
		
		public void showControls() {
			stroke(128);
			noFill();
		
			beginShape(QUADS);
			for (int y = 0; y < cPoints.length - 1; y++) {
				for (int x = 0; x < cPoints.length - 1; x++) {
					vertex(cPoints[y][x].x, cPoints[y][x].y, cPoints[y][x].z);
					vertex(cPoints[y][x + 1].x, cPoints[y][x + 1].y, cPoints[y][x + 1].z);
					vertex(cPoints[y + 1][x + 1].x, cPoints[y + 1][x + 1].y, cPoints[y + 1][x + 1].z);
					vertex(cPoints[y + 1][x].x, cPoints[y + 1][x].y, cPoints[y + 1][x].z);
				}
			}
			endShape();
		
			stroke(0);
			fill(255);
		}
		
		public PVector[][] computeSurface(int subdiv) {
			if(activePoints < degreeX * degreeY) {
				println("Not all points were initialized!");
				exit();
			}
			output = new PVector[subdiv + 1][subdiv + 1];
			for (int y = 0; y <= subdiv; y++) {
				PVector[] bPoints = new PVector[cPoints.length];
				for (int i = 0; i < cPoints.length; i++) {
					bPoints[i] = computePoint(cPoints[i], ((float) y) / subdiv);
				}
		
				for (int x = 0; x <= subdiv; x++) {
					output[y][x] = computePoint(bPoints, ((float) x) / subdiv);
				}
			}
		
			return output;
		}
		
		private PVector computePoint(PVector[] pts, float w) {
			PVector out = new PVector(0, 0, 0);
			for (int i = pts.length - 1; i > 0; i--) {
				PVector[] lerped = new PVector[i];
				for (int p = 0; p < i; p++) {
					lerped[p] = PVector.lerp(pts[p], pts[p + 1], w);
				}
		
				pts = lerped;
		
				if (pts.length == 1) { out = pts[0]; }
			}
		
			return out;
		}
	}
}
