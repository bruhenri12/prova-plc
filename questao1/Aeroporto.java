package questao1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Aeroporto {

    private static Scanner inputs = new Scanner(System.in);
    
    private static int totalPistas;
    
    public static List<Aviao> avioes = new ArrayList<Aviao>();
    public static List<Pista> pistas = new ArrayList<Pista>();
    
    public static void main(String[] args) {


        System.out.println("Quantidade de avioes saindo: ");
        int paraDecolar = inputs.nextInt();
        
        avioes = appendAvioes(avioes,paraDecolar,"saindo");

        System.out.println("Quantidade de avioes chegando: ");
        int paraChegar = inputs.nextInt();
        
        avioes = appendAvioes(avioes,paraChegar,"chegando");

        System.out.println("Quantidade de pistas disponiveis: ");
        totalPistas = inputs.nextInt();
        
        inputs.close();

        avioes = sortAviaoArr(avioes);

        for (int i = 0; i < totalPistas; i++) {
            pistas.add(new Pista(i));
        }

        for (int i = 0; i < totalPistas; i++) {
            pistas.get(i).threadStart();
        }
    }

    private static List<Aviao> appendAvioes(List<Aviao> avioes, int quantidade, String status) {

        for (int i = 0; i < quantidade; i++) {
            System.out.println("Horario do aviao " + (i+1) + ": ");
            long saidaAviao = inputs.nextLong();

            avioes.add(new Aviao(saidaAviao, status));
        }

        return avioes;
    }

    private static List<Aviao> sortAviaoArr(List<Aviao> arrAviao) {
        Collections.sort(arrAviao);
        return arrAviao;
    }
}
