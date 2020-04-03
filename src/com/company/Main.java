package com.company;

import java.io.*;

public class Main {
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
