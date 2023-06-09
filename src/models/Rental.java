package models;

import interfaces.ProductInterface;
import interfaces.RentalInterface;
import interfaces.UserInterface;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

// model opisujący wypożyczenie, implementuje interfejs wypożyczenia i rozszerzana klasę abstrakcyjną BasicData zawierającą id
public class Rental extends BasicData implements RentalInterface {

    // pola opisujące jaki produkt został wypożyczony, komu, kiedy, i kiedy został zwrócony
    LocalDate rentDate;
    LocalDate returnDate;
    ProductInterface product;
    UserInterface rentedTo;

    // implementacja metod interfejsu
    @Override
    public String getId() {
        return id;
    }

    // implementacja metody setId interfejsu RentalInterface,
    // tworząca id na podstawie id produktu, klienta i daty wypożyczenia,
    // zwracająca obiekt klasy
    @Override
    public RentalInterface setId(String productId, String userId, LocalDate date) {
        this.id = "WYP/" +
                productId +
                "/" +
                "KL/" +
                userId +
                "/" +
                date;

        return this;
    }

    @Override
    public LocalDate getRentDate() {
        return rentDate;
    }

    @Override
    public LocalDate getReturnDate() {
        return returnDate;
    }

    // implementacja metody zwracającej liczbę dni wypożyczenia
    // w przypadku zakończonego wypożyczenia zwraca liczbę dni od wypożyczenia do zwrotu,
    // w przypadku trwającego wypożyczenia - liczbę dni od wypożyczenia do obecnego dnia
    // w obu przypadkach, jeśli dzien wypożyczenia jest dniem obecnym - zwraca 1.
    // Wynika to z przyjętego sposobu naliczania płatności - przedmioty wypożyczane są na całe dni.
    // W przypadku zwrotu przedmioty w tym samym dniu koszt jest równy cenie wypożyczenia na jeden dzień.
    @Override
    public long getRentDays() {
        if (returnDate != null) {
            return DAYS.between(rentDate, returnDate) > 0 ? DAYS.between(rentDate, returnDate) : 1;
        }
        return DAYS.between(rentDate, LocalDate.now()) > 0 ? DAYS.between(rentDate, LocalDate.now()) : 1;
    }

    // implementacja metody obliczającej koszt wypożyczenia na podstawie ceny wypożyczenia produktu
    // i liczby dni, przez które produkt pozostawał wypożyczony
    @Override
    public double getRentPrice() {
        return getRentDays() * product.getPrice();
    }

    @Override
    public ProductInterface getProduct() {
        return product;
    }

    @Override
    public UserInterface getRentedTo() {
        return rentedTo;
    }

    @Override
    public RentalInterface setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public RentalInterface setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
        return this;
    }

    @Override
    public RentalInterface setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
        return this;
    }

    @Override
    public RentalInterface setProduct(ProductInterface product) {
        this.product = product;
        return this;
    }

    @Override
    public RentalInterface setRentedTo(UserInterface user) {
        this.rentedTo = user;
        return this;
    }

    @Override
    public String toString() {
        return "Wypożyczenie " + id +
                ", data wypożyczenia=" + getRentDate() +
                ", klient=" + getRentedTo().getName() + " id=" + getRentedTo().getId() +
                ", przedmiot=" + getProduct().getName() + " id=" + getProduct().getId() +
                ", aktualny koszt wypożyczenia=" + getRentPrice();
    }

}
