package test;

import engine.element.ExploreEdge;
import engine.element.Mission;
import engine.map.Block;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.concurrent.locks.ReentrantLock;

public class SharedPriorityQueue {
    private PriorityQueue<Mission> missionPriorityQueue = new PriorityQueue<>();

    private ReentrantLock lock = new ReentrantLock();

    private int counter = 0;

    public  SharedPriorityQueue(){
        List<Thread> threads = new ArrayList<>();

        Block enter1 = new Block(19,5);
        ExploreEdge exploreEdge1 = new ExploreEdge(1,1,19,27);
        Mission mission1 = new Mission(enter1,exploreEdge1,0,1);

        Block enter2 = new Block(19,53);
        ExploreEdge exploreEdge2 = new ExploreEdge(1,30,19,57);
        Mission mission2 = new Mission(enter1,exploreEdge1,0,2);

        Block enter3 = new Block(19,86);
        ExploreEdge exploreEdge3 = new ExploreEdge(1,59,19,89);
        Mission mission3 = new Mission(enter1,exploreEdge1,0,3);

        Block enter4 = new Block(27,17);
        ExploreEdge exploreEdge4 = new ExploreEdge(27,1,46,27);
        Mission mission4 = new Mission(enter1,exploreEdge1,0,4);

        Block enter5 = new Block(27,53);
        ExploreEdge exploreEdge5 = new ExploreEdge(27,30,46,57);
        Mission mission5 = new Mission(enter1,exploreEdge1,0,5);

        Block enter6 = new Block(27,72);
        ExploreEdge exploreEdge6 = new ExploreEdge(27,59,46,89);
        Mission mission6 = new Mission(enter1,exploreEdge1,0,6);

        missionPriorityQueue.add(mission1);
        missionPriorityQueue.add(mission2);
        missionPriorityQueue.add(mission3);
        missionPriorityQueue.add(mission4);
        missionPriorityQueue.add(mission5);
        missionPriorityQueue.add(mission6);


        for(int i = 1; i <= 5; i++) {
            Myrunnable myrunnable = new Myrunnable(i, missionPriorityQueue, lock);
            Thread thread = new Thread(myrunnable);
            threads.add(thread);
        }
        startAllThreads(threads);
        joinAllThreads(threads);

    }

    private void startAllThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            thread.start();
        }
    }

    private void joinAllThreads(List<Thread> threads) {
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        SharedPriorityQueue sharedPriorityQueue = new SharedPriorityQueue();
    }

}


class Counter {
    public static int count = 0;
}


class Myrunnable implements Runnable {
    private int threadNumber;
    private PriorityQueue<Mission> sharedQueue;

    final ReentrantLock lock;

    public Myrunnable(int threadNumber, PriorityQueue<Mission> sharedQueue, ReentrantLock lock) {
        this.threadNumber = threadNumber;
        this.sharedQueue = sharedQueue;
        this.lock = lock;
    }

    @Override
    public void run() {
        Mission mission;
        try {
            while(!sharedQueue.isEmpty()){
                Thread.sleep(1000);
                lock.lock();
                mission = sharedQueue.poll();
                ++Counter.count;
                lock.unlock();

                if(mission == null){
                    System.out.println("空了");
                    return;
                }
                else{
                    System.out.println("第"+Counter.count+"拿到，Thread"+threadNumber+": get Mission("+mission.getAreaId()+")");
                }
            }

        }
        catch (InterruptedException ex) {
            throw new RuntimeException(ex);
        }





    }
}