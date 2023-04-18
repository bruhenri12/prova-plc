package questao1;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Controlador{

    private static Lock lock = new ReentrantLock();
    private static Condition liberou = lock.newCondition();
    private static Condition ocupou = lock.newCondition();

    public static void Operador() {
        Thread libera = new Thread(new LiberaPista());
        Thread ocupa = new Thread(new OcupaPista());
        libera.start();
        ocupa.start();
    }
    
    public static class LiberaPista implements Runnable {

        private void operacao() throws InterruptedException {

            lock.lock();
            try {
                while (Aeroporto.pistas.size() == Aeroporto.totalPistas) {
                    ocupou.await();
                }

                Aeroporto.pistas.add(new Pista(Aeroporto.pistaLivreId));
                liberou.signal();

            } finally {
                lock.unlock();
            }
        }

        @Override
        public void run() {
            do {
                try {
                    operacao();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (!Aeroporto.avioes.isEmpty());
        }
    }

    public static class OcupaPista implements Runnable {
        
        private void operacao() throws InterruptedException {

            lock.lock();
            try {
                while (Aeroporto.pistas.isEmpty()) {
                    liberou.await(500L,TimeUnit.MILLISECONDS);
                }
                
                Pista pistaAtual = Aeroporto.pistas.get(0);
                pistaAtual.setAviao(Aeroporto.avioes.get(0));
                
                Aeroporto.pistaLivreId = pistaAtual.getId();
                (new Thread(pistaAtual)).start();
                
                Aeroporto.avioes.remove(0);
                Aeroporto.pistas.remove(0);

                ocupou.signal();
            } finally {
                lock.unlock();
            }
        }

        @Override
        public void run() {
            while (!Aeroporto.avioes.isEmpty()) {
                try {
                    operacao();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
