package com.company;


import java.io.*;
import java.util.*;

public class MySet<T> implements Set<T>, Serializable {

    private T[] backingStore;
    private int size = 0;
    private int addIndex = 0;

    public MySet() {
        backingStore = (T[]) new Object[20];
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        int empty = 0;
        for (T elements : backingStore){
            if (elements == null){
                empty++;
                if (empty == backingStore.length){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean contains(Object o) {
        for(int i = 0; i < backingStore.length; i++){
            if (((T)o).equals(backingStore[i])){
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new setIterator();
    }

    private class setIterator implements Iterator{

        int index = 0;

        @Override
        public boolean hasNext() {
            return index < size;
        }

        @Override
        public Object next() {
            return backingStore[index++];
        }
    }

    @Override
    public Object[] toArray() {
        T[] temp = (T[]) new Object[size];
        System.arraycopy(backingStore, 0, temp, 0, size);
        return temp;
    }


    @Override
    public <T1> T1[] toArray(T1[] a){
        if(a.length < size || a.length > size) {
            return (T1[]) Arrays.copyOf(backingStore, size, a.getClass());
        }
        System.arraycopy(backingStore, 0, a, 0, size);
        return a;
    }

    @Override
    public boolean add(T t) {
        if (contains(t)){
            return false;
        }
        if (backingStore.length == size){
            T[] temp = backingStore;
            int tempSize = size * 2;
            backingStore = (T[]) new Object[tempSize];
            System.arraycopy(temp, 0, backingStore, 0, temp.length);
        }
        backingStore[addIndex] = t;
        addIndex++;
        size++;
        return true;
    }

    @Override
    public boolean remove(Object o) {
        for (int i = 0; i < backingStore.length; i++){
            if (((T)o).equals(backingStore[i])){
                backingStore[i] = null;
                size--;
                addIndex--;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        ArrayList<T> temp = new ArrayList<>();
        for (Object object : c) {
            temp.add((T) object);
        }

        Iterator iterator = temp.iterator();

        if(iterator.hasNext()) {
            for (int i = 0; i < temp.size(); i++) {
                contains(temp.get(i));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        ArrayList<T> temp = new ArrayList<>();
        for (Object object : c) {
            temp.add((T) object);
        }

        Iterator iterator = temp.iterator();

        if(iterator.hasNext()){
            for (int i = 0; i < temp.size(); i++) {
                add(temp.get(i));
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        ArrayList<T> hold = new ArrayList<>();
        ArrayList<T> temp = new ArrayList<>();
        for (Object object : c) {
            temp.add((T) object);
        }

        Iterator iterator = temp.iterator();

        if (iterator.hasNext()){
            for (int i = 0; i < temp.size(); i++){
                if (contains(temp.get(i))){
                    hold.add(temp.get(i));
                }
            }

            T[] stop = (T[]) new Object[hold.size()];

            for (int i = 0; i < hold.size(); i++){
                stop[i] = hold.get(i);
            }
            clear();
            backingStore = stop;
            size = hold.size();
            return true;
        }

        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        ArrayList<T> temp = new ArrayList<>();
        for (Object object : c) {
            temp.add((T) object);
        }

        Iterator iterator = temp.iterator();

        if(iterator.hasNext()){
            for (int i = 0; i < temp.size(); i++) {
                remove(temp.get(i));
            }
            return true;
        }
        return false;
    }

    @Override
    public void clear() {
        backingStore = (T[])new Object[20];
        size = 0;
        addIndex = 0;
    }


    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {

        ois.defaultReadObject();
        int numElements = ois.readInt();
        for (int i = 0; i < numElements; i++){
            ois.readObject();
        }
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {

        oos.defaultWriteObject();
        oos.writeInt(size);
        for (T write : backingStore){
            oos.writeObject(write);
        }
    }


    public static void main(String[] args) throws IOException, ClassNotFoundException{

        MySet<Integer> test = new MySet<>();
        for (int i = 0; i < 5; i++){
            test.add(i);
        }
        test.add(34);

        try(FileOutputStream fos = new FileOutputStream("MySetTest.txt");
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            ObjectOutputStream oos = new ObjectOutputStream(bos))
        {
            oos.writeObject(test);
        }

        try(FileInputStream fis = new FileInputStream("MySetTest.txt");
            ObjectInputStream ois = new ObjectInputStream(fis))
        {
            MySet test2 = (MySet) ois.readObject();

            for(Object print : test2){
                System.out.println(print);
            }
        }
    }
}
