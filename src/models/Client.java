package models;

import interfaces.UserInterface;
public class Client extends BasicData implements UserInterface {
    String name;

    @Override
    public String getId() { return id; }

    @Override
    public String getName() { return name; }

    @Override
    public UserInterface setId(String id){
        this.id = id;
        return this;
    }

    public UserInterface setName(String name){
        this.name = name;
        return this;
    }

    @Override
    public String toString(){
        return "Klient " + id +
                ", nazwa=" + getName();
    }
}
