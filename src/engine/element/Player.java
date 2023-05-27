package engine.element;

import engine.map.Block;

// comment
// chinese : 人物类
// french : class ‘player’
public class Player extends Object {
    public Player(int type, Block position) {
        super(position);
        this.type = type;
    }

    public int type;

    public int getType(){
        return this.type;
    }

    public void setType(int type){
        this.type = type;
    }



}
