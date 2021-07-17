/**
 * Класс для создания объекта ReentrantLock, как одной из реализаций интерфейса Lock, создания
 * объектиа счётчика и запуска 2-х потоков.
 */
package hwThree;

import java.util.concurrent.locks.ReentrantLock;

public class MainLock {
    public static void main(String[] args) {

        ReentrantLock reentrantLock = new ReentrantLock();
        ResourceLock resourceLock = new ResourceLock(2);
        new LockThread("A", reentrantLock, resourceLock).start();
        new LockThread("B", reentrantLock, resourceLock).start();

    }
}
