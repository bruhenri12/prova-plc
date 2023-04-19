import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pista implements Runnable {
    private int pistaId;
    public boolean ocupada = true;
    public Lock lock = new ReentrantLock();
    public Condition condOcupada = lock.newCondition();
    private Condition podeVir;

    Pista(int pistaId) {
        this.pistaId = pistaId;
    }

    public void ocupaPista() {
        lock.lock();
        try {
            while (ocupada == true) {
                try {
                    condOcupada.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ocupada = true;
            condOcupada.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void liberaPista() {
        lock.lock();
        try {
            while (ocupada == false) {
                try {
                    condOcupada.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            ocupada = false;
            condOcupada.signalAll();
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void run() {
        try {
            this.ocupaPista();
            Thread.sleep(500);
            this.liberaPista();
            System.out.println("dormiu");
        } catch (InterruptedException e) {

        }
    }
}
