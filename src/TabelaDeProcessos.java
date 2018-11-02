import java.util.Collections;
import java.util.ArrayList;

public class TabelaDeProcessos {
	public ArrayList<BCP> [] prontos;
    public ArrayList<BCP> bloqueados;
    
    public ArrayList<BCP> [] getProntos () {
    	return prontos;
    }
    
    public ArrayList<BCP> getBloqueados () {
    	return bloqueados;
    }
    	
    //Precisa inicializar cada uma das ArrayLists
	//� um Array de Arrays
    @SuppressWarnings("unchecked")
    public void inicializaArrayList (int N) {
    	bloqueados = new ArrayList<>();
    	prontos = (ArrayList<BCP>[])new ArrayList[N + 1];
        for (int i = 0;  i < N + 1; i++) {
            prontos[i] = new ArrayList<>();
        }
    }

    //Processo � deixado de forma que fique em ordem alfab�tica
    //Como tinha que criar uma lista do maior para o menor, � necess�rio
    //fazer o 'm-posicao'. A �ltima fila � dos processos com 0 cr�ditos.
    public void inserirProcessoPronto (TabelaDeProcessos t, BCP processo, int posicao, int m) {
    	ArrayList<BCP> [] p = t.getProntos();
    	p[m - posicao].add(processo);
        int i = p[m - posicao].size() - 1;

        while (i > 0) {
        	int j = p[m - posicao].indexOf(processo);
        	if (p[m - posicao].get(j).getNome().compareTo(p[m - posicao].get(i - 1).getNome()) < 0) {
        		Collections.swap(p[m - posicao], i - 1, j);
            }
            i--;
        }
    }

    //Prioridade n�o importa, ent�o � s� inserir.
    public void inserirProcessoBloqueado (ArrayList<BCP> b, BCP processo) {
        b.add(processo);
    }

    //Remove o 'processo' na fila de prontos.
    public void removerProcessoPronto (ArrayList<BCP> [] p, BCP processo, int credito) {
        p[credito].remove(processo);
    }

    //Remove os bloqueados
    public void removerProcessoBloqueado (ArrayList<BCP> b, BCP processo) {
        b.remove(processo);
    }
}