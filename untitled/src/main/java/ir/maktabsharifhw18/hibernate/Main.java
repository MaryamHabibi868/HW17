package ir.maktabsharifhw18.hibernate;


import ir.maktabsharifhw17.jdbc.domains.*;
import ir.maktabsharifhw17.jdbc.service.*;
import ir.maktabsharifhw18.hibernate.entity.BankName;
import ir.maktabsharifhw18.hibernate.entity.Card;
import ir.maktabsharifhw18.hibernate.entity.User;
import ir.maktabsharifhw18.hibernate.service.CardService;
import ir.maktabsharifhw18.hibernate.service.TransactionService;
import ir.maktabsharifhw18.hibernate.service.UserService;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        UserService userService = new UserService();
        CardService cardService = new CardService();
        TransactionService transactionService = new TransactionService();

        while (true) {
            System.out.println("\nMain Menu:");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");


            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    User newUser = new User();
                    System.out.print("First Name: ");
                    newUser.setFirstName(scanner.nextLine());
                    System.out.print("Last Name: ");
                    newUser.setLastName(scanner.nextLine());
                    System.out.print("User Name: ");
                    newUser.setUserName(scanner.nextLine());
                    System.out.print("Password: ");
                    newUser.setPassword(scanner.nextLine());

                    Optional<User> regUser = userService.register(newUser);
                    if (regUser.isPresent())
                        System.out.println("Registration Successful.");
                    else
                        System.out.println("This user name is already exists.");
                    break;

                case 2:
                    System.out.print("User Name: ");
                    String username = scanner.nextLine();
                    System.out.print("Password: ");
                    String password = scanner.nextLine();
                    Optional<User> loggedIn = userService.login(username, password);
                    if (loggedIn.isEmpty()) {
                        System.out.println("Login Failed.");
                        break;
                    }

                    User currentUser = loggedIn.get();

                    while (true) {
                        System.out.println("\nTransaction:");
                        System.out.println("1. Add Card");
                        System.out.println("2. Delete Card");
                        System.out.println("3. Card to Card");
                        System.out.println("4. Paya");
                        System.out.println("5. Satna");
                        System.out.println("6. Back to main menu");

                        int op = scanner.nextInt();
                        scanner.nextLine();

                        if (op == 6) break;

                        switch (op) {
                            case 1:
                                Card card = new Card();
                                System.out.print("Card Number: ");
                                card.setCardNumber(scanner.nextLine());
                                System.out.print("Bank Name: ");
                                card.setBankName(BankName.valueOf(scanner.nextLine().toUpperCase()));
                                System.out.print("Balance: ");
                                card.setBalance(scanner.nextDouble());
                                scanner.nextLine();
                                System.out.print("Expired Date: ");
                                card.setExpiredDate(LocalDate.parse(scanner.nextLine()));
                                card.setUser(currentUser);
                                cardService.create(card);
                                System.out.println("Add Card Successful.");
                                break;

                            case 2:
                                System.out.print("Id card for delete: ");
                                int deleteId = scanner.nextInt();
                                scanner.nextLine();
                                boolean deleted = cardService.deleteById(deleteId);
                                System.out.println(deleted ? "Delete successfully." : "Card not found.");
                                break;

                            case 3:
                                System.out.print("Source Card Number: ");
                                String srcCard = scanner.nextLine();
                                System.out.print("Destination Card Number: ");
                                String dstCard = scanner.nextLine();
                                System.out.print("Amount: ");
                                double amount1 = scanner.nextDouble();
                                scanner.nextLine();
                                transactionService.performCardToCard(srcCard, dstCard, amount1);
                                break;

                            case 4:
                                System.out.print("Source Card Number: ");
                                String payaSrc = scanner.nextLine();
                                System.out.print("Destination Card Number: ");
                                String payaDst = scanner.nextLine();
                                System.out.print("Amount: ");
                                double amount2 = scanner.nextDouble();
                                scanner.nextLine();
                                transactionService.performPaya(payaSrc, payaDst, amount2);
                                break;

                            case 5:
                                System.out.print("Source Card Number: ");
                                String satnaSrc = scanner.nextLine();
                                System.out.print("Destination Card Number: ");
                                String satnaDst = scanner.nextLine();
                                System.out.print("Amount: ");
                                double amount3 = scanner.nextDouble();
                                scanner.nextLine();
                                transactionService.performSatna(satnaSrc, satnaDst, amount3);
                                break;

                            default:
                                System.out.println("Wrong choice.");
                        }
                    }
                    break;

                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;

                default:
                    System.out.println("Wrong choice.");
            }
        }
    }
}
