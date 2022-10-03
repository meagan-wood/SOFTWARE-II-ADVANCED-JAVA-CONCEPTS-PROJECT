package controller;

import database.AppointmentQueries;
import database.ContactQueries;
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
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ResourceBundle;

public class Reports implements Initializable{
    public ComboBox monthComboBox;
    public ComboBox<Contact> reportContactBox;
    public TableView contactScheduleTable;
    public TableColumn appointmentIdColumn;
    public TableColumn titleColumn;
    public TableColumn typeColumn;
    public TableColumn descriptionColumn;
    public TableColumn startColumn;
    public TableColumn endColumn;
    public TableColumn customerIdColumn;
    public TableColumn userIdColumn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            monthComboBox.setItems(listOfMonths());
            reportContactBox.setItems(ContactQueries.contacts());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static  ObservableList<String> listOfMonths(){

        ObservableList<String> months = FXCollections.observableArrayList("01 January", "02 February", "03 March", "04 April", "05 May", "06 June", "07 July", "08 August",
                "09 September", "10 October", "11 November", "12 December");
        return months;
    }


    public void onReport1Button(ActionEvent actionEvent) {
        ObservableList<String> debriefingList = FXCollections.observableArrayList();
        ObservableList<String> planningList = FXCollections.observableArrayList();
        ObservableList<String> followupList = FXCollections.observableArrayList();
        ObservableList<String> openList = FXCollections.observableArrayList();
        ObservableList<String> newList = FXCollections.observableArrayList();
        ObservableList<String> otherList = FXCollections.observableArrayList();

        if(monthComboBox.getValue() == null){
            Alert alert3 = new Alert (Alert.AlertType.ERROR);
            alert3.setTitle("ERROR");
            alert3.setHeaderText("NO MONTH SELECTED");
            alert3.setContentText("Please select the month to generate report.");
            alert3.showAndWait();
        }
        else{
            try{
                String monthOfValue = monthComboBox.getValue().toString();
                String monthValueNum = monthOfValue.substring(0,2);
                Integer monthId = Integer.parseInt(monthValueNum);
                ObservableList<Appointment> appointmentsByMonthList = AppointmentQueries.appointmentsByMonthName(monthId);
                for (Appointment appointmentList: appointmentsByMonthList){
                    String appointmentType = appointmentList.getType();
                    if(appointmentType.equals("Planning") || appointmentType.equals("Planning Session")){
                        planningList.add(appointmentType);
                    }
                    if(appointmentType.equals("Debriefing") || appointmentType.equals("De-Briefing")){
                        debriefingList.add(appointmentType);
                    }
                    if(appointmentType.equals("Followup") || appointmentType.equals("Follow-Up")){
                        followupList.add(appointmentType);
                    }
                    if(appointmentType.equals("Open")){
                        openList.add(appointmentType);
                    }
                    if(appointmentType.equals("New") || appointmentType.equals("New Client") || appointmentType.equals("New Customer") || appointmentType.equals("new")){
                        newList.add(appointmentType);
                    }

                    if (appointmentType.equals("Other")){
                        otherList.add(appointmentType);
                    }
                    if (!appointmentType.equals("Planning") && !appointmentType.equals("Planning Session")  && !appointmentType.equals("Debriefing") &&
                            !appointmentType.equals("De-Briefing") && !appointmentType.equals("Followup") && !appointmentType.equals("Follow-Up") && !appointmentType.equals("Open") &&
                            !appointmentType.equals("New") && !appointmentType.equals("New Client") && !appointmentType.equals("New Customer") && !appointmentType.equals("new") &&
                            !appointmentType.equals("Other")){
                        otherList.add(appointmentType);
                        System.out.println(otherList);
                    }


                }
                Alert alert = new Alert (Alert.AlertType.INFORMATION);
                alert.setTitle("REPORT 1");
                alert.setHeaderText("Appointment count by types for month: " + monthOfValue);
                alert.setContentText("There are " + appointmentsByMonthList.size() + " appointments for the month " + "\nPlanning: " + planningList.size() + "\nDe-Briefing: " + debriefingList.size() +
                        "\nFollow-up: " + followupList.size() + "\nOpen " + openList.size() + "\nNew Clients: " + newList.size() + "\nOther: " + otherList.size());
                alert.showAndWait();

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }



    public void onReport2Button(ActionEvent actionEvent) throws SQLException {

        if(reportContactBox.getValue() == null){
            Alert alert3 = new Alert(Alert.AlertType.ERROR);
            alert3.setTitle("ERROR");
            alert3.setHeaderText("NO CONTACT SELECTED");
            alert3.setContentText("Please select the contact to generate report.");
            alert3.showAndWait();
        }
        else{
            Contact contactSelected = reportContactBox.getValue();
            int contactId = contactSelected.getContactId();
            ObservableList<Appointment> appointmentsContacts = AppointmentQueries.appointmentsByContactId(contactId);
            contactScheduleTable.setItems(appointmentsContacts);
            startColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("startDateTime"));
            endColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("endDateTime"));
            appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
            userIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userId"));
        }
    }

    public void onReport3Button(ActionEvent actionEvent) {
    }

    public void onReturnHome(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.FXML"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }
}
