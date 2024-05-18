Adventura - A Travel Companion App
Adventura is a Java application designed to manage users and places. It provides a simple interface for users to add themselves, specify their preferences, add places they want to visit, and match with other users based on their preferences.

Getting Started
To get started with Adventura, follow these steps:

Clone the repository to your local machine:

bash
Copy code
git clone https://github.com/wahajrashed/adventura.git

Open the project in your preferred Java IDE.

Compile and run the AdventuraApp class.

Usage
Adventura provides the following features:

Adding Users: Users can be added to the system by providing their name, age, and preference.
Adding Places: Places can be added as events or sightseeing locations, along with their relevant details.
Matching Users: Users can be matched based on their preferences to find common interests.
Displaying Places: All places stored in the system can be displayed along with their details.
To use these features, simply follow the prompts in the application's menu-driven interface.

Classes
The Adventura application consists of the following classes:

Place: Represents a generic place with attributes like name, location, and type.
Event: Represents a specific event with a date attribute. Subclass of Place.
Sightseeing: Represents a sightseeing location. Subclass of Place.
MatchService: Interface for matching users based on preferences.
User: Represents a user with attributes like name, age, and preference.
Adventura: Manages users and places within the application. Provides methods for adding users and places, matching users, and displaying places.
AdventuraApp: Main class for running the application. Provides a menu-driven interface for user interaction.
Contributing
Contributions are welcome! If you have any suggestions, improvements, or bug fixes, feel free to open an issue or create a pull request.


This project is licensed under the MIT License.
