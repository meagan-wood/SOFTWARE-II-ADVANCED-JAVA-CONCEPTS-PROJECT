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
import java.util.Optional;
import java.util.ResourceBundle;

public class Reports implements Initializable{
    public ComboBox<Integer> monthComboBox;
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

    public static  ObservableList listOfMonths(){

        ObservableList months = FXCollections.observableArrayList("1 January", "2 February", "3 March", "4 April", "5 May", "6 June", "7 July", "8 August",
                "9 September", "10 October", "11 November", "12 December");
        return months;

    }
//

    public void onReport1Button(ActionEvent actionEvent) {

        if(monthComboBox.getValue() == null){
            Alert alert3 = new Alert (Alert.AlertType.ERROR);
            alert3.setTitle("ERROR");
            alert3.setHeaderText("NO MONTH SELECTED");
            alert3.setContentText("Please select the month to generate report.");
            alert3.showAndWait();
        }
        else{
            ObservableList<String> debrief = FXCollections.observableArrayList();
            ObservableList<String> planning = FXCollections.observableArrayList();
            ObservableList<String> followup = FXCollections.observableArrayList();
            ObservableList<String> other = FXCollections.observableArrayList();
            ObservableList<String> unspecified = FXCollections.observableArrayList();
            System.out.println(monthComboBox.getValue());
            try{
                int monthId = Integer.parseInt(monthComboBox.getValue().toString());
                System.out.println(monthId);
                ObservableList<Appointment> appointmentsByType = AppointmentQueries.appointmentsByMonth(monthId);
                System.out.println(appointmentsByType);



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
