import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
public class CenterEmployee extends Thread {
    private final int TIME_WORK = 10000;
    private final int EMPLOYEES_COUNT = 5;
    private final Queue<Object> callList;
    private final List<Employee> employeeList;
    private ExecutorService pool;

    public CenterEmployee() {
        callList = new ConcurrentLinkedQueue<>();
        employeeList = new LinkedList<>();
        pool = Executors.newFixedThreadPool(EMPLOYEES_COUNT);
    }

    public void call(Object object) {
        callList.offer(object);
    }

    public Object answerCall() {
        return callList.poll();
    }

    public void run() {
        for (int i = 0; i < EMPLOYEES_COUNT; i++) {
            Employee employee = new Employee("Employee " + (i + 1), this);
            employeeList.add(employee);
            pool.submit(employee);
        }
        try {
            Thread.sleep(TIME_WORK);
            pool.shutdown();
            employeeList.forEach(x -> x.interrupt());
        } catch (InterruptedException e) { }
        System.out.println("Center end");

    }
}
