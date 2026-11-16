import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

public class Pousada {
    private String nome;
    private String contato;
    private ArrayList<Quarto> quartos = new ArrayList<>();
    private ArrayList<Reserva> reservas = new ArrayList<>();
    private ArrayList<Produto> produtos = new ArrayList<>();
    private static String separador = "\t";

    public Pousada (String nome, String contato){
        this.nome =  nome;
        this.contato = contato;
    }
    
    public Pousada (String linha){
        deserializar(linha);
    }

    public void carregaDados() {
        try { 
            String linha;

            FileReader reader1 = new FileReader("pousada.txt");
            BufferedReader brPousada = new  BufferedReader(reader1);
            brPousada.readLine();
            deserializar(brPousada.readLine());
            brPousada.close();

            FileReader reader2 = new FileReader("produto.txt");
            BufferedReader brProduto = new  BufferedReader(reader2);
            brProduto.readLine();
            while ((linha = brProduto.readLine()) != null) {
                Produto produto = new Produto(linha);
                produtos.add(produto);
            }
            brProduto.close();
        
            FileReader reader3 = new FileReader("quarto.txt");
            BufferedReader brQuarto = new  BufferedReader(reader3);
            brQuarto.readLine();
            while ((linha = brQuarto.readLine()) != null) {
                Quarto quarto = new Quarto(linha, produtos);
                quartos.add(quarto);
            }    
            brQuarto.close();
            
            FileReader reader4 = new FileReader("reserva.txt");
            BufferedReader brReserva = new  BufferedReader(reader4); 
            brReserva.readLine(); 
            while ((linha = brReserva.readLine()) != null) {  
            Reserva reserva = new Reserva(linha, quartos);
            reservas.add(reserva);
            }
            brReserva.close();
        } 
        catch (Exception e) {
            System.out.println("Erro ao carregar arquivos: " + e.getMessage());        
        }
    }
    public void salvaDados() {}
    public void consultarDisponibilidade(Data data, Quarto quarto) {

    if (quarto == null) {
        System.out.println("Quarto não encontrado. Verifique parâmetros.");
        return;
    }

    int nQuarto = quarto.getNumero();

    for (Reserva r : reservas) {
        if (r.getQuarto().getNumero() == nQuarto && r.ocupaData(data)) {
            System.out.println("Quarto indisponível.");
            return;
        }
        
        System.out.println("Quarto disponível. Seguem os respectivos dados:");
        quarto.exibeDadosQ();
    }
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
    public void realizaCheckIn(String cliente) {
        boolean jaRealizouCheckIn = false;

        for (Reserva reserva : reservas) {
            if (reserva.getCliente().equalsIgnoreCase(cliente)) {
                if (reserva.getStatus() == 'A') {
                    reserva.setStatus('I');
                    System.out.println("Check-in realizado com sucesso!");
                    return;
                }
                if (reserva.getStatus() == 'I') {
                    jaRealizouCheckIn = true;
                }
            }
        }

        if (jaRealizouCheckIn) {
            System.out.println("Cliente já realizou o check-in.");
        } else {
            System.out.println("Reserva ativa não encontrada para este cliente.");
        }
    }
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
    public void deserializar(String linha) {
        String[] campos = linha.split(separador);
        nome = campos[0];
        contato = campos[1];
    }
}
