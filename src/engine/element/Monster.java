package engine.element;

import engine.map.Block;

public class Monster extends Object {

    public Monster(Block position, int type) {
        super(position);
        this.type = type;
    }

    private int type;   // [0,1,2,3]


    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


}
