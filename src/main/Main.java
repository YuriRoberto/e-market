import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

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

    private static List<Product> produtos = new ArrayList<>();
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
                    String nome = scanner.nextLine();
                
                    System.out.print("Digite a descrição do produto: ");
                    String descricao = scanner.nextLine();
                    
                    System.out.print("Digite o preço do produto: ");
                    double preco = Double.parseDouble(scanner.nextLine());
                    
                    System.out.print("Digite a quantidade em estoque do produto: ");
                    int quantidadeEstoque = Integer.parseInt(scanner.nextLine());

                    Product product = new Product(nome, descricao, preco, quantidadeEstoque);

                    administrador.criarProduto(produtos, product);

                    System.out.println("Nome: " + product.getNome());
                    System.out.println("Descrição: " + product.getDescricao());
                    System.out.println("Preço: " + product.getPreco());
                    System.out.println("Quantidade em estoque: " + product.getQuantidadeEstoque());

                    break;
                case 2:
                    // administrador.criarUsuario(scanner, usuarios);
                    break;
                case 3:
                    // relatorioPedidoMaisCaro();
                    break;
                case 4:
                    // relatorioProdutoMenorEstoque();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuUsuario(Scanner scanner) {
        // Carrinho carrinho = new Carrinho();

        while (true) {
            System.out.println("Menu do Usuário");
            System.out.println("1 - Start new order");
            System.out.println("2 - Exit");
            int opcao = scanner.nextInt();

            switch (opcao) {
                case 1:
                    while (true) {
                        System.out.println("1 - Add product");
                        System.out.println("2 - View shopping cart");
                        System.out.println("3 - Finish order");
                        System.out.println("4 - Exit");
                        int opcaoProduto = scanner.nextInt();

                        switch (opcaoProduto) {
                            case 1:
                                // Lógica para adicionar um produto ao carrinho
                                break;
                            case 2:
                                // Lógica para visualizar o carrinho
                                break;
                            case 3:
                                // Lógica para finalizar a compra
                                break;
                            case 4:
                                return;
                            default:
                                System.out.println("Opção inválida.");
                        }
                    }
                case 2:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

}