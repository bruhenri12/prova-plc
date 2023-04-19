import javax.swing.plaf.TableHeaderUI;
import javax.swing.plaf.synth.SynthTabbedPaneUI;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Aeroporto {
    public static Scanner sn = new Scanner(System.in);
    public static List<Aviao> avioes = new ArrayList<Aviao>();
    public static List<Pista> pistas = new ArrayList<Pista>();
    public static Controlador controlador;

    public static void main(String[] args) {
        System.out.print("Digite o número de aviões decolando: ");
        int numAvioesDecolando = sn.nextInt();

        avioes = appendAvioes(avioes, numAvioesDecolando, "Decolando");

        System.out.print("Digite o número de aviões aterrissando: ");
        int numAvioesAterrissando = sn.nextInt();

        avioes = appendAvioes(avioes, numAvioesAterrissando, "Aterrissando");

        System.out.print("Quantidade de pistas disponiveis: ");
        int numPistas = sn.nextInt();

        sn.close();

        avioes = sortAviaoArr(avioes);

        printAviaoPista(avioes, numPistas);

        for (int i = 0; i < numPistas; i++) {
            pistas.add(new Pista(i));
        }

        controlador = new Controlador(pistas, avioes, System.currentTimeMillis());
        controlador.controla();
//        Thread t = new Thread(pistas.get(0));
//        t.start();
//        System.out.println("pistas");

//        Controlador.controlar();

    }

    private static List<Aviao> appendAvioes(List<Aviao> avioes, int quantidade, String status) {

        for (int i = 0; i < quantidade; i++) {
            System.out.print("Horario do aviao " + (i+1) + ": ");
            long saidaAviao = sn.nextLong();

            avioes.add(new Aviao(saidaAviao, status));
        }

        return avioes;
    }

    private static List<Aviao> sortAviaoArr(List<Aviao> arrAviao) {
        Collections.sort(arrAviao);
        return arrAviao;
    }

    private static void printAviaoPista(List<Aviao> avioes, int numPistas) {
        for (Aviao aviao:avioes) {
            System.out.print(aviao.horaSaida);
            System.out.print(" ");
            System.out.print(aviao.atividade);
            System.out.println("");
        }
        System.out.print("Num pistas: ");
        System.out.println(numPistas);
    }
}
