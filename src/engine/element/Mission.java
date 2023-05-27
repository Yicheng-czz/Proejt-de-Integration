package engine.element;

import engine.map.Block;

public class Mission implements Comparable<Mission> {
    private Block enter;

    private int missionType;
    private int areaId;

    private ExploreEdge exploreEdge;

    private int currentPlayer;

    public Mission(Block enter, ExploreEdge exploreEdge, int missionType, int areaId){
        this.enter = enter;
        this.exploreEdge = exploreEdge;
        this.missionType = missionType;
        this.areaId = areaId;
    }

    public Mission(Block enter, ExploreEdge exploreEdge, int missionType, int areaId, int currentPlayer){
        this.enter = enter;
        this.exploreEdge = exploreEdge;
        this.missionType = missionType;
        this.areaId = areaId;
        this.currentPlayer = currentPlayer;
    }



    public Block getEnter() {
        return enter;
    }

    public void setEnter(Block enter) {
        this.enter = enter;
    }

    public ExploreEdge getExploreEdge() {
        return exploreEdge;
    }

    public void setExploreEdge(ExploreEdge exploreEdge) {
        this.exploreEdge = exploreEdge;
    }

    public int getMissionType() {
        return missionType;
    }

    public void setMissionType(int missionType) {
        this.missionType = missionType;
    }

    public int getAreaId(){
        return areaId;
    }

    @Override
    public int compareTo(Mission other) {
        // 按missionType递减的顺序排序
        int cmp = Integer.compare(other.missionType, this.missionType);
        if (cmp == 0) {
            // 如果missionType相同，则按areaId递增的顺序排序
            cmp = Integer.compare(this.areaId, other.areaId);
        }
        return cmp;
    }

    public int getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(int currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
}
