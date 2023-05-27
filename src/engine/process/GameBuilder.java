package engine.process;

import config.GameConfiguration;
import engine.element.*;
import engine.map.Block;
import engine.map.Map;

import java.io.*;

public class GameBuilder {

    // 搭建地图
    public static Map buildMap(String filePath){
        Map map = new Map(GameConfiguration.LINE_COUNT, GameConfiguration.COLUMN_COUNT);
        FileInputStream fin = null;
        try{
            fin = new FileInputStream(filePath);
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }

        assert fin != null;
        InputStreamReader reader = new InputStreamReader(fin);
        BufferedReader buffReader = new BufferedReader(reader);
        String strTmp = "";
        int i = 0;
        try{
            while((strTmp = buffReader.readLine())!=null){
                String[] arr = strTmp.split(" +");
                for(int j = 0; j < GameConfiguration.COLUMN_COUNT; j++){
                    map.getBlock(i,j).setTile(Integer.parseInt(arr[j]));
                }
                ++i;
            }
            buffReader.close();
        }catch (IOException e){
            e.printStackTrace();
        }
        return map;
    }

    // 初始化 物体
    public static ElementManager buildElement(Map map) {
        // 创建ElementManager
        ElementManager manager = new ElementManager(map);
        // 初始化任务
        initializeMissions(manager);
        // 初始化宝藏
        initializeTreasures(manager);
        // 初始化怪物
        initializeMonsters(manager);
        // 初始化人物
        initializePlayers(manager);

        return manager;
    }

    private static void initializePlayers(ElementManager manager) {

        Block block = new Block(23,1);
        PlayerManager playerManager = new PlayerManager(new Player(0,block),manager.getMissionQueue(),manager.getLock(),manager.getMonsterList(), manager.getMap(), manager.getTreasureList(),manager.getMutex(), manager.getHelpAsks());

        Block block1 = new Block(24, 1);
        PlayerManager playerManager1 = new PlayerManager(new Player(1,block1),manager.getMissionQueue(),manager.getLock(),manager.getMonsterList(), manager.getMap(), manager.getTreasureList(),manager.getMutex(), manager.getHelpAsks());

        Block block2 = new Block(23, 3);
        PlayerManager playerManager2 = new PlayerManager(new Player(2,block2),manager.getMissionQueue(),manager.getLock(),manager.getMonsterList(), manager.getMap(), manager.getTreasureList(),manager.getMutex(), manager.getHelpAsks());

        Block block3 = new Block(24, 3);
        PlayerManager playerManager3 = new PlayerManager(new Player(3,block3),manager.getMissionQueue(),manager.getLock(),manager.getMonsterList(), manager.getMap(), manager.getTreasureList(),manager.getMutex(), manager.getHelpAsks());

        // 将初始化好的人物放进列表中
        // Mise en place des players initialisés dans la liste
        manager.addPlayerManager(playerManager);
        manager.addPlayerManager(playerManager1);
        manager.addPlayerManager(playerManager2);
        manager.addPlayerManager(playerManager3);

    }

    private static void initializeTreasures(ElementManager manager){
        // area 1
        Treasure treasure1 = new Treasure(new Block(5,3),1);
        // area 2
        Treasure treasure2 = new Treasure(new Block(4,36),2);
        // area 3
        Treasure treasure3 = new Treasure(new Block(13,85),3);
        // area 4
        Treasure treasure4 = new Treasure(new Block(29,12),4);
        // area 5
        Treasure treasure5 = new Treasure(new Block(38,32),5);
        // area 6
        Treasure treasure6 = new Treasure(new Block(43,86),6);
        manager.getTreasureList().add(treasure1);
        manager.getTreasureList().add(treasure2);
        manager.getTreasureList().add(treasure3);
        manager.getTreasureList().add(treasure4);
        manager.getTreasureList().add(treasure5);
        manager.getTreasureList().add(treasure6);
    }


    private static void initializeMissions(ElementManager manager){

        // area 1
        Block enter1 = new Block(19,5);
        ExploreEdge exploreEdge1 = new ExploreEdge(1,1,19,27);
        Mission mission1 = new Mission(enter1,exploreEdge1,0,1);

        // area 2
        Block enter2 = new Block(19,53);
        ExploreEdge exploreEdge2 = new ExploreEdge(1,30,19,57);
        Mission mission2 = new Mission(enter2,exploreEdge2,0,2);

        // area 3
        Block enter3 = new Block(19,86);
        ExploreEdge exploreEdge3 = new ExploreEdge(1,59,19,88);
        Mission mission3 = new Mission(enter3,exploreEdge3,0,3);

        // area 4
        Block enter4 = new Block(27,17);
        ExploreEdge exploreEdge4 = new ExploreEdge(27,1,46,27);
        Mission mission4 = new Mission(enter4,exploreEdge4,0,4);

        // area 5
        Block enter5 = new Block(27,53);
        ExploreEdge exploreEdge5 = new ExploreEdge(27,30,46,57);
        Mission mission5 = new Mission(enter5,exploreEdge5,0,5);

        // area 6
        Block enter6 = new Block(27,72);
        ExploreEdge exploreEdge6 = new ExploreEdge(27,59,46,89);
        Mission mission6 = new Mission(enter6,exploreEdge6,0,6);

        manager.getMissionQueue().add(mission1);
        manager.getMissionQueue().add(mission2);
        manager.getMissionQueue().add(mission3);
        manager.getMissionQueue().add(mission4);
        manager.getMissionQueue().add(mission5);
        manager.getMissionQueue().add(mission6);

    }

    private static void initializeMonsters(ElementManager manager){
        // area 1
        Monster monster1 = new Monster(new Block(12,7),1);
        Monster monster2 = new Monster(new Block(10,25),0);
        Monster monster33 = new Monster(new Block(17,3),3);

        // area 2
        Monster monster6 = new Monster(new Block(4,38),2);
        Monster monster20 = new Monster(new Block(17,52),3);

        // area 3
        Monster monster10 = new Monster(new Block(7,85),0);
        Monster monster11 = new Monster(new Block(4,81),2);
        Monster monster12 = new Monster(new Block(19,80),1);

        // area 4
        Monster monster3 = new Monster(new Block(38,6),1);
        Monster monster4 = new Monster(new Block(31,7),2);

        // area 5
        Monster monster7 = new Monster(new Block(31,53),2);
        Monster monster8 = new Monster(new Block(33,41),0);
        Monster monster9 = new Monster(new Block(42,51),3);

        // area 6
        Monster monster13 = new Monster(new Block(31,88),1);
        Monster monster14 = new Monster(new Block(32,72),1);
        Monster monster15 = new Monster(new Block(44,70),0);

        manager.addMonster(monster1);
        manager.addMonster(monster2);
        manager.addMonster(monster3);
        manager.addMonster(monster4);
        manager.addMonster(monster6);
        manager.addMonster(monster7);
        manager.addMonster(monster8);
        manager.addMonster(monster9);
        manager.addMonster(monster10);
        manager.addMonster(monster11);
        manager.addMonster(monster12);
        manager.addMonster(monster13);
        manager.addMonster(monster14);
        manager.addMonster(monster15);
        manager.addMonster(monster33);
        manager.addMonster(monster20);

    }
}
