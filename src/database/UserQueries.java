package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import model.Contact;
import model.Users;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class UserQueries {

    public static ObservableList<Users> users() throws SQLException {

        ObservableList<Users> userNames = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM users";
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                String userName = resultSet.getString("User_Name");
                Integer userId = resultSet.getInt("User_ID");
                String userPassword = resultSet.getString("Password");
                Users newUser = new Users(userId, userName, userPassword);
                userNames.add(newUser);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return userNames;
    }

    public static Users usersById (int userId) throws SQLException {
        try{
            String sql = "SELECT * FROM users WHERE user_ID=?";
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ps.setInt(1, userId);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                Integer userID = resultSet.getInt("User_ID");
                String userName = resultSet.getString("User_Name");
                String password = resultSet.getString("Password");
                Users newUser = new Users(userID, userName, password);
                return newUser;
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return null;
    }

    public static ObservableList<Users> userLoginsByMonth(int userId) {
        ObservableList<Users> userLogins = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM USERS WHERE month(start) = ? ";
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ps.setInt(1,userId);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                int userID = resultSet.getInt("User_ID");
                String userName = resultSet.getString("UserName");
                String password = resultSet.getString("Password");
                Users newUserLogins = new Users(userId , userName, password);
                userLogins.add(newUserLogins);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return userLogins;
    }
}
