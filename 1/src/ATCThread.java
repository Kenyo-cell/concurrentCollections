public class ATCThread extends Thread {
    private final int INTERVAL = 9000;
    private final int CALLS_PER_IT = 10;

    private CenterEmployee center;

    public ATCThread(CenterEmployee center) {
        this.center = center;
    }


    @Override
    public void run() {
        while (!center.isInterrupted()) {
            for (int i = 0; i < CALLS_PER_IT; i++) {
                center.call(new Object());
            }
            try {
                Thread.sleep(INTERVAL);
            } catch (InterruptedException e) { }
        }
        System.out.println("ATC ended");
    }
}
