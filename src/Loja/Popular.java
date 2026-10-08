// Define o pacote ao qual a subclasse pertence.
package Loja;

/**
 * SUBCLASSE POPULAR
 *
 * Especialização da classe abstrata/base 'Veiculo'.
 * Representa a categoria de carros econômicos da locadora, adicionando
 * atributos específicos (como 'arCondicionado') e implementando suas próprias
 * regras de cálculo de diária, seguro e manutenção.
 */
public class Popular extends Veiculo {

    // Atributo específico da classe Popular (carros de outras categorias podem ter regras diferentes).
    private boolean arCondicionado;

    /**
     * CONSTRUTOR
     * Inicializa os atributos herdados da classe pai (Veiculo) e os específicos desta subclasse.
     *
     * @param placa Placa do veículo.
     * @param marca Marca/Fabricante.
     * @param modelo Modelo do carro.
     * @param ano Ano de fabricação.
     * @param valorBaseDiaria Valor padrão da diária base.
     * @param arCondicionado Indica se o carro possui ar-condicionado.
     */
    public Popular(String placa, String marca, String modelo, int ano, double valorBaseDiaria, boolean arCondicionado) {
        // 'super(...)': Chama obrigatoriamente o construtor da classe pai ('Veiculo').
        // Repassa os parâmetros gerais para serem validados e inicializados pela superclasse.
        super(placa, marca, modelo, ano, valorBaseDiaria);

        // Inicializa o atributo exclusivo da subclasse Popular.
        this.arCondicionado = arCondicionado;
    }

    /**
     * Getter para o atributo boolean 'arCondicionado'.
     * Convenção Java: Para atributos booleanos que indicam posse ou estado,
     * é comum utilizar o prefixo 'has' (tem) ou 'is' (é/está) em vez de 'get'.
     */
    public boolean hasArCondicionado() {
        return arCondicionado;
    }

    /**
     * SOBREESCRITA DO CÁLCULO DA DIÁRIA (@Override)
     * Regra da categoria Popular: Valor base * dias + taxa fixa por dia se tiver ar-condicionado.
     *
     * @param dias Quantidade de dias alugados.
     * @return O valor total das diárias para a categoria Popular.
     */
    @Override
    public double calcularDiaria(int dias) {
        // Multiplica o valor da diária base (herdado de Veiculo) pela quantidade de dias.
        // Correção aplicada: Adicionado operador de multiplicação (*) que faltava.
        double valor = valorBaseDiaria * dias;

        // Se o veículo tiver ar-condicionado, adiciona R$ 15,00 por dia ao valor total.
        if (arCondicionado) {
            valor += 15.0 * dias; // Taxa de opcional econômico
        }

        return valor;
    }

    /**
     * SOBREESCRITA DO CÁLCULO DO SEGURO (@Override)
     * Regra da categoria Popular: Valor fixo acessível de R$ 20,00 por dia.
     *
     * @param dias Quantidade de dias alugados.
     * @return O valor total do seguro.
     */
    @Override
    public double calcularSeguro(int dias) {
        // Correção aplicada: Adicionado operador de multiplicação (*) entre 20.0 e a variável dias.
        return 20.0 * dias; // Taxa acessível de seguro
    }

    /**
     * SOBREESCRITA DO CÁLCULO DE MANUTENÇÃO (@Override)
     * Regra da categoria Popular: Valor fixo de revisão básica (R$ 150,00).
     *
     * @return Valor fixo da revisão preventiva/manutenção.
     */
    @Override
    public double calcularManutencao() {
        return 150.00; // Revisão básica
    }
}