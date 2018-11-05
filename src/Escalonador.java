import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;

public class Escalonador {
	private TabelaDeProcessos tp;
    private int n_com;
    
    public Escalonador () {
    	tp = new TabelaDeProcessos();
    }

    public void setNCom (int q) {
        n_com = q;
    }

    public int getNCom () {
        return n_com;
    }
    
    //Verifica se a tabela de processos prontos est� vazia
    public static boolean estahCheio (ArrayList<BCP> [] p, Ler leitura) {
        for (int i = 0; i < leitura.maxCredito() + 1; i++)
            if (p[i].size() != 0)
                return true;
        return false;
    }
    
    //Para cada processo que � executado, � necess�rio decrementar o valores de 
    //espera dos processos bloqueados.
    public void decrementaEsperaBloqueados (TabelaDeProcessos t, Ler l) {
    	for (int i = 0; i < t.getBloqueados().size(); i++) {
    		if (t.getBloqueados().get(i).getEspera() > 0) {
    			BCP bloqueado = t.getBloqueados().get(i);
    			bloqueado.setEspera(bloqueado.getEspera() - 1); 
    		}
    	}
    	int i = 0;
    	while (i < t.getBloqueados().size()) {
    		BCP bloqueado = t.getBloqueados().get(i); 
    		if (bloqueado.getEspera() == 0) {
    			bloqueado.setEstadoProcesso('p');
    			t.inserirProcessoPronto(t, bloqueado, bloqueado.getCreditos(), l.maxCredito());
    			t.removerProcessoBloqueado(t.getBloqueados(), bloqueado);
    		}
    		else 
    			i++;
    	}
    }

    public void incrementaCP(BCP processo){
    	int cp = processo.getCP();
    	processo.setCP(cp + 1);
    }
    
  //M�todo principal para escalonar os processos
    public void EscalonarProcessos(TabelaDeProcessos t, Escrever mensagem, Ler leitura) throws IOException {
    	int numIntrucoesRodadas = 0; 	//Nenhuma instrucao rodada ate agora;
    	int quantQuantas = 0;
    	int numChaveamento=1;			//executar o processo conta como troca
    	
        //Loop v�lido enquanto existem processos bloqueados ou executando
    	//Primeiro While
        while (estahCheio(t.getProntos(), leitura) || t.getBloqueados().size() > 0) {   	
        	//Ser� visto fila por fila os processos      	
            for (int i = 0; i < t.getProntos().length - 1; i++) {	//Loop feito para n�o passar pelo �ltima posi��o
            	ArrayList<BCP> [] prioridadei = t.getProntos();		//Resolvemos uma fila de prioridade por vez
            	//Segundo While
            	while (!prioridadei[i].isEmpty()) {					//Sempre a partir da maior prioridade
            		numChaveamento++;								//Processo entrou na CPU? Incrementa!            		
            		//Auxiliares 
            		BCP processo = prioridadei[i].get(0);			//Pega sempre o primeiro da lista.
                	String processoNome = processo.getNome();
                	
                	mensagem.escrevendoExecutando(processo.getNome());	//Escreve o processo que entrou na CPU                	
                	quantQuantas+=processo.getQuantum(); 				//Incrementa o numero de quantum usado
                	
                	int n_instrucoes = 0;	//Controlar o n�mero de vezes que o loop acontece
                	processo_em_execucao:	//Label para o break em E/S.                	
                	//Terceiro While
                	while (n_instrucoes < processo.getQuantum()*getNCom() && !"SAIDA".equals(processo.getComando(processo.getCP() - 1))) {
	                	processo.setEstadoProcesso('e'); //Come�ou a ser executado
	                	int pc = processo.getCP();
	                	String instrucao = processo.getComando(pc);
	                	if("COM".equals(instrucao)) {	//COM
	                		numIntrucoesRodadas++; 		// Cada ciclo � uma instru��o rodada
	                       	incrementaCP(processo); 	//incrementa pc
	                   	}
	                   	else if(instrucao.contains("X=")) {
	                   		numIntrucoesRodadas++; 		// Cada ciclo � uma instru��o rodada
	                		processo.setEstadoX(Integer.parseInt((instrucao.substring(2)))); //carrega registador
	                		incrementaCP(processo);
	                   	}
	                   	else if (instrucao.contains("Y=")) {
	                   		numIntrucoesRodadas++; 		// Cada ciclo � uma instru��o rodada
	                   		processo.setEstadoY(Integer.parseInt((instrucao.substring(2)))); //carrega registador
	                		incrementaCP(processo);
	                	}
	                   	else if("E/S".equals(instrucao)) {
	                   		numIntrucoesRodadas++; 		// Cada ciclo � uma instru��o rodada
	                   		mensagem.escrevendoES(processoNome);      
	                       	if ("COM".equals(processo.getComando(processo.getCP() - 1)))
	                       		mensagem.escrevendoInterrompendoESCOM(processoNome, n_instrucoes);
	                       	else
	                       		mensagem.escrevendoInterrompendo(processoNome, n_instrucoes);
	                       	incrementaCP(processo);
	                       	processo.setEstadoProcesso('b'); 					//Ser� bloqueado
	                       	processo.setEspera(2); 								//Settar o tempo de espera
	                       	processo.setCreditos(processo.getCreditos() - 1);	//Perde 1 cr�dito 
	                       	processo.setQuantum(processo.getQuantum() + 1);		//Recebe o dobro de Quantum
				           	t.inserirProcessoBloqueado(t.getBloqueados(), processo);
				           	t.removerProcessoPronto(t.getProntos(), processo, i);
				           	break processo_em_execucao;
	                   	}
	                   	else if ("SAIDA".equals(instrucao)) {
	                   		numIntrucoesRodadas++;		// Cada ciclo � uma instru��o rodada
	                   		incrementaCP(processo);
	                   		mensagem.escrevendoTerminando(processoNome, processo.getEstadoX(), processo.getEstadoY()); //Encrever a finaliza��o do processo
	                       	t.removerProcessoPronto(t.getProntos(), processo, i); //Processo recebeu sa�da, ent�o sai da lista de prontos
	                	} 
	                	n_instrucoes++;//incrementar instrucoes rodadas
	                } //Fim do Terceiro While de Instru��es
	                if (processo.getEstadoProcesso() == 'e' && !"SAIDA".equals(processo.getComando(processo.getCP() - 1)) && !"E/S".equals(processo.getComando(processo.getCP() - 1))) {		//Se n�o foi bloqueado
	                	processo.setEstadoProcesso('p');			//ele ir� para o Estado 'pronto'
	                	processo.setCreditos(processo.getCreditos() - 1);
	                	processo.setQuantum(processo.getQuantum() + 1);                		
	                	//Atualiza o processo em sua nova posicao na fila de prioridades
	                	t.removerProcessoPronto(t.getProntos(), processo, i);
	                	t.inserirProcessoPronto(t, processo, processo.getCreditos(), leitura.maxCredito());
	                	mensagem.escrevendoInterrompendo(processo.getNome(), n_instrucoes);
	                	decrementaEsperaBloqueados(t, leitura);	//Processo passou pelo estado EXECUTANDO
	                }											//ent�o decrementamos os bloqueados.
            	} //Fim do Segundo While que verifica uma fila de prioridade
            } //Fim do For que verifica todas as linhas com prioridade diferente de zero.
          
	        //ArrayLists auxiliares.
	        ArrayList<BCP> [] auxProntos = t.getProntos();
	        ArrayList<BCP> creditosZero = auxProntos[leitura.maxCredito()];
	        ArrayList<BCP> auxBloqueados = t.getBloqueados();
            //Acabou os loops das prioridades diferente de zero
            //pode estar tudo com 0 cr�dito e/ou bloquados ou s� bloqueados
            //ent�o verficamos e se tiver bloqueados, decrementamos.
            if (auxBloqueados.size() > 0)
            	decrementaEsperaBloqueados(t, leitura);
	            
            //Se a fila dos processos com 0 cr�ditos possuir processo e a dos bloqueados 
            //estiver vazia, ent�o devemos repopular a lista dos 'prontos' com 
            //os que estavam com zero de prioridade.                        
            if (!creditosZero.isEmpty() && auxBloqueados.size() == 0) {
            	//Enquanto houver bloqueados, vai retirando e inserindo em prontos
            	//Como a ordem n�o importa, vai pegando sempre com o get(0) mesmo.
            	while (creditosZero.size() > 0) {            		
            		BCP aux = creditosZero.get(0);            		
            		t.removerProcessoPronto(auxProntos, aux, leitura.maxCredito());            		
            		aux.setEstadoProcesso('p');
            		aux.setQuantum(1);
            		aux.setCreditos(aux.getPrioridade());
            		t.inserirProcessoPronto(t, aux, aux.getCreditos(), leitura.maxCredito());
            	}
            }
           
        }// Fim do Primeiro While
       
        mensagem.escrevendo((double)numChaveamento/leitura.getNumProcessos(), (double)numIntrucoesRodadas/quantQuantas, getNCom());
    }


    public static void main(String[] args) throws IOException  {
        Ler ler = new Ler();					//Respos�vel por ler os arquivos .txt
        Escrever escrever = new Escrever();		//Respos�vel por abrir e escrever no logXX.txt
        BCP [] bcp = new BCP[ler.getNumProcessos()];
        Escalonador esc = new Escalonador();

        //In�cio da leitura do Quantum (n_com)
        FileReader quantum = new FileReader("quantum.txt");
        BufferedReader lerQuantum = new BufferedReader(quantum);
        String linhaQ = lerQuantum.readLine();
        //Definindo o n_com
        esc.setNCom(Integer.parseInt(linhaQ));
        lerQuantum.close();
        //Fim da leitura do Quantum

        try {
            //Inicializar o logXX.txt, arquivo de sa�da do programa
            escrever.criarLog(esc.getNCom());
            //Come�a a leitura dos processos e suas prioridades
            ler.lerArq(bcp, esc, escrever);
            //Inicializar o Arraylist com processos prontos por prioridade
            esc.tp.inicializaArrayList(ler.maxCredito());
            //Criando a lista de processos prontos com os BCPs lidos.
            for (int i = 0; i < ler.getNumProcessos(); i++) {
            	int p = bcp[i].getPrioridade();
            	bcp[i].setCreditos(p);
                esc.tp.inserirProcessoPronto(esc.tp, bcp[i], p, ler.maxCredito());
            }
            //Impress�o dos processos em ordem da tabela de processos.
            for (int i = 0;  i < ler.maxCredito() + 1; i++) {
            	ArrayList<BCP>[] lista= esc.tp.getProntos();
                for (BCP aux : lista[i])
                	escrever.escrevendoCarregando(aux.getNome());
            }
            //ESCALONAR
            esc.EscalonarProcessos (esc.tp, escrever, ler);
            //Fechar o arquivo logXX.txt. N�o h� mais o que escrever
            escrever.fecharArq();
            
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
    }
}