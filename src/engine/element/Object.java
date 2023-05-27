package engine.element;

import engine.map.Block;

// comment
// chinese : element包下所有类的基类
// french : Classe de base pour toutes les classes du paquet "element".
public abstract class Object {

    private Block position;

    public Object(Block position) {
        this.position = position;
    }

    public Block getPosition() {
        return position;
    }

    public void setPosition(Block position) {
        this.position = position;
    }
}
