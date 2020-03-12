package base.jse.thread;

import java.util.Random;
import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * Author: areful
 * Date: 2019/4/24
 */
public class CyclicBarrierSample {
    private static class Player implements Runnable {
        private CyclicBarrier cyclicBarrier;
        private int id;

        private Player(int id, CyclicBarrier cyclicBarrier) {
            this.id = id;
            this.cyclicBarrier = cyclicBarrier;
        }

        @Override
        public void run() {
            try {
                System.out.println("玩家" + id + "正在玩第一关...");
                Thread.sleep(new Random().nextInt(1000));
                System.out.println("玩家" + id + "进入第二关...");
                Thread.sleep(new Random().nextInt(1000));
                System.out.println("玩家" + id + "进入第三关...");
                cyclicBarrier.await();
            } catch (InterruptedException | BrokenBarrierException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        final int PLAYER_NUM = 4;
        CyclicBarrier cyclicBarrier = new CyclicBarrier(PLAYER_NUM, new Runnable() {
            @Override
            public void run() {
                System.out.println("所有玩家进入第二关！");
            }
        });

        for (int i = 0; i < PLAYER_NUM; i++) {
            new Thread(new Player(i, cyclicBarrier)).start();
        }
    }
}