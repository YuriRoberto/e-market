import java.util.List;
import java.util.Scanner;


public class Administrator extends User {
    public Administrator(String name, String email, String password) {
        super(name, email, password); // Chama o construtor da classe pai
    }

    // Métodos específicos para administradores
    public void createProduct(List<Product> products, Product newProduct) {
        products.add(newProduct);
        System.out.println("Produto criado com sucesso!");
    }

    public void createUser(Scanner scanner, List<User> users) {
        System.out.print("Digite o name do usuário: ");
        String name = scanner.nextLine();

        System.out.print("Digite o email do usuário: ");
        String email = scanner.nextLine();

        System.out.print("Digite a password do usuário: ");
        String password = scanner.nextLine();

        User newUser = new User(name, email, password);
        users.add(newUser);
        System.out.println("Usuário criado com sucesso!");
    }
}