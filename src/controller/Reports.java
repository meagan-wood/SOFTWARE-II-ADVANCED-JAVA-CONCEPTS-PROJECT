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
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import model.Appointment;
import model.Contact;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Optional;
import java.util.ResourceBundle;

public class Reports implements Initializable{
    public ComboBox monthComboBox;
    public ComboBox<Contact> reportContactBox;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            monthComboBox.setItems(listOfMonths());
            reportContactBox.setItems(ContactQueries.contacts());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public static ObservableList listOfTypes(){
        ObservableList typeList = FXCollections.observableArrayList("De-Briefing", "Planning", "Open", "Follow-up", " Open", "Other", "Unspecified");
        return typeList;
    }


    public static  ObservableList listOfMonths(){

        ObservableList months = FXCollections.observableArrayList("01 January", "02 February", "03 March", "04 April", "05 May", "06 June", "07 July", "08 August",
                "09 September", "10 October", "11 November", "12 December");
        //
        return months;

    }
//

    public void onReport1Button(ActionEvent actionEvent) {
        ObservableList<String> debriefingList = FXCollections.observableArrayList();
        ObservableList<String> planningList = FXCollections.observableArrayList();
        ObservableList<String> followupList = FXCollections.observableArrayList();
        ObservableList<String> openList = FXCollections.observableArrayList();
        ObservableList<String> newList = FXCollections.observableArrayList();
        ObservableList<String> otherList = FXCollections.observableArrayList();
        ObservableList<String> unspecifiedList = FXCollections.observableArrayList();


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
                //System.out.println(monthId + "  MonthId");

                //String monthId = monthComboBox.getValue().toString();
                //int monthId1 = 2;
                ObservableList<Appointment> appointmentsByMonthList = AppointmentQueries.appointmentsByMonthName(monthId);//monthId
                System.out.println(appointmentsByMonthList + "  AppointmentsByMonthList");
                if(appointmentsByMonthList != null){
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
                        if(appointmentType.equals("New") || appointmentType.equals("New Client") || appointmentType.equals("New Customer")){
                            newList.add(appointmentType);
                        }
                        if(appointmentType.equals("") || appointmentType.isEmpty()){
                            unspecifiedList.add(appointmentType);
                        }
                        otherList.add(appointmentType);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public void onReport2Button(ActionEvent actionEvent) {
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
