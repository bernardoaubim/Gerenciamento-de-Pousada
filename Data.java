import java.time.LocalDate;
import java.time.YearMonth;
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
        if (campos.length != 3)
            throw new IllegalArgumentException("Data inválida, use dd/mm/aaaa: " + texto);
        try {
            setAno(Integer.parseInt(campos[2].trim()));
            setMes(Integer.parseInt(campos[1].trim()));
            setDia(Integer.parseInt(campos[0].trim()));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Data inválida, use dd/mm/aaaa: " + texto);
        }
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

    // Valida o dia conforme o mês e o ano (ex.: 31/02 e 29/02 em ano não bissexto
    // são inválidos)
    public void setDia(int dia) {
        int ultimoDiaDoMes = YearMonth.of(ano, mes).lengthOfMonth();
        if (dia < 1 || dia > ultimoDiaDoMes)
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
        LocalDate inicio = LocalDate.of(ano, mes, dia); // Transforma Data (nossa classe) em LocalDate (classe do Java)
        LocalDate fim = LocalDate.of(outra.ano, outra.mes, outra.dia);
        return (int) ChronoUnit.DAYS.between(inicio, fim); // Calcula a diferença de dias entre duas datas
    }

    @Override
    public String toString() {
        return serializar();
    }
}
