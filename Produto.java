public class Produto {
    private int codigo;
    private String nome;
    private double preco;
    private static String separador = "\t";

    public Produto(int codigo, String nome, double preco) {
        setCodigo(codigo);
        setNome(nome);
        setPreco(preco);
    }

    public Produto(String linha) {
        this.deserializar(linha);
    }

    public static String cabecalho() {
        return "codigo" + separador + "nome" + separador + "preco";
    }

    public String serializar() {
        return System.lineSeparator() + codigo + separador + nome + separador + preco;
    }

    public void deserializar(String linha) {
        String[] campos = linha.split(separador);
        setCodigo(Integer.parseInt(campos[0]));
        setNome(campos[1]);
        setPreco(Double.parseDouble(campos[2]));
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo < 1)
            throw new IllegalArgumentException("Código inválido: " + codigo);
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty())
            throw new IllegalArgumentException("Nome não pode ser vazio");
        this.nome = nome.trim();
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (preco < 0)
            throw new IllegalArgumentException("Preço inválido: " + preco);
        this.preco = preco;
    }

    @Override
    public String toString() {
        return String.format("Código: %d | Nome: %s | Preço: R$ %.2f", codigo, nome, preco);
    }
}