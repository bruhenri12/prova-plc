package questao1;

public class Pista implements Runnable{
    private int pista;
    private boolean ocupada = false;
    private Aviao aviao;

    public Pista(int pistaId) {
        this.pista = pistaId;
    }

    public void setAviao(Aviao av) {
        this.aviao = av;
    }

    public int getPista() {
        return this.pista;
    }

    public boolean isOcupada() {
        return this.ocupada;
    }

    @Override
    public void run() {
        
        System.out.println("Aviao ");
    }
}
