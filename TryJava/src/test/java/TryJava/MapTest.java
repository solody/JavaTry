package TryJava;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class MapTest {
    @Test
    void test() {
        Map<String, Long> map = new HashMap<>();
        String key1 = "key1";
        map.put(key1, 1L);



        if (map.containsKey(key1)) {
            Long total = map.get(key1);
            map.put(key1, ++total);
        } else {
            map.put(key1, 1L);
        }
    }
}
