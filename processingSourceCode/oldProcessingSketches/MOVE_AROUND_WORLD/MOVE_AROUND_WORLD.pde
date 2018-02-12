char k = ' ';

float angle = 0;
int cameraZ = 0;
int phiAngle = 0;
int thetaAngle = 0;

PVector camp;
PVector caml;
PVector camd;
PVector normalY;
 
void setup() {
  size(600, 480, P3D);
  ellipseMode(CENTER);
  rectMode(CENTER);
  lights();
  frameRate(200);
  //smooth(8);
  float camAngle = PI/6;

  
  camp = new PVector(width/2.0, height/2.0, (height/2.0) / tan(PI*30.0 / 180.0));
  caml = new PVector(width/2.0, height/2.0, 0);
  camd = PVector.sub(caml, camp);
  normalY = new PVector(1,0,0);
    
  camera(camp.x,camp.y,camp.z, camp.x+camd.x,camp.y+camd.y,camp.z+camd.z, 0,1,0);
}

void draw() {
  
  moveAround(k); //k is modified in keyPressed and keyReleased functions to get a continuous movement experience
  camera(camp.x,camp.y,camp.z, camp.x+camd.x,camp.y+camd.y,camp.z+camd.z, 0,1,0);
   
  background(0); //Needed to draw objects anew every time camera was moved, otherwise objects will stay on screen
  //This is because the camera-movement refers only to what is drawn in the future!
  
  //Put some objects into the world.
      rect(width/4,height/4,200,200);
      pushMatrix();
      translate(500, height*0.35, -200);
      noFill();
      stroke(255);
      sphere(280);
      popMatrix();
      pushMatrix();
      translate(130, height/2, 0);
      rotateY(1.25);
      rotateX(-0.4);
      box(100);
      popMatrix();
}
 

void keyReleased(){
  k = ' ';
}

void keyPressed() {
  if(key == 'w') { // shall mean zooming in
    k = 'w';
  }
  else if(key == 's') {
    k = 's';
  }
  else if(key == 'a') {
    k = 'a';
  }
  else if(key == 'd') {
    k = 'd';
  }
  if(keyCode == LEFT) {
    k = 'l';
  }
  else if(keyCode == RIGHT) {
    k = 'r';
  }
  if(keyCode == UP) {
    k = 'o';
  }
  else if(keyCode == DOWN) {
    k = 'u';
  }
}
void moveAround(char c) {
  if(c == 'w') { // shall mean zooming in
    camp = go(camp,camd,10);
  }
  else if(c == 's') {
    camp = go(camp,camd,-10);
  }
  else if(c == 'a') {
    //camd = left(camd,-1);
  }
  else if(c == 'd') {
    //camd = right(camd,-1);
  }
  else if(c == 'l') {
    camd = rotY(camd,-1);
  }
  else if(c == 'r') {
    camd = rotY(camd,1);
  }
  if(c == 'o') {
    camd = rotX(camd,1);
  }
  else if(c == 'u') {
    camd = rotX(camd,-1);
  }
  else {
  }
}

PVector go(PVector cp, PVector cd,int steps) {
  int steplength = 1;
  if (steps < 0)
  {
    steps = -steps;
    PVector d = cd.normalize().mult(steplength*steps);
    cp = cp.sub(d);
  }
  else
  {
    PVector d = cd.normalize().mult(steplength*steps);
    cp = cp.add(d);
  }
  return cp;
}
 
PVector rotX(PVector cd, float angle) {
  //rotation matrix around X axis:
  //(1  0   0   )
  //(0  cos -sin)
  //(0  sin cos )
  cd.y = cos(radians(angle))*cd.y-sin(radians(angle))*cd.z;
  cd.z = sin(radians(angle))*cd.y+cos(radians(angle))*cd.z;
  normalY.y = cos(radians(angle))*normalY.y-sin(radians(angle))*normalY.z;
  normalY.z = sin(radians(angle))*normalY.y+cos(radians(angle))*normalY.z;
  
  return cd;
}
PVector rotY(PVector cd, float angle) {
  //rotation matrix around Y axis:
  //(cos 0 -sin)
  //(0   1  0  )
  //(sin 0 cos )
  cd.x = cos(radians(angle))*cd.x-sin(radians(angle))*cd.z;
  cd.z = sin(radians(angle))*cd.x+cos(radians(angle))*cd.z;
  normalY.x = cos(radians(angle))*normalY.x-sin(radians(angle))*normalY.z;
  normalY.z = sin(radians(angle))*normalY.x+cos(radians(angle))*normalY.z;
  
  return cd;
}
PVector rotZ(PVector cd, float angle) {
  //rotation matrix around Z axis:
  //(cos -sin 0)
  //(sin cos  0)
  //(0   0    1)
  cd.x = cos(radians(angle))*cd.x-sin(radians(angle))*cd.y;
  cd.y = sin(radians(angle))*cd.x+cos(radians(angle))*cd.y;
  return cd;
}