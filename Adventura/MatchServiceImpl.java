// Concrete class implementing MatchService
class MatchServiceImpl implements MatchService {
    // Method to connect two users based on their preferences
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
