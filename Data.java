import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Data {
    private int dia;
    private int mes;
    private int ano;
    private static String separador = "/";

    public Data(int dia, int mes, int ano) {
        setAno(ano);
        setMes(mes);
        setDia(dia);
    }

    public Data(String texto) {
        this.deserializar(texto);
    }

    public void deserializar(String texto) {
        String[] campos = texto.trim().split(separador);
        setAno(Integer.parseInt(campos[2]));
        setMes(Integer.parseInt(campos[1]));
        setDia(Integer.parseInt(campos[0]));
    }

    public String serializar() {
        return String.format("%02d%s%02d%s%04d", dia, separador, mes, separador, ano);
    }

    public int getDia() {
        return dia;
    }

    public int getMes() {
        return mes;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        if (ano < 1)
            throw new IllegalArgumentException("Ano inválido: " + ano);
        this.ano = ano;
    }

    public void setMes(int mes) {
        if (mes < 1 || mes > 12)
            throw new IllegalArgumentException("Mês inválido: " + mes);
        this.mes = mes;
    }

    public void setDia(int dia) {
        if (dia < 1 || dia > 31)
            throw new IllegalArgumentException("Dia inválido: " + dia);
        this.dia = dia;
    }

    // Negativo se this vem antes de outra, 0 se iguais, positivo se vem depois
    public int comparaCom(Data outra) {
        if (ano != outra.ano)
            return ano - outra.ano;
        if (mes != outra.mes)
            return mes - outra.mes;
        return dia - outra.dia;
    }

    public int diasAte(Data outra) {
        LocalDate inicio = LocalDate.of(ano, mes, dia); // Linha para transformar Data (nossa classe) em LocalDate
                                                        // (classe própria do java)
        LocalDate fim = LocalDate.of(outra.ano, outra.mes, outra.dia);
        return (int) ChronoUnit.DAYS.between(inicio, fim); // esse metodo da biblioteca calcula a diferença de dias
                                                           // entre duas datas
    }

    @Override
    public String toString() {
        return serializar();
    }
}