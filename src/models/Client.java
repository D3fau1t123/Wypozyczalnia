package models;

import interfaces.UserInterface;

// model opisujący klienta wypożyczalni, implementuje interfejs użytkownika i rozszerzana klasę abstrakcyjną BasicData zawierającą id
public class Client extends BasicData implements UserInterface {

    String name;

    // implementacja metod interfejsu
    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public UserInterface setId(String id) {
        this.id = id;
        return this;
    }

    public UserInterface setName(String name) {
        this.name = name;
        return this;
    }

    // implementacja metody toString, wykorzystywana w klasie ConsoleView do wyświetlania danych użytkownika
    @Override
    public String toString() {
        return "Klient " + id +
                ", nazwa=" + getName();
    }
}
