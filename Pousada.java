import java.util.ArrayList;

public class Pousada {
    private String nome;
    private String contato;
    private ArrayList<Quarto> quartos;
    private ArrayList<Reserva> reservas;
    private ArrayList<Produto> produtos;

    public void carregaDados() {}
    public void salvaDados() {}
    public boolean consultaDisponibilidadeP(Data data, int nQuarto) {
        boolean disp = true;

        Quarto quarto = getQuarto(nQuarto);

        if (quarto == null) {
            disp = false;
            System.out.println("Quarto não encontrado. Verifique parâmetros.");
        }

        for (Reserva r : reservas) {
           if (r.getQuarto().getNumero() == nQuarto
            && r.ocupaData(data)) {
                disp = false;
                System.out.println("Quarto indisponível.");
                break;
            }
        }
        return disp;
    }
    public void consultaReserva(Data data, String cliente, Quarto quarto) {}
    public void realizaReserva(Data dtIni, Data dtFim, String cliente, Quarto quarto) {}
    public void cancelaReserva(String cliente) {}
    public void realizaCheckIn(String cliente) {}
    public void realizaCheckOut(String cliente) {}

    

    //GetById porém N quarto é o id, logo numeros repitidos podem gerar erros
    public Quarto getQuarto(int numero) {
    for (Quarto q : quartos) {
        if (q.getNumero() == numero) {
            return q;
        }
    }
    return null;
}
}