import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

class Order {
    private int numeroPedido;
    private LocalDate dataPedido;
    private List<OrderItem> listaProdutos;
    private double totalPedido;

    private static AtomicInteger contadorPedidos = new AtomicInteger(0);

    public Order() {
        this.numeroPedido = contadorPedidos.incrementAndGet();
        this.dataPedido = LocalDate.now(); // Define a data atual como data do pedido
        this.listaProdutos = new ArrayList<>();
        this.totalPedido = 0.0;
    }

    public Order(int numeroPedido, LocalDate data, List<OrderItem> itensPedido, double total) {
        this.numeroPedido = numeroPedido;
        this.dataPedido = data;
        this.listaProdutos = itensPedido;
        this.totalPedido = total;
    }

    public int getNumeroPedido() {
        return numeroPedido;
    }

    public LocalDate getDataPedido() {
        return dataPedido;
    }

    public List<OrderItem> getListaProdutos() {
        return listaProdutos;
    }

    public double getTotalPedido() {
        double total = 0.0;
        for (OrderItem item : listaProdutos) {
            total += item.getPreco();
        }
        return total;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Pedido Nº: ").append(numeroPedido).append("\n");
        sb.append("Data: ").append(dataPedido).append("\n");
        sb.append("Produtos:\n");
        for (OrderItem p : listaProdutos) {
            sb.append(" - ").append(p).append("\n");
        }
        sb.append(String.format("Total do Pedido: R$ %.2f", totalPedido));
        return sb.toString();
    }
}
