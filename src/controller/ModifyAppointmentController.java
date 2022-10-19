package controller;

import database.AppointmentQueries;
import database.ContactQueries;
import database.UserQueries;
import helper.TimeUtility;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;
import model.Users;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.ParseException;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

/** Modify Appointment controller class. */
public class ModifyAppointmentController implements Initializable {

    public TextField customerIdText;
    public TextField descriptionText;
    public ComboBox<Contact> contactNameComboBox;
    public TextField appointmentIdText;
    public TextField locationText;
    public TextField titleText;
    public TextField typeText;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;
    public ComboBox<LocalTime> startTimeComboBox;
    public ComboBox<LocalTime> endTimeComboBox;

    public Appointment appointmentToModify = null;
    public ComboBox<Users> userIdComboBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactNameComboBox.setItems(ContactQueries.contacts());
            startEndTimeCombos();
            userIdComboBox.setItems(UserQueries.users());

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }


    public void OnCancelButton(ActionEvent actionEvent) throws IOException {

        Alert alert3 = new Alert (Alert.AlertType.CONFIRMATION);
        alert3.setTitle("Confirmation");
        alert3.setHeaderText((null));
        alert3.setContentText("Are you sure you want to cancel?");
        Optional<ButtonType> result = alert3.showAndWait();
        if (alert3.getResult() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.FXML"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void onSaveAppointment(ActionEvent actionEvent) throws SQLException, ParseException {
        boolean noBlankFields = checkValidEntries();
        if(noBlankFields){
            boolean timesValid = checkWithinBusinessHours();
            if(timesValid){
                boolean noOverlapCustomer = checkSchedulingConflicts();

                if (noOverlapCustomer){
                    try{
                        int rowsAffected = AppointmentQueries.updateAppointment(titleText.getText(),descriptionText.getText(), locationText.getText(), typeText.getText(),
                                LocalDateTime.of(startDatePicker.getValue(), startTimeComboBox.getValue()), LocalDateTime.of(endDatePicker.getValue(), endTimeComboBox.getValue()),
                                Integer.parseInt(customerIdText.getText()), userIdComboBox.getValue().getUserId(), contactNameComboBox.getValue().getContactId(), Integer.parseInt(appointmentIdText.getText()));
                        if(rowsAffected >0){

                            Alert alert3 = new Alert (Alert.AlertType.CONFIRMATION);
                            alert3.setTitle("Confirmation");
                            alert3.setHeaderText((null));
                            alert3.setContentText("Appointment has been updated");
                            Optional<ButtonType> result = alert3.showAndWait();
                            if (alert3.getResult() == ButtonType.OK) {
                                Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.FXML"));
                                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                stage.setScene(new Scene(root));
                                stage.show();
                            }
                        }
                        else{
                            Alert alert2 = new Alert (Alert.AlertType.ERROR);
                            alert2.setTitle("ERROR");
                            alert2.setHeaderText("CANNOT UPDATE APPOINTMENT");
                            alert2.setContentText("Sorry: Unable to update appointment at this time. Please try again.");
                            alert2.showAndWait();
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
            }
        }
    }





    public void sendAppointment(Appointment appointment) throws SQLException {

        appointmentToModify = appointment;
        customerIdText.setText(String.valueOf(appointment.getCustomerId()));
        descriptionText.setText(appointment.getDescription());
        userIdComboBox.setValue(appointment.getUserId());
        contactNameComboBox.setValue(appointment.getContactId());
        appointmentIdText.setText(String.valueOf(appointment.getAppointmentId()));
        locationText.setText(appointment.getLocation());
        titleText.setText(appointment.getTitle());
        typeText.setText(appointment.getType());
        startDatePicker.setValue(appointment.getStartDateTime().toLocalDate());
        endDatePicker.setValue(appointment.getEndDateTime().toLocalDate());
        startTimeComboBox.setValue(appointment.getStartDateTime().toLocalTime());
        endTimeComboBox.setValue(appointment.getEndDateTime().toLocalTime());
    }


    private void startEndTimeCombos() {

        startTimeComboBox.setItems(TimeUtility.getStartEndTime());
        endTimeComboBox.setItems(TimeUtility.getStartEndTime());

    }


    private boolean checkWithinBusinessHours() {
        LocalTime startTime = startTimeComboBox.getValue();
        LocalTime endTime = endTimeComboBox.getValue();

        DayOfWeek dWeek = startDatePicker.getValue().getDayOfWeek();

        if(dWeek == DayOfWeek.SATURDAY || dWeek == DayOfWeek.SUNDAY){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("INVALID DATES");
            alert.setContentText("Appointments can only be scheduled Monday-Friday");
            alert.showAndWait();
            return false;
        }
        if(startDatePicker.getValue().isBefore(endDatePicker.getValue()) || startDatePicker.getValue().isAfter(endDatePicker.getValue())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("INVALID DATES");
            alert.setContentText("Start date and end date must be the same");
            alert.showAndWait();
            return false;
        }
        if(startDatePicker.getValue().isBefore(LocalDate.now())){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("INVALID DATES");
            alert.setContentText("You cannot schedule appointments for dates prior to today.");
            alert.showAndWait();
            return false;
        }
        if(startTime.isAfter(endTime)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("INVALID TIMES");
            alert.setContentText("Start time must be before end time");
            alert.showAndWait();
            return false;
        }
        if(startTime.equals(endTime)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("INVALID TIMES");
            alert.setContentText("Start time cannot be the same as end time");
            alert.showAndWait();
            return false;
        }
        return true;
    }


    boolean checkSchedulingConflicts() throws SQLException {

        LocalTime newStartTime = startTimeComboBox.getValue();
        LocalTime newEndTime = endTimeComboBox.getValue();

        LocalDate newStartDate = startDatePicker.getValue();
        LocalDate newEndDate = endDatePicker.getValue();

        LocalDateTime newStartDT = LocalDateTime.of(newStartDate, newStartTime);
        LocalDateTime newEndDT = LocalDateTime.of(newEndDate, newEndTime);

        int newAppointmentId = Integer.parseInt(appointmentIdText.getText());
        ObservableList<Appointment> customerAppointments = AppointmentQueries.associatedApointments(Integer.parseInt(customerIdText.getText()));

        LocalDateTime existingAppointmentStart;
        LocalDateTime existingAppointmentEnd;

        for(Appointment appointment: customerAppointments) {
            if (appointment.getAppointmentId() == newAppointmentId) {
                continue;
            }

            existingAppointmentStart = appointment.getStartDateTime();
            existingAppointmentEnd = appointment.getEndDateTime();

            if (newStartDT.isAfter(existingAppointmentStart) && newStartDT.isBefore(existingAppointmentEnd)) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setHeaderText("2: OVERLAP");
                alert2.setContentText("Customer already has appointment during this time");
                alert2.showAndWait();
                return false;
            } else if (newEndDT.isAfter(existingAppointmentStart) && newEndDT.isBefore(existingAppointmentEnd)) {
                Alert alert2 = new Alert(Alert.AlertType.ERROR);
                alert2.setTitle("Error");
                alert2.setHeaderText("3: OVERLAP");
                alert2.setContentText("Customer already has appointment during this time");
                alert2.showAndWait();
                return false;
            }
        }
        return true;
    }



    private boolean checkValidEntries(){
        if(customerIdText.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("NO CUSTOMER SELECTED");
            alert.setContentText("Please select the customer from the table to schedule an appointment for.");
            alert.showAndWait();
            return false;
        }
        if(descriptionText.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("NO DESCRIPTION");
            alert.setContentText("Description field cannot be blank.");
            alert.showAndWait();
            return false;
        }
        if(userIdComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("NO USER ID");
            alert.setContentText("User ID field cannot be blank.");
            alert.showAndWait();
            return false;
        }
        if(contactNameComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("NO CONTACT SELECTED");
            alert.setContentText("Please select a contact name.");
            alert.showAndWait();
            return false;
        }
        if(locationText.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("NO LOCATION");
            alert.setContentText("Location field cannot be blank.");
            alert.showAndWait();
            return false;
        }
        if(titleText.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("NO TITLE");
            alert.setContentText("Title field cannot be blank.");
            alert.showAndWait();
            return false;
        }
        if(typeText.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("NO TYPE");
            alert.setContentText("Type field cannot be blank.");
            alert.showAndWait();
            return false;
        }
        if(startDatePicker.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("NO START DATE");
            alert.setContentText("Please select a start date.");
            alert.showAndWait();
            return false;
        }
        if(endDatePicker.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("NO END DATE");
            alert.setContentText("Please select an end date.");
            alert.showAndWait();
            return false;
        }
        if(startTimeComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("NO START TIME");
            alert.setContentText("Please select a start time.");
            alert.showAndWait();
            return false;
        }
        if(endTimeComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("NO END TIME");
            alert.setContentText("Please select an end time.");
            alert.showAndWait();
            return false;
        }
        return true;
    }

}
