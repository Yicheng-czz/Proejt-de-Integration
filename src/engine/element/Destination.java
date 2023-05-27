package engine.element;

import java.util.HashMap;

import Astar.Node;



public class Destination {
	

//	private volatile HashMap<Integer, Node> destination = new HashMap<Integer, Node>();	
	private HashMap<Integer, Node> destination = new HashMap<Integer, Node>();	
	//Node(x,y)
	public Destination() {
		Node destination1 = new Node(2,2);
		Node destination2 = new Node(3,3);
		Node destination3 = new Node(4,4);
		Node destination4 = new Node(5,5);
		Node destination5 = new Node(20,10);
		Node destination6 = new Node(23,85);
		destination.put(1, destination1);
		destination.put(2, destination2);
		destination.put(3, destination3);
		destination.put(4, destination4);
	    destination.put(5, destination5);
		destination.put(6, destination6);
		
//		System.out.println(destination.get(1).x);
//		System.out.println(destination.get(1).y);
	}
	
	public Node getDestNode(int key){    	
    	return destination.get(key);
    }
    
    public void setDestination(HashMap<Integer, Node> destination) {
    	this.destination = destination;
	}
    

//	public BlockManager getBlockManager(int position) {
//		return blockManagersByPosition.get(position);
//	}
}
