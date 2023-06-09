package util;

import interfaces.ProductInterface;
import interfaces.RentalInterface;
import interfaces.UserInterface;
import models.Client;
import models.Product;
import models.Rental;

import java.time.LocalDate;
import java.util.Map;

// klasa z metodą pomocniczą dodającą przykładowe dane
public class InitApp {

    public static void prepareData(
            Map<String, UserInterface> users,
            Map<String, ProductInterface> products,
            Map<String, RentalInterface> rentals
    ) {
        ProductInterface product1 = new Product()
                .setId("SCSTO1")
                .setName("Ściski stolarskie")
                .setPrice(20);

        ProductInterface product2 = new Product()
                .setId("BOSCH-WIERT")
                .setName("Wiertarka Bosch")
                .setPrice(18);

        ProductInterface product3 = new Product()
                .setId("BOSCH-MYJKA")
                .setName("Myjka ciśnieniowa BOSCH")
                .setPrice(25);

        products.put(product1.getId(), product1);
        products.put(product2.getId(), product2);
        products.put(product3.getId(), product3);

        UserInterface user1 = new Client()
                .setId("KOWJAN")
                .setName("Jan Kowalski");

        UserInterface user2 = new Client()
                .setId("NOWPIO")
                .setName("Piotr Nowak");

        users.put(user1.getId(), user1);
        users.put(user2.getId(), user2);

        RentalInterface rental1 = new Rental()
                .setId(product1.getId(), user1.getId(), LocalDate.now().minusDays(3))
                .setRentDate(LocalDate.now().minusDays(3))
                .setProduct(product1)
                .setRentedTo(user1);

        RentalInterface rental2 = new Rental()
                .setId(product2.getId(), user2.getId(), LocalDate.now().minusDays(6))
                .setRentDate(LocalDate.now().minusDays(6))
                .setProduct(product2)
                .setRentedTo(user2);

        RentalInterface rental3 = new Rental()
                .setId(product3.getId(), user2.getId(), LocalDate.now().minusDays(2))
                .setRentDate(LocalDate.now().minusDays(2))
                .setReturnDate(LocalDate.now().minusDays(1))
                .setProduct(product2)
                .setRentedTo(user2);

        rentals.put(rental1.getId(), rental1);
        rentals.put(rental2.getId(), rental2);
        rentals.put(rental3.getId(), rental3);
    }
}
