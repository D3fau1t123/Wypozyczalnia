package models;

import interfaces.ProductInterface;
import interfaces.RentalInterface;
import interfaces.UserInterface;

import java.time.LocalDate;

import static java.time.temporal.ChronoUnit.DAYS;

public class Rental extends BasicData implements RentalInterface {

    LocalDate rentDate;
    LocalDate returnDate;
    ProductInterface product;
    UserInterface rentedTo;

    @Override
    public String getId() {
        return id;
    }

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

    @Override
    public long getRentDays() {
        return DAYS.between(rentDate, LocalDate.now()) > 0 ? DAYS.between(rentDate, LocalDate.now()) : 1;
    }

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