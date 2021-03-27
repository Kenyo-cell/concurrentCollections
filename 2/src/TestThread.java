import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TestThread extends Thread {
    private final int THREADS = 600;
    private final int TIMER = 10000;
    private ConcurrentMap<Integer, Integer> cMap;
    private Map<Integer, Integer> map;
    ExecutorService pool = Executors.newFixedThreadPool(THREADS);

    public TestThread(Map<Integer, Integer> map) {
        this.map = map;
    }

    public TestThread(ConcurrentMap<Integer, Integer> cMap) {
        this.cMap = cMap;
    }

    private void testMapRead() {
        map.get(Main.random.nextInt(Main.SIZE));
    }

    private void testCMapRead() {
        cMap.get(Main.random.nextInt(Main.SIZE));
    }

    private void testMapWrite() {
        int i = Main.random.nextInt(Main.SIZE);
        map.put(i, i * i);
    }

    private void testCMapWrite() {
        int i = Main.random.nextInt(Main.SIZE);
        map.put(i, i * i);
    }

    @Override
    public void run() {
        long start = System.currentTimeMillis();
        if (map != null) {
            for (int i = 0; i < THREADS; i++) {
                pool.submit(new Thread(this::testMapRead));
            }
            pool.shutdown();
            System.out.println("Time " + (System.currentTimeMillis() - start));

            pool = Executors.newFixedThreadPool(THREADS);
            start = System.currentTimeMillis();
            for (int i = 0; i < THREADS; i++) {
                pool.submit(new Thread(this::testMapWrite));
            }
            pool.shutdown();
            System.out.println("Time " + (System.currentTimeMillis() - start));
        } else if (cMap != null) {
            for (int i = 0; i < THREADS; i++) {
                pool.submit(new Thread(this::testCMapRead));
            }
            pool.shutdown();
            System.out.println("Time " + (System.currentTimeMillis() - start));

            pool = Executors.newFixedThreadPool(THREADS);
            start = System.currentTimeMillis();
            for (int i = 0; i < THREADS; i++) {
                pool.submit(new Thread(this::testCMapWrite));
            }
            pool.shutdown();
            System.out.println("Time " + (System.currentTimeMillis() - start));
        }
    }
}
