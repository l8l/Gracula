package gracula;

import processing.core.PApplet;
import processing.core.PImage;

public class Plotter extends PApplet {
	ModifiedCam cam;
	
	public int displayMode = 2;
	public boolean rotate = true;
	public float frameCounter = 0.0f;
	public int screenShotCounter = 0;

	SphereParameterisation sp = new SphereParameterisation(100);
	MoebiusParameterisation mp = new MoebiusParameterisation(1);
	TorusParameterisation tp = new TorusParameterisation();
	HelixParameterisation hxp = new HelixParameterisation();
	HelicoidParameterisation hp = new HelicoidParameterisation();
	CatenoidParameterisation cp = new CatenoidParameterisation();
	TrefoilKnotParameterisation tkp = new TrefoilKnotParameterisation();
	KleinParameterisation kp = new KleinParameterisation();
	CrossCapParameterisation ccp = new CrossCapParameterisation();
	OpenCrossCapParameterisation occp = new OpenCrossCapParameterisation();
	DoubleOpenedCrossCapParameterisation doccp = new DoubleOpenedCrossCapParameterisation();

	ParameterisedObject sphere;
	ParameterisedObject moebius;
	ParameterisedObject torus;
	ParameterisedObject helix;
	ParameterisedObject helicoid;
	ParameterisedObject catenoid;
	ParameterisedObject trefoilKnot;
	ParameterisedObject klein;
	ParameterisedObject crossCap;
	ParameterisedObject openCrossCap;
	ParameterisedObject doubleOpenedCrossCap;
	
	public void settings() {
		fullScreen(P3D);
	}
	
	public void setup() {
		float cameraZ;
		cam = new ModifiedCam(this);    
		cam.sensitivity = 0.5f;
		cam.speed = 2.0f;
		    
		cameraZ = (height/2.0f) / tan(PI*60.0f/360.0f);
		perspective(PI/3, (float)width/height, cameraZ/100.0f, cameraZ*100.0f);
		  
	    sphere = new ParameterisedObject(sp, this);
	    moebius = new ParameterisedObject(mp, this);
	    torus = new ParameterisedObject(tp, this);
	    helix = new ParameterisedObject(hxp, this);
	    helicoid = new ParameterisedObject(hp, this);
	    catenoid = new ParameterisedObject(cp, this);
	    trefoilKnot = new ParameterisedObject(tkp, this);
	    klein = new ParameterisedObject(kp, this);
	   
	    //crossCap = new ParameterisedObject(ccp, this);
	    //openCrossCap = new ParameterisedObject(occp, this);
	    doubleOpenedCrossCap = new ParameterisedObject(doccp, this);   
	}
			
	public void draw() {
		background(79, 86, 201);
		lights();  
		   
	    sphere.display(frameCounter, 470+320, 0, 0, this, displayMode);
	    torus.display(frameCounter, 470+320*2, 0, -320, this, displayMode);
	    klein.display(frameCounter, 470+320, 0, 320, this, displayMode);
	    moebius.display(frameCounter, 470, 0, 320, this, displayMode);
	    trefoilKnot.display(frameCounter, 470+320*2, 0, 0, this, displayMode);
	    helix.display(frameCounter, 470+320, 0, -320, this, displayMode);
	    catenoid.display(frameCounter, 470, 0, 0, this, displayMode);
	    helicoid.display(frameCounter, 470, 0, -320, this, displayMode);
		   
	    //crossCap.display(frameCounter, 470+320*2, 0, -320);
	    //openCrossCap.display(frameCounter, 470+320*2, 0, 0);
	    doubleOpenedCrossCap.display(frameCounter, 470+320*2, 0, 320, this, displayMode);
	   
	    if(rotate==true){
	      frameCounter += 1.0/50.0;
	    }
	}
	
	public void keyPressed(){ 
		// displayMode
		if (key =='0'){
			displayMode = 0;
		}
		else if (key =='1'){
		    displayMode = 1;
		}
		else if (key =='2'){
		    displayMode = 2;
		}
		  
		//rotation
		if (key == 'r'){
			if(rotate ==false){
				rotate=true;
		    }
			else{
				rotate=false;
		    }
		 }
		  
		 //cameraMovingSpeed
		 if(key == '-' && cam.speed > 0.5f){
			 cam.speed -= 0.5f;
		 }
		 if(key == '-' && cam.speed <= 0.5f && cam.speed > 0.1f){
			 cam.speed -= 0.1f;
		 }
		 else if(key == '+' && cam.speed < 10.0f){
			 cam.speed += 1.0f;
		 }    
		  
		 //print screenshots
		 if(key == 'p'){
			 PImage screenshot = get();
			 screenShotCounter += 1;
			 String name = "Shot "+str(screenShotCounter)+".jpg";
			 screenshot.save(name);
		 }
	}
}
