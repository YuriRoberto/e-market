
public class Product {
    private static int contadorProdutos = 0;
    
    private int id;
    private String name;
    private String description;
    private String category;
    private double price;
    private int quantityStock;

    public Product(String name, String description, double price, int quantityStock, String category) {
        this.id = ++contadorProdutos;
        this.name = name;
        this.description = description;
        this.category = category;
        this.price = price;
        this.quantityStock = quantityStock;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantityStock() {
        return quantityStock;
    }

    public void setQuantityStock(int quantityStock) {
        this.quantityStock = quantityStock;
    }

    public boolean isInStock() {
        return quantityStock > 0;
    }

    public void addStock(int quantity) {
        quantityStock += quantity;
    }

    public void removeStock(int quantity) {
        if (quantityStock >= quantity) {
            quantityStock -= quantity;
        } else {
            System.out.println("Quantidade insuficiente em estoque.");
        }
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", category='" + category + '\'' +
                ", price=" + price +
                ", quantityStock=" + quantityStock +
                '}';
    }
}