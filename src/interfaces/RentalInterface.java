package interfaces;

import java.time.LocalDate;

// interfejs opisujący metody służące do zarzadzania wypożyczeniami
public interface RentalInterface extends BasicInterface {

    // metoda statyczna generująca id wypożyczenia na podstawie id produktu, klienta oraz daty
    static String getId(String productId, String userId, LocalDate date) {
        return "WYP/" +
                productId +
                "/" +
                "KL/" +
                userId +
                "/" +
                date;
    }

    // deklaracja metody ustawiającej id wypożyczenia na postawie id produktu, klienta oraz daty
    RentalInterface setId(String productId, String userId, LocalDate date);

    // pozostałe gettery/settery - pobieranie i ustawianie dat wypożyczenia i zwrotu,
    // danych produktu i klienta, pobieranie aktualnego kosztu wypożyczenia i czasu,
    // na jaki przedmiot został wypożyczony (w dniach)
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
