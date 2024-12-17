
public class Product {
    private static int contadorProdutos = 0; // Gerador automático de número do pedido
    
    private int id;
    private String nome;
    private String descricao;
    private String categoria;
    private double preco;
    private int quantidadeEstoque;

    // Construtor
    public Product(String nome, String descricao, double preco, int quantidadeEstoque, String categoria) {
        this.id = ++contadorProdutos;
        this.nome = nome;
        this.descricao = descricao;
        this.categoria = categoria;
        this.preco = preco;
        this.quantidadeEstoque = quantidadeEstoque;
    }

    // Getters e setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        this.preco = preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        this.quantidadeEstoque = quantidadeEstoque;
    }

    // Método para verificar se o produto está em estoque
    public boolean estaEmEstoque() {
        return quantidadeEstoque > 0;
    }

    // Método para adicionar quantidade ao estoque
    public void adicionarEstoque(int quantidade) {
        quantidadeEstoque += quantidade;
    }

    // Método para remover quantidade do estoque
    public void removerEstoque(int quantidade) {
        if (quantidadeEstoque >= quantidade) {
            quantidadeEstoque -= quantidade;
        } else {
            System.out.println("Quantidade insuficiente em estoque.");
        }
    }

    // Método para obter uma representação em string do produto
    @Override
    public String toString() {
        return "Produto{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", descricao='" + descricao + '\'' +
                ", categoria='" + categoria + '\'' +
                ", preco=" + preco +
                ", quantidadeEstoque=" + quantidadeEstoque +
                '}';
    }
}