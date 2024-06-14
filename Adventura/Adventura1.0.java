import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Abstract class representing a Place (Event or Sightseeing)
abstract class Place {
    protected String name;
    protected String location;
    protected String type; // e.g., "Event", "Sightseeing"

    // Constructor
    public Place(String name, String location, String type) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (location == null || location.isEmpty()) {
            throw new IllegalArgumentException("Location cannot be null or empty");
        }
        if (type == null || type.isEmpty()) {
            throw new IllegalArgumentException("Type cannot be null or empty");
        }
        this.name = name;
        this.location = location;
        this.type = type;
    }

    // Abstract method to display place details
    public abstract void displayDetails();
}

// Concrete class representing an Event
class Event extends Place {
    private String date;

    // Constructor
    public Event(String name, String location, String date) {
        super(name, location, "Event");
        if (date == null || date.isEmpty()) {
            throw new IllegalArgumentException("Date cannot be null or empty");
        }
        this.date = date;
    }

    @Override
    public void displayDetails() {
        System.out.println("Event: " + name);
        System.out.println("Location: " + location);
        System.out.println("Date: " + date);
    }
}

// Concrete class representing a Sightseeing location
class Sightseeing extends Place {

    // Constructor
    public Sightseeing(String name, String location) {
        super(name, location, "Sightseeing");
    }

    @Override
    public void displayDetails() {
        System.out.println("Sightseeing: " + name);
        System.out.println("Location: " + location);
    }
}

// Interface for matching users based on preferences
interface MatchService {
    void connectUsers(User user1, User user2);
}

// Concrete class implementing MatchService
class MatchServiceImpl implements MatchService {

    @Override
    public void connectUsers(User user1, User user2) {
        if (user1 == null || user2 == null) {
            throw new IllegalArgumentException("Users cannot be null");
        }
        if (user1.getPreference().equalsIgnoreCase(user2.getPreference())) {
            System.out.println("Match found!");
            System.out.println(user1.getName() + " and " + user2.getName() + " both enjoy " + user1.getPreference());
        } else {
            System.out.println("No match found based on preferences.");
        }
    }
}

// Class representing a User
class User {
    private String name;
    private int age;
    private String preference; // e.g., "Adventure", "Relaxation", "Culture"

    // Constructor
    public User(String name, int age, String preference) {
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty");
        }
        if (age <= 0) {
            throw new IllegalArgumentException("Age must be positive");
        }
        if (preference == null || preference.isEmpty()) {
            throw new IllegalArgumentException("Preference cannot be null or empty");
        }
        this.name = name;
        this.age = age;
        this.preference = preference;
    }

    // Getters
    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public String getPreference() {
        return preference;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", preference='" + preference + '\'' +
                '}';
    }
}

// Class to manage users and places
class Adventura {
    private List<User> users;
    private List<Place> places;
    private MatchService matchService;


    // Constructor
    public Adventura() {
        users = new ArrayList<>();
        places = new ArrayList<>();
        matchService = new MatchServiceImpl();
    }

    // Method to add a user
    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        users.add(user);
    }

    // Method to add a place
    public void addPlace(Place place) {
        if (place == null) {
            throw new IllegalArgumentException("Place cannot be null");
        }
        places.add(place);
    }

    // Method to match users
    public void matchUsers(User user1, User user2) {
        matchService.connectUsers(user1, user2);
    }
    // Method to get users list
    public List<User> getUsers() {
        return users;
    }


    // Method to display all places
    public void displayAllPlaces() {
        if (places.isEmpty()) {
            System.out.println("No places available.");
        } else {
            for (Place place : places) {
                place.displayDetails();
                System.out.println("--------------------");
            }
        }
    }
}

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

        System.out.print("Enter User Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Age: ");
        int age = scanner.nextInt();

        scanner.nextLine(); // Consume the newline

        System.out.print("Enter Preference (e.g., Adventure, Relaxation, Culture): ");
        String preference = scanner.nextLine();

        try {
            User user = new User(name, age, preference);
            adventura.addUser(user);
            System.out.println("User added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void addPlace() {
        scanner.nextLine(); //
        System.out.print("Enter Place Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Location: ");
        String location = scanner.nextLine();

        System.out.print("Is this an Event or Sightseeing? (Enter E for Event, S for Sightseeing): ");
        char type = scanner.nextLine().toUpperCase().charAt(0);

        try {
            if (type == 'E') {
                System.out.print("Enter Event Date: ");
                String date = scanner.nextLine();
                Event event = new Event(name, location, date);
                adventura.addPlace(event);
            } else if (type == 'S') {
                Sightseeing sightseeing = new Sightseeing(name, location);
                adventura.addPlace(sightseeing);
            } else {
                throw new IllegalArgumentException("Invalid type. Must be 'E' for Event or 'S' for Sightseeing.");
            }
            System.out.println("Place added successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void matchUsers() {
        scanner.nextLine(); // Consume the newline

        System.out.print("Enter First User Name: ");
        String user1Name = scanner.nextLine();

        System.out.print("Enter Second User Name: ");
        String user2Name = scanner.nextLine();

        User user1 = null;
        User user2 = null;

        for (User user : adventura.getUsers()) {
            if (user.getName().equalsIgnoreCase(user1Name)) {
                user1 = user;
            }
            if (user.getName().equalsIgnoreCase(user2Name)) {
                user2 = user;
            }
        }

        if (user1 != null && user2 != null) {
            adventura.matchUsers(user1, user2);
        } else {
            System.out.println("One or both users not found.");
        }
    }


    private static void displayAllPlaces() {
        System.out.println("Displaying all places:");
        adventura.displayAllPlaces();
    }
}
