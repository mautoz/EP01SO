import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Ler {
    private static int [] prioridades = new int[10];
    //private static int [] prioridadesSemRepeticao;
    //private static int tamArrayList;

    //public int [] getArrayPrioridades () {
    //    return prioridades;
    //}

    //public int gettamArrayList() {
    //    return tamArrayList;
    //}

    //Para preencher a lista de prioridades, � preciso saber a posi��o
    /*public int getPosicaoDaPrioridade (int p) {
        int i = 0;

        while (i < prioridades.length && prioridades[i] != p)
            i++;

        return i;
    }*/

    //Cria um vetor com as prioridades passadas mas sem repeti��o.
    //Serve para ter uma posi��o 'i' para uma prioridade 'p'.
    public int maxCredito () {
    	int max = prioridades[0];
    	for (int i = 0; i < prioridades.length; i++)
    		if (prioridades[i] > max)
    			max = prioridades[i];    	
    	return max;
    }
    
    /*public void numPrioridades (int [] v) {
        int i = 1, j, contador = 1;

        Arrays.sort(v);

        while (i < v.length) {
            if (prioridades[i] != prioridades[i - 1])
                contador++;
            i++;
        }

        tamArrayList = contador + 1;
        System.out.println("Contador = " + contador);

        prioridadesSemRepeticao = new int[tamArrayList];
        prioridadesSemRepeticao[0] = 0; //Quando fica com zero cr�ditos
        prioridadesSemRepeticao[1] = prioridades[0];
        i = 1;
        j = 2;
        while (i < v.length) {
            if (v[i - 1] != v[i])
                prioridadesSemRepeticao[j++] = v[i];
            i++;
        }
        //Precisa criar do maior para o menor, ent�o vamos inverter a lista
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
    }*/

    public void lerArq (BCP [] bcp, Escalonador esc, Escrever escrever) throws IOException {
        FileReader processos, prior;
        BufferedReader lerProcessos, lerPrior;

        prior = new FileReader("prioridades.txt");
        lerPrior = new BufferedReader(prior);

        //La�o para ler do 01.txt at� 10.txt. Em paralelo, ser� lido
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
            String linhaP = lerProcessos.readLine(); //Linha 0 � o Nome do processo
            String linhaPrior = lerPrior.readLine(); //Linha da prioridade do processo 'i'.
            bcp[i - 1] = new BCP (linhaP);
            escrever.escrevendoCarregando(linhaP);

            bcp[i - 1].setEstadoProcesso('p');
            bcp[i - 1].setPrioridade(Integer.parseInt(linhaPrior));
            bcp[i - 1].setCP(1);
            bcp[i - 1].setEspera(0);
            System.out.println("Nome: " + bcp[i - 1].getNome());  //Apagar esse print ap�s testes
            System.out.println("Prioridade: " + bcp[i - 1].getPrioridade());
            prioridades[i - 1] = bcp[i - 1].getPrioridade();

            //La�o para leitura de todos os comandos do processo 'i'.
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
