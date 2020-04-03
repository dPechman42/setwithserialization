package com.company;

import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class MySetTest {

    MySet<Integer> testInt = new MySet<>();

    MySet<Integer> testInt2 = new MySet<>();

    MySet<String> testString = new MySet<>();


    MySet<String> testString2 = new MySet<>();


    @Test
    void size() {
        assertEquals(0, testInt.size());
        testInt.add(5);
        testInt.add(7);
        assertEquals(2, testInt.size());
    }

    @Test
    void isEmpty() {
        testString.add("Hello");
        testString.add("World!");
        assertFalse(testString.isEmpty());

        testString.clear();
        assertTrue(testString.isEmpty());
    }

    @Test
    void contains() {
        testString.add("Java");
        testString.add("Kerfufle");
        assertTrue(testString.contains("Java"));

        testInt.add(9);
        testInt.add(19);
        assertTrue(testInt.contains(19));
    }

    @Test
    void iterator() {
        int[] test = new int[6];
        for (int i = 0; i < 6; i++) {
            testInt.add(i);
            test[i] = i;
        }

        int[] array = new int[testInt.size()];
        int count = 0;
        for (int i : testInt) {
            array[count++] = i;
        }

        assertArrayEquals(test, array);
    }

    @Test
    void toArray() {
        testInt.add(3);
        testInt.add(4);
        testInt.add(5);
        int[] test = {3, 4, 5};
        Object[] temp = testInt.toArray();
        int[] array = new int[temp.length];
        for (int i = 0; i < temp.length; i++) {
            array[i] = (int) temp[i];
        }

        assertArrayEquals(test, array);
    }

    @Test
    void testToArray() {
        testString.add("Hello");
        testString.add("Bye");
        testString.add("Defenestration");
        testString.add("Pandemic");

        String[] test = {"Hello", "Bye", "Defenestration", "Pandemic"};
        String[] array = new String[5];
        array = testString.toArray(array);
        assertArrayEquals(test, array);
        String[] array2 = new String[testString.size()];
        array2 = testString.toArray(array2);
        assertArrayEquals(test, array2);
        String[] array3 = new String[0];
        array3 = testString.toArray(array3);
        assertArrayEquals(test, array3);
    }

    @Test
    void add() {
        for (int i = 0; i < 35; i++) {
            testInt.add(20 + i);
        }

        assertEquals(35, testInt.size());

        assertTrue(testInt.add(1));
        assertFalse(testInt.add(21));
    }


    @Test
    void remove() {
        testString.add("Test");
        testString.add("Fail");
        testString.remove("Fail");
        assertEquals(1, testString.size());

        testInt.add(37);
        testInt.add(350);
        testInt.add(924837);
        testInt.remove(37);
        assertEquals(2, testInt.size());

        assertTrue(testString.remove("Test"));
        assertFalse(testString.remove("Ironman"));
    }

    @Test
    void containsAll() {

        for (int i = 0; i < 15; i++) {
            testInt.add(i);
        }

        for (int i = 0; i < 5; i++) {
            testInt2.add(i);
        }

        assertTrue(testInt.containsAll(testInt2));

        testString.add("Hello");
        testString.add("World");
        testString.add("the virus");
        testString.add("is giving me cabin fever");

        testString2.add("Hello");
        testString2.add("the virus");

        assertTrue(testString.containsAll(testString2));


    }

    @Test
    void addAll() {

        for (int i = 0; i < 10; i++) {
            testInt.add(i);
        }

        assertEquals(10, testInt.size());

        for (int i = 0; i < 15; i += 2) {
            testInt2.add(i);
        }

        assertEquals(8, testInt2.size());

        testInt.addAll(testInt2);

        assertEquals(13, testInt.size());

        assertTrue(testInt.addAll(testInt2));

    }

    @Test
    void retainAll() {
        for (int i = 0; i < 35; i++) {
            testInt.add(i);
        }

        assertEquals(35, testInt.size());


        for (int i = 0; i < 100; i += 2) {
            testInt.remove(i);
        }

        assertEquals(17, testInt.size());


        for (int i = 0; i < 10; i++) {
            testInt2.add(i);
        }

        assertEquals(10, testInt2.size());

        testInt.retainAll(testInt2);

        assertEquals(5, testInt.size());

        assertTrue(testInt.retainAll(testInt2));
    }

    @Test
    void removeAll() {
        testString.add("Hello");
        testString.add("My");
        testString.add("Is");
        testString.add("Sweeney");
        testString2.add("Is");
        testString2.add("Sweeney");

        testString.removeAll(testString2);
        assertEquals(2, testString.size());

        assertTrue(testString.removeAll(testString2));
    }

    @Test
    void clear() {
        testInt.add(8);
        testInt.add(7);
        testInt.add(4);
        testInt.add(2);
        testInt.add(6);
        testInt.add(42);


        assertFalse(testInt.isEmpty());
        testInt.clear();

        assertEquals(0, testInt.size());

    }

    @Test
    void serial() throws IOException, ClassNotFoundException {
        testInt = new MySet<>();
        for (int i = 0; i < 5; i++) {
            testInt.add(i);
        }
        testInt.add(34);

        try (FileOutputStream fos = new FileOutputStream("MySetTest.txt");
             BufferedOutputStream bos = new BufferedOutputStream(fos);
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {
            oos.writeObject(testInt);
        }

        MySet<Integer> test2;
        try (FileInputStream fis = new FileInputStream("MySetTest.txt");
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            test2 = (MySet) ois.readObject();

        }

        Integer[] testIntArr = new Integer[0];
        testIntArr = testInt.toArray(testIntArr);
        Integer[] testSerial = new Integer[test2.size()];
        testSerial = test2.toArray(testSerial);

        assertArrayEquals(testSerial, testIntArr);

    }

}