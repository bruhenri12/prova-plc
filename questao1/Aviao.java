package questao1;

public class Aviao implements Comparable<Aviao> {
    private long horario;
    private String status;

    public Aviao(long hr, String st) {
        this.horario = hr;
        this.status = st;
    }

    public long getHorario() {
        return this.horario;
    }
    
    public String getStatus() {
        return this.status;
    }

    @Override
    public int compareTo(Aviao av) {
        return (int) this.horario - (int) av.horario;
    }
}
