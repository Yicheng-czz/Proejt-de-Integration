package gui;

import config.GameConfiguration;
import engine.element.Monster;
import engine.element.Treasure;
import engine.map.Block;
import engine.map.Map;
import engine.element.Player;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class PaintStrategy {

    private Image[] tileArr = new Image[51];    // 51 tiles
    private Image[] playerArr = new Image[4];   // 4 players
    private Image treasureRes;
    private Image[] monsterArr = new Image[4];   // 4 monsters

    public PaintStrategy(){
        // comment
        // chinese : 加载贴片数组
        // french : Chargement du tableau des tuiles
        for(int i = 0;i < 51;i++) {
            try{
                if(i < 10) {
                    assert false;
                    tileArr[i] = ImageIO.read(new File("src/res/tileRes/00"+i+ ".png"));
                }
                else {
                    assert false;
                    tileArr[i] = ImageIO.read(new File("src/res/tileRes/0"+i+ ".png"));
                }
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        // comment
        // chinese : 读取人物贴片数组
        // french : Chargement du tableau des players
        for(int i = 0;i<4;i++){
            try{
                assert false;
                playerArr[i] = ImageIO.read(new File("src/res/playerRes/"+i+".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // comment
        // chinese : 读取怪兽贴片数组
        // french : Chargement du tableau des monstres
        for(int i = 0; i < 4;i++){
            try{
                assert false;
                monsterArr[i] = ImageIO.read(new File("src/res/monsterRes/"+i+".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        // comment
        // chinese : 读取宝藏贴片
        // french : Chargement de tuile de tresor
        try{
            assert false;
            treasureRes = ImageIO.read(new File("src/res/treasureRes/chest.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    // 画地图
    public void paint(Map map, Graphics graphics) {

        int blockSize = GameConfiguration.BLOCK_SIZE;
        Block[][] blocks = map.getBlocks();

        for (int lineIndex = 0; lineIndex < map.getLineCount(); lineIndex++) {
            for (int columnIndex = 0; columnIndex < map.getColumnCount(); columnIndex++) {
                Block block = blocks[lineIndex][columnIndex];
                Image ima = this.tileArr[block.getTile()];

                graphics.drawImage(ima,block.getColumn() * blockSize, block.getLine() * blockSize, blockSize, blockSize,null);

            }
        }
    }

    // 画 人物
    public void paint(Player player, Graphics graphics) {
        Block position = player.getPosition();
        int blockSize = GameConfiguration.BLOCK_SIZE;
        // 获取人物的类型
        int playerType = player.getType();
        int y = position.getLine();
        int x = position.getColumn();

        graphics.drawImage(playerArr[playerType],x * blockSize, y * blockSize, blockSize, blockSize,null);

    }

    // 画宝藏
    public void paint(Treasure treasure, Graphics graphics){
        Block position = treasure.getPosition();
        int blockSize = GameConfiguration.BLOCK_SIZE;
        int y = position.getLine();
        int x = position.getColumn();
        graphics.drawImage(treasureRes,x * blockSize, y * blockSize, blockSize, blockSize,null);
    }

    // 画怪兽
    public void paint(Monster monster, Graphics graphics){
        Block position = monster.getPosition();
        int blockSize = GameConfiguration.BLOCK_SIZE;
        int y = position.getLine();
        int x = position.getColumn();
        Image ima = this.monsterArr[monster.getType()];
        graphics.drawImage(ima,x * blockSize, y * blockSize, blockSize, blockSize,null);
    }


    public Image[] getTileArr() {
        return tileArr;
    }

    public void setTileArr(Image[] tileArr) {
        this.tileArr = tileArr;
    }

    public void setTile(Image tile, int index){
        this.tileArr[index] = tile;
    }
}
