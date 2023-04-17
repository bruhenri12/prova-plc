package questao1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Aeroporto {

    public static int totalPistas;
    public static long horarioRealStart;
    public static int pistaLivreId;

    private static Scanner inputs = new Scanner(System.in);
    
    public static List<Aviao> avioes = new ArrayList<Aviao>();
    public static List<Pista> pistas = new ArrayList<Pista>();
    
    public static void main(String[] args) {


        System.out.print("Quantidade de avioes saindo: ");
        int paraDecolar = inputs.nextInt();
        
        avioes = appendAvioes(avioes,paraDecolar,"saindo");

        System.out.print("Quantidade de avioes chegando: ");
        int paraChegar = inputs.nextInt();
        
        avioes = appendAvioes(avioes,paraChegar,"chegando");

        System.out.print("Quantidade de pistas disponiveis: ");
        totalPistas = inputs.nextInt();
        
        inputs.close();

        avioes = sortAviaoArr(avioes);

        for (int i = 1; i < totalPistas; i++) {
            pistas.add(new Pista(i));
        }
        
        horarioRealStart = System.currentTimeMillis();

        Controlador.Operador();
    }

    private static List<Aviao> appendAvioes(List<Aviao> avioes, int quantidade, String status) {

        for (int i = 0; i < quantidade; i++) {
            System.out.print("Horario do aviao " + (i+1) + ": ");
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
