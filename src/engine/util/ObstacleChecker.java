package engine.util;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ObstacleChecker {

    private List<Integer> obstacleTiles;

    public ObstacleChecker(){
        // 承装障碍物对应的tile编号
        this.obstacleTiles = new ArrayList<>(Arrays.asList(16,18,19,20,21,22,23,24,25,26,27,28,29,30,31,32,33,35,39,41,44,45,46,47,48,49,50));
    }

    public Boolean isObstacle(int tile){
        // 判断当前的tile编号是否被包含在list中
        return this.obstacleTiles.contains(tile);
    }

}

