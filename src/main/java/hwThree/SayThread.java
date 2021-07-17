/**
 * Данный класс расширяет класс Thread, это сделано для простоты реализации и для простоты понимания.
 * Т.к. в ТЗ указано: реализовать программу, в которой два потока поочерёдно пишут ping и pong.
 * И я выбрал такой подход по следующим причинам:
 *
 * 1. Не хотелось всё впихивать в лямбду в одном методе main.
 * 2. Такой подход позволит задать удобочитаемые имена и большую гибкость в реализации и изменении логики.
 * 3. Выбран способ запрашивания разрешения у экземпляра класса Semaphore. Выбран такой способ просто ради интереса,
 * и не претендует на правильность. Понравилась сама идея реализации без синхронизированных методов, блоков,
 * флагов или атомарных величин.
 * 4. В данном классе также используется бесконечный цикл - это некрасивый подход, но выбран для простоты реализации.
 * На мой взгляд, стоило выбрать исполнитель, его реализацию на фиксированное количество потоков.
 * 5. Реализуется небольшая задержка, опять таки, для удобства восприятия.
 */

package hwThree;

import java.util.concurrent.Semaphore;

public class SayThread extends Thread{

    private String nameThread;
    private String word;
    private Semaphore semaphore;

    public SayThread(String nameThread, String word, Semaphore semaphore) {
        this.nameThread = nameThread;
        this.word = word;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        while (true) {
            try {
                this.semaphore.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(nameThread + " say: " + word);

            this.semaphore.release();
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
