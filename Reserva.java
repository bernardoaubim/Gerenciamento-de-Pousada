package sistema; 

public class Reserva { 
    private Data dtInicio; 
    private Data dtFim; 
    private String cliente; 
    private Quarto quarto; 
    private char status; // 'A' - Ativa, 'I' - Inativa/Encerrada 
    
    public Reserva(Data dtInicio, Data dtFim, String cliente, Quarto quarto, char status) { 
        this.dtInicio = dtInicio; 
        this.dtFim = dtFim; 
        this.cliente = cliente; 
        this.quarto = quarto; 
        this.status = Character.toUpperCase(status); 
    } 
    
    public Data getDtInicio() { 
        return dtInicio; 
    } 
    
    public Data getDtFim() { 
        return dtFim; 
    }
    
    public String getCliente() { 
        return cliente; 
    } 
    
    public Quarto getQuarto() {
        return quarto; 
    } 
    
    public char getStatus() { 
        return status; 
    } 
    
    public void setStatus(char status) { 
        this.status = Character.toUpperCase(status); 
    } 
    
    public String toLinha() { 
        return dtInicio + "\\t" + dtFim + "\\t" + cliente + "\\t" + quarto.getNumero() + "\\t" + status; 
    } 
    
    @Override 
    public String toString() { 
        return String.format("Cliente: %-10s | Quarto: %d | Período: %s até %s | Status: %s", cliente, quarto.getNumero(), dtInicio, dtFim, (status == 'A' ? "Ativa" : "Inativa")); 
    } 
}
