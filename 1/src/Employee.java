public class Employee extends Thread {
    private final int ONE_CALL = 4000;
    private CenterEmployee center;

    public Employee(String name, CenterEmployee center) {
        super(name);
        this.center = center;
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            center.answerCall();
            System.out.printf("%s answering call\n", getName());
            try {
                Thread.sleep(ONE_CALL);
            } catch (InterruptedException e) { }
        }
        System.out.println(getName() + " ended");
    }
}
