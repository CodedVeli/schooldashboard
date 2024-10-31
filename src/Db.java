// connecting to the database 
// This is school dashboard user can add or drop a unit user and units that when a user drops units they are only disassociated with the user not delete  also the there relationship of the grades with user and unit  also a create a relationship of user  and create fees table whether paid or not paid by user 
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Db {
    private static final String DATABASE_URL = "jdbc:mysql://localhost/school";
	private static final String DATABASE_USERNAME = "root";
	private static final String DATABASE_PASSWORD = "root";
    private static final String REGISTER_QUERY = "INSERT INTO users (username, password, email, is_admin) VALUES (?, ?, ?, ?)";
    private static final String LOGIN_QUERY = "SELECT * FROM users WHERE email = ? AND password = ?";
    private static final String CREATE_UNITS = "INSERT INTO units (code, name) VALUES (?, ?)";
    private static final String ADD_UNITS_FOR_STUDENTS = "INSERT INTO user_units (user_id, unit_id, status) VALUES (?, ?, ?)";
    private static final String READ_UNITS_FOR_USERS = "SELECT * FROM units JOIN user_units ON units.id = user_units.unit_id WHERE user_units.user_id = ?";
    private static final String DROP_UNITS_FOR_STUDENTS = "UPDATE user_units SET status = FALSE WHERE user_id = ? AND unit_id = ?";
    private static final String CREATE_GRADES = "INSERT INTO grades (user_id, unit_id, grade) VALUES (?, ?, ?)";
    private static final String READ_GRADES_FOR_USERS = "SELECT g.grade_id, u.code, u.name, g.grade FROM grades g JOIN units u ON g.unit_id = u.unit_id WHERE g.user_id = ?";
    private static final String CREATE_FEES = "INSERT INTO fees (user_id,  paid) VALUES (?,?)";
    private static final String READ_FEES_FOR_USERS = "SELECT * FROM fees WHERE user_id = ?";
    private static final String UPDATE_FEES_FOR_USERS = "UPDATE fees SET paid = TRUE WHERE user_id = ?";


    public void register(String username, String password, String email, boolean isAdmin) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(REGISTER_QUERY)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setBoolean(4, isAdmin);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
  // the login should check if is admin to redirect to the admin page or user page
    public LoginResult login(String email, String password) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement stmt = connection.prepareStatement(LOGIN_QUERY)) {
    
            stmt.setString(1, email);
            stmt.setString(2, password);
    
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                int userId = rs.getInt("id"); // Assuming "id" is the column name for user id
                boolean isAdmin = rs.getBoolean("is_admin");
                return new LoginResult(userId, isAdmin); // Return both userId and admin status
            } else {
                return null; // Login failed
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
    public void createUnits(String code, String name) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_UNITS)) {
            preparedStatement.setString(1, code);
            preparedStatement.setString(2, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addUnitsForStudents(int userId, int unitId, boolean status) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(ADD_UNITS_FOR_STUDENTS)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, unitId);
            preparedStatement.setBoolean(3, status);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    
    public List<String> readUnitsForUsers(int userId) {
        List<String> units = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(READ_UNITS_FOR_USERS)) {
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                units.add(rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return units;
    }
    public void dropUnitsForStudents(int userId, int unitId) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DROP_UNITS_FOR_STUDENTS)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, unitId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void createGrades(int userId, int unitId, String grade) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_GRADES)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, unitId);
            preparedStatement.setString(3, grade);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> readGradesForUsers(int userId) {
        List<String> grades = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(READ_GRADES_FOR_USERS)) {
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                grades.add(rs.getString("grade"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return grades;
    }

    public void createFees(int userId, boolean paid) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(CREATE_FEES)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.setBoolean(2, paid);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Boolean> readFeesForUsers(int userId) {
        List<Boolean> fees = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(READ_FEES_FOR_USERS)) {
            preparedStatement.setInt(1, userId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                fees.add(rs.getBoolean("paid"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fees;
    }

    public void updateFeesForUsers(int userId) {
        try (Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_FEES_FOR_USERS)) {
            preparedStatement.setInt(1, userId);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void printSQLException(SQLException ex) {
        for (Throwable e: ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public static void main(String[] args) {
        Db db = new Db();
        db.register("admin", "admin", "1234", true);

        db.createUnits("COMP101", "Introduction to Computer Science");
        db.createUnits("COMP102", "Data Structures and Algorithms");

    }

}
    