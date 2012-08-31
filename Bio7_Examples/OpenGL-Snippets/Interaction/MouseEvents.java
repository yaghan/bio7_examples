import static com.eco.bio7.spatial.SpatialEvents.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.KeyEvent;

/*
This example demonstrate how to integrate mouse and key events
in the Space view.
This will work if the ecomain method is executed from the 
GL timer! 
Remove comments to try your favorite event!
*/

public void ecomain(GL gl,GLU glu,GLUT glut){
	
	/*if(isMouseDragged()){
		MouseEvent e=getDragEvent();
		int x = e.getX();
		int y = e.getY();
		System.out.println(x+" "+y);
		
	}*/
	if(isMouseWheelMoved()){
		MouseWheelEvent e=getMouseWheelEvent();
		int w=e.getWheelRotation();
		
		System.out.println("moved, value= "+w);
		
	}
	
	/*if(isMouseClicked()){
		
		System.out.println("clicked");
		MouseEvent e=getClickEvent();
		int x = e.getX();
		int y = e.getY();
		System.out.println(x+" "+y);
		
	}*/
	/*if(isMouseDoubleClicked()){
		
		System.out.println("doubleClicked");
		MouseEvent e=getDoubleClickEvent();
		int x = e.getX();
		int y = e.getY();
		System.out.println(x+" "+y);
		
	}*/
	if(isRightMouseClicked()){
		
		System.out.println("RightClick");
		MouseEvent e=getRightClickEvent();
		int x = e.getX();
		int y = e.getY();
		System.out.println(x+" "+y);
		
	}
	if(isMouseTripleClicked()){
		
		System.out.println("TripleClick");
		MouseEvent e=getTripleClickEvent();
		int x = e.getX();
		int y = e.getY();
		System.out.println(x+" "+y);
		
	}
	
	/*if(isMouseMoved()){
		
		System.out.println("MouseMoved");
		MouseEvent e=getMouseMoveEvent();
		int x = e.getX();
		int y = e.getY();
		System.out.println(x+" "+y);
		
	}*/
	
	/*if(isMousePressed()){
		
		System.out.println("pressed");
		MouseEvent e=getPressEvent();
		int x = e.getX();
		int y = e.getY();
		System.out.println(x+" "+y);
		
	}*/
	
	if(isMouseWheelClicked()){
		
		System.out.println("WheelClicked");
		MouseEvent e=getWheelClickEvent();
		int x = e.getX();
		int y = e.getY();
		System.out.println(x+" "+y);
		
	}
	/*if(isMouseEntered()){
		
		System.out.println("MouseEntered");
		
		
	}
	if(isMouseExited()){
		
		System.out.println("MouseExited");
		
		
	}*/
	
	
	
	if(isKeyPressed()){
	KeyEvent e = getKeyPressEvent() ;
	int keyCode=e.getKeyCode();
	System.out.println(keyCode);
			switch (keyCode) {
			case KeyEvent.VK_LEFT:
				System.out.println("Left");
				break;
			case KeyEvent.VK_RIGHT:
				System.out.println("Right");
				break;
			case KeyEvent.VK_A:
				System.out.println("A");
				break;
			
			default:

				break;
			}
	
	}
	/*if(isKeyTyped()){
	KeyEvent e = getKeyTypedEvent() ;
	int keyCode=e.getKeyChar();
	System.out.println(keyCode);
			switch (keyCode) {
			case KeyEvent.VK_LEFT:
				System.out.println("Left");
				break;
			case KeyEvent.VK_RIGHT:
				System.out.println("Right");
				break;
			case KeyEvent.VK_A:
				System.out.println("A");
				break;
			
			default:

				break;
			}
	
	}*/
	/*if(isKeyReleased()){
	KeyEvent e = getKeyReleaseEvent() ;
	int keyCode=e.getKeyCode();
	
			switch (keyCode) {
			
			case KeyEvent.VK_A:
				System.out.println("A");
				break;
			
			default:

				break;
			}
	
	}*/
}