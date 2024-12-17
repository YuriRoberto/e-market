import java.util.ArrayList;
import java.util.Date;
import java.util.List;

// public class Order {
//     private Date date;
//     private Status status;
//     private Customer customer;
//     private List<OrderItem> items;
//     // ... outros atributos e métodos
// }

// Classe Pedido
class Order {
    private static int contadorPedidos = 0; // Gerador automático de número do pedido

    private int numeroPedido;
    private Date dataPedido;
    private List<Product> listaProdutos;
    private double totalPedido;

    public Order() {
        this.numeroPedido = ++contadorPedidos;
        this.dataPedido = new Date(); // Define a data atual como data do pedido
        this.listaProdutos = new ArrayList<>();
        this.totalPedido = 0.0;
    }

    // // Adiciona um produto ao pedido
    // public void adicionarProduto(Product produto) {
    // listaProdutos.add(produto);
    // recalcularTotal();
    // }

    // // Recalcula o total do pedido
    // private void recalcularTotal() {
    // totalPedido = listaProdutos.stream()
    // .mapToDouble(Product::calcularTotal)
    // .sum();
    // }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public List<Product> getListaProdutos() {
        return listaProdutos;
    }

    public double getTotalPedido() {
        for (int i = 0; i < listaProdutos.size(); i++) {
            totalPedido += listaProdutos.get(i).getPreco();
        }

        return totalPedido;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido Nº: ").append(numeroPedido).append("\n");
        sb.append("Data: ").append(dataPedido).append("\n");
        sb.append("Produtos:\n");
        for (Product p : listaProdutos) {
            sb.append(" - ").append(p).append("\n");
        }
        sb.append(String.format("Total do Pedido: R$ %.2f", totalPedido));
        return sb.toString();
    }
}
