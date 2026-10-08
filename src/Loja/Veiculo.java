// Define o pacote 'Loja', organizando os componentes de domínio do sistema.
package Loja;

/**
 * CLASSE ABSTRATA VEICULO
 *
 * Atua como a SUPERCLASSE (classe-base) para todos os tipos de veículos da frota (Popular, Sedan, SUV).
 *
 * Conceito de Classe Abstrata (abstract):
 * - NÃO pode ser instanciada diretamente (ex: 'new Veiculo(...)' gera erro de compilação).
 * - Serve para definir um modelo comum de atributos e comportamentos herdados por subclasses.
 * - Força as subclasses concretas a implementarem os métodos abstratos declarados.
 */
public abstract class Veiculo {

    // --- ATRIBUTOS PROTEGIDOS (protected) ---
    // O modificador 'protected' permite que as subclasses (Popular, Sedan, SUV)
    // acessem essas variáveis diretamente, mantendo-as protegidas contra acesso externo fora do pacote.
    protected String placa;
    protected String marca;
    protected String modelo;
    protected int ano;
    protected double valorBaseDiaria;
    protected boolean disponivel;
    protected Cliente clienteResponsavel; // Associação: Guarda a referência do cliente que alugou o veículo.

    /**
     * CONSTRUTOR DA SUPERCLASSE
     * Chamado implicitamente pelas subclasses através do comando 'super(...)'.
     * Executa os setters para garantir que todas as validações de integridade sejam aplicadas na criação.
     *
     * @param placa Placa do veículo.
     * @param marca Marca do fabricante.
     * @param modelo Modelo do veículo.
     * @param ano Ano de fabricação.
     * @param valorBaseDiaria Valor padrão da diária base.
     */
    public Veiculo(String placa, String marca, String modelo, int ano, double valorBaseDiaria) {
        // Usa os métodos setters internos para reaproveitar a lógica de validação.
        setPlaca(placa);
        setMarca(marca);
        setModelo(modelo);
        setAno(ano);
        setValorBaseDiaria(valorBaseDiaria);

        // Estado inicial padrão ao cadastrar um novo veículo no sistema.
        this.disponivel = true;
        this.clienteResponsavel = null;
    }

    /*===========================================================================
     * MÉTODOS ABSTRATOS (Contrato de Polimorfismo)
     *
     * Não possuem corpo/implementação nesta classe (terminam com ;).
     * OBRIGAM todas as subclasses concretas a fornecerem suas próprias regras de negócio.
     *===========================================================================*/

    /**
     * Calcula o valor total das diárias com base na quantidade de dias e categoria.
     */
    public abstract double calcularDiaria(int dias);

    /**
     * Calcula o valor do seguro com base na categoria do veículo.
     */
    public abstract double calcularSeguro(int dias);

    /**
     * Calcula o custo fixo de manutenção preventiva específico da categoria.
     */
    public abstract double calcularManutencao();

    /*===========================================================================
     * ENCAPSULAMENTO: Getters e Setters com Programação Defensiva
     *===========================================================================*/

    public String getPlaca() {
        return placa;
    }

    /**
     * Valida e formata a placa.
     * @param placa Placa do veículo.
     */
    public void setPlaca(String placa) {
        if (placa == null || placa.trim().isEmpty()) {
            throw new IllegalArgumentException("A placa do veículo é obrigatória!");
        }
        // trim() remove espaços extras e toUpperCase() padroniza a placa em letras maiúsculas.
        this.placa = placa.trim().toUpperCase();
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        if (marca == null || marca.trim().isEmpty()) {
            throw new IllegalArgumentException("A marca não pode ser vazia!");
        }
        this.marca = marca.trim();
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        if (modelo == null || modelo.trim().isEmpty()) {
            throw new IllegalArgumentException("O modelo não pode ser vazio!");
        }
        this.modelo = modelo.trim();
    }

    public int getAno() {
        return ano;
    }

    /**
     * Valida o ano de fabricação permitindo apenas um intervalo razoável.
     * @param ano Ano do veículo.
     */
    public void setAno(int ano) {
        if (ano < 1990 || ano > 2027) {
            throw new IllegalArgumentException("Ano fora do limite permitido (1990-2027)!");
        }
        this.ano = ano;
    }

    public double getValorBaseDiaria() {
        return valorBaseDiaria;
    }

    public void setValorBaseDiaria(double valorBaseDiaria) {
        if (valorBaseDiaria <= 0) {
            throw new IllegalArgumentException("Valor base da diária deve ser positivo!");
        }
        this.valorBaseDiaria = valorBaseDiaria;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public Cliente getClienteResponsavel() {
        return clienteResponsavel;
    }

    public void setClienteResponsavel(Cliente clienteResponsavel) {
        this.clienteResponsavel = clienteResponsavel;
    }

    /**
     * SOBREESCRITA DO MÉTODO toString()
     * Gera uma representação em texto detalhada do veículo, incluindo sua categoria dinâmica,
     * dados cadastrais e status atual de locação.
     *
     * @return String formatada com a ficha do veículo.
     */
    @Override
    public String toString() {
        // Monta o trecho do texto do cliente responsável apenas se o veículo estiver alugado.
        String resp = (clienteResponsavel != null && !disponivel)
                ? " | Responsável: " + clienteResponsavel.getNome() + " (CPF: " + String.format("%011d", clienteResponsavel.getCPF()) + ")"
                : "";

        // String.format centraliza a formatação:
        // %s -> String | %d -> Inteiro | %.2f -> Número decimal com 2 casas
        // getClass().getSimpleName() captura dinamicamente o nome da subclasse concreta (Popular, Sedan, SUV).
        return String.format("[%s] %s %s (%d) | Placa: %s | Base: R$ %.2f | Status: %s%s",
                getClass().getSimpleName(), marca, modelo, ano, placa, valorBaseDiaria,
                disponivel ? "DISPONÍVEL" : "ALUGADO", resp);
    }
}