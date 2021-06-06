//Zak Haider

import java.awt.Graphics;
import java.util.LinkedList;

public class Handler {
	
	LinkedList<GameObject> object = new LinkedList<GameObject>();
	
	public void tick() {
		for(int i=0; i<object.size(); i++) {
			GameObject obj = object.get(i);
			obj.tick();
		}
	}
	
	public void render(Graphics g) {
		for(int i=0; i<object.size(); i++) {
			GameObject obj = object.get(i);
			obj.paintComponent(g);
		}
	}
	
	public void addObject(GameObject object) {
		this.object.add(object);
	}
	
	public void removeObject(GameObject object) {
		this.object.remove(object);
	}
}
