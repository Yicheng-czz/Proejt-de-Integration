package gui;

import engine.element.Monster;
import engine.element.Player;
import engine.element.Treasure;
import engine.map.Map;
import engine.process.ElementManager;
import engine.process.PlayerManager;
import javax.swing.*;
import java.awt.*;
import java.util.List;

// 主要用来画各种东西
public class GameDisplay extends JPanel {

    private Map map;
    private ElementManager elementManager;
    private PaintStrategy paintStrategy = new PaintStrategy();

    public GameDisplay(Map map, ElementManager elementManager) {
        this.map = map;
        this.elementManager = elementManager;
    }

    // 画(Peinture)
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 画地图
        // Dessiner la carte
        paintStrategy.paint(map, g);

        // 画宝藏
        List<Treasure> treasures = elementManager.getTreasureList();
        for(Treasure treasure : treasures){
            paintStrategy.paint(treasure,g);
        }

        // 画人物
        List<PlayerManager> players = elementManager.getPlayerManagers();
        for(PlayerManager playerManager : players){
            Player player = playerManager.getPlayer();
            paintStrategy.paint(player, g);
        }

        // 画怪兽
        List<Monster> monsters = elementManager.getMonsterList();
        for(Monster monster : monsters){
            paintStrategy.paint(monster,g);
        }

    }





}
