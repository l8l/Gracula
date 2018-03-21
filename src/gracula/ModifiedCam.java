/**
 * Modified QueasyCaam
 * A super-simple FPS camera for Processing.
 * https://github.com/jrc03c/queasycam
 *
 * Copyright  Josh Castle https://twitter.com/jrc03c
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 * 
 * @author      Josh Castle https://twitter.com/jrc03c
 * @modified    07/02/2016
 * @version     1.1 (2)
 */
package gracula;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Robot;
import java.awt.GraphicsEnvironment;
import java.util.HashMap;
import processing.core.*;
import processing.event.KeyEvent;

public class ModifiedCam {//modifed line 107, see comment below
	public final static String VERSION = "1.1";

	public float speed;
	public float sensitivity;
	public float friction;

	private PApplet applet;
	private Robot robot;
	private PVector eye;
	private PVector center;
	private PVector up;
	private PVector right;
	private PVector forward;
	private PVector velocity;
	private Point mouse;
	private Point prevMouse;
	private float pan;
	private float tilt;
	private HashMap<Character, Boolean> keys;

	public ModifiedCam(PApplet applet){
		this.applet = applet;
		applet.registerMethod("draw", this);
		applet.registerMethod("keyEvent", this);
		
		try {
			robot = new Robot();
		} catch (Exception e){}

		speed = 3f;
		sensitivity = 2f;
		eye = new PVector(0f, 0f, 0f);
		up = new PVector(0f, 1f, 0f);
		right = new PVector(1f, 0f, 0f);
		forward = new PVector(0f, 0f, 1f);
		velocity = new PVector(0f, 0f, 0f);
		pan = 0f;
		tilt = 0f;
		friction = 0.75f;
		keys = new HashMap<Character, Boolean>();

		applet.perspective(PConstants.PI/3f, (float)applet.width/(float)applet.height, 0.01f, 1000f);
	}

	public void draw(){
		mouse = MouseInfo.getPointerInfo().getLocation();
		if (prevMouse == null) prevMouse = new Point(mouse.x, mouse.y);
		
		int w = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().width;
		int h = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds().height;
		
		if (mouse.x < 1 && (mouse.x - prevMouse.x) < 0){
			robot.mouseMove(w-2, mouse.y);
			mouse.x = w-2;
			prevMouse.x = w-2;
		}
				
		if (mouse.x > w-2 && (mouse.x - prevMouse.x) > 0){
			robot.mouseMove(2, mouse.y);
			mouse.x = 2;
			prevMouse.x = 2;
		}
		
		if (mouse.y < 1 && (mouse.y - prevMouse.y) < 0){
			robot.mouseMove(mouse.x, h-2);
			mouse.y = h-2;
			prevMouse.y = h-2;
		}
		
		if (mouse.y > h-10 && (mouse.y - prevMouse.y) > 0){//changed this line because my mouse did not reach a lower bound
			robot.mouseMove(mouse.x, 2);
			mouse.y = 2;
			prevMouse.y = 2;
		}
		
		pan += PApplet.map(mouse.x - prevMouse.x, 0, applet.width, 0, PConstants.TWO_PI) * sensitivity;
		tilt += PApplet.map(mouse.y - prevMouse.y, 0, applet.height, 0, PConstants.PI) * sensitivity;
		tilt = clamp(tilt, -PConstants.PI/2.01f, PConstants.PI/2.01f);
		
		if (tilt == PConstants.PI/2) tilt += 0.001f;

		forward = new PVector(PApplet.cos(pan), PApplet.tan(tilt), PApplet.sin(pan));
		forward.normalize();
		right = new PVector(PApplet.cos(pan - PConstants.PI/2), 0, PApplet.sin(pan - PConstants.PI/2));
		
		prevMouse = new Point(mouse.x, mouse.y);
		
		if (keys.containsKey('a') && keys.get('a')) velocity.add(PVector.mult(right, speed));
		if (keys.containsKey('d') && keys.get('d')) velocity.sub(PVector.mult(right, speed));
		if (keys.containsKey('w') && keys.get('w')) velocity.add(PVector.mult(forward, speed));
		if (keys.containsKey('s') && keys.get('s')) velocity.sub(PVector.mult(forward, speed));
		if (keys.containsKey('q') && keys.get('q')) velocity.add(PVector.mult(up, speed));
		if (keys.containsKey('e') && keys.get('e')) velocity.sub(PVector.mult(up, speed));

		velocity.mult(friction);
		eye.add(velocity);
		center = PVector.add(eye, forward);
		applet.camera(eye.x, eye.y, eye.z, center.x, center.y, center.z, up.x, up.y, up.z);
	}
	
	public void keyEvent(KeyEvent event){
		char key = event.getKey();
		
		switch (event.getAction()){
			case KeyEvent.PRESS: 
				keys.put(key, true);
				break;
			case KeyEvent.RELEASE:
				keys.put(key, false);
				break;
		}
	}
	
	private float clamp(float x, float min, float max){
		if (x > max) return max;
		if (x < min) return min;
		return x;
	}
}