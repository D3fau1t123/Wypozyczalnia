package interfaces;

public interface ProductInterface extends BasicInterface{
    String getName();
    double getPrice();

    ProductInterface setName(String name);
    ProductInterface setPrice(double price);
}
