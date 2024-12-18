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
        String usersFile = "users.txt";

        while (true) {
            System.out.println("1 - Login");
            System.out.println("2 - Exit");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    login(scanner, usersFile);
                    break;
                case 2:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static AtomicInteger counterOrders = new AtomicInteger(0);
    private static int generateOrderNumber() {
        return counterOrders.incrementAndGet();
    }

    private static List<Product> products = new ArrayList<>();
    private static List<ShoppingCart> cart = new ArrayList<>();
    private static List<Order> order = new ArrayList<>();
    // private static List<User> usuarios = new ArrayList<>();

    private static void registerUser(Scanner scanner, String usersFile) {
        Console console = System.console();
        if (console == null) {
            System.err.println("Não é possível obter o console.");
            System.exit(1);
        }

        System.out.print("Digite seu email: ");
        scanner.nextLine();
        String email = scanner.nextLine();

        System.out.print("Digite sua senha: ");
        char[] passwordChar = console.readPassword();
        String password = new String(passwordChar);
        Arrays.fill(passwordChar, '\u0000');

        System.out.print("É administrador? (s/n): ");
        String isAdministrador = scanner.nextLine().toLowerCase();

        try (FileWriter fw = new FileWriter(usersFile, true);
                BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(email + ":" + password + ":" + isAdministrador + "\n");
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    private static void login(Scanner scanner, String usersFile) {
        Console console = System.console();
        if (console == null) {
            System.err.println("Não é possível obter o console.");
            System.exit(1);
        }

        System.out.print("Digite seu email: ");
        scanner.nextLine();
        String email = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        char[] passwordChar = console.readPassword();
        String password = new String(passwordChar);

        // Limpar o array de senha
        Arrays.fill(passwordChar, '\u0000');

        try (BufferedReader br = new BufferedReader(new FileReader(usersFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(":");
                if (data[0].equals(email) && data[1].equals(password)) {
                    System.out.println("Login realizado com sucesso!");
                    String userType = data[2];
                    if (userType.equals("s")) {
                        Administrator adm = new Administrator(email, email, password);
                        menuAdministrator(scanner, adm, usersFile);
                    } else {
                        menuUser(scanner);
                    }
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao realizar login: " + e.getMessage());
        }

        System.out.println("Email ou senha inválidos.");
    }

    private static void menuAdministrator(Scanner scanner, Administrator administrador, String usersFile) {
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
                    String name = scanner.nextLine();

                    System.out.print("Digite a categoria do produto: ");
                    String category = scanner.nextLine();

                    System.out.print("Digite a descrição do produto: ");
                    String description = scanner.nextLine();

                    System.out.print("Digite o preço do produto: ");
                    double price = Double.parseDouble(scanner.nextLine());

                    System.out.print("Digite a quantidade em estoque do produto: ");
                    int quantityStock = Integer.parseInt(scanner.nextLine());

                    Product product = new Product(name, description, price, quantityStock, category);

                    administrador.createProduct(products, product);

                    System.out.println(product.toString());

                    break;
                case 2:
                    registerUser(scanner, usersFile);
                    break;
                case 3:
                    SortOrdersByPrice();
                    order.get(0).toString();
                    break;
                case 4:
                    SortProductsByStock();
                    products.get(0).toString();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuUser(Scanner scanner) {
        ShoppingCart cart = new ShoppingCart();

        while (true) {
            System.out.println("Menu do Usuário");
            System.out.println("1 - Start new order");
            System.out.println("2 - Exit");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    StarNewOrder(scanner, cart);
                case 2:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void StarNewOrder(Scanner scanner, ShoppingCart cart) {
        while (true) {
            Client client = new Client();
            System.out.println("1 - Add product");
            System.out.println("2 - View shopping cart");
            System.out.println("3 - Finish order");
            System.out.println("4 - Exit");
            int optionProduct = scanner.nextInt();

            switch (optionProduct) {
                case 1:
                    System.out.print("Digite o id do produto: ");
                    int idProduct = scanner.nextInt();
                    Product productFind = null;

                    for (int i = 0; i < products.size(); i++) {
                        if (products.get(i).getId() == idProduct) {
                            productFind = products.get(i);
                            break;
                        }
                    }

                    if (productFind != null) {
                        cart.addProduct(productFind);
                    } else {
                        System.out.println("Produto não encontrado.");
                    }

                    break;
                case 2:
                    cart.ViewCart();
                    break;
                case 3:
                    if (client == null || cart.getProduct().isEmpty()) {
                        System.out.println("Não é possível finalizar o pedido. Verifique os dados do client e o carrinho.");
                        return;
                    }
                    LocalDate data = LocalDate.now();
                    double total = cart.calculateTotal();
                    List<OrderItem> itensPedido = new ArrayList<>();
                    for (Product product : cart.getProduct()) {
                        OrderItem item = new OrderItem(product, 1);
                        itensPedido.add(item);
                    }

                    // Gerar número do pedido (implemente sua lógica aqui)
                    int orderNumber = generateOrderNumber();

                    // Criar o objeto Pedido
                    Order order = new Order(orderNumber, data, itensPedido, total);

                    // Adicionar o pedido ao histórico do client
                    client.addOrder(order);

                    // Atualizar o estoque
                    for (Product product : cart.getProduct()) {
                        product.setQuantityStock(product.getQuantityStock() - 1);
                    }

                    // Limpar o cart
                    cart.getProduct().clear();

                    System.out.println("Pedido finalizado com sucesso! Número do pedido: " + orderNumber);
                    break;
                case 4:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void SortOrdersByPrice() {
        Collections.sort(order, new Comparator<Order>() {
            @Override
            public int compare(Order p1, Order p2) {
                return Double.compare(p1.getTotalPedido(), p2.getTotalPedido());
            }
        });
    }

    private static void SortProductsByStock() {
        Collections.sort(products, new Comparator<Product>() {
            @Override
            public int compare(Product p1, Product p2) {
                return Double.compare(p1.getQuantityStock(), p2.getQuantityStock());
            }
        });
    }

}