import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BancoDeDados {
    private static int CAPACIDADE = 1_000_000;
    private Mercadoria[] mercadorias;
    public int getQuantidade() {
        return quantidade;
    }
    private int quantidade;
    public BancoDeDados(String arquivoCSV)  {
        mercadorias = new Mercadoria[1_000_000];
        quantidade=0;
        carregarArquivo(arquivoCSV);
        ordenarPorCodigo();
    }
    private void carregarArquivo(String arquivoCSV) {
        FileReader arquivo = null;
        try {
            arquivo = new FileReader(arquivoCSV);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        BufferedReader leitor = new BufferedReader(arquivo);
        String linha;
        try {
            linha = leitor.readLine();
            while (linha != null) {
                if (!((linha = leitor.readLine()) != null)) break;
                String[] colunas = linha.split(",");
                Mercadoria m = new Mercadoria(colunas[0], colunas[1], Double.parseDouble(colunas[2]));
                mercadorias[quantidade] = m;
                quantidade++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Mercadoria pesquisarMercadoria(String chave) {
        for (int i = 0; i < quantidade; i++) {
            if(chave.equals(mercadorias[i].getCodigo())) return mercadorias[i];
        }
        return null;
    }
    private void ordenarPorCodigo() {
        for (int i = 0; i < this.quantidade; i++) {
            for (int j = 0; j < this.quantidade-i-1; j++) {
                if(mercadorias[j].getCodigo().compareTo(mercadorias[j+1].getCodigo())>0)  {
                    Mercadoria temp = mercadorias[j+1];
                    mercadorias[j+1] = mercadorias[j];
                    mercadorias[j] = temp;
                }
            }
        }
    }
}
