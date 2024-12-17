import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {
    private List<Product> produtos = new ArrayList<>();

    public void adicionarProduto(Product produto) {
        if (produto.estaEmEstoque()) {
            produtos.add(produto);
            System.out.println("Produto adicionado ao carrinho.");
        } else {
            System.out.println("Produto indisponível em estoque.");
        }
    }

    public void VisualizarCarrinho() {
        if(produtos.isEmpty())
            System.out.println("O carrinho está vazio.");
        else {
            String stringReturn = "";
            for (int i = 0; i < produtos.size(); i++) {
                stringReturn += produtos.get(i).toString();
                stringReturn += "\n";
            }
            System.out.println(stringReturn);
        }

    }

    public List<Product> getProdutos() {
        return new ArrayList<>(produtos);
    }

    public double calcularTotal() {
        double total = 0.0;
        for (Product produto : produtos) {
            total += produto.getPreco();
        }
        return total;
    }
}