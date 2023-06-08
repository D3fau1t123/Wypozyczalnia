package interfaces;

import java.time.LocalDate;

public interface RentalInterface extends BasicInterface{

    static String getId(String productId, String userId, LocalDate date){
        return "WYP/" +
                productId +
                "/" +
                "KL/" +
                userId +
                "/" +
                date;
    }

    RentalInterface setId(String productId, String userId, LocalDate date);

    LocalDate getRentDate();
    LocalDate getReturnDate();

    long getRentDays();

    double getRentPrice();

    ProductInterface getProduct();

    UserInterface getRentedTo();

    RentalInterface setRentDate(LocalDate rentDate);
    RentalInterface setReturnDate(LocalDate returnDate);
    RentalInterface setProduct(ProductInterface product);
    RentalInterface setRentedTo(UserInterface user);
}
