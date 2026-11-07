import java.util.ArrayList;

public class Quarto {
    private int numero;
    private char categoria; // S - Standard, M - Master, P - Premium
    private double diaria;
    private ArrayList<Produto> consumo = new ArrayList<>();
    private static String separador = "\t";

    public Quarto(int numero, char categoria, double diaria) {
        setNumero(numero);
        setCategoria(categoria);
        setDiaria(diaria);
    }

    public Quarto(String linha, ArrayList<Produto> produtos) {
        this.deserializar(linha, produtos);
    }

    public static String cabecalho() {
        return "numero" + separador + "categoria" + separador + "diaria" + separador + "consumo";
    }

    public String serializar() {
        String linha = System.lineSeparator() + numero + separador + categoria + separador + diaria;
        for (Produto produto : consumo) {
            linha += separador + produto.getCodigo();
        }
        return linha;
    }

    public void deserializar(String linha, ArrayList<Produto> produtos) {
        String[] campos = linha.split(separador);
        setNumero(Integer.parseInt(campos[0]));
        setCategoria(campos[1].charAt(0));
        setDiaria(Double.parseDouble(campos[2]));

        limpaConsumo();
        for (int i = 3; i < campos.length; i++) { // Aqui coloquei i=3 para percorrer a partir do indice 3 do vetor que é onde tem os códigos do produto
            int codigo = Integer.parseInt(campos[i].trim());
            Produto produto = buscaProdutoPorCodigo(codigo, produtos);
            if (produto != null) {
                consumo.add(produto);
            }
        }
    }

    private static Produto buscaProdutoPorCodigo(int codigo, ArrayList<Produto> produtos) {
        for (Produto produto : produtos) {
            if (produto.getCodigo() == codigo) {
                return produto;
            }
        }
        return null;
    }

    public void adicionaConsumo(Produto produto) {
        if (produto == null)
            throw new IllegalArgumentException("Produto não pode ser nulo");
        consumo.add(produto);
    }

    public void listaConsumo() {
        if (consumo.isEmpty()) {
            System.out.println("Nenhum consumo registrado.");
            return;
        }
        for (Produto produto : consumo) {
            System.out.format("- %s: R$ %.2f%n", produto.getNome(), produto.getPreco());
        }
    }

    public double valorTotalConsumo() {
        double total = 0;
        for (Produto produto : consumo) {
            total += produto.getPreco();
        }
        return total;
    }

    public void limpaConsumo() {
        consumo.clear();
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        if (numero < 1)
            throw new IllegalArgumentException("Número do quarto inválido: " + numero);
        this.numero = numero;
    }

    public char getCategoria() {
        return categoria;
    }

    public void setCategoria(char categoria) {
        char maiuscula = Character.toUpperCase(categoria);
        if (maiuscula != 'S' && maiuscula != 'M' && maiuscula != 'P')
            throw new IllegalArgumentException("Categoria inválida (use S, M ou P): " + categoria);
        this.categoria = maiuscula;
    }

    public double getDiaria() {
        return diaria;
    }

    public void setDiaria(double diaria) {
        if (diaria <= 0)
            throw new IllegalArgumentException("Diária inválida: " + diaria);
        this.diaria = diaria;
    }

    public ArrayList<Produto> getConsumo() {
        return consumo;
    }

    private String nomeCategoria() {
        switch (categoria) {
            case 'S':
                return "Standard";
            case 'M':
                return "Master";
            default:
                return "Premium";
        }
    }

    @Override
    public String toString() {
        return String.format("Quarto Nº: %d | Categoria: %s | Diária: R$ %.2f",
                numero, nomeCategoria(), diaria);
    }
}