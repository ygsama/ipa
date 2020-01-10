package io.giithub.ygsama;

/**
 * 死锁案例
 */
public class Demo2 {

    private static String A = "A";
    private static String B = "B";

    public static void main(String[] args) {
        Demo2.deadLock();
    }

    private static void deadLock() {
        Thread t1 = new Thread(() -> {
            synchronized (A) {
                try {
                    System.out.println("t1 sleep");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                synchronized (B) {
                    System.out.println("t1");
                }
            }
        });
        Thread t2 = new Thread(() -> {
            synchronized (B) {
                synchronized (A) {
                    System.out.println("t2");
                }
            }
        });
        t1.start();
        t2.start();
    }
}
