package org.example.TryJava;

import org.junit.jupiter.api.Test;

public class SwitchTest {
    @Test
    void testSwitch() {
        int indicator = 0;
        switch (indicator) {
            case 0: {
                System.out.println(0);
                break;
            }
            case 1: {
                System.out.println(1);
                break;
            }
            case 2: {
                System.out.println(2);
                break;
            }
            case 3: {
                System.out.println(3);
                break;
            }
            case 99: {
                System.out.println(99);
                break;
            }
        }
    }
}
