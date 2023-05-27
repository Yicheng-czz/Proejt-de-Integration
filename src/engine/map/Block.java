package engine.map;


public class Block {

    private int lig;
    private int col;

    private int tile;

    public Block(int line, int column) {
        this.lig = line;
        this.col = column;
    }

    public int getLine() {
        return lig;
    }

    public int getColumn() {
        return col;
    }

    public int getTile() {
        return tile;
    }

    public void setTile(int tile) {
        this.tile = tile;
    }
}
