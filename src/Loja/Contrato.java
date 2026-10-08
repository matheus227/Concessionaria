// Define o pacote 'Loja', onde residem as classes de domínio da locadora.
package Loja;

/**
 * CLASSE CONTRATO
 *
 * Modela uma transação de locação entre um Cliente e um Veiculo.
 * Implementa a interface 'Imprimivel', garantindo que a classe cumpra o contrato
 * de implementação dos métodos de geração e emissão de comprovantes oficiais.
 */
public class Contrato implements Imprimivel {

    // --- ATRIBUTO ESTÁTICO (static) ---
    // Variável compartilhada entre todas as instâncias para gerar IDs únicos e sequenciais.
    // Começa em 1000 para que o primeiro contrato receba o ID 1001.
    private static int contadorId = 1000;

    // --- ATRIBUTOS DE INSTÂNCIA ---
    private int idContrato;
    private Cliente cliente;         // Associação: O contrato guarda a referência do Cliente contratante.
    private Veiculo veiculo;         // Associação/Polimorfismo: Guarda a referência de qualquer Veículo (Popular, Sedan, SUV).
    private int diasContratados;
    private double valorTotal;
    private boolean ativo;           // Indica o status do contrato (true = aberto/em andamento, false = encerrado).

    /**
     * CONSTRUTOR
     * Inicializa o contrato, calcula o custo total e atualiza o estado do veículo.
     *
     * @param cliente Instância do cliente que está realizando o aluguel.
     * @param veiculo Instância do veículo que está sendo alugado.
     * @param diasContratados Quantidade de dias previstos para a locação.
     */
    public Contrato(Cliente cliente, Veiculo veiculo, int diasContratados) {
        // Pré-incremento (++contadorId): Incrementa 'contadorId' antes de atribuir o valor a 'idContrato'.
        // Garante um ID único e sequencial automaticamente para cada contrato instanciado.
        this.idContrato = ++contadorId;

        this.cliente = cliente;
        this.veiculo = veiculo;
        this.diasContratados = diasContratados;

        // POLIMORFISMO EM AÇÃO:
        // Chama os métodos calcularDiaria e calcularSeguro do objeto 'veiculo'.
        // O Java executará a regra de cálculo específica da subclasse real (Popular, Sedan ou SUV) dinamicamente.
        this.valorTotal = veiculo.calcularDiaria(diasContratados) + veiculo.calcularSeguro(diasContratados);

        // Define o contrato como ativo na sua criação.
        this.ativo = true;

        // Atualização de Estado / Efeito Colateral do Negócio:
        // Vincula este cliente ao veículo e marca o veículo como indisponível para novos aluguéis.
        this.veiculo.setClienteResponsavel(cliente);
        this.veiculo.setDisponivel(false);
    }

    // --- MÉTODOS GETTERS ---
    // Métodos para consulta dos valores dos atributos privados da classe.

    public int getIdContrato() {
        return idContrato;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    /**
     * Retorna o status do contrato.
     * Convenção Java: Métodos getters para atributos do tipo 'boolean' costumam começar com 'is' em vez de 'get'.
     */
    public boolean isAtivo() {
        return ativo;
    }

    /**
     * Encerra o contrato atual (Devolução do veículo).
     * Libera o veículo para novas locações e remove a associação do cliente responsável.
     */
    public void encerrar() {
        this.ativo = false;

        // Restaura o estado do veículo para disponível e remove o responsável.
        this.veiculo.setDisponivel(true);
        this.veiculo.setClienteResponsavel(null);
    }

    // --- IMPLEMENTAÇÃO DA INTERFACE IMPRIMIVEL ---

    /**
     * Sobreescreve o método obrigatorio da interface 'Imprimivel'.
     * Constrói e formata o comprovante do contrato em formato texto.
     *
     * @return String contendo o texto completo do comprovante.
     */
    @Override
    public String gerarComprovante() {
        // Uso da classe StringBuilder:
        // É muito mais eficiente em uso de memória do que concatenar Strings com o operador '+' repetidamente,
        // pois cria um buffer mutável para montar o texto.
        StringBuilder sb = new StringBuilder();

        sb.append("====================================================\n");
        sb.append("      LOCADORA RELAMPAGO MATT - CONTRATO #").append(idContrato).append("\n");
        sb.append("====================================================\n");

        // Obtém dados do cliente associado
        sb.append("CLIENTE: ").append(cliente.getNome())
                .append(" (CPF: ").append(String.format("%011d", cliente.getCPF())).append(")\n");

        // Obtém dados do veículo associado
        sb.append("VEÍCULO: ").append(veiculo.getMarca()).append(" ").append(veiculo.getModelo())
                .append(" | Placa: ").append(veiculo.getPlaca()).append("\n");

        // REFLECTION (Introspecção de Tipos):
        // veiculo.getClass().getSimpleName() descobre em tempo de execução qual é o nome exato
        // da subclasse concreta (ex: "Popular", "Sedan" ou "SUV") sem precisar de vários 'if/else' ou 'switch'.
        sb.append("CATEGORIA: ").append(veiculo.getClass().getSimpleName()).append("\n");

        sb.append("DIAS CONTRATADOS: ").append(diasContratados).append("\n");

        // Formatação monetária com duas casas decimais (%.2f)
        sb.append(String.format("DIÁRIAS: R$ %.2f | SEGURO: R$ %.2f\n",
                veiculo.calcularDiaria(diasContratados),
                veiculo.calcularSeguro(diasContratados)));

        sb.append(String.format("VALOR TOTAL: R$ %.2f\n", valorTotal));

        // Operador Ternário (condicao ? verdadeiro : falso):
        // Se 'ativo' for true, exibe "EM ANDAMENTO...", caso contrário exibe "FINALIZADO...".
        sb.append("STATUS: ").append(ativo ? "EM ANDAMENTO (VEÍCULO ALUGADO)" : "FINALIZADO E DEVOLVIDO").append("\n");

        sb.append("====================================================\n");

        // Converte o buffer do StringBuilder para uma String comum e retorna
        return sb.toString();
    }

    /**
     * Sobreescreve o segundo método obrigatorio da interface 'Imprimivel'.
     * Responsável por imprimir diretamente o comprovante gerado no console.
     */
    @Override
    public void imprimir() {
        System.out.println(gerarComprovante());
    }
}