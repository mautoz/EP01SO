public class BCP {

    private String nome;        	//Linha 0 de cada processo é o Nome do Processo
    private String [] comandos; 	//Armazenar as referências das instruçõe de cada processo
    private int prioridade;			//Dada pelo prioridade.txt
    private int CP;					//Armazena a posição em que parou a leitura das instruções de cada processo 
    private char estadoProcesso;	//(e)xecutando, (p)ronto ou (b)loqueado
    private int creditos = prioridade;
    private int quantum;
    private int espera;
    private int estadoX;
    private int estadoY;

    public BCP(String nome){
        this.nome = nome;
        comandos = new String[22];
    }
     /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }
    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getQuantum() {
        return quantum;
    }

    public void setQuantum (int q) {
        this.quantum = q;
    }

    public int getCreditos() {
        return creditos;
    }

    public void setCreditos (int c) {
        this.creditos = c;
    }

    public int getPrioridade() {
        return prioridade;
    }

    public void setPrioridade(int p) {
        this.prioridade = p;
    }

    public void setComando (String comandos, int i) {
        this.comandos[i] = comandos;
    }

    public String getComando (int i) {
        return comandos[i];
    }

    /**
     * @return the CP
     */
    public int getCP() {
        return CP;
    }

    /**
     * @param CP the CP to set
     */
    public void setCP(int CP) {
        this.CP = CP;
    }

    /**
     * @return the estadoProcesso
     */
    public char getEstadoProcesso() {
        return estadoProcesso;
    }

    /**
     * @param estadoProcesso the estadoProcesso to set
     */
    public void setEstadoProcesso(char estadoProcesso) {
        this.estadoProcesso = estadoProcesso;
    }
    
    public int getEspera() {
        return espera;
    }

    public void setEspera(int espera) {
        this.espera = espera;
    }
    
    public int getEstadoX() {
        return estadoX;
    }

    public void setEstadoX(int x) {
        this.estadoX = x;
    }    
    
    public int getEstadoY() {
        return estadoY;
    }

    public void setEstadoY(int y) {
        this.estadoY = y;
    }
    
    public int tamanhoComandos () {
    	return comandos.length;
    }
}
