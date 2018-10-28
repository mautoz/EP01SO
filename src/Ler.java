import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Ler {
    private static int [] prioridades = new int[10];

    //Cria um vetor com as prioridades passadas mas sem repeti��o.
    //Serve para ter uma posi��o 'i' para uma prioridade 'p'.
    public int maxCredito () {
    	int max = prioridades[0];
    	for (int i = 0; i < prioridades.length; i++)
    		if (prioridades[i] > max)
    			max = prioridades[i];
    	return max;
    }

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
            //Settando vari�veis default
            bcp[i - 1].setEstadoProcesso('p');							//Processo Pronto
            bcp[i - 1].setPrioridade(Integer.parseInt(linhaPrior));
            prioridades[i - 1] = bcp[i - 1].getPrioridade();
            bcp[i - 1].setCP(1);										//CP padr�o 1 pois 0 � o nome do processo
            bcp[i - 1].setEspera(0);									//Como est� pronto, processo � 0
            bcp[i - 1].setEstadoX(0);
            bcp[i - 1].setEstadoY(0);         
            
            
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
}
