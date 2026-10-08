// Define o pacote ao qual a classe pertence.
// Organiza a camada principal/infraestrutura do sistema.
package Principal;

import java.io.Serial;

/**
 * EXCEÇÕES CUSTOMIZADAS DO SISTEMA
 * <p>
 * Esta classe atua como um agrupador/contêiner de Exceções Customizadas (Custom Exceptions).
 * <p>
 * Por estenderem diretamente a classe 'Exception', essas exceções são do tipo
 * "Checked Exceptions" (Exceções Checadas). O compilador do Java exige que elas sejam
 * explicitamente tratadas com blocos 'try-catch' ou declaradas na assinatura dos métodos com 'throws'.
 * <p>
 * Vantagem no projeto:
 * Permite identificar a causa exata de uma falha de negócio (ex: tentar alugar um veículo já ocupado)
 * de forma muito mais específica do que lançar uma 'Exception' ou 'RuntimeException' genérica.
 */
public class Excecoes {

    /**
     * Exceção lançada quando uma operação tenta alugar ou reservar um veículo que já está ocupado/indisponível.
     * <p>
     * Uso do 'static class':
     * Permite que esta subclasse de exceção seja instanciada diretamente sem precisar criar
     * um objeto da classe externa 'Excecoes' (ex: 'throw new Excecoes.VeiculoIndisponivelException(...)').
     */
    public static class VeiculoIndisponivelException extends Exception {

        // serialVersionUID:
        // Identificador único de versão exigido para classes que implementam a interface 'Serializable' (como Exception).
        // Garante a compatibilidade durante o processo de serialização/deserialização do objeto na rede ou em disco.
        @Serial
        private static final long serialVersionUID = 1L;

        /**
         * Construtor da exceção customizada.
         *
         * @param msg Mensagem descritiva do erro.
         */
        public VeiculoIndisponivelException(String msg) {
            // 'super(msg)': Repassa a mensagem de erro para o construtor da superclasse ('Exception').
            // Essa mensagem fica acessível posteriormente através do método '.getMessage()'.
            super(msg);
        }
    }

    /**
     * Exceção lançada quando ocorrem erros relacionados a datas e prazos
     * (ex: tentar contratar uma quantidade negativa ou zerada de dias, datas de devolução inconsistentes, etc.).
     */
    public static class DataInvalidaException extends Exception {

        private static final long serialVersionUID = 1L;

        public DataInvalidaException(String msg) {
            super(msg);
        }
    }

    /**
     * Exceção genérica para falhas de validação de dados de entrada do sistema
     * (ex: formatos inválidos, campos obrigatórios não preenchidos ou falhas de regras cadastrais).
     */
    public static class ValidacaoException extends Exception {

        private static final long serialVersionUID = 1L;

        public ValidacaoException(String msg) {
            super(msg);
        }
    }
}