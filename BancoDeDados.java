import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BancoDeDados {
    private static final int CAPACIDADE = 1_000_000;
    private final Mercadoria[] mercadorias;

    public int getQuantidade() {
        return quantidade;
    }

    private int quantidade;

    public BancoDeDados(String arquivoCSV) {
        mercadorias = new Mercadoria[1_000_000];
        quantidade = 0;
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
                if ((linha = leitor.readLine()) == null)
                    break;
                String[] colunas = linha.split(",");
                Mercadoria m = new Mercadoria(colunas[0], colunas[1], Double.parseDouble(colunas[2]));
                mercadorias[quantidade] = m;
                quantidade++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // método busca binaria implementado pelo grupo
    public Mercadoria pesquisarMercadoria(String chave) {
        int inicio = 0;
        int fim = quantidade - 1;

        while(inicio <= fim){
            int meio = inicio + (fim - inicio) /2;
            String codigoMeio = mercadorias[meio].getCodigo();
            int comparacao = chave.compareTo(codigoMeio);

            if(comparacao == 0) return mercadorias[meio];
            else if (comparacao < 0) fim = meio - 1;
            else  inicio =meio + 1;
        }
        return null;
    }

    // método merge sort implementado pelo grupo
    private void mergeSort(int low, int high) {
        if (low < high) {
            int middle = (low + high) / 2;
            mergeSort(low, middle);
            mergeSort(middle + 1, high);
            merge(low, middle, high);
        }
    }

    private void merge(int low, int middle, int high) {
        Mercadoria[] temp = new Mercadoria[high - low + 1];
        int i = low;
        int j = middle + 1;
        int k = 0;

        while (i <= middle && j <= high) {
            if (mercadorias[i].getCodigo().compareTo(mercadorias[j].getCodigo()) <= 0) {
                temp[k] = mercadorias[i];
                i++;
            } else {
                temp[k] = mercadorias[j];
                j++;
            }
            k++;
        }

        while (i <= middle) {
            temp[k] = mercadorias[i];
            i++;
            k++;
        }

        while (j <= high) {
            temp[k] = mercadorias[j];
            j++;
            k++;
        }

        for (k = 0; k < temp.length; k++) {
            mercadorias[low + k] = temp[k];
        }
    }

    public void ordenarPorCodigo() {
        mergeSort(0, quantidade - 1);
    }

}
