/*
An example which draws a Bezier surface.
Adapted from http://nehe.gamedev.net/
*/

import javax.media.opengl.*;
import com.sun.opengl.util.*; 
import java.awt.event.*;
import javax.swing.*;
import java.nio.*;


private GLU glu;
  private GLUT glut;
  private GLCapabilities caps;
  private GLCanvas canvas; 
  // as from C version of file
  private static final float ctrlpoints[][][] = new float[][][]
  {
  {
  { -1.5f, -1.5f, 4.0f },
  { -0.5f, -1.5f, 2.0f },
  { 0.5f, -1.5f, -1.0f },
  { 1.5f, -1.5f, 2.0f } },
  {
  { -1.5f, -0.5f, 1.0f },
  { -0.5f, -0.5f, 3.0f },
  { 0.5f, -0.5f, 0.0f },
  { 1.5f, -0.5f, -1.0f } },
  {
  { -1.5f, 0.5f, 4.0f },
  { -0.5f, 0.5f, 0.0f },
  { 0.5f, 0.5f, 3.0f },
  { 1.5f, 0.5f, 4.0f } },
  {
  { -1.5f, 1.5f, -2.0f },
  { -0.5f, 1.5f, -2.0f },
  { 0.5f, 1.5f, 0.0f },
  { 1.5f, 1.5f, -1.0f } } };
  
  
  public void setup(GL gl,GLU glu,GLUT glut){
  	gl.glEnable(GL.GL_MAP2_VERTEX_3);
    gl.glEnable(GL.GL_AUTO_NORMAL);
    gl.glEnable(GL.GL_NORMALIZE);
    gl.glMap2f(GL.GL_MAP2_VERTEX_3, 0, 1, 3, 4, 0, 1, 12, 4, ctrlpointsBuf);
    gl.glMapGrid2f(20, 0.0f, 1.0f, 20, 0.0f, 1.0f);
   
  }
  
public void ecomain(GL gl,GLU glu,GLUT glut){
	 float no_mat[] =
    { 0.0f, 0.0f, 0.0f, 1.0f };
    float mat_ambient[] =
    { 0.7f, 0.7f, 0.7f, 1.0f };
    float mat_ambient_color[] =
    { 0.8f, 0.8f, 0.2f, 1.0f };
    float mat_diffuse[] =
    { 0.1f, 0.5f, 0.8f, 1.0f };
    float mat_specular[] =
    { 1.0f, 1.0f, 1.0f, 1.0f };
    float no_shininess[] =
    { 0.0f };
    float low_shininess[] =
    { 5.0f };
    float high_shininess[] =
    { 100.0f };
    float mat_emission[] =
    { 0.3f, 0.2f, 0.2f, 0.0f };
// Please enter your OpenGL code here
 gl.glPushMatrix();
 gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, no_mat, 0);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, mat_diffuse, 0);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, mat_specular, 0);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, low_shininess, 0);
    gl.glMaterialfv(GL.GL_FRONT, GL.GL_EMISSION, no_mat, 0);
    gl.glRotatef(85.0f, 1.0f, 1.0f, 1.0f);
    gl.glScaled(100, 100, 100);
    gl.glEvalMesh2(GL.GL_FILL, 0, 20, 0, 20);
    gl.glPopMatrix();



}
 private FloatBuffer ctrlpointsBuf = BufferUtil
      .newFloatBuffer(ctrlpoints.length * ctrlpoints[0].length
                      * ctrlpoints[0][0].length);
  {// SO copy 4x4x3 array above to float buffer
    for (int i = 0; i < ctrlpoints.length; i++)
    {
      // System.out.print(ctrlpoints.length+ " ");
      for (int j = 0; j < ctrlpoints[0].length; j++)
      {
        // System.out.println(ctrlpoints[0][0].length+" ");
        for (int k = 0; k < ctrlpoints[0][0].length; k++)
        {
          ctrlpointsBuf.put(ctrlpoints[i][j][k]);
          System.out.print(ctrlpoints[i][j][k] + " ");
        }
        System.out.println();
      }
    }
    // THEN rewind it before use
    ctrlpointsBuf.rewind();
  }