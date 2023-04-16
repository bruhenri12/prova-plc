package questao1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Pista {
    private int pista;
    private Aviao aviao;
    
    private static boolean ocupada = true;
    private static Lock lock = new ReentrantLock();
    private static Condition pistaLivre = lock.newCondition();
    private static Condition pistaOcupada = lock.newCondition();

    public Pista(int pistaId) {
        this.pista = pistaId;
    }

    public void setAviao(Aviao av) {
        this.aviao = av;
    }

    public int getPista() {
        return pista;
    }

    public Aviao getAviao() {
        return aviao;
    }

    public boolean isOcupada() {
        return ocupada;
    }

    public void threadStart() {
        (new Thread(new LiberaPista())).start();
        (new Thread(new OcupaPista())).start();
    }

    public static class LiberaPista implements Runnable {

        public void operacao() throws InterruptedException {

            lock.lock();
            try {
                while (!ocupada) {
                    pistaOcupada.await();
                }
                
                Aviao aviaoAtual = Aeroporto.avioes.get(0);

                long horarioEsperado = aviaoAtual.getHorario();
                String status = aviaoAtual.getStatus();

                long horarioReal = System.currentTimeMillis();

                System.out.println(
                    "Aviao para " + horarioEsperado + ", " + status + " as " + 
                    horarioReal + " com " + (horarioReal-horarioEsperado) + 
                    " de atraso."
                );

                Aeroporto.avioes.remove(0);

                ocupada = false;
                pistaLivre.signal();
            } finally {
                lock.unlock();
            }
        }

        @Override
        public void run() {
            try {
                operacao();
            } catch (InterruptedException e) {
                System.err.println("Erro libera");
            }
        }
    }

    public static class OcupaPista implements Runnable {
        
        public void operacao() throws InterruptedException {

            lock.lock();
            try {
                while (ocupada) {
                    pistaLivre.await();
                }
                
                Aviao aviaoAtual = Aeroporto.avioes.get(0);
                long horarioEsperado = aviaoAtual.getHorario();
                long horarioReal = System.currentTimeMillis();
                
                while (horarioReal <= horarioEsperado) {
                }
                
                Thread.sleep(500);
                
                ocupada = true;
                pistaOcupada.signal();
            } finally {
                lock.unlock();
            }
        }

        @Override
        public void run() {
            try {
                operacao();
            } catch (InterruptedException e) {
                System.err.println("Erro ocupa");
            }
        }
    }
}
