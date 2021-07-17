package hwTwo;

import java.util.Iterator;

public class DoubleLinkedList<T> {
    private class Node {
        public T data;
        public Node next;
        public Node previous;

        public Node(T data) {
            this.data = data;
            this.next = null;
            this.previous = null;
        }

        public Node(T data, Node next, Node previous) {
            this.data = data;
            this.next = next;
            this.previous = previous;
        }

        public String toString() {
            return "Node(" + data.toString() + ")";
        }
    }

    private int size;
    private Node head;
    private Node tail;

    public DoubleLinkedList() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    public T remove(){
        if (size == 0){
            return null;
        }
        T tmp = this.head.data;
        this.head = this.head.previous;
        this.head.next = null;
        this.size--;

        return tmp;
    }

    public T removeTail(){
        if (size == 0){
            return null;
        }
        T tmp = this.tail.data;
        this.tail = this.tail.next;
        this.tail.previous = null;
        this.size--;

        return tmp;
    }

    public void add(T element){
        if (head == null){
            Node tmp = new Node(element);
            this.head = tmp;
            this.tail = tmp;
        } else {
            Node tmp = new Node(element, null, head);
            this.head.next = tmp;
            this.head = tmp;
        }
        this.size++;
    }

    public void addTail(T element) {
        if (head == null) {
            add(element);
        } else {
            Node tmp = new Node(element, tail, null);
            this.tail.previous = tmp;
            this.tail = tmp;
            this.size++;
        }
    }

    public Object[] toArray() {
        Object[] array = new Object[this.size];
        int i = 0;
        for (Node node = this.tail; node != null; node = node.next) {
            array[i] = node.data;
            i++;
        }
        return array;
    }

    public int indexOf(Object target){
        for (int i = 0; i < this.size; i++) {
            if (equals(target, getNode(i))){
                return i;
            }
        }
        return -1;
    }

    public T get(int index) {
        Node node = getNode(index);
        return node.data;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node node = this.tail;
        for (int i=0; i<index; i++) {
            node = node.next;
        }
        return node;
    }

    private boolean equals(Object target, Object element) {
        if (target == null) {
            return element == null;
        } else {
            return target.equals(element);
        }
    }

    public Iterator<T> iterator() {
        return new Iterator<T>() {

            Node tmp = head;

            @Override
            public boolean hasNext() {
                return tmp != null;
            }

            @Override
            public T next() {
                T value = tmp.data;
                tmp = tmp.next;
                return value;
            }
        };
    }

    public int size() {
        return this.size;
    }
}
