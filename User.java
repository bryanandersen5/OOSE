import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;
import java.security.MessageDigest;

public class User {

    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/Stock_Trading_Management_System";
    private static final String DB_USER = "root";  // Replace with your database username
    private static final String DB_PASSWORD = "2025Spring"; // Replace with your database password

    private String username;
    private String name;
    private String password;
    private String email;
    private String securityQuestion;
    private String securityAnswer;
    private String phoneNumber;

    public User() {
    }

    public User(String username, String password, String name, String email, String securityQuestion, String securityAnswer, String phoneNumber) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.email = email;
        this.securityQuestion = securityQuestion;
        this.securityAnswer = securityAnswer;
        this.phoneNumber = phoneNumber;
    }

    // Getters and Setters
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getSecurityQuestion() {
        return securityQuestion;
    }
    public void setSecurityQuestion(String securityQuestion) {
        this.securityQuestion = securityQuestion;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSecurityAnswer() {
        return securityAnswer;
    }
    public void setSecurityAnswer(String securityAnswer) {
        this.securityAnswer = securityAnswer;
    }

    public boolean signUp(User user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            // Check if username already exists
            String checkQuery = "SELECT * FROM USER WHERE username = ?";
            PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
            checkStatement.setString(1, user.getUsername());
            ResultSet resultSet = checkStatement.executeQuery();

            if (resultSet.next()) {
                System.out.println("Username already exists. Please choose another username.");
                return false;
            }

            // Hash the password before storing
            String hashedPassword = hashPassword(user.getPassword());

            String query = "INSERT INTO USER (username, password, name, email, securityQuestion, securityAnswer, phoneNumber) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            statement.setString(2, hashedPassword);
            statement.setString(3, user.getName());
            statement.setString(4, user.getEmail());
            statement.setString(5, user.getSecurityQuestion());
            statement.setString(6, user.getSecurityAnswer());
            statement.setString(7, user.getPhoneNumber());

            int result = statement.executeUpdate();
            if (result > 0) {
                System.out.println("User registered successfully!");
                return true;
            } else {
                System.out.println("Registration failed.");
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public boolean login(User user) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

            String query = "SELECT * FROM USER WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, user.getUsername());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                // Compare hashed passwords
                String storedHash = resultSet.getString("password");
                String inputHash = hashPassword(user.getPassword());

                if (storedHash.equals(inputHash)) {
                    System.out.println("Login successful. Welcome, " + resultSet.getString("name") + "!");
                    return true;
                } else {
                    System.out.println("Incorrect password.");
                    return false;
                }
            } else {
                System.out.println("Username not found.");
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    private String hashPassword(String password) throws SQLException {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hashBytes = digest.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hashBytes) {
                sb.append(Integer.toHexString(0xFF & b));
            }
            return sb.toString();
        } catch (Exception e) {
            throw new SQLException("Error hashing password: " + e.getMessage());
        }
    }

}
