public class OrderItem {
    private Product produto;
    private int quantidade;

    public OrderItem(Product produto, int quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
    }

    public double getPreco() {
        return produto.getPreco() * quantidade;
    }
}
