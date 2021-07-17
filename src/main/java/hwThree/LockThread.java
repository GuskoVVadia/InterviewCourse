/**
 * Задача и специфика класса:
 * 1. Расширение класса Thread с указание "имени" потока.
 * 2. Пользуясь реализацией блокировки Lock, получаем блокировку, увеличиваем счётчик, засыпаем на 100 ms
 * с дальнейшим освобождением блокировки.
 * 3. Увеличение псчётчика происходит разово.
 */

package hwThree;

import java.util.concurrent.locks.Lock;

public class LockThread extends Thread{

    private String name;
    private Lock lock;
    private ResourceLock resourceLock;

    public LockThread(String name, Lock lock, ResourceLock resourceLock) {
        this.name = name;
        this.lock = lock;
        this.resourceLock = resourceLock;
    }


    @Override
    public void run() {
        System.out.println("Start Thread " + this.name);

        try {
            System.out.println("Ожидание блокировки потоком с именем " + this.name);
            this.lock.lock();

            System.out.println("Thread name " + this.name + " заблокировал счётчик.");
            this.resourceLock.getAndIncrement();
            System.out.println("Счётчик = " + this.resourceLock.getValue());

            Thread.sleep(100);
        } catch (InterruptedException e){
            System.err.println(e);
        } finally {
            System.out.println("Thread name " + this.name + " разблокирует счётчик.");
            this.lock.unlock();
        }
    }
}
