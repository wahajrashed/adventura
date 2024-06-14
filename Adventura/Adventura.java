import java.io.*;
import java.util.ArrayList;
import java.util.List;

// Class to manage users and places
class Adventura {
    private List<User> users;
    private List<Place> places;
    private MatchService matchService;
    private static final String USERS_FILE = "users.dat";
    private static final String PLACES_FILE = "places.dat";

    // Constructor to initialize Adventura, load users and places from files
    public Adventura() {
        users = new ArrayList<>();
        places = new ArrayList<>();
        matchService = new MatchServiceImpl();
        loadUsers();
        loadPlaces();
    }

    // Method to add a user to the list and save users to file
    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        users.add(user);
        saveUsers();
    }

    // Method to add a place to the list and save places to file
    public void addPlace(Place place) {
        if (place == null) {
            throw new IllegalArgumentException("Place cannot be null");
        }
        places.add(place);
        savePlaces();
    }

    // Method to match two users based on their preferences
    public void matchUsers(User user1, User user2) {
        matchService.connectUsers(user1, user2);
    }

    // Getter for users
    public List<User> getUsers() {
        return users;
    }

    // Getter for places
    public List<Place> getPlaces() {
        return places;
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

    // Method to save users to file
    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to save places to file
    private void savePlaces() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PLACES_FILE))) {
            oos.writeObject(places);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to load users from file
    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // Method to load places from file
    private void loadPlaces() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PLACES_FILE))) {
            places = (List<Place>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
