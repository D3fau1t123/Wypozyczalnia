package interfaces;

// interjes produktu z metodami do pobierania
// i ustawiania danych produktowych - nazwy i ceny za wypożyczenie na jeden dzień
public interface ProductInterface extends BasicInterface {
    String getName();
    double getPrice();

    ProductInterface setName(String name);
    ProductInterface setPrice(double price);
}
