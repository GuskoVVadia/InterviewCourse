/*
Как правило, программист должен предоставить конструктор void (без аргументов)
 и Collection в соответствии с рекомендациями в спецификации интерфейса Collection .
 */

package hwTwo;

import java.io.Serializable;
import java.util.*;

public class MArrayList<E> extends AbstractList<E>
        implements List<E>, RandomAccess, Serializable{

    public static final long serialVersionUID = -3280886958256103307L;

    /*
    Вместимость по умолчанию.
     */
    public static final int DEFAULT_CAPACITY = 10;

    /*
    Позиция в массиве (листе), иными словами размер.
     */
    private int size = 0;

    /*
    Внутренний массив.
     */
    private Object[] data;

    /**
     * Своя реализация итератора для класса.
     * Итератор проходиться по массиву объектов и не хранит у себя копию массива.
     * В итераторе реализуется проверка на изменение состояния внутреннего массива класса.
     * Реализованы только обязательные методы.
     * В классе не реализовал проверки внутреннего массива на длину, на null, т.к. предполагается,
     * что за массив (хранилище) ответственнен класс выше.
     * @param <E>
     */
    private class InnerMIterator<E> implements Iterator<E>{

        private int step;
        private int check;

        public InnerMIterator() {
            this.check = modCount;
            this.step = 0;
        }

        /**
         * Метод проверки возможности следующего шага итерации.
         * @return true - если на следующем шаге итерация не выйдет за пределы массива и false - если
         *  выйдет.
         */
        @Override
        public boolean hasNext() {
            checkModificationArray();
            return (this.step < data.length) ? true : false;
        }

        /**
         * Передача элемента массива под индексом step.
         * @return - элемент массива.
         */
        @Override
        public E next() {
            checkModificationArray();
            return (E) data[step++];
        }

        private void checkModificationArray(){
            if (this.check != modCount){
                throw new ConcurrentModificationException("В объект были внесены изменения");
            }
        }
    }

    /**
     * Конструктор по умолчанию.
     * Создание внутреннего массива с ёмкостью по умолчанию.
     */
    public MArrayList() {
        this.data = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Конструктор класса с указанием ёмкости.
     * @param initialCapacity - требуемая ёмкость.
     * @throws IllegalArgumentException - при указании отрицательного значения.
     */
    public MArrayList(int initialCapacity) {
        if (initialCapacity > 0){
            this.data = new Object[initialCapacity];
        } else if (initialCapacity == 0){
            this.data = new Object[DEFAULT_CAPACITY];
        } else {
            throw new IllegalArgumentException("Illegal Capacity: "+
                    initialCapacity);
        }
    }

    /**
     * Конструктор класса с аргументом в виде коллекции, в соответствии с рекомендациями
     * в спецификации интерфейса Collection.
     * Создаёт список на основании указанной коллекции.
     * @param collection - коллекция, которую нужно будет поместить в список.
     * @throws NullPointerException - если коллекция равна null.
     */
    public MArrayList(Collection<? extends E> collection){
        if (collection == null) {
            throw new NullPointerException("Collection is null");
        }

        Object[] objects = collection.toArray();
        if ((size = objects.length) != 0){
            if (collection.getClass() == MArrayList.class){
                this.data = objects;
            } else {
                this.data = Arrays.copyOf(objects, size, Object[].class);
            }
        } else {
            this.data = new Object[DEFAULT_CAPACITY];
        }
    }

    /**
     * Добавляет элемент в конец списка.
     * @param e - элемент, который будет добавлен к этому списку
     * @return истина (как указано в Collection.add)
     */
    @Override
    public boolean add(E e) {
        ensureCapacityInternal(size + 1);
        this.data[size++] = e;
        this.modCount++;
        return true;
    }

    /**
     * Заменяет элемент в указанной позиции во внутреннем массиве на указанный элемент.
     * @param index - индекс элемента, который необходимо заменить.
     * @param element - элемент, который будет установлен на указанную позицию.
     * @return - элемент, который был заменён.
     * @throws IndexOutOfBoundsException - если индекс либо отрицательный, либо превышает
     * размеры массива.
     */
    @Override
    public E set(int index, E element) {
        checkIndex(index);

        E oldValue = (E) data[index];
        this.data[index] = element;
        this.modCount++;
        return oldValue;
    }

    /**
     * Вставляет указанный элемент в указанную позицию в этом списке. Сдвигает элемент,
     * который в данный момент находится в этой позиции (если есть),
     * и все последующие элементы вправо (добавляет единицу к их индексам).
     * @param index - по которому должен быть вставлен указанный элемент.
     * @param element - элемент для вставки.
     * @throws IndexOutOfBoundsException (если индекс вне допустимого диапазона ( index <0 || index> size () ))
     */
    @Override
    public void add(int index, E element) {
        checkIndex(index);
        ensureCapacityInternal(size + 1);
        System.arraycopy(data, index, data, index + 1, size - index);
        this.data[index] = element;
        this.modCount++;
        this.size++;
    }

    /**
     * Удаление элемента из внутреннего массива по указанному индексу с последующим сдвигом элементов влево
     * и заменой на null последнего значения, а также с уменьшением на 1 значения size.
     * @param index - индекс элемента в массиве.
     * @return - удаляемый элемент.
     * @throws IndexOutOfBoundsException (если индекс вне допустимого диапазона ( index <0 || index> size () ))
     */
    @Override
    public E remove(int index) {
        checkIndex(index);

        E oldValue = (E) data[index];
        int numMoved = size - index - 1;
        if (numMoved > 0){
            System.arraycopy(data, index+1, data, index, numMoved);
        }
        data[--size] = null;
        this.modCount++;

        return oldValue;
    }

    /**
     * Из описания класса ArrayList:
     * Возвращает индекс первого вхождения указанного элемента в этом списке или -1,
     * если этот список не содержит элемент.
     * Более формально, возвращает наименьший индекс i, такой
     * что (o == null? Get (i) == null: o.equals (get (i))) , или -1, если такого индекса нет.
     *
     * @param o - элемент для поиска
     * @return - индекс первого вхождения указанного элемента или -1, если такового индекса нет.
     */
    @Override
    public int indexOf(Object o) {
        if (o == null) {
            for (int i = 0; i < size; i++)
                if (data[i]==null)
                    return i;
        } else {
            for (int i = 0; i < size; i++)
                if (o.equals(data[i]))
                    return i;
        }
        return -1;
    }

    /**
     * Из описания класса ArrayList:
     * Возвращает индекс последнего вхождения указанного элемента в этом списке или -1,
     * если этот список не содержит элемент.
     * Более формально, возвращает наивысший индекс i, такой
     * что (o == null? Get (i) == null: o.equals (get (i))) , или -1, если такого индекса нет.
     *
     * @param o - объект для поиска
     * @return - индекс последнего вхождения указанного элемента или -1, если такового индекса нет.
     */
    @Override
    public int lastIndexOf(Object o) {
        if (o == null) {
            for (int i = size-1; i >= 0; i--)
                if (data[i]==null)
                    return i;
        } else {
            for (int i = size-1; i >= 0; i--)
                if (o.equals(data[i]))
                    return i;
        }
        return -1;
    }

    /**
     * Удаляет все элементы из этого списка.
     * Список будет пустым после того, как этот вызов вернется.
     */
    @Override
    public void clear() {
        for (int i = 0; i < size; i++) {
            data[i] = null;
        }
        size = 0;
        this.modCount++;
    }

    /**
     * Вставка коллекции по указанному индексу со смещением массива на длинну вставляемой коллекции влево.
     * @param index - индекс вставки передаваемой коллекции.
     * @param c - коллекция для вставки.
     * @return - возвращается true, если массив (внутренний массив данных) изменился.
     */
    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        checkIndex(index);

        Object[] a = c.toArray();
        int numNew = a.length;
        ensureCapacityInternal(size + numNew);

        int numMoved = size - index;
        if (numMoved > 0)
            System.arraycopy(data, index, data, index + numNew,
                    numMoved);

        System.arraycopy(a, 0, data, index, numNew);
        size += numNew;
        this.modCount++;
        return numNew != 0;
    }

    /**
     * Итератор для прохода по массиву данных.
     * @return итератор.
     */
    @Override
    public Iterator<E> iterator() {
        return new InnerMIterator<>();
    }

    /**
     * Этот метод не реализован в данном классе. Реализация возложена на AbstractList.
     * @return listIterator
     */
    @Override
    public ListIterator<E> listIterator() {
        return super.listIterator();
    }

    /**
     * Этот метод не реализован в данном классе. Реализация возложена на AbstractList.
     * @return listIterator
     */
    @Override
    public ListIterator<E> listIterator(int index) {
        return super.listIterator(index);
    }

    /**
     * Этот метод не реализован в данном классе. Реализация возложена на AbstractList.
     * @param fromIndex
     * @param toIndex
     * @return
     */
    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        return super.subList(fromIndex, toIndex);
    }

    /**
     * Метод реализован средствами IDE.
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        MArrayList<?> that = (MArrayList<?>) o;
        return size == that.size && Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    /**
     * Передача элемента под запрашиваемым индексом в массиве, с проверкой индекса.
     * @param index - индекс, запрашиваемого элемента
     * @return - елемент, расположенный в массиве под указанным индексом.
     * @throws IndexOutOfBoundsException - если запрашиваемый индекс имеет отрицательное значение или же
     * выходит за пределы массива.
     */
    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) data[index];
    }

    /**
     * Ёмкость внутреннего массива.
     * @return - возвращаем size, т.е. внутреннюю длинну массива.
     */
    @Override
    public int size() {
        return this.size;
    }

    /**
     * Метод обеспечения ёмкости во внутреннем массиве.
     * Проверяет возможность вставки в массив данных, т.е. превосходит ли значение value
     * общую длинну внутреннего массива.
     * Если превосходит, то массив нужно увеличивать настолько, что бы вместить имеющиеся данные и
     * обеспечить дополнительное пространство для добавления новых данных.
     * Если пространства не хватает метод увеличивает массив с копирование данных.
     * @param value - число ячеек, которые должны уместиться во внутреннем массиве.
     */
    private void ensureCapacityInternal(int value){

        if (data.length < value){
            int inc = value / data.length;
            if (inc == 1){
                inc = 2;
            }
            data = Arrays.copyOf(data, (data.length * inc));
        }
    }

    /**
     * Проверяет, находиться ли запрашиваемый индекс в допустимом диапозоне.
     * @param index - индекс
     * @throws IndexOutOfBoundsException - в случае, если указанный индекс вне диапозона, т.е.
     * либо отрицательный, либо превышает размеры внутреннего массива.
     */
    private void checkIndex(int index){
        if (index > size || index < 0)
            throw new IndexOutOfBoundsException("Индекс вне допустимого диапозона");
    }

    /**
     * Метод реализован методами IDE, пожалуй, для удобства восприятия при выводе.
     * @return - строка с размером и массив данных.
     */
    @Override
    public String toString() {
        return "MArrayList{" +
                "size=" + size +
                ", data=" + Arrays.toString(data) +
                '}';
    }
}
