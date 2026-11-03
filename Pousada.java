import java.util.ArrayList;

public class Pousada {
    private String nome;
    private String contato;
    private ArrayList<Quarto> quartos;
    private ArrayList<Reserva> reservas;
    private ArrayList<Produto> produtos;

    public void carregaDados() {}
    public void salvaDados() {}
    public void consultaDisponibilidade(Data data, Quarto quarto) {}
    public void consultaReserva(Data data, String cliente, Quarto quarto) {}
    public void realizaReserva(Data dtIni, Data dtFim, String cliente, Quarto quarto) {}
    public void cancelaReserva(String cliente) {}
    public void realizaCheckIn(String cliente) {}
    public void realizaCheckOut(String cliente) {}
}