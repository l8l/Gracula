package gracula;

import processing.core.PApplet;
import processing.core.PConstants;
import processing.core.PVector;

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
	private GraculaProcessing applet;
	private PVector[][] cPoints;
	private PVector[][] output;
	private int degreeX;
	private int degreeY;
	private int activePoints;

	public BezierSurface(int degreeX, int degreeY, GraculaProcessing applet) {
		cPoints = new PVector[degreeY][degreeX];
		this.degreeX = degreeX;
		this.degreeY = degreeY;
		this.applet = applet;
	}

	public void setPoint(PVector p, int x, int y) {
		if (x < 0 | x >= degreeX | y < 0 | y >= degreeY) {
			PApplet.println("Index out of bounds!");
			applet.exit();
		}
	
		if (cPoints[y][x] == null) { activePoints++; }
		cPoints[y][x] = p;
	}
	
	public void showControls() {
		applet.stroke(128);
		applet.noFill();
	
		applet.beginShape(PConstants.QUADS);
		for (int y = 0; y < cPoints.length - 1; y++) {
			for (int x = 0; x < cPoints.length - 1; x++) {
				applet.vertex(cPoints[y][x].x, cPoints[y][x].y, cPoints[y][x].z);
				applet.vertex(cPoints[y][x + 1].x, cPoints[y][x + 1].y, cPoints[y][x + 1].z);
				applet.vertex(cPoints[y + 1][x + 1].x, cPoints[y + 1][x + 1].y, cPoints[y + 1][x + 1].z);
				applet.vertex(cPoints[y + 1][x].x, cPoints[y + 1][x].y, cPoints[y + 1][x].z);
			}
		}
		applet.endShape();
	
		applet.stroke(0);
		applet.fill(255);
	}
	
	public PVector[][] computeSurface(int subdiv) {
		if(activePoints < degreeX * degreeY) {
			PApplet.println("Not all points were initialized!");
			applet.exit();
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