package models;

import interfaces.ProductInterface;

// model opisujący produkt, implementuje interfejs produktu i rozszerzana klasę abstrakcyjną BasicData zawierającą id

public class Product extends BasicData implements ProductInterface {

    String name;
    double price;

    // implementacja metod interfejsu (settery zwracają obiekt klasy ProductInterface)
    @Override
    public String getId() {
        return id;
    }

    @Override
    public ProductInterface setId(String id) {
        this.id = id;
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public double getPrice() {
        return price;
    }

    public ProductInterface setName(String name) {
        this.name = name;
        return this;
    }

    public ProductInterface setPrice(double price) {
        this.price = price;
        return this;
    }

    // implementacja metodfy toString (wykorzystywane w ConsoleView do wyświetlania danych o produktach)
    @Override
    public String toString() {
        return "Produkt " + id +
                ", nazwa=" + getName() +
                ", cena wypożyczenia=" + getPrice();
    }
}
