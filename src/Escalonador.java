//package so_epiescalonador;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.io.IOException;

public class Escalonador {

    ArrayList<BCP>[] TabelaDeProcessos;
    private static int n_com;

    //registradores
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

    public void inicializarArray (int tam) {
        TabelaDeProcessos = (ArrayList<BCP>[])new ArrayList[tam];
    }

    public static void setNCom (int q) {
        n_com = q;
    }

    public static int getNCom () {
        return n_com;
    }

    public Escalonador(){
        //iniciando X e Y com 0
        X = 0;
        Y = 0;
    }

    //Verifica se a tabela de processos prontos est� vazia
    public static boolean estahVazio (ArrayList<BCP> [] p, Ler leitura) {
        for (int i = 0; i < leitura.gettamArrayList(); i++)
            if (p[i].size() != 0)
                return true;
        return false;
    }
    
    //Para cada processo que � executado, � necess�rio decrementar o valores de 
    //espera dos processos bloqueados.
    public void decrementaEsperaBloqueados (TabelaDeProcessos t, ArrayList<BCP> [] p, ArrayList<BCP> b) {
    	for (int i = 0; i < b.size(); i++) {
    		if (b.get(i).getEspera() > 0)
    			b.get(i).setEspera(b.get(i).getEspera() - 1);
    	}
    	for (int i = 0; i < b.size(); i++) {
    		if (b.get(i).getEspera() == 0) {
    			t.inserirProcessoPronto(p, b.get(i), b.get(i).getCreditos());
    			t.removerProcessoBloqueado(b, b.get(i));
    		}
    	}
    }

    public void incrementaCP(BCP processo){
    	int cp = processo.getCP();
    	processo.setCP(cp + 1);
    }
    
    //M�todo principal para escalonar os processos
    public void EscalonarProcessos(TabelaDeProcessos t, ArrayList<BCP> [] p, ArrayList<BCP> b,
                                            ArrayList<BCP> e, BCP [] processos, Escrever mensagem,
                                            Ler leitura) throws IOException {
    	setX(0);
    	setY(0);

        //int tamanho = leitura.gettamArrayList();
    	
        //Loop v�lido enquanto existem processos bloqueados ou executando
        while (estahVazio(p, leitura) || b.size() > 0) {
        	//Ser� visto fila por fila os processos      	
            for (int i = 0; i < p.length; i++) {
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
                	
                	int n_instrucoes = 0;
                	while (n_instrucoes < processo.getQuantum()*getNCom()) {
                		processo.setEstadoProcesso('e'); //Come�ou a ser executado               		
                		
                		
                    	if(instrucao.equals("COM")) {//COM
                    		mensagem.escrevendoES(processoNome);
                        	System.out.println("COM");
                        	incrementaCP(processo); //incrementa pc
                        	
                    	}
                    	else if(instrucao.contains("X=")) {
                    		String temp = instrucao;
                			setX(Integer.parseInt((temp.substring(2)))); //carrega registador
                			System.out.println("Integer.parseInt(temp.substring(2)) =>"  + Integer.parseInt(temp.substring(2)));//teste, pode apagar?
                			mensagem.escrevendoExecutando(processoNome);
                			System.out.println("X="  + getX());//teste
                			incrementaCP(processo);
                    	}
                    	else if (instrucao.contains("Y=")) {
                    		String temp = instrucao;
                			setX(Integer.parseInt((temp.substring(2)))); //carrega registador
                			System.out.println("Integer.parseInt(temp.substring(2)) =>"  + Integer.parseInt(temp.substring(2)));//teste, pode apagar?
                			mensagem.escrevendoExecutando(processoNome);
                			System.out.println("X="  + getX());//teste
                			incrementaCP(processo);
                		}
                    	else if (instrucao.equals("SAIDA")) {
                    		mensagem.escrevendoTerminando(processoNome, 0, 0); //Alterar valores 0 e 0 
                    		t.removerProcessoPronto(p, p[i].get(0), i);
                		}
                    	else if(instrucao.equals("E/S")) {
                    		mensagem.escrevendoES(processoNome);
                        	System.out.println("E/S");
                        	incrementaCP(processo);
                        	p[i].get(0).setEstadoProcesso('b'); //Ser� bloqueado
			            	p[i].get(0).setEspera(2); //Settar o tempo de espera
			            	t.inserirProcessoBloqueado(b, p[i].get(0));
			            	t.inserirProcessoPronto(p, p[i].get(0), i);
                    	}
			            n_instrucoes++;
                	}
                	if (processo.getEstadoProcesso() == 'e') {	//Se n�o foi bloqueado
                		processo.setEstadoProcesso('p');			//ele ir� para o Estado 'pronto'
                		processo.setCreditos(processo.getCreditos() - 1);
                		processo.setQuantum(processo.getQuantum()*2);

                		//Atualiza o processo em sua nova
                		t.removerProcessoPronto(p, processo, i);		//posicao na fila de prioridades
                		t.inserirProcessoPronto(p, processo, processo.getCreditos());                		
                	}
                	
            	}
            }
            
            System.out.println("Valores de sa�da da lista de prontos");
            System.out.println("p.length  => " + p.length);
        	System.out.println("p[0].size()  => " + p[0].isEmpty());
        	System.out.println("p[1].size()  => " + p[1].isEmpty());
        	System.out.println("p[2].size()  => " + p[2].isEmpty());
        	System.out.println("p[3].size()  => " + p[3].isEmpty());
        	System.out.println("p[4].size()  => " + p[4].isEmpty());
        	
        	System.out.println("X=" + getX() + "Y=" + getY());
        	
            /*for (int i = 0;  i < leitura.gettamArrayList(); i++) {
                for (int j = 0; j < p[i].size(); j++) {
                    BCP aux = p[i].get(j);
                    aux.setQuantum(1);
                    aux.setCreditos(aux.getCreditos()-1);
                    aux.setEstadoProcesso('e');
                    t.inserirProcessoExecutando(e, aux);
                    t.removerProcessoPronto(p, aux, i);
                }
            }
            System.out.println();*/
        }        

        for (BCP aux : e) {
            System.out.println(aux.getNome());
        }
    }


    public static void main(String[] args) throws IOException  {
        Ler ler = new Ler();
        Escrever escrever = new Escrever();
        BCP [] bcp = new BCP[10];
        Escalonador esc = new Escalonador();


        //A tabela de processos � composta por ArrayList
        ArrayList<BCP> [] prontos;
        ArrayList<BCP> bloqueados = new ArrayList<>();
        ArrayList<BCP> executando = new ArrayList<>();
        TabelaDeProcessos tp = new TabelaDeProcessos();

        FileReader quantum = new FileReader("quantum.txt");
        BufferedReader lerQuantum = new BufferedReader(quantum);
        String linhaQ = lerQuantum.readLine();
        //Definindo o n_com
        setNCom(Integer.parseInt(linhaQ));
        System.out.println("Quantum: " + getNCom ());
        lerQuantum.close();

        try {
            //inicializa o logXX.txt. aquivo de sa�da do programa
            escrever.criarLog(getNCom ());
            //Come�a aleitura dos processos e suas prioridades
            ler.lerArq(bcp, esc, escrever);

            esc.inicializarArray(ler.gettamArrayList());

            System.out.println("n_com = " + getNCom());
            System.out.println("Exemplo de leitura de comando para o BCP[2]");

            for (int k = 0; k < 10; k++) {
                System.out.println("Comando " + k + " = " + bcp[2].getComando(k));
            }

            System.out.println("Prioridade do Processo " + bcp[2].getNome() + " � " + bcp[2].getPrioridade());
            System.out.println("Valor do Quantum " + getNCom());

            //Testes com Array List
            //Primeiro: ler todas as 10 prioridades, colocar em ordem e criar um novo
            //vetor, mas sem as repeti��es
            ler.numPrioridades(ler.getArrayPrioridades());

        //Bloco de Prints para testes, pode apagar
            for (int i = 0; i < 10; i++)
                System.out.println(bcp[i].getPrioridade() + " " + ler.getPosicaoDaPrioridade(bcp[i].getPrioridade()) + " ");
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
            System.out.println("\n=================================================+++++++++++++++++++\n");
            System.out.println("Tamanho do bloqueados: " + bloqueados.size());
            System.out.println("Tamanho do prontos[0]: " + prontos[0].size());

            for (int i = 0; i < 10; i++) {
                tp.inserirProcessoPronto(prontos, bcp[i], ler.getPosicaoDaPrioridade(bcp[i].getPrioridade()));
                //tp.inserirProcessoBloqueado(bloqueados, bcp[i]);
            }

            System.out.println("\n=================================================+++++++++++++++++++\n");
            System.out.println("Tamanho do bloqueados: " + bloqueados.size());
            System.out.println("Tamanho do prontos[0]: " + prontos[0].size());


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

            
            //ESCALONAR
            esc.EscalonarProcessos (tp, prontos, bloqueados, executando,
                                bcp, escrever, ler);

            escrever.escrevendo(0, 0.0, getNCom());


            //Fechar o arquivo logXX.txt. N�o h� mais o que escrever
            escrever.fecharArq();
        } catch (IOException e) {
            System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
        }
    }
}
