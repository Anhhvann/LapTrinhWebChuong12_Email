package murach.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import murach.business.User;

public class UserDB {

    public static void insert(User user) {
        String sql = "INSERT INTO users (email, firstName, lastName) VALUES (?, ?, ?)";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            
            int rowsInserted = ps.executeUpdate();
            System.out.println("✓ User inserted: " + rowsInserted + " row(s) affected");
        } catch (SQLException e) {
            System.err.println("✗ Error inserting user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void update(User user) {
        String sql = "UPDATE users SET email = ?, firstName = ?, lastName = ? WHERE userId = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, user.getEmail());
            ps.setString(2, user.getFirstName());
            ps.setString(3, user.getLastName());
            ps.setLong(4, user.getUserId());
            
            int rowsUpdated = ps.executeUpdate();
            System.out.println("✓ User updated: " + rowsUpdated + " row(s) affected");
        } catch (SQLException e) {
            System.err.println("✗ Error updating user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void delete(User user) {
        String sql = "DELETE FROM users WHERE userId = ?";
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setLong(1, user.getUserId());
            int rowsDeleted = ps.executeUpdate();
            System.out.println("✓ User deleted: " + rowsDeleted + " row(s) affected");
        } catch (SQLException e) {
            System.err.println("✗ Error deleting user: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static User selectUser(String email) {
        String sql = "SELECT userId, email, firstName, lastName FROM users WHERE email = ?";
        User user = null;
        
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, email);
            System.out.println("Searching for user with email: " + email);
            
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    user = new User();
                    user.setUserId(rs.getLong("userId"));
                    user.setEmail(rs.getString("email"));
                    user.setFirstName(rs.getString("firstName"));
                    user.setLastName(rs.getString("lastName"));
                    System.out.println("✓ User found: " + user.getEmail());
                } else {
                    System.out.println("✓ No user found with email: " + email);
                }
            }
        } catch (SQLException e) {
            System.err.println("✗ Error selecting user: " + e.getMessage());
            e.printStackTrace();
        }
        
        return user;
    }

    public static boolean emailExists(String email) {
        User u = selectUser(email);   
        return u != null;
    }
}