package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

public class AppointmentQueries {
    public static ObservableList<Appointment> associatedApointments(int CustomerId) throws SQLException {

        ObservableList customerAppointments = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM APPOINTMENTS WHERE customer_ID = ?";
            //String sql = "SELECT * FROM APPOINTMENTS AS a INNER JOIN customers AS c ON a.Customer_ID=c.Customer_ID WHERE a.Customer_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, CustomerId);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                int appointmentId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime startDateTime = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = resultSet.getTimestamp("End").toLocalDateTime();
                int customerID = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");
                Appointment newAppointment = new Appointment(appointmentId, title, description, location, type, startDateTime, endDateTime, customerID, userId, contactId);
                customerAppointments.add(newAppointment);

            }
        }
        catch
        (SQLException ex) {
            ex.printStackTrace();
        }
        return customerAppointments;
    }

    public static ObservableList<Appointment> appointmentsByContactId(int ContactId) throws SQLException {

        ObservableList contactAppointments = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM APPOINTMENTS WHERE contact_ID = ?";
            System.out.println(sql +"  SQL Query");
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, ContactId);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                int appointmentId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime startDateTime = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = resultSet.getTimestamp("End").toLocalDateTime();
                int customerID = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");
                Appointment newAppointment = new Appointment(appointmentId, title, description, location, type, startDateTime, endDateTime, customerID, userId, contactId);
                contactAppointments.add(newAppointment);

            }
        }
        catch
        (SQLException ex) {
            ex.printStackTrace();
        }
        return contactAppointments;
    }

    public static ObservableList<Appointment> appointments() throws SQLException{

        ObservableList<Appointment> appointmentList = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM APPOINTMENTS";
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                int appointmentId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime startDateTime = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = resultSet.getTimestamp("End").toLocalDateTime();
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");
                Appointment newAppointment = new Appointment(appointmentId, title, description, location, type, startDateTime, endDateTime, customerId, userId,contactId);
                appointmentList.add(newAppointment);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return appointmentList;
    }

    public static ObservableList<Appointment> appointmentsByMonth(){

        ObservableList<Appointment> appointmentMonth = FXCollections.observableArrayList();

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusDays(30);

        try{
            String sql = "SELECT * FROM APPOINTMENTS WHERE start>= '" + startDate + "' AND " + "start <= '" + endDate + "' ";
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                int appointmentId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime startDateTime = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = resultSet.getTimestamp("End").toLocalDateTime();
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");
                Appointment newAppointment = new Appointment(appointmentId, title, description, location, type, startDateTime, endDateTime, customerId, userId,contactId);
                appointmentMonth.add(newAppointment);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return appointmentMonth;
    }

    public static ObservableList<Appointment> appointmentsByWeek(){

        ObservableList<Appointment> appointmentWeek = FXCollections.observableArrayList();

        LocalDateTime startDate = LocalDateTime.now();
        LocalDateTime endDate = startDate.plusWeeks(1);

        try{
            String sql = "SELECT * FROM APPOINTMENTS WHERE start>= '" + startDate + "' AND " + "start <= '" + endDate + "' ";
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                int appointmentId = resultSet.getInt("Appointment_ID");
                String title = resultSet.getString("Title");
                String description = resultSet.getString("Description");
                String location = resultSet.getString("Location");
                String type = resultSet.getString("Type");
                LocalDateTime startDateTime = resultSet.getTimestamp("Start").toLocalDateTime();
                LocalDateTime endDateTime = resultSet.getTimestamp("End").toLocalDateTime();
                int customerId = resultSet.getInt("Customer_ID");
                int userId = resultSet.getInt("User_ID");
                int contactId = resultSet.getInt("Contact_ID");
                Appointment newAppointment = new Appointment(appointmentId, title, description, location, type, startDateTime, endDateTime, customerId, userId,contactId);
                appointmentWeek.add(newAppointment);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
            return appointmentWeek;
    }

    public static int deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID=?";
        PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int insertAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) throws SQLException{
        String sql = "INSERT INTO APPOINTMENTS(Title, Description, Location, Type, Start, End, Customer_ID, Uer_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
        ps.setString(1, title);
        ps.setString(2, description);
        ps.setString(3, location);
        ps.setString(4, type);
        ps.setTimestamp(5, Timestamp.valueOf(start));
        ps.setTimestamp(6, Timestamp.valueOf(end));
        ps.setInt(7, customerId);
        ps.setInt(8, userId);
        ps.setInt(9, contactId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}
