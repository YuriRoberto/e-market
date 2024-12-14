import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    // public static void main(String[] args) {
    //     // ... código para carregar usuários do banco de dados ou de um arquivo

    //     Scanner scanner = new Scanner(System.in);
    //     System.out.print("Digite seu email: ");
    //     String email = scanner.nextLine();
    //     System.out.print("Digite sua senha: ");
    //     String password = scanner.nextLine();

    //     System.out.print(email);
    //     System.out.print(password);

    //     // User user = authenticateUser(email, password); // método para autenticar o usuário

    //     // if (user instanceof Customer) {
    //     //     // mostrar menu para cliente
    //     //     customerMenu((Customer) user);
    //     // } else if (user instanceof Administrator) {
    //     //     // mostrar menu para administrador
    //     //     adminMenu((Administrator) user);
    //     // } else {
    //     //     System.out.println("Usuário não encontrado.");
    //     // }
    // }

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

    private static void cadastrarUsuario(Scanner scanner, String arquivoUsuarios) {
        System.out.print("Digite seu email: ");
        scanner.nextLine();
        String email = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        try (FileWriter fw = new FileWriter(arquivoUsuarios, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(email + ":" + senha + "\n");
            System.out.println("Usuário cadastrado com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao cadastrar usuário: " + e.getMessage());
        }
    }

    private static void login(Scanner scanner, String arquivoUsuarios) {
        System.out.print("Digite seu email: ");
        scanner.nextLine();
        String email = scanner.nextLine();
        System.out.print("Digite sua senha: ");
        String senha = scanner.nextLine();

        try (BufferedReader br = new BufferedReader(new FileReader(arquivoUsuarios))) {
            String linha;
            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(":");
                if (dados[0].equals(email) && dados[1].equals(senha)) {
                    System.out.println("Login realizado com sucesso!");
                    return;
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao realizar login: " + e.getMessage());
        }

        System.out.println("Email ou senha inválidos.");
    }

}