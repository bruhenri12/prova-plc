import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Colmeia {
    public static int totalOperarios, totalTarefas;
    public static List<String> totals, tarefas = new ArrayList<>();
    public static List<String> tarefasConcluidas = new ArrayList<>();
    public static void main(String[] args) {
        try {
            getInput();
        } catch (FileNotFoundException e) {
            System.err.println("Nao foi possivel ler o arquivo.");
        }

        ExecutorService rainhaComanda = Executors.newFixedThreadPool(totalOperarios);

        System.out.println("Saida:");
        while (!tarefas.isEmpty()) {

            boolean fimDaFila = false;

            String tarefaAtual = tarefas.get(0);
            tarefas.remove(0);

            List<String> propriedades = List.of(tarefaAtual.split(" "));

            String id = propriedades.get(0);
            long tempo = Long.parseLong(propriedades.get(1));

            for (int i = 2; i < propriedades.size(); i++) {
                if (!tarefasConcluidas.contains(propriedades.get(i))) {
                    fimDaFila = true;
                    break;
                }
            }

            if (fimDaFila) {
                tarefas.add(tarefaAtual);
            } else {
                Runnable operario = new Operario(tempo);
                rainhaComanda.execute(operario);
                tarefasConcluidas.add(id);

                System.out.println("tarefa " + id + " feita");
            }

        }
        rainhaComanda.shutdown();
    }

    private record Operario(long tempo) implements Runnable {

        @Override
            public void run() {
                try {
                    Thread.sleep(tempo);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    private static void getInput() throws FileNotFoundException {
        System.out.println("Insira o caminho do arquivo teste:");
        Scanner usrInput = new Scanner(System.in);
        String fileName = usrInput.nextLine();
        usrInput.close();

        File rpnFile = new File(fileName);
        Scanner rpnRead = new Scanner(rpnFile);

        System.out.println("Entrada:");
        String readData = rpnRead.nextLine();
        System.out.println(readData);

        totals = List.of(readData.split(" "));

        totalOperarios = Integer.parseInt(totals.get(0));
        totalTarefas = Integer.parseInt(totals.get(1));

        for (int i = 0; i < totalTarefas; i++) {
            readData = rpnRead.nextLine();
            System.out.println(readData);
            tarefas.add(readData);
        }
        rpnRead.close();
    }
}
