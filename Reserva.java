import java.util.ArrayList;

public class Reserva {
    private Data dtInicio;
    private Data dtFim;
    private String cliente;
    private Quarto quarto;
    private char status; // A - Ativa, C - Cancelada, I - Check-In, O - Check-Out
    private static String separador = "\t";

    public Reserva(Data dtInicio, Data dtFim, String cliente, Quarto quarto, char status) {
        setDtInicio(dtInicio);
        setDtFim(dtFim);
        setCliente(cliente);
        setQuarto(quarto);
        setStatus(status);
    }

    public Reserva(String linha, ArrayList<Quarto> quartos) {
        this.deserializar(linha, quartos);
    }

    public static String cabecalho() {
        return "dtInicio" + separador + "dtFim" + separador + "cliente" + separador
                + "quarto" + separador + "status";
    }


    public String serializar() {
        return System.lineSeparator()
                + dtInicio.serializar() + separador
                + dtFim.serializar() + separador
                + cliente + separador
                + quarto.getNumero() + separador
                + status;
    }

    public void deserializar(String linha, ArrayList<Quarto> quartos) {
        String[] campos = linha.split(separador);
        setDtInicio(new Data(campos[0]));
        setDtFim(new Data(campos[1]));
        setCliente(campos[2]);
        setQuarto(buscaQuartoPorNumero(Integer.parseInt(campos[3].trim()), quartos));
        setStatus(campos[4].trim().charAt(0));
    }

    private static Quarto buscaQuartoPorNumero(int numero, ArrayList<Quarto> quartos) {
        for (Quarto quarto : quartos) {
            if (quarto.getNumero() == numero) {
                return quarto;
            }
        }
        return null;
    }

    public int getQuantidadeDias() {
        return dtInicio.diasAte(dtFim) + 1; // + 1 pq estamos considerando o dia do checkout como dia reservado
    }

    public double valorTotalDiarias() {
        return getQuantidadeDias() * quarto.getDiaria();
    }

 
    public boolean estaVigente() {
        return status == 'A' || status == 'I';
    }

  
    public boolean ocupaData(Data data) {
        return estaVigente()
                && data.comparaCom(dtInicio) >= 0
                && data.comparaCom(dtFim) <= 0;
    }

    public boolean ocupaPeriodo(Data novoInicio, Data novoFim) {
        return estaVigente()
                && novoInicio.comparaCom(dtFim) <= 0
                && novoFim.comparaCom(dtInicio) >= 0;
    }


    public Data getDtInicio() {
        return dtInicio;
    }

    public void setDtInicio(Data dtInicio) {
        if (dtInicio == null)
            throw new IllegalArgumentException("Data inicial não pode ser nula");
        if (this.dtFim != null && dtInicio.comparaCom(this.dtFim) >= 0)
            throw new IllegalArgumentException("Data inicial deve ser anterior à data final");
        this.dtInicio = dtInicio;
    }

    public Data getDtFim() {
        return dtFim;
    }

    public void setDtFim(Data dtFim) {
        if (dtFim == null)
            throw new IllegalArgumentException("Data final não pode ser nula");
        if (this.dtInicio != null && dtFim.comparaCom(this.dtInicio) <= 0)
            throw new IllegalArgumentException("Data final deve ser posterior à data inicial");
        this.dtFim = dtFim;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        if (cliente == null || cliente.trim().isEmpty())
            throw new IllegalArgumentException("Nome do cliente não pode ser vazio");
        this.cliente = cliente.trim();
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public void setQuarto(Quarto quarto) {
        if (quarto == null)
            throw new IllegalArgumentException("Quarto não encontrado");
        this.quarto = quarto;
    }

    public char getStatus() {
        return status;
    }

    public void setStatus(char status) {
        char maiuscula = Character.toUpperCase(status);
        if (maiuscula != 'A' && maiuscula != 'C' && maiuscula != 'I' && maiuscula != 'O')
            throw new IllegalArgumentException("Status inválido (use A, C, I ou O): " + status);
        this.status = maiuscula;
    }

    private String nomeStatus() {
        switch (status) {
            case 'A':
                return "Ativa";
            case 'C':
                return "Cancelada";
            case 'I':
                return "Check-In";
            default:
                return "Check-Out";
        }
    }

    @Override
    public String toString() {
        return String.format("Reserva de %s | %s a %s | Status: %s%n%s",
                cliente, dtInicio, dtFim, nomeStatus(), quarto);
    }
}