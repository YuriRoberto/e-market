import java.util.List;
import java.util.ArrayList;

class Client {
    private String address;
    private List<Order> historicoCompras = new ArrayList<>();

    public void adicionarPedido(Order pedido) {
        historicoCompras.add(pedido);
    }
}