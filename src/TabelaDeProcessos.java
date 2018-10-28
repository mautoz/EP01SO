import java.util.Collections;
import java.util.ArrayList;
import java.io.IOException;

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
    	int j = p[credito].indexOf(processo);
        p[credito].remove(p[credito].get(j));
    }

    public void removerProcessoBloqueado (ArrayList<BCP> b, BCP processo) {
        b.remove(processo);
    }

    //**IGNOREM** tudo que está no main é tudo para testes
    public static void main(String[] args) throws IOException  {
        Ler ler = new Ler();
        BCP [] teste = new BCP[10];
        Escalonador esc = new Escalonador();
        ArrayList<BCP> bloqueados = new ArrayList<>();
        ArrayList<BCP> [] prontos;
        TabelaDeProcessos tp = new TabelaDeProcessos();
        Escrever escrever = new Escrever();

        try {
            escrever.criarLog(esc.getNCom ());
            ler.lerArq(teste, esc, escrever);
            //esc.inicializarArray(ler.maxCredito());

            System.out.println("Quantum = " + esc.getNCom());

            System.out.println("\n=================================================\n");
            System.out.println("Exemplo de leitura de comando para o BCP[2]");

            for (int k = 0; k < 10; k++) {
                System.out.println("Comando " + k + " = " + teste[2].getComando(k));
            }

            System.out.println("Prioridade do Processo " + teste[2].getNome() + " é " + teste[2].getPrioridade());
            System.out.println("Valor do Quantum " + esc.getNCom());

            //Inicializar o Arraylist com processos prontos por prioridade
            //O tamanho é de acordo com ler.maxCredito() 
            prontos = (ArrayList<BCP>[])new ArrayList[ler.maxCredito() + 1];
            tp.inicializaArrayList(prontos, ler.maxCredito() + 1);

            for (int i = 0; i < 10; i++) {
                tp.inserirProcessoPronto(prontos, teste[i], teste[i].getPrioridade(), ler.maxCredito());
                tp.inserirProcessoBloqueado(bloqueados, teste[i]);
            }
            //Tudo print para ver se colocou corretamente, futuramente será deletado
            System.out.println("Lista de Prioridades: ");
            System.out.println();
            //Teste de impressão para verificar se estão corretamente alocados.
            for (int i = 0;  i < ler.maxCredito() + 1; i++) {
                System.out.println("Posição da Prioridade: " + i);
                for (BCP aux : prontos[i]) {
                    System.out.println("Nome do processo: " + aux.getNome() + " Prioridade: " + aux.getPrioridade() + " Estado do Processo: " + aux.getEstadoProcesso());
                }
                System.out.println();
            }

        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
    }
}
