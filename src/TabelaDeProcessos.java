//package so_epiescalonador;

import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.io.IOException;

public class TabelaDeProcessos {
    //Precisa inicializar cada uma das Array Lists
    public void inicializaArrayList (ArrayList<BCP> [] b, int N) {
        System.out.println("N " + N);

        for (int i = 0;  i < N; i++) {
            b[i] = new ArrayList<>();
        }
        System.out.println("Fim N " + N);
    }

    //Processo � deixado de forma que fique em ordem alfab�tica
    public void inserirProcessoPronto (ArrayList<BCP> [] b, BCP processo, int posicao) {
        System.out.println("Posicao " + posicao);
        b[posicao].add(processo);

        int i = b[posicao].size() - 1;
        while (i > 0) {
            int j = b[posicao].indexOf(processo);
            //String temp = b[posicao].get(j).getNome();
            //Pode retirar esses prints e coment�rios in�teis
            System.out.println("j = " + j + " " + b[posicao].get(i - 1).getNome() + " " + b[posicao].get(j).getNome());
            if (b[posicao].get(j).getNome().compareTo(b[posicao].get(i - 1).getNome()) < 0) {
                Collections.swap(b[posicao], i - 1, j);
            }
            i--;
        }

    }

    //Prioridade n�o importa, ent�o � s� inserir
    public void inserirProcessoBloqueado (ArrayList<BCP> b, BCP processo) {
        b.add(processo);
    }

    //Talvez n�o precise
    public void inserirProcessoExecutando (ArrayList<BCP> exe, BCP processo) {
        exe.add(processo);
    }

    public void removerProcessoPronto (ArrayList<BCP> [] p, BCP processo, int credito) {
        int j = p[credito].indexOf(processo);
        p[credito].remove(p[credito].get(j));
    }

    public void removerProcessoBloqueado (ArrayList<BCP> b, BCP processo) {
        b.remove(processo);
    }

    public void removerProcessoExecutando (ArrayList<BCP> exe, BCP processo) {
        exe.remove(processo);
    }

    //**IGNOREM** tudo que est� no main � tudo para testes
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
            esc.inicializarArray(ler.gettamArrayList());

            System.out.println("Quantum = " + esc.getNCom());

            System.out.println("\n=================================================\n");
            System.out.println("Exemplo de leitura de comando para o BCP[2]");

            for (int k = 0; k < 10; k++) {
                System.out.println("Comando " + k + " = " + teste[2].getComando(k));
            }

            System.out.println("Prioridade do Processo " + teste[2].getNome() + " � " + teste[2].getPrioridade());
            System.out.println("Valor do Quantum " + esc.getNCom());



            //Testes com Array List


            //Primeiro: ler todas as 10 prioridades, colocar em ordem e criar um novo
            //vetor, mas sem as repeti��es
            ler.numPrioridades(ler.getArrayPrioridades());

            //Bloco de Prints para testes, pode apagar
            for (int i = 0; i < 10; i++)
                System.out.println(teste[i].getPrioridade() + " " + ler.getPosicaoDaPrioridade(teste[i].getPrioridade()) + " ");
            System.out.println("Size() = " + bloqueados.size());
            System.out.println("Bloqueados = ");
            //Como  pegar o processo em remover
            for(int i = 0; i < bloqueados.size(); i++)
                System.out.print(bloqueados.get(i).getNome() + "\n");
            System.out.println("ler.gettamArrayList() " + ler.gettamArrayList());
            //Fim do bloco de prints para deletar


            //Inicializar o Arraylist com processos prontos por prioridade
            //O tamanho � de acordo com ler.gettamArrayList()
            prontos = (ArrayList<BCP>[])new ArrayList[ler.gettamArrayList()];
            tp.inicializaArrayList(prontos, ler.gettamArrayList());

            for (int i = 0; i < 10; i++) {
                tp.inserirProcessoPronto(prontos, teste[i], ler.getPosicaoDaPrioridade(teste[i].getPrioridade()));
                tp.inserirProcessoBloqueado(bloqueados, teste[i]);
            }
            //Tudo print para ver se colocou corretamente, futuramente ser� deletado
            System.out.println("Lista de Prioridades: ");
            System.out.println();
            //Teste de impress�o para verificar se est�o corretamente alocados.
            for (int i = 0;  i < ler.gettamArrayList(); i++) {
                System.out.println("Posi��o da Prioridade: " + (i+1));
                for (BCP aux : prontos[i]) {
                    System.out.println("Nome do processo: " + aux.getNome() + " Prioridade: " + aux.getPrioridade() + " Estado do Processo: " + aux.getEstadoProcesso());
                }
                System.out.println();
            }

            /* Ignorem tudo deste LIST*/
            //Ignorem isso, � um trecho da internet para eu entender como isso funciona...
            List<String> list = new ArrayList<String>();
            list.add("1");
            list.add("2");
            list.add("3");
            list.add("4");

            String toMoveUp = "3";
            while (list.indexOf(toMoveUp) != 0) {
                int i = list.indexOf(toMoveUp);
                Collections.swap(list, i, i - 1);
            }
            System.out.println(list);
            list.add("5");
            System.out.println(list);
            list.remove(0);
            System.out.println(list);
            System.out.println(list.indexOf(toMoveUp));
            //Tudo acima � para ignorar

        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
    }
/*
    //recebe um array de strings com cada posicao como linha do arq de processo
    //retorna null se array vazio
    public static BCP criaBCP(String[] param){
        BCP pbcp = null;
        if(param.length == 0); //vazio, nao faz nada
        else{
            pbcp= new BCP(param);//cria o bcp do processo usando construtor
        }
        return pbcp;
    }
*/
}
