package questao1;

public class Pista implements Runnable {

    private int pistaId;
    private Aviao aviao;

    public Pista(int id) {
        this.pistaId = id;
    }

    public void setAviao(Aviao a) {
        this.aviao = a;
    }

    public int getId() {
        return this.pistaId;
    }

    @Override
    public void run() {

    
        long hrDesejado = this.aviao.getHorario();
        String status = this.aviao.getStatus();
        long hrAtual = System.currentTimeMillis() - Aeroporto.horarioRealStart;
        
        while (hrAtual < hrDesejado + 500) {
            hrAtual = System.currentTimeMillis() - Aeroporto.horarioRealStart;
        }

        long atraso = hrAtual - (hrDesejado + 500);

        System.out.print("Pista " + this.pistaId);
        System.out.print(": Aviao para ");
        System.out.print(hrDesejado);
        System.out.print(" " + status + " ");
        System.out.print("em " + hrAtual + " com atraso de ");
        System.out.println(atraso);

    }
}
