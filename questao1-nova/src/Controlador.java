import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Controlador {
    private List<Pista> pistaList;
    private List<Thread> pistasThreadList = new ArrayList<Thread>();
    private List<Aviao> pistasAviao = new ArrayList<Aviao>();
    private Lock lock = new ReentrantLock();
    private Condition podeAtribuirAviao = lock.newCondition();
    private long horarioBase;

    Controlador (List<Pista> pistaList, List<Aviao> pistasAviao, long horarioBase) {
        this.pistaList = pistaList;
        this.pistasAviao = pistasAviao;
        this.populaPistasThread();
        this.horarioBase = horarioBase;
    }

    private void populaPistasThread() {
        for (int i = 0; i == this.pistaList.size(); i++) {
            pistasThreadList.add(new Thread(this.pistaList.get(i)));
        }
    }

    public void controla() {
        pistasAviao.get(0).setPista(this.pistaList.get(0));
        pistasAviao.get(0).setHoraAtual(System.currentTimeMillis());
        Thread t = new Thread(pistasAviao.get(0));
        t.start();
    }



//    public void ocupaPista() {
//        try {
//            while (nao ha pista && condicional deu signal) {
//                atribuicao.await()
//            }
//            for(pistasDisponiveis) {
//                pistaDisponivel.colocaAviao // retirar pista disponivel
//            }
//            atribuicao.signal
//        }
//    }
//
//    public void liberaPista() {
//        try {
//            while (ha pista) {
//                atribuicao.await()
//            }
//            for(pistasOcupadas) {
//                pistaOcupada.liberar // Aqui é onde o thread.sleep roda. quando ele acabar, da o signal de dentro da thread
//            }
//            atribuicao.signal
//        }
//    }
//
//    public void controla() {
//        Thread libera = new Thread(new LiberaPista());
//        Thread ocupa = new Thread(new OcupaPista());
//        libera.start();
//        ocupa.start();
//    }

    private boolean todasPistasOcupadas() {
        for(Pista pista : pistaList) {
            if (pista.ocupada == false) {
                return false;
            }
        }
        return true;
    }


//    aviao.executarOperacao
//    if (passou do tempo) {
//        aviao.terminarOperacao
//    }
}
