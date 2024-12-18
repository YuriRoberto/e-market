import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> products = new ArrayList<>();

    public void addProduct(Product product) {
        if (product.isInStock()) {
            products.add(product);
            System.out.println("Produto adicionado ao carrinho.");
        } else {
            System.out.println("Produto indisponível em estoque.");
        }
    }

    public void ViewCart() {
        if(products.isEmpty())
            System.out.println("O carrinho está vazio.");
        else {
            String stringReturn = "";
            for (int i = 0; i < products.size(); i++) {
                stringReturn += products.get(i).toString();
                stringReturn += "\n";
            }
            System.out.println(stringReturn);
        }

    }

    public List<Product> getProduct() {
        return new ArrayList<>(products);
    }

    public double calculateTotal() {
        double total = 0.0;
        for (Product product : products) {
            total += product.getPrice();
        }
        return total;
    }
}