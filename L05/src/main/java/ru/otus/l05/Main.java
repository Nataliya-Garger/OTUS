package ru.otus.l05;

import ru.otus.l05.test.Testing;
import ru.otus.l05.testframework.TestRunner;

public class Main {
    public static void main(String[] args) throws Exception {
        TestRunner.runByClassName(Testing.class.getName());

    }
}
