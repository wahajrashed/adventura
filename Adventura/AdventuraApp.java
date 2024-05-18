import java.util.InputMismatchException;
import java.util.Scanner;

// Main class to run the application
public class AdventuraApp {

    private static Adventura adventura = new Adventura();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

        while (!exit) {
            displayMenu();
            int choice = getChoice();
            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    addPlace();
                    break;
                case 3:
                    matchUsers();
                    break;
                case 4:
                    displayAllPlaces();
                    break;
                case 5:
                    exit = true;
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void displayMenu() {
        System.out.println("Welcome to the Adventura App!");
        System.out.println("1. Add User");
        System.out.println("2. Add Place");
        System.out.println("3. Match Users");
        System.out.println("4. Display All Places");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
    }

    private static int getChoice() {
        int choice = -1;
        try {
            choice = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a number between 1 and 5.");
            scanner.next(); // Clear the invalid input
        }
        return choice;
    }

    private static void addUser() {
        scanner.nextLine(); // Consume the newline

        System.out.print("
