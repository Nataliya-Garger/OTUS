package ru.otus.l02;

public class MeasureObject {

    protected static long getObjectSize (ObjectType objectType, int count) throws InterruptedException {
        long startMemory = getMemorySize();
        Object[] array = new Object[count];
        for (int j = 0; j < count; j++) {
            array[j] = ObjectFactory.createObject(objectType);
        }
        long endMemory = getMemorySize();
        return (endMemory-startMemory)/count;
    }


    private static long getMemorySize() throws InterruptedException {
        System.gc();
        Thread.sleep(10);
        Runtime runtime = Runtime.getRuntime();
        return runtime.totalMemory() - runtime.freeMemory();
    }

}
