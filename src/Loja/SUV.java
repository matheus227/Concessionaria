// Define o pacote ao qual a subclasse pertence.
package Loja;

/**
 * SUBCLASSE SUV
 *
 * Especialização de 'Veiculo' para utilitários esportivos e veículos 4x4.
 * É a categoria de maior valor no sistema, aplicando um acréscimo base de 30%,
 * adicionais específicos para modelos com tração nas quatro rodas e taxas de seguro e manutenção superiores.
 */
public class SUV extends Veiculo {

    // Atributo específico da classe SUV: indica se o modelo possui tração 4x4 (off-road).
    private boolean tracao4x4;

    /**
     * CONSTRUTOR
     * Repassa os parâmetros gerais para o construtor de 'Veiculo' e define a propriedade de tração 4x4.
     *
     * @param placa Placa do veículo.
     * @param marca Marca do veículo.
     * @param modelo Modelo do veículo.
     * @param ano Ano de fabricação.
     * @param valorBaseDiaria Valor padrão da diária base.
     * @param tracao4x4 Booleano indicando se possui tração 4x4.
     */
    public SUV(String placa, String marca, String modelo, int ano, double valorBaseDiaria, boolean tracao4x4) {
        // Invoca o construtor da superclasse 'Veiculo'.
        super(placa, marca, modelo, ano, valorBaseDiaria);

        // Atribui o parâmetro específico da subclasse SUV.
        this.tracao4x4 = tracao4x4;
    }

    /**
     * Getter para o atributo booleano 'tracao4x4'.
     * Convenção Java: Usa o prefixo 'is' por se tratar de um estado booleano (ex: "é tração 4x4?").
     *
     * @return true se o SUV tiver tração 4x4, false caso contrário.
     */
    public boolean isTracao4x4() {
        return tracao4x4;
    }

    /**
     * SOBREESCRITA DO CÁLCULO DA DIÁRIA (@Override)
     * Regra da categoria SUV:
     * 1. Valor base com acréscimo de 30% (multiplicador 1.30) vezes os dias alugados.
     * 2. Se possuir tração 4x4, soma uma taxa adicional off-road de R$ 50,00 por dia.
     *
     * @param dias Quantidade de dias contratados.
     * @return O valor total acumulado das diárias.
     */
    @Override
    public double calcularDiaria(int dias) {
        // Correção aplicada: Adicionado o operador de multiplicação (*).
        // (valorBaseDiaria * 1.30) adiciona 30% de taxa de categoria sobre a diária base.
        double valor = (valorBaseDiaria * 1.30) * dias;

        // Se o SUV for 4x4, cobra o adicional diário de uso off-road.
        if (tracao4x4) {
            // Correção aplicada: Adicionado operador de multiplicação (*) entre 50.0 e a variável dias.
            valor += 50.0 * dias; // Adicional off-road
        }

        return valor;
    }

    /**
     * SOBREESCRITA DO CÁLCULO DO SEGURO (@Override)
     * Regra da categoria SUV: Cobrança de seguro de categoria alta (R$ 60,00 por dia).
     *
     * @param dias Quantidade de dias alugados.
     * @return Valor total do seguro para a categoria SUV.
     */
    @Override
    public double calcularSeguro(int dias) {
        // Correção aplicada: Adicionado operador de multiplicação (*) entre 60.0 e a variável dias.
        return 60.0 * dias; // Seguro de categoria alta
    }

    /**
     * SOBREESCRITA DO CÁLCULO DE MANUTENÇÃO (@Override)
     * Regra da categoria SUV: Custo de manutenção preventiva superior devido ao porte e mecânica.
     *
     * @return Valor fixo de R$ 550,00.
     */
    @Override
    public double calcularManutencao() {
        return 550.00;
    }
}