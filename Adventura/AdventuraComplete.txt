import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Abstract class representing a Place (Event or Sightseeing)
abstract class Place implements Serializable {
    protected String name;
    protected String location;
    protected String type;

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

    public abstract void displayDetails();
}

// Concrete class representing an Event
class Event extends Place {
    private String date;

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
class User implements Serializable {
    private String name;
    private int age;
    private String preference;

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
    private static final String USERS_FILE = "users.dat";
    private static final String PLACES_FILE = "places.dat";

    public Adventura() {
        users = new ArrayList<>();
        places = new ArrayList<>();
        matchService = new MatchServiceImpl();
        loadUsers();
        loadPlaces();
    }

    public void addUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("User cannot be null");
        }
        users.add(user);
        saveUsers();
    }

    public void addPlace(Place place) {
        if (place == null) {
            throw new IllegalArgumentException("Place cannot be null");
        }
        places.add(place);
        savePlaces();
    }

    public void matchUsers(User user1, User user2) {
        matchService.connectUsers(user1, user2);
    }

    public List<User> getUsers() {
        return users;
    }

    public List<Place> getPlaces() {
        return places;
    }

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

    private void saveUsers() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(USERS_FILE))) {
            oos.writeObject(users);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void savePlaces() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(PLACES_FILE))) {
            oos.writeObject(places);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(USERS_FILE))) {
            users = (List<User>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void loadPlaces() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(PLACES_FILE))) {
            places = (List<Place>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}

// Custom JPanel to paint the background image
class BackgroundPanel extends JPanel {
    private BufferedImage image;

    public BackgroundPanel(String filePath) {
        try {
            image = ImageIO.read(new File(filePath));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), this);
    }
}

// Main class to run the application with GUI
public class AdventuraApp {
    private static Adventura adventura = new Adventura();
    private static Map<String, String> accounts = new HashMap<>();
    private static JFrame frame;
    private static final String ACCOUNTS_FILE = "accounts.dat";

    public static void main(String[] args) {
        loadAccounts();

        // Create the main frame
        frame = new JFrame("Adventura App");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        showLoginScreen();

        frame.setVisible(true);
    }

    private static void showLoginScreen() {
        BackgroundPanel panel = new BackgroundPanel("C:/Users/DELL/pexels-christianzanemedia-11589110.jpg"); // Update with the correct path
        panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel welcomeLabel = new JLabel("WELCOME TO ADVENTURA, your travel companion", SwingConstants.CENTER);
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 20));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(welcomeLabel, gbc);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setForeground(Color.WHITE);
        usernameLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        panel.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField();
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setForeground(Color.WHITE);
        passwordLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        gbc.gridx = 0;
        gbc.gridy = 2;
        panel.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        JButton loginButton = new JButton("Login");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        panel.add(loginButton, gbc);

        JButton registerButton = new JButton("Register");
        gbc.gridx = 1;
        gbc.gridy = 3;
        panel.add(registerButton, gbc);

        frame.setContentPane(panel);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (accounts.containsKey(username) && accounts.get(username).equals(password)) {
                    showMainScreen();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.");
                }
            }
        });

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());
                if (accounts.containsKey(username)) {
                    JOptionPane.showMessageDialog(frame, "Username already exists.");
                } else {
                    accounts.put(username, password);
                    saveAccounts();
                    JOptionPane.showMessageDialog(frame, "Account created successfully!");
                }
            }
        });

        frame.revalidate();
        frame.repaint();
    }

    private static void showMainScreen() {
        BackgroundPanel panel = new BackgroundPanel("C:/Users/DELL/pexels-christianzanemedia-11589110.jpg"); // Update with the correct path
        panel.setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // User tab
        JPanel userPanel = new JPanel();
        userPanel.setOpaque(false);
        userPanel.setLayout(new GridLayout(2, 1));
        JButton addUserButton = new JButton("Add User");
        JButton matchUsersButton = new JButton("Match Users");
        userPanel.add(addUserButton);
        userPanel.add(matchUsersButton);
        tabbedPane.addTab("Users", userPanel);

        // Places tab
        JPanel placesPanel = new JPanel();
        placesPanel.setOpaque(false);
        placesPanel.setLayout(new GridLayout(2, 1));
        JButton addPlaceButton = new JButton("Add Place");
        JButton displayPlacesButton = new JButton("Display Places");
        placesPanel.add(addPlaceButton);
        placesPanel.add(displayPlacesButton);
        tabbedPane.addTab("Places", placesPanel);

        panel.add(tabbedPane, BorderLayout.CENTER);

        frame.setContentPane(panel);

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddUserDialog();
            }
        });

        addPlaceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAddPlaceDialog();
            }
        });

        matchUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showMatchUsersDialog();
            }
        });

        displayPlacesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adventura.displayAllPlaces();
            }
        });

        frame.revalidate();
        frame.repaint();
    }

    private static void showAddUserDialog() {
        JDialog addUserDialog = new JDialog(frame, "Add User", true);
        addUserDialog.setSize(300, 200);

        Container addUserPane = addUserDialog.getContentPane();
        addUserPane.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        JLabel preferenceLabel = new JLabel("Preference:");
        JTextField preferenceField = new JTextField();
        JButton submitButton = new JButton("Submit");

        addUserPane.add(nameLabel);
        addUserPane.add(nameField);
        addUserPane.add(ageLabel);
        addUserPane.add(ageField);
        addUserPane.add(preferenceLabel);
        addUserPane.add(preferenceField);
        addUserPane.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    int age = Integer.parseInt(ageField.getText());
                    String preference = preferenceField.getText();
                    User user = new User(name, age, preference);
                    adventura.addUser(user);
                    JOptionPane.showMessageDialog(addUserDialog, "User added successfully!");
                    addUserDialog.dispose();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(addUserDialog, ex.getMessage());
                }
            }
        });

        addUserDialog.setVisible(true);
    }

    private static void showAddPlaceDialog() {
        JDialog addPlaceDialog = new JDialog(frame, "Add Place", true);
        addPlaceDialog.setSize(300, 200);

        Container addPlacePane = addPlaceDialog.getContentPane();
        addPlacePane.setLayout(new GridLayout(5, 2));

        JLabel nameLabel = new JLabel("Place Name:");
        JTextField nameField = new JTextField();
        JLabel locationLabel = new JLabel("Location:");
        JTextField locationField = new JTextField();
        JLabel typeLabel = new JLabel("Type (E for Event, S for Sightseeing):");
        JTextField typeField = new JTextField();
        JLabel dateLabel = new JLabel("Date (for Event):");
        JTextField dateField = new JTextField();
        JButton submitButton = new JButton("Submit");

        addPlacePane.add(nameLabel);
        addPlacePane.add(nameField);
        addPlacePane.add(locationLabel);
        addPlacePane.add(locationField);
        addPlacePane.add(typeLabel);
        addPlacePane.add(typeField);
        addPlacePane.add(dateLabel);
        addPlacePane.add(dateField);
        addPlacePane.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    String name = nameField.getText();
                    String location = locationField.getText();
                    char type = typeField.getText().toUpperCase().charAt(0);
                    if (type == 'E') {
                        String date = dateField.getText();
                        Event event = new Event(name, location, date);
                        adventura.addPlace(event);
                    } else if (type == 'S') {
                        Sightseeing sightseeing = new Sightseeing(name, location);
                        adventura.addPlace(sightseeing);
                    } else {
                        throw new IllegalArgumentException("Invalid type. Must be 'E' for Event or 'S' for Sightseeing.");
                    }
                    JOptionPane.showMessageDialog(addPlaceDialog, "Place added successfully!");
                    addPlaceDialog.dispose();
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(addPlaceDialog, ex.getMessage());
                }
            }
        });

        addPlaceDialog.setVisible(true);
    }

    private static void showMatchUsersDialog() {
        JDialog matchUsersDialog = new JDialog(frame, "Match Users", true);
        matchUsersDialog.setSize(300, 200);

        Container matchUsersPane = matchUsersDialog.getContentPane();
        matchUsersPane.setLayout(new GridLayout(3, 2));

        JLabel user1Label = new JLabel("User 1 Name:");
        JTextField user1Field = new JTextField();
        JLabel user2Label = new JLabel("User 2 Name:");
        JTextField user2Field = new JTextField();
        JButton submitButton = new JButton("Submit");

        matchUsersPane.add(user1Label);
        matchUsersPane.add(user1Field);
        matchUsersPane.add(user2Label);
        matchUsersPane.add(user2Field);
        matchUsersPane.add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String user1Name = user1Field.getText();
                String user2Name = user2Field.getText();
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
                    JOptionPane.showMessageDialog(matchUsersDialog, "Match found! Check console for details.");
                } else {
                    JOptionPane.showMessageDialog(matchUsersDialog, "One or both users not found.");
                }
            }
        });

        matchUsersDialog.setVisible(true);
    }

    private static void loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ACCOUNTS_FILE))) {
            accounts = (Map<String, String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ACCOUNTS_FILE))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

