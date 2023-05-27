//package test;
//
//import engine.map.Block;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.ArrayBlockingQueue;
//import java.util.concurrent.BlockingQueue;
//
//public class SharedQueueExample {
//    private BlockingQueue<Block> sharedQueue = new ArrayBlockingQueue<>(10);
//
//    public SharedQueueExample() {
//        List<Thread> threads = new ArrayList<>();
//
//        for(int i = 0; i < 4; i++){
//            try{
//                this.sharedQueue.put(new Block(i*2,i*2));
//            } catch (InterruptedException e) {
//                throw new RuntimeException(e);
//            }
//
//        }
//
//        for (int i = 1; i <= 5; i++) {
//            MyRunnable myRunnable = new MyRunnable(i, sharedQueue);
//            Thread thread = new Thread(myRunnable);
//            threads.add(thread);
//        }
//        startAllThreads(threads);
//        joinAllThreads(threads);
//    }
//
//    private void startAllThreads(List<Thread> threads) {
//        for (Thread thread : threads) {
//            thread.start();
//        }
//    }
//
//    private void joinAllThreads(List<Thread> threads) {
//        for (Thread thread : threads) {
//            try {
//                thread.join();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    public static void main(String[] args) {
//        SharedQueueExample example = new SharedQueueExample();
//    }
//}
//
//class MyRunnable implements Runnable {
//    private int threadNumber;
//    private BlockingQueue<Block> sharedQueue;
//
//    public MyRunnable(int threadNumber, BlockingQueue<Block> sharedQueue) {
//        this.threadNumber = threadNumber;
//        this.sharedQueue = sharedQueue;
//
//    }
//
//    @Override
//    public void run() {
//        try {
//            Thread.sleep(1000);
//
//            Block item = sharedQueue.poll();
//            if(item == null){
//                System.out.println("空了");
//                return;
//            }
//            System.out.println("Thread"+threadNumber+": get Block("+item.getLine()+","+item.getColumn()+")");
//
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//    }
//}
