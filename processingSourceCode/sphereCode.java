public void sphere(float r) {
    if ((sphereDetailU < 3) || (sphereDetailV < 2)) {
      sphereDetail(30);
    }

    edge(false);


    // 1st ring from south pole
    beginShape(TRIANGLE_STRIP);
    for (int i = 0; i < sphereDetailU; i++) {
      normal(0, -1, 0);
      vertex(0, -r, 0);
      normal(sphereX[i], sphereY[i], sphereZ[i]);
      vertex(r * sphereX[i], r * sphereY[i], r * sphereZ[i]);
    }
    normal(0, -r, 0);
    vertex(0, -r, 0);
    normal(sphereX[0], sphereY[0], sphereZ[0]);
    vertex(r * sphereX[0], r * sphereY[0], r * sphereZ[0]);
    endShape();

    int v1,v11,v2;

    // middle rings
    int voff = 0;
    for (int i = 2; i < sphereDetailV; i++) {
      v1 = v11 = voff;
      voff += sphereDetailU;
      v2 = voff;
      beginShape(TRIANGLE_STRIP);
      for (int j = 0; j < sphereDetailU; j++) {
        normal(sphereX[v1], sphereY[v1], sphereZ[v1]);
        vertex(r * sphereX[v1], r * sphereY[v1], r * sphereZ[v1++]);
        normal(sphereX[v2], sphereY[v2], sphereZ[v2]);
        vertex(r * sphereX[v2], r * sphereY[v2], r * sphereZ[v2++]);
      }
      // close each ring
      v1 = v11;
      v2 = voff;
      normal(sphereX[v1], sphereY[v1], sphereZ[v1]);
      vertex(r * sphereX[v1], r * sphereY[v1], r * sphereZ[v1]);
      normal(sphereX[v2], sphereY[v2], sphereZ[v2]);
      vertex(r * sphereX[v2], r * sphereY[v2], r * sphereZ[v2]);
      endShape();
    }

    // add the northern cap
    beginShape(TRIANGLE_STRIP);
    for (int i = 0; i < sphereDetailU; i++) {
      v2 = voff + i;
      normal(sphereX[v2], sphereY[v2], sphereZ[v2]);
      vertex(r * sphereX[v2], r * sphereY[v2], r * sphereZ[v2]);
      normal(0, 1, 0);
      vertex(0, r, 0);
    }
    normal(sphereX[voff], sphereY[voff], sphereZ[voff]);
    vertex(r * sphereX[voff], r * sphereY[voff], r * sphereZ[voff]);
    normal(0, 1, 0);
    vertex(0, r, 0);
    endShape();

    edge(true);
  }
