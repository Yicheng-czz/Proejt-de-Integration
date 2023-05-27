package Astar;

import java.util.ArrayList;
import java.util.List;

import engine.map.Map;
import engine.process.GameBuilder;
import engine.util.ObstacleChecker;


public class AStar {
	
	
	public Map map = GameBuilder.buildMap("src/res/mapInfo/worldmap01.txt");
	public Node startNode;
	public Node endNode;

	public ObstacleChecker obstacle = new ObstacleChecker();
//	
//	TileManager tileM = new TileManager(gp);
//	public int maps[][] = tileM.mapTileNum;
//public	Node startNode = new Node(24,21);
//public	Node endNode = new Node(9,7);
		
	
		
		public AStar(Node startNode, Node endNode) {

			this.startNode = startNode;
			this.endNode = endNode;

			printPaths();
		}
		
		public final int[][] direct = {{0, -1}, {0, 1},{-1, 0}, {1, 0}};
		public final int step = 10;
	    public ArrayList<Node> openList = new ArrayList<Node>();
	    public ArrayList<Node> closeList = new ArrayList<Node>();
	    public int[][] maps = getTilemaps();	    
	    
	    
	    public int[][] getTilemaps(){
				    	
//	    	System.out.println(map.getLineCount());
	    	
	    	int[][] Tilemaps = new int[map.getLineCount()][map.getColumnCount()];
	    	
	    	for (int i = 0; i < map.getLineCount(); i++) {
	    		for (int j = 0; j < map.getColumnCount(); j++) {
	    			Tilemaps[i][j] = map.getBlock(i,j).getTile();				
	    		}
			}
	    	
	    	return Tilemaps;
	    }
	    
	    
	    
	    
	    
	    public Node findMinFNodeInOpneList() {
	        Node tempNode = openList.get(0);
	        for (Node node : openList) {
	            if (node.F < tempNode.F) {
	                tempNode = node;
	            }
	        }
	        return tempNode;
	    }
	    
	    public boolean exists(List<Node> maps, Node node) {
	        for (Node n : maps) {
	            if ((n.x == node.x) && (n.y == node.y)) {
	                return true;
	            }
	        }
	        return false;
	    }
	    public boolean exists(List<Node> maps, int y, int x) {
	        for (Node n : maps) {
	            if ((n.x == x) && (n.y == y)) {
	                return true;
	            }
	        }
	        return false;
	    }
	    
	    
	    public int calcG(Node start, Node node) {
	        int G = step;
	        int parentG = node.parent != null ? node.parent.G : 0;
	        return G + parentG;
	    }

	    public int calcH(Node end, Node node) {
	        int step = Math.abs(node.x - end.x) + Math.abs(node.y - end.y);
	        return step * step;
	    }
	    
	    
	    
	    public boolean canReach(int y, int x) {

//	    	System.out.println(obstacle.isObstacle(maps[y][x]));
	    	if (obstacle.isObstacle(maps[y][x])) {
				return false;
			}
	    	else {
				return true;
			}
	    }
	    
	    
	    
	    public ArrayList<Node> findNeighborNodes(Node currentNode) {
	        ArrayList<Node> arrayList = new ArrayList<Node>();
	        // 只考虑上下左右，不考虑斜对角
	        for (int i = 0; i < 4; i++) {
	            int newX = currentNode.x + direct[i][0];
	            int newY = currentNode.y + direct[i][1];
	            // 如果当前节点的相邻节点，可达（不是障碍位置） 且 不在闭合链表
	            if (canReach(newY,newX) && !exists(openList, newY, newX))
	                arrayList.add(new Node(newY, newX));
	        }

	        return arrayList;
	    }
		
	    
	    public Node find(List<Node> maps, Node point) {
	        for (Node n : maps)
	            if ((n.x == point.x) && (n.y == point.y)) {
	                return n;
	            }
	        return null;
	    }
	    
	    public void foundPoint(Node tempStart, Node node) {
	        int G = calcG(tempStart, node);
	        //途径当前节点tempStart到达该节点node的距离G更小时，更新F
	        if (G < node.G) {
	            node.parent = tempStart;
	            node.G = G;
	            node.calcF();
	        }
	    }

	    public void notFoundPoint(Node tempStart, Node end, Node node) {
	        node.parent = tempStart;
	        node.G = calcG(tempStart, node);
	        node.H = calcH(end, node);
	        node.calcF();
	        openList.add(node);
	    }
	    
	    public Node findPath(Node startNode, Node endNode) {

	        // 把起点加入 open list
	        openList.add(startNode);

	        while (openList.size() > 0) {
	            // 遍历 open list ，查找 F值最小的节点，把它作为当前要处理的节点
	            Node currentNode = findMinFNodeInOpneList();
	            // 从open list中移除
	            openList.remove(currentNode);
	            // 把这个节点移到 close list
	            closeList.add(currentNode);

	            ArrayList<Node> neighborNodes = findNeighborNodes(currentNode);
	            for (Node node : neighborNodes) {
	                //当前节点的相邻界节点已经在开放链表中
	                if (exists(openList, node)) {
	                    foundPoint(currentNode, node);
	                } else {
	                    notFoundPoint(currentNode, endNode, node);
	                }
	            }
	            //终点在开放链表中，则找到路径
	            if (find(openList, endNode) != null) {
	                return find(openList, endNode);
	            }
	        }

	        return find(openList, endNode);
	    }
	    
	    public ArrayList<Node> getPaths(Node endNode) {
	        ArrayList<Node> arrayList = new ArrayList<Node>();
	        Node pre = endNode;
	        while (pre != null) {
	            arrayList.add(new Node(pre.y, pre.x));
	            pre = pre.parent;
	        }
	        return arrayList;
	    }
	    
	    public ArrayList<Node> getParcour(){
	    	
	    	Node parent = findPath(startNode, endNode);
	        ArrayList<Node> arrayList = parent != null ? getPaths(parent) : null;

	    	return arrayList;
	    }
	    
	    
	    public void printPaths() {
	    	          
//	         System.out.println(Tilemaps[2][0]);
//	         System.out.println();
	         
//	        Node parent = findPath(startNode, endNode);
//	        
//	        ArrayList<Node> arrayList = parent != null ? getPaths(parent) : null;
//	    	
//	        System.out.println(arrayList.size());
	    	
//	    	ArrayList<Node> arrayList = getParcour();
//	    	
//	    	for (int i = arrayList.size() - 1; i >= 0; i--) {
//	            if (i == 0)
//	                System.out.print(arrayList.get(i));
//	            else
//	                System.out.print(arrayList.get(i) + "->");
//	        }
//	        System.out.println();
//	        System.out.println("*************");
	    }
}
