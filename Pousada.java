import java.util.ArrayList;

public class Pousada {
    private String nome;
    private String contato;
    private ArrayList<Quarto> quartos;
    private ArrayList<Reserva> reservas;
    private ArrayList<Produto> produtos;

    public void carregaDados() {}
    public void salvaDados() {}
    public boolean consultarDisponibilidade(Data data, Quarto quarto) {

        if (quarto == null) {
           
            System.out.println("Quarto não encontrado. Verifique parâmetros.");
            return false;
        }
        
        int nQuarto = quarto.getNumero();

        for (Reserva r : reservas) {
           if (r.getQuarto().getNumero() == nQuarto
            && r.ocupaData(data)) {
               
                System.out.println("Quarto indisponível.");
                return false;
            }
        }
        return true;
    }
    public ArrayList<Reserva> consultaReserva(Data data, String cliente, Quarto quarto) {
        
        ArrayList<Reserva> encontradas = new ArrayList<>();

        if (data == null && cliente == null && quarto == null) {
            System.out.println("Informe ao menos um parâmetro para consulta.");
            return encontradas;
        }

        for (Reserva reserva : reservas) {
            if (reserva.getStatus() == 'A'
            && (data == null || reserva.ocupaData(data))
            && (cliente == null || reserva.getCliente().equalsIgnoreCase(cliente))
            && (quarto == null || reserva.getQuarto().getNumero() == quarto.getNumero())) {

                encontradas.add(reserva);
            }
        }
    return encontradas;
    }
    public void realizaReserva(Data dtInicio, Data dtFim, String cliente, Quarto quarto) {

        if (quarto == null) {
            System.out.println("Quarto não encontrado. Verifique parâmetros.");
            return;
        }
        for (Reserva r : reservas) {
            if (r.getQuarto().getNumero() == quarto.getNumero()
            && r.ocupaPeriodo(dtInicio, dtFim)) {
                System.out.println("Quarto já reservado durante o período requisitado.");
                return;
            }
            if (r.estaVigente() && r.getCliente().equalsIgnoreCase(cliente)) {
                System.out.println("Cliente já possui reserva.");
                return;
            }               
        }
        Reserva novaReserva = new Reserva(dtInicio, dtFim, cliente, quarto, 'A');
        reservas.add(novaReserva);
        System.out.println("Reserva efetuada com sucesso.");
    }
    public void cancelaReserva(String cliente) {
        for (Reserva r : reservas) {
            if (r.getCliente().equalsIgnoreCase(cliente) && r.getStatus() == 'A'){
                r.setStatus('C');
                System.out.println("Reserva cancelada com sucesso.");
                return;
            } 
        }
        System.out.println("Reserva não encontrada. Verifique o nome do titular.");
    }
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