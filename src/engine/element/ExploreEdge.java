package engine.element;

// comment
// chinese : 一个用于规定人物自己的探索边界的类
// french : Une classe pour spécifier les limites d'exploration du personnage.
public class ExploreEdge {

    private int startX;

    private int startY;

    private int endX;

    private int endY;

    public ExploreEdge(int startX, int startY, int endX, int endY){
        this.startX = startX;
        this.startY = startY;
        this.endX = endX;
        this.endY = endY;
    }
    public int getEndY() {
        return endY;
    }

    public void setEndY(int endY) {
        this.endY = endY;
    }

    public int getEndX() {
        return endX;
    }

    public void setEndX(int endX) {
        this.endX = endX;
    }

    public int getStartY() {
        return startY;
    }

    public void setStartY(int startY) {
        this.startY = startY;
    }

    public int getStartX() {
        return startX;
    }

    public void setStartX(int startX) {
        this.startX = startX;
    }
}
