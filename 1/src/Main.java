public class Main {

    public static void main(String[] args) throws InterruptedException {
        CenterEmployee center = new CenterEmployee();
        ATCThread atc = new ATCThread(center);

        center.start();
        atc.start();

        center.join();
        center.interrupt();
        atc.interrupt();
    }
}
