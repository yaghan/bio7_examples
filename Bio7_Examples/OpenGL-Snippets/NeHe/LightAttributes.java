/*
An example which adjusts some material and light attributes!
Adapted from http://nehe.gamedev.net/
*/
 float []no_mat =
    { 0.0f, 0.0f, 0.0f, 1.0f };
    float[] mat_ambient =
    { 0.7f, 0.7f, 0.7f, 1.0f };
    float []mat_ambient_color =
    { 0.8f, 0.8f, 0.2f, 1.0f };
    float []mat_diffuse =
    { 0.1f, 0.5f, 0.8f, 1.0f };
    float []mat_specular =
    { 1.0f, 1.0f, 1.0f, 1.0f };
    float []no_shininess =
    { 0.0f };
    float []low_shininess =
    { 5.0f };
    float []high_shininess =
    { 100.0f };
    float [] mat_emission =
    { 0.3f, 0.2f, 0.2f, 0.0f }; 
      
public void ecomain(GL gl, GLU glu, GLUT glut) {

	gl.glMaterialfv(GL.GL_FRONT, GL.GL_AMBIENT, no_mat, 0);
	gl.glMaterialfv(GL.GL_FRONT, GL.GL_DIFFUSE, mat_diffuse, 0);
	gl.glMaterialfv(GL.GL_FRONT, GL.GL_SPECULAR, no_mat, 0);
	gl.glMaterialfv(GL.GL_FRONT, GL.GL_SHININESS, no_shininess, 0);
	gl.glMaterialfv(GL.GL_FRONT, GL.GL_EMISSION, no_mat, 0);

}