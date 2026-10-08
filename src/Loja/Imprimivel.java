// Define o pacote ao qual a interface pertence.
package Loja;

/**
 * INTERFACE IMPRIMIVEL
 *
 * Uma Interface em Java funciona como um "contrato de comportamento".
 * Ela define QUAIS métodos as classes devem ter, mas NÃO COMO esses métodos
 * devem ser executados (sem implementação de corpo).
 *
 * Qualquer classe que implementar esta interface (como a classe 'Contrato')
 * assume a responsabilidade obrigatória de fornecer o código concreto para estes métodos.
 *
 * Principais Vantagens no Projeto:
 * 1. Padronização: Garante que diferentes partes do sistema (contratos, recibos, relatórios)
 *    possuam uma forma uniforme de gerar e exibir dados.
 * 2. Polimorfismo/Desacoplamento: Permite criar rotinas de impressão genéricas.
 *    Exemplo: É possível criar um método 'imprimirRelatorio(Imprimivel item)' que aceite
 *    QUALQUER classe que implemente 'Imprimivel', sem precisar saber a classe exata do objeto.
 */
public interface Imprimivel {

    /**
     * Assinatura de método para montar a representação textual do documento.
     *
     * Em interfaces Java, os métodos são implicitamente 'public' e 'abstract',
     * portanto não é necessário escrever 'public abstract String gerarComprovante();'.
     *
     * @return String contendo o texto formatado do comprovante, contrato ou relatório.
     */
    String gerarComprovante();

    /**
     * Assinatura de método para emitir/exibir o documento.
     *
     * Responsável por enviar o resultado de 'gerarComprovante()' para o canal de saída desejado
     * (por exemplo, imprimir diretamente no console via System.out.println, salvar em arquivo, etc.).
     */
    void imprimir();
}