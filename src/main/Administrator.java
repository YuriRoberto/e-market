import java.util.List;
import java.util.Scanner;


public class Administrator extends User {
    public Administrator(String nome, String email, String senha) {
        super(nome, email, senha); // Chama o construtor da classe pai
    }

    // Métodos específicos para administradores
    public void criarProduto(List<Product> produtos, Product novoProduto) {
        produtos.add(novoProduto);
        System.out.println("Produto criado com sucesso!");
    }

    public void criarUsuario(Scanner scanner, List<User> usuarios) {
        System.out.print("Digite o nome do usuário: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o email do usuário: ");
        String email = scanner.nextLine();

        System.out.print("Digite a senha do usuário: ");
        String senha = scanner.nextLine();

        User novoUsuario = new User(nome, email, senha);
        usuarios.add(novoUsuario);
        System.out.println("Usuário criado com sucesso!");
    }
}