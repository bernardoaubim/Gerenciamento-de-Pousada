import java.util.ArrayList;

public class SistemaPousada {
    static Pousada pousada = new Pousada("Nome", "Contato");
    public static void main(String[] args) {
    
        pousada.carregaDados();

        int opcao = -1;

        while (opcao != 0) {

            System.out.println("\n================================");
            System.out.println("       SISTEMA DE POUSADA");
            System.out.println("================================");
            System.out.println("1 - Consultar disponibilidade");
            System.out.println("2 - Consultar reserva");
            System.out.println("3 - Realizar reserva");
            System.out.println("4 - Cancelar reserva");
            System.out.println("5 - Realizar check-in");
            System.out.println("6 - Realizar check-out");
            System.out.println("7 - Registrar consumo");
            System.out.println("8 - Salvar");
            System.out.println("0 - Sair");
            System.out.println("================================");

            opcao = Teclado.leInt("Escolha uma opção: ");

            switch (opcao) {

                case 1: {
                    
                    Data data = new Data(Teclado.leString("Informe a data (dd/mm/aaaa): "));
                    Quarto quarto = pousada.getQuarto(Teclado.leInt("Informe o número do quarto: "));

                    consultarDisponibilidade(data, quarto);
                    break;
                }
                case 2: {

                    String textoData = Teclado.leString("Informe a data ou Enter para ignorar: ");
                    Data data = textoData.isEmpty() ? null : new Data(textoData);

                    String cliente = Teclado.leString("Informe o nome do cliente ou Enter para ignorar: ");
                        if (cliente.trim().isEmpty()) cliente = null;

                    String textoQuarto = Teclado.leString("Informe o número do quarto ou Enter para ignorar: ");
                    Quarto quarto = textoQuarto.isEmpty() ? null : pousada.getQuarto(Integer.parseInt(textoQuarto));

                    consultarReserva(data, quarto, cliente);
                    break;
                }
                case 3: {

                    Data dtInicio = new Data(Teclado.leString("Informe a data inicial (dd/mm/aaaa): "));
                    Data dtFim = new Data(Teclado.leString("Informe a data final (dd/mm/aaaa): "));
                    String cliente =  Teclado.leString("Informe o nome do cliente: ");
                    Quarto quarto = pousada.getQuarto(Teclado.leInt("Informe o número do quarto: "));

                    realizarReserva(dtInicio, dtFim, cliente, quarto);
                    break;
                }
                case 4: {
                    String cliente =  Teclado.leString("Informe o nome do cliente: ");
                    
                    cancelarReserva(cliente); 
                    break;
                }
                case 5:
                    realizarCheckIn();
                    break;

                case 6:
                    realizarCheckOut();
                    break;

                case 7:
                    registrarConsumo();
                    break;

                case 8:
                    salvar();
                    break;
               
                case 0:
                    pousada.salvaDados();
                    System.out.println("Encerrando o sistema...");
                    break;
                 
                default:
                    System.out.println("Opção inválida!");
                    break;
            }
        }
    }
    public static void consultarDisponibilidade(Data data, Quarto quarto) {
        if (pousada.consultarDisponibilidade(data, quarto)){
           System.out.println("Quarto disponível. Seguem os respectivos dados:");
           quarto.exibeDadosQ();
        }
    }
    public static void consultarReserva(Data data, Quarto quarto, String cliente) {
        ArrayList<Reserva> encontradas = pousada.consultaReserva(data, cliente, quarto);

     if (encontradas.isEmpty()) {
            System.out.println("Não há reservas para os parâmetros informados.");
        } else {
            for (Reserva reserva : encontradas) {
                System.out.println(reserva);
            }
        }
    }
    public static void realizarReserva(Data dtInicio, Data dtFim, String cliente, Quarto quarto) {
        pousada.realizaReserva(dtInicio, dtFim, cliente, quarto);
    }

    static void cancelarReserva(String cliente) {
        pousada.cancelaReserva(cliente);
    }

    static void realizarCheckIn() {}

    static void realizarCheckOut() {}

    static void registrarConsumo() {}

    static void salvar() {}
}
