// Define o pacote principal do sistema.
package Principal;

// Importação de bibliotecas nativas do Java.
// LinkedList é uma estrutura de dados de lista duplamente encadeada.
// Scanner é a classe responsável por ler as entradas do teclado.
import java.util.LinkedList;
import java.util.Scanner;

// Importações das classes de domínio (pacote Loja) e exceções customizadas.
import Loja.Cliente;
import Loja.Contrato;
import Loja.Popular;
import Loja.SUV;
import Loja.Sedan;
import Loja.Veiculo;
import Principal.Excecoes.*;

/**
 * CLASSE ABSTRATA INTERFACE
 *
 * Centraliza a interação com o usuário (Menus), a manutenção das coleções de dados (Listas)
 * e o tratamento das Exceções do sistema.
 *
 * É 'abstract' porque não serve para ser instanciada diretamente (ninguém faz 'new Interface()'),
 * mas sim para ser herdada pela classe 'Main', que executará o fluxo.
 */
public abstract class Interface {

    // Instancia o Scanner escutando a entrada padrão do sistema (teclado).
    Scanner sc = new Scanner(System.in);

    // COLEÇÕES GENÉRICAS (Generics)
    // LinkedList (Lista Encadeada) armazena os dados em memória.
    // É eficiente para adicionar ou remover itens frequentemente.
    LinkedList<Veiculo> frota = new LinkedList<>();
    LinkedList<Contrato> contratos = new LinkedList<>();

    // Variável global para controlar a navegação nos menus.
    int optAtual = -1;

    /**
     * Exibe o painel principal e captura a escolha do perfil.
     * @return O número inteiro da opção escolhida.
     */
    public int menuPrincipal() {
        System.out.println("======== LOCADORA ROTA RELAMPAGO MATT =========");
        System.out.println("| Eu sou:                             |");
        System.out.println("|     1 - Cliente                     |");
        System.out.println("|     2 - Operador                    |");
        System.out.println("|     3 - Gestor                      |");
        System.out.println("|     4 - Demonstração de Resiliência |");
        System.out.println("|     0 - Sair                        |");
        System.out.println("=======================================");

        try {
            // Boas práticas com Scanner: Usar nextLine() e converter para Integer.
            // Isso evita o famoso "bug" do nextInt() que deixa o caractere "Enter" no buffer da memória.
            optAtual = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException ne) {
            // Captura o erro se o usuário digitar letras em vez de números.
            // System.err imprime a mensagem no canal de erro (geralmente em vermelho no console).
            System.err.println("Digite apenas números inteiros.");
            optAtual = -1;
        }
        return optAtual;
    }

    /**
     * Submenu do perfil CLIENTE.
     * Focado em cadastro, busca e operações de locação/devolução.
     */
    public void cliente() {
        System.out.println("===== Área do Cliente =====");
        System.out.println("1 - Novo Cadastro");
        System.out.println("2 - Buscar por CPF");
        System.out.println("3 - Abrir Locação (Gerar Contrato)");
        System.out.println("4 - Fazer Devolução");
        System.out.println("5 - Voltar");
        System.out.println("0 - Sair");

        int opcao;
        try {
            opcao = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException ne) {
            System.err.println("Digite apenas números inteiros.");
            return; // Interrompe o método e volta ao menu principal
        }

        if (opcao == 1) {
            System.out.println("===== Cadastro de Cliente =====");
            try {
                System.out.print("Nome: ");
                String nome = sc.nextLine();
                System.out.print("CPF: ");
                long cpf = Long.parseLong(sc.nextLine());

                // O próprio construtor se encarrega de validar as regras e salvar o cliente no HashMap estático.
                new Cliente(nome, cpf);
                System.out.println("Cliente cadastrado com sucesso!");

            } catch (NumberFormatException e) {
                System.err.println("Erro: CPF deve conter apenas números.");
            } catch (IllegalArgumentException e) {
                // Captura as validações (nome com números, CPF inválido, CPF duplicado)
                System.err.println("Erro de Validação: " + e.getMessage());
            }

        } else if (opcao == 2) {
            System.out.print("Informe seu CPF: ");
            try {
                long cpfBusca = Long.parseLong(sc.nextLine());

                // Aciona a busca rápida O(1) desenvolvida na classe Cliente.
                Cliente c = Cliente.buscarPorCPF(cpfBusca);
                if (c != null) {
                    System.out.println("\nCliente Localizado: " + c);
                } else {
                    System.out.println("Cliente não encontrado.");
                }
            } catch (NumberFormatException e) {
                System.err.println("Erro: Digite apenas números no CPF.");
            }

        } else if (opcao == 3) {
            System.out.println("===== Abertura de Locação =====");
            try {
                System.out.print("CPF do Cliente: ");
                long cpf = Long.parseLong(sc.nextLine());
                Cliente c = Cliente.buscarPorCPF(cpf);

                // Lança a exceção customizada caso o cliente não exista.
                if (c == null) throw new ValidacaoException("Cliente não cadastrado no sistema!");

                System.out.print("Placa do Veículo: ");
                String placa = sc.nextLine();

                // Busca linear na lista da frota
                Veiculo vEncontrado = null;
                for (Veiculo v : frota) {
                    if (v.getPlaca().equalsIgnoreCase(placa)) {
                        vEncontrado = v;
                        break; // Para o loop ao encontrar
                    }
                }

                // Disparo de Exceções Customizadas baseadas nas regras de negócio.
                if (vEncontrado == null) throw new VeiculoIndisponivelException("Veículo não encontrado na frota!");
                if (!vEncontrado.isDisponivel()) throw new VeiculoIndisponivelException("Veículo já se encontra ALUGADO!");

                System.out.print("Dias de locação: ");
                int dias = Integer.parseInt(sc.nextLine());
                if (dias <= 0) throw new DataInvalidaException("Quantidade de dias deve ser maior que zero!");

                // Se o código chegou até aqui sem cair em nenhum 'catch', gera o contrato com segurança.
                Contrato contrato = new Contrato(c, vEncontrado, dias);
                contratos.add(contrato);

                // Integração com sistema de arquivos (chamada para classe estática de persistência não detalhada aqui).
                Persistencia.salvarHistorico(contratos);

                System.out.println("\n--- COMPROVANTE EMITIDO COM SUCESSO ---");
                // Polimorfismo da interface 'Imprimivel'
                contrato.imprimir();

            } catch (NumberFormatException e) {
                System.err.println("Erro: Entrada numérica inválida!");
            } catch (Exception e) {
                // Catch genérico captura VeiculoIndisponivelException, ValidacaoException, etc.
                System.err.println("Falha na Locação: " + e.getMessage());
            }

        } else if (opcao == 4) {
            System.out.println("===== Devolução de Veículo =====");
            try {
                System.out.print("Código do Contrato: ");
                int id = Integer.parseInt(sc.nextLine());

                Contrato cEncontrado = null;
                for (Contrato c : contratos) {
                    if (c.getIdContrato() == id && c.isAtivo()) {
                        cEncontrado = c;
                        break;
                    }
                }

                if (cEncontrado == null) {
                    System.err.println("Contrato ativo não localizado!");
                } else {
                    // Executa a devolução, atualizando o status do carro para disponível
                    cEncontrado.encerrar();
                    Persistencia.salvarHistorico(contratos);
                    System.out.println("Devolução realizada e contrato encerrado!");
                }
            } catch (NumberFormatException e) {
                System.err.println("Erro: Código do contrato deve ser um número.");
            }

        } else if (opcao == 5) {
            optAtual = -1;
        } else if (opcao == 0) {
            optAtual = 0;
        }
    }

    /**
     * Submenu do perfil OPERADOR.
     * Responsável pelo gerenciamento e cadastro da frota (estoque).
     */
    public void operador() {
        System.out.println("===== Área do Operador =====");
        System.out.println("1 - Cadastrar Veículo");
        System.out.println("2 - Listar Frota Completa");
        System.out.println("3 - Voltar");
        System.out.println("0 - Sair");

        int opcao;
        try {
            opcao = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException ne) {
            return;
        }

        if (opcao == 1) {
            try {
                System.out.println("Categoria: 1- Popular | 2- Sedan | 3- SUV");
                int cat = Integer.parseInt(sc.nextLine());

                System.out.print("Placa: ");
                String placa = sc.nextLine();
                System.out.print("Marca: ");
                String marca = sc.nextLine();
                System.out.print("Modelo: ");
                String modelo = sc.nextLine();
                System.out.print("Ano: ");
                int ano = Integer.parseInt(sc.nextLine());
                System.out.print("Diária Base (R$): ");
                double base = Double.parseDouble(sc.nextLine());

                // SWITCH EXPRESSION (Sintaxe Java 14+):
                // Avalia a categoria e RETORNA (yield) o objeto específico para a variável 'v'.
                // Age como um padrão de design 'Factory' simples.
                Veiculo v = switch (cat) {
                    case 1 -> {
                        System.out.print("Tem Ar Condicionado? (true/false): ");
                        boolean ar = Boolean.parseBoolean(sc.nextLine());
                        yield new Popular(placa, marca, modelo, ano, base, ar); // yield retorna o valor no bloco switch
                    }
                    case 2 -> {
                        System.out.print("Capacidade Porta-malas (Litros): ");
                        double cap = Double.parseDouble(sc.nextLine());
                        yield new Sedan(placa, marca, modelo, ano, base, cap);
                    }
                    case 3 -> {
                        System.out.print("Tem Tração 4x4? (true/false): ");
                        boolean t4 = Boolean.parseBoolean(sc.nextLine());
                        yield new SUV(placa, marca, modelo, ano, base, t4);
                    }
                    default -> throw new IllegalArgumentException("Categoria inválida.");
                };

                frota.add(v);
                System.out.println("Veículo cadastrado na frota!");

            } catch (Exception e) {
                System.err.println("Erro ao cadastrar veículo: " + e.getMessage());
            }

        } else if (opcao == 2) {
            System.out.println("\n--- RELATÓRIO DA FROTA ---");
            for (Veiculo v : frota) {
                // Ao imprimir 'v', o Java chama automaticamente o toString() formatado criado na superclasse.
                System.out.println(v);

                // POLIMORFISMO: Cada veículo fará o cálculo usando as regras da sua subclasse real (Popular, Sedan, SUV).
                System.out.printf("   > Estimativa Diária (3 dias): R$ %.2f | Seguro: R$ %.2f | Manutenção: R$ %.2f\n",
                        v.calcularDiaria(3), v.calcularSeguro(3), v.calcularManutencao());
            }
            System.out.println("---------------------------\n");
        } else if (opcao == 3) {
            optAtual = -1;
        } else if (opcao == 0) {
            optAtual = 0;
        }
    }

    /**
     * Submenu do perfil GESTOR.
     * Visão de indicadores e relatórios de alto nível.
     */
    public void gestor() {
        System.out.println("===== Área do Gestor =====");
        System.out.println("1 - Total de Clientes Cadastrados");
        System.out.println("2 - Relatório de Veículos Alugados");
        System.out.println("3 - Relatório de Histórico de Contratos");
        System.out.println("4 - Voltar");
        System.out.println("0 - Sair");

        int opcao;
        try {
            opcao = Integer.parseInt(sc.nextLine());
        } catch (NumberFormatException ne) {
            return;
        }

        if (opcao == 1) {
            // Acesso a método estático: não precisa instanciar cliente para saber o total.
            System.out.println("Total de clientes: " + Cliente.getQuantidade());
        } else if (opcao == 2) {
            System.out.println("\n--- VEÍCULOS ALUGADOS NO MOMENTO ---");
            for (Veiculo v : frota) {
                if (!v.isDisponivel()) System.out.println(v);
            }
            System.out.println("------------------------------------\n");
        } else if (opcao == 3) {
            System.out.println("\n--- HISTÓRICO DE CONTRATOS REGISTRADOS ---");
            for (Contrato c : contratos) {
                c.imprimir();
            }
        } else if (opcao == 4) {
            optAtual = -1;
        } else if (opcao == 0) {
            optAtual = 0;
        }
    }

    /**
     * Método para demonstrar os tratamentos de falhas do negócio (Resiliência).
     * Força a criação de cenários de erro para testar se as exceções customizadas protegem o sistema.
     */
    public void testarResiliencia() {
        System.out.println("\n=== DEMONSTRAÇÃO PRÁTICA DE RESILIÊNCIA E TRATAMENTO DE ERROS ===");

        // Correção de sintaxe aplicada aqui (linhas unidas corretamente)
        System.out.println("\n1. Testando cadastro com nome inválido (contendo números):");
        try {
            new Cliente("Maria123", 11122233344L);
        } catch (IllegalArgumentException e) {
            System.out.println("   [Exceção Capturada]: " + e.getMessage());
        }

        System.out.println("\n2. Testando locação de veículo ocupado:");
        try {
            Cliente c = Cliente.buscarPorCPF(12345678901L); // CPF plantado previamente no main
            if (c != null && !frota.isEmpty()) {
                Veiculo v = frota.getFirst(); // Pega o primeiro veículo da frota
                v.setDisponivel(false);       // Força indisponibilidade artificialmente

                if (!v.isDisponivel()) {
                    throw new VeiculoIndisponivelException("Veículo " + v.getModelo() + " está indisponível para aluguel!");
                }
            }
        } catch (VeiculoIndisponivelException e) {
            System.out.println("   [Exceção Capturada]: " + e.getMessage());
        }

        System.out.println("\n3. Testando quantidade inválida de dias:");
        try {
            int dias = -5;
            if (dias <= 0) throw new DataInvalidaException("Dias de locação devem ser um número positivo!");
        } catch (DataInvalidaException e) {
            System.out.println("   [Exceção Capturada]: " + e.getMessage());
        }
        System.out.println("=================================================================\n");
    }
}