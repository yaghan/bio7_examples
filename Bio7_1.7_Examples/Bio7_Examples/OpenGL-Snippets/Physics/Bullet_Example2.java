import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;
import com.bulletphysics.BulletGlobals;
import com.bulletphysics.BulletStats;
import com.bulletphysics.ContactAddedCallback;
import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.CollisionFlags;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.narrowphase.ManifoldPoint;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.BvhTriangleMeshShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.TriangleIndexVertexArray;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.Transform;
import javax.vecmath.Vector3f;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import static com.eco.bio7.spatial.SpatialEvents.*;
import static javax.media.opengl.GL2.*;

/*
This example demonstrates the use of the JBullet physics library
(http://jbullet.advel.cz/)in Bio7.

For an manual and overview see
http://www.bulletphysics.com/Bullet/wordpress/

Invoke the setup and execution method to see the result!

A double click with the mouse starts the animation of the
ground plane (unfinished example)!
*/


// keep the collision shapes, for deletion/cleanup
    public DiscreteDynamicsWorld dynamicsWorld;
	private List collisionShapes = new ArrayList();
	private TriangleIndexVertexArray indexVertexArrays;
	private BroadphaseInterface broadphase;
	private CollisionDispatcher dispatcher;
	private ConstraintSolver solver;
	private DefaultCollisionConfiguration collisionConfiguration;
	private boolean animatedMesh = false;
	private static ByteBuffer gVertices;
	private static ByteBuffer gIndices;
	private static BvhTriangleMeshShape trimeshShape;
	private static RigidBody staticBody;
	private static float waveheight = 10.f;
	private static final float TRIANGLE_SIZE=30.f;
	private static int NUM_VERTS_X = 30;
	private static int NUM_VERTS_Y = 30;
	private static int totalVerts = NUM_VERTS_X*NUM_VERTS_Y;

public Vector3f vec;
public float []no_mat = { 0.0f, 0.0f, 0.0f, 1.0f };
public float []fBrightLight = { 1.0f, 1.0f, 1.0f, 1.0f };
public double [] c1=new double[12];
public double [] c2=new double[12];
public double [] c3=new double[12];


public void setup(GL2 gl, GLU glu, GLUT glut) {
	
	animatedMesh = false;
	gl.glLightModeli(GL_LIGHT_MODEL_COLOR_CONTROL,GL_SEPARATE_SPECULAR_COLOR);
	
	/*Here we initialize the physics!*/
	initPhysics(gl);

	int numObjects = dynamicsWorld.getNumCollisionObjects();
	//System.out.println(numObjects);

}

public void ecomain(GL2 gl, GLU glu, GLUT glut) {
	drawTriangle(gl);

	if (isMouseDoubleClicked()) {

		if (animatedMesh) {
			animatedMesh = false;
		} else {
			animatedMesh = true;
		}

	}

	if (animatedMesh) {

		offset += 0.01f;
		float waveheight = (float) Math.random() * 10;

		setVertexPositions(waveheight, offset, gl);

		trimeshShape.refitTree(null,null);

		// clear all contact points involving mesh proxy. Note: this is a slow/unoptimized operation.
		dynamicsWorld.getBroadphase().getOverlappingPairCache()
			.cleanProxyFromPairs(
				staticBody.getBroadphaseHandle(),
				dynamicsWorld.getDispatcher());

		//BulletGlobals.updateTime = (System.nanoTime() - t0) / 1000000;
	}

	if (dynamicsWorld != null) {
		dynamicsWorld.stepSimulation(1f / 30f, 0);
		int numObjects = dynamicsWorld.getNumCollisionObjects();
		//System.out.println(numObjects);
		for (int i = 0; i < numObjects; i++) {

			CollisionObject colObj = (CollisionObject) dynamicsWorld
				.getCollisionObjectArray().get(i);
			RigidBody body = RigidBody.upcast(colObj);

			/*Here i draw the shapes from the position of the physics shapes!*/
			vec = body.getCenterOfMassPosition(new Vector3f());

			if (i > 1) {
				/*Here we draw the shapes with the GL context!!!!!*/
				gl.glPushMatrix();
				gl.glTranslated(vec.x, vec.y, vec.z);
				gl.glEnable(GL_COLOR_MATERIAL);
				gl.glColorMaterial(GL.GL_FRONT, GL_AMBIENT_AND_DIFFUSE);
				gl.glMaterialfv(GL.GL_FRONT, GL_SPECULAR, fBrightLight, 0);
				gl.glMateriali(GL.GL_FRONT, GL_SHININESS, 110);
				gl.glColor4d(0.5f, 0, 0, 1.0f);
				glut.glutSolidCube(20);
				gl.glPopMatrix();
			}

		}
	}
}
public void drawTriangle(GL2 gl) {
	int i;
	int j;
	boolean oben = true;

	gl.glBegin(GL.GL_TRIANGLES);

	Vector3f tmp = new Vector3f();
	for (i = 0; i < NUM_VERTS_X; i++) {
		for (j = 0; j < NUM_VERTS_Y; j++) {

			int index = i + j * NUM_VERTS_X;
				//obere Seite
			gl.glColor3f(1,1,0);
			gl.glVertex3f(
				(i - NUM_VERTS_X * 0.5f) * TRIANGLE_SIZE,
				(j - NUM_VERTS_Y * 0.5f) * TRIANGLE_SIZE,
				waveheight
					* (float) Math.sin((float) i + offset)
					* (float) Math.cos((float) j + offset));

			gl.glVertex3f(
				(i+1 - NUM_VERTS_X * 0.5f) * TRIANGLE_SIZE,
				(j - NUM_VERTS_Y * 0.5f) * TRIANGLE_SIZE,
				waveheight
					* (float) Math.sin((float) i+1 + offset)
					* (float) Math.cos((float) j + offset));
					
			gl.glVertex3f(
				(i - NUM_VERTS_X * 0.5f) * TRIANGLE_SIZE,
				(j+1 - NUM_VERTS_Y * 0.5f) * TRIANGLE_SIZE,
				waveheight
					* (float) Math.sin((float) i + offset)
					* (float) Math.cos((float) j+1 + offset));	
				//untere Seite		
			gl.glColor3f(0,1,0);
			gl.glVertex3f(
				(i+1 - NUM_VERTS_X * 0.5f) * TRIANGLE_SIZE,
				(j - NUM_VERTS_Y * 0.5f) * TRIANGLE_SIZE,
				waveheight
					* (float) Math.sin((float) i+1 + offset)
					* (float) Math.cos((float) j + offset));

			gl.glVertex3f(
				(i+1 - NUM_VERTS_X * 0.5f) * TRIANGLE_SIZE,
				(j+1 - NUM_VERTS_Y * 0.5f) * TRIANGLE_SIZE,
				waveheight
					* (float) Math.sin((float) i+1 + offset)
					* (float) Math.cos((float) j+1 + offset));
					
			gl.glVertex3f(
				(i - NUM_VERTS_X * 0.5f) * TRIANGLE_SIZE,
				(j+1 - NUM_VERTS_Y * 0.5f) * TRIANGLE_SIZE,
				waveheight
					* (float) Math.sin((float) i + offset)
					* (float) Math.cos((float) j+1 + offset));			
		}
	}
	gl.glEnd();
}

public void setVertexPositions(float waveheight, float offset,GL gl) {
		int i;
		int j;
		Vector3f tmp = new Vector3f();

		for (i = 0; i < NUM_VERTS_X; i++) {
			for (j = 0; j < NUM_VERTS_Y; j++) {
				/*The positions of the triangles are set here in the x-y plane.(original was x-z plane)!*/
				tmp.set(
						(i - NUM_VERTS_X * 0.5f) * TRIANGLE_SIZE,(j - NUM_VERTS_Y * 0.5f) * TRIANGLE_SIZE,waveheight * (float) Math.sin((float) i + offset) * (float) Math.cos((float) j + offset));

				int index = i + j * NUM_VERTS_X;
				gVertices.putFloat((index*3 + 0) * 4, tmp.x);
				gVertices.putFloat((index*3 + 1) * 4, tmp.y);
				gVertices.putFloat((index*3 + 2) * 4, tmp.z);
				
			}
		}
	}
	
public void initPhysics(GL gl) {
		

		BulletGlobals.setContactAddedCallback(new CustomMaterialCombinerCallback());

		
		int vertStride = 3 * 4;
		int indexStride = 3 * 4;

		int totalTriangles = 2 * (NUM_VERTS_X - 1) * (NUM_VERTS_Y - 1);

		gVertices = ByteBuffer.allocateDirect(totalVerts * 3 * 4).order(ByteOrder.nativeOrder());
		gIndices = ByteBuffer.allocateDirect(totalTriangles * 3 * 4).order(ByteOrder.nativeOrder());

		int i;

		setVertexPositions(waveheight, 0.f, gl);

		//int index=0;
		gIndices.clear();
		for (i = 0; i < NUM_VERTS_X - 1; i++) {
			for (int j = 0; j < NUM_VERTS_Y - 1; j++) {
				gIndices.putInt(j * NUM_VERTS_X + i);
				gIndices.putInt(j * NUM_VERTS_X + i + 1);
				gIndices.putInt((j + 1) * NUM_VERTS_X + i + 1);

				gIndices.putInt(j * NUM_VERTS_X + i);
				gIndices.putInt((j + 1) * NUM_VERTS_X + i + 1);
				gIndices.putInt((j + 1) * NUM_VERTS_X + i);
			}
		}
		gIndices.flip();

		indexVertexArrays = new TriangleIndexVertexArray(totalTriangles,
				gIndices,
				indexStride,
				totalVerts, gVertices, vertStride);

		boolean useQuantizedAabbCompression = true;

		
		trimeshShape = new BvhTriangleMeshShape(indexVertexArrays, useQuantizedAabbCompression);
		collisionShapes.add(trimeshShape);

		CollisionShape groundShape = trimeshShape;
	
		collisionConfiguration = new DefaultCollisionConfiguration();
		
		dispatcher = new CollisionDispatcher(collisionConfiguration);

		Vector3f worldMin = new Vector3f(-1000f, -1000f, -1000f);
		Vector3f worldMax = new Vector3f(1000f, 1000f, 1000f);
		//broadphase = new AxisSweep3(worldMin, worldMax);
		broadphase = new AxisSweep3(worldMin, worldMax);
		solver = new SequentialImpulseConstraintSolver();
		dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, broadphase, solver, collisionConfiguration);
		
		/*Changed the gravity settings (in z-direction!)*/
		dynamicsWorld.setGravity(new Vector3f(0f, 0.0f, -60.0f));
						
		float mass = 0f;
		Transform startTransform = new Transform();
		startTransform.setIdentity();
		startTransform.origin.set(0f, 0f, 0f);

		CollisionShape colShape = new BoxShape(new Vector3f(20f, 20f, 20f));
		collisionShapes.add(colShape);

		{
			for (i = 0; i < 10; i++) {
				//btCollisionShape* colShape = new btCapsuleShape(0.5,2.0);//boxShape = new btSphereShape(1.f);
				/*Here the position of the Spheres at start!*/
				startTransform.origin.set(50f * i, 10f, 1000f);
				localCreateRigidBody(1f, startTransform, colShape);
			}
		}

		startTransform.setIdentity();
		staticBody = localCreateRigidBody(mass, startTransform, groundShape);
		
		staticBody.setCollisionFlags(staticBody.getCollisionFlags() | CollisionFlags.STATIC_OBJECT);
		// enable custom material callback
		staticBody.setCollisionFlags(staticBody.getCollisionFlags() | CollisionFlags.CUSTOM_MATERIAL_CALLBACK);
	}
	
	private static float offset = 0f;
	
	private static float calculateCombinedFriction(float friction0, float friction1) {
		float friction = friction0 * friction1;

		float MAX_FRICTION = 10f;
		if (friction < -MAX_FRICTION) {
			friction = -MAX_FRICTION;
		}
		if (friction > MAX_FRICTION) {
			friction = MAX_FRICTION;
		}
		return friction;
	}

	private static float calculateCombinedRestitution(float restitution0, float restitution1) {
		return restitution0 * restitution1;
	}
	
	private static class CustomMaterialCombinerCallback extends ContactAddedCallback {
		public boolean contactAdded(ManifoldPoint cp, CollisionObject colObj0, int partId0, int index0, CollisionObject colObj1, int partId1, int index1) {
			float friction0 = colObj0.getFriction();
			float friction1 = colObj1.getFriction();
			float restitution0 = colObj0.getRestitution();
			float restitution1 = colObj1.getRestitution();

			if ((colObj0.getCollisionFlags() & CollisionFlags.CUSTOM_MATERIAL_CALLBACK) != 0) {
				friction0 = 1f; //partId0,index0
				restitution0 = 0f;
			}
			if ((colObj1.getCollisionFlags() & CollisionFlags.CUSTOM_MATERIAL_CALLBACK) != 0) {
				if ((index1 & 1) != 0) {
					friction1 = 1f; //partId1,index1
				}
				else {
					friction1 = 0f;
				}
				restitution1 = 0f;
			}

			cp.combinedFriction = calculateCombinedFriction(friction0, friction1);
			cp.combinedRestitution = calculateCombinedRestitution(restitution0, restitution1);

			// this return value is currently ignored, but to be on the safe side: return false if you don't calculate friction
			return true;
		}
	}
	
	/*Original method from the DemoApplication class! */
	public RigidBody localCreateRigidBody(float mass, Transform startTransform, CollisionShape shape) {
		// rigidbody is dynamic if and only if mass is non zero, otherwise static
		boolean isDynamic = (mass != 0f);

		Vector3f localInertia = new Vector3f(0f, 0f, 0f);
		if (isDynamic) {
			shape.calculateLocalInertia(mass, localInertia);
		}

		// using motionstate is recommended, it provides interpolation capabilities, and only synchronizes 'active' objects

		//#define USE_MOTIONSTATE 1
		//#ifdef USE_MOTIONSTATE
		DefaultMotionState myMotionState = new DefaultMotionState(startTransform);
		
		RigidBodyConstructionInfo cInfo = new RigidBodyConstructionInfo(mass, myMotionState, shape, localInertia);
		
		RigidBody body = new RigidBody(cInfo);
		//#else
		//btRigidBody* body = new btRigidBody(mass,0,shape,localInertia);	
		//body->setWorldTransform(startTransform);
		//#endif//
		
		dynamicsWorld.addRigidBody(body);

		return body;
	}
	

	
