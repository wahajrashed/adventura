import java.io.Serializable;

// Class representing a User
class User implements Serializable {
    private String name;
    private int age;
    private String preference;

    // Constructor to initialize the User with name, age, and preference
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

    // Getter for name
    public String getName() {
        return name;
    }

    // Getter for age
    public int getAge() {
        return age;
    }

    // Getter for preference
    public String getPreference() {
        return preference;
    }

    // Overridden toString method to return a string representation of the User
    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", preference='" + preference + '\'' +
                '}';
    }
}
