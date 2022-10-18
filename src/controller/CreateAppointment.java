package controller;

import database.AppointmentQueries;
import database.ContactQueries;
import database.CustomerQueries;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

/** Create Appointment controller class*/
public class CreateAppointment implements Initializable {

    public TableView custCreateAppTable;
    public TableColumn nameColumn;
    public TableColumn phoneColumn;
    public TableColumn addressColumn;
    public TableColumn countryColumn;
    public TableColumn stateColumn;
    public TableColumn zipcodeColumn;
    public TableColumn customerIdColumn;
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
    public ComboBox<Users> userIdComboBox;

    /** Initialize new appointment form. Loads the country and state combo boxes, loads start/end time combo boxes, and sets existing customer table
     * Catches exceptions, prints stacktrace
     * @param resourceBundle resource bundle
     * @param url url
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            custCreateAppTable.setItems(CustomerQueries.existingCustomers());
            nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
            countryColumn.setCellValueFactory(new PropertyValueFactory<Country, String>("country"));
            stateColumn.setCellValueFactory(new PropertyValueFactory<Division, String>("divisionName"));
            zipcodeColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
            //divisionIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionId"));
            contactNameComboBox.setItems(ContactQueries.contacts());
            userIdComboBox.setItems(UserQueries.users());
            startEndTimeCombos();
        }
        catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /** Creates the lists for the start time combo boxes.
     */
    private void startEndTimeCombos(){

        startTimeComboBox.setItems(TimeUtility.getStartEndTime());
        endTimeComboBox.setItems(TimeUtility.getStartEndTime());
    }


    /** Cancel button on create appointment form. Verifies you wish to cancel and lose unsaved data, returns to main form
     * @param actionEvent clicked button
     */
    public void OnCancelButton(ActionEvent actionEvent) throws IOException {
        Alert alert3 = new Alert (Alert.AlertType.CONFIRMATION);
        alert3.setTitle("Confirmation");
        alert3.setHeaderText((null));
        alert3.setContentText("Are you sure you want to cancel? All data will be lost.");
        Optional<ButtonType> result = alert3.showAndWait();
        if (alert3.getResult() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.FXML"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    /** Select customer button to schedule appointment for. Provides error if no customer is selected
     * @param actionEvent clicked button
     */
    public void selectCustomerButton(ActionEvent actionEvent) {
        Customer c = (Customer) custCreateAppTable.getSelectionModel().getSelectedItem();
        if (c == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing Selected");
            alert.setContentText("Please select a customer");
            alert.showAndWait();
            return;
        } else {
            customerIdText.setText(String.valueOf(c.getCustomerId()));
        }

    }

    /** Schedule appointment button. Verifies all data is entered correctly, checks for valid times, prints error for invalid data, missing data, invalid times, and saves data to database.
     * Displays confirmation if save successful.
     * Catches exceptions, prints stack trace.
     * @param actionEvent clicked button
     */
    public void onScheduleAppointment(ActionEvent actionEvent) throws SQLException {
        boolean noBlankFields = checkValidEntries();
        if(noBlankFields){
            boolean timesValid = checkWithinBusinessHours();
            if(timesValid){
                boolean noOverlapCustomer = checkSchedulingConflicts();

                if (noOverlapCustomer){
                    try{
                        int rowsAffected = AppointmentQueries.insertAppointment(titleText.getText(), descriptionText.getText(), locationText.getText(), typeText.getText(),
                                LocalDateTime.of(startDatePicker.getValue(), startTimeComboBox.getValue()), LocalDateTime.of(endDatePicker.getValue(), endTimeComboBox.getValue()),
                                Integer.parseInt(customerIdText.getText()), userIdComboBox.getValue().getUserId(), contactNameComboBox.getValue().getContactId());

                        if(rowsAffected >0){

                            Alert alert3 = new Alert (Alert.AlertType.CONFIRMATION);
                            alert3.setTitle("Confirmation");
                            alert3.setHeaderText((null));
                            alert3.setContentText("Appointment has been saved.");
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
                            alert2.setHeaderText("CANNOT SAVE APPOINTMENT");
                            alert2.setContentText("Sorry: Unable to add this appointment at this time. Please try again.");
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

    /** Checks appointment times are within business hours. Provides specific alerts for each scenario.
     * @return Boolean returns true if hours are valid, else returns false
     */
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

    private boolean checkValidEntries(){
        if(customerIdText.getText().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("NO CUSTOMER SELECTED");
            alert.setContentText("To schedule an appointment, please select a customer from the table. For a new customer, please add the customer before scheduling an appointment");
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
            alert.setContentText("Please select a User.");
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
        if( endTimeComboBox.getValue() == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("NO END TIME");
            alert.setContentText("Please select an end time.");
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

        ObservableList<Appointment> customerAppointments = AppointmentQueries.associatedApointments(Integer.parseInt(customerIdText.getText()));

        LocalDateTime existingAppointmentStart;
        LocalDateTime existingAppointmentEnd;

        for(Appointment appointment: customerAppointments){

            existingAppointmentStart = appointment.getStartDateTime();
            existingAppointmentEnd = appointment.getEndDateTime();

            if(newStartDT.isAfter(existingAppointmentStart) && newStartDT.isBefore(existingAppointmentEnd)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("1: SCHEDULING CONFLICT");
                alert.setContentText("Customer already has an appointment during that time.");
                alert.showAndWait();
                return false;
            }
            else if(newEndDT.isAfter(existingAppointmentStart) && newEndDT.isBefore(existingAppointmentEnd)){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("2: SCHEDULING CONFLICT");
                alert.setContentText("Customer already has an appointment during that time.");
                alert.showAndWait();
                return false;
            }
        }
        return true;
    }

}
