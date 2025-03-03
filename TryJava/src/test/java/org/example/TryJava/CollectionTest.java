package org.example.TryJava;

import org.junit.jupiter.api.Test;

import java.util.*;

public class CollectionTest {
    @Test
    void testPrint() {
        Queue<Long> queue = new LinkedList<>();
        queue.offer(123L);
        queue.offer(12L);
        queue.offer(124563L);

        System.out.println("values: " + queue);
    }

    @Test
    void testListSorting() {
        List<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(1);
        list.add(4);
        list.add(3);
        list.add(18);
        list.add(8);
        System.out.println("values: " + list);

        Collections.sort(list, (a, b) -> {
            if (a>b) return 1;
            if (a==b) return 0;
            if (a<b) return -1;
            return 0;
        });

        System.out.println("values: " + list.toString());

        int totalCount = 0;
        for (int i = list.size() - 1; i >= 0; i--) {
            totalCount += list.get(i);
            if (i == list.size() - 3) {
                break;
            }
        }
        System.out.println("total count: " + totalCount);

        Random random = new Random();
        int randomNumber = random.nextInt(totalCount) % (totalCount + 1) + 1;
        System.out.println("randomNumber: " + randomNumber);

//        int d = (random.nextInt(20)%(20+1)) + 1;
        int d = 1%30;
        System.out.println("cal: " + d);
    }
}
