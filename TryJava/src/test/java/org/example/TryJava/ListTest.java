package org.example.TryJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ListTest {
    @Test
    void testSorting() {
        List<Integer> fullList = new ArrayList<>();
        fullList.add(56);
        fullList.add(5667);
        fullList.add(562354645);
        fullList.add(563);
        fullList.add(56456);
        fullList.add(5);

        Collections.sort(fullList, (a, b) -> {
            if (a < b) return -1;
            else if (a == b) {
                return 0;
            }
            else {
                return 1;
            }
        });

        List<Integer> apartList = split(fullList, 5000);

        Assertions.assertEquals(3, fullList.size());
        Assertions.assertEquals(3, apartList.size());
    }

    private List<Integer> split(List<Integer> fullList, int predicate) {
        List<Integer> apartList = new ArrayList<>();

        fullList.removeIf(item -> {
            if (item > predicate) {
                apartList.add(item);
                return true;
            } else {
                return false;
            }
        });

        return apartList;
    }
}
