// Concrete class representing a Sightseeing location
class Sightseeing extends Place {
    // Constructor to initialize the Sightseeing with name and location
    public Sightseeing(String name, String location) {
        super(name, location, "Sightseeing");
    }

    // Overridden method to display details of the Sightseeing
    @Override
    public void displayDetails() {
        System.out.println("Sightseeing: " + name);
        System.out.println("Location: " + location);
    }
}
