package engine.element;

import engine.map.Block;

public class Treasure extends Object{

    private int areaId;

    public Treasure(Block position, int areaId) {
        super(position);
        this.areaId = areaId;
    }

    public int getAreaId() {
        return areaId;
    }
    
//    public Block getEnter() {
//        return position;
//    }

}
