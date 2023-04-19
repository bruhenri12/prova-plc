import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Aviao implements Comparable<Aviao>, Runnable {
    long horaSaida;
    String atividade;
    private Pista pista;
    private long horaAtual;

    Aviao (long horaSaida, String atividade) {
        this.horaSaida = horaSaida;
        this.atividade = atividade;
    }

    public void setPista(Pista p) {
        this.pista = p;
    }

    public void setHoraAtual(long hora) {
        this.horaAtual = hora;
    }

    public void executarOperacao() {
        this.pista.lock.lock();
        try {
//            while (passou do tempo == false) {
                try {
                    System.out.print("hora atual ");
                    System.out.println(this.horaAtual);
//                    this.pista.condOcupada.await();
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
//            }
            this.pista.ocupada = true;
            System.out.println("Operacao executada");
            System.out.print("hora atual depois ");
            System.out.println(this.horaAtual);
            this.pista.condOcupada.signal();
        } finally {
            this.pista.lock.unlock();
        }
    }


    @Override
    public int compareTo(Aviao av) {
        return (int) this.horaSaida - (int) av.horaSaida;
    }

    @Override
    public void run() {
        executarOperacao();
    }
}
