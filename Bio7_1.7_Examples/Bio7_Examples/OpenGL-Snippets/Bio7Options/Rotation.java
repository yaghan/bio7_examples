/*
This example shows how to rotate the default view!
*/
public float rot=0.0f;

public void ecomain(GL2 gl,GLU glu,GLUT glut){

rot=(rot+0.1f)%360;//One rotation and starts with 0.0f!
SpatialUtil.setRotationZ(rot);
//setRotationX(rot);
//setRotationY(rot);
//System.out.println(getRotationZ());
}