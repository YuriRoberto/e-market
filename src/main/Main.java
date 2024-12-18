import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String arquivoUsuarios = "usuarios.txt"; // Nome do arquivo para armazenar os usuários

        while (true) {
            System.out.println("1 - Login");
            System.out.println("2 - Cadastro");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    login(scanner, arquivoUsuarios);
                    break;
                case 2:
                    cadastrarUsuario(scanner, arquivoUsuarios);
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static AtomicInteger contadorPedidos = new AtomicInteger(0);
    private static int gerarNumeroPedido() {
        return contadorPedidos.incrementAndGet();
    }

    private static List<Product> produtos = new ArrayList<>();
    private static List<ShoppingCart> carrinho = new ArrayList<>();
    private static List<Order> pedidos = new ArrayList<>();
    // private static List<User> usuarios = new ArrayList<>();

    private static void cadastrarUsuario(Scanner scanner, String arquivoUsuarios) {
        Console console = System.console();
        if (console == null) {
            System.err.println("Não é possível obter o console.");
            System.exit(1);
        }

        System.out.print("Digite seu email: ");
        scanner.nextLine();
        String email = scanner.nextLine();

        System.out.print("Digite sua senha: ");
        char[] senhaChar = console.readPassword();
        String senha = new String(senhaChar);
        Arrays.fill(senhaChar, '\u0000');

        System.out.print("É administrador? (s/n): ");
        String isAdministrador = scanner.nextLine().toLowerCase();

        try (FileWriter fw = new FileWriter(arquivoUsuarios, true);
                BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(email + ":" + senha + ":" + isAdministrador + "\n");
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    private static void login(Scanner scanner, String arquivoUsuarios) {
        Console console = System.console();
        if (console == null) {
            System.err.println("Não é possível obter o console.");
            System.exit(1);
        }

        System.out.print("Digite seu email: ");
        scanner.nextLine();
        String email = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        char[] senhaChar = console.readPassword();
        String senha = new String(senhaChar);

        // Limpar o array de senha
        Arrays.fill(senhaChar, '\u0000');

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoUsuarios))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(":");
                if (dados[0].equals(email) && dados[1].equals(senha)) {
                    System.out.println("Login realizado com sucesso!");
                    String tipoUsuario = dados[2];
                    if (tipoUsuario.equals("s")) {
                        Administrator adm = new Administrator(email, email, senha);
                        menuAdministrador(scanner, adm);
                    } else {
                        menuUsuario(scanner);
                    }
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao realizar login: " + e.getMessage());
        }

        System.out.println("Email ou senha inválidos.");
    }

    private static void menuAdministrador(Scanner scanner, Administrator administrador) {
        while (true) {
            System.out.println("Menu do Administrador");
            System.out.println("1 - Create new product");
            System.out.println("2 - Create new user");
            System.out.println("3 - Report - more expensive order");
            System.out.println("4 - Report - product with lowest inventory");
            System.out.println("5 - Exit");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    System.out.print("Digite o nome do produto: ");
                    scanner.nextLine();
                    String nome = scanner.nextLine();

                    System.out.print("Digite a categoria do produto: ");
                    String categoria = scanner.nextLine();

                    System.out.print("Digite a descrição do produto: ");
                    String descricao = scanner.nextLine();

                    System.out.print("Digite o preço do produto: ");
                    double preco = Double.parseDouble(scanner.nextLine());

                    System.out.print("Digite a quantidade em estoque do produto: ");
                    int quantidadeEstoque = Integer.parseInt(scanner.nextLine());

                    Product product = new Product(nome, descricao, preco, quantidadeEstoque, categoria);

                    administrador.criarProduto(produtos, product);

                    System.out.println(product.toString());

                    break;
                case 2:
                    // administrador.criarUsuario(scanner, usuarios);
                    break;
                case 3:
                    OrdenarPedidosPorPreco();
                    pedidos.get(0).toString();
                    break;
                case 4:
                    OrdenarProdutosPorEstoque();
                    produtos.get(0).toString();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuUsuario(Scanner scanner) {
        ShoppingCart carrinho = new ShoppingCart();

        while (true) {
            System.out.println("Menu do Usuário");
            System.out.println("1 - Start new order");
            System.out.println("2 - Exit");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    StarNewOrder(scanner, carrinho);
                case 2:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void StarNewOrder(Scanner scanner, ShoppingCart carrinho) {
        while (true) {
            Client cliente = new Client();
            System.out.println("1 - Add product");
            System.out.println("2 - View shopping cart");
            System.out.println("3 - Finish order");
            System.out.println("4 - Exit");
            int opcaoProduto = scanner.nextInt();

            switch (opcaoProduto) {
                case 1:
                    System.out.print("Digite o id do produto: ");
                    int idProduto = scanner.nextInt();
                    Product produtoEncontrado = null;

                    for (int i = 0; i < produtos.size(); i++) {
                        if (produtos.get(i).getId() == idProduto) {
                            produtoEncontrado = produtos.get(i);
                            break;
                        }
                    }

                    if (produtoEncontrado != null) {
                        carrinho.adicionarProduto(produtoEncontrado);
                    } else {
                        System.out.println("Produto não encontrado.");
                    }

                    break;
                case 2:
                    carrinho.VisualizarCarrinho();
                    break;
                case 3:
                    if (cliente == null || carrinho.getProdutos().isEmpty()) {
                        System.out.println("Não é possível finalizar o pedido. Verifique os dados do cliente e o carrinho.");
                        return;
                    }
                    // int numeroPedido = gerarNumeroPedido();
                    LocalDate data = LocalDate.now();

                    // Calcular o total
                    double total = carrinho.calcularTotal();

                    // Criar o objeto Pedido
                    // Criar uma nova lista de OrderItem
                    List<OrderItem> itensPedido = new ArrayList<>();
                    for (Product produto : carrinho.getProdutos()) {
                        OrderItem item = new OrderItem(produto, 1); // Cria um novo OrderItem com quantidade 1
                        itensPedido.add(item);
                    }

                    // Gerar número do pedido (implemente sua lógica aqui)
                    int numeroPedido = gerarNumeroPedido();

                    // Criar o objeto Pedido
                    Order pedido = new Order(numeroPedido, data, itensPedido, total);

                    // Adicionar o pedido ao histórico do cliente
                    cliente.adicionarPedido(pedido);

                    // Atualizar o estoque
                    for (Product produto : carrinho.getProdutos()) {
                        produto.setQuantidadeEstoque(produto.getQuantidadeEstoque() - 1);
                    }

                    // Limpar o carrinho
                    carrinho.getProdutos().clear();

                    System.out.println("Pedido finalizado com sucesso! Número do pedido: " + numeroPedido);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void OrdenarPedidosPorPreco() {
        Collections.sort(pedidos, new Comparator<Order>() {
            @Override
            public int compare(Order p1, Order p2) {
                return Double.compare(p1.getTotalPedido(), p2.getTotalPedido());
            }
        });
    }

    private static void OrdenarProdutosPorEstoque() {
        Collections.sort(produtos, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Double.compare(p1.getQuantidadeEstoque(), p2.getQuantidadeEstoque());
            }
        });
    }

}