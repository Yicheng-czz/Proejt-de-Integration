package engine.process;

import engine.element.Mission;
import engine.element.Monster;
import engine.element.Treasure;
import engine.map.Block;
import engine.map.Map;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;


// comment
// chinese : ElementManager类，将player列表与地图整合到一起（面向对象的性质）
// french : Classe ElementManager, qui intègre la liste des joueurs à la carte (orientée objet par nature).
public class ElementManager {

    private PriorityQueue<Mission> missionQueue = new PriorityQueue<Mission>();

    private List<PlayerManager> playerManagers = new ArrayList<PlayerManager>();

    private List<Monster> monsterList = new ArrayList<>();

    private List<Treasure> treasureList = new ArrayList<>();

    private int[] helpAsks = {0,0,0,0};

    private ReentrantLock lock = new ReentrantLock();

    private Map map;

    private Block mutex = new Block(1,1);

    public ElementManager(Map map){
        this.map = map;
    }

    public ReentrantLock getLock(){
        return this.lock;
    }

    public List<Monster> getMonsterList(){
        return this.monsterList;
    }

    public void setMonsterList(List<Monster> monsterList){
        this.monsterList = monsterList;
    }

    public void addMonster(Monster monster){
        getMonsterList().add(monster);
    }

    public List<Treasure> getTreasureList(){
        return treasureList;
    }

    public List<PlayerManager> getPlayerManagers() {
        return playerManagers;
    }

    public void setPlayerManagers(List<PlayerManager> playerManagers) {
        this.playerManagers = playerManagers;
    }

    public void addPlayerManager(PlayerManager playerManager){
        this.playerManagers.add(playerManager);
    }

    public PriorityQueue<Mission> getMissionQueue(){
        return this.missionQueue;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }

    public Block getMutex() {
        return mutex;
    }

    public void setMutex(Block mutex) {
        this.mutex = mutex;
    }

    public int[] getHelpAsks() {
        return helpAsks;
    }

    public void setHelpAsks(int[] aideAsks) {
        this.helpAsks = aideAsks;
    }
}
