import java.util.List;
import java.util.ArrayList;

class Client {
    private String address;
    private List<Order> historyPurchases = new ArrayList<>();

    public void addOrder(Order order) {
        historyPurchases.add(order);
    }
}