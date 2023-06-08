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

public class RunController {
    private final ConsoleView view;
    private final Map<String, UserInterface> users = new HashMap<>();
    private final Map<String, ProductInterface> products = new HashMap<>();
    private final Map<String, RentalInterface> rentals = new HashMap<>();

    public RunController(ConsoleView view){
        this.view = view;
        InitApp.prepareData(users, products, rentals);
    }

    public void run(){
        Scanner scanner = new Scanner(System.in);
        String message = view.getProperty("message.menu");
        String decision;
        String[] menu = message.split("\\.");
        String[] output;
        updateView(menu);
        do{
            System.out.print("> ");
            decision = String.valueOf(scanner.next());
            decision = decision.toUpperCase().substring(0,1);

            switch(decision){
                case "R" -> {
                    registerClient(users);
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
                default -> view.printError(view.getProperty("error.incorrect.input") + "\n");
            }
            updateView(menu);
        }while(true);
    }

    private void rentProduct(
            Map<String, UserInterface> users,
            Map<String, ProductInterface> products,
            Map<String, RentalInterface> rentals
    ){
        Map<String, ProductInterface> availableProducts = getAvailableProducts(products, getActiveRentals(rentals));

        view.printLine(view.getProperty("message.add.rental.welcome") + "\n");

        Scanner input = new Scanner(System.in);
        String[] userList = view.getMap(view.getProperty("message.users.title"), users);
        updateView(userList);

        view.printLine(view.getProperty("message.add.rental.get.client"));
        String clientId = input.nextLine().toUpperCase();

        String[] productList = view.getMap(view.getProperty("message.products.title"), availableProducts);
        updateView(productList);

        view.printLine(view.getProperty("message.add.rental.get.product"));
        String productId = input.nextLine().toUpperCase();
        if (availableProducts.containsKey(productId) && users.containsKey(clientId)) {
            LocalDate rentDate = LocalDate.now();
            ProductInterface product = availableProducts.get(productId);
            UserInterface client = users.get(clientId);

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
        view.printLine(view.getProperty("message.add.rental.return") + "\n");

        String[] userList = view.getMap(view.getProperty("message.users.title"), users);
        updateView(userList);

        Scanner input = new Scanner(System.in);
        view.printLine(view.getProperty("message.add.rental.get.client"));
        String clientId = input.nextLine().toUpperCase();

        String[] rentalList = view.getMap(view.getProperty("message.products.title"), getUserRentals(clientId));
        updateView(rentalList);

        view.printLine(view.getProperty("message.add.rental.get.product"));
        String productId = input.nextLine().toUpperCase();

        view.printLine(view.getProperty("message.add.rental.get.date"));
        String rentDateString = input.nextLine();
        LocalDate rentDate = LocalDate.parse(rentDateString, DateTimeFormatter.ISO_LOCAL_DATE);

        if (products.containsKey(productId) && users.containsKey(clientId)) {
            LocalDate returnDate = LocalDate.now();
            String rentalId = RentalInterface.getId(productId, clientId, rentDate);
            System.out.println(rentalId);
            if (rentals.containsKey(rentalId)) {
                RentalInterface rental = rentals.get(rentalId);
                rental.setReturnDate(returnDate);
                rentals.put(rental.getId(), rental);
            } else {
                System.out.println("tutaj");
                view.printError(view.getProperty("error.incorrect.input") + "\n");
            }
        } else {
            System.out.println("a tutaj");
            view.printError(view.getProperty("error.incorrect.input") + "\n");
        }
    }

    private void addProduct(Map<String, ProductInterface> products) {
        view.printLine(view.getProperty("message.add.product.welcome") + "\n");
        Scanner input = new Scanner(System.in);
        view.printLine(view.getProperty("message.add.product.get.id"));
        String id = input.nextLine().toUpperCase();
        view.printLine(view.getProperty("message.add.product.get.name"));
        String name = input.nextLine();

        try {
            view.printLine(view.getProperty("message.add.product.get.price"));
            double price = input.nextDouble();

            ProductInterface product = new Product().setId(id).setName(name).setPrice(price);
            products.put(product.getId(), product);

        } catch (InputMismatchException e) {
            view.printError(view.getProperty("error.incorrect.input") + "\n");
        }
    }

    private void registerClient(Map<String, UserInterface> users) {
        view.printLine(view.getProperty("message.add.user.welcome") + "\n");
        Scanner input = new Scanner(System.in);
        view.printLine(view.getProperty("message.add.user.get.id"));
        String id = input.nextLine().toUpperCase();
        view.printLine(view.getProperty("message.add.user.get.name"));
        String name = input.nextLine();

        UserInterface client = new Client().setId(id).setName(name);
        users.put(client.getId(), client);
    }

    private Map<String, ProductInterface> getAvailableProducts(Map<String, ProductInterface> products, Map<String, RentalInterface> rentals) {
        Map<String, ProductInterface> result = new HashMap<>(products);

        for (String rentalKey: rentals.keySet()) {
            for (String productKey: products.keySet()) {
                if (rentals.get(rentalKey).getProduct().equals(products.get(productKey))) {
                    result.remove(productKey);
                }
            }
        }
        return result;
    }

    private Map<String, RentalInterface> getActiveRentals(Map<String, RentalInterface> rentals) {
        Map<String, RentalInterface> result = new HashMap<>();

        for (String rentalKey: rentals.keySet()) {
            if (rentals.get(rentalKey).getReturnDate() == null) {
                result.put(rentals.get(rentalKey).getId(), rentals.get(rentalKey));
            }
        }
        return result;
    }

    private Map<String,? extends BasicInterface> getUserRentals(String clientId) {
        Map<String, RentalInterface> result = new HashMap<>();
        if (users.containsKey(clientId)) {
            UserInterface client = users.get(clientId);
            for (String rentalKey: rentals.keySet()) {
                if (rentals.get(rentalKey).getRentedTo().equals(client)) {
                    result.put(rentals.get(rentalKey).getId(), rentals.get(rentalKey));
                }
            }
        }
        return result;
    }

    public void updateView(String[] data) { view.print(data); }
}
