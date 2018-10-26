//package so_epiescalonador;

public class BCP {

    private String nome;        //Linha 0 de cada processo é o nome
    private String [] comandos; //Armazenar os comandos de cada processo
    private int prioridade;
    private int CP;
    private char estadoProcesso; //(e)xecutando, (p)ronto ou (b)loqueado
    private int referencia;
    private int estadoAtual;
    private int creditos = prioridade;
    private int quantum;

    //contem os valores dos registradores antes do chaveamento
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

    /**
     * @return the referencia
     */
    public int getReferencia() {
        return referencia;
    }

    /**
     * @param referencia the referencia to set
     */
    public void setReferencia(int referencia) {
        this.referencia = referencia;
    }

    /**
     * @return the estadoAtual
     */
    public int getEstadoAtual() {
        return estadoAtual;
    }

    /**
     * @param estadoAtual the estadoAtual to set
     */
    public void setEstadoAtual(int estadoAtual) {
        this.estadoAtual = estadoAtual;
    }

     /**
     * @return the estadoX
     */
    public int getEstadoX() {
        return estadoX;
    }

    /**
     * @param estadoX the estadoX to set
     */
    public void setEstadoX(int estadoX) {
        this.estadoX = estadoX;
    }

    /**
     * @return the estadoY
     */
    public int getEstadoY() {
        return estadoY;
    }

    /**
     * @param estadoY the estadoY to set
     */
    public void setEstadoY(int estadoY) {
        this.estadoY = estadoY;
    }
}
