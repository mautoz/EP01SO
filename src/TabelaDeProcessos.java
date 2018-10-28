import java.util.Collections;
import java.util.ArrayList;

public class TabelaDeProcessos {
    //Precisa inicializar cada uma das Array Lists
    public void inicializaArrayList (ArrayList<BCP> [] b, int N) {
        for (int i = 0;  i < N; i++) {
            b[i] = new ArrayList<>();
        }
    }

    //Processo é deixado de forma que fique em ordem alfabética
    public void inserirProcessoPronto (ArrayList<BCP> [] b, BCP processo, int posicao, int m) {
    	b[m - posicao].add(processo);
        int i = b[m - posicao].size() - 1;

        while (i > 0) {
            int j = b[m - posicao].indexOf(processo);
            //String temp = b[posicao].get(j).getNome();
            //Pode retirar esses prints e comentários inúteis
           if (b[m - posicao].get(j).getNome().compareTo(b[m - posicao].get(i - 1).getNome()) < 0) {
                Collections.swap(b[m - posicao], i - 1, j);
            }
            i--;
        }

    }

    //Prioridade não importa, então é só inserir
    public void inserirProcessoBloqueado (ArrayList<BCP> b, BCP processo) {
        b.add(processo);
    }

    public void removerProcessoPronto (ArrayList<BCP> [] p, BCP processo, int credito) {
        p[credito].remove(processo);
    }

    public void removerProcessoBloqueado (ArrayList<BCP> b, BCP processo) {
        b.remove(processo);
    }
}
