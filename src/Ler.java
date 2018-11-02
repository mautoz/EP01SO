import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Ler {
    private static int [] prioridades = new int[10];
    public static int maximo;

    //Com as prioridades em um vetor, apenas o maior é
    //buscado para servir de tamanho máximo na fila de
    //prioridades de processos prontos.
    public void prioridadeMax () {
    	int max = prioridades[0];
    	for (int i = 0; i < prioridades.length; i++)
    		if (prioridades[i] > max)
    			max = prioridades[i];
    	maximo = max;
    }
    
    //Retorna o valor máximo, isto é, a maior prioridade.
    public int maxCredito () {
    	return maximo;
    }

    //Toda a leitura dos 11 '.txt' é feita por aqui.
    //Leitura do Quantum é feita pelo próprio Escalonador.java 
    //Nome do programa e prioridade são lidos em paralelo.
    public void lerArq (BCP [] bcp, Escalonador esc, Escrever escrever) throws IOException {
        FileReader processos, prior;
        BufferedReader lerProcessos, lerPrior;

        prior = new FileReader("prioridades.txt");
        lerPrior = new BufferedReader(prior);

        //Laço para ler os processos na fomra XX.txt. Por default, numProcessos = 10
        //Em paralelo, será lido as prioridades de 'prioridade.txt'.
        int numProcessos = 10;
        for (int i = 1; i <= numProcessos;  i++) {
            StringBuffer buffer = new StringBuffer(40);
            String filename;

            if (i <= 9)
                filename = buffer.append("0").append(i).append(".txt").toString();
            else
                filename = buffer.append(i).append(".txt").toString();

            processos = new FileReader(filename);
            lerProcessos = new BufferedReader(processos);

            //Garantir a leitura das primeiras linhas de processo e prioridade
            String linhaP = lerProcessos.readLine(); //Linha 0 é o Nome do processo
            String linhaPrior = lerPrior.readLine(); //Linha da prioridade do processo 'i'.
            bcp[i - 1] = new BCP (linhaP);
            //Settando variáveis default
            bcp[i - 1].setEstadoProcesso('p');							//Processo Pronto
            bcp[i - 1].setPrioridade(Integer.parseInt(linhaPrior));
            prioridades[i - 1] = bcp[i - 1].getPrioridade();
            bcp[i - 1].setCP(1);										//CP padrão 1 pois 0 é o nome do processo
            bcp[i - 1].setEspera(0);									//Como está pronto, processo é 0
            bcp[i - 1].setEstadoX(0);
            bcp[i - 1].setEstadoY(0);         
            bcp[i - 1].setCreditos(bcp[i - 1].getPrioridade());
            bcp[i - 1].setQuantum(1);
            
            //Laço para leitura de todos os comandos do processo 'i'.
            int j = 0;
            while (linhaP != null) {
                bcp[i - 1].setComando(linhaP, j++);
                linhaP = lerProcessos.readLine();
            }

            processos.close();
        }
        prioridadeMax();
        prior.close();
    }
}
