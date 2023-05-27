package Astar;


public class Node {
    public Node(int y, int x) {
        this.x = x;
        this.y = y;
    }

    public int x;
    public int y;

    public int F;
    public int G;
    public int H;

    public void calcF() {
        this.F = this.G + this.H;
    }

    public Node parent;

    @Override
    public String toString() {
        return "(" + y + "," + x + ")";
    }

}
