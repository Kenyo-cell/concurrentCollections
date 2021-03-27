import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
public class Main {
    public static final Random random = new Random();
    public static final int SIZE = 100000;

    private static ConcurrentMap<Integer, Integer> cMap;
    private static Map<Integer, Integer> map;

    private static Map<Integer, Integer> fillMap(Map<Integer, Integer> map, int[] series) {
        map = new HashMap<>();
        for (int i : series) {
            map.put(i, i);
        }
        return map;
    }

    private static ConcurrentMap<Integer, Integer> fillCoMap(ConcurrentMap<Integer, Integer> map, int[] series) {
        map = new ConcurrentHashMap<>();
        for (int i : series) {
            map.putIfAbsent(i, i);
        }
        return map;
    }

    private static int[] generateSeries() {
        int[] series = new int[SIZE];
        for (int i = 0; i < SIZE; i++) {
            series[i] = i;
        }
        return series;
    }



    public static void main(String[] args) throws InterruptedException {
        int[] series = generateSeries();
        map = Collections.synchronizedMap(fillMap(map, series));
        cMap = fillCoMap(cMap, series);
        TestThread tt = new TestThread(map);
        tt.start();
        tt.join();
        tt = new TestThread(cMap);
        tt.start();
        tt.join();
        tt.interrupt();
    }
}
