import java.io.*;
import java.util.*;

// --- CLASSE PRODUTO ---
class Produto {
    int id;
    String nome;
    double preco;
    int quantidade;

    public Produto(int id, String nome, double preco, int quantidade) {
        this.id = id;
        this.nome = nome;
        this.preco = preco;
        this.quantidade = quantidade;
    }

    // ESSENCIAL: Permite que o HashMap identifique o produto pelo ID
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Produto produto = (Produto) o;
        return id == produto.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("%d | %-15s | R$ %8.2f | Estoque: %d", id, nome, preco, quantidade);
    }

    public String paraLinhaArquivo() {
        return id + ";" + nome + ";" + preco + ";" + quantidade;
    }
}

// --- CLASSE PRINCIPAL ---
public class SistemaVendas {
    private static final String CAMINHO_ARQUIVO = "produtos.txt";
    private static List<Produto> estoque = new ArrayList<>();
    private static Map<Produto, Integer> carrinho = new HashMap<>();

    public static void main(String[] args) {
        carregarEstoque();
        Scanner scanner = new Scanner(System.in);
        int opcao = 0;

        while (opcao != 4) {
            System.out.println("\n--- SIMULADOR DE VENDAS ---");
            System.out.println("1. Ver Estoque");
            System.out.println("2. Adicionar ao Carrinho");
            System.out.println("3. Finalizar Venda");
            System.out.println("4. Sair (Cancelar operação)");
            System.out.print("Escolha: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); // Limpa o buffer do \n
            } catch (InputMismatchException e) {
                System.out.println("Erro: Digite apenas números.");
                scanner.nextLine();
                continue;
            }

            switch (opcao) {
                case 1 -> exibirEstoque();
                case 2 -> adicionarAoCarrinho(scanner);
                case 3 -> finalizarVenda();
                case 4 -> System.out.println("Saindo... O estoque original foi mantido.");
                default -> System.out.println("Opção inválida!");
            }
        }
    }

    private static void carregarEstoque() {
        estoque.clear();
        File arquivo = new File(CAMINHO_ARQUIVO);

        // Se o arquivo não existe, vamos criá-lo com dados iniciais para teste
        if (!arquivo.exists() || arquivo.length() == 0) {
            System.out.println("Aviso: Arquivo inexistente ou vazio. Criando produtos padrão...");
            inicializarArquivoPadrao();
        }

        try (BufferedReader br = new BufferedReader(new FileReader(CAMINHO_ARQUIVO))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                if (linha.trim().isEmpty()) continue;

                String[] dados = linha.split(";");
                if (dados.length == 4) {
                    int id = Integer.parseInt(dados[0].trim());
                    String nome = dados[1].trim();
                    double preco = Double.parseDouble(dados[2].trim());
                    int qtd = Integer.parseInt(dados[3].trim());
                    estoque.add(new Produto(id, nome, preco, qtd));
                }
            }
            System.out.println("Carga concluída! Itens no estoque: " + estoque.size());
        } catch (IOException | NumberFormatException e) {
            System.out.println("Erro ao processar o arquivo: " + e.getMessage());
        }
    }

    // Método auxiliar para criar o arquivo caso ele não exista
    private static void inicializarArquivoPadrao() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            bw.write("1;Camiseta;59.90;10"); bw.newLine();
            bw.write("2;Calca Jeans;120.00;5"); bw.newLine();
            bw.write("3;Tenis Esportivo;250.00;3"); bw.newLine();
            System.out.println("Arquivo 'produtos.txt' gerado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao criar arquivo padrão: " + e.getMessage());
        }
    }

    private static void exibirEstoque() {
        System.out.println("\nID | NOME            | PREÇO       | QTD");
        System.out.println("------------------------------------------");
        for (Produto p : estoque) System.out.println(p);
    }

    private static void adicionarAoCarrinho(Scanner sc) {
        exibirEstoque();
        System.out.print("Digite o ID do produto: ");
        int id = sc.nextInt();
        System.out.print("Quantidade desejada: ");
        int qtd = sc.nextInt();
        sc.nextLine(); // Limpa buffer

        for (Produto p : estoque) {
            if (p.id == id) {
                if (p.quantidade >= qtd) {
                    // Se o produto já estiver no carrinho, soma a quantidade
                    carrinho.put(p, carrinho.getOrDefault(p, 0) + qtd);
                    p.quantidade -= qtd; // Reserva temporária na memória
                    System.out.println("OK: " + qtd + " unidade(s) de " + p.nome + " no carrinho.");
                } else {
                    System.out.println("Erro: Estoque insuficiente (Apenas " + p.quantidade + " disponíveis).");
                }
                return;
            }
        }
        System.out.println("Erro: Produto com ID " + id + " não encontrado.");
    }

    private static void finalizarVenda() {
        if (carrinho.isEmpty()) {
            System.out.println("Carrinho vazio! Adicione itens antes de finalizar.");
            return;
        }

        double totalGeral = 0;
        System.out.println("\n========= NOTA FISCAL =========");
        for (var item : carrinho.entrySet()) {
            Produto p = item.getKey();
            int qtdComprada = item.getValue();
            double subtotal = p.preco * qtdComprada;
            totalGeral += subtotal;
            System.out.printf("%-15s x %d = R$ %.2f\n", p.nome, qtdComprada, subtotal);
        }
        System.out.println("-------------------------------");
        System.out.printf("TOTAL DA VENDA: R$ %.2f\n", totalGeral);
        System.out.println("===============================");

        salvarEstoqueNoArquivo();
        carrinho.clear(); // Limpa o carrinho para a próxima venda
        System.out.println("Venda gravada com sucesso no arquivo!");
    }

    private static void salvarEstoqueNoArquivo() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(CAMINHO_ARQUIVO))) {
            for (Produto p : estoque) {
                bw.write(p.paraLinhaArquivo());
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Erro crítico ao salvar estoque: " + e.getMessage());
        }
    }
}