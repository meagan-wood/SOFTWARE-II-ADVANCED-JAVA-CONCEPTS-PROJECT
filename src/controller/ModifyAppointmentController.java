package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.util.Optional;

public class ModifyAppointmentController {
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

    public Appointment appointmentToModify = null;

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

    public void onSaveAppointment(ActionEvent actionEvent) {
    }


    public void sendAppointment(Appointment appointment){

        appointmentToModify = appointment;
        customerIdText.setText(String.valueOf(appointment.getCustomerId()));
        descriptionText.setText(appointment.getDescription());
        userIdText.setText(String.valueOf(appointment.getUserId()));
        //contactNameComboBox
        appointmentIdText.setText(String.valueOf(appointment.getAppointmentId()));
        locationText.setText(appointment.getLocation());
        titleText.setText(appointment.getTitle());
        typeText.setText(appointment.getType());
        //startDatePicker
        //endDatePicker
        //startTimeComboBox
        //endTimeComboBox
    }
}
