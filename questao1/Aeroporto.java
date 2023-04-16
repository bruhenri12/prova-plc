package questao1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class Aeroporto {
    private static Lock lock = new ReentrantLock();
    private static Condition condition = lock.newCondition();
    public static void main(String[] args) {

        Scanner inputs = new Scanner(System.in);

        List<Aviao> avioes = new ArrayList<Aviao>();
        List<Pista> pistas = new ArrayList<Pista>();
        
        System.out.println("Quantidade de avioes saindo: ");
        int paraDecolar = inputs.nextInt();
        
        avioes = appendAvioes(avioes,paraDecolar,"saindo");

        System.out.println("Quantidade de avioes chegando: ");
        int paraChegar = inputs.nextInt();
        
        avioes = appendAvioes(avioes,paraDecolar,"chegando");

        System.out.println("Quantidade de pistas disponiveis: ");
        int totalPistas = inputs.nextInt();
        
        inputs.close();

        avioes = sortAviaoArr(avioes);

        for (int i = 0; i < totalPistas; i++) {
            pistas.add(new Pista(i));
        }

        controlePista(avioes, pistas);
    }

    private static List<Aviao> appendAvioes(List<Aviao> avioes, int quantidade, String status) {
        
        Scanner inputs = new Scanner(System.in);

        for (int i = 0; i < quantidade; i++) {
            System.out.println("Horario do aviao " + (i+1) + ": ");
            long saidaAviao = inputs.nextLong();

            avioes.add(new Aviao(saidaAviao, status));
        }
        
        inputs.close();

        return avioes;
    }

    private static List<Aviao> sortAviaoArr(List<Aviao> arrAviao) {
        Collections.sort(arrAviao);
        return arrAviao;
    }

    private static void controlePista(List<Aviao> avioes, List<Pista> pistas) throws InterruptedException{
        lock.lock();
        try {
            while (!avioes.isEmpty()) {

            }
        } finally {
            lock.unlock();
        }
    }
}
