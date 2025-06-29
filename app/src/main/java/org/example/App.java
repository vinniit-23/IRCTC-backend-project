package org.example;

import org.example.entities.Ticket;
import org.example.entities.Train;
import org.example.entities.User;
import org.example.services.TrainService;
import org.example.services.UserBookingService;
import org.example.util.UserServiceUtil;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

public class App {
    public static void main(String[] args) {
        System.out.println("Running train booking service");
        Scanner scan = new Scanner(System.in);
        int option = 0;
        UserBookingService userBookingService;
        User loggedInUser = null; // Track the logged-in user

        try {
            userBookingService = new UserBookingService();
        } catch (Exception e) {
            System.out.println("there is something wrong: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        while (option != 7) {
            System.out.println("Choose option");
            System.out.println("1. Sign up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a seat");
            System.out.println("6. Cancel My Booking");
            System.out.println("7. Exit the App");
            option = scan.nextInt();

            switch (option) {
                case 1:
                    System.out.println("Enter the Username to signup");
                    String nameToSignUp = scan.next();
                    System.out.println("Enter password");
                    String passwordToSignUp = scan.next();
                    User user = new User(nameToSignUp, passwordToSignUp,
                            UserServiceUtil.hashedPassword(passwordToSignUp),
                            new ArrayList<>(), UUID.randomUUID().toString());
                    userBookingService.signUp(user);
                    break;
                case 2:
                    System.out.println("enter your username to login");
                    String nameToLoginUp = scan.next();
                    System.out.println("enter your password to login");
                    String passwordToLoginUp = scan.next();
                    loggedInUser = new User(nameToLoginUp, passwordToLoginUp,
                            UserServiceUtil.hashedPassword(passwordToLoginUp),
                            new ArrayList<>(), UUID.randomUUID().toString());
                    try {
                        userBookingService = new UserBookingService(loggedInUser);
                    } catch (Exception e) {
                        System.out.println("Login failed: " + e.getMessage());
                        loggedInUser = null;
                    }
                    break;
                case 3:
                    if (loggedInUser == null) {
                        System.out.println("Please login first.");
                        break;
                    }
                    System.out.println("showing your Bookings");
                    userBookingService.showBooking();
                    break;
                case 4:
                    System.out.println("enter source ");
                    String source = scan.next();
                    System.out.println("enter destination");
                    String destination = scan.next();
                    try {
                        TrainService trainService = new TrainService();
                        trainService.searchTrains(source, destination);
                    } catch (Exception e) {
                        System.out.println("Some error is occurring in searching: " + e.getMessage());
                    }
                    break;
                case 5:
                    if (loggedInUser == null) {
                        System.out.println("Please login first.");
                        break;
                    }
                    System.out.println("showing available seats");
                    Train train = new Train();
                    train.getSeats();
                    System.out.println("Enter source");
                    source = scan.next();
                    System.out.println("Enter destination");
                    destination = scan.next();
                    System.out.println("Enter row number to book");
                    int row = scan.nextInt();
                    System.out.println("Enter column number to book");
                    int col = scan.nextInt();
                    System.out.println("Enter ticket id");
                    String ticketId = scan.next();
                    try {
                        TrainService trainService = new TrainService();
                        Ticket ticket = new Ticket(ticketId, loggedInUser.getUserID(), source, destination, null, train);
                        trainService.bookSeat(row, col, train, ticket, trainService.searchTrains(source, destination), loggedInUser);
                    } catch (Exception e) {
                        System.out.println("Error in booking seat: " + e.getMessage());
                    }
                    break;
                case 6:
                    if (loggedInUser == null) {
                        System.out.println("Please login first.");
                        break;
                    }
                    System.out.println("Enter ticket id to cancel booking");
                    String ticketIdToCancel = scan.next();
                    if (userBookingService.cancelBooking(ticketIdToCancel)) {
                        System.out.println("Booking cancelled successfully.");
                    } else {
                        System.out.println("Failed to cancel booking. Ticket not found.");
                    }
                    break;
                case 7:
                    System.out.println("Exiting the application.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        }
        scan.close();
    }
}