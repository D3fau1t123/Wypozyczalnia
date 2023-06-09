package controller;

import interfaces.BasicInterface;
import interfaces.ProductInterface;
import interfaces.RentalInterface;
import interfaces.UserInterface;
import models.Client;
import models.Product;
import models.Rental;
import util.InitApp;
import view.ConsoleView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.Scanner;

// klasa obsługująca akcje użytkownika
public class RunController {
    // obiekt odpowiedzialny za wyświetlanie komunikatów i danych (widok)
    private final ConsoleView view;

    // kolekcje przechowujące dane o użytkownikach, produktach i wypożyczeniach
    // w postaci wpisów klucz-wartość
    // klucze są unikalne, co oznacza, że podanie takiego samego klucza spowoduje zmianę wartości (obiektu)
    private final Map<String, UserInterface> users = new HashMap<>();
    private final Map<String, ProductInterface> products = new HashMap<>();
    private final Map<String, RentalInterface> rentals = new HashMap<>();

    public RunController(ConsoleView view) {
        this.view = view;
        // wczytanie do kolekcji przykładowych danych
        // można to rozbudować o wczytywanie z pliku bądź bazy danych
        InitApp.prepareData(users, products, rentals);
    }

    public void run() {
        Scanner scanner = new Scanner(System.in);
        // pobranie tekstu o podanym kluczu z pliku messages.xml za pomocą obiektu Properties
        String message = view.getProperty("message.menu");
        String decision;
        // podzielenie tekstu na poszczególne linie (. oznacza koniec linii)
        String[] menu = message.split("\\.");
        String[] output;

        // wyświetlenie menu
        updateView(menu);
        do {
            System.out.print("> ");

            // pobranie znaku z konsoli (pobierany jest pierwszy znak)
            decision = String.valueOf(scanner.next());
            decision = decision.toUpperCase().substring(0,1);

            // wybór akcji
            switch (decision) {
                case "R" -> {
                    registerClient(users);
                    // aktualizacja widoku
                    output = view.getMap(view.getProperty("message.users.title"), users);
                    updateView(output);
                }
                case "D" -> {
                    addProduct(products);
                    output = view.getMap(view.getProperty("message.products.title"), products);
                    updateView(output);
                }
                case "W" -> {
                    rentProduct(users, products, rentals);
                    output = view.getMap(view.getProperty("message.rentals.title"), getActiveRentals(rentals));
                    updateView(output);
                }
                case "Z" -> {
                    returnProduct(users, products, rentals);
                }
                case "K" -> {
                    output = view.getMap(view.getProperty("message.users.title"), users);
                    updateView(output);
                }
                case "P" -> {
                    output = view.getMap(view.getProperty("message.products.title"), getAvailableProducts(products, getActiveRentals(rentals)));
                    updateView(output);
                }
                case "A" -> {
                    output = view.getMap(view.getProperty("message.rentals.title"), getActiveRentals(rentals));
                    updateView(output);
                }
                case "Q" -> {
                    output = new String[]{view.getProperty("message.bye")};
                    updateView(output);
                    return;
                }
                default -> {
                    view.printError(view.getProperty("error.incorrect.input") + "\n");
                }
            }
            updateView(menu);
        } while (true);

    }

    private void rentProduct(
            Map<String, UserInterface> users,
            Map<String, ProductInterface> products,
            Map<String, RentalInterface> rentals
    ) {
        // pobranie mapy dostępnych produktów na podstawie wszystkich produktów
        Map<String, ProductInterface> availableProducts = getAvailableProducts(products, getActiveRentals(rentals));

        // wyświetlanie wiadomości powitalnej dla dodawania wynajmu
        view.printLine(view.getProperty("message.add.rental.welcome") + "\n");

        Scanner input = new Scanner(System.in);

        // pobieranie listy użytkowników i zaktualizowanie widoku
        String[] userList = view.getMap(view.getProperty("message.users.title"), users);
        updateView(userList);

        // pobranie id klienta
        view.printLine(view.getProperty("message.add.rental.get.client"));
        String clientId = input.nextLine().toUpperCase();

        // pobranie listy dostępnych produktów i zaktualizowanie widoku
        String[] productList = view.getMap(view.getProperty("message.products.title"), availableProducts);
        updateView(productList);

        // prośba o podanie id produktu
        view.printLine(view.getProperty("message.add.rental.get.product"));
        String productId = input.nextLine().toUpperCase();

        if (availableProducts.containsKey(productId) && users.containsKey(clientId)) {
            // pobranie bieżącej daty wynajmu
            LocalDate rentDate = LocalDate.now();
            // pobranie obiektu produktu i klienta na podstawie id
            ProductInterface product = availableProducts.get(productId);
            UserInterface client = users.get(clientId);

            // tworzenie nowego obiektu wynajmu i dodawanie go do mapy wynajmów
            RentalInterface rental = new Rental()
                    .setId(product.getId(), client.getId(), rentDate)
                    .setProduct(product)
                    .setRentedTo(client)
                    .setRentDate(rentDate);
            rentals.put(rental.getId(), rental);
        } else {
            view.printError(view.getProperty("error.incorrect.input") + "\n");
        }
    }

    private void returnProduct(
            Map<String, UserInterface> users,
            Map<String, ProductInterface> products,
            Map<String, RentalInterface> rentals
    ) {
        // wyświetlanie wiadomości rozpoczynających metodę
        view.printLine(view.getProperty("message.add.rental.return") + "\n");

        // pobranie listy użytkowników
        String[] userList = view.getMap(view.getProperty("message.users.title"), users);
        updateView(userList);

        // pobranie id klienta
        Scanner input = new Scanner(System.in);
        view.printLine(view.getProperty("message.add.rental.get.client"));
        String clientId = input.nextLine().toUpperCase();

        // pobranie listy wynajmów dla określonego klienta
        String[] rentalList = view.getMap(view.getProperty("message.products.title"), getUserRentals(clientId));
        updateView(rentalList);

        // pobranie id produktu
        view.printLine(view.getProperty("message.add.rental.get.product"));
        String productId = input.nextLine().toUpperCase();

        // pobranie daty zwrotu
        view.printLine(view.getProperty("message.add.rental.get.date"));
        String rentDateString = input.nextLine();
        LocalDate rentDate = LocalDate.parse(rentDateString, DateTimeFormatter.ISO_LOCAL_DATE);

        // sprawdzenie czy podany produkt i klient istnieją
        if (products.containsKey(productId) && users.containsKey(clientId)) {
            LocalDate returnDate = LocalDate.now();
            String rentalId = RentalInterface.getId(productId, clientId, rentDate);

            // zaktualizowanie daty zwrotu i wynajmu
            if (rentals.containsKey(rentalId)) {
                RentalInterface rental = rentals.get(rentalId);
                rental.setReturnDate(returnDate);
                rentals.put(rental.getId(), rental);
            } else {
                view.printError(view.getProperty("error.incorrect.input") + "\n");
            }
        } else {
            view.printError(view.getProperty("error.incorrect.input") + "\n");
        }
    }


    private void addProduct(Map<String, ProductInterface> products) {
        view.printLine(view.getProperty("message.add.product.welcome") + "\n");
        // pobranie id produktu
        Scanner input = new Scanner(System.in);
        view.printLine(view.getProperty("message.add.product.get.id"));
        String id = input.nextLine().toUpperCase();

        // pobranie nazwy produktu
        view.printLine(view.getProperty("message.add.product.get.name"));
        String name = input.nextLine();

        try {
            // pobranie ceny produktu
            view.printLine(view.getProperty("message.add.product.get.price"));
            double price = input.nextDouble();

            // tworzenie nowego obiektu i produktu i dodanie go do mapy produktów
            ProductInterface product = new Product().setId(id).setName(name).setPrice(price);
            products.put(product.getId(), product);

        } catch (InputMismatchException e) {
            view.printError(view.getProperty("error.incorrect.input") + "\n");
        }
    }

    private void registerClient(Map<String, UserInterface> users) {

        // pobranie danych użytkownika - należy podać id i nazwę użytkownika (np. imię i nazwisko)
        view.printLine(view.getProperty("message.add.user.welcome") + "\n");
        Scanner input = new Scanner(System.in);
        view.printLine(view.getProperty("message.add.user.get.id"));
        String id = input.nextLine().toUpperCase();
        view.printLine(view.getProperty("message.add.user.get.name"));
        String name = input.nextLine();

        // utworzenie nowego obiektu i dodanie go do kolekcji
        UserInterface client = new Client().setId(id).setName(name);
        users.put(client.getId(), client);
    }

    // wyświetlenie dostępnych (obecnie nie wypożyczonych) produktów
    private Map<String, ProductInterface> getAvailableProducts(Map<String, ProductInterface> products, Map<String, RentalInterface> rentals) {
        // utworzenie nowej kolekcji typu Map, zawierającej wszystkie produkty
        Map<String, ProductInterface> result = new HashMap<>(products);

        // pętla for przechodząca przez kolekcję (Set) kluczy z kolekcji wypożyczeń
        for (String rentalKey: rentals.keySet()) {
            // pętla for przechodząca przez kolekcję (Set) kluczy z kolekcji produktów
            for (String productKey: products.keySet()) {
                // sprawdzenie, czy produkt jest aktualnie wypożyczony - czy znajduje się w przekazanej kolekcji wypożyczeń
                // jeśli tak, usuwamy produkt z wynikowej kolekcji
                // (przy wywołaniu przekazujemy kolekcję z aktywnymi wypożyczeniami, więc nie sprawdzamy daty zwrotu)
                if (rentals.get(rentalKey).getProduct().equals(products.get(productKey))) {
                    result.remove(productKey);
                }
            }
        }
        //zwrócenie kolekcji zawierającej dostępne produkty
        return result;
    }

    // wyświetlenie danych o aktualnie wypożyczonych produktach
    private Map<String, RentalInterface> getActiveRentals(Map<String, RentalInterface> rentals) {
        Map<String, RentalInterface> result = new HashMap<>();

        for (String rentalKey: rentals.keySet()) {
            // jeśli obiekt nie ma ustawionej daty zwrotu, oznacza to, że jest aktualnie wypożyczony
            if (rentals.get(rentalKey).getReturnDate() == null) {
                result.put(rentals.get(rentalKey).getId(), rentals.get(rentalKey));
            }
        }

        return result;
    }

    private Map<String,? extends BasicInterface> getUserRentals(String clientId) {
        Map<String, RentalInterface> result = new HashMap<>();
        // sprawdzenie, czy użytkownik o danym id istnieje
        if (users.containsKey(clientId)) {
            // pobranie obiektu użytkownika z kolekcji
            UserInterface client = users.get(clientId);
            // pętla for przechodząca przez kolekcję (Set) kluczy z kolekcji wypożyczeń
            // sprawdzamy całą kolekcję w celu wyświetlenia historii wypożyczeń
            // jeśli chcielibyśmy wyświetlić tylko aktualnie wypożyczone przedmioty,
            // należałoby pobrać aktualne wypożyczenia za pomocą akcji getActiveRentals
            for (String rentalKey: rentals.keySet()) {
                // jeśli przedmiot został wypożyczony klientowi, dodajemy wpis do kolekcji wynikowej
                if (rentals.get(rentalKey).getRentedTo().equals(client)) {
                    result.put(rentals.get(rentalKey).getId(), rentals.get(rentalKey));
                }
            }
        }

        return result;
    }

    // aktualizacja widoku, wypisanie danych za pomocą metody z klasy pomocniczej ConsoleView
    public void updateView(String[] data) {
        view.print(data);
    }
}
