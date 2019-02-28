package ru.otus.l02;

import jdk.nashorn.internal.ir.debug.ObjectSizeCalculator;

public class Main {

    public static void main(String[] args) throws Exception {
        System.out.println(ObjectSizeCalculator.getObjectSize(ObjectFactory.createObject(ObjectType.CHARACTER)));
    }
}
