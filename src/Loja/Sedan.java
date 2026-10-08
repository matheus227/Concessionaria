// Define o pacote ao qual a subclasse pertence.
package Loja;

/**
 * SUBCLASSE SEDAN
 *
 * Especialização da classe 'Veiculo' para carros de médio/grande porte.
 * Agrega a propriedade de 'capacidadePortaMalas' e ajusta os cálculos
 * de diária, seguro e manutenção para refletir o nível superior de conforto e porte.
 */
public class Sedan extends Veiculo {

    // Atributo específico da categoria Sedan (em litros, ex: 470.0 L).
    private double capacidadePortaMalas;

    /**
     * CONSTRUTOR
     * Inicializa os atributos gerais via superclasse e valida/atribui o atributo específico.
     *
     * @param placa Placa do veículo.
     * @param marca Marca do veículo.
     * @param modelo Modelo do carro.
     * @param ano Ano de fabricação.
     * @param valorBaseDiaria Valor padrão da diária base.
     * @param capacidadePortaMalas Capacidade do porta-malas em litros.
     * @throws IllegalArgumentException Se a capacidade do porta-malas for menor ou igual a zero.
     */
    public Sedan(String placa, String marca, String modelo, int ano, double valorBaseDiaria, double capacidadePortaMalas) {
        // Invoca o construtor da superclasse 'Veiculo' para reaproveitar sua lógica de inicialização.
        super(placa, marca, modelo, ano, valorBaseDiaria);

        // Defesa Programada / Programação Defensiva:
        // Garante que o objeto Sedan não seja instanciado em um estado inválido.
        if (capacidadePortaMalas <= 0) {
            throw new IllegalArgumentException("Capacidade do porta-malas deve ser maior que zero!");
        }

        this.capacidadePortaMalas = capacidadePortaMalas;
    }

    /**
     * Getter para obter a capacidade do porta-malas.
     * @return Capacidade em litros.
     */
    public double getCapacidadePortaMalas() {
        return capacidadePortaMalas;
    }

    /**
     * SOBREESCRITA DO CÁLCULO DA DIÁRIA (@Override)
     * Regra da categoria Sedan: Aplica um acréscimo de 15% sobre o valor base da diária
     * devido ao adicional de conforto/espaço interno.
     *
     * @param dias Quantidade de dias de aluguel.
     * @return O valor total acumulado das diárias.
     */
    @Override
    public double calcularDiaria(int dias) {
        // Correção aplicada: Ajustada a sintaxe do operador de multiplicação (*).
        // (valorBaseDiaria * 1.15) aplica uma taxa de +15% no valor base antes de multiplicar pelos dias.
        return (valorBaseDiaria * 1.15) * dias; // 15% de adicional de conforto
    }

    /**
     * SOBREESCRITA DO CÁLCULO DO SEGURO (@Override)
     * Regra da categoria Sedan: Taxa diária fixa de R$ 35,00.
     *
     * @param dias Quantidade de dias alugados.
     * @return O valor total do seguro para o período.
     */
    @Override
    public double calcularSeguro(int dias) {
        // Correção aplicada: Adicionado operador de multiplicação (*) entre 35.0 e a variável dias.
        return 35.0 * dias;
    }

    /**
     * SOBREESCRITA DO CÁLCULO DE MANUTENÇÃO (@Override)
     * Regra da categoria Sedan: Custo fixo de manutenção preventiva ajustado para carros de médio porte.
     *
     * @return Valor fixo de R$ 300,00.
     */
    @Override
    public double calcularManutencao() {
        return 300.00;
    }
}