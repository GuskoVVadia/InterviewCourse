/**
 * Именно такая реализация мне кажется более правильной.
 * Классы, которые будут наследоваться от данного класса смогут переопределить нужные для них методы.
 * Все интерфейсы реализуются в этом классе, т.к. любая машина обладает такими чертами.
 */
package hw1.homeVersion;

public class Car implements Moveable, Stopable{
    private Engine engine;
    private String color;
    private String name;

    public Car(Engine engine, String color, String name) {
        this.engine = engine;
        this.color = color;
        this.name = name;
    }

    protected Engine getEngine(){
        return engine;
    }

    protected void setEngine(Engine engine){
        this.engine = engine;
    }

    protected String getColor() {
        return color;
    }

    protected void setColor(String color) {
        this.color = color;
    }

    protected String getName() {
        return name;
    }

    protected void setName(String name) {
        this.name = name;
    }

    public void start(){
        System.out.println("Car starting");
    }

    public void open(){
        System.out.println("Car is open");
    };

    @Override
    public void move() {
        System.out.println("Car is moving");
    }

    @Override
    public void stop() {
        System.out.println("Car is stop");
    }
}
