import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;

public class Ler {
    private static int [] prioridades = new int[10];
    private static int [] prioridadesSemRepeticao;
    private static int tamArrayList;

    public static int [] getArrayPrioridades () {
        return prioridades;
    }

    public static int gettamArrayList() {
        return tamArrayList;
    }
    //Método inútil, apagar antes de entregar
    public static void imprimir () {
        Arrays.sort(prioridades);
        for (int i = 0; i < prioridades.length; i++)
            System.out.print(prioridades[i] + " ");
    }

    //Para preencher a lista de prioridades, é preciso saber a posição
    public static int getPosicaoDaPrioridade (int p) {
        int i = 0;

        while (i < prioridadesSemRepeticao.length && prioridadesSemRepeticao[i] != p)
            i++;

        return i;
    }

    //Cria um vetor com as prioridades passadas mas sem repetição.
    //Serve para ter uma posição 'i' para uma prioridade 'p'.
    public static void numPrioridades (int [] v) {
        int i = 1, j, contador = 1;

        Arrays.sort(v);

        while (i < v.length) {
            if (prioridades[i] != prioridades[i - 1])
                contador++;
            i++;
        }

        tamArrayList = contador;
        System.out.println("Contador = " + contador);

        prioridadesSemRepeticao = new int[contador];
        prioridadesSemRepeticao[0] = prioridades[0];
        i = 1;
        j = 1;
        while (i < v.length) {
            if (v[i - 1] != v[i])
                prioridadesSemRepeticao[j++] = v[i];
            i++;
        }
        //Precisa criar do maior para o menor, então vamos inverter a lista
        i = 0;
        while (i < prioridadesSemRepeticao.length/2) {
            int temp = prioridadesSemRepeticao[i];
            prioridadesSemRepeticao[i] = prioridadesSemRepeticao[prioridadesSemRepeticao.length - 1 - i];
            prioridadesSemRepeticao[prioridadesSemRepeticao.length - 1 - i] = temp;
            i++;
        }

        System.out.println("prioridadesSemRepeticao = ");
        for (i = 0; i < prioridadesSemRepeticao.length; i++)
            System.out.print(prioridadesSemRepeticao[i] + " ");
    }

    public static void lerArq (BCP [] bcp, Escalonador esc, Escrever escrever) throws IOException {
        FileReader processos, prior;
        BufferedReader lerProcessos, lerPrior;

        prior = new FileReader("prioridades.txt");
        lerPrior = new BufferedReader(prior);

        //Laço para ler do 01.txt até 10.txt. Em paralelo, será lido
        //as prioridades de 'prioridade.txt'.
        for (int i = 1; i < 11;  i++) {
            StringBuffer buffer = new StringBuffer(40);
            String filename;

            if (i <= 9)
                filename = buffer.append("0").append(i).append(".txt").toString();
            else
                filename = buffer.append(i).append(".txt").toString();
            System.out.println(filename);

            processos = new FileReader(filename);
            lerProcessos = new BufferedReader(processos);

            //Garantir a leitura das primeiras linhas de processo e prioridade
            String linhaP = lerProcessos.readLine(); //Linha 0 é o Nome do processo
            String linhaPrior = lerPrior.readLine(); //Linha da prioridade do processo 'i'.
            bcp[i - 1] = new BCP (linhaP);
            escrever.escrevendoCarregando(linhaP);

            bcp[i - 1].setEstadoProcesso('p');
            bcp[i - 1].setReferencia(1); //Pois 0 é o nome e as instruções começam no 1
            bcp[i - 1].setPrioridade(Integer.parseInt(linhaPrior));
            System.out.println("Nome: " + bcp[i - 1].getNome());  //Apagar esse print após testes
            System.out.println("Prioridade: " + bcp[i - 1].getPrioridade());
            prioridades[i - 1] = bcp[i - 1].getPrioridade();

            //Laço para leitura de todos os comandos do processo 'i'.
            int j = 0;
            while (linhaP != null) {
                bcp[i - 1].setComando(linhaP, j++);
                System.out.println("Comando: " + linhaP);
                linhaP = lerProcessos.readLine();
            }

            processos.close();
        }
        prior.close();
    }

    public static void main(String[] args) throws IOException {

    }
}
