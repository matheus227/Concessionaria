// Define o pacote ao qual a classe pertence.
// Organiza as entidades de negócio da aplicação (domínio do sistema).
package Loja;

// Importação das estruturas de dados do pacote java.util:
// Map é uma interface de chave-valor.
// HashMap é a implementação baseada em Tabela Espalhada (Hash Table).
import java.util.HashMap;
import java.util.Map;

/**
 * CLASSE CLIENTE
 * Modela os dados cadastrais dos clientes da locadora e atua como um
 * repositório estático em memória, permitindo busca rápida por CPF em O(1).
 */
public class Cliente {

    // --- ATRIBUTOS DE INSTÂNCIA ---
    // Pertencem a CADA OBJETO 'Cliente' criado individualmente.
    // O modificador 'private' garante o Encapsulamento (proteção dos dados).
    private String nome;
    private long cpf;

    // --- ATRIBUTOS ESTÁTICOS (static) ---
    // Pertencem à CLASSE como um todo, compartilhados por todas as instâncias de Cliente.

    // Contabiliza o total de clientes instanciados no sistema.
    private static int quantidade;

    // Repositório em memória (Tabela Hash):
    // Chave (K): Long -> CPF do cliente.
    // Valor (V): Cliente -> A referência para o próprio objeto Cliente.
    private static Map<Long, Cliente> bancoDeClientes = new HashMap<>();

    /**
     * CONSTRUTOR
     * Chamado na criação de um novo objeto: 'new Cliente(nome, cpf)'.
     *
     * @param nome Nome completo do cliente.
     * @param cpf Número do CPF (apenas números, tipo long).
     * @throws IllegalArgumentException se o CPF for duplicado ou os dados forem inválidos.
     */
    public Cliente(String nome, long cpf) {
        // Usa os métodos setters para aplicar as regras de validação antes de atribuir os valores.
        this.setCPF(cpf);
        this.setNome(nome);

        // Regra de Negócio: Não permite cadastrar dois clientes com o mesmo CPF.
        // O método containsKey() verifica a existência da chave no HashMap em tempo constante O(1).
        if (bancoDeClientes.containsKey(cpf)) {
            throw new IllegalArgumentException("CPF " + cpf + " já está cadastrado!");
        }

        // Armazena a referência deste objeto 'this' no mapa estático associado à sua chave (cpf).
        bancoDeClientes.put(cpf, this);

        // Incrementa o contador geral de clientes da classe.
        Cliente.quantidade += 1;
    }

    /**
     * MÉTODO ESTÁTICO DE BUSCA
     * Permite consultar um cliente diretamente pelo CPF sem precisar instanciar a classe.
     * Exemplo de uso: Cliente c = Cliente.buscarPorCPF(12345678901L);
     *
     * Complexidade O(1): Como utiliza um HashMap, a busca é quase instantânea,
     * independente da quantidade de clientes cadastrados.
     *
     * @param cpf O CPF a ser pesquisado.
     * @return O objeto Cliente encontrado ou 'null' se não existir no mapa.
     */
    public static Cliente buscarPorCPF(long cpf) {
        return bancoDeClientes.get(cpf);
    }

    // --- MÉTODOS GETTERS ---
    // Fornecem acesso de leitura aos atributos privados da instância/classe.

    public String getNome() {
        return nome;
    }

    public long getCPF() {
        return cpf;
    }

    // Método estático para ler um atributo estático.
    public static int getQuantidade() {
        return quantidade;
    }

    // --- MÉTODOS SETTERS (Com Validações de Regra de Negócio) ---

    /**
     * Valida e atribui o CPF.
     * @param cpf Número do CPF.
     */
    public void setCPF(long cpf) {
        // Validação: CPF não pode ser negativo ou ter mais de 11 dígitos.
        // String.valueOf(cpf) converte o número para String temporariamente para medir o tamanho (.length()).
        if (cpf <= 0 || String.valueOf(cpf).length() > 11) {
            throw new IllegalArgumentException("CPF deve ser um número positivo de até 11 dígitos!");
        }
        this.cpf = cpf;
    }

    /**
     * Valida e atribui o Nome do cliente.
     * @param nome Nome do cliente.
     */
    public void setNome(String nome) {
        // Validação 1: O nome não pode ser nulo nem conter apenas espaços em branco.
        // nome.trim() remove os espaços do início e do fim da String.
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome não pode ser vazio!");
        }

        // Validação 2 (Expressão Regular / Regex):
        // ".*\\d.*" significa: "qualquer texto (.*) seguido de pelo menos um dígito (\\d) e qualquer texto (.*)".
        // Garante que o nome não contenha números.
        if (nome.matches(".*\\d.*")) {
            throw new IllegalArgumentException("Nome inválido! O nome não pode conter números.");
        }

        // Se passar em todas as validações, salva o nome sem espaços sobressalentes nas pontas.
        this.nome = nome.trim();
    }

    /**
     * SOBREESCRITA DO MÉTODO toString()
     * Sobreescreve o método herdado da classe base do Java (java.lang.Object).
     * Define como o objeto Cliente será exibido textual e amigavelmente em prints ou logs.
     *
     * @return String formatada com os dados do cliente.
     */
    @Override
    public String toString() {
        // String.format("%011d", this.cpf): Formata o número do CPF para SEMPRE exibir 11 dígitos.
        // Se o CPF tiver menos de 11 dígitos (ex: 123456789), ele preenche com zeros à esquerda (ex: 00123456789).
        return "Nome: " + this.nome + " | CPF: " + String.format("%011d", this.cpf);
    }
}