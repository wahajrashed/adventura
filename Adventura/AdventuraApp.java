import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

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
        // Update with the correct path for the background image
        BackgroundPanel loginPanel = new BackgroundPanel("background.jpg");
        loginPanel.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel userLabel = new JLabel("Username:");
        JTextField userField = new JTextField(20);
        JLabel passLabel = new JLabel("Password:");
        JPasswordField passField = new JPasswordField(20);

        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginPanel.add(userLabel, gbc);
        gbc.gridx = 1;
        loginPanel.add(userField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        loginPanel.add(passLabel, gbc);
        gbc.gridx = 1;
        loginPanel.add(passField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        loginPanel.add(loginButton, gbc);
        gbc.gridx = 1;
        loginPanel.add(registerButton, gbc);

        // Add action listener for login button
        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                if (authenticate(username, password)) {
                    showMainMenu();
                } else {
                    JOptionPane.showMessageDialog(frame, "Invalid username or password.", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add action listener for register button
        registerButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = userField.getText();
                String password = new String(passField.getPassword());
                if (register(username, password)) {
                    JOptionPane.showMessageDialog(frame, "Registration successful!", "Register", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(frame, "Username already exists.", "Register Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.getContentPane().removeAll();
        frame.getContentPane().add(loginPanel);
        frame.revalidate();
        frame.repaint();
    }

    private static boolean authenticate(String username, String password) {
        return accounts.containsKey(username) && accounts.get(username).equals(password);
    }

    private static boolean register(String username, String password) {
        if (accounts.containsKey(username)) {
            return false;
        } else {
            accounts.put(username, password);
            saveAccounts();
            return true;
        }
    }

    private static void saveAccounts() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ACCOUNTS_FILE))) {
            oos.writeObject(accounts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void loadAccounts() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ACCOUNTS_FILE))) {
            accounts = (Map<String, String>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void showMainMenu() {
        // Create the main menu panel
        JPanel mainMenuPanel = new JPanel(new GridLayout(0, 1));

        JButton addUserButton = new JButton("Add User");
        JButton addPlaceButton = new JButton("Add Place");
        JButton displayPlacesButton = new JButton("Display Places");
        JButton matchUsersButton = new JButton("Match Users");

        // Add action listeners
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddUserScreen();
            }
        });

        addPlaceButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showAddPlaceScreen();
            }
        });

        displayPlacesButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                displayAllPlaces();
            }
        });

        matchUsersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMatchUsersScreen();
            }
        });

        // Add buttons to the panel
        mainMenuPanel.add(addUserButton);
        mainMenuPanel.add(addPlaceButton);
        mainMenuPanel.add(displayPlacesButton);
        mainMenuPanel.add(matchUsersButton);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(mainMenuPanel);
        frame.revalidate();
        frame.repaint();
    }

    private static void showAddUserScreen() {
        JPanel addUserPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField(20);
        JLabel preferenceLabel = new JLabel("Preference:");
        JTextField preferenceField = new JTextField(20);

        JButton saveButton = new JButton("Save");

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        addUserPanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        addUserPanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        addUserPanel.add(ageLabel, gbc);
        gbc.gridx = 1;
        addUserPanel.add(ageField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        addUserPanel.add(preferenceLabel, gbc);
        gbc.gridx = 1;
        addUserPanel.add(preferenceField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 3;
        addUserPanel.add(saveButton, gbc);

        // Add action listener for save button
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                int age = Integer.parseInt(ageField.getText());
                String preference = preferenceField.getText();

                User user = new User(name, age, preference);
                adventura.addUser(user);

                JOptionPane.showMessageDialog(frame, "User added successfully!", "Add User", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.getContentPane().removeAll();
        frame.getContentPane().add(addUserPanel);
        frame.revalidate();
        frame.repaint();
    }

    private static void showAddPlaceScreen() {
        JPanel addPlacePanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField(20);
        JLabel locationLabel = new JLabel("Location:");
        JTextField locationField = new JTextField(20);
        JLabel typeLabel = new JLabel("Type (Event/Sightseeing):");
        JTextField typeField = new JTextField(20);
        JLabel dateLabel = new JLabel("Date (if Event):");
        JTextField dateField = new JTextField(20);

        JButton saveButton = new JButton("Save");

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        addPlacePanel.add(nameLabel, gbc);
        gbc.gridx = 1;
        addPlacePanel.add(nameField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        addPlacePanel.add(locationLabel, gbc);
        gbc.gridx = 1;
        addPlacePanel.add(locationField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        addPlacePanel.add(typeLabel, gbc);
        gbc.gridx = 1;
        addPlacePanel.add(typeField, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        addPlacePanel.add(dateLabel, gbc);
        gbc.gridx = 1;
        addPlacePanel.add(dateField, gbc);
        gbc.gridx = 1;
        gbc.gridy = 4;
        addPlacePanel.add(saveButton, gbc);

        // Add action listener for save button
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String location = locationField.getText();
                String type = typeField.getText();

                Place place;
                if (type.equalsIgnoreCase("Event")) {
                    String date = dateField.getText();
                    place = new Event(name, location, date);
                } else {
                    place = new Sightseeing(name, location);
                }

                adventura.addPlace(place);

                JOptionPane.showMessageDialog(frame, "Place added successfully!", "Add Place", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        frame.getContentPane().removeAll();
        frame.getContentPane().add(addPlacePanel);
        frame.revalidate();
        frame.repaint();
    }

    private static void displayAllPlaces() {
        JPanel displayPlacesPanel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea(20, 40);
        textArea.setEditable(false);

        for (Place place : adventura.getPlaces()) {
            textArea.append(place.getClass().getSimpleName() + ":\n");
            textArea.append("Name: " + place.name + "\n");
            textArea.append("Location: " + place.location + "\n");
            if (place instanceof Event) {
                textArea.append("Date: " + ((Event) place).date + "\n");
            }
            textArea.append("--------------------\n");
        }

        JScrollPane scrollPane = new JScrollPane(textArea);

        displayPlacesPanel.add(scrollPane, BorderLayout.CENTER);

        frame.getContentPane().removeAll();
        frame.getContentPane().add(displayPlacesPanel);
        frame.revalidate();
        frame.repaint();
    }

    private static void showMatchUsersScreen() {
        JPanel matchUsersPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel user1Label = new JLabel("User 1:");
        JTextField user1Field = new JTextField(20);
        JLabel user2Label = new JLabel("User 2:");
        JTextField user2Field = new JTextField(20);

        JButton matchButton = new JButton("Match");

        // Add components to the panel
        gbc.gridx = 0;
        gbc.gridy = 0;
        matchUsersPanel.add(user1Label, gbc);
        gbc.gridx = 1;
        matchUsersPanel.add(user1Field, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        matchUsersPanel.add(user2Label, gbc);
        gbc.gridx = 1;
        matchUsersPanel.add(user2Field, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        matchUsersPanel.add(matchButton, gbc);

        // Add action listener for match button
        matchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String user1Name = user1Field.getText();
                String user2Name = user2Field.getText();

                User user1 = null;
                User user2 = null;

                for (User user : adventura.getUsers()) {
                    if (user.getName().equals(user1Name)) {
                        user1 = user;
                    }
                    if (user.getName().equals(user2Name)) {
                        user2 = user;
                    }
                }

                if (user1 != null && user2 != null) {
                    adventura.matchUsers(user1, user2);
                } else {
                    JOptionPane.showMessageDialog(frame, "One or both users not found.", "Match Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        frame.getContentPane().removeAll();
        frame.getContentPane().add(matchUsersPanel);
        frame.revalidate();
        frame.repaint();
    }
}
