import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class Pousada {
    private String nome;
    private String contato;
    private ArrayList<Quarto> quartos = new ArrayList<>();
    private ArrayList<Reserva> reservas = new ArrayList<>();
    private ArrayList<Produto> produtos = new ArrayList<>();
    private static String separador = "\t";

    // true quando existe alteração ainda não gravada em arquivo
    private boolean dadosAlterados = false;

    public Pousada(String nome, String contato) {
        this.nome = nome;
        this.contato = contato;
    }

    public Pousada(String linha) {
        deserializar(linha);
    }

    public boolean temDadosNaoSalvos() {
        return dadosAlterados;
    }

    public void carregaDados() {
        try {
            String linha;

            FileReader reader1 = new FileReader("pousada.txt");
            BufferedReader brPousada = new BufferedReader(reader1);
            brPousada.readLine();
            deserializar(brPousada.readLine());
            brPousada.close();

            FileReader reader2 = new FileReader("produto.txt");
            BufferedReader brProduto = new BufferedReader(reader2);
            brProduto.readLine();
            while ((linha = brProduto.readLine()) != null) {
                Produto produto = new Produto(linha);
                produtos.add(produto);
            }
            brProduto.close();

            FileReader reader3 = new FileReader("quarto.txt");
            BufferedReader brQuarto = new BufferedReader(reader3);
            brQuarto.readLine();
            while ((linha = brQuarto.readLine()) != null) {
                Quarto quarto = new Quarto(linha, produtos);
                quartos.add(quarto);
            }
            brQuarto.close();

            FileReader reader4 = new FileReader("reserva.txt");
            BufferedReader brReserva = new BufferedReader(reader4);
            brReserva.readLine();
            while ((linha = brReserva.readLine()) != null) {
                Reserva reserva = new Reserva(linha, quartos);
                reservas.add(reserva);
            }
            brReserva.close();
        } catch (Exception e) {
            System.out.println("Erro ao carregar arquivos: " + e.getMessage());
        }
    }

    public void salvaDados() {
        try {
            FileWriter writer1 = new FileWriter("quarto.txt", false);
            BufferedWriter bwQuarto = new BufferedWriter(writer1);
            bwQuarto.write(Quarto.cabecalho());

            for (Quarto quarto : quartos) {
                bwQuarto.write(quarto.serializar());
            }

            bwQuarto.close();

            FileWriter writer2 = new FileWriter("reserva.txt", false);
            BufferedWriter bwReserva = new BufferedWriter(writer2);
            bwReserva.write(Reserva.cabecalho());

            Iterator<Reserva> iterador = reservas.iterator();

            while (iterador.hasNext()) {
                Reserva reserva = iterador.next();

                if (reserva.getStatus() == 'C' || reserva.getStatus() == 'O') {
                    iterador.remove();
                } else {
                    bwReserva.write(reserva.serializar());
                }
            }

            bwReserva.close();

            dadosAlterados = false;
            System.out.println("Dados salvos com sucesso.");

        } catch (IOException e) {
            System.out.println("Erro ao salvar arquivos: " + e.getMessage());
        }
    }

    public void consultaDisponibilidade(Data data, Quarto quarto) {
        if (quarto == null) {
            System.out.println("Quarto não encontrado. Verifique parâmetros.");
            return;
        }

        for (Reserva r : reservas) {
            if (r.getQuarto().getNumero() == quarto.getNumero() && r.ocupaData(data)) {
                System.out.println("Quarto indisponível nesta data.");
                return;
            }
        }
        // só chega aqui se NENHUMA reserva ocupa a data
        System.out.println("Quarto disponível. Seguem os respectivos dados:");
        quarto.exibeDadosQ();
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
        dadosAlterados = true;
        System.out.println("Reserva efetuada com sucesso.");
    }

    public void cancelaReserva(String cliente) {
        for (Reserva r : reservas) {
            if (r.getCliente().equalsIgnoreCase(cliente) && r.getStatus() == 'A') {
                r.setStatus('C');
                dadosAlterados = true;
                System.out.println("Reserva cancelada com sucesso.");
                return;
            }
        }
        System.out.println("Reserva ativa não encontrada. Verifique o nome do titular.");
    }

    public void realizaCheckIn(String cliente) {
        boolean jaRealizouCheckIn = false;

        for (Reserva reserva : reservas) {
            if (reserva.getCliente().equalsIgnoreCase(cliente)) {
                if (reserva.getStatus() == 'A') {
                    reserva.setStatus('I');
                    dadosAlterados = true;

                    System.out.println("\n==== CHECK-IN ====");
                    System.out.println("Data de início: " + reserva.getDtInicio());
                    System.out.println("Data de fim: " + reserva.getDtFim());
                    System.out.println("Quantidade de dias: " + reserva.getQuantidadeDias());
                    System.out.printf("Valor total das diárias: R$ %.2f%n", reserva.valorTotalDiarias());

                    System.out.println("\nDados do quarto:");
                    reserva.getQuarto().exibeDadosQ();

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

    public void realizaCheckOut(String cliente) {
        Reserva reserva = buscaReservaEmCheckIn(cliente);

        if (reserva == null) {
            System.out.println("Check-in ativo não encontrado para este cliente.");
            return;
        }

        Quarto quarto = reserva.getQuarto();
        int quantidadeDias = reserva.getQuantidadeDias();
        double valorDiarias = reserva.valorTotalDiarias();
        double valorConsumo = quarto.valorTotalConsumo();

        System.out.println("\n==== CHECK-OUT ====");
        System.out.println("Data de início: " + reserva.getDtInicio());
        System.out.println("Data final: " + reserva.getDtFim());
        System.out.println("Quantidade de dias: " + quantidadeDias);
        System.out.printf("Valor das diárias: R$ %.2f%n", valorDiarias);

        System.out.println("\nConsumo:");
        quarto.listaConsumo();
        System.out.printf("Valor total dos consumos: R$ %.2f%n", valorConsumo);

        System.out.printf("%nValor final a pagar: R$ %.2f%n", valorDiarias + valorConsumo);

        reserva.setStatus('O');
        quarto.limpaConsumo();
        dadosAlterados = true;

        System.out.println("Check-out realizado com sucesso!");
    }

    // Devolve a reserva do cliente que está em check-in (status I) ou null
    public Reserva buscaReservaEmCheckIn(String cliente) {
        for (Reserva reserva : reservas) {
            if (reserva.getCliente().equalsIgnoreCase(cliente) && reserva.getStatus() == 'I') {
                return reserva;
            }
        }
        return null;
    }

    public void listaProdutosDisponiveis() {
        System.out.println("\n==== PRODUTOS DA COPA ====");
        for (Produto produto : produtos) {
            System.out.println(produto);
        }
    }

    public void registrarConsumo(String cliente, Produto produto) {
        if (produto == null) {
            System.out.println("Produto não disponível na copa.");
            return;
        }

        Produto produtoDisponivel = getProduto(produto.getCodigo());

        if (produtoDisponivel == null) {
            System.out.println("Produto não disponível na copa.");
            return;
        }

        Reserva reserva = buscaReservaEmCheckIn(cliente);

        if (reserva == null) {
            System.out.println("Check-in ativo não encontrado para este cliente.");
            return;
        }

        reserva.getQuarto().adicionaConsumo(produtoDisponivel);
        dadosAlterados = true;

        System.out.println("Consumo adicionado com sucesso!");
    }

    // GetById porém N quarto é o id, logo numeros repitidos podem gerar erros
    public Quarto getQuarto(int numero) {
        for (Quarto q : quartos) {
            if (q.getNumero() == numero) {
                return q;
            }
        }
        return null;
    }

    public Produto getProduto(int codigo) {
        for (Produto p : produtos) {
            if (p.getCodigo() == codigo) {
                return p;
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
