package sistema; 

public class Produto { 
    private int codigo; 
    private String nome; 
    private double preco; 
    
    public Produto(int codigo, String nome, double preco) { 
        this.codigo = codigo; 
        this.nome = nome; 
        this.preco = preco; 
    } 
    
    public int getCodigo() { 
        return codigo; 
    } 
    
    public String getNome() { 
        return nome; 
    } 
    public double getPreco() { 
        return preco; 
    } 
    
    public String toLinha() { 
        return codigo + "\\t" + nome + "\\t" + preco; 
    } 
    
    @Override 
    public String toString() { 
        return String.format("Código: %d | Nome: %s | Preço: R$ %.2f", codigo, nome, preco); 
    } 
}
