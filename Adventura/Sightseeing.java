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
