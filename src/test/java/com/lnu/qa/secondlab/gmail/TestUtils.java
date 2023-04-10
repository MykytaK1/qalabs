package com.lnu.qa.secondlab.gmail;

import java.time.Duration;

public class TestUtils {

    public static void sleep(Duration duration){
        try {
            Thread.sleep(duration.toMillis());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
