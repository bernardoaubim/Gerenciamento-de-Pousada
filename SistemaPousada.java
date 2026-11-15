import java.util.Scanner;

public class SistemaPousada {
    static Pousada pousada = new Pousada();

    static Scanner sc = new Scanner(System.in);
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
            System.out.print("Escolha uma opção: ");

            opcao = sc.nextInt();
            sc.nextLine();

            switch (opcao) {

                case 1:
                    
                    Data data = new Data(Teclado.leString("Informe a data (dd/mm/aaaa): "));
                    int nQuarto = Teclado.leInt("Informe o número do quarto: ");

                    consultarDisponibilidade(data, nQuarto);
                    break;

                case 2:
                    consultarReserva();
                    break;

                case 3:
                    realizarReserva();
                    break;

                case 4:
                    cancelarReserva();
                    break;

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

        sc.close();
    }

    public static void consultarDisponibilidade(Data data, int nQuarto) {
        if (pousada.consultaDisponibilidadeP(data, nQuarto)){
           Quarto quarto = pousada.getQuarto(nQuarto);
           System.out.println("Quarto disponível. Seguem os respectivos dados:");
           quarto.exibeDadosQ();
        }

    }

    static void consultarReserva() {}

    static void realizarReserva() {}

    static void cancelarReserva() {}

    static void realizarCheckIn() {}

    static void realizarCheckOut() {}

    static void registrarConsumo() {}

    static void salvar() {}
}
