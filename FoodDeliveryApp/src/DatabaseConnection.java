import java.sql.*;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/my_database";
    private static final String USER = "root"; // Change if you have a different username
    private static final String PASSWORD = "<>?f5abc30v5";

    public static void main(String[] args) {
        try {
            // Load MySQL JDBC Driver (optional in newer versions)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establish connection
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected successfully!");

            String query = "SELECT * FROM users WHERE id = ?";

            int userId = 1;

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, userId);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("User Found: " + resultSet.getString("name") + " (" + resultSet.getString("email") + ")");
            } else {
                System.out.println("User not found.");
            }

            // Close connection
            connection.close();
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }
}
