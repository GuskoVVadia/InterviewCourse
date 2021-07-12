/**
 * Ошибки из методички в данном классе:
 * 1. класс расширяет абстрактный и имплементирует интерфейсы, а в методичке только расширяет.
 * 2. т.к. данный класс не абстрактный, то необходимо определить метод open() из класса Car.
 */

package hw1.lessonVersion;

public class Lorry extends Car implements Moveable, Stopable{

    @Override
    void open() {
        System.out.println("Не реализован");
    }

    @Override
    public void move() {
        System.out.println("Car is moving");
    }

    @Override
    public void stop() {
        System.out.println("Car is stop");
    }
}
