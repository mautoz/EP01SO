import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//Classe criada exclusivamente para escrever as mensagens no .txt
//Acreditamos que os nomes dos métodos são autoexplicativos
public class Escrever {
  public static FileWriter arq;
  public static PrintWriter gravarArq;

  public static void criarLog (int X) throws IOException {
    String outfile;
    StringBuffer buffer = new StringBuffer(40);

    //Na verdade o valor de N vai depender do XX pedido no arquivo
    if (X < 10)
        outfile = buffer.append("log0").append(X).append(".txt").toString();
    else
        outfile = buffer.append("log").append(X).append(".txt").toString();
    arq = new FileWriter(outfile);
  }

  public void escrevendo(int m_trocas, double m_instrucoes, int quantum) throws IOException {
    gravarArq = new PrintWriter(arq);
    gravarArq.println("MEDIA DE TROCAS: " + m_trocas);
    gravarArq.println("MEDIA DE INSTRUCOES: " + m_instrucoes);
    gravarArq.println("QUANTUM: " + quantum + "\n");
  }

  public void escrevendoCarregando(String S) throws IOException {
    gravarArq = new PrintWriter(arq);
    gravarArq.println("Carregando " + S);
  }

  public void escrevendoExecutando(String S) throws IOException {
    gravarArq = new PrintWriter(arq);
    gravarArq.println("Executando " + S);
  }

  public void escrevendoES(String S) throws IOException {
    gravarArq = new PrintWriter(arq);
    gravarArq.println("E/S iniciada em " + S);
  }

  public void escrevendoTerminando(String S, int x, int y) throws IOException {
    gravarArq = new PrintWriter(arq);
    gravarArq.println(S + " terminado. X=" + x + ". Y=" + y);
  }

  //Verificar que pode existir informações entre ( )
  public void escrevendoInterrompendo(String S) throws IOException {
    gravarArq = new PrintWriter(arq);
    gravarArq.println("Carregando " + S);
  }

  public void fecharArq () throws IOException {
    arq.close();
  }

  //**IGNOREM** tudo que está no main é tudo para testes
  public static void main(String[] args) throws IOException {
    Escrever leitura = new Escrever();
    try {
      Escrever.criarLog(5);
      leitura.escrevendo(5, 2.5, 3);
      leitura.escrevendo(6, 7.5, 5);
      leitura.escrevendoCarregando("TESTE-1");
      leitura.escrevendoTerminando("TESTE-2", 2, 3);
      leitura.fecharArq();
    }
    catch (IOException e) {
      System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
    }
  }
}
