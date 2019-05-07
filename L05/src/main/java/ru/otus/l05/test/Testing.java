package ru.otus.l05.test;

import org.junit.Assert;
import ru.otus.l05.testframework.annotation.*;

public class Testing {
    private boolean isBeforeExecuted = false;

    @Before
    public void setUp() {
        isBeforeExecuted = true;
        System.out.println("@Before executed");
    }

    @Test
    public void testOk() {
        Assert.assertTrue(isBeforeExecuted);
        Assert.assertTrue(true);
    }

    @Test
    public void testFail() {
        Assert.fail();
    }

    @Test
    public void testException() {
        throw new RuntimeException("Test threw exception");
    }

    @After
    public void tearDown() {
        System.out.println("@After executed");
    }
}
