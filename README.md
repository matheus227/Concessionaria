# Concessionaria

**Disciplina:** Técnicas de Programação I (TP1)  
**Aluno:** Matheus Kenzo Cardoso Takahashi  
**Matrícula:** 1301392611027  
**IDE de Desenvolvimento:** Eclipse Java ID

---

# 🚗 Locadora Rota Segura

Sistema de locação de veículos em Java, executado via console. Permite cadastrar clientes e veículos, abrir e encerrar locações, emitir comprovantes e persistir o histórico de contratos em arquivo texto.

O projeto demonstra conceitos de Programação Orientada a Objetos: herança, polimorfismo, classes abstratas, interfaces, encapsulamento, exceções customizadas e I/O de arquivos.

## 📐 Arquitetura

O código é dividido em dois pacotes, cada um com uma responsabilidade:

| Pacote | Responsabilidade | Classes |
|---|---|---|
| `Loja` | Domínio (regras de negócio) | `Veiculo`, `Popular`, `Sedan`, `SUV`, `Cliente`, `Contrato`, `Imprimivel` |
| `Principal` | Aplicação / infraestrutura (menus, entrada do usuário, disco, erros) | `Main`, `Interface`, `Persistencia`, `Excecoes` |

### Principais decisões de projeto

- **Herança + polimorfismo:** `Veiculo` é abstrata e declara `calcularDiaria`, `calcularSeguro` e `calcularManutencao`. Cada categoria (`Popular`, `Sedan`, `SUV`) implementa sua própria regra, e `Contrato` calcula o valor total sem saber o tipo concreto do veículo.
- **Interface `Imprimivel`:** define `gerarComprovante()` e `imprimir()`. `Contrato` a implementa, e `Persistencia` grava os comprovantes sem depender de nada além da interface.
- **Repositório de clientes em memória:** `Cliente` mantém um `HashMap<Long, Cliente>` estático, com busca por CPF em O(1) e bloqueio de CPF duplicado.
- **Validação no construtor/setters:** os objetos nunca são criados em estado inválido (programação defensiva). Os setters lançam `IllegalArgumentException`.
- **Exceções de negócio:** `Excecoes` agrupa as *checked exceptions* `VeiculoIndisponivelException`, `DataInvalidaException` e `ValidacaoException`.
- **Camada de interface:** `Interface` (abstrata) concentra os menus e as coleções `frota` e `contratos` (`LinkedList`). `Main` a estende e executa o laço principal.
- **Persistência:** `Persistencia.salvarHistorico()` sobrescreve `historico_locacoes.txt` a cada nova locação ou devolução, usando *try-with-resources*.

## Diagrama De Classes

```text
                          «interface»
                         ┌─────────────────────┐
                         │     Imprimivel      │
                         ├─────────────────────┤
                         │ +gerarComprovante() │
                         │ +imprimir()         │
                         └──────────△──────────┘
                                    ┆ implements
                                    ┆
┌──────────────────────────┐        ┆        ┌──────────────────────────┐
│         Cliente          │        ┆        │         Contrato         │
├──────────────────────────┤        ┆        ├──────────────────────────┤
│ -nome: String            │   ┌────┴────────┤ -contadorId: int {static}│
│ -cpf: long               │◄──┤ contratante │ -idContrato: int         │
│ -quantidade: int {static}│   └─────────────┤ -cliente: Cliente        │
│ -bancoDeClientes: Map    │                 │ -veiculo: Veiculo        │
│   {static}               │                 │ -diasContratados: int    │
├──────────────────────────┤                 │ -valorTotal: double      │
│ +buscarPorCPF(cpf)       │                 │ -ativo: boolean          │
│   {static}               │                 ├──────────────────────────┤
└────────────△─────────────┘                 │ +encerrar()              │
             │                               │ +gerarComprovante()      │
             │ clienteResponsavel            │ +imprimir()              │
             │ (0..1)                        └────────────┬─────────────┘
             │                                            │ veiculo (1)
┌────────────┴─────────────┐                              │
│   «abstract»  Veiculo    │◄─────────────────────────────┘
├──────────────────────────┤
│ #placa: String           │
│ #marca: String           │
│ #modelo: String          │
│ #ano: int                │
│ #valorBaseDiaria: double │
│ #disponivel: boolean     │
│ #clienteResponsavel      │
├──────────────────────────┤
│ +calcularDiaria(dias)*   │      * = método abstrato
│ +calcularSeguro(dias)*   │
│ +calcularManutencao()*   │
└────────────△─────────────┘
             │ extends
   ┌─────────┼───────────────────┐
   │         │                   │
┌──┴────────────┐  ┌─────────────┴───┐  ┌──────────────┐
│    Popular    │  │      Sedan      │  │     SUV      │
├───────────────┤  ├─────────────────┤  ├──────────────┤
│ -arCondicio-  │  │ -capacidade-    │  │ -tracao4x4:  │
│  nado:boolean │  │  PortaMalas:    │  │  boolean     │
│               │  │  double         │  │              │
├───────────────┤  ├─────────────────┤  ├──────────────┤
│ +calcularDia- │  │ +calcularDia-   │  │ +calcular-   │
│  ria()        │  │  ria()          │  │  Diaria()    │
│ +calcularSe-  │  │ +calcularSe-    │  │ +calcular-   │
│  guro()       │  │  guro()         │  │  Seguro()    │
│ +calcularMa-  │  │ +calcularMa-    │  │ +calcular-   │
│  nutencao()   │  │  nutencao()     │  │  Manutencao()│
└───────────────┘  └─────────────────┘  └──────────────┘


════════════ Pacote Principal (aplicação) ════════════

┌───────────────────────────┐   extends   ┌─────────────────────┐
│ «abstract»  Interface     │◄────────────┤        Main         │
├───────────────────────────┤             ├─────────────────────┤
│ ~sc: Scanner              │             │ +main(args) {static}│
│ ~frota: LinkedList<Veic.> │             └─────────────────────┘
│ ~contratos: LinkedList<   │
│   Contrato>               │
├───────────────────────────┤
│ +menuPrincipal()          │
│ +cliente()                │
│ +operador()               │
│ +gestor()                 │
│ +testarResiliencia()      │
└─────┬──────────┬──────────┘
      │ usa      │ usa
      ▽          ▽
┌───────────────────────────┐   ┌────────────────────────────────┐
│       Persistencia        │   │            Excecoes            │
├───────────────────────────┤   ├────────────────────────────────┤
│ -ARQUIVO_HISTORICO        │   │ VeiculoIndisponivelException   │
│   {static final}          │   │ DataInvalidaException          │
├───────────────────────────┤   │ ValidacaoException             │
│ +salvarHistorico(         │   │ (todas estendem Exception)     │
│    List<Contrato>) {static}   └────────────────────────────────┘
└───────────────────────────┘

Interface também agrega (frota/contratos) → Veiculo e Contrato,
e usa Cliente para cadastro e busca por CPF.
Persistencia depende de Contrato (grava os comprovantes).


Legenda:  △ herança/implementação (┆ tracejado = interface)
          ◄ associação (seta aponta para a classe referenciada)
          # protected   - private   + public   ~ pacote
```

## Estrutura Das Pastas

```text
projeto/
├── Loja/
│   ├── Cliente.java
│   ├── Contrato.java
│   ├── Imprimivel.java
│   ├── Popular.java
│   ├── Sedan.java
│   ├── SUV.java
│   └── Veiculo.java
├── Principal/
│   ├── Excecoes.java
│   ├── Interface.java
│   ├── Main.java
│   └── Persistencia.java
└── README.md
```
