package hwTwo;

import java.util.Iterator;

public class SingleLinkedList<T> {
    private class Node {
        private T data;
        public Node next;

        public Node(T data) {
            this.data = data;
            this.next = null;
        }

        public Node(T data, Node next) {
            this.data = data;
            this.next = next;
        }
    }

    private int size;
    private Node head;

    public SingleLinkedList() {
        this.size = 0;
        this.head = null;
    }

    public boolean add(T element) {
        if (this.head == null) {
            this.head = new Node(element);
        } else {
            Node node = this.head;
            for ( ; node.next != null; node = node.next) {}
            node.next = new Node(element);
        }
        size++;
        return true;
    }

    public void add(int index, T element) {
        if (index == 0) {
            this.head = new Node(element, head);
        } else {
            Node node = getNode(index-1);
            node.next = new Node(element, node.next);
        }
        size++;
    }

    public void clear() {
        this.head = null;
        this.size = 0;
    }

    public boolean contains(Object obj) {
        return indexOf(obj) != -1;
    }

    public T get(int index) {
        Node node = getNode(index);
        return node.data;
    }

    private Node getNode(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node node = this.head;
        for (int i=0; i<index; i++) {
            node = node.next;
        }
        return node;
    }

    public int indexOf(Object target) {
        for (int i = 0; i < this.size; i++) {
            if (equals(target, getNode(i))){
                return i;
            }
        }
        return -1;
    }

    private boolean equals(Object target, Object element) {
        if (target == null) {
            return element == null;
        } else {
            return target.equals(element);
        }
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

//    public Iterator<T> iterator() {
//        T[] array = (T[]) toArray();
//        return Arrays.asList(array).iterator();
//    }

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


    public int lastIndexOf(Object target) {
        Node node = this.head;
        int index = -1;
        for (int i=0; i<size; i++) {
            if (equals(target, node.data)) {
                index = i;
            }
            node = node.next;
        }
        return index;
    }

    public boolean remove(Object obj) {
        int index = indexOf(obj);
        if (index == -1) {
            return false;
        }
        remove(index);
        return true;
    }

    public T remove(int index) {
        T element = get(index);
        if (index == 0) {
            head = head.next;
        } else {
            Node node = getNode(index-1);
            node.next = node.next.next;
        }
        size--;
        return element;
    }

    public T set(int index, T element) {
        Node node = getNode(index);
        T old = node.data;
        node.data = element;
        return old;
    }

    public int size() {
        return this.size;
    }

    public Object[] toArray() {
        Object[] array = new Object[this.size];
        int i = 0;
        for (Node node = head; node != null; node = node.next) {
            array[i] = node.data;
            i++;
        }
        return array;
    }

    public <T> T[] toArray(T[] a) {
        int i = 0;
        for (Node node = head; node != null; node = node.next) {
            a[i] = (T) node.data;
            i++;
        }
        return a;
    }

}
