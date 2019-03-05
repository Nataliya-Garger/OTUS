package ru.otus.l02;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

public class Main {
    private static int count = 1_000_000;
//    private static ObjectType objectType = ObjectType.STRING;
    private static ObjectType objectType = ObjectType.INTEGR;
//    private static ObjectType objectType = ObjectType.LONG;
//    private static ObjectType objectType = ObjectType.CHARACTER;
//    private static ObjectType objectType = ObjectType.DOUBLE;


    public static void main(String[] args) throws Exception {

        System.out.println("ObjectSizeCalculator = " + ObjectSizeCalculator.getObjectSize(ObjectFactory.createObject(objectType)));
        System.out.println("CustomCalculator = " + MeasureObject.getObjectSize(objectType, count));

    }


}
