package sistema; 

public class Quarto { 
    private int numero; 
    private char categoria; // 'S' - Solteiro, 'M' - Master, 'P' - Presidencial 
    private double diaria; 
    private int[] consumo; 
    private int totalConsumo; 
    
    public Quarto(int numero, char categoria, double diaria) { 
        this.numero = numero; 
        this.categoria = Character.toUpperCase(categoria); 
        this.diaria = diaria; 
        this.consumo = new int[50]; 
    } 
    
    public int getNumero() { 
        return numero; 
    } 
    
    public char getCategoria() {
        return categoria; 
    } 
    
    public double getDiaria() { 
        return diaria; 
    } 
    
    public int[] getConsumo() { 
        return consumo; 
    } 
    
    public int getTotalConsumo() { 
        return totalConsumo; 
    } 
    
    public void adicionarConsumo(int codigoProduto) { 
        if (totalConsumo &lt; consumo.length) { 
            consumo[totalConsumo] = codigoProduto; 
            totalConsumo++; 
        } 
    } 
    
    public void limparConsumo() { 
        totalConsumo = 0; 
    } 
    
    public String toLinha() { 
        StringBuilder sb = new StringBuilder(); 
        sb.append(numero).append("\\t").append(categoria).append("\\t").append(diaria); 
        for (int i = 0; i &lt; totalConsumo; i++) { 
            sb.append("\\t").append(consumo[i]); 
        } 
        return sb.toString(); 
    } 
    
    @Override 
    public String toString() { 
        return String.format("Quarto Nº: %d | Categoria: %c | Diária: R$ %.2f | Itens Consumidos: %d", numero, categoria, diaria, totalConsumo); 
    } 
}
