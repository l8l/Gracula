package gracula;

import processing.core.PVector;

public abstract class Parameterisation {
	
	abstract float getT_0();
	abstract float getT_1();
	abstract float getS_0();
	abstract float getS_1();
	abstract float getScaling();

	float radius;

	Parameterisation(){
	}
	Parameterisation(float radius){
	  this.radius = radius;
	}

	abstract PVector calculateVertex(float s, float t);
	abstract PVector sDerivative(float s, float t);
  	abstract PVector tDerivative(float s, float t);
  
 	public float cos(float value){
 	  return (float)Math.cos(value); //convert from double to float
 	}
 	public float sin(float value){
 	  return (float)Math.sin(value); //convert from double to float
 	}
 	public float tan(float value){
 	  return (float)Math.tan(value); //convert from double to float
 	}
	public float cosh(float value){
	  return (float)Math.cosh(value); //convert from double to float
	}
	public float sinh(float value){
	  return (float)Math.sinh(value); //convert from double to float
	}
	public float tanh(float value){
	  return (float)Math.tanh(value); //convert from double to float
	}
	public float radians(float degree) {
		return (float) (degree*2*Math.PI/360.0);
	}
	public static float PI =(float) (Math.PI);
}


class SphereParameterisation extends Parameterisation {

  float radius = 100.0f;
  
  float getS_0(){return 0.0f;}
  float getS_1(){return 360.0f;}
  float getT_0(){return 0.0f;}
  float getT_1(){return 180.0f;}
  float getScaling(){return 1;}
  
  SphereParameterisation(){
    
  }
  SphereParameterisation(float radius){
    super(radius);
  }
  
  PVector calculateVertex(float theta, float phi){
    PVector v = new PVector();
    
    v.x = radius*cos(radians(theta))*sin(radians(phi));
    v.y = radius*sin(radians(theta))*sin(radians(phi));
    v.z = radius*cos(radians(phi));
    
    return v;
  }
  
  PVector sDerivative(float theta, float phi){
    PVector v = new PVector();
    
    v.x = -radius*sin(radians(theta))*sin(radians(phi));
    v.y = radius*cos(radians(theta))*sin(radians(phi));
    v.z = 0.0f;
    
    return v;
  }
    
  PVector tDerivative(float theta, float phi){
    PVector w = new PVector();
    
    w.x = radius*cos(radians(theta))*(cos(radians(phi)));
    w.y = radius*sin(radians(theta))*(cos(radians(phi)));
    w.z = -radius*sin(radians(phi));
    
    return w;
  }         
}





class MoebiusParameterisation extends Parameterisation {

  float radius = 1.0f;
  
  float getS_0(){return 0.0f;}
  float getS_1(){return 360.0f;}
  float getT_0(){return -1.0f;}
  float getT_1(){return 1.0f;}
  float getScaling(){return 85.0f;}
   
  MoebiusParameterisation(){
    
  }
  MoebiusParameterisation(float radius){
    super(radius);
  }
  
  PVector calculateVertex(float s, float t){
    PVector w = new PVector();
    
    w.x = (1.0f+t/2.0f*cos(radians(s/2.0f)))*cos(radians(s))*radius;
    w.y = (1.0f+t/2.0f*cos(radians(s/2.0f)))*sin(radians(s))*radius;
    w.z = t/2.0f*sin(radians(s/2.0f))*radius;
    
    return w;
  }
    
  PVector sDerivative(float s, float t){
    PVector v = new PVector();
    
    v.x = -(1.0f+t/2.0f*cos(radians(s/2.0f)))*sin(radians(s))*radius - t/2.0f*sin(radians(s))*sin(radians(s/2.0f))*radius/2.0f;
    v.y = (1.0f+t/2.0f*cos(radians(s/2.0f)))*cos(radians(s))*radius - t/2.0f*sin(radians(s))*sin(radians(s/2.0f))*radius/2.0f;
    v.z = t/2.0f*sin(radians(s/2.0f))*radius/2.0f;
    
    return v;
  }       
  PVector tDerivative(float s, float t){
    PVector w = new PVector();
    
    w.x = radius/2.0f*cos(radians(s/2.0f))*cos(radians(s));
    w.y = radius/2.0f*cos(radians(s/2.0f))*sin(radians(s));
    w.z = radius/2.0f*sin(radians(s/2.0f));
    
    return w;
  }  
}


class TorusParameterisation extends Parameterisation {

  float radius = 26.0f;
  float longRadius = 100.0f-radius/2;
  
  float getS_0(){return 0.0f;}
  float getS_1(){return 360.0f;}
  float getT_0(){return 0.0f;}
  float getT_1(){return 360.0f;}
  float getScaling(){return 1;}
   
  TorusParameterisation(){
    
  }
  TorusParameterisation(float radius, float longRadius){
    super(radius);
    this.longRadius = longRadius;
  }
  
  PVector calculateVertex(float s, float t){
    PVector v = new PVector();
    
    v.x = cos(radians(s))*(longRadius + radius*sin(radians(t)));
    v.y = sin(radians(s))*(longRadius + radius*sin(radians(t)));
    v.z = radius*cos(radians(t));
    
    return v;
  }
    
  PVector sDerivative(float s, float t){
    PVector v = new PVector();
    
    v.x = -sin(radians(s))*(longRadius+sin(radians(t))*radius);
    v.y = cos(radians(s))*(longRadius+sin(radians(t))*radius);
    v.z = 0.0f;
        
    return v;
  }       
  PVector tDerivative(float s, float t){
    PVector w = new PVector();

    w.x = radius*cos(radians(s))*(cos(radians(t)));
    w.y = radius*sin(radians(s))*(cos(radians(t)));
    w.z = -radius*sin(radians(t));
    
    return w;
  }
}


class HelixParameterisation extends Parameterisation {

  float radius = 26.0f;
  float longRadius = 100.0f-radius/2;
  float lengthFactor = 10;
  
  float getS_0(){return 0.0f;}
  float getS_1(){return 720.0f;}
  float getT_0(){return 0.0f;}
  float getT_1(){return 360.0f;}
  float getScaling(){return 1;}
   
  HelixParameterisation(){
    
  }
  HelixParameterisation(float radius, float longRadius){
    super(radius);
    this.longRadius = longRadius;
  }
  
  PVector calculateVertex(float s, float t){
    PVector v = new PVector();
    
    v.x = cos(radians(s))*(longRadius + radius*sin(radians(t)));
    v.y = sin(radians(s))*(longRadius + radius*sin(radians(t)));
    v.z = radius*cos(radians(t))+lengthFactor*radians(s);
    
    return v;
  }
    
  PVector sDerivative(float s, float t){
    PVector v = new PVector();
    
    v.x = -sin(radians(s))*(longRadius+sin(radians(t))*radius);
    v.y = cos(radians(s))*(longRadius+sin(radians(t))*radius);
    v.z = lengthFactor*radians(1.0f);
        
    return v;
  }       
  PVector tDerivative(float s, float t){
    PVector w = new PVector();

    w.x = radius*cos(radians(s))*(cos(radians(t)));
    w.y = radius*sin(radians(s))*(cos(radians(t)));
    w.z = -radius*sin(radians(t));
    
    return w;
  }
}



class HelicoidParameterisation extends Parameterisation {

  float radius = 70.0f;
  float rotations = 2.0f;
  float lengthFactor = 10.0f;
  
  float getS_0(){return -rotations*360.0f/2.0f;}
  float getS_1(){return rotations*360.0f/2.0f;}
  float getT_0(){return 0.0f;}
  float getT_1(){return radius;}
  float getScaling(){return 1;}
   
  HelicoidParameterisation(){
    
  }
  HelicoidParameterisation(float radius){
    super(radius);
  }
  
  PVector calculateVertex(float s, float t){
    PVector v = new PVector();
    
    v.x = t*cos(radians(s));
    v.y = t*sin(radians(s));
    v.z = lengthFactor*radians(s);
    
    return v;
  }
    
  PVector sDerivative(float s, float t){
    PVector v = new PVector();
    
    v.x = -t*sin(radians(s));
    v.y = t*cos(radians(s));
    v.z = lengthFactor*radians(1);
        
    return v;
  }       
  PVector tDerivative(float s, float t){
    PVector w = new PVector();

    w.x = cos(radians(s));
    w.y = sin(radians(s));
    w.z = 0;
    
    return w;
  }
}


class CatenoidParameterisation extends Parameterisation {

  float radius = 30.0f;
  float lengthFactor = 1;
  
  float getS_0(){return -180.0f;}
  float getS_1(){return 180.0f;}
  float getT_0(){return -50.0f;}
  float getT_1(){return 50.0f;}
  float getScaling(){return 1;}
   
  CatenoidParameterisation(){
    
  }
  CatenoidParameterisation(float radius){
    super(radius);
  }
  
  PVector calculateVertex(float s, float t){
    PVector v = new PVector();
    
    v.x = radius*((float)Math.cosh(t/radius))*cos(radians(s));
    v.y = radius*((float)Math.cosh(t/radius))*sin(radians(s));
    v.z = lengthFactor*t;
    
    return v;
  }
    
  PVector sDerivative(float s, float t){
    PVector v = new PVector();
    
    v.x = -radius*((float)Math.cosh(t/radius))*sin(radians(s));
    v.y = radius*((float)Math.cosh(t/radius))*cos(radians(s));
    v.z = 0;
    
    return v;
  }       
  PVector tDerivative(float s, float t){
    PVector w = new PVector();

    w.x = ((float)Math.sinh(t/radius))*cos(radians(s));
    w.y = ((float)Math.sinh(t/radius))*sin(radians(s));
    w.z = lengthFactor;
    
    return w;
  }
}

class TrefoilKnotParameterisation extends Parameterisation {

  float radius = 30.0f;
  float lengthFactor = 1;
  
  float getS_0(){return -180.0f;}
  float getS_1(){return 180.0f;}
  float getT_0(){return -180.0f;}
  float getT_1(){return 180.0f;}
  float getScaling(){return 25;}
   
  TrefoilKnotParameterisation(){
    
  }
  TrefoilKnotParameterisation(float radius){
    super(radius);
  }
  
  PVector calculateVertex(float s, float t){
    PVector v = new PVector();
    
    v.x = (4.0f*(1.0f+0.25f*sin(radians(3.0f*s)))+cos(radians(t)))*cos(radians(2.0f*s));
    v.y = (4.0f*(1.0f+0.25f*sin(radians(3.0f*s)))+cos(radians(t)))*sin(radians(2.0f*s));
    v.z = sin(radians(t))+2.0f*cos(radians(3.0f*s));
    
    return v;
  }
    
  PVector sDerivative(float s, float t){
    PVector w = new PVector();

    w.x = -2.0f*(4.0f*(1.0f+0.25f*sin(radians(3.0f*s)))+cos(radians(t)))*sin(radians(2.0f*s))+(4.0f*(3.0f*0.25f*cos(radians(3.0f*s))))*cos(radians(2.0f*s));
    w.y =  2.0f*(4.0f*(1.0f+0.25f*sin(radians(3.0f*s)))+cos(radians(t)))*cos(radians(2.0f*s))+(4.0f*(3.0f*0.25f*cos(radians(3.0f*s))))*sin(radians(2.0f*s));
    w.z = 2.0f*3.0f*cos(radians(3.0f*s));
    
    return w;
  }
  PVector tDerivative(float s, float t){
    PVector v = new PVector();
    
    v.x = -sin(radians(t))*cos(radians(2.0f*s));
    v.y = -sin(radians(t))*sin(radians(2.0f*s));
    v.z = cos(radians(t));
    
    return v;
  }       
}


class KleinParameterisation extends Parameterisation {

  float uT1 = 1.5f;       //upper half Torus small radius
  float uT2 = 3.0f;       //upper half Torus big radius
  
  float tubeLength = 3.0f;//length of tubes connecting the upper half torus and the lower torus
    
  float lT1 = uT2 - uT1; //lower Torus small radius must match upper Torus inner radius
  float lT2 = 2.6f;       //lower Torus big radius can vary 
  
  
  
  float getS_0(){return 0.0f;}
  float getS_1(){return radians(360.0f);}
  float getT_0(){return radians(0.0f);}
  float getT_1(){return radians(720.0f);}
  float getScaling(){return 13;}
   
  KleinParameterisation(){
    
  }
  KleinParameterisation(float radius){
    super(radius);
  }
  
  PVector calculateVertex(float u, float v){
    PVector w = new PVector();
    
    if(v < radians(180.0f)){
      
      w.x = (uT2 - uT1*cos(v))*cos(u);
      w.y = (uT2 - uT1*cos(v))*sin(u);
      w.z = - uT2*sin(v);
      
    }else if(v >= radians(180.0f) && v < radians(360.0f)){
      
      w.x = (uT2 - uT1*cos(v))*cos(u);
      w.y = (uT2 - uT1*cos(v))*sin(u);
      w.z = tubeLength*(v - radians(180.0f));
      
    }else if(v >= radians(360.0f) && v < radians(540.0f)){
      
      w.x = (lT2 + lT1*cos(u))*cos(v) - lT2;
      w.y = lT1*sin(u);
      w.z = (lT2 + lT1*cos(u))*sin(v) + tubeLength*radians(180.0f);
      
    }else if(v > radians(540.0f) && v < radians(721.0f)){
      
      w.x = -lT1*cos(u) + lT2*(cos(v) - 1);
      w.y = lT1*sin(u);
      w.z = tubeLength*(4.0f*radians(180.0f) - v);

    }
    
    return w;
  }
    
  PVector sDerivative(float u, float v){
    PVector w = new PVector();
    
    if(v < radians(180.0f)){
      
      w.x = -(uT2-uT1*cos(v))*sin(u);
      w.y = (uT2-uT1*cos(v))*cos(u);
      w.z = 0.0f;
      
    }else if(v >= radians(180.0f) && v < radians(360.0f)){
      
      w.x = -(uT2-uT1*cos(v))*sin(u);
      w.y = (uT2-uT1*cos(v))*cos(u);
      w.z = 0.0f;
      
    }else if(v >= radians(360.0f) && v < radians(540.0f)){
      
      w.x = -lT1*sin(u)*cos(v);
      w.y =  lT1*cos(u);
      w.z = -lT1*sin(u)*sin(v);
      
    }else if(v > radians(540.0f) && v < radians(721.0f)){
      
      w.x = lT1*sin(u);
      w.y = lT1*cos(u);
      w.z = 0.0f;
    }
    
    return w;
  }
  PVector tDerivative(float u, float v){
    PVector w = new PVector();
    
    if(v < radians(180.0f)){
      
      w.x = uT1*sin(v)*cos(u);
      w.y = uT1*sin(v)*sin(u);
      w.z = -uT2*cos(v);
      
    }else if(v >= radians(180.0f) && v < radians(360.0f)){
      
      w.x = uT1*sin(v)*cos(u);
      w.y = uT1*sin(v)*sin(u);
      w.z = tubeLength;
      
    }else if(v >= radians(360.0f) && v < radians(540.0f)){
      
      w.x = -(lT2 + lT1*cos(u))*sin(v);
      w.y = 0.0f;
      w.z = (lT2 + lT1*cos(u))*cos(v);
      
    }else if(v > radians(540.0f) && v < radians(721.0f)){
      
      w.x = -lT2*sin(v);
      w.y = 0.0f;
      w.z = -tubeLength;

    }
    
    return w;
  }       
}




class CrossCapParameterisation extends Parameterisation {

  float radius = 50.0f;
  float longRadius = 100.0f-radius/2.0f;
  
  float getS_0(){return radians(0.0f);}
  float getS_1(){return radians(360.0f);}
  float getT_0(){return radians(0.0f);}
  float getT_1(){return radians(360.0f);}
  float getScaling(){return 1;}
   
  CrossCapParameterisation(){
    
  }
  CrossCapParameterisation(float radius, float longRadius){
    super(radius);
  }
  
  PVector calculateVertex(float s, float t){
    PVector v = new PVector();
    
    v.x = radius*cos(s)*(1.0f + cos(t));
    v.y = radius*sin(s)*(1.0f + cos(t));
    v.z = - radius*tanh(s - PI)*sin(t);

    return v;
  }
    
  PVector sDerivative(float s, float t){
    PVector v = new PVector();
    
    v.x = -radius*sin(s)*(1.0f + cos(t));
    v.y = radius*cos(s)*(1.0f + cos(t));
    v.z = - (radius/(cosh(s - PI)*cosh(s - PI)))*sin(t);
        
    return v;
  }       
  PVector tDerivative(float s, float t){
    PVector w = new PVector();

    w.x = -radius*cos(s)*(sin(t));
    w.y = -radius*sin(s)*(sin(t));
    w.z = - radius*tanh(s - PI)*cos(t);
    
    return w;
  }
}




class OpenCrossCapParameterisation extends Parameterisation {

  float radius = 50.0f;
  float openingGap = 1.0f/5.0f;
  
  float getS_0(){return radians(0.0f);}
  float getS_1(){return radians(360.0f);}
  float getT_0(){return radians(0.0f);}
  float getT_1(){return radians(360.0f);}
  float getScaling(){return 1;}
   
  OpenCrossCapParameterisation(){
    
  }
  OpenCrossCapParameterisation(float radius, float longRadius){
    super(radius);
  }
  
  PVector calculateVertex(float s, float t){
    PVector v = new PVector();
    
    v.x = radius*cos(s)*(1.0f + cos(t));
    v.y = radius*sin(s)*(1.0f + cos(t));
    v.z = - radius*tanh(s - PI)*(sin(t)-t*openingGap)*cos(s/2);

    return v;
  }
    
  PVector sDerivative(float s, float t){
    PVector v = new PVector();
    
    v.x = -radius*sin(s)*(1.0f + cos(t));
    v.y = radius*cos(s)*(1.0f + cos(t));
    v.z = - (radius/(cosh(s - PI)*cosh(s - PI)))*(sin(t)-t*openingGap)*cos(s/2)+ radius*tanh(s - PI)*(sin(t)-t*openingGap)*sin(s/2)/2.0f;
        
    return v;
  }       
  PVector tDerivative(float s, float t){
    PVector w = new PVector();

    w.x = -radius*cos(s)*(sin(t));
    w.y = -radius*sin(s)*(sin(t));
    w.z = - radius*tanh(s - PI)*(cos(t)-openingGap)*cos(s/2);
    
    return w;
  }
}


class DoubleOpenedCrossCapParameterisation extends Parameterisation {

  float radius = 50.0f;
  float openingGap = 1.0f/5.0f;
  
  float getS_0(){return radians(0.0f);}
  float getS_1(){return radians(360.0f);}
  float getT_0(){return radians(0.0f);}
  float getT_1(){return radians(360.0f);}
  float getScaling(){return 1;}
   
  DoubleOpenedCrossCapParameterisation(){
    
  }
  DoubleOpenedCrossCapParameterisation(float radius){
    super(radius);
  }
  
  PVector calculateVertex(float s, float t){
    PVector v = new PVector();
    
    v.x = radius*cos(s)*(1.0f + cos(t));
    v.y = radius*sin(s)*(1.0f + cos(t));
    v.z = - radius*tanh(s - PI)*(sin(t)-t*openingGap);

    return v;
  }
    
  PVector sDerivative(float s, float t){
    PVector v = new PVector();
    
    v.x = -radius*sin(s)*(1.0f + cos(t));
    v.y = radius*cos(s)*(1.0f + cos(t));
    v.z = - (radius/(cosh(s - PI)*cosh(s - PI)))*(sin(t)-t*openingGap);
    
    return v;
  }       
  PVector tDerivative(float s, float t){
    PVector w = new PVector();

    w.x = -radius*cos(s)*(sin(t));
    w.y = -radius*sin(s)*(sin(t));
    w.z = - radius*tanh(s - PI)*(cos(t) - openingGap);
    
    return w;
  }
}


