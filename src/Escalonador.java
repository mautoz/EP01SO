import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;

public class Escalonador {

    ArrayList<BCP>[] TabelaDeProcessos;
    private int n_com;

    //registradores
    /*
    private static int X;
    private static int Y;
    
    
    public static void setX (int x) {
    	X = x;
    }
    
    public static void setY (int y) {
    	Y = y;
    }
    
    public static int getX () {
    	return X;
    }
    
    public static int getY () {
    	return Y;
    }
    */
    public void setNCom (int q) {
        n_com = q;
    }

    public int getNCom () {
        return n_com;
    }
    
/*
    public Escalonador(){
        //iniciando X e Y com 0
        X = 0;
        Y = 0;
    }
*/
    
    //Verifica se a tabela de processos prontos está vazia
    public static boolean estahCheio (ArrayList<BCP> [] p, Ler leitura) {
        for (int i = 0; i < leitura.maxCredito() + 1; i++)
            if (p[i].size() != 0)
                return true;
        return false;
    }
    
    //Para cada processo que é executado, é necessário decrementar o valores de 
    //espera dos processos bloqueados.
    public void decrementaEsperaBloqueados (TabelaDeProcessos t, ArrayList<BCP> [] p, ArrayList<BCP> b, Ler l) {
    	for (int i = 0; i < b.size(); i++) {
    		if (b.get(i).getEspera() > 0)
    			b.get(i).setEspera(b.get(i).getEspera() - 1);
    	}
    	for (int i = 0; i < b.size(); i++) {
    		if (b.get(i).getEspera() == 0) {
    			b.get(i).setEstadoProcesso('p');
    			t.inserirProcessoPronto(p, b.get(i), b.get(i).getCreditos(), l.maxCredito());
    			t.removerProcessoBloqueado(b, b.get(i));
    		}
    	}
    }

    public void incrementaCP(BCP processo){
    	int cp = processo.getCP();
    	processo.setCP(cp + 1);
    }
    
    //Método principal para escalonar os processos
    public void EscalonarProcessos(TabelaDeProcessos t, ArrayList<BCP> [] p, ArrayList<BCP> b,
                                            ArrayList<BCP> e, BCP [] processos, Escrever mensagem,
                                            Ler leitura) throws IOException {
    	
        //Loop válido enquanto existem processos bloqueados ou executando
        while (estahCheio(p, leitura) || b.size() > 0) {
        	//Será visto fila por fila os processos      	
            for (int i = 0; i < p.length - 1; i++) {
            	while (!p[i].isEmpty()) {
            		System.out.println("p.length  => " + p.length);
                	System.out.println("p[" + i + "].size()  => " + p[i].size());
                	System.out.println("p[" + i + "].get(0).getNome()  => " + p[i].get(0).getNome());
                	System.out.println("p[" + i + "].get(0).getCP()  => " + p[i].get(0).getCP());
                	System.out.println("p[" + i + "].get(0).getComando(p[i].get(0).getCP())  => " + p[i].get(0).getComando(p[i].get(0).getCP()));
                	
                	BCP processo = p[i].get(0);
                	int pc = processo.getCP();
                	String instrucao = processo.getComando(pc);
                	String processoNome = processo.getNome();  
                	processo.setQuantum(1);
                	
                	int n_instrucoes = 0;
                	mensagem.escrevendoExecutando(processoNome);
                	processo_em_execucao:
                	while (n_instrucoes < processo.getQuantum()*getNCom()) {
                		processo.setEstadoProcesso('e'); //Começou a ser executado                		
                		
                    	if(instrucao.equals("COM")) {//COM
                        	System.out.println("COM");
                        	incrementaCP(processo); //incrementa pc                       	
                    	}
                    	else if(instrucao.contains("X=")) {
                    		String temp = instrucao;
                			processo.setEstadoX(Integer.parseInt((temp.substring(2)))); //carrega registador
                			System.out.println("Integer.parseInt(temp.substring(2)) =>"  + Integer.parseInt(temp.substring(2)));//teste, pode apagar?
                			System.out.println("X="  + processo.getEstadoX());//teste
                			incrementaCP(processo);
                    	}
                    	else if (instrucao.contains("Y=")) {
                    		String temp = instrucao;
                    		processo.setEstadoY(Integer.parseInt((temp.substring(2)))); //carrega registador
                			System.out.println("Integer.parseInt(temp.substring(2)) =>"  + Integer.parseInt(temp.substring(2)));//teste, pode apagar?
                			System.out.println("X="  + processo.getEstadoY());//teste
                			incrementaCP(processo);
                		}
                    	else if(instrucao.equals("E/S")) {
                    		mensagem.escrevendoES(processoNome);
                        	System.out.println("E/S");
                        	if (processo.getComando(processo.getCP() - 1).contains("COM"))
                        		mensagem.escrevendoInterrompendoESCOM(processoNome, n_instrucoes);
                        	else
                        		mensagem.escrevendoInterrompendo(processoNome, n_instrucoes);
                        	incrementaCP(processo);
                        	processo.setEstadoProcesso('b'); 					//Será bloqueado
                        	processo.setEspera(2); 								//Settar o tempo de espera
                        	processo.setCreditos(processo.getCreditos() - 1);	//Perde 1 crédito 
                        	processo.setQuantum(processo.getQuantum()*2);		//Recebe o dobro de Quantum
			            	t.inserirProcessoBloqueado(b, processo);
			            	t.removerProcessoBloqueado(b, processo);
			            	mensagem.escrevendoInterrompendo(processoNome, n_instrucoes);
			            	break processo_em_execucao;
                    	}
                    	else if (instrucao.equals("SAIDA")) {
                    		mensagem.escrevendoTerminando(processoNome, 0, 0); //Alterar valores 0 e 0 
                    		t.removerProcessoPronto(p, processo, i);
                		}
			            n_instrucoes++;			            
                	}
                	if (processo.getEstadoProcesso() == 'e') {		//Se não foi bloqueado
                		processo.setEstadoProcesso('p');			//ele irá para o Estado 'pronto'
                		processo.setCreditos(processo.getCreditos() - 1);
                		processo.setQuantum(processo.getQuantum()*2);
                		//Atualiza o processo em sua nova
                		t.removerProcessoPronto(p, processo, i);		//posicao na fila de prioridades
                		t.inserirProcessoPronto(p, processo, processo.getCreditos(), leitura.maxCredito());              		
                	}
                	mensagem.escrevendoInterrompendo(processo.getNome(), n_instrucoes);
                	decrementaEsperaBloqueados(t, p, b, leitura);
            	}
            }

            //Acabou os loops das prioridades diferente de zero
            //pode tá tudo com 0 crédito e/ou bloquados ou só bloqueados
            //então verficamos e se tiver bloqueados, decrementamos.
            if (b.size() > 0)
            	decrementaEsperaBloqueados(t, p, b, leitura);
            
            //Se a fila dos vazios estiver tiver processo e a dos bloqueados estiver 
            //vazia, então devemos popular os processos prontos com 
            //os que estavam com zero de prioridade.
            if (!p[leitura.maxCredito()].isEmpty() && b.size() == 0) {
            	int indice = leitura.maxCredito();
            	for (int k = 0; k < p[indice].size(); k++) {
            		BCP aux = p[indice].get(k);
            		aux.setEstadoProcesso('p');
            		aux.setCreditos(aux.getPrioridade());
            		t.removerProcessoBloqueado(b, aux);
            		t.inserirProcessoPronto(p, aux, aux.getCreditos(), leitura.maxCredito());
            	}
            }
        }
        mensagem.escrevendo(0, 0.0, getNCom());
    }


    public static void main(String[] args) throws IOException  {
        Ler ler = new Ler();					//Resposável por ler os 12 arquivos .txt
        Escrever escrever = new Escrever();		//Resposável por abrir e escrever o logXX.txt
        BCP [] bcp = new BCP[10];
        Escalonador esc = new Escalonador();

        //A tabela de processos é composta por ArrayList
        ArrayList<BCP> [] prontos;
        ArrayList<BCP> bloqueados = new ArrayList<>();
        TabelaDeProcessos tp = new TabelaDeProcessos();

        //Início da leitura do Quantum (n_com)
        FileReader quantum = new FileReader("quantum.txt");
        BufferedReader lerQuantum = new BufferedReader(quantum);
        String linhaQ = lerQuantum.readLine();
        //Definindo o n_com
        esc.setNCom(Integer.parseInt(linhaQ));
        lerQuantum.close();
        //Fim da leitura do Quantum

        try {
            //Inicializar o logXX.txt, arquivo de saída do programa
            escrever.criarLog(esc.getNCom());
            //Começa a leitura dos processos e suas prioridades
            ler.lerArq(bcp, esc, escrever);

            //Inicializar o Arraylist com processos prontos por prioridade
            //O tamanho é de acordo com ler.gettamArrayList()   
            prontos = (ArrayList<BCP>[])new ArrayList[ler.maxCredito() + 1];
            tp.inicializaArrayList(prontos, ler.maxCredito() + 1);

            for (int i = 0; i < 10; i++) {
            	int p = bcp[i].getPrioridade();
            	bcp[i].setCreditos(p);
                tp.inserirProcessoPronto(prontos, bcp[i], p, ler.maxCredito());
            }

            //Impressão dos processos em ordem da tabela de processos.
            for (int i = 0;  i < ler.maxCredito() + 1; i++) {
                System.out.println("Posição da Prioridade: " + (ler.maxCredito()-i));
                for (BCP aux : prontos[i]) {
                	escrever.escrevendoCarregando(aux.getNome());
                    System.out.println("Nome do processo: " + aux.getNome() + " Prioridade: " + aux.getPrioridade() + " Estado do Processo: " + aux.getEstadoProcesso());
                }
                System.out.println();
            }            
            //ESCALONAR
            //esc.EscalonarProcessos (tp, prontos, bloqueados, executando,
            //                    bcp, escrever, ler);

            //Fechar o arquivo logXX.txt. Não há mais o que escrever
            escrever.fecharArq();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
    }
}
