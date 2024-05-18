import java.util.ArrayList;
import java.util.List;

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
