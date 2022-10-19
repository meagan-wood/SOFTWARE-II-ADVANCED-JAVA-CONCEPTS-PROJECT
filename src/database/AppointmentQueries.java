package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/** Appointment queries class. */
public class AppointmentQueries {

    /** Associated appointments query. Database query returns appointments associated to customer by customer ID.
     * Catches exceptions, prints stacktrace
     * @return customerAppointments observable list
     * @throws SQLException SQLException
     */
    public static ObservableList<Appointment> associatedApointments(int CustomerId) throws SQLException {

        ObservableList customerAppointments = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM APPOINTMENTS WHERE customer_ID = ?";
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

    /** LAMBDA EXPRESSION, appointments by contact ID. Database query returns appointments and Lambda finds the appointments with specified contact ID.
     * Catches exceptions, prints stacktrace
     * @return appointments for contact selected
     * @throws SQLException SQLException
     * @param ContactId contact id
     */
   public static ObservableList<Appointment> appointmentsByContactId(int ContactId) throws SQLException {
       ObservableList<Appointment> contactAppointments = FXCollections.observableArrayList();
       try{
           String sql = "SELECT * FROM APPOINTMENTS";
           PreparedStatement ps = JDBC.connection.prepareStatement(sql);
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
       /*
         LAMBDA EXPRESSION
        */
       ObservableList<Appointment> appointmentsForContact = contactAppointments.filtered(a ->{
           if(a.getId() == ContactId)
               return true;
           return false;
       });
       return appointmentsForContact;
   }

    /** All appointments query. Database query returns all appointments.
     * Catches exceptions, prints stacktrace
     * @return all appointments
     * @throws SQLException SQLException
     */
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

    /** All appointments for selected month query.
     * Catches exceptions, prints stacktrace
     * @return appointments for the month selected
     */
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

    /** Appointments for the week query. Database query returns appointments for current week.
     * Catches exceptions, prints stacktrace
     * @return the weeks appointments
     */
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

    /** Deletes appointment query. Database query deletes the selected appointment.
     * Catches exceptions, prints stacktrace
     * @return rows affected
     */
    public static int deleteAppointment(int appointmentId) throws SQLException {
        String sql = "DELETE FROM APPOINTMENTS WHERE Appointment_ID=?";
        PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
        ps.setInt(1, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** Insert appointments query. Database query insert appointment data.
     * Catches exceptions, prints stacktrace
     * @return rows affected
     * @throws SQLException SQLException
     * @param contactId contact id
     * @param customerId customer id
     * @param description description
     * @param end end date time
     * @param location location
     * @param start start date time
     * @param title title
     * @param type type
     * @param userId user id
     */
    public static int insertAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId) throws SQLException{
        String sql = "INSERT INTO APPOINTMENTS(Title, Description, Location, Type, Start, End, Customer_ID, User_ID, Contact_ID) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
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

    /** Update appointments query. Database query to update appointment data at given appointment id.
     * Catches exceptions, prints stacktrace
     * @return rows affected
     * @throws SQLException SQLException
     * @param contactId contact id
     * @param customerId customer id
     * @param description description
     * @param end end date time
     * @param location location
     * @param start start date time
     * @param title title
     * @param type type
     * @param userId user id
     * @param appointmentId appointment id
     */
    public static int updateAppointment(String title, String description, String location, String type, LocalDateTime start, LocalDateTime end, int customerId, int userId, int contactId, int appointmentId) throws SQLException {

        String sql = "UPDATE Appointments SET Title=?, Description=?, Location=?, Type=?, Start=?, End=?, Customer_ID=?, User_ID=?, Contact_ID=? WHERE Appointment_ID=?";

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
        ps.setInt(10, appointmentId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    /** Gets appointments for specific month query.
    * Catches exceptions, prints stacktrace
    * @return observable list of appointments for given month
    * @param monthId month id
    */
    public static ObservableList<Appointment> appointmentsByMonthName(int monthId) {
        ObservableList<Appointment> monthlyAppointments = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM APPOINTMENTS WHERE month(start) = ? "; //AND
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ps.setInt(1,monthId);
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
                monthlyAppointments.add(newAppointment);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return monthlyAppointments;
    }

    /** Gets appointments scheduled by specific user.
     * Catches exceptions, prints stacktrace
     * @return observable list of appointments scheduled by specific user
     * @param userID user id
     */
    public static ObservableList<Appointment> appointmentsByUser(int userID) {

        ObservableList<Appointment> userAppointments = FXCollections.observableArrayList();

        try {
            String sql = "SELECT * FROM APPOINTMENTS WHERE USER_ID = ? ";
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ps.setInt(1, userID);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
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
                Appointment newAppointment = new Appointment(appointmentId, title, description, location, type, startDateTime, endDateTime, customerId, userId, contactId);
                userAppointments.add(newAppointment);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return userAppointments;
    }

}
