package models;

import interfaces.ProductInterface;
public class Product extends BasicData implements ProductInterface {
    String name;
    double price;

    @Override
    public String getId() { return id; }

    @Override
    public ProductInterface setId(String id){
        this.id = id;
        return this;
    }

    @Override
    public String getName(){ return name; }

    @Override
    public double getPrice() { return price; }

    public ProductInterface setName(String name){
        this.name = name;
        return this;
    }

    public ProductInterface setPrice(double price){
        this.price = price;
        return this;
    }

    @Override
    public String toString(){
        return "Produkt " + id +
                ", nazwa=" + getName() +
                ", cena wypożyczenia=" + getPrice();
    }
}
