package engine.map;

public class Map {

    private Block[][] blocks;

    private int lineCount;

    private int colCount;

    public Map(int lineCount, int colCount){
        this.lineCount = lineCount;
        this.colCount = colCount;

        blocks = new Block[this.lineCount][this.colCount];

        for(int lineIndex = 0;  lineIndex < this.lineCount; lineIndex++){
            for(int colIndex = 0; colIndex < this.colCount; colIndex++){
                blocks[lineIndex][colIndex] = new Block(lineIndex, colIndex);
            }
        }
    }


    public Block[][] getBlocks() {
        return this.blocks;
    }

    public int getLineCount() {
        return this.lineCount;
    }

    public int getColumnCount() {
        return this.colCount;
    }

    public Block getBlock(int line, int col) {
        return this.blocks[line][col];
    }

    public boolean isOnTop(Block block) {
        int line = block.getLine();
        return line == 0;
    }

    public boolean isOnBottom(Block block) {
        int line = block.getLine();
        return line == this.getLineCount() - 1;
    }

    public boolean isOnLeftBorder(Block block) {
        int column = block.getColumn();
        return column == 0;
    }

    public boolean isOnRightBorder(Block block) {
        int column = block.getColumn();
        return column == this.getColumnCount() - 1;
    }

    public boolean isOnBorder(Block block) {
        return isOnTop(block) || isOnBottom(block) || isOnLeftBorder(block) || isOnRightBorder(block);
    }

}
