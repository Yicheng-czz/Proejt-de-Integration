package engine.process;

import config.GameConfiguration;
import engine.element.*;
import engine.map.Block;
import engine.map.Map;
import engine.util.ObstacleChecker;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.text.html.HTMLDocument.Iterator;

import Astar.AStar;
import Astar.Node;

// Threaded classes to allow player control
public class PlayerManager implements Runnable {
	private Player player;
	private String[] directions = {"up", "down", "left", "right"};
	private String direction;
	private ObstacleChecker obstacleChecker;
	private Mission missionCurrent = null;
	private AStar aStar;
	private Map map;
	private int switchStrategy = 3;

	// Shared Data
	final ReentrantLock lock;
	private PriorityQueue<Mission> missionQueue;
	private List<Monster> monsterList;
	private List<Treasure> treasureList;
	private int[] helpAsks;
	private Block mutex;

	public PlayerManager(Player player, PriorityQueue<Mission> missionQueue, ReentrantLock lock, List<Monster> monsterList, Map map, List<Treasure> treasureList, Block mutex, int[] helpAsks){
		this.player = player;
		this.obstacleChecker = new ObstacleChecker();
		this.missionQueue = missionQueue;
		this.lock = lock;
		this.monsterList = monsterList;
		this.map = map;
		this.treasureList = treasureList;
		this.mutex = mutex;
		this.helpAsks = helpAsks;
	}

	private synchronized void setHelpAsksIElem(int flag, int playerType){
		this.helpAsks[playerType] = flag;
	}

	private synchronized int getHelpAsksIElem(){
		return this.helpAsks[getPlayer().getType()];
	}

	public String[] getDirections(){
		return this.directions;
	}

	public void randomDirection(){
		Random r = new Random();
		setDirection(this.getDirections()[r.nextInt(4)]);
	}

	public Player getPlayer() {
		return player;
	}

	public String getDirection(){
		return this.direction;
	}

	public void setDirection(String direction){
		this.direction = direction;
	}

	private boolean inExploreEdge(int line, int col){
		ExploreEdge exploreEdge = getMissionCurrent().getExploreEdge();
		return line<=exploreEdge.getEndX() && line>=exploreEdge.getStartX() && col<=exploreEdge.getEndY() && col>=exploreEdge.getStartY();
	}

	public Block aStarMove(Node dest){
		Node startNode = new Node(getPlayer().getPosition().getLine(),getPlayer().getPosition().getColumn());
		Node endNode = dest;
		this.aStar = new AStar(startNode, endNode);
		ArrayList<Node> Parcour = aStar.getParcour();
		int etape = Parcour.size()-2;
		int next_X = Parcour.get(etape).x;
		int next_Y = Parcour.get(etape).y;
		Parcour = null;
		return new Block(next_Y, next_X);
	}

	// Return to the next step the player is about to take
	public Block randMove(){
		Block player_position = this.getPlayer().getPosition();
		int line = player_position.getLine();
		int col = player_position.getColumn();
		randomDirection();
		Block block = null;
		switch (getDirection()){
			case "up":
				if(inExploreEdge(line-1, col)){
					block = map.getBlock(line-1,col);
				}
				break;
			case "down":
				if(inExploreEdge(line+1, col)){
					block = map.getBlock(line+1,col);
				}
				break;
			case "left":
				if(inExploreEdge(line, col-1)){
					block = map.getBlock(line,col-1);
				}
				break;
			case "right":
				if(inExploreEdge(line, col+1)){
					block = map.getBlock(line,col+1);
				}
				break;
		}
		if(block == null){
			return getPlayer().getPosition();
		}
		return block;

	}

	public synchronized Monster isMonster(Block block){
		for(Monster monster : monsterList){
			if(monster.getPosition().getLine() == block.getLine() && monster.getPosition().getColumn() == block.getColumn()){
				return monster;
			}
		}
		return null;
	}

	public boolean isSamePosition(Block block1, Block block2){
		return block1.getLine() == block2.getLine() && block1.getColumn() == block2.getColumn();
	}

	public synchronized Treasure get_ID_treasure(List<Treasure> treasureList,int idArea){
		for(Treasure t : treasureList){
			if(t.getAreaId() == idArea) {
				return t;
			}
		}
		return null;
	}

	public Boolean collisionTreasure(){
		Treasure t = get_ID_treasure(treasureList,missionCurrent.getAreaId());
		return player.getPosition().getLine() == t.getPosition().getLine()
				&& player.getPosition().getColumn() == t.getPosition().getColumn();
	}

	public synchronized void supp_ID_treasure(List<Treasure> treasureList,int idArea){
		for(int i=0; i<treasureList.size();i++) {
			if(treasureList.get(i).getAreaId() == idArea ) {
				treasureList.remove(i);					
			}
		}
	}

	// run
	@Override
	public void run(){
		int strategy = this.switchStrategy;
		// treasureList is not empty
		while(!treasureList.isEmpty()){
			try {
				Thread.sleep(GameConfiguration.GAME_SPEED);
			} catch (InterruptedException e) {
				throw new RuntimeException(e);
			}
			// If there is a current mission
			if(getMissionCurrent() != null){
				if(missionCurrent.getMissionType() == 1){
					this.switchStrategy = 4;
				}
				switch (getSwitchStrategy()) {
					case 1 -> {
						Block block = null;
						// If not within the exploration boundary of the current mission
						if (!inExploreEdge(player.getPosition().getLine(), player.getPosition().getColumn())) {
							block = aStarMove(new Node(missionCurrent.getEnter().getLine(), missionCurrent.getEnter().getColumn()));
						}
						// If within the exploration boundary of the current mission
						else {
							Treasure t = get_ID_treasure(treasureList, missionCurrent.getAreaId());
							int line = t.getPosition().getLine();
							int col = t.getPosition().getColumn();
							block = aStarMove(new Node(line, col));
						}
						Monster monster = isMonster(block);
						if (monster != null) {
							if (monster.getType() == 3) {
								try {
									Mission mission = new Mission(block, missionCurrent.getExploreEdge(), 1, missionCurrent.getAreaId());
									lock.lock();
									missionQueue.offer(mission);
									lock.unlock();
									synchronized (this.mutex){
										this.mutex.wait();
									}
								} catch (InterruptedException e) {
									throw new RuntimeException(e);
								}
							} else {
								getPlayer().setPosition(block);
								lock.lock();
								monsterList.remove(monster);
								lock.unlock();
							}
						}else{
							getPlayer().setPosition(block);
						}
						if (collisionTreasure()) {
							supp_ID_treasure(treasureList, missionCurrent.getAreaId());
							setMissionCurrent(null);
						}


					}
					case 2 -> {
						Block block = null;
						if (!inExploreEdge(player.getPosition().getLine(), player.getPosition().getColumn())) {
							Block b = new Block(missionCurrent.getEnter().getLine(), missionCurrent.getEnter().getColumn());
							player.setPosition(b);
						}
						else {
							Treasure t = get_ID_treasure(treasureList, missionCurrent.getAreaId());
							int line = t.getPosition().getLine();
							int col = t.getPosition().getColumn();
							block = aStarMove(new Node(line, col));

							Monster monster = isMonster(block);
							if (monster != null) {
								// choose the first possible position, like bypassing the monster
								Block[] blocks = {new Block(block.getLine()-1,block.getColumn()),
												  new Block(block.getLine()+1, block.getColumn()),
												  new Block(block.getLine(),block.getColumn()+1),
												  new Block(block.getLine(),block.getColumn()-1)};
								for(Block b: blocks){
									if(isMonster(b) == null && inExploreEdge(b.getLine(),b.getColumn()) && !obstacleChecker.isObstacle(b.getTile())){
										block = b;
									}
								}
								getPlayer().setPosition(block);
							}
							else{
								getPlayer().setPosition(block);
							}
							if (collisionTreasure()) {
								supp_ID_treasure(treasureList, missionCurrent.getAreaId());
								setMissionCurrent(null);
							}
						}

					}
					case 3 -> {
						Block block = null;
						if (!inExploreEdge(player.getPosition().getLine(), player.getPosition().getColumn())) {
							block = aStarMove(new Node(missionCurrent.getEnter().getLine(), missionCurrent.getEnter().getColumn()));
							player.setPosition(block);
						} else {
							block = randMove();
							// If it is not an obstacle
							if (!obstacleChecker.isObstacle(block.getTile())) {
								// If it's a monster
								Monster monster = isMonster(block);
								if (monster != null) {
									// If it is not a skeleton
									if (monster.getType() != 3) {
										lock.lock();
										monsterList.remove(monster);
										lock.unlock();
										getPlayer().setPosition(block);
									}
									// If it's a skeleton, call for help and wait()
									else {
										Mission mission = new Mission(block, missionCurrent.getExploreEdge(), 1, missionCurrent.getAreaId(), getPlayer().getType());
										lock.lock();
										missionQueue.offer(mission);
										lock.unlock();
										synchronized (this.mutex){
											try {
												setHelpAsksIElem(1, getPlayer().type);
												System.out.println();
												while(getHelpAsksIElem() == 1){
													this.mutex.wait();
												}
											} catch (InterruptedException e) {
												throw new RuntimeException(e);
											}
										}
									}
								} else {
									getPlayer().setPosition(block);
								}

								// if it is a treasure
								if (collisionTreasure()) {
									supp_ID_treasure(treasureList, missionCurrent.getAreaId());
									setMissionCurrent(null);
								}
							}
						}
					}
					case 4 -> {
						Block block = null;
						block = aStarMove(new Node(missionCurrent.getEnter().getLine(), missionCurrent.getEnter().getColumn()));
						getPlayer().setPosition(block);
						if (isSamePosition(player.getPosition(), missionCurrent.getEnter())) {
							Monster monster = isMonster(player.getPosition());
							lock.lock();
							monsterList.remove(monster);
							lock.unlock();
							synchronized (this.mutex){
								setHelpAsksIElem(0,missionCurrent.getCurrentPlayer());
								this.mutex.notifyAll();
							}
							setMissionCurrent(null);
							switchStrategy = strategy;
						}
					}
				}
			}
			// If there is no mission for the ‘missionCurrent’, it will go to the missionQueue and pick up the mission
			else{
				lock.lock();
				this.missionCurrent = missionQueue.poll();
				lock.unlock();
				// After trying to fetch a task, if the task queue is empty
				if(getMissionCurrent() == null){
					lock.lock();
					if(treasureList.isEmpty()){
						return;
					}
					lock.unlock();
				}
				else{
					System.out.println(player.getType()+" player get the mission of "+missionCurrent.getAreaId()+" area, the corresponding entrance : ("+missionCurrent.getEnter().getLine()+","+missionCurrent.getEnter().getColumn()+"), missionType : " + missionCurrent.getMissionType());
				}
			}
		}
	}

	public Mission getMissionCurrent() {
		return missionCurrent;
	}

	public void setMissionCurrent(Mission missionCurrent) {
		this.missionCurrent = missionCurrent;
	}

	public int getSwitchStrategy() {
		return switchStrategy;
	}

	public void setSwitchStrategy(int switchStrategy) {
		this.switchStrategy = switchStrategy;
	}
}

