import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Aeroporto {
    public static long horaInicial;
    public static int totalPistas;

    private static Scanner rpnRead;

    public static List<Aviao> avioes = new ArrayList<>();
    public static List<Pista> pistas = new ArrayList<Pista>();

    private static final Lock lock = new ReentrantLock();
    private static final Condition pistaLivre = lock.newCondition();
    private static final Condition pistaOcupada = lock.newCondition();

    public static void main(String[] args) {
        try {
            getInputs();
        } catch (FileNotFoundException e) {
            System.err.println("Arquivo nao encontrado.");
        }

        for (int i = 0; i < totalPistas; i++) {
            pistas.add(new Pista());
        }

        Collections.sort(avioes);

        horaInicial = System.currentTimeMillis();

        for (int i = 0; i < avioes.size(); i++) {
            Thread av = new Thread(avioes.get(i));
            av.start();
        }


    }

    private static void appendAvioes(int quantidade, String status) {

        for (int i = 0; i < quantidade; i++) {
            System.out.print("Horario do aviao " + (i+1) + ": ");
            long saidaAviao = Long.parseLong(rpnRead.nextLine());
            System.out.println(saidaAviao);

            avioes.add(new Aviao(saidaAviao, status));
        }
    }

    private static void getInputs() throws FileNotFoundException {

        System.out.println("Insira o caminho do arquivo teste:");
        Scanner usrInput = new Scanner(System.in);
        String fileName = usrInput.nextLine();
        usrInput.close();

        File rpnFile = new File(fileName);
        rpnRead = new Scanner(rpnFile);

        System.out.println("Entrada:");

        System.out.print("Quantidade de avioes saindo: ");
        int paraDecolar = Integer.parseInt(rpnRead.nextLine());
        System.out.println(paraDecolar);

        appendAvioes(paraDecolar,"saindo");

        System.out.print("Quantidade de avioes chegando: ");
        int paraChegar = Integer.parseInt(rpnRead.nextLine());
        System.out.println(paraChegar);

        appendAvioes(paraChegar,"chegando");

        System.out.print("Quantidade de pistas disponiveis: ");
        totalPistas = Integer.parseInt(rpnRead.nextLine());
        System.out.println(totalPistas);

        rpnRead.close();
    }

    public static class Pista {

    }
    public static class Aviao implements Comparable<Aviao>, Runnable {
        private final long horario;
        private final String status;


        public Aviao(long hr, String st) {
            this.horario = hr;
            this.status = st;
        }

        @Override
        public int compareTo(Aviao av) {
            return (int) this.horario - (int) av.horario;
        }

        @Override
        public void run() {
            try {
                print();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        private void print() throws InterruptedException {
            lock.lock();
            try {
                long horaAtual = System.currentTimeMillis() - horaInicial;

                if (horaAtual < this.horario) {
                    Thread.sleep(this.horario-horaAtual);
                }
                if (pistas.isEmpty()) {
                    pistaLivre.await();
                }
                pistas.remove(0);

                Thread.sleep(500);
                horaAtual = System.currentTimeMillis() - horaInicial;

                long atraso = horaAtual - (this.horario + 500);

                System.out.print("Aviao para " + this.horario);
                System.out.print(" " + this.status);
                System.out.print(" as " + horaAtual);
                System.out.println(" com atraso de " + atraso);

                pistas.add(new Pista());

                pistaLivre.signalAll();
            } finally {
                lock.unlock();
            }
        }
    }
}