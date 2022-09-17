package controller;

import database.ContactQueries;
import database.CustomerQueries;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

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
    public TextField userIdText;
    public ComboBox contactNameComboBox;
    public TextField appointmentIdText;
    public TextField locationText;
    public TextField titleText;
    public TextField typeText;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;
    public ComboBox startTimeComboBox;
    public ComboBox endTimeComboBox;

    private int index;
    Customer customerToModify = null;
    //private Customer newCustomer;

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
            startEndTimeCombos();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }



    private void startEndTimeCombos(){
        ObservableList<String> schedulingTimes = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(4, 0) ;
        LocalTime end = LocalTime.of(22, 0);
        schedulingTimes.add(start.toString());
        while(start.isBefore(end)){
            start = start.plusMinutes(30);
            schedulingTimes.add(start.toString());
        }
        startTimeComboBox.setItems(schedulingTimes);
        endTimeComboBox.setItems(schedulingTimes);

        /*ObservableList<String> schedulingTimes = FXCollections.observableArrayList();

        ZoneOffset zone = ZoneOffset.of("America/New_York").getRules().getOffset(8, );
        OffsetTime start = LocalTime.now().atOffset(zone);
        //ZoneOffset zoneOffset = zone.getRules().getOffset(Instant.from(start));
        //OffsetTime end = LocalTime.of(22,0).atOffset(zone);
        LocalTime start = LocalTime.of(8, 0);
        //ZoneId zone = ZoneId.of("America/New_York");
        //start.atOffset(zone.getRules().getOffset(LocalDateTime.now(ZoneId.of("America/New_York"))));
        //ZoneId zone = ZoneId.of(String.valueOf(ZoneId.systemDefault()));
        //start.atOffset(zone.getRules().getOffset(LocalDateTime.now()));
        //System.out.println(start.atOffset(zone.getRules().getOffset(LocalDateTime.now())));
        OffsetTime end = LocalTime.of(22, 0).atOffset(ZoneOffset.of("America/New_York"));
        //LocalTime st = ZoneOffset.of("America/New_York").
        System.out.println(end);

*/
    }




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


    public void selectCustomerButton(ActionEvent actionEvent) {
       Customer c = (Customer) custCreateAppTable.getSelectionModel().getSelectedItem();
            if (c == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Nothing Selected");
                alert.setContentText("Please select a customer");
                alert.showAndWait();
                return;
            }
            else{
                customerIdText.setText(String.valueOf(c.getCustomerId()));
            }
    }

    public void onScheduleAppointment(ActionEvent actionEvent) {

        boolean timesValid = checkWithinBusinessHours();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText("Appointment times");
        if(timesValid){
            alert.setContentText("Times are valid");

        } else{
            alert.setContentText("Times are not within business hours");

        }
        alert.showAndWait();
    }

    private boolean checkWithinBusinessHours() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        ZoneId systemZoneId = ZoneId.systemDefault();
        System.out.println(systemZoneId + " DefaultZone");

        LocalDate startDate = startDatePicker.getValue();
        System.out.println(startDate + " StartDatePicker");
        LocalDate endDate = endDatePicker.getValue();
        System.out.println(endDate + " EndDatePicker");

        LocalTime startTime = LocalTime.parse((CharSequence) startTimeComboBox.getSelectionModel().getSelectedItem());
        System.out.println(startTime + " startTime");
        LocalTime endTime = LocalTime.parse((CharSequence) endTimeComboBox.getSelectionModel().getSelectedItem(), formatter);
        System.out.println(endTime + " endTime");
        //LocalTime startTime = (LocalTime) startTimeComboBox.getSelectionModel().getSelectedItem(); //LocalTime.parse();

        LocalDateTime startDateTime = LocalDateTime.of(startDate, startTime);
        System.out.println(startDateTime + " StartDateTime");
        LocalDateTime endDateTime = LocalDateTime.of(endDate, endTime);
        System.out.println(endDateTime + " EndDateTime");

        ZonedDateTime estStartTime = startDateTime.atZone(systemZoneId).withZoneSameInstant(ZoneId.of("America/New_York"));
        System.out.println(estStartTime + " EstStartTime");
        ZonedDateTime estEndTime = endDateTime.atZone(systemZoneId).withZoneSameInstant(ZoneId.of("America/New_York"));
        System.out.println(estEndTime + " EstEndTime");


        if (estStartTime.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("INVALID START TIME");
            alert.setContentText("Business hours are Monday-Friday 8:00AM-10:00PM EST.");
            alert.showAndWait();
            return false;
        }

        if (estStartTime.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("INVALID START TIME");
            alert.setContentText("Business hours are Monday-Friday 8:00AM-10:00PM EST.");
            alert.showAndWait();
            return false;
        }

        if (estEndTime.toLocalTime().isBefore(LocalTime.of(8, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("INVALID END TIME");
            alert.setContentText("Business hours are Monday-Friday 8:00AM-10:00PM EST.");
            alert.showAndWait();
            return false;
        }

        if (estEndTime.toLocalTime().isAfter(LocalTime.of(22, 0))) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("INVALID END TIME");
            alert.setContentText("Business hours are Monday-Friday 8:00AM-10:00PM EST.");
            alert.showAndWait();
            return false;
        }
        return true;
    }


}
