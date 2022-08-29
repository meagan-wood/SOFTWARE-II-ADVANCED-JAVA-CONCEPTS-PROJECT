package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Appointment;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class AppointmentQueries {
    public static ObservableList<Appointment> associatedApointments(int CustomerId) throws SQLException {

        ObservableList customerAppointments = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM APPOINTMENTS AS a INNER JOIN customers AS c ON a.Customer_ID=c.Customer_ID WHERE a.Customer_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, CustomerId);
            System.out.println(CustomerId);
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
}
