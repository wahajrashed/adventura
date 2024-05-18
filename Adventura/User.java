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
