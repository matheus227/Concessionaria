// Define o pacote Principal, que gerencia a infraestrutura da aplicação.
package Principal;

// Importação das classes de domínio.
import Loja.Contrato;

// Importações do pacote java.io (Input/Output), responsável por operações de leitura e gravação em disco.
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

// Importação da interface List para trabalhar com coleções genéricas (ex: LinkedList, ArrayList).
import java.util.List;

/**
 * CLASSE PERSISTENCIA (I/O DE DADOS EM DISCO)
 *
 * Atua como uma classe Utilitária (Utility Class).
 * Seu objetivo é "persistir" (salvar permanentemente) os dados que estão
 * na memória RAM (volátil) para o disco rígido (HD/SSD) em um arquivo de texto (.txt).
 */
public class Persistencia {

    // --- CONSTANTE ESTÁTICA ---
    // 'static': Pertence à classe (não exige instanciar 'new Persistencia()').
    // 'final': Transforma a variável em uma Constante (seu valor nunca pode ser alterado).
    // Por convenção no Java, constantes são escritas em letras MAIÚSCULAS separadas por UNDERLINE (SNAKE_CASE).
    private static final String ARQUIVO_HISTORICO = "historico_locacoes.txt";

    /**
     * Salva a lista completa de contratos no arquivo de texto.
     * Como é um método estático, é chamado diretamente via 'Persistencia.salvarHistorico(...)'.
     *
     * @param contratos Lista contendo todos os contratos registrados no sistema.
     */
    public static void salvarHistorico(List<Contrato> contratos) {

        // --- TRY-WITH-RESOURCES (Try com Recursos) ---
        // Adicionado no Java 7. Ao instanciar os objetos de I/O dentro dos parênteses do 'try',
        // o Java se encarrega de fechar automaticamente (chamar o método .close()) o 'writer'
        // no final do bloco, mesmo que ocorra um erro. Isso evita vazamento de memória e arquivos corrompidos/presos.
        try (
                // FileWriter: Abre a conexão com o arquivo em disco.
                // O parâmetro 'false' indica modo "Sobrescrita" (Overwrite).
                // (Se fosse 'true', ele adicionaria os dados no final do arquivo existente - Append mode).
                FileWriter fw = new FileWriter(ARQUIVO_HISTORICO, false);

                // BufferedWriter: Funciona como uma "sala de espera" na memória (Buffer).
                // Em vez de gravar letra por letra direto no HD (o que é muito lento),
                // ele junta blocos grandes de texto e grava de uma vez, otimizando muito a performance.
                BufferedWriter writer = new BufferedWriter(fw)
        ) {

            // Grava o cabeçalho no arquivo.
            // O '\n' representa uma quebra de linha.
            writer.write("====== RELATÓRIO OFICIAL DE CONTRATOS - ROTA SEGURA ======\n\n");

            // Laço For-Each: Percorre a lista de contratos recebida por parâmetro.
            for (Contrato c : contratos) {
                // Polimorfismo: Chama o método gerarComprovante() que foi obrigado pela interface Imprimivel.
                writer.write(c.gerarComprovante());

                // Pula uma linha entre um contrato e outro para facilitar a leitura no bloco de notas.
                writer.write("\n");
            }

            // Feedback visual no terminal confirmando que a operação de gravação funcionou.
            System.out.println("[Persistência] Histórico salvo em '" + ARQUIVO_HISTORICO + "' com sucesso!");

        } catch (IOException e) {
            // Checked Exception: O Java obriga a capturar IOException sempre que manipulamos arquivos,
            // pois o disco pode estar cheio, sem permissão de administrador ou o arquivo bloqueado por outro app.
            System.err.println("[Erro I/O] Falha ao salvar arquivo de histórico: " + e.getMessage());
        }
    }
}