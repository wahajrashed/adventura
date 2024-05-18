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
