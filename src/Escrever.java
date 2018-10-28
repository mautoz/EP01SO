import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//Classe criada exclusivamente para escrever as mensagens no .txt
//Acreditamos que os nomes dos métodos são autoexplicativos
public class Escrever {
  public static FileWriter arq;
  public static PrintWriter gravarArq;

  public void criarLog (int X) throws IOException {
    String outfile;
    StringBuffer buffer = new StringBuffer(40);

    //Na verdade o valor de N vai depender do XX pedido no arquivo
    if (X < 10)
        outfile = buffer.append("log0").append(X).append(".txt").toString();
    else
        outfile = buffer.append("log").append(X).append(".txt").toString();
    arq = new FileWriter(outfile);
  }
  //Escrita final
  public void escrevendo(int m_trocas, double m_instrucoes, int quantum) throws IOException {
    gravarArq = new PrintWriter(arq);
    gravarArq.println("MEDIA DE TROCAS: " + m_trocas);
    gravarArq.println("MEDIA DE INSTRUCOES: " + m_instrucoes);
    gravarArq.println("QUANTUM: " + quantum + "\n");
  }
  //Processos carregados
  public void escrevendoCarregando(String S) throws IOException {
    gravarArq = new PrintWriter(arq);
    gravarArq.println("Carregando " + S);
  }
  //Proceso que será executado
  public void escrevendoExecutando(String S) throws IOException {
    gravarArq = new PrintWriter(arq);
    gravarArq.println("Executando " + S);
  }
  //Proceso que será executado
  public void escrevendoES(String S) throws IOException {
    gravarArq = new PrintWriter(arq);
    gravarArq.println("E/S iniciada em " + S);
  }
  //Proceso que será executado
  public void escrevendoTerminando(String S, int x, int y) throws IOException {
    gravarArq = new PrintWriter(arq);
    gravarArq.println(S + " terminado. X=" + x + ". Y=" + y);
  }

  //Verificar que pode existir informações entre ( )
  public void escrevendoInterrompendoESCOM(String S, int n) throws IOException {
    gravarArq = new PrintWriter(arq);
    gravarArq.println("Interrompendo " + S + "após " + n + " instruções. (Havia um comando antes da E/S");
  }
  //Proceso que será executado
  public void escrevendoInterrompendo(String S, int n) throws IOException {
	    gravarArq = new PrintWriter(arq);
	    gravarArq.println("Interrompendo " + S + "após " + n + " instruções.");
  }
  //Método chamado ao término do Escalonador.java  
  public void fecharArq () throws IOException {
    arq.close();
  }
}
