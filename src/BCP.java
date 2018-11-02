import java.util.ArrayList;
import java.util.List;

public class BCP {
    private String nome;        	//Linha 0 de cada processo é o Nome do Processo
    private List<String> comandos = new ArrayList<>(); //Armazenar as referências das instruçõe de cada processo
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
    }

    public String getNome() {
        return nome;
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

    public void setComando (String instrucao) {
        comandos.add(instrucao);
    }

    public String getComando (int i) {
        return comandos.get(i);
    }
    
    public int tamanhoComandos () {
    	return comandos.size();
    }    
    
    public int getCP() {
        return CP;
    }

    public void setCP(int CP) {
        this.CP = CP;
    }

    public char getEstadoProcesso() {
        return estadoProcesso;
    }

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
}