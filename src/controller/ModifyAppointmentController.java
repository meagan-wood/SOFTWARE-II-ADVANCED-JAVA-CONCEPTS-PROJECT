package controller;

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
import javafx.stage.Stage;
import model.Appointment;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class ModifyAppointmentController implements Initializable {
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactNameComboBox.setItems(ContactQueries.contacts());
            startEndTimeCombos();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

    public void onSaveAppointment(ActionEvent actionEvent) {
    }


    public void sendAppointment(Appointment appointment) throws SQLException {

        appointmentToModify = appointment;
        customerIdText.setText(String.valueOf(appointment.getCustomerId()));
        descriptionText.setText(appointment.getDescription());
        userIdText.setText(String.valueOf(appointment.getUserId()));
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
        ObservableList<String> schedulingTimes = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22, 0);
        schedulingTimes.add(start.toString());
        while (start.isBefore(end)) {
            start = start.plusMinutes(30);
            schedulingTimes.add(start.toString());
        }
        startTimeComboBox.setItems(schedulingTimes);
        endTimeComboBox.setItems(schedulingTimes);
    }

}
