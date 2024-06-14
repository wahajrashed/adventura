import java.io.Serializable;

// Abstract class representing a Place (Event or Sightseeing)
abstract class Place implements Serializable {
    protected String name;
    protected String location;
    protected String type;

    // Constructor to initialize the Place with name, location, and type
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

    // Abstract method to display details of the place
    public abstract void displayDetails();
}
