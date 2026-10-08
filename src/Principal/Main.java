// Define o pacote ao qual esta classe pertence.
// Organiza o projeto agrupando classes de uma mesma camada ou responsabilidade.

//** Código comentado por Inteligencia Artificial. I.A Usada nos commits: Gemini **//
package Principal;

// Importações de classes de outro pacote ('Loja').
// Permitem usar as classes Popular, SUV e Sedan sem precisar escrever o caminho completo (Loja.Popular) todas as vezes.
import Loja.Popular;
import Loja.SUV;
import Loja.Sedan;

/**
 * CLASSE MAIN
 * Ponto de entrada (entry point) do sistema.
 *
 * Herança (extends Interface):
 * Esta classe herda atributos e métodos da classe 'Interface', como a lista 'frota',
 * o método 'menuPrincipal()', entre outros métodos de interação com o usuário.
 */
public class Main extends Interface {

    /**
     * Método principal que o JVM (Java Virtual Machine) procura e executa primeiro ao rodar o programa.
     *
     * @param args Vetor de strings para receber argumentos via linha de comando (não utilizado aqui).
     */
    public static void main(String[] args) {

        // Instancia a própria classe Main para podermos acessar seus atributos e métodos herdados
        // em um contexto não-estático (já que o método main é static).
        Main sistema = new Main();

        // Bloco try-catch: Usado para tratar exceções (erros que podem ocorrer durante a execução).
        // Aqui é feito um "seed" ou "carga inicial" de dados fictícios para testes.
        try {
            // Instancia novos clientes (executa o construtor da classe Loja.Cliente).
            // O 'L' no final do número indica que o valor é do tipo 'long' (64 bits),
            // necessário para CPF/CNPJ sem pontuação que ultrapassam o limite do tipo 'int'.
            new Loja.Cliente("Maria Silva", 12345678901L);
            new Loja.Cliente("João Souza", 98765432100L);

            // Adiciona diferentes veículos à coleção 'frota' (herdada da classe Interface).
            // Conceito de Polimorfismo: Como 'Popular', 'Sedan' e 'SUV' provavelmente herdam
            // de uma classe base (ex: 'Veiculo'), todos podem ser armazenados na mesma lista 'frota'.
            sistema.frota.add(new Popular("ABC-1234", "Fiat", "Mobi", 2023, 90.0, true));
            sistema.frota.add(new Sedan("XYZ-5678", "Toyota", "Corolla", 2024, 180.0, 470.0));
            sistema.frota.add(new SUV("SUV-9999", "Jeep", "Compass", 2024, 250.0, true));

        } catch (Exception ignored) {
            // Se qualquer exceção/erro ocorrer na carga inicial (ex: validação de CPF ou veículo duplicado),
            // ela é capturada e ignorada de propósito para não interromper a inicialização do sistema.
        }

        // Variável de controle do menu. Inicializada com -1 para garantir que entre no loop 'while'.
        int opcao = -1;

        // Loop de repetição (laço principal do sistema).
        // Continua executando enquanto o usuário não escolher a opção '0' (Sair).
        while (opcao != 0) {

            // Exibe o menu na tela, lê a entrada do teclado e retorna o número escolhido.
            opcao = sistema.menuPrincipal();

            // Switch Expression (sintaxe moderna com '->' introduzida no Java 12+):
            // Avalia o valor de 'opcao' e executa o bloco correspondente.
            // Não necessita do comando 'break' explicitamente no final de cada caso.
            switch (opcao) {
                case 1 -> sistema.cliente();             // Redireciona para as opções do cliente
                case 2 -> sistema.operador();            // Redireciona para as opções do operador
                case 3 -> sistema.gestor();              // Redireciona para as opções do gestor
                case 4 -> sistema.testarResiliencia();   // Executa testes de estresse/falha no sistema
                case 0 -> System.out.println("Saindo do sistema Locadora Rota Segura...");
                default -> System.out.println("Opção inválida!"); // Caso digite um número fora do menu
            }
        }
    }
}