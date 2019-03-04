package ru.otus.l02;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

public class Main {
    private static int count = 1_000_000;

    public static void main(String[] args) throws Exception {
        System.out.println("ObjectSizeCalculator = " + ObjectSizeCalculator.getObjectSize(ObjectFactory.createObject(ObjectType.STRING)));

        long startMemory = getSize();
        Object[] array = new Object[count];
        for (int j = 0; j < count; j++) {
            array[j] = ObjectFactory.createObject(ObjectType.STRING);
        }
        long endMemory = getSize();
        System.out.println("CustomCalculator = " + (endMemory-startMemory)/count);

    }

    private static long getSize () throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }
}
