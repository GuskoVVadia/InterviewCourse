/**
 * Класс, как ресурс для работы с Lock блокировкой.
 */

package hwThree;

public class ResourceLock {
    private int value;

    public ResourceLock() {
        this(0);
    }

    public ResourceLock(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getAndIncrement(){
        return this.value++;
    }
}
