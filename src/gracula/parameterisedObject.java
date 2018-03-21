package gracula;

import processing.core.PVector;
import processing.core.PApplet;
import processing.core.PShape;

class ParameterisedObject {
  
  int pts = 40; 
  int segments = 40;
  
  float s_0;
  float s_1;
  float deltaS;
  
  float t_0;
  float t_1;
  float deltaT;
  
  //vertices
  PVector vertices[][];
  
  // for shaded or wireframe rendering 
  float scaling = 1.0f;
  
  PShape[] object;
  PApplet applet;
  
  Parameterisation parameterisation;

  ParameterisedObject(Parameterisation parameterisation, PApplet applet){ 
    
	this.applet = applet;
    this.parameterisation = parameterisation;
    s_0 = parameterisation.getS_0();
    s_1 = parameterisation.getS_1();
    deltaS = (s_1-s_0)/segments;
    
    t_0 = parameterisation.getT_0();
    t_1 = parameterisation.getT_1();
    deltaT = (t_1-t_0)/pts;
    scaling = parameterisation.getScaling();
    
    object = new PShape[3];
    for (int mode=0; mode<3; mode++){//create wire frame, filled surfaces and a combination
      object[mode] = createObject(mode, parameterisation, applet);
    }
  }
  
  private PShape createObject(int mod, Parameterisation parameterisation, PApplet applet){
    
    //Our completeShape is divided into segments. Each segment is a shape in itself 
    vertices = new PVector[pts+1][2];
    PShape shape;
    PShape completeShape;
    completeShape = applet.createShape(PShape.GROUP);

    //parameter s of our shape varies over all segments
    float s = s_0;
    for(int i=0; i<segments; i++){
      
      //parameter s of our shape only varies over all points of one segment
      float t = t_0;
      //we create 1 segment
      shape = applet.createShape();
      shape.beginShape(PShape.QUAD_STRIP);
      
      shape = decideDisplayMode(mod,shape);
      
      for(int j=0; j<pts+1; j++){

        for(int k=0; k<=1; k++){ 
          //This additional loop is necessary because we use the QUAD_STRIP tesselation of beginShape
          vertices[j][k] = new PVector();
          vertices[j][k] = parameterisation.calculateVertex(s+k*deltaS,t);
          
          /*  The surface will have a much better quality if we give the normal vector of the surface 
              to the renderer at every point.
          
              If x(t,s) is the parameterisation of a surface, 
              then the normal vector is the normalized cross product of the derivatives 
              n = normalized(\partial x/\partial t X \partial x/\partial s).
              
              For example, in case we have the following parameterisation of a sphere
                        (cos(s)sin(t))
              x(t,s) = r(sin(s)sin(t))
                        (cos(t)      )
                       
              Then the derivatives are
                                       (-sin(s)sin(t))
              \partial x/\partial s = r(cos(s)sin(t) ) =: v
                                       (0            )
              and              
                                       (cos(s)cos(t) )     
              \partial x/\partial t = r(sin(s)cos(t) ) =: w
                                       (-sin(t)      )
                                       
              In the following we implement the normal for our surface:                     
          */
          PVector normal = new PVector();
          PVector v = new PVector();
          PVector w = new PVector();
          
          PVector crossProduct = new PVector();
          PVector emptyVector = new PVector();
          emptyVector.x=0;
          emptyVector.y=0;
          emptyVector.z=0;
          crossProduct.x=0;
          crossProduct.y=0;
          crossProduct.z=0;
          int next = 0;
          while(crossProduct.dist(emptyVector) < 0.001){
            //we need a while-loop because the derivative might be (nearly) zero at certain points
        	//which would make our cross-product ill-defined
            //thus we iterate until we obtain a non-zero value
            
            v = parameterisation.sDerivative(s+(k+next)*deltaS,t+next*deltaT);
            w = parameterisation.tDerivative(s+(k+next)*deltaS,t+next*deltaT);
            
            crossProduct = v.cross(w);
                          
            //if the normal is zero, we want the normal to take the value of the most adjacent normal
            //if we are at the beginning of an interval, we must take an adjacent normal in the positive direction
            if(j==0){
              next += 1;
            }
            //if we are not at the beginning of an interval, 
            //the most adjacent normal shall depend on k (because of the tesselation QUAD_STRIP)
            else if(j!=0 && k==0){
              next -= 1;
            }
            else if(j!=0 && k==1){
              next += 1;
            }
            if(next > 1 || next < -1){break;}
          }
          normal = crossProduct.normalize();
     
          //Now we can add the normal and the corresponding vertex to the PShape:
          shape.normal(normal.x,normal.y,normal.z);
          shape.vertex(vertices[j][k].x, vertices[j][k].y, vertices[j][k].z);
        }
        t+=deltaT;
      }
      shape.endShape();
      completeShape.addChild(shape);
      
      s+=deltaS;
    }
    return completeShape;
  }
  
  private PShape decideDisplayMode(int dispMode, PShape s){
    if (dispMode==0){
        s.stroke(255, 255, 180);
        s.noFill();
     } 
     else if(dispMode==1) {
        s.noStroke();
        s.fill(246,250,48);
     }
     else if(dispMode==2) {
        s.stroke(255,255,155);
        s.fill(246,250,48);
     }
     return s;
  }
    
  public void display(float frameNumber, int translateX, int translateY, int translateZ, PApplet applet, int displayMode){
    applet.pushMatrix();
     
    //translate, rotate and scale object (must be in this particular order)    
    applet.translate(translateX, translateY, translateZ);
      
    applet.rotateX(frameNumber);
    applet.rotateY(frameNumber/4.0f);
    applet.rotateZ(frameNumber/2.0f);
        
    applet.scale(scaling);
      
      //display shape with or without wire
      applet.shape(object[displayMode]);
    applet.popMatrix();
  }
}