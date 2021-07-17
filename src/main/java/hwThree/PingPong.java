/**
 * Реализован метод main для создания экземпляра Семафора и для создания и запуска двух зацикленных потоков
 * с разными именами.
 */
package hwThree;

import java.util.concurrent.Semaphore;

public class PingPong {

    public static void main(String[] args) throws InterruptedException {

        Semaphore semaphore = new Semaphore(1);
        new SayThread("A", "ping", semaphore).start();
        Thread.sleep(2);
        new SayThread("B", "pong", semaphore).start();

    }
}
